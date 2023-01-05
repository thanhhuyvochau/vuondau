package fpt.capstone.vuondau.service.Impl;



import fpt.capstone.vuondau.repository.FeedbackRepository;
import fpt.capstone.vuondau.service.IFeedbackService;

import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository ;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
}
