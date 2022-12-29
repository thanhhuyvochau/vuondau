package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.request.GetCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.request.CreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.response.CourseResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleModuleResponse;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.MoodleRepository.request.GetCourseRequest;
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
        GetCategoryRequest request = new GetCategoryRequest();
        List<GetCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>();
        GetCategoryRequest.MoodleCategoryBody moodleCategoryBody = new GetCategoryRequest.MoodleCategoryBody();
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody);
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategories(request);
        return category;
    }

    @Override
    public Boolean crateCategoryToMoodle(CreateCategoryRequest.CreateCategoryBody createCategoryBody) throws JsonProcessingException {
        CreateCategoryRequest request = new CreateCategoryRequest();

        List<CreateCategoryRequest.CreateCategoryBody> createCategoryBodyList = new ArrayList<>();
        CreateCategoryRequest.CreateCategoryBody set = new CreateCategoryRequest.CreateCategoryBody();
        set.setName(createCategoryBody.getName());
        set.setParent(createCategoryBody.getParent());
        set.setIdnumber(createCategoryBody.getIdnumber());
        set.setDescription(createCategoryBody.getDescription());
        set.setDescriptionformat(createCategoryBody.getDescriptionformat());
//        set.setTheme(moodleCreateCategoryBody.getTheme());
        createCategoryBodyList.add(set);

        request.setCategories(createCategoryBodyList);

        moodleCourseRepository.createCategory(request);
        return true;
    }

    @Override
    public ApiPage<CourseResponse> synchronizedClass() throws JsonProcessingException {
        MoodleMasterDataRequest request = new MoodleMasterDataRequest();
        List<CourseResponse> course = moodleCourseRepository.getCourses(request);
        Page<CourseResponse> page = new PageImpl<>(course);
        return PageUtil.convert(page.map(moodleClassResponse -> ObjectUtil.copyProperties(moodleClassResponse, new CourseResponse(), CourseResponse.class)));
    }

    @Override
    public Boolean synchronizedClassDetail() throws JsonProcessingException {
        List<Class> classes = classRepository.findAll();


        for (Class clazz : classes) {
            GetCourseRequest getCourseRequest = new GetCourseRequest();
            getCourseRequest.setCourseid(clazz.getResourceMoodleId());
            List<MoodleSectionResponse> detailCourse = moodleCourseRepository.getResourceCourse(getCourseRequest);
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
