package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.EResourceType;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.repository.AccountDetailRepository;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.IAccountDetailService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountDetailServiceImpl implements IAccountDetailService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final AccountDetailRepository accountDetailRepository;


    private final MinioAdapter minioAdapter;

    private final ResourceRepository resourceRepository;

    @Value("${minio.url}")
    String minioUrl;


    public AccountDetailServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, AccountDetailRepository accountDetailRepository, MinioAdapter minioAdapter, ResourceRepository resourceRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.accountDetailRepository = accountDetailRepository;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public boolean registerTutor(AccountDetailRequest accountDetailRequest) {
        AccountDetail accountDetail = new AccountDetail();

        accountDetail.setFullName(accountDetailRequest.getFullName());
        accountDetail.setBirthDay(accountDetailRequest.getBirthDay());
        accountDetail.setEmail(accountDetailRequest.getEmail());
        accountDetail.setPhone(accountDetailRequest.getPhone());
        // chưa có list cái tỉnh thành
        accountDetail.setTeachingProvince(accountDetailRequest.getTeachingProvince());
        //Nguyên quán
        accountDetail.setDomicile(accountDetailRequest.getDomicile());

        accountDetail.setCurrentAddress(accountDetailRequest.getCurrentAddress());

        accountDetail.setIdCard(accountDetailRequest.getIdCard());

        // ngành học
        accountDetail.setMajors(accountDetailRequest.getMajors());

        // tên trươnng đh / cao đang đã học
        accountDetail.setTrainingSchoolName(accountDetailRequest.getTrainingSchoolName());


        // trinh độ : h , cao đẳng
        accountDetail.setLevel(accountDetailRequest.getLevel());

        List<UploadAvatarRequest> uploadFiles = accountDetailRequest.getUploadFiles();

        List<Resource> resourceList = new ArrayList<>( );
        for (UploadAvatarRequest imageRequest : uploadFiles){
            try {

                String name = imageRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
                ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, imageRequest.getFile().getContentType(),
                        imageRequest.getFile().getInputStream(), imageRequest.getFile().getSize());

                Resource resource = new Resource();
                resource.setName(name);
                resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
                resource.setAccountDetail(accountDetail);
                if (imageRequest.getResourceType().equals(EResourceType.CARTPHOTO)){
                    resource.setResourceType(EResourceType.CARTPHOTO);
                }
                else if (imageRequest.getResourceType().equals(EResourceType.DEGREE)){
                    resource.setResourceType(EResourceType.CARTPHOTO);
                }
                else if (imageRequest.getResourceType().equals(EResourceType.CCCD)){
                    resource.setResourceType(EResourceType.CCCD);
                }

                resourceList.add(resource) ;



            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        accountDetail.setResources(resourceList);
        accountDetailRepository.save(accountDetail) ;
        return false;
    }
}
