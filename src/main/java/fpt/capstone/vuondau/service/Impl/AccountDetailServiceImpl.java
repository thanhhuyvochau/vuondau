package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.*;
import fpt.capstone.vuondau.entity.dto.EmailDto;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.RequestEditAccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;

import fpt.capstone.vuondau.entity.response.FeedbackAccountLogResponse;
import fpt.capstone.vuondau.entity.response.ResponseAccountDetailResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountDetailServiceImpl implements IAccountDetailService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final PasswordEncoder passwordEncoder;

    private final AccountDetailRepository accountDetailRepository;

    private final FeedbackAccountLogRepository feedbackAccountLogRepository;


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

    public AccountDetailServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, PasswordEncoder passwordEncoder, AccountDetailRepository accountDetailRepository, FeedbackAccountLogRepository feedbackAccountLogRepository, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository, RoleRepository roleRepository, SecurityUtil securityUtil, SubjectRepository subjectRepository, ClassLevelRepository classLevelRepository, SendMailServiceImplServiceImpl sendMailServiceImplService) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.passwordEncoder = passwordEncoder;
        this.accountDetailRepository = accountDetailRepository;
        this.feedbackAccountLogRepository = feedbackAccountLogRepository;
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


        AccountDetail accountDetail = new AccountDetail();

        if (accountRepository.existsAccountByUsername(accountDetailRequest.getEmail())
                || accountDetailRequest.getEmail() == null
                || accountDetailRepository.existsAccountByEmail(accountDetailRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Email đã có tài khoản trong hệ thống"));
        }

        if (accountDetailRepository.existsAccountDetailByPhone(accountDetailRequest.getPhone()) || accountDetailRequest.getPhone() == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Phone đã có tà khoản trong hệ thống"));
        }

        accountDetail.setTeachingProvince(accountDetailRequest.getTeachingProvince());

        accountDetail.setLastName(accountDetailRequest.getLastName());
        accountDetail.setFirstName(accountDetailRequest.getFirstName());
//        if (accountDetailRequest.getBirthDay() != null) {
//            if (checkBirthday(accountDetailRequest.getBirthDay().toString())) {
//                accountDetail.setBirthDay(accountDetailRequest.getBirthDay());
//            }
//        }
        accountDetail.setBirthDay(accountDetailRequest.getBirthDay());

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
        if (!PasswordUtil.validationPassword(accountDetailRequest.getPassword()) || accountDetailRequest.getPassword() == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Mật khẩu phải có ít nhất một ký tự số, ký tự viết thường, ký tự viết hoa, ký hiệu đặc biệt trong số @#$% và độ dài phải từ 8 đến 20"));
        }
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
        accountDetail.setStatus(EAccountDetailStatus.REQUESTING);
        accountDetail.setActive(true);


        List<Resource> resourceList = new ArrayList<>();
        for (UploadAvatarRequest uploadImageRequest : accountDetailRequest.getFiles()) {
            try {
                String name = uploadImageRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
                ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadImageRequest.getFile().getContentType(),
                        uploadImageRequest.getFile().getInputStream(), uploadImageRequest.getFile().getSize());

                Resource resource = new Resource();
                resource.setName(name);
                resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
                resource.setAccountDetail(accountDetail);
                if (uploadImageRequest.getResourceType().equals(EResourceType.CARTPHOTO)) {
                    resource.setResourceType(EResourceType.CARTPHOTO);
                } else if (uploadImageRequest.getResourceType().equals(EResourceType.DEGREE)) {
                    resource.setResourceType(EResourceType.CARTPHOTO);
                } else if (uploadImageRequest.getResourceType().equals(EResourceType.CCCDONE)) {
                    resource.setResourceType(EResourceType.CCCD);

                } else if (uploadImageRequest.getResourceType().equals(EResourceType.CCCDTWO)) {
                    resource.setResourceType(EResourceType.CCCD);
                }
                resourceList.add(resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (resourceList.size() < 4) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Hệ thống cần bạn upload đầy đủ hình ảnh."));
        }

        accountDetail.setResources(resourceList);


        Account account = new Account();
        account.setUsername(accountDetailRequest.getEmail());
        account.setIsActive(true);
        account.setKeycloak(true);
        account.setPassword(accountDetail.getPassword());
        Role role = roleRepository.findRoleByCode(EAccountRole.TEACHER)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);


        accountDetail.setAccount(account);

        Boolean saveAccountSuccess = keycloakUserUtil.create(account);
        Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getCode().name(), account);

        AccountDetail save = accountDetailRepository.save(accountDetail);


        return save.getId();
    }


    @Override
    public List<ResourceDto> uploadImageRegisterProfile(Long id, UploadAvatarRequest uploadImageRequest) {

        AccountDetail accountDetail = accountDetailRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));

        List<Resource> resourceList = new ArrayList<>();

        try {
            String name = uploadImageRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadImageRequest.getFile().getContentType(),
                    uploadImageRequest.getFile().getInputStream(), uploadImageRequest.getFile().getSize());

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
            resourceDtoList.add(resourceDto);
        });
        return resourceDtoList;
    }

    @Override
    public ResponseAccountDetailResponse approveRegisterAccount(RequestEditAccountDetailRequest editAccountDetailRequest) {
        ResponseAccountDetailResponse response = new ResponseAccountDetailResponse();
        List<AccountDetail> accountDetailList = new ArrayList<>();
        Account teacher = accountRepository.findById(editAccountDetailRequest.getId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay tài khoản")));
        List<FeedbackAccountLog> feedbackAccountLogs = new ArrayList<>();

        AccountDetail accountDetail = teacher.getAccountDetail();
        if (accountDetail != null) {
            EmailDto emailDto = new EmailDto();

            if (accountDetail.getEmail() == null) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có email"));
            }
            if (accountDetail.getPassword() == null) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có password"));
            }

            FeedbackAccountLog log = new FeedbackAccountLog();
            log.setAccountDetail(accountDetail);
            log.setAccount(teacher);
            log.setStatus(EFeedbackAccountLogStatus.APPROVE);
            log.setContent(editAccountDetailRequest.getContent());
            feedbackAccountLogs.add(log);
            accountDetail.setFeedbackAccountLogs(feedbackAccountLogs);
            accountDetailList.add(accountDetail);
            accountDetail.setStatus(EAccountDetailStatus.REQUESTED);
            accountDetail.setPassword(PasswordUtil.BCryptPasswordEncoder(accountDetail.getPassword()));

            accountDetailList.add(accountDetail);
//                emailDto.setMail(accountDetail.getEmail());
//                emailDto.setName(accountDetail.getFirstName() + "" + accountDetail.getLastName());
//                emailDto.setPassword(accountDetail.getPassword());

//                mail.add(emailDto);

            AccountDetailResponse accountDetailResponse = ConvertUtil.doConvertEntityToResponse(accountDetail);
            List<FeedbackAccountLogResponse> feedbackAccountLogResponseList = new ArrayList<>();
            response.setAccountDetail(accountDetailResponse);
            feedbackAccountLogs.forEach(feedbackAccountLog -> {
                feedbackAccountLogResponseList.add(ConvertUtil.doConvertEntityToResponse(log));
            });
            response.setFeedbackAccountLog(feedbackAccountLogResponseList);
        }


        List<FeedbackAccountLog> feedbackAccountLog = feedbackAccountLogRepository.saveAll(feedbackAccountLogs);


