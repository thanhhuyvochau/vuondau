package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.User;
import fpt.capstone.vuondau.repository.UserRepository;
import fpt.capstone.vuondau.service.IUserService;
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
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
        org.springframework.security.core.userdetails.User springUser = null;
        if (!opt.isPresent()) {
            throw new UsernameNotFoundException("User with username: " + username);
        } else {
            User user = opt.get();
            List<Role> roleList = user.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (Role role : roleList) {
                ga.add(new SimpleGrantedAuthority(role.getName()));
            }
            springUser = new org.springframework.security.core.userdetails.User(username, user.getPassword(), ga);
        }
        return springUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
