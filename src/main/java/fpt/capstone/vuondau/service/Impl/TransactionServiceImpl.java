package fpt.capstone.vuondau.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.StudentClass;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.PaymentResponse;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.TransactionRepository;
import fpt.capstone.vuondau.service.IMoodleService;
import fpt.capstone.vuondau.service.ITransactionService;
import fpt.capstone.vuondau.util.SecurityUtil;
import fpt.capstone.vuondau.util.TransactionUtil;
import fpt.capstone.vuondau.util.WebSocketUtil;
import fpt.capstone.vuondau.util.vnpay.VnpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {
    private final VnpConfig vnpConfig;
    private final SecurityUtil securityUtil;
    private final TransactionRepository transactionRepository;
    private final ClassRepository classRepository;
    private final WebSocketUtil webSocketUtil;
    private final TransactionUtil transactionUtil;
    private final IMoodleService moodleService;
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(VnpConfig vnpConfig, SecurityUtil securityUtil, TransactionRepository transactionRepository, ClassRepository classRepository, WebSocketUtil webSocketUtil, TransactionUtil transactionUtil, IMoodleService moodleService) {
        this.vnpConfig = vnpConfig;
        this.securityUtil = securityUtil;
        this.transactionRepository = transactionRepository;
        this.classRepository = classRepository;
        this.webSocketUtil = webSocketUtil;
        this.transactionUtil = transactionUtil;
        this.moodleService = moodleService;
    }

    @Override
    public PaymentResponse startPayment(HttpServletRequest req, VpnPayRequest request) throws UnsupportedEncodingException {
        Class clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Class not found with id:" + request.getClassId()));
        if (!clazz.getStatus().equals(EClassStatus.NOTSTART)) {
            throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage("You cannot buy this class because status is invalid!");
        }
        logger.debug("PRINCIPAL:" + request.getSessionId());
        Account student = securityUtil.getCurrentUser();
        boolean isInClass = clazz.getStudentClasses().stream().map(StudentClass::getAccount).collect(Collectors.toList()).contains(student);
        boolean isPaidClass = transactionUtil.isClassPaid(student, clazz);
        if (isPaidClass) {
            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Class was paid, try other class!");
        }
        if (isInClass) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Student already in this class!");
        }
        if (clazz.getNumberStudent() >= clazz.getMaxNumberStudent()) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Class got maximum number of student :(( !");
        }
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = request.getVnp_OrderInfo();
        String orderType = request.getOrdertype();
        String vnp_IpAddr = vnpConfig.getIpAddress(req);
        String vnp_TmnCode = vnpConfig.getVnp_TmnCode();

        int amount = clazz.getFinalPrice().intValue() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        Transaction transaction = new Transaction();
        Account account = securityUtil.getCurrentUser();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setVpnCommand(vnp_Command);
        transaction.setOrderInfo(vnp_OrderInfo);
        transaction.setPaymentClass(clazz);
        transactionRepository.save(transaction);
        String vnp_TxnRef = transaction.getId().toString();
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef + "&" + request.getSessionId());
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "VN";
        vnp_Params.put("vnp_Locale", locate);
        vnp_Params.put("vnp_ReturnUrl", vnpConfig.getVnp_Returnurl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT-7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.YEAR, 1);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        SimpleDateFormat formatterCheck = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("EXPIRED:" + formatterCheck.format(cld.getTime()));
        //Billing


        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpConfig.hmacSHA512(vnpConfig.getVnp_HashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpConfig.getVnp_PayUrl() + "?" + queryUrl;
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setCode("00");
        paymentResponse.setMessage("success");
        paymentResponse.setPaymentUrl(paymentUrl);
        return paymentResponse;
    }

    @Override
    public Transaction executeAfterPayment(HttpServletRequest request) throws JsonProcessingException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        String[] referenceValues = request.getParameter("vnp_TxnRef").split("&");
        if (referenceValues.length != 2) {
            throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage("Request was edited by someone, try a new payment!");
        }
        String transactionId = referenceValues[0];
        String sessionId = referenceValues[1];

        Transaction transaction = transactionRepository
                .findById(Long.valueOf(transactionId)).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                        .withMessage("Transaction not found with id:" + transactionId));
        transaction.setPayDate(Instant.now());
        if (transactionStatus.equals("00") && responseCode.equals("00")) {
            Class paymentClass = transaction.getPaymentClass();
            if (paymentClass.getNumberStudent() < paymentClass.getMaxNumberStudent()) {
                transaction.setTransactionNo(transactionNo);
                transaction.setSuccess(true);
                StudentClass studentClass = new StudentClass();
                studentClass.setAClass(paymentClass);
                studentClass.setAccount(transaction.getAccount());
                studentClass.setEnrollDate(Instant.now());
                studentClass.setIs_enrolled(true);
                paymentClass.getStudentClasses().add(studentClass);
                paymentClass.setNumberStudent(paymentClass.getNumberStudent() + 1);
                classRepository.save(paymentClass);
                moodleService.enrolUserToCourseMoodle(transaction.getPaymentClass());
            } else {
                throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Class got maximum number of student :(( !");
            }
        } else {
            transaction.setSuccess(false);
        }
        transactionRepository.save(transaction);
        if (sessionId != null) {
            String message = "";
            message = transaction.getSuccess() ? "Thanh toán thành công" : "Thanh toán thất bại";
            webSocketUtil.sendPrivateMessage(message, sessionId);
        }
        return transaction;
    }
}
