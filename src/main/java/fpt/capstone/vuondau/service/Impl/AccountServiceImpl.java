package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.AccountRepository;

import fpt.capstone.vuondau.service.IAccountService;

import fpt.capstone.vuondau.entity.Role;

import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.util.keycloak.KeycloakRoleUtil;
import fpt.capstone.vuondau.util.keycloak.KeycloakUserUtil;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import org.keycloak.admin.client.Keycloak;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, MessageUtil messageUtil, RoleRepository roleRepository1, Keycloak keycloak, KeycloakUserUtil keycloakUserUtil, KeycloakRoleUtil keycloakRoleUtil) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository1;
        this.keycloak = keycloak;
        this.keycloakUserUtil = keycloakUserUtil;
        this.keycloakRoleUtil = keycloakRoleUtil;
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
        account.setUsername(accountRequest.getUsername());
        account.setFirstName(accountRequest.getFirstName());
        account.setLastName(accountRequest.getLastName());
        account.setPhoneNumber(account.getPhoneNumber());
        Role role = roleRepository.findRoleByCode(accountRequest.getRoleAccount().name())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);
        Account save = accountRepository.save(account);
        Boolean saveAccountSuccess = keycloakUserUtil.create(account);
        Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getName(), account);
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
            account.setUsername(studentRequestAccount.getUsername());
            account.setPassword(studentRequestAccount.getPassword());
            account.setFirstName(studentRequest.getFirstName());
            account.setLastName(studentRequest.getLastName());
            account.setActive(false);
            account.setEmail(studentRequest.getEmail());
            account.setPhoneNumber(studentRequest.getPhoneNumber());
            Role role = roleRepository.findRoleByCode(EAccountRole.STUDENT.name())
                    .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
            account.setRole(role);
            Account accountSave = accountRepository.save(account);
            Boolean saveAccountSuccess = keycloakUserUtil.create(account);
            Boolean assignRoleSuccess = keycloakRoleUtil.assignRoleToUser(role.getName(), account);
            if (saveAccountSuccess && assignRoleSuccess) {
                return ObjectUtil.copyProperties(accountSave, new StudentResponse(), StudentResponse.class);
            }
        }
        return null;  // throw exception in future
    }
}
