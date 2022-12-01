package fpt.capstone.vuondau.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.S1CourseRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.ResourceMoodleResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.ClassSearchRequest;
import fpt.capstone.vuondau.entity.request.CourseIdRequest;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IClassService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.specification.ClassSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClassServiceImpl implements IClassService {


    final private RequestUtil requestUtil;
    private final AccountRepository accountRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;

    private final CourseRepository courseRepository;

    private final MoodleCourseRepository moodleCourseRepository;

    private final CourseServiceImpl courseServiceImpl;
    private final StudentClassRepository studentClassRepository;
    private final MessageUtil messageUtil;

    public ClassServiceImpl(RequestUtil requestUtil, AccountRepository accountRepository, SubjectRepository subjectRepository, ClassRepository classRepository, CourseRepository courseRepository, MoodleCourseRepository moodleCourseRepository, CourseServiceImpl courseServiceImpl, StudentClassRepository studentClassRepository, MessageUtil messageUtil) {
        this.requestUtil = requestUtil;
        this.accountRepository = accountRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.courseRepository = courseRepository;
        this.moodleCourseRepository = moodleCourseRepository;
        this.courseServiceImpl = courseServiceImpl;
        this.studentClassRepository = studentClassRepository;
        this.messageUtil = messageUtil;
    }


    @Override
    public Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) throws JsonProcessingException {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay teacher" + teacherId));


        // set class bên vườn đậu
        Class clazz = new Class();
        clazz.setName(createClassRequest.getName());
        if (classRepository.existsByCode(createClassRequest.getCode())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class code da ton tai"));
        }

        clazz.setCode(createClassRequest.getCode());
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setNumberStudent(createClassRequest.getNumberStudent());
        clazz.setMaxNumberStudent(createClassRequest.getMaxNumberStudent());
        clazz.setStatus(EClassStatus.REQUESTING);
        clazz.setStartDate(createClassRequest.getStartDate());
        clazz.setEndDate(createClassRequest.getEndDate());
        clazz.setActive(false);
        clazz.setAccount(teacher);
        classRepository.save(clazz);



        return true;
    }

    @Override
    public Boolean synchronizedClassToMoodle(MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {


        S1CourseRequest s1CourseRequest = new S1CourseRequest();

        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();


        for (MoodleCourseDataRequest.MoodleCourseBody request : moodleCourseDataRequest.getCourses()) {
            MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
            moodleCourseBody.setFullname(request.getFullname());
            moodleCourseBody.setShortname(request.getShortname());
            moodleCourseBody.setCategoryid(request.getCategoryid());
            moodleCourseBodyList.add(moodleCourseBody);
        }

        s1CourseRequest.setCourses(moodleCourseBodyList);

        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);

        return true;
    }

    @Override
    public ClassDto adminApproveRequestCreateClass(Long id) throws JsonProcessingException {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + id));
        if (!aClass.getStatus().equals(EClassStatus.REQUESTING)) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class khong phai trang thai de Active"));
        }


        aClass.setActive(true);
        aClass.setStatus(EClassStatus.NOTSTART);
        Class save = classRepository.save(aClass);

        S1CourseRequest s1CourseRequest = new S1CourseRequest();
        List<MoodleCourseDataRequest.MoodleCourseBody> moodleCourseBodyList = new ArrayList<>();

        MoodleCourseDataRequest.MoodleCourseBody moodleCourseBody = new MoodleCourseDataRequest.MoodleCourseBody();
        moodleCourseBody.setFullname(save.getName());
        moodleCourseBody.setShortname(save.getCode());
        Course course = save.getCourse();
        if (course != null) {
            moodleCourseBody.setCategoryid(course.getSubject().getCategoryMoodleId());
        }
        if (save.getStartDate() != null) {
            moodleCourseBody.setStartdate(save.getStartDate().toEpochMilli());
        }
        if (save.getEndDate() != null) {
            moodleCourseBody.setEnddate(save.getEndDate().toEpochMilli());
        }
        moodleCourseBodyList.add(moodleCourseBody);
        s1CourseRequest.setCourses(moodleCourseBodyList);

        List<MoodleClassResponse> moodleClassResponses = moodleCourseRepository.postCourse(s1CourseRequest);


        ClassDto classDto = ObjectUtil.copyProperties(save, new ClassDto(), ClassDto.class);


        return classDto;
    }

    @Override
    public ApiPage<ClassDto> getClassRequesting(ClassSearchRequest query, Pageable pageable) {
        ClassSpecificationBuilder builder = ClassSpecificationBuilder.specification()
                .queryStatusClass(query.getStatus());

        Page<Class> classesPage = classRepository.findAll(builder.build(), pageable);

        return PageUtil.convert(classesPage.map(ConvertUtil::doConvertEntityToResponse));


    }

    @Override
    public Boolean studentEnrollClass(Long studentId, Long classId) {
        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay student" + studentId));

        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));


        List<StudentClass> studentClasses = student.getStudentClasses();
        studentClasses.stream().map(studentClass -> {
            if (studentClass.getaClass().equals(aClass)) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("student dang trong class nay roi"));
            }
            return studentClass;
        }).collect(Collectors.toList());


        StudentClass studentClass = new StudentClass();
        StudentClassKey key = new StudentClassKey();
        key.setClassId(aClass.getId());
        key.setStudentId(studentId);

        studentClass.setId(key);
        studentClass.setAClass(aClass);
        studentClass.setAccount(student);
        studentClass.setIs_enrolled(false);
