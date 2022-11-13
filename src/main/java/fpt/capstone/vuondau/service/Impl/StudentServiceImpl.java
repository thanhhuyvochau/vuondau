package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Request;
import fpt.capstone.vuondau.entity.RequestType;
import fpt.capstone.vuondau.entity.common.ApiException;

import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.request.RequestFormDto;
import fpt.capstone.vuondau.entity.request.StudentDto;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.RequestRepository;
import fpt.capstone.vuondau.repository.RequestTypeRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.service.IStudentService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.RequestUrlUtil;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {
    private final MessageUtil messageUtil;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    private final MinioAdapter minioAdapter;


    private final RequestRepository requestRepository ;

    private final RequestTypeRepository requestTypeRepository ;

    @Value("${minio.url}")
    String minioUrl;

    public StudentServiceImpl(MessageUtil messageUtil, RoleRepository roleRepository, AccountRepository accountRepository, MinioAdapter minioAdapter, RequestRepository requestRepository, RequestTypeRepository requestTypeRepository) {
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.minioAdapter = minioAdapter;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
    }


    @Override
    public RequestFormResponse uploadRequestForm( Long id , RequestFormDto requestFormDto) {
        try{
            String name = requestFormDto.getFile().getOriginalFilename() + "-" + Instant.now().toString() ;
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name ,  requestFormDto.getFile().getContentType(),
                    requestFormDto.getFile().getInputStream(),  requestFormDto.getFile().getSize()) ;
            Request request = new Request() ;
            request.setName(name);
            request.setTitle(requestFormDto.getTitle());
            request.setReason(requestFormDto.getReason());
            RequestType requestType = requestTypeRepository.findById(requestFormDto.getRequestTypeId())
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay request Type") + requestFormDto.getRequestTypeId()));
            request.setRequestType(requestType) ;
            request.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));

            Account student = accountRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay student") + id));
            request.setAccount(student);
            Request save = requestRepository.save(request);

            RequestFormResponse requestFormResponse = ObjectUtil.copyProperties(save, new RequestFormResponse(), RequestFormResponse.class);
            requestFormResponse.setRequestType(ObjectUtil.copyProperties(requestType, new RequestTypeDto(), RequestTypeDto.class));
            requestFormResponse.setStudent(ObjectUtil.copyProperties(student ,new StudentDto(), StudentDto.class));

            return requestFormResponse ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}