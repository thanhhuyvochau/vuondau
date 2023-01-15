package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.dto.RequestFormReplyDto;
import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.request.RequestFormSearchRequest;
import fpt.capstone.vuondau.entity.response.RequestFormReplyResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRequestFormService {

    RequestFormResponse uploadRequestForm(RequestFormDto requestFormDto);

    ApiPage<RequestFormResponse> getAllRequestForm(Pageable pageable);

    RequestFormResponse getRequestDetail(Long id);

    List<RequestTypeDto> getRequestType();

    ApiPage<RequestFormResponse> searchRequestForm(RequestFormSearchRequest searchRequestForm, Pageable pageable);

    RequestFormReplyResponse replyRequest(Long id , RequestFormReplyDto requestFormReplyDto );
}
