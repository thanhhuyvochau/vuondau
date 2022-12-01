package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.VpnPayRequest;
import fpt.capstone.vuondau.entity.response.VpnPayResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface ITransactionService {
    VpnPayResponse startPayment(HttpServletRequest req, VpnPayRequest request) throws UnsupportedEncodingException;

    Boolean executeAfterPayment(HttpServletRequest request);
}
