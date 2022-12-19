package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.*;
import fpt.capstone.vuondau.entity.dto.EmailDto;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;

import fpt.capstone.vuondau.repository.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.*;
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

    private final SecurityUtil securityUtil;

    private final SubjectRepository subjectRepository;

    private final ClassLevelRepository classLevelRepository;

    private final SendMailServiceImplServiceImpl sendMailServiceImplService;


    @Value("${minio.url}")
    String minioUrl;

    public AccountDetailServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, AccountDetailRepository accountDetailRepository, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository, RoleRepository roleRepository, SecurityUtil securityUtil, SubjectRepository subjectRepository, ClassLevelRepository classLevelRepository, SendMailServiceImplServiceImpl sendMailServiceImplService) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.accountDetailRepository = accountDetailRepository;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
        this.securityUtil = securityUtil;
        this.subjectRepository = subjectRepository;
        this.classLevelRepository = classLevelRepository;
        this.sendMailServiceImplService = sendMailServiceImplService;
    }


    public Boolean checkBirthday(String birthday) {

        LocalDate today = LocalDate.now();

        Instant instant = Instant.parse(birthday);
        LocalDate localDate
                = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();

        Period p = Period.between(localDate, today);
        int checkBirthday = p.getYears();
        System.out.println("You are " + p.getYears() + " years");
        if (checkBirthday <= 18) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Bạn chưa đủ 18 tuổi"));
        }
        return true;
    }


    @Override
    public Long registerTutor(AccountDetailRequest accountDetailRequest) {

//        Account teacher = securityUtil.getCurrentUser();

        AccountDetail accountDetail = new AccountDetail();

        if (accountRepository.existsAccountByEmail(accountDetailRequest.getEmail())
                || accountDetailRequest.getEmail() == null
                || accountDetailRepository.existsAccountByEmail(accountDetailRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Email đã có tà khoản trong hệ thống"));
        }

        if (accountDetailRepository.existsAccountByEmail(accountDetailRequest.getPhone()) || accountDetailRequest.getPhone() == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Phone đã có tà khoản trong hệ thống"));
        }

        accountDetail.setTeachingProvince(accountDetailRequest.getTeachingProvince());

        accountDetail.setLastName(accountDetailRequest.getLastName());
        accountDetail.setFirstName(accountDetailRequest.getFirstName());
        if (accountDetailRequest.getBirthDay() != null) {
            if (checkBirthday(accountDetailRequest.getBirthDay().toString())) {
                accountDetail.setBirthDay(accountDetailRequest.getBirthDay());
            }
        }

        //Nguyên quán
        accountDetail.setDomicile(accountDetailRequest.getDomicile());
        accountDetail.setGender(accountDetailRequest.getGender());
        accountDetail.setCurrentAddress(accountDetailRequest.getCurrentAddress());
        if (accountDetailRepository.existsAccountDetailByIdCard(accountDetailRequest.getIdCard()) || accountDetailRequest.getIdCard() == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Số chứng minh / căn cước công dân đã có trong hệ thống"));
        }

        accountDetail.setIdCard(accountDetailRequest.getIdCard());
        accountDetail.setEmail(accountDetailRequest.getEmail());
        accountDetail.setPhone(accountDetailRequest.getPhone());
        accountDetail.setPassword(accountDetailRequest.getPassword());
        // tên trươnng đh / cao đang đã học
        accountDetail.setTrainingSchoolName(accountDetailRequest.getTrainingSchoolName());
        // ngành học
        accountDetail.setMajors(accountDetailRequest.getMajors());
        // trinh độ : h , cao đẳng
        accountDetail.setLevel(accountDetailRequest.getLevel());
        List<Long> subjects = accountDetailRequest.getSubjects();
        List<AccountDetailSubject> accountDetailSubjectList = new ArrayList<>();
        if (subjects != null) {
            List<Subject> allSubjects = subjectRepository.findAllById(subjects);


            allSubjects.forEach(subject -> {
                AccountDetailSubject accountDetailSubject = new AccountDetailSubject();
                AccountDetailSubjectKey accountDetailSubjectKey = new AccountDetailSubjectKey();
                accountDetailSubjectKey.setSubjectId(subject.getId());
                accountDetailSubjectKey.setAccountDetailId(accountDetail.getId());
                accountDetailSubject.setId(accountDetailSubjectKey);
                accountDetailSubject.setSubject(subject);
                accountDetailSubject.setAccountDetail(accountDetail);
                accountDetailSubjectList.add(accountDetailSubject);
            });
        }


        accountDetail.setAccountDetailSubjects(accountDetailSubjectList);
        List<AccountDetailClassLevel> accountDetailClassLevelList = new ArrayList<>();
        List<Long> classLevels = accountDetailRequest.getClassLevels();
        if (classLevels != null) {
            List<ClassLevel> allClassLevel = classLevelRepository.findAllById(classLevels);
            allClassLevel.forEach(classLevel -> {
                AccountDetailClassLevel accountDetailClassLevel = new AccountDetailClassLevel();
                AccountDetailClassLevelKey accountDetailClassLevelKey = new AccountDetailClassLevelKey();
                accountDetailClassLevelKey.setClassLevelId(classLevel.getId());
                accountDetailClassLevelKey.setAccountDetailId(accountDetail.getId());
                accountDetailClassLevel.setId(accountDetailClassLevelKey);
                accountDetailClassLevel.setAccountDetail(accountDetail);
                accountDetailClassLevel.setClassLevel(classLevel);
                accountDetailClassLevelList.add(accountDetailClassLevel);
            });
        }


        accountDetail.setAccountDetailClassLevels(accountDetailClassLevelList);
        accountDetail.setVoice(accountDetailRequest.getVoice());
        accountDetail.setStatus(EAccountDetailStatus.REQUESTED);
        accountDetail.setActive(false);


        AccountDetail save = accountDetailRepository.save(accountDetail);


        return save.getId();
    }
//        List<Resource> resourceList = new ArrayList<>();

//        for (UploadAvatarRequest uploadImageRequest : accountDetailRequest.getUploadFiles()) {
//        UploadAvatarRequest uploadFiles = accountDetailRequest.getUploadFiles();
//        try {
//                String name = accountDetailRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
//                ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, accountDetailRequest.getFile().getContentType(),
//                        accountDetailRequest.getFile().getInputStream(), accountDetailRequest.getFile().getSize());
//
//                Resource resource = new Resource();
//                resource.setName(name);
//                resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
//                resource.setAccountDetail(accountDetail);
//                if (uploadImageRequest.getResourceType().equals(EResourceType.CARTPHOTO)) {
//                    resource.setResourceType(EResourceType.CARTPHOTO);
//                } else if (uploadImageRequest.getResourceType().equals(EResourceType.DEGREE)) {
//                    resource.setResourceType(EResourceType.DEGREE);
//                } else if (uploadImageRequest.getResourceType().equals(EResourceType.CCCD)) {
//                    resource.setResourceType(EResourceType.CCCD);
//                }

//                resourceList.add(resource);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        accountDetail.setResources(resourceList);
//        resourceRepository.saveAll(resourceList);

//
//        List<Resource> resourceList = new ArrayList<>();
//        for (UploadAvatarRequest uploadImageRequest : accountDetailRequest.getUploadFiles()) {
//            try {
//                String name = uploadImageRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
//                ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadImageRequest.getFile().getContentType(),
//                        uploadImageRequest.getFile().getInputStream(), uploadImageRequest.getFile().getSize());
//
//                Resource resource = new Resource();
//                resource.setName(name);
//                resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
//                resource.setAccountDetail(accountDetail);
//                if (uploadImageRequest.getResourceType().equals(EResourceType.CARTPHOTO)) {
//                    resource.setResourceType(EResourceType.CARTPHOTO);
//                } else if (uploadImageRequest.getResourceType().equals(EResourceType.DEGREE)) {
//                    resource.setResourceType(EResourceType.CARTPHOTO);
//                } else if (uploadImageRequest.getResourceType().equals(EResourceType.CCCD)) {
//                    resource.setResourceType(EResourceType.CCCD);
//                }
//
//                resourceList.add(resource);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        if (resourceList.size() <3 ){
//            throw ApiException.create(HttpStatus.BAD_REQUEST)
//                    .withMessage(messageUtil.getLocalMessage("Hệ thống cần bạn upload đầy đủ hình ảnh."));
//        }

//        resourceRepository.saveAll(resourceList);


    @Override
    public List<ResourceDto> uploadImageRegisterProfile(Long id, UploadAvatarRequest uploadImageRequest) {

        AccountDetail accountDetail = accountDetailRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));

        List<Resource> resourceList = new ArrayList<>();

        try {
            String name = uploadImageRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name,  uploadImageRequest.getFile().getContentType(),
                    uploadImageRequest.getFile().getInputStream(),  uploadImageRequest.getFile().getSize());

            Resource resource = new Resource();
            resource.setName(name);
            resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
            resource.setAccountDetail(accountDetail);
            if (uploadImageRequest.getResourceType().equals(EResourceType.CARTPHOTO)) {
                resource.setResourceType(EResourceType.CARTPHOTO);
            } else if (uploadImageRequest.getResourceType().equals(EResourceType.DEGREE)) {
                resource.setResourceType(EResourceType.DEGREE);
            } else if (uploadImageRequest.getResourceType().equals(EResourceType.CCCD)) {
                resource.setResourceType(EResourceType.CCCD);
            }

            resourceList.add(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        resourceRepository.saveAll(resourceList);

        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resourceList.forEach(resource -> {
            ResourceDto resourceDto = ObjectUtil.copyProperties(resource, new ResourceDto(), ResourceDto.class);
            resourceDto.setResourceType(resource.getResourceType());
            resourceDtoList.add(resourceDto) ;
        });
        return resourceDtoList;
    }

    @Override
    public List<EmailDto> approveRegisterAccount(List<Long> id) {
        List<EmailDto> mail = new ArrayList<>();
        List<AccountDetail> accountDetailList = new ArrayList<>();
        List<AccountDetail> accountDetails = accountDetailRepository.findAllByIdInAndIsActiveIsFalse(id);
        if (accountDetails != null) {
            accountDetails.forEach(accountDetail -> {
                EmailDto emailDto = new EmailDto();

                accountDetail.setActive(true);
                Account account = new Account();
                if (accountDetail.getEmail() == null) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có email"));
                }
                if (accountDetail.getPassword() == null) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có password"));
                }
                account.setUsername(accountDetail.getEmail());
                account.setPassword(accountDetail.getPassword());
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
                accountDetailList.add(accountDetail);
                emailDto.setMail(accountDetail.getEmail());
                emailDto.setName(accountDetail.getFirstName() + "" + accountDetail.getLastName());
                emailDto.setPassword(accountDetail.getPassword());

                mail.add(emailDto);
            });
        }

        sendMailServiceImplService.sendMail(mail);
        List<AccountDetail> accountDetailList1 = accountDetailRepository.saveAll(accountDetailList);

        return mail;
    }

    @Override
    public ApiPage<AccountDetailResponse> getRequestToActiveAccount( Boolean isActive , Pageable pageable) {
        Page<AccountDetail> accountDetailPage = accountDetailRepository.findAllByIsActive(isActive,pageable);

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
            accountDetailResponse.setActive(accountDetail.getActive());
            return accountDetailResponse;
        }));

    }

    @Override
    public AccountDetailResponse getAccountDetail(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account")));
        AccountDetail accountDetail = accountDetailRepository.findByAccount(account);
        AccountDetailResponse accountDetailResponse = ObjectUtil.copyProperties(accountDetail, new AccountDetailResponse(), AccountDetailResponse.class);
        List<AccountDetailSubject> accountDetailSubjects = accountDetail.getAccountDetailSubjects();
        List<SubjectDto> subjects = new ArrayList<>();
        accountDetailSubjects.forEach(accountDetailSubject -> {
            subjects.add(ObjectUtil.copyProperties(accountDetailSubject.getSubject(), new SubjectDto(), SubjectDto.class));
        });
        accountDetailResponse.setSubjects(subjects);

        List<Resource> resources = accountDetail.getResources();

        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resources.forEach(resource -> {
            resourceDtoList.add(ObjectUtil.copyProperties(resource, new ResourceDto(), ResourceDto.class));
        });

        accountDetailResponse.setResources(resourceDtoList);

        EGenderType gender = accountDetail.getGender();
        if (gender!= null){
            accountDetailResponse.setGender(gender.getLabel());
        }

        return accountDetailResponse;
    }


}