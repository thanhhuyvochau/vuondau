package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.repository.AccountRepository;

import fpt.capstone.vuondau.service.IAccountService;

import fpt.capstone.vuondau.entity.Role;

import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService, UserDetailsService {
    private final AccountRepository accountRepository;

    private final MessageUtil messageUtil;

    private final RoleRepository  roleRepository ;
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, MessageUtil messageUtil, RoleRepository roleRepository1) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository1;
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

        AccountTeacherResponse response = ObjectUtil.copyProperties(save, new AccountTeacherResponse(), AccountTeacherResponse.class);
        return response;
    }

    @Override
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
