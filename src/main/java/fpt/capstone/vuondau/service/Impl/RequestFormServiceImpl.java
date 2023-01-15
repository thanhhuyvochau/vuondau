package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Request;
import fpt.capstone.vuondau.entity.RequestReply;
import fpt.capstone.vuondau.entity.RequestType;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ERequestStatus;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.dto.RequestFormReplyDto;
import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.request.RequestFormSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.RequestFormReplyResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import fpt.capstone.vuondau.repository.RequestReplyRepository;
import fpt.capstone.vuondau.repository.RequestRepository;
import fpt.capstone.vuondau.repository.RequestTypeRepository;
import fpt.capstone.vuondau.service.IRequestFormService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import fpt.capstone.vuondau.util.specification.RequestFormSpecificationBuilder;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RequestFormServiceImpl implements IRequestFormService {
    private final MessageUtil messageUtil;


    private final MinioAdapter minioAdapter;

    private final SecurityUtil securityUtil;
    private final RequestRepository requestRepository;

    private final RequestTypeRepository requestTypeRepository;

    private final RequestReplyRepository requestReplyRepository;


    @Value("${minio.url}")
    String minioUrl;

    public RequestFormServiceImpl(MessageUtil messageUtil,
                                  MinioAdapter minioAdapter, SecurityUtil securityUtil, RequestRepository requestRepository,
                                  RequestTypeRepository requestTypeRepository, RequestReplyRepository requestReplyRepository) {
        this.messageUtil = messageUtil;
        this.securityUtil = securityUtil;

        this.minioAdapter = minioAdapter;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.requestReplyRepository = requestReplyRepository;
    }


    @Override
    public RequestFormResponse uploadRequestForm(RequestFormDto requestFormDto) {
        Account student = securityUtil.getCurrentUserThrowNotFoundException();
        try {
            String name = requestFormDto.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, requestFormDto.getFile().getContentType(),
                    requestFormDto.getFile().getInputStream(), requestFormDto.getFile().getSize());

            Request request = new Request();
            request.setTitle(requestFormDto.getTitle());
            request.setReason(requestFormDto.getReason());
            RequestType requestType = requestTypeRepository.findById(requestFormDto.getRequestTypeId())
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request Type") + requestFormDto.getRequestTypeId()));
            request.setRequestType(requestType);
            request.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
            request.setStatus(ERequestStatus.WAITING);
            request.setAccount(student);
            Request save = requestRepository.save(request);
            RequestFormResponse requestFormResponse = ObjectUtil.copyProperties(save, new RequestFormResponse(), RequestFormResponse.class);
            requestFormResponse.setRequestType(ObjectUtil.copyProperties(requestType, new RequestTypeDto(), RequestTypeDto.class));
            AccountResponse accountResponse = ConvertUtil.doConvertEntityToResponse(student);
            requestFormResponse.setStudent(accountResponse);

            return requestFormResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiPage<RequestFormResponse> getAllRequestForm(Pageable pageable) {
        Page<Request> requestPage = requestRepository.findAll(pageable);
        return PageUtil.convert(requestPage.map(ConvertUtil::convertRequestToRequestResponse));
    }


    @Override
    public RequestFormResponse getRequestDetail(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request") + id));
        return ConvertUtil.convertRequestToRequestResponse(request);
    }



    @Override
    public List<RequestTypeDto> getRequestType() {
        List<RequestType> allRequestType = requestTypeRepository.findAll();
        List<RequestTypeDto> requestTypeDtoList = new ArrayList<>();
        allRequestType.forEach(requestType -> {
            RequestTypeDto requestTypeDto = new RequestTypeDto();
            requestTypeDto.setId(requestType.getId());
            requestTypeDto.setName(requestType.getName());
            requestTypeDtoList.add(requestTypeDto);
        });
        return requestTypeDtoList;
    }

    @Override
    public ApiPage<RequestFormResponse> searchRequestForm(RequestFormSearchRequest searchRequestForm, Pageable pageable) {
        RequestFormSpecificationBuilder builder = RequestFormSpecificationBuilder.specification()
                .queryByStatus(searchRequestForm.getStatus())
                .queryByDate(searchRequestForm.getDateFrom(), searchRequestForm.getDateTo())
                .queryByStudent(searchRequestForm.getStudentId())
                .queryLike(searchRequestForm.getQ());
        Page<Request> allRequest = requestRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(allRequest.map(ConvertUtil::convertRequestToRequestResponse));
    }

    @Override
    public RequestFormReplyResponse replyRequest(Long id, RequestFormReplyDto requestFormReplyDto) {
        Account admin = securityUtil.getCurrentUserThrowNotFoundException();
        Request request = requestRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request") + id));
        RequestReply requestReply = new RequestReply();
        requestReply.setRequest(request);
        requestReply.setAccount(admin);
        requestReply.setContent(requestFormReplyDto.getContent());

        if (requestFormReplyDto.getFile() != null) {
            try {
                String name = requestFormReplyDto.getFile().getOriginalFilename() + "-" + Instant.now().toString();
                ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, requestFormReplyDto.getFile().getContentType(),
                        requestFormReplyDto.getFile().getInputStream(), requestFormReplyDto.getFile().getSize());

                requestReply.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        RequestReply save = requestReplyRepository.save(requestReply);
        RequestFormReplyResponse response = new RequestFormReplyResponse();
        response.setId(save.getId());
        response.setReplierId(admin.getId());
        response.setRequestId(request.getId());
        response.setContent(requestFormReplyDto.getContent());
        response.setUrl(save.getUrl());
        return response;
    }
}