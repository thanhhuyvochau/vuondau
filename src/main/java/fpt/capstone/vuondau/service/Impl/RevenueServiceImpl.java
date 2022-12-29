package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.RequestSearchRequest;
import fpt.capstone.vuondau.entity.request.RevenueSearchRequest;
import fpt.capstone.vuondau.entity.response.RevenueClassResponse;
import fpt.capstone.vuondau.entity.response.SalaryEstimatesResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IRevenueService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.specification.RevenueSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class RevenueServiceImpl implements IRevenueService {

    private final ClassRepository classRepository ;

    private final TransactionRepository transactionRepository ;

    public RevenueServiceImpl(ClassRepository classRepository, TransactionRepository transactionRepository) {
        this.classRepository = classRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<SalaryEstimatesResponse> salaryEstimatesTeachers(BigDecimal priceEachStudent, Long numberStudent , Long numberMonth) {
       return  RevenueUtil.payPriceTeacherReceived(priceEachStudent, numberStudent , numberMonth ) ;
    }

    @Override
    public BigDecimal RevenueClass(Long classId) {

        Class aClass = classRepository.findById(classId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));
        List<Transaction> allByPaymentClass = transactionRepository.findAllByPaymentClass(aClass);
            Long totalMoney = 0L;
        for (Transaction transaction : allByPaymentClass) {
            if (transaction.getSuccess()!=null) {
                if (transaction.getSuccess()){
                    Long l = transaction.getAmount().longValue();
                    totalMoney += l ;
                }
            }

        }
        return BigDecimal.valueOf(totalMoney) ;

    }

    @Override
    public List<RevenueClassResponse> RevenueAllClass() {

      List<Transaction> allByPaymentClass = transactionRepository.findAll();
        List<Class> allClass = allByPaymentClass.stream().map(Transaction::getPaymentClass).distinct().collect(Collectors.toList());

        List<RevenueClassResponse> revenueClassResponseList = new ArrayList<>( );

        allClass.forEach(aClass -> {
            RevenueClassResponse revenueClassResponse = new RevenueClassResponse();
            revenueClassResponse.setClassId(aClass.getId());
            List<Transaction> transactions = transactionRepository.findAllByPaymentClass(aClass);
            Long totalMoney = 0L;
            for (Transaction transaction : transactions) {
                if (transaction.getSuccess()!=null) {
                    if (transaction.getSuccess()){
                        Long l = transaction.getAmount().longValue();
                        totalMoney += l ;
                    }
                }
                revenueClassResponse.setRevenue(totalMoney);
            }

            revenueClassResponseList.add(revenueClassResponse) ;
        });

        return  revenueClassResponseList ;
    }

    @Override
    public List<RevenueClassResponse> searchRevenue(RevenueSearchRequest query) {
//        List<Class> allById = new ArrayList<>() ;
//        if (query.getClassIds()!= null){
//            allById  = classRepository.findAllById(query.getClassIds());
//
//        }
        RevenueSpecificationBuilder builder = RevenueSpecificationBuilder.specification()

                .queryByClasses(query.getClassIds());
//                .queryByTeacherIds(query.getTeacherIds());
        List<Transaction> transactionPage = transactionRepository.findAll(builder.build());
        List<Class> allClass = transactionPage.stream().map(Transaction::getPaymentClass).distinct().collect(Collectors.toList());

        List<RevenueClassResponse> revenueClassResponseList = new ArrayList<>( );
        for (Class aClass : allClass) {
            RevenueClassResponse revenueClassResponse = new RevenueClassResponse();
            revenueClassResponse.setClassId(aClass.getId());
            List<Transaction> transactions = transactionRepository.findAllByPaymentClass(aClass);
            Long totalMoney = 0L;
            for (Transaction transaction : transactions) {
                if (transaction.getSuccess()!=null) {
                    if (transaction.getSuccess()){
                        Long l = transaction.getAmount().longValue();
                        totalMoney += l ;
                    }
                }
                revenueClassResponse.setRevenue(totalMoney);
            }

            revenueClassResponseList.add(revenueClassResponse) ;
        }

        return  revenueClassResponseList ;

    }

    @Override
    public List<RevenueClassResponse> studentGetTuitionFee(Long studentId) {
        return null;
    }
}
