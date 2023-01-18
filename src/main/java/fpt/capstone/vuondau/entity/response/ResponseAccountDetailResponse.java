package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;

import java.time.Instant;
import java.util.List;


public class ResponseAccountDetailResponse {


    private AccountDetailResponse accountDetail ;

    private List<FeedbackAccountLogResponse> feedbackAccountLog ;



    public AccountDetailResponse getAccountDetail() {
        return accountDetail;
    }

    public void setAccountDetail(AccountDetailResponse accountDetail) {
        this.accountDetail = accountDetail;
    }

    public List<FeedbackAccountLogResponse> getFeedbackAccountLog() {
        return feedbackAccountLog;
    }

    public void setFeedbackAccountLog(List<FeedbackAccountLogResponse> feedbackAccountLog) {
        this.feedbackAccountLog = feedbackAccountLog;
    }
}
