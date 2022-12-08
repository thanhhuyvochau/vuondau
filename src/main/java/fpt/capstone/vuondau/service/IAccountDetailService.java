package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IAccountDetailService {


    boolean registerTutor(AccountDetailRequest accountDetailRequest);

    List<ResourceDto> uploadImageRegisterProfile(long id, UploadAvatarRequest uploadAvatarRequest);

    AccountResponse approveRegisterAccount(long id);

    ApiPage<AccountDetailResponse> getRequestToActiveAccount(Pageable pageable);

}
