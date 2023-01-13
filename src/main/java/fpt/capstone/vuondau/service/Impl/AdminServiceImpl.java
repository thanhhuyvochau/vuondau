package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;


import fpt.capstone.vuondau.entity.dto.*;

import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.ConvertUtil;
import fpt.capstone.vuondau.util.ObjectUtil;

import fpt.capstone.vuondau.util.PageUtil;

import fpt.capstone.vuondau.util.specification.RequestFormSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private final ClassRepository classRepository;
    private final FeedbackRepository feedbackRepository;
    private final RequestRepository requestRepository;

    public AdminServiceImpl(ClassRepository classRepository, FeedbackRepository feedbackRepository, RequestRepository requestRepository) {
        this.classRepository = classRepository;
        this.feedbackRepository = feedbackRepository;
        this.requestRepository = requestRepository;
    }


    @Override
    public FeedBackDto viewStudentFeedbackClass(Long classId) {
        Class fbclass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay class") + classId));


        FeedBackDto feedBackDto = new FeedBackDto();
        ClassDto classDto = ObjectUtil.copyProperties(fbclass, new ClassDto(), ClassDto.class);
        feedBackDto.setClassInfo(classDto);

        List<FeedBack> feedBacks = feedbackRepository.countAllByClazz(fbclass);
        List<FeedBacClassDto> feedBacClassDtoList = new ArrayList<>();
        List<FeedBacClassDto> collect = feedBacks.stream().map(feedBack -> {
            FeedBacClassDto feedBacClassDto = ObjectUtil.copyProperties(feedBack, new FeedBacClassDto(), FeedBacClassDto.class);
            feedBacClassDtoList.add(feedBacClassDto);
            return feedBacClassDto;
        }).collect(Collectors.toList());
        feedBackDto.setFeedBacClass(feedBacClassDtoList);
        return feedBackDto;
    }




}
