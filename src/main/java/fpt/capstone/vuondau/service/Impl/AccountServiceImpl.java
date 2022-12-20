package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;

import fpt.capstone.vuondau.entity.common.*;
import fpt.capstone.vuondau.entity.dto.RoleDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.*;

import fpt.capstone.vuondau.service.IAccountService;

import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import fpt.capstone.vuondau.util.keycloak.KeycloakRoleUtil;
import fpt.capstone.vuondau.util.keycloak.KeycloakUserUtil;

import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import io.minio.ObjectWriteResponse;
import org.keycloak.admin.client.Keycloak;
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

import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final RoleRepository roleRepository;
    private final Keycloak keycloak;

    private final KeycloakUserUtil keycloakUserUtil;
    private final KeycloakRoleUtil keycloakRoleUtil;


    private final ResourceRepository resourceRepository;

    private final AccountDetailRepository accountDetailRepository;
    private final MinioAdapter minioAdapter;
    @Value("${minio.url}")
    String minioUrl;
    private final SecurityUtil securityUtil;

    private final SubjectRepository subjectRepository;

    private final ClassLevelRepository classLevelRepository;


    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, MessageUtil messageUtil, RoleRepository roleRepository1, Keycloak keycloak, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository, AccountDetailRepository accountDetailRepository, SecurityUtil securityUtil, SubjectRepository subjectRepository, ClassLevelRepository classLevelRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository1;
        this.keycloak = keycloak;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
        this.accountDetailRepository = accountDetailRepository;
        this.securityUtil = securityUtil;
        this.subjectRepository = subjectRepository;
        this.classLevelRepository = classLevelRepository;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Account saveAccount(Account account) {
        return null;
    }


    @Override
    public AccountTeacherResponse createTeacherAccount(AccountExistedTeacherRequest accountRequest) {


        Account account = new Account();
        if (accountRepository.existsAccountByUsername(accountRequest.getUsername())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("user name  đã tòn tạo"));
        }
//        if (accountRepository.existsAccountByEmail(accountRequest.getEmail())) {
//            throw ApiException.create(HttpStatus.BAD_REQUEST)
//                    .withMessage(messageUtil.getLocalMessage("Email đã tòn tạo"));
//        }
        account.setUsername(accountRequest.getUsername());
//        account.setFirstName(accountRequest.getFirstName());
//        account.setLastName(accountRequest.getLastName());
//        account.setPhoneNumber(accountRequest.getPhone());
        account.setPassword(accountRequest.getPassword());
        EGenderType gender = GenderUtil.getGenderByCode(accountRequest.getGenderCode());
