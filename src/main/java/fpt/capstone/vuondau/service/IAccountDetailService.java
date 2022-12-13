package fpt.capstone.vuondau.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.AccountDetail;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.EmailDto;
import fpt.capstone.vuondau.entity.dto.ProvincesDto;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IAccountDetailService {


    boolean registerTutor(AccountDetailRequest accountDetailRequest);

    List<ResourceDto> uploadImageRegisterProfile(long id, List<UploadAvatarRequest> UploadAvatarRequest);

    List<EmailDto> approveRegisterAccount(List<Long> id);

    ApiPage<AccountDetailResponse> getRequestToActiveAccount(Pageable pageable);


}
