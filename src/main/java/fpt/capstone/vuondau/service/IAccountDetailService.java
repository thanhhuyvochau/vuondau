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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IAccountDetailService {


    Long registerTutor(AccountDetailRequest accountDetailRequest);

    List<ResourceDto> uploadImageRegisterProfile(Long id,  UploadAvatarRequest uploadImageRequest);

    List<EmailDto> approveRegisterAccount(List<Long> id);

    ApiPage<AccountDetailResponse> getRequestToActiveAccount(Pageable pageable);


}