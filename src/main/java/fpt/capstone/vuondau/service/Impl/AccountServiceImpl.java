package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.repository.AccountRepository;

import fpt.capstone.vuondau.service.IAccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Optional<Account> opt = accountRepository.findByUsername(username);
//        org.springframework.security.core.userdetails.User springUser = null;
//        if (!opt.isPresent()) {
//            throw new UsernameNotFoundException("User with username: " + username);
//        } else {
//            Account account = opt.get();
//            Role role = account.getRole();
//            Set<GrantedAuthority> ga = new HashSet<>();
////            for (Role role : roleList) {
//            ga.add(new SimpleGrantedAuthority(role.getName()));
////            }
//            springUser = new org.springframework.security.core.userdetails.User(username, account.getPassword(), ga);
//        }
//        return springUser;
//    }

//    @Override
//    public Optional<Account> findByUsername(String username) {
//        return accountRepository.findByUsername(username);
//    }
//
//    @Override
//    public Long saveAccount(Account account) {
//        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//        return accountRepository.save(account).getId();
//    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Long saveAccount(Account account) {
        return null;
    }

    @Override
    public AccountTeacherResponse createTeacherAccount(AccountExistedTeacherRequest accountRequest) {
        return null;
    }

//    @Override
//    public AccountTeacherResponse createTeacherAccount(AccountExistedTeacherRequest accountRequest) {
//        Account account = new Account();
//        account.setUsername(accountRequest.getUsername());
//        account.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));
//        account.setActive(accountRequest.isActive());
//        Teacher teacher = teacherRepository.findById(accountRequest.getTeachId()).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher"));
//        account.setTeacher(teacher);
//
//        Account save = accountRepository.save(account);
//        AccountTeacherResponse response = new AccountTeacherResponse();
//        response.setUsername(save.getUsername());
//        response.setActive(save.getActive());
//        response.setTeacherId(save.getTeacher().getId());
//        return response;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
