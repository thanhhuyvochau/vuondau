package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.PaymentResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

public interface ITransactionService {
    PaymentResponse startPayment(HttpServletRequest req, VpnPayRequest request) throws UnsupportedEncodingException;

    Transaction executeAfterPayment(HttpServletRequest request);
}
