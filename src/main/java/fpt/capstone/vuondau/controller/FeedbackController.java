package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.service.IFeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    private final IFeedbackService iFeedbackService;


    public FeedbackController(IFeedbackService iFeedbackService) {
        this.iFeedbackService = iFeedbackService;
    }


}
