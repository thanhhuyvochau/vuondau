package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.EResourceType;
import fpt.capstone.vuondau.entity.dto.RoleDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.AccountRepository;

import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.IAccountService;

import fpt.capstone.vuondau.entity.Role;

import fpt.capstone.vuondau.repository.RoleRepository;
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

    private final MinioAdapter minioAdapter;

    private final ResourceRepository resourceRepository;

    @Value("${minio.url}")
    String minioUrl;
    private final SecurityUtil securityUtil;

    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, MessageUtil messageUtil, RoleRepository roleRepository1, Keycloak keycloak, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository, SecurityUtil securityUtil) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository1;
        this.keycloak = keycloak;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
        this.securityUtil = securityUtil;
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
        if (accountRepository.existsAccountByEmail(accountRequest.getEmail())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Email đã tòn tạo"));
        }
        account.setUsername(accountRequest.getUsername());
        account.setName(accountRequest.getName());
//        account.setFirstName(accountRequest.getFirstName());
//        account.setLastName(accountRequest.getLastName());
        account.setPhoneNumber(accountRequest.getPhone());

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
        Account account = new Account();
        AccountRequest studentRequestAccount = studentRequest.getAccount();
        if (studentRequestAccount != null) {

            if (accountRepository.existsAccountByUsername(studentRequestAccount.getUsername())) {
                throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Username đã tồn tại");
            }
            if (accountRepository.existsAccountByEmail(studentRequest.getEmail())) {
                throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Email đã tồn tại");
            }
            account.setUsername(studentRequestAccount.getUsername());
            account.setPassword(studentRequestAccount.getPassword());
            account.setName(studentRequest.getName());
//            account.setFirstName(studentRequest.getFirstName());
//            account.setLastName(studentRequest.getLastName());
            account.setActive(true);
            account.setEmail(studentRequest.getEmail());
            account.setPhoneNumber(studentRequest.getPhoneNumber());
            Role role = roleRepository.findRoleByCode(EAccountRole.STUDENT)
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
            account.setRole(role);
            Account accountSave = accountRepository.save(account);

            Boolean saveAccountSuccess = keycloakUserUtil.create(account);
            Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getCode().name(), account);
            if (saveAccountSuccess && assignRoleSuccess) {
                return ObjectUtil.copyProperties(accountSave, new StudentResponse(), StudentResponse.class);
            }
        }
        return null;  // throw exception in future
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
        account.setBirthday(accountEditRequest.getBirthDay());
        account.setEmail(accountEditRequest.getMail());
        account.setName(accountEditRequest.getName());
        account.setPhoneNumber(accountEditRequest.getPhone());


        Account save = accountRepository.save(account);
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
        account.setBirthday(accountEditRequest.getBirthDay());
        account.setEmail(accountEditRequest.getMail());
        account.setName(accountEditRequest.getName());
        account.setPhoneNumber(accountEditRequest.getPhone());


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
}
