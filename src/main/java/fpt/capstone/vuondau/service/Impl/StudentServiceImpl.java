package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.service.IStudentService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {
    private final MessageUtil messageUtil;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    public StudentServiceImpl(MessageUtil messageUtil, RoleRepository roleRepository, AccountRepository accountRepository) {
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
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
            return ObjectUtil.copyProperties(accountSave, new StudentResponse(), StudentResponse.class);
        }
        return null;
    }
}