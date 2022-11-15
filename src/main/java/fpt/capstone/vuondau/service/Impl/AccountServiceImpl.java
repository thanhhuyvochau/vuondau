package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.EResourceType;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.AccountRepository;

import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.IAccountService;

import fpt.capstone.vuondau.entity.Role;

import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.util.RequestUrlUtil;
import fpt.capstone.vuondau.util.adapter.MinioAdapter;
import fpt.capstone.vuondau.util.keycloak.KeycloakRoleUtil;
import fpt.capstone.vuondau.util.keycloak.KeycloakUserUtil;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import io.minio.ObjectWriteResponse;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
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

    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, MessageUtil messageUtil, RoleRepository roleRepository1, Keycloak keycloak, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil, MinioAdapter minioAdapter, ResourceRepository resourceRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository1;
        this.keycloak = keycloak;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
        this.minioAdapter = minioAdapter;
        this.resourceRepository = resourceRepository;
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
        account.setFirstName(accountRequest.getFirstName());
        account.setLastName(accountRequest.getLastName());
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
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }

    @Override
    public StudentResponse studentCreateAccount(StudentRequest studentRequest) {
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
            account.setFirstName(studentRequest.getFirstName());
            account.setLastName(studentRequest.getLastName());
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
            accountRepository.save(account) ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