//        Long numberStudent = aClass.getNumberStudent();
//        aClass.setNumberStudent(numberStudent  + 1);
        student.getStudentClasses().add(studentClass);
        accountRepository.save(student);


        return true;
    }

    @Override
    public List<ClassDto> studentWaitingApproveIntoClass(Long classId) {
        return null;
    }

    @Override
    public List<ClassStudentDto> getStudentWaitingIntoClass(Long classId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));
        List<StudentClass> studentClasses = aClass.getStudentClasses();

        ClassStudentDto classStudentDto = new ClassStudentDto();
        List<ClassStudentDto> classStudentDtos = new ArrayList<>();
        classStudentDto.setClassId(classId);
        List<StudentDto> studentList = new ArrayList<>();
        studentClasses.stream().map(studentClass -> {
            StudentDto studentDto = new StudentDto();
            Account account = studentClass.getAccount();
            if (account != null) {
                studentDto = ObjectUtil.copyProperties(account, new StudentDto(), StudentDto.class);
                studentDto.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
//                studentDto.setPhoneNumber(account.getPhoneNumber());
//                studentDto.setBirthday();
            }

            studentList.add(studentDto);
            return studentClass;
        }).collect(Collectors.toList());

        classStudentDto.setStudents(studentList);
        classStudentDtos.add(classStudentDto);
        return classStudentDtos;
    }

    @Override
    public List<ClassDto> searchClass(ClassSearchRequest query) {
        ClassSpecificationBuilder builder = ClassSpecificationBuilder.specification()
                .queryLike(query.getQ())
                .queryStatusClass(query.getStatus());

        List<Class> classList = classRepository.findAll(builder.build());
        List<ClassDto> classDtoList = new ArrayList<>();
        classList.stream().map(aClass -> {
            ClassDto classDto = ObjectUtil.copyProperties(aClass, new ClassDto(), ClassDto.class);
            classDtoList.add(classDto);
            return aClass;
        }).collect(Collectors.toList());
        return classDtoList;
    }



    @Override
    public ClassDetailDto classDetail(Long id) throws JsonProcessingException {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + id));
        ClassDetailDto classDetail = ObjectUtil.copyProperties(aClass, new ClassDetailDto(), ClassDetailDto.class);
        classDetail.setUnitPrice(aClass.getUnitPrice());
        classDetail.setFinalPrice(aClass.getFinalPrice());
        Course course = aClass.getCourse();
        if (course != null) {

            CourseDetailResponse courseDetailResponse = new CourseDetailResponse();


            // set course
            // set course detail

            courseDetailResponse = ObjectUtil.copyProperties(course, new CourseDetailResponse(), CourseDetailResponse.class, true);
            if (course.getResource() != null) {
                courseDetailResponse.setImage(course.getResource().getUrl());
            }
            courseDetailResponse.setActive(course.getIsActive());
            courseDetailResponse.setTitle(course.getTitle());

            // set subject
            Subject subject = course.getSubject();
            if (subject != null) {
                SubjectDto subjectDto = new SubjectDto();
                subjectDto.setId(subject.getId());
                subjectDto.setName(subject.getName());
                subjectDto.setCode(subject.getCode());
                courseDetailResponse.setSubject(subjectDto);
            }

        if (aClass.getResourceMoodleId()!=null){
            CourseIdRequest courseIdRequest = new CourseIdRequest();

            courseIdRequest.setCourseid(aClass.getResourceMoodleId());
            try {
                List<MoodleRecourseClassResponse> resourceCourse = moodleCourseRepository.getResourceCourse(courseIdRequest);

                List<MoodleRecourseClassResponse> moodleRecourseClassResponseList = new ArrayList<>();
                resourceCourse.stream().peek(moodleRecourseClassResponse -> {
                    MoodleRecourseClassResponse setResource = ObjectUtil.copyProperties(moodleRecourseClassResponse, new MoodleRecourseClassResponse(), MoodleRecourseClassResponse.class);

                    List<ResourceMoodleResponse> modules = moodleRecourseClassResponse.getModules();
                    List<ResourceMoodleResponse> resourceMoodleResponseList = new ArrayList<>();
                    modules.stream().peek(moodleResponse -> {

                        ResourceMoodleResponse resourceMoodleResponse = ObjectUtil.copyProperties(moodleResponse, new ResourceMoodleResponse(), ResourceMoodleResponse.class);

                        resourceMoodleResponseList.add(resourceMoodleResponse);
                    }).collect(Collectors.toList());

                    setResource.setModules(resourceMoodleResponseList);

                    moodleRecourseClassResponseList.add(setResource);
                }).collect(Collectors.toList());
                courseDetailResponse.setResources(moodleRecourseClassResponseList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            classDetail.setCourse(courseDetailResponse);
        }
        Account account = aClass.getAccount();
        if (account != null) {
            AccountResponse accountResponse = ObjectUtil.copyProperties(account, new AccountResponse(), AccountResponse.class);
            accountResponse.setRole(ObjectUtil.copyProperties(account.getRole(), new RoleDto(), RoleDto.class));
            Resource resource = account.getResource();
            if (resource != null) {
                accountResponse.setAvatar(resource.getUrl());
            }
            classDetail.setTeacher(accountResponse);
        }


        List<Account> studentList = aClass.getStudentClasses().stream().map(StudentClass::getAccount).collect(Collectors.toList());

        List<AccountResponse> accountResponses = new ArrayList<>();
        studentList.stream().map(studentMap -> {
            AccountResponse student = ObjectUtil.copyProperties(studentMap, new AccountResponse(), AccountResponse.class);
            student.setRole(ObjectUtil.copyProperties(studentMap.getRole(), new RoleDto(), RoleDto.class));
            if (studentMap.getRequests() != null) {
                student.setAvatar(studentMap.getResource().getUrl());
            }

            accountResponses.add(student);
            return studentMap;
        }).collect(Collectors.toList());
        classDetail.setStudents(accountResponses);

        List<TimeTableDto> timeTableDtoList = new ArrayList<>();

        List<TimeTable> timeTables = aClass.getTimeTables();
        timeTables.stream().map(timeTable -> {
            TimeTableDto timeTableDto = new TimeTableDto();
            timeTableDto.setId(timeTable.getId());
            timeTableDto.setDate(timeTable.getDate());
            timeTableDto.setSlotNumber(timeTable.getSlotNumber());
            ArchetypeTimeDto archetypeTimeDto = new ArchetypeTimeDto();
            ArchetypeTime archetypeTime = timeTable.getArchetypeTime();
            if (archetypeTime != null) {
                Archetype archetype = archetypeTime.getArchetype();
                if (archetype != null) {
                    archetypeTimeDto.setArchetype(ObjectUtil.copyProperties(archetype, new ArchetypeDto(), ArchetypeDto.class));
                }
                Slot slot = archetypeTime.getSlot();
                if (slot != null) {
                    archetypeTimeDto.setSlot(ObjectUtil.copyProperties(slot, new SlotDto(), SlotDto.class));
                }
                DayOfWeek dayOfWeek = archetypeTime.getDayOfWeek();
                if (dayOfWeek != null) {
                    archetypeTimeDto.setDayOfWeek(ObjectUtil.copyProperties(dayOfWeek, new DayOfWeekDto(), DayOfWeekDto.class));
                }
            }


            timeTableDto.setArchetypeTime(archetypeTimeDto);
            timeTableDtoList.add(timeTableDto);
            return timeTable;
        }).collect(Collectors.toList());
        classDetail.setTimeTable(timeTableDtoList);
        return classDetail;
    }



    @Override
    public ApiPage<ClassDto> getAllClass(Pageable pageable) {
        Page<Class> classesPage = classRepository.findAllByIsActiveIsTrue(pageable);

        return PageUtil.convert(classesPage.map(ConvertUtil::doConvertEntityToResponse));

    }


//    @Override
//    public  List<MoodleClassResponse>  synchronizedClass() throws JsonProcessingException {
//        MoodleMasterDataRequest s1MasterDataRequest = new MoodleMasterDataRequest() ;
//        List<MoodleClassResponse> response  = moodleCourseRepository.getCourse(s1MasterDataRequest);
//        List<MoodleClassResponse> classList = new ArrayList<>() ;
//        List<MoodleClassResponse> listClassMoodle = response.stream().map(moodleClassResponse -> {
//            MoodleClassResponse moodleClassResponse1 = new MoodleClassResponse() ;
//            moodleClassResponse1.setId(moodleClassResponse.getId());
//            classList.add(moodleClassResponse1 );
//            return moodleClassResponse1 ;
//        }).collect(Collectors.toList()) ;
//        return classList;
//    }
//


}