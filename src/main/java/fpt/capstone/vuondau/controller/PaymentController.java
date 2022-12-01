package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.VpnPayResponse;
import fpt.capstone.vuondau.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final ITransactionService transactionService;

    public PaymentController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VpnPayResponse>> getPayment(HttpServletRequest req, @RequestBody VpnPayRequest request) throws IOException {
        return ResponseEntity.ok(ApiResponse.success(transactionService.startPayment(req, request)));
    }


    @GetMapping("/payment-result")
    public ResponseEntity<ApiResponse<Boolean>> executeAfterPayment(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.executeAfterPayment(request)));
    }


}
