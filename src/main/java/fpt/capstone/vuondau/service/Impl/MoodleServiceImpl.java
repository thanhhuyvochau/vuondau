package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.service.IMoodleService;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.RequestUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MoodleServiceImpl implements IMoodleService {

    final private RequestUtil requestUtil;
    private final MoodleCourseRepository moodleCourseRepository;

    public MoodleServiceImpl(RequestUtil requestUtil, MoodleCourseRepository moodleCourseRepository) {
        this.requestUtil = requestUtil;
        this.moodleCourseRepository = moodleCourseRepository;
    }

    @Override
    public List<CategoryResponse> getCategoryFromMoodle() throws JsonProcessingException {
        MoodleCategoryRequest request = new MoodleCategoryRequest();
        List<MoodleCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>();
        MoodleCategoryRequest.MoodleCategoryBody moodleCategoryBody = new MoodleCategoryRequest.MoodleCategoryBody();
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody);
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategory(request);
        return category;
    }

    @Override
    public Boolean crateCategoryToMoodle(MoodleCreateCategoryRequest.MoodleCreateCategoryBody moodleCreateCategoryBody) throws JsonProcessingException {
        MoodleCreateCategoryRequest request = new MoodleCreateCategoryRequest();

        List<MoodleCreateCategoryRequest.MoodleCreateCategoryBody> moodleCreateCategoryBodyList = new ArrayList<>();
        MoodleCreateCategoryRequest.MoodleCreateCategoryBody set = new MoodleCreateCategoryRequest.MoodleCreateCategoryBody();
        set.setName(moodleCreateCategoryBody.getName());
        set.setParent(moodleCreateCategoryBody.getParent());
        set.setIdnumber(moodleCreateCategoryBody.getIdnumber());
        set.setDescription(moodleCreateCategoryBody.getDescription());
        set.setDescriptionformat(moodleCreateCategoryBody.getDescriptionformat());
//        set.setTheme(moodleCreateCategoryBody.getTheme());
        moodleCreateCategoryBodyList.add(set);

        request.setCategories(moodleCreateCategoryBodyList);

        moodleCourseRepository.postCategory(request);
        return true;
    }

    @Override
    public ApiPage<MoodleClassResponse> synchronizedClass() throws JsonProcessingException {
        MoodleMasterDataRequest request = new MoodleMasterDataRequest();
        List<MoodleClassResponse> course = moodleCourseRepository.getCourse(request);
        Page<MoodleClassResponse> page = new PageImpl<>(course);
        return PageUtil.convert(page.map(moodleClassResponse -> ObjectUtil.copyProperties(moodleClassResponse, new MoodleClassResponse(), MoodleClassResponse.class)));

    }
}
