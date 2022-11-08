package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.service.ITeacherService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService {

    private final AccountRepository accountRepository ;

    public TeacherServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public List<Account> getTeacher() {
//        List<Account> allTeacher = accountRepository.findAccountByRole(eAccountRole);
        List<Account> allTeacher1 = accountRepository.findAll();

        return  allTeacher1;
    }
}