//        sendMailServiceImplService.sendMail(mail);
        return response;
    }

    @Override
    public ResponseAccountDetailResponse requestEditRegisterAccount(RequestEditAccountDetailRequest editAccountDetailRequest) {
        ResponseAccountDetailResponse response = new ResponseAccountDetailResponse();
        Account teacher = securityUtil.getCurrentUserThrowNotFoundException();
//        List<EmailDto> mail = new ArrayList<>();
        List<AccountDetail> accountDetailList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        ids.add(editAccountDetailRequest.getId());
        Account account = accountRepository.findById(editAccountDetailRequest.getId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay tài khoản")));

        List<FeedbackAccountLog> feedbackAccountLogs = new ArrayList<>();

        AccountDetail accountDetail = account.getAccountDetail();
        if (accountDetail != null) {
//            EmailDto emailDto = new EmailDto();

            if (accountDetail.getEmail() == null) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có email"));
            }
            if (accountDetail.getPassword() == null) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("Không thể phê duyệt tài khoản vì không có password"));
            }

            accountDetail.setStatus(EAccountDetailStatus.REQUESTING);

            FeedbackAccountLog log = new FeedbackAccountLog();
            log.setAccountDetail(accountDetail);
            log.setAccount(teacher);
            log.setStatus(EFeedbackAccountLogStatus.EDITREQUEST);
            log.setContent(editAccountDetailRequest.getContent());
            feedbackAccountLogs.add(log);

            accountDetail.setFeedbackAccountLogs(feedbackAccountLogs);


            accountDetailList.add(accountDetail);
//            emailDto.setMail(accountDetail.getEmail());
//            emailDto.setName(accountDetail.getFirstName() + "" + accountDetail.getLastName());
//            emailDto.setPassword(accountDetail.getPassword());

