package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.VpnPayResponse;
import fpt.capstone.vuondau.service.ITransactionService;
import fpt.capstone.vuondau.util.vnpay.VnpConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class VpnController {
    private final ITransactionService transactionService;

    public VpnController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/payment")
    public VpnPayResponse getPayment(HttpServletRequest req, @RequestBody VpnPayRequest request) throws IOException {
        return transactionService.startPayment(req, request);
    }


    @GetMapping("/payment-result")
    public ResponseEntity<ApiResponse<Boolean>> executeAfterPayment(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.executeAfterPayment(request)));
    }


}
