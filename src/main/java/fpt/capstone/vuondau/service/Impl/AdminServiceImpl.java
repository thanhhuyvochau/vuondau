package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Dto.RoleDto;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.AccountSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.CourseRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;

import fpt.capstone.vuondau.util.specification.CourseSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private final AccountRepository accountRepository ;

    private final MessageUtil messageUtil;

    private final RoleRepository roleRepository ;

    private final CourseRepository courseRepository ;

    public AdminServiceImpl(AccountRepository accountRepository, MessageUtil messageUtil, RoleRepository roleRepository, CourseRepository courseRepository) {
        this.accountRepository = accountRepository;
        this.messageUtil = messageUtil;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable) {
        AccountSpecificationBuilder builder = AccountSpecificationBuilder.specification()
                .queryLike(query.getQ()) ;

        Page<Account> accountPage = accountRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(accountPage.map(this::convertAccountToAccountResponse));

    }


    public AccountResponse convertAccountToAccountResponse(Account account){
        AccountResponse accountResponse = ObjectUtil.copyProperties(account , new AccountResponse(), AccountResponse.class) ;
        if (account.getRole()!= null) {
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto() , RoleDto.class));
        }
        return  accountResponse;
    }


    @Override
    public ApiPage<AccountResponse> viewAllAccountDetail(Pageable pageable) {
        Page<Account> allAccount = accountRepository.findAll(pageable);
        return PageUtil.convert(allAccount
                .map(account -> {
                    AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
                    if (account.getRole()!= null) {
                        accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto() , RoleDto.class));
                    }
                    return accountResponse;
                }));
    }

    @Override
    public AccountResponse viewAccountDetail(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
        if (account.getRole()!= null) {
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto() , RoleDto.class));
        }
        return accountResponse;
    }

    @Override
    public Boolean banAndUbBanAccount(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        if (account.getActive()== true){
            account.setActive(false);
        }else {
            account.setActive(true);
        }
        accountRepository.save(account) ;
        return true ;
    }

    @Override
    public AccountResponse updateRoleAccount(long id, EAccountRole eAccountRole) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay account") + id));
        Role role = roleRepository.findRoleByCode(eAccountRole.name())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Khong tim thay role")));
        account.setRole(role);
        Account save = accountRepository.save(account);
        AccountResponse accountResponse = ObjectUtil.copyProperties(save, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(role, new RoleDto() , RoleDto.class));
        return accountResponse;
    }

    @Override
    public ApiPage<CourseResponse> searchCourse(AccountSearchRequest query, Pageable pageable) {
        CourseSpecificationBuilder builder = CourseSpecificationBuilder.specification()
                .queryLike(query.getQ()) ;

        Page<Course> coursePage = courseRepository.findAll(builder.build(), pageable);
        return PageUtil.convert(coursePage.map(this::convertCourseToCourseResponse));
    }

    public CourseResponse convertCourseToCourseResponse(Course course){
        CourseResponse accountResponse = ObjectUtil.copyProperties(course , new CourseResponse(), CourseResponse.class) ;
        return  accountResponse;
    }
}
