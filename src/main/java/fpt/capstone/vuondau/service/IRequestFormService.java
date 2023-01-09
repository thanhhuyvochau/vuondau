package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import org.springframework.data.domain.Pageable;

public interface IRequestFormService {

    RequestFormResponse uploadRequestForm(RequestFormDto requestFormDto);

    ApiPage<RequestFormResponse> getAllRequestForm(Pageable pageable);

    RequestFormResponse getRequestDetail(Long id);
}