//            mail.add(emailDto);
            AccountDetailResponse accountDetailResponse = ConvertUtil.doConvertEntityToResponse(accountDetail);

            response.setAccountDetail(accountDetailResponse);
            List<FeedbackAccountLogResponse> feedbackAccountLogResponseList = new ArrayList<>();
            response.setAccountDetail(accountDetailResponse);
            feedbackAccountLogs.forEach(feedbackAccountLog -> {
                feedbackAccountLogResponseList.add(ConvertUtil.doConvertEntityToResponse(log));
            });
            response.setFeedbackAccountLog(feedbackAccountLogResponseList);
        }


        List<FeedbackAccountLog> feedbackAccountLog = feedbackAccountLogRepository.saveAll(feedbackAccountLogs);


//        sendMailServiceImplService.sendMail(mail);
        return response;
    }

    @Override
    public ResponseAccountDetailResponse refuseRegisterAccount(RequestEditAccountDetailRequest editAccountDetailRequest) {
        ResponseAccountDetailResponse response = new ResponseAccountDetailResponse();
        Account teacher = securityUtil.getCurrentUserThrowNotFoundException();
//        List<EmailDto> mail = new ArrayList<>();
        List<AccountDetail> accountDetailList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        ids.add(editAccountDetailRequest.getId());
        Account account = accountRepository.findById(editAccountDetailRequest.getId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay tài khoản")));

        List<FeedbackAccountLog> feedbackAccountLogs = new ArrayList<>();

        AccountDetail accountDetail = account.getAccountDetail();
        if (accountDetail != null) {
//            EmailDto emailDto = new EmailDto();

            accountDetail.setStatus(EAccountDetailStatus.REFUSE);

            FeedbackAccountLog log = new FeedbackAccountLog();
            log.setAccountDetail(accountDetail);
            log.setAccount(teacher);
            log.setStatus(EFeedbackAccountLogStatus.REFUSE);
            log.setContent(editAccountDetailRequest.getContent());
            feedbackAccountLogs.add(log);

            accountDetail.setFeedbackAccountLogs(feedbackAccountLogs);


            accountDetailList.add(accountDetail);
//            emailDto.setMail(accountDetail.getEmail());
//            emailDto.setName(accountDetail.getFirstName() + "" + accountDetail.getLastName());
//            emailDto.setPassword(accountDetail.getPassword());

//            mail.add(emailDto);
            AccountDetailResponse accountDetailResponse = ConvertUtil.doConvertEntityToResponse(accountDetail);

            response.setAccountDetail(accountDetailResponse);
            List<FeedbackAccountLogResponse> feedbackAccountLogResponseList = new ArrayList<>();
            response.setAccountDetail(accountDetailResponse);
            feedbackAccountLogs.forEach(feedbackAccountLog -> {
                feedbackAccountLogResponseList.add(ConvertUtil.doConvertEntityToResponse(log));
            });
            response.setFeedbackAccountLog(feedbackAccountLogResponseList);
        }


        List<FeedbackAccountLog> feedbackAccountLog = feedbackAccountLogRepository.saveAll(feedbackAccountLogs);


//        sendMailServiceImplService.sendMail(mail);
        return response;
    }


    @Override
    public ApiPage<AccountDetailResponse> getRequestToActiveAccount(EAccountDetailStatus status, Pageable pageable) {

        Role role = roleRepository.findRoleByCode(EAccountRole.TEACHER)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));

        Page<Account> accounts = null;

        status = EAccountDetailStatus.REQUESTING;

        accounts = accountRepository.findAccountByRoleAndAccountDetailStatus(role, status, pageable);

        return PageUtil.convert(accounts.map(account -> ConvertUtil.doConvertEntityToResponse(account.getAccountDetail())));

    }

    @Override
    public ResponseAccountDetailResponse teacherGetInfo() {
        ResponseAccountDetailResponse response = new ResponseAccountDetailResponse();
        Account teacher = securityUtil.getCurrentUserThrowNotFoundException();
        AccountDetailResponse accountDetailResponse = ConvertUtil.doConvertEntityToResponse(teacher.getAccountDetail());
        response.setAccountDetail(accountDetailResponse);

        List<FeedbackAccountLogResponse> feedbackAccountLogResponseList = new ArrayList<>();
        if (teacher.getAccountDetail() != null) {
            List<FeedbackAccountLog> feedbackAccountLogList = feedbackAccountLogRepository.findFeedbackAccountLogByAccountDetailId(teacher.getAccountDetail().getId());

            feedbackAccountLogList.forEach(feedbackAccountLog -> {
                FeedbackAccountLogResponse convertEntityToResponse = ConvertUtil.doConvertEntityToResponse(feedbackAccountLog);
                feedbackAccountLogResponseList.add(convertEntityToResponse);
            });

        }

        response.setFeedbackAccountLog(feedbackAccountLogResponseList);
        return response;

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
        if (gender != null) {
            accountDetailResponse.setGender(gender.getLabel());
        }

        return accountDetailResponse;
    }


}