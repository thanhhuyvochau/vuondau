package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.AccountSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private final AccountRepository accountRepository ;

    public AdminServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable) {
//        AccountSpecificationBuilder builder = AccountSpecificationBuilder.specification()
//                .queryLike(query.getQ()) ;

//        Page<Account> accountPage = accountRepository.findAll(builder.build(), pageable);
//        return PageUtil.convert(accountPage.map(this::convertAccountToAccountResponse));
        return null ;
    }

    public AccountResponse convertAccountToAccountResponse(Account account){
        AccountResponse accountResponse = ObjectUtil.copyProperties(account , new AccountResponse(), AccountResponse.class) ;
        return  accountResponse;
    }
}
