package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.PaymentResponse;
import fpt.capstone.vuondau.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final ITransactionService transactionService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PaymentController(ITransactionService transactionService, SimpMessagingTemplate simpMessagingTemplate) {
        this.transactionService = transactionService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(HttpServletRequest req, @RequestBody VpnPayRequest request, final Principal principal) throws IOException {
        return ResponseEntity.ok(ApiResponse.success(transactionService.startPayment(req, request,principal)));
    }


    @GetMapping("/payment-result")
    public void executeAfterPayment(HttpServletRequest request) {
        Transaction transaction = transactionService.executeAfterPayment(request);
//        Account student = transaction.getAccount();
//        Boolean success = transaction.getSuccess();
//        ResponseEntity<ApiResponse<String>> response = null;
//        if (success) {
//            response = ResponseEntity.ok(ApiResponse.success("Thanh toán thành công"));
//        } else {
//            response = ResponseEntity.ok(ApiResponse.success("Thanh toán thất bại"));
//        }
//        simpMessagingTemplate.convertAndSendToUser(student.getUsername(), "/queue/payment-reply", response);
    }


}
