package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.service.IAccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opt = accountRepository.findByUsername(username);
        org.springframework.security.core.userdetails.User springUser = null;
        if (!opt.isPresent()) {
            throw new UsernameNotFoundException("User with username: " + username);
        } else {
            Account account = opt.get();
            List<Role> roleList = account.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (Role role : roleList) {
                ga.add(new SimpleGrantedAuthority(role.getName()));
            }
            springUser = new org.springframework.security.core.userdetails.User(username, account.getPassword(), ga);
        }
        return springUser;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