//        account.setGender(gender);
        Role role = roleRepository.findRoleByCode(EAccountRole.TEACHER)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);
        Account save = accountRepository.save(account);
        Boolean saveAccountSuccess = keycloakUserUtil.create(account);
        Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getCode().name(), account);
        if (saveAccountSuccess && assignRoleSuccess) {
            return ObjectUtil.copyProperties(save, new AccountTeacherResponse(), AccountTeacherResponse.class);
        }
        return null;
    }

    @Override
    public ApiPage<AccountResponse> getAccounts(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return PageUtil.convert(accounts.map(ConvertUtil::doConvertEntityToResponse));
    }


    @Override
    public StudentResponse createStudentAccount(StudentRequest studentRequest) {


        //set Account

        if (accountRepository.existsAccountByUsername(studentRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Username đã tồn tại");
        }
        if (accountDetailRepository.existsAccountDetailByPhone(studentRequest.getPhone())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Số điện thoại đã tồn tại");
        }
        if (accountDetailRepository.existsAccountDetailByEmail(studentRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Email đã tồn tại");
        }

        if (!PasswordUtil.validationPassword(studentRequest.getPassword()) || studentRequest.getPassword() == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Mật khẩu phải có ít nhất một ký tự số, ký tự viết thường, ký tự viết hoa, ký hiệu đặc biệt trong số @#$% và độ dài phải từ 8 đến 2"));
        }

        Account account = new Account();
        if (!EmailUtil.isValidEmail(studentRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Email không đúng định dạng. ");
        }
        account.setUsername(studentRequest.getEmail());
        account.setPassword(studentRequest.getPassword());
//        account.setLastName(studentRequest.getLastName());
//        account.setFirstName(studentRequest.getFirstName());
//        account.setEmail(studentRequest.getEmail());
        account.setActive(true);
        Role role = roleRepository.findRoleByCode(EAccountRole.STUDENT)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);

        //set Account Profile

        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setAccount(account);

        EGenderType gender = GenderUtil.getGenderByCode(studentRequest.getGender());
        accountDetail.setGender(gender);
        accountDetail.setPhone(studentRequest.getPhone());
        accountDetail.setEmail(studentRequest.getEmail());
        accountDetail.setBirthDay(studentRequest.getBirthDay());
        accountDetail.setFirstName(studentRequest.getFirstName());
        accountDetail.setLastName(studentRequest.getLastName());
        accountDetail.setCurrentAddress(studentRequest.getCurrentAddress());

        List<Long> subjects = studentRequest.getSubjects();
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
        Long classLevel = studentRequest.getClassLevel();
        if (classLevel != null) {
            ClassLevel classLevel1 = classLevelRepository.findById(classLevel).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class level" + classLevel));
            AccountDetailClassLevel accountDetailClassLevel = new AccountDetailClassLevel();
            AccountDetailClassLevelKey accountDetailClassLevelKey = new AccountDetailClassLevelKey();
            accountDetailClassLevelKey.setClassLevelId(classLevel1.getId());
            accountDetailClassLevelKey.setAccountDetailId(accountDetail.getId());
            accountDetailClassLevel.setId(accountDetailClassLevelKey);
            accountDetailClassLevel.setAccountDetail(accountDetail);
            accountDetailClassLevel.setClassLevel(classLevel1);
            accountDetailClassLevelList.add(accountDetailClassLevel);
        }


        accountDetail.setAccountDetailClassLevels(accountDetailClassLevelList);
        accountDetail.setActive(true);


        account.setAccountDetail(accountDetail);
        Account accountSave = accountRepository.save(account);

        Boolean saveAccountSuccess = keycloakUserUtil.create(account);
        Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getCode().name(), account);
        if (saveAccountSuccess && assignRoleSuccess) {
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(accountSave.getId());
            studentResponse.setEmail(studentRequest.getEmail());
            studentResponse.setName(studentRequest.getFirstName() + " " + studentRequest.getLastName());
            return studentResponse;
        }
        return null;
    }


    @Override
    public Boolean uploadAvatar(long id, UploadAvatarRequest uploadAvatarRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        try {
            String name = uploadAvatarRequest.getFile().getOriginalFilename() + "-" + Instant.now().toString();
            ObjectWriteResponse objectWriteResponse = minioAdapter.uploadFile(name, uploadAvatarRequest.getFile().getContentType(),
                    uploadAvatarRequest.getFile().getInputStream(), uploadAvatarRequest.getFile().getSize());

            Resource resource = new Resource();
            resource.setName(name);
            resource.setUrl(RequestUrlUtil.buildUrl(minioUrl, objectWriteResponse));
//            resource.getAccounts().addAll(accounts) ;
            resource.setAccounts(accounts);
            resource.setResourceType(EResourceType.AVATAR);
            resourceRepository.save(resource);

            account.setResource(resource);
            accountRepository.save(account);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return true;
    }

    @Override
    public AccountResponse editProfile(long id, AccountEditRequest accountEditRequest) {
        return null;
    }

    @Override
    public AccountResponse editTeacherProfile(long id, AccountEditRequest accountEditRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
//        account.setBirthday(accountEditRequest.getBirthDay());
//        account.setEmail(accountEditRequest.getMail());
//        account.setFirstName(accountEditRequest.getFirstName());
//        account.setLastName(accountEditRequest.getLastName());
//        account.setPhoneNumber(accountEditRequest.getPhone());


        Account save = accountRepository.save(account);
        keycloakUserUtil.update(save);
        AccountResponse response = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        if (save.getRole() != null) {
            response.setRole(ObjectUtil.copyProperties(save.getRole(), new RoleDto(), RoleDto.class));
        }

        return response;
    }

    @Override
    public AccountResponse editStudentProfile(long id, AccountEditRequest accountEditRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
//        account.setBirthday(accountEditRequest.getBirthDay());
//        account.setEmail(accountEditRequest.getMail());
//        account.setFirstName(accountEditRequest.getFirstName());
//        account.setLastName(accountEditRequest.getLastName());
//        account.setPhoneNumber(accountEditRequest.getPhone());
//        account.setGender(accountEditRequest.getGender());
        Account save = accountRepository.save(account);
        AccountResponse response = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        if (save.getRole() != null) {
            response.setRole(ObjectUtil.copyProperties(save.getRole(), new RoleDto(), RoleDto.class));
        }

        return response;
    }

    @Override
    public ApiPage<AccountResponse> getTeacherAccounts(Pageable pageable) {
        Role role = roleRepository.findRoleByCode(EAccountRole.TEACHER)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Role not found by code:" + EAccountRole.TEACHER));
        Page<Account> accounts = accountRepository.findAccountByRole(pageable, role);
        return PageUtil.convert(accounts.map(ConvertUtil::doConvertEntityToResponse));
    }

    @Override
    public ApiPage<AccountResponse> getStudentAccounts(Pageable pageable) {
        Role role = roleRepository.findRoleByCode(EAccountRole.STUDENT)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Role not found by code:" + EAccountRole.STUDENT));
        Page<Account> accounts = accountRepository.findAccountByRole(pageable, role);
        return PageUtil.convert(accounts.map(ConvertUtil::doConvertEntityToResponse));
    }

    @Override
    public Boolean banAndUbBanAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
        if (account.getActive()) {
            account.setActive(false);
        } else {
            account.setActive(true);
        }
        accountRepository.save(account);
        return true;
    }

    @Override
    public ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable) {
        AccountSpecificationBuilder builder = AccountSpecificationBuilder.specification()
                .queryLike(query.getQ());

        Page<Account> accountPage = accountRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(accountPage.map(ConvertUtil::doConvertEntityToResponse));

    }

    @Override
    public AccountResponse updateRoleAndActiveAccount(long id, AccountEditRequest accountEditRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
        Role role = roleRepository.findRoleByCode(accountEditRequest.getRole())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay role"));
        account.setRole(role);
        account.setActive(accountEditRequest.isActive());
        Account save = accountRepository.save(account);
        return ConvertUtil.doConvertEntityToResponse(save);
    }

    @Override
    public AccountResponse approveTeacherAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay account") + id));
        account.setActive(true);
        Account save = accountRepository.save(account);
        return ConvertUtil.doConvertEntityToResponse(save);
    }

    @Override
    public AccountResponse getSelfAccount() {
        Account currentUser = securityUtil.getCurrentUser();
        return ConvertUtil.doConvertEntityToResponse(currentUser);
    }

    public AccountResponse getAccountById(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + id));
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
        if (account.getResource() != null) {
            accountResponse.setAvatar(account.getResource().getUrl());
        }
        return accountResponse;
    }


    @Override
    public ApiPage<AccountDetailResponse> getAllInfoTeacher(Pageable pageable) {
        Page<AccountDetail> all = accountDetailRepository.findAllByIsActiveIsTrue(pageable);
        return PageUtil.convert(all.map(ConvertUtil::doConvertEntityToResponse));
    }

}
