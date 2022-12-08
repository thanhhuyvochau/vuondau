package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleModuleResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EFileType;
import fpt.capstone.vuondau.entity.request.CourseIdRequest;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.FileAttachmentRepository;
import fpt.capstone.vuondau.repository.SectionRepository;
import fpt.capstone.vuondau.service.IMoodleService;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import fpt.capstone.vuondau.util.RequestUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class MoodleServiceImpl implements IMoodleService {

    final private RequestUtil requestUtil;
    private final MoodleCourseRepository moodleCourseRepository;
    private final ClassRepository classRepository;
    private final SectionRepository sectionRepository;

    private final FileAttachmentRepository fileAttachmentRepository ;

    public MoodleServiceImpl(RequestUtil requestUtil, MoodleCourseRepository moodleCourseRepository, ClassRepository classRepository, SectionRepository sectionRepository, FileAttachmentRepository fileAttachmentRepository) {
        this.requestUtil = requestUtil;
        this.moodleCourseRepository = moodleCourseRepository;
        this.classRepository = classRepository;
        this.sectionRepository = sectionRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
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

    @Override
    public Boolean synchronizedClassDetail() throws JsonProcessingException {
        List<Class> classes = classRepository.findAll();


        for (Class clazz : classes) {
            CourseIdRequest courseIdRequest = new CourseIdRequest();
            courseIdRequest.setCourseid(clazz.getResourceMoodleId());
            List<MoodleSectionResponse> detailCourse = moodleCourseRepository.getResourceCourse(courseIdRequest);
            List<Section> sections = new ArrayList<>();
            for (MoodleSectionResponse moodleSectionResponse : detailCourse) {
                Section section = createSection(clazz, moodleSectionResponse);

                for (MoodleModuleResponse moodleModuleResponse : moodleSectionResponse.getModules()) {
                    Module module = createModule(section, moodleModuleResponse);
                    section.getModules().add(module);

                }
                sections.add(section);
            }

            clazz.getSections().clear();
            clazz.getSections().addAll(sections);
        }
        classRepository.saveAll(classes);
        return true;
    }

    @NotNull
    private Section createSection(Class clazz, MoodleSectionResponse moodleSectionResponse) {
        Section section = new Section();
        section.setClazz(clazz);
        section.setName(moodleSectionResponse.getName());
        if (Objects.equals(moodleSectionResponse.getName(), "General")) {
            section.setVisible(false);
        } else {
            section.setVisible(moodleSectionResponse.isUservisible());
        }
        return section;
    }

    @NotNull
    private Module createModule(Section section, MoodleModuleResponse moodleModuleResponse) {
        Module module = new Module();
        module.setName(moodleModuleResponse.getName());
        module.setType(getModuleType(moodleModuleResponse.getModname()));
        module.setSection(section);
        module.setUrl(moodleModuleResponse.getUrl());
        return module;
    }

    private EModuleType getModuleType(String modname) {
        if (modname.equals(EModuleType.QUIZ.getLabel())) {
            return EModuleType.QUIZ;
        } else if (modname.equals(EModuleType.LESSON.getLabel())) {
            return EModuleType.LESSON;
        }
        else if (modname.equals(EModuleType.ASSIGN.getLabel())) {
            return EModuleType.ASSIGN;
        }
        return null;
    }
}
