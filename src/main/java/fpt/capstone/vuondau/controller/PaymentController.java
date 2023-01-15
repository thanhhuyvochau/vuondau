package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.config.socket.ClientHandshakeHandler;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ResponseMessage;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.PaymentResponse;
import fpt.capstone.vuondau.service.ITransactionService;
import fpt.capstone.vuondau.util.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private final WebSocketUtil webSocketUtil;
    @Value("${payment-redirect}")
    private String paymentRedirect;
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(ITransactionService transactionService, SimpMessagingTemplate simpMessagingTemplate, WebSocketUtil webSocketUtil) {
        this.transactionService = transactionService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.webSocketUtil = webSocketUtil;
    }

    @PostMapping

    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(HttpServletRequest req, @RequestBody VpnPayRequest request) throws IOException {
        return ResponseEntity.ok(ApiResponse.success(transactionService.startPayment(req, request)));
    }


    @GetMapping("/payment-result")
    public void executeAfterPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Transaction transaction = transactionService.executeAfterPayment(request);
        response.sendRedirect(paymentRedirect);
    }

//    @PostMapping("/send-private-message/{id}")
//    public void sendPrivateMessage(@RequestBody ResponseMessage message, @PathVariable String id) {
//        logger.debug("SESSION ID:" + id);
//        webSocketUtil.sendPrivateMessage(message.getContent(), id.trim());
//    }


}
