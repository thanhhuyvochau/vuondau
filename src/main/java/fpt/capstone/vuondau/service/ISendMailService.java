package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface ISendMailService {
    String sendMail () ;

}
