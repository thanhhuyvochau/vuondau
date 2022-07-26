package fpt.capstone.vuondau;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.MoodleCourseRepository;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCreateCategoryRequest;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.CategoryResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.DayOfWeek;
import fpt.capstone.vuondau.entity.common.*;
import fpt.capstone.vuondau.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.text.ParseException;
import java.util.*;
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

    private final RequestTypeRepository requestTypeRepository;

    private final MoodleCourseRepository moodleCourseRepository;

    private final SlotRepository slotRepository;

    private final DayOfWeekRepository dayOfWeekRepository;

    private final ClassRepository classRepository;

    public HatdauApplication(RoleRepository roleRepository, SubjectRepository subjectRepository, RequestTypeRepository requestTypeRepository, MoodleCourseRepository moodleCourseRepository, SlotRepository slotRepository, DayOfWeekRepository dayOfWeekRepository, ClassRepository classRepository) {
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.moodleCourseRepository = moodleCourseRepository;
        this.slotRepository = slotRepository;
        this.dayOfWeekRepository = dayOfWeekRepository;
        this.classRepository = classRepository;
    }


    public static void main(String[] args) throws ParseException {

        SpringApplication.run(HatdauApplication.class, args);
//
//        Instant start = Instant.parse("2022-12-20T04:29:08.293Z");
//        String oneSubString = start.toString().substring(0, 10);
//
//
//
//        LocalDate startDate = LocalDate.parse(oneSubString);
//        System.out.println(startDate);
//
//        LocalDate endDate = startDate.plusDays(7);
//        System.out.println(endDate);
//
//        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//        List<LocalDate> collectDay = IntStream.iterate(0, i -> i + 1)
//                .limit(numOfDaysBetween)
//                .mapToObj(startDate::plusDays)
//                .collect(Collectors.toList());
//        System.out.println(collectDay);
//
//        java.time.DayOfWeek tuesday = MONDAY;
//
//        for (LocalDate ld : collectDay) {
//                java.time.DayOfWeek dayf = ld.getDayOfWeek();
//                System.out.println(dayf);
//                if (tuesday.equals(dayf)){
//                    System.out.println("có ne");
//                }
//
//            }

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

        List<RequestType> requestTypeList = new ArrayList<>();
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
        Boolean existVatLy = false;
        Boolean existHoaHoc = false;
        Boolean existTiengAnh = false;
        Boolean existSinhHoc = false;
        Boolean existNguVan = false;
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
        List<MoodleCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>();
        MoodleCategoryRequest.MoodleCategoryBody moodleCategoryBody = new MoodleCategoryRequest.MoodleCategoryBody();
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody);
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategory(request);

        List<String> allNameCategory = category.stream().map(CategoryResponse::getName).collect(Collectors.toList());


        List<Subject> allSubject = subjectRepository.findAll();
        List<String> allNameSubject = allSubject.stream().map(subject -> subject.getCode().name()).collect(Collectors.toList());

        List<String> collect = allNameSubject.stream().filter(s -> !allNameCategory.contains(s)).filter(Objects::nonNull).collect(Collectors.toList());

        MoodleCreateCategoryRequest moodleCreateCategoryRequest = new MoodleCreateCategoryRequest();

        List<MoodleCreateCategoryRequest.MoodleCreateCategoryBody> moodleCreateCategoryBodyList = new ArrayList<>();

        for (String s : collect) {
            MoodleCreateCategoryRequest.MoodleCreateCategoryBody set = new MoodleCreateCategoryRequest.MoodleCreateCategoryBody();
            set.setName(s);
            set.setParent(0L);
            set.setIdnumber("");
            set.setDescription("");
            set.setDescriptionformat(0L);

            moodleCreateCategoryBodyList.add(set);
        }


        moodleCreateCategoryRequest.setCategories(moodleCreateCategoryBodyList);
        moodleCourseRepository.postCategory(moodleCreateCategoryRequest);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiMoodleCategoryIdToSubject() throws JsonProcessingException {
        MoodleCategoryRequest request = new MoodleCategoryRequest();
        List<MoodleCategoryRequest.MoodleCategoryBody> moodleCategoryBodyList = new ArrayList<>();
        MoodleCategoryRequest.MoodleCategoryBody moodleCategoryBody = new MoodleCategoryRequest.MoodleCategoryBody();
        moodleCategoryBody.setKey("id");
        moodleCategoryBody.setValue("");
        moodleCategoryBodyList.add(moodleCategoryBody);
        request.setCriteria(moodleCategoryBodyList);
        List<CategoryResponse> category = moodleCourseRepository.getCategory(request);

        List<String> allNameCategory = category.stream().map(CategoryResponse::getName).collect(Collectors.toList());


        List<Subject> allSubject = subjectRepository.findAll();
        List<String> allNameSubject = allSubject.stream().map(subject -> subject.getCode().name()).collect(Collectors.toList());

        List<String> collect = allNameSubject.stream().filter(allNameCategory::contains).filter(Objects::nonNull).collect(Collectors.toList());

        List<Subject> subjectList = new ArrayList<>();
        for (String s : collect) {
            ESubjectCode eSubjectCode = ESubjectCode.valueOf(s);
            Subject byCode = subjectRepository.findByCode(eSubjectCode);
            for (CategoryResponse categoryResponse : category) {
                if (categoryResponse.getName().equals(byCode.getCode().name())) {
                    byCode.setCategoryMoodleId(categoryResponse.getId());
                    subjectList.add(byCode);
                }
            }

        }
        subjectRepository.saveAll(subjectList);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void intiClassMoodleOntoToClass() throws JsonProcessingException {

        MoodleMasterDataRequest request = new MoodleMasterDataRequest();

        List<MoodleClassResponse> courseMoodle = moodleCourseRepository.getCourse(request);

        List<Class> allClass = classRepository.findAll();
        List<Class> classList = new ArrayList<>();
        courseMoodle.stream().map(moodleClassResponse -> {
            for (Class aClass : allClass) {
                if (aClass.getCode().equals(moodleClassResponse.getShortname())) {
                    aClass.setResourceMoodleId(moodleClassResponse.getId());
                    classList.add(aClass);
                }
            }
            return moodleClassResponse;
        }).collect(Collectors.toList());

        courseMoodle.forEach(moodleClassResponse -> {
            Class byCode = classRepository.findByCode(moodleClassResponse.getShortname());
            if (byCode != null) {
                byCode.setResourceMoodleId(moodleClassResponse.getId());

                classList.add(byCode);
            }
            if (byCode == null) {
//                Subject byCategoryMoodleId = subjectRepository.findByCategoryMoodleId(moodleClassResponse.getCategoryid());
                Class aClass = new Class();
                aClass.setCode(moodleClassResponse.getShortname());
                aClass.setName(moodleClassResponse.getFullname());

                aClass.setActive(true);
                classList.add(aClass);
            }
        });

        classRepository.saveAll(classList);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void intiDatSlot() {

        List<Slot> allSlot = slotRepository.findAll();
        Boolean existSlot1 = false;
        Boolean existSlot2 = false;
        Boolean existSlot3 = false;
        Boolean existSlot4 = false;
        Boolean existSlot5 = false;
        Boolean existSlot6 = false;
        Boolean existSlot7 = false;
        Boolean existSlot8 = false;
        Boolean existSlot9 = false;
        Boolean existSlot10 = false;
        for (Slot slot : allSlot) {
            if (slot.getCode().equals(ESlotCode.SLOT1)) {
                existSlot1 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT2)) {
                existSlot2 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT3)) {
                existSlot3 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT4)) {
                existSlot4 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT5)) {
                existSlot5 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT6)) {
                existSlot6 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT7)) {
                existSlot7 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT8)) {
                existSlot8 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT9)) {
                existSlot9 = true;
            }
            if (slot.getCode().equals(ESlotCode.SLOT10)) {
                existSlot10 = true;
            }
        }

        List<Slot> slotList = new ArrayList<>();
        if (!existSlot1) {
            Slot slotOne = new Slot();
            slotOne.setCode(ESlotCode.SLOT1);
            slotOne.setName("slot One");
            slotOne.setStartTime("07:00");
            slotOne.setEndTime("08:30");
            slotList.add(slotOne);
        }
        if (!existSlot2) {
            Slot slotTwo = new Slot();
            slotTwo.setCode(ESlotCode.SLOT2);
            slotTwo.setName("slot Two");
            slotTwo.setStartTime("08:30");
            slotTwo.setEndTime("10:00");
            slotList.add(slotTwo);
        }

        if (!existSlot3) {
            Slot slotThree = new Slot();
            slotThree.setCode(ESlotCode.SLOT3);
            slotThree.setName("slot Three");
            slotThree.setStartTime("10:00");
            slotThree.setEndTime("11:30");
            slotList.add(slotThree);
        }

        if (!existSlot4) {
            Slot slotFour = new Slot();
            slotFour.setCode(ESlotCode.SLOT4);
            slotFour.setName("slot Four");
            slotFour.setStartTime("11:30");
            slotFour.setEndTime("13:00");
            slotList.add(slotFour);
        }

        if (!existSlot5) {
            Slot slotFive = new Slot();
            slotFive.setCode(ESlotCode.SLOT5);
            slotFive.setName("slot Fine");
            slotFive.setStartTime("13:00");
            slotFive.setEndTime("14:30");
            slotList.add(slotFive);
        }

        if (!existSlot6) {
            Slot slotSix = new Slot();
            slotSix.setCode(ESlotCode.SLOT6);
            slotSix.setName("slot Six");
            slotSix.setStartTime("14:30");
            slotSix.setEndTime("16:00");
            slotList.add(slotSix);
        }

        if (!existSlot7) {
            Slot slotSeven = new Slot();
            slotSeven.setCode(ESlotCode.SLOT7);
            slotSeven.setName("slot Seven");
            slotSeven.setStartTime("16:00");
            slotSeven.setEndTime("17:30");
            slotList.add(slotSeven);
        }


        if (!existSlot8) {
            Slot slotEight = new Slot();
            slotEight.setCode(ESlotCode.SLOT8);
            slotEight.setName("slot Eight");
            slotEight.setStartTime("17:30");
            slotEight.setEndTime("19:00");
            slotList.add(slotEight);
        }


        if (!existSlot9) {
            Slot slotNine = new Slot();
            slotNine.setCode(ESlotCode.SLOT9);
            slotNine.setName("slot Nine");
            slotNine.setStartTime("19:00");
            slotNine.setEndTime("20:30");
            slotList.add(slotNine);
        }
        slotRepository.saveAll(slotList);


    }


    @EventListener(ApplicationReadyEvent.class)
    public void intiDataDateOfWeek() {

        List<DayOfWeek> allDayOfWeeks = dayOfWeekRepository.findAll();

        Boolean existDay2 = false;
        Boolean existDay3 = false;
        Boolean existDay4 = false;
        Boolean existDay5 = false;
        Boolean existDay6 = false;
        Boolean existDay7 = false;
        for (DayOfWeek dayOfWeek : allDayOfWeeks) {
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.MONDAY)) {
                existDay2 = true;
            }
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.TUESDAY)) {
                existDay3 = true;
            }
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.WEDNESDAY)) {
                existDay4 = true;
            }
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.THURSDAY)) {
                existDay5 = true;
            }
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.FRIDAY)) {
                existDay6 = true;
            }
            if (dayOfWeek.getCode().equals(EDayOfWeekCode.SATURDAY)) {
                existDay7 = true;
            }


        }

        List<DayOfWeek> dayOfWeekList = new ArrayList<>();
        if (!existDay2) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.MONDAY);
            dayOfWeek.setName("Thứ hai");
            dayOfWeekList.add(dayOfWeek);
        }
        if (!existDay3) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.TUESDAY);
            dayOfWeek.setName("Thứ Ba");
            dayOfWeekList.add(dayOfWeek);
        }

        if (!existDay4) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.WEDNESDAY);
            dayOfWeek.setName("Thứ Tư");
            dayOfWeekList.add(dayOfWeek);
        }

        if (!existDay5) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.THURSDAY);
            dayOfWeek.setName("Thứ Năm");
            dayOfWeekList.add(dayOfWeek);
        }

        if (!existDay6) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.FRIDAY);
            dayOfWeek.setName("Thứ Sáu");
            dayOfWeekList.add(dayOfWeek);
        }

        if (!existDay7) {
            DayOfWeek dayOfWeek = new DayOfWeek();
            dayOfWeek.setCode(EDayOfWeekCode.SATURDAY);
            dayOfWeek.setName("Thứ Bảy");
            dayOfWeekList.add(dayOfWeek);
        }

        dayOfWeekRepository.saveAll(dayOfWeekList);


    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void intiDataClasType() {
//
//        List<ClassType> all = classTypeRepository.findAll();
//        Boolean existClass10 = false;
//        Boolean existClass11 = false;
//        Boolean existClass12 = false;
//
//        for (ClassType classType : all) {
//            if (classType.getCode().equals(EClassLevel.TEN)) {
//                existClass10 = true;
//            }
//            if (classType.getCode().equals(EClassLevel.ELEVENT)) {
//                existClass11 = true;
//            }
//            if (classType.getCode().equals(EClassLevel.TWELFTH)) {
//                existClass12 = true;
//            }
//        }
//
//        List<ClassType> classTypeList = new ArrayList<>();
//        if (!existClass10) {
//            ClassType classType = new ClassType();
//            classType.setCode(EClassLevel.TEN);
//            classType.setName("Lớp 10");
//
//            classTypeList.add(classType);
//        }
//        if (!existClass11) {
//
//            ClassType classType = new ClassType();
//            classType.setCode(EClassLevel.ELEVENT);
//            classType.setName("Lớp 11");
//
//            classTypeList.add(classType);
//
//        }
//
//        if (!existClass12) {
//            ClassType classType = new ClassType();
//            classType.setCode(EClassLevel.TWELFTH);
//            classType.setName("Lớp 12");
//
//            classTypeList.add(classType);
//        }
//
//        classTypeRepository.saveAll(classTypeList);
//
//
//    }

}