package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.RevenueSearchRequest;
import fpt.capstone.vuondau.entity.response.RevenueClassResponse;
import fpt.capstone.vuondau.entity.response.SalaryEstimatesResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IRevenueService {


    List<SalaryEstimatesResponse> salaryEstimatesTeachers(BigDecimal priceEachStudent, Long numberStudent, Long numberMonth);

    BigDecimal RevenueClass(Long classId);

    List<RevenueClassResponse> RevenueAllClass();

    List<RevenueClassResponse> searchRevenue(RevenueSearchRequest query);

    List<RevenueClassResponse> studentGetTuitionFee(RevenueSearchRequest query);
}
