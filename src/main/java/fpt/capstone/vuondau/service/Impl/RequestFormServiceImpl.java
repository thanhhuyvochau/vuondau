package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Request;
import fpt.capstone.vuondau.entity.RequestType;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ERequestStatus;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.dto.StudentDto;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.RequestRepository;
import fpt.capstone.vuondau.repository.RequestTypeRepository;
import fpt.capstone.vuondau.service.IRequestFormService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.RequestUrlUtil;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;

@Service
@Transactional
public class RequestFormServiceImpl implements IRequestFormService {
    private final MessageUtil messageUtil;

    private final AccountRepository accountRepository;

    private final MinioAdapter minioAdapter;


    private final RequestRepository requestRepository;

    private final RequestTypeRepository requestTypeRepository;


    @Value("${minio.url}")
    String minioUrl;

    public RequestFormServiceImpl(MessageUtil messageUtil, AccountRepository accountRepository, MinioAdapter minioAdapter, RequestRepository requestRepository, RequestTypeRepository requestTypeRepository) {
        this.messageUtil = messageUtil;
        this.accountRepository = accountRepository;
        this.minioAdapter = minioAdapter;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
    }


    @Override
    public RequestFormResponse uploadRequestForm(Long studentId, RequestFormDto requestFormDto) {
        try {
            String name = requestFormDto.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, requestFormDto.getFile().getContentType(),
                    requestFormDto.getFile().getInputStream(), requestFormDto.getFile().getSize());
            Request request = new Request();
            request.setName(name);
            request.setTitle(requestFormDto.getTitle());
            request.setReason(requestFormDto.getReason());
            RequestType requestType = requestTypeRepository.findById(requestFormDto.getRequestTypeId())
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request Type") + requestFormDto.getRequestTypeId()));
            request.setRequestType(requestType);
            request.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
            request.setStatus(ERequestStatus.WAITING);
            Account student = accountRepository.findById(studentId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay student") + studentId));
            request.setAccount(student);
            Request save = requestRepository.save(request);

            RequestFormResponse requestFormResponse = ObjectUtil.copyProperties(save, new RequestFormResponse(), RequestFormResponse.class);
            requestFormResponse.setRequestType(ObjectUtil.copyProperties(requestType, new RequestTypeDto(), RequestTypeDto.class));
            requestFormResponse.setStudent(ObjectUtil.copyProperties(student, new StudentDto(), StudentDto.class));

            return requestFormResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiPage<RequestFormResponse> getAllRequestForm(Pageable pageable) {
        Page<Request> requestPage = requestRepository.findAll(pageable);
        return PageUtil.convert(requestPage.map(this::convertRequestToRequestResponse));
    }



    private RequestFormResponse convertRequestToRequestResponse(Request request) {
        RequestFormResponse response = new RequestFormResponse();
        StudentDto studentDto = null;
        if (request.getAccount()!= null) {
             studentDto = ObjectUtil.copyProperties(request.getAccount(), new StudentDto(), StudentDto.class);
        }
        response.setId(request.getId());
        response.setStudent(studentDto);
        response.setName(request.getName());
        response.setReason(request.getReason());
        response.setUrl(request.getUrl());
        response.setTitle(request.getTitle());
        response.setStatus(request.getStatus());

        response.setRequestType( ObjectUtil.copyProperties(request.getRequestType(), new RequestTypeDto(), RequestTypeDto.class));
        return  response ;
    }

    @Override
    public RequestFormResponse getRequestDetail(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request") + id));
        return convertRequestToRequestResponse(request);
    }
}