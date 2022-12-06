package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.*;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.repository.AccountDetailRepository;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.service.IAccountDetailService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import fpt.capstone.vuondau.util.keycloak.KeycloakRoleUtil;
import fpt.capstone.vuondau.util.keycloak.KeycloakUserUtil;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountDetailServiceImpl implements IAccountDetailService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final AccountDetailRepository accountDetailRepository;


    private final KeycloakUserUtil keycloakUserUtil;
    private final KeycloakRoleUtil keycloakRoleUtil;

    private final MinioAdapter minioAdapter;

    private final ResourceRepository resourceRepository;

    private final RoleRepository roleRepository;


    @Value("${minio.url}")
    String minioUrl;


    public AccountDetailServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, AccountDetailRepository accountDetailRepository, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.accountDetailRepository = accountDetailRepository;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean registerTutor(AccountDetailRequest accountDetailRequest) {
        AccountDetail accountDetail = new AccountDetail();
        if (accountRepository.existsAccountByEmail(accountDetailRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Email đã có tà khoản trong hệ thống"));
        }
        accountDetail.setLastName(accountDetailRequest.getLastName());
        accountDetail.setFirstName(accountDetail.getFirstName());
        accountDetail.setBirthDay(accountDetailRequest.getBirthDay());
        accountDetail.setEmail(accountDetailRequest.getEmail());
        accountDetail.setPhone(accountDetailRequest.getPhone());
        // chưa có list cái tỉnh thành
        accountDetail.setTeachingProvince(accountDetailRequest.getTeachingProvince());
        //Nguyên quán
        accountDetail.setDomicile(accountDetailRequest.getDomicile());

        accountDetail.setCurrentAddress(accountDetailRequest.getCurrentAddress());

        accountDetail.setIdCard(accountDetailRequest.getIdCard());

        accountDetail.setVoice(accountDetailRequest.getVoice());
        // ngành học
        accountDetail.setMajors(accountDetailRequest.getMajors());

        // tên trươnng đh / cao đang đã học
        accountDetail.setTrainingSchoolName(accountDetailRequest.getTrainingSchoolName());
        accountDetail.setStatus(EAccountDetailStatus.REQUESTED);
        accountDetail.setActive(false);

        // trinh độ : h , cao đẳng
        accountDetail.setLevel(accountDetailRequest.getLevel());

        accountDetailRepository.save(accountDetail);
        return true;
    }

    @Override
    public List<ResourceDto> uploadImageRegisterProfile(long id, UploadAvatarRequest uploadAvatarRequest) {

        AccountDetail accountDetail = accountDetailRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));

        List<Resource> resourceList = new ArrayList<>();

        try {
            String name = uploadAvatarRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadAvatarRequest.getFile().getContentType(),
                    uploadAvatarRequest.getFile().getInputStream(), uploadAvatarRequest.getFile().getSize());

            Resource resource = new Resource();
            resource.setName(name);
            resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
            resource.setAccountDetail(accountDetail);
            if (uploadAvatarRequest.getResourceType().equals(EResourceType.CARTPHOTO)) {
                resource.setResourceType(EResourceType.CARTPHOTO);
            } else if (uploadAvatarRequest.getResourceType().equals(EResourceType.DEGREE)) {
                resource.setResourceType(EResourceType.CARTPHOTO);
            } else if (uploadAvatarRequest.getResourceType().equals(EResourceType.CCCD)) {
                resource.setResourceType(EResourceType.CCCD);
            }

            resourceList.add(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        resourceRepository.saveAll(resourceList);

        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resourceList.stream().map(resource -> {
            resourceDtoList.add(ObjectUtil.copyProperties(resource, new ResourceDto(), ResourceDto.class));
            return resource;
        }).collect(Collectors.toList());
        return resourceDtoList;
    }

    @Override
    public AccountResponse approveRegisterAccount(long id) {

        AccountDetail accountDetail = accountDetailRepository.findByIdAndIsActiveIsFalse(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay request" + id));
        accountDetail.setActive(true);


        Account account = new Account();
        account.setUsername(accountDetail.getEmail());
        account.setPassword("123456");
        account.setActive(true);
        account.setLastName(accountDetail.getLastName());
        account.setFirstName(accountDetail.getFirstName());
        account.setEmail(accountDetail.getEmail());
        account.setGender(accountDetail.getGender());
        account.setPhoneNumber(accountDetail.getPhone());
        Role role = roleRepository.findRoleByCode(EAccountRole.TEACHER).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay role"));

        account.setRole(role);
        account.setBirthday(accountDetail.getBirthDay());
        account.setAccountDetail(accountDetail);


        Boolean saveAccountSuccess = keycloakUserUtil.create(account);
        Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getCode().name(), account);
        Account save = accountRepository.save(account);

        accountDetail.setStatus(EAccountDetailStatus.REQUESTED);
        accountDetail.setAccount(account);
        accountDetailRepository.save(accountDetail);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);

        return accountResponse;
    }

    @Override
    public ApiPage<AccountDetailResponse> getRequestToActiveAccount(Pageable pageable) {
        Page<AccountDetail> accountDetailPage = accountDetailRepository.findAll(pageable);

        return PageUtil.convert(accountDetailPage.map(accountDetail -> {
            AccountDetailResponse accountDetailResponse = ObjectUtil.copyProperties(accountDetail, new AccountDetailResponse(), AccountDetailResponse.class);

            List<AccountDetailSubject> accountDetailSubjects = accountDetail.getAccountDetailSubjects();
            List<SubjectDto> subjects = new ArrayList<>();
            accountDetailSubjects.forEach(accountDetailSubject -> {
                subjects.add(ObjectUtil.copyProperties(accountDetailSubject.getSubject(), new SubjectDto(), SubjectDto.class));
            });
            accountDetailResponse.setSubjects(subjects);
            List<ResourceDto> resourceDtoList = new ArrayList<>();
            List<Resource> resources = accountDetail.getResources();
            resources.forEach(resource -> {
                resourceDtoList.add(ObjectUtil.copyProperties(resource, new ResourceDto(), ResourceDto.class));
            });

            accountDetailResponse.setResources(resourceDtoList);
            accountDetailResponse.setActive(accountDetail.isActive());
            return accountDetailResponse;
        }));

    }
}