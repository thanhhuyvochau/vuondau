package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountDetailStatus;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.service.ITeacherService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService {

    private final AccountRepository accountRepository ;

    private final RoleRepository roleRepository ;

    public TeacherServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Account> getTeacher() {
//        List<Account> allTeacher = accountRepository.findAccountByRole(eAccountRole);
        List<Account> allTeacher1 = accountRepository.findAll();

        return  allTeacher1;
    }

    @Override
    public ApiPage<AccountDetailResponse> getAllTeacher(Pageable pageable) {
        Role role_not_found = roleRepository.findRoleByCode(EAccountRole.TEACHER)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Role not found"));
        Page<Account> accountByRoleAndAccountDetailStatus = accountRepository.findAccountByRoleAndAccountDetailStatus(role_not_found, EAccountDetailStatus.APPROVE, pageable);
       return PageUtil.convert(accountByRoleAndAccountDetailStatus.map(account -> {
           return ConvertUtil.doConvertEntityToResponse(account.getAccountDetail());
       })) ;
    }
}
