package fpt.capstone.vuondau;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.RequestType;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.ESubjectCode;
import fpt.capstone.vuondau.repository.RequestTypeRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static fpt.capstone.vuondau.entity.common.EAccountRole.STUDENT;
import static fpt.capstone.vuondau.entity.common.EAccountRole.TEACHER;
import static fpt.capstone.vuondau.entity.common.EResourceType.AVATAR;
import static fpt.capstone.vuondau.entity.common.EResourceType.FILE;
import static fpt.capstone.vuondau.entity.common.ESubjectCode.*;


@SpringBootApplication
public class HatdauApplication {

    private final RoleRepository roleRepository;



    private final SubjectRepository subjectRepository;

    private final RequestTypeRepository requestTypeRepository ;

    private final MoodleCourseRepository moodleCourseRepository;

    public HatdauApplication(RoleRepository roleRepository, SubjectRepository subjectRepository, RequestTypeRepository requestTypeRepository, MoodleCourseRepository moodleCourseRepository) {
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.moodleCourseRepository = moodleCourseRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(HatdauApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiDataRole() {

        List<Role> allRole = roleRepository.findAll();
        Boolean existTeacherRole = false;
        Boolean existStudentRole = false;
        for (Role role : allRole) {
            if (role.getCode().equals(TEACHER)) {
                existTeacherRole = true;
            }
            if (role.getCode().equals(STUDENT)) {
                existStudentRole = true;
            }
        }

        List<Role> roleList = new ArrayList<>();
        if (!existTeacherRole) {
            Role teacherRole = new Role();
            teacherRole.setCode(EAccountRole.TEACHER);
            teacherRole.setName("teacher");
            roleList.add(teacherRole);
        }
        if (!existStudentRole) {
            Role roleStudent = new Role();
            roleStudent.setCode(EAccountRole.STUDENT);
            roleStudent.setName("student");
            roleList.add(roleStudent);
        }
        roleRepository.saveAll(roleList);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiRequestType() {

        List<RequestType> allRequestType = requestTypeRepository.findAll();

        Boolean existTypeAvatar = false;
        Boolean existTypeFile = false;
        for (RequestType requestType : allRequestType) {
            if (requestType.getCode().equals(AVATAR)) {
                existTypeAvatar = true;
            }
            if (requestType.getCode().equals(FILE)) {
                existTypeFile = true;
            }
        }

        List<RequestType> requestTypeList= new ArrayList<>();
        if (!existTypeAvatar) {
            RequestType requestType = new RequestType();
            requestType.setCode(AVATAR);
            requestType.setName("avatar");

            requestTypeList.add(requestType);
        }
        if (!existTypeFile) {
            RequestType requestType = new RequestType();
            requestType.setCode(FILE);
            requestType.setName("file");

            requestTypeList.add(requestType);
        }
        requestTypeRepository.saveAll(requestTypeList);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiDataSubject() throws JsonProcessingException {
        List<Subject> allSubject = subjectRepository.findAll();
        Boolean existToan = false;
        Boolean existVatLy= false;
        Boolean existHoaHoc= false;
        Boolean existTiengAnh= false;
        Boolean existSinhHoc= false;
        Boolean existNguVan= false;
        Boolean existTinHoc = false;



        for (Subject subject : allSubject) {
            if (subject.getCode().equals(Toan)) {
                existToan = true;
            }
            if (subject.getCode().equals(VatLy)) {
                existVatLy = true;
            }
            if (subject.getCode().equals(HoaHoc)) {
                existHoaHoc = true;
            }
            if (subject.getCode().equals(TiengAnh)) {
                existTiengAnh = true;
            }
            if (subject.getCode().equals(SinhHoc)) {
                existSinhHoc = true;
            }
            if (subject.getCode().equals(NguVan)) {
                existNguVan = true;
            }
            if (subject.getCode().equals(TinHoc)) {
                existTinHoc = true;
            }
        }
        List<Subject> subjectList = new ArrayList<>();
        if (!existToan) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.Toan);
            subject.setName(Toan.label);
            subjectList.add(subject);
        }
        if (!existVatLy) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.VatLy);
            subject.setName(VatLy.label);
            subjectList.add(subject);
        }
        if (!existHoaHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.HoaHoc);
            subject.setName(HoaHoc.label);
            subjectList.add(subject);
        }
        if (!existTiengAnh) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.TiengAnh);
            subject.setName(TiengAnh.label);
            subjectList.add(subject);
        }
        if (!existSinhHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.SinhHoc);
            subject.setName(SinhHoc.label);
            subjectList.add(subject);
        }
        if (!existNguVan) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.NguVan);
            subject.setName(NguVan.label);
            subjectList.add(subject);
        }
        if (!existTinHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.TinHoc);
            subject.setName(TinHoc.label);
            subjectList.add(subject);
        }



        subjectRepository.saveAll(subjectList);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiSubjectToMoodle() throws JsonProcessingException {

        MoodleCategoryRequest request = new MoodleCategoryRequest();
        List< MoodleCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>() ;
        MoodleCategoryRequest.MoodleCategoryBody moodleCategoryBody = new MoodleCategoryRequest.MoodleCategoryBody() ;
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody) ;
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategory(request);

        List<String> allNameCategory = category.stream().map(CategoryResponse::getName).collect(Collectors.toList());


        List<Subject> allSubject = subjectRepository.findAll();
        List<String> allNameSubject = allSubject.stream().map(subject -> subject.getCode().name()).collect(Collectors.toList());

        List<String> collect = allNameSubject.stream().filter(s -> !allNameCategory.contains(s)).filter(Objects::nonNull).collect(Collectors.toList());

        MoodleCreateCategoryRequest moodleCreateCategoryRequest = new MoodleCreateCategoryRequest() ;

        List<MoodleCreateCategoryRequest.MoodleCreateCategoryBody> moodleCreateCategoryBodyList = new ArrayList<>() ;

        for (String s : collect) {
            MoodleCreateCategoryRequest.MoodleCreateCategoryBody set = new MoodleCreateCategoryRequest.MoodleCreateCategoryBody();
            set.setName(s);
            set.setParent(0L);
            set.setIdnumber("");
            set.setDescription("");
            set.setDescriptionformat(0L);

            moodleCreateCategoryBodyList.add(set) ;
        }



        moodleCreateCategoryRequest.setCategories(moodleCreateCategoryBodyList) ;
        moodleCourseRepository.postCategory(moodleCreateCategoryRequest);

    }
    @EventListener(ApplicationReadyEvent.class)
    public void intiMoodleCategoryIdToSubject() throws JsonProcessingException {
        MoodleCategoryRequest request = new MoodleCategoryRequest();
        List< MoodleCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>() ;
        MoodleCategoryRequest.MoodleCategoryBody moodleCategoryBody = new MoodleCategoryRequest.MoodleCategoryBody() ;
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody) ;
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategory(request);

        List<String> allNameCategory = category.stream().map(CategoryResponse::getName).collect(Collectors.toList());


        List<Subject> allSubject = subjectRepository.findAll();
        List<String> allNameSubject = allSubject.stream().map(subject -> subject.getCode().name()).collect(Collectors.toList());

        List<String> collect = allNameSubject.stream().filter(allNameCategory::contains).filter(Objects::nonNull).collect(Collectors.toList());

        List<Subject>subjectList = new ArrayList<>() ;
        for (String s : collect) {
            ESubjectCode eSubjectCode = ESubjectCode.valueOf(s) ;
            Subject byCode = subjectRepository.findByCode(eSubjectCode);
            for (CategoryResponse categoryResponse : category){
                if (categoryResponse.getName().equals(byCode.getCode().name())){
                    byCode.setCategoryMoodleId(categoryResponse.getId());
                    subjectList.add(byCode) ;
                }
            }

        }
        subjectRepository.saveAll(subjectList) ;
    }

}