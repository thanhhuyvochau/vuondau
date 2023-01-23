package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeacherService {
    List<Account> getTeacher();

    ApiPage<AccountDetailResponse> getAllTeacher(Pageable pageable);

}
