package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;

import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.TimeTableRequest;
import fpt.capstone.vuondau.entity.request.TimeTableSearchRequest;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ITimeTableService;
import fpt.capstone.vuondau.util.*;
import fpt.capstone.vuondau.util.specification.TimeTableSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TimeTableServiceImpl implements ITimeTableService {

    private final ClassRepository classRepository;

    private final SlotRepository slotRepository;

    private final DayOfWeekRepository dayOfWeekRepository;

    private final ArchetypeRepository archetypeRepository;

    private final ArchetypeTimeRepository archetypeTimeRepository;

    private final MessageUtil messageUtil;

    private final TimeTableRepository timeTableRepository;

    private final AccountRepository accountRepository;

    private final SecurityUtil SecurityUtil;

    public TimeTableServiceImpl(ClassRepository classRepository, SlotRepository slotRepository, DayOfWeekRepository dayOfWeekRepository, ArchetypeRepository archetypeRepository, ArchetypeTimeRepository archetypeTimeRepository, MessageUtil messageUtil, TimeTableRepository timeTableRepository, AccountRepository accountRepository, fpt.capstone.vuondau.util.SecurityUtil securityUtil) {
        this.classRepository = classRepository;
        this.slotRepository = slotRepository;
        this.dayOfWeekRepository = dayOfWeekRepository;
        this.archetypeRepository = archetypeRepository;
        this.archetypeTimeRepository = archetypeTimeRepository;
        this.messageUtil = messageUtil;
        this.timeTableRepository = timeTableRepository;
        this.accountRepository = accountRepository;
        SecurityUtil = securityUtil;
    }




    public static Boolean getDatesBetweenUsingJava8(String startDate, java.time.DayOfWeek dow ) throws ParseException {
        Instant start = Instant.parse(startDate);
        String oneSubString = start.toString().substring(0, 10);
        LocalDate startLocalDate = LocalDate.parse(oneSubString);
        LocalDate endDate = startLocalDate.plusDays(7);

        // lấy tất cả ngày / thứ trong 1 tuần : tính từ ngày bắt dầu

        long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endDate);
        List<LocalDate> collectDay = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startLocalDate::plusDays)
                .collect(Collectors.toList());

        for (LocalDate ld : collectDay) {
            java.time.DayOfWeek dayf = ld.getDayOfWeek();
            System.out.println(dayf);
            if (dow.equals(dayf)) {
                return true;
            } else {
                return false;
            }
        }
        return true;

    }


    @Override
    public Long createTimeTableClass(Long classId, Long numberSlot, TimeTableRequest timeTableRequest) throws ParseException {
        Account currentUser = SecurityUtil.getCurrentUser();

        Class aClass = classRepository.findByIdAndAccount(classId, currentUser);
        if (aClass == null) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Class không tồn tai"));
        }
        if (aClass.getTimeTables().size() > 0) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class đã có thời khoá biểu"));
        }
        if (timeTableRequest.getSlotDow().size() > 3 || timeTableRequest.getSlotDow().size() < 2) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Vui lòng kiểm tra lại số buổi trong tuần "));
        }

        if (archetypeRepository.existsByIdAndCode(aClass.getId(), timeTableRequest.getArchetypeCode())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("time table code da ton tai"));
        }
        if (timeTableRequest.getSlotDow().size() != numberSlot) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Thêm số buổi dạy trong tuần đang lớn / nhỏ hơn với số buổi bạn đã chọn "));
        }

        //Set Archetype
        Archetype archetype = new Archetype();
        archetype.setCode(timeTableRequest.getArchetypeCode());
        archetype.setName(timeTableRequest.getArchetypeName());
        if (aClass.getAccount() != null) {
            archetype.setCreatedByTeacherId(aClass.getAccount().getId());
        }


        List<SlotDowDto> slotDows = timeTableRequest.getSlotDow();


        List<Slot> slotList = slotRepository.findAll();
        Map<Long, Slot> slotMap = HashMapUtil.convertSlotListToMap(slotList);

        List<fpt.capstone.vuondau.entity.DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        Map<Long, fpt.capstone.vuondau.entity.DayOfWeek> dayOfWeekMap = HashMapUtil.convertDoWListToMap(dayOfWeekList);

        List<TimeTable> timeTableList = new ArrayList<>();

        int slotNumber = 1;
        Instant startDate = aClass.getStartDate();

        Instant endDate = aClass.getEndDate();


        for (SlotDowDto slotDowDto : slotDows) {
            fpt.capstone.vuondau.entity.DayOfWeek dayOfWeek = dayOfWeekMap.get(slotDowDto.getDayOfWeekId());

            Slot slot = null;
            if (!getDatesBetweenUsingJava8(startDate.toString(), DayOfWeek.valueOf(dayOfWeek.getCode().name()))){
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage(messageUtil.getLocalMessage("Khong tim thấy thứ trong tuần"));
            }

            // Set  Archetype_Time
            {
                slot = slotMap.get(slotDowDto.getSlotId());
            }
            ArchetypeTime archetypeTime = new ArchetypeTime();
            archetypeTime.setSlot(slot);


            archetypeTime.setDayOfWeek(dayOfWeek);
            archetypeTime.setArchetype(archetype);


            //Set TimeTable
            TimeTable timeTable = new TimeTable();

            timeTable.setSlotNumber(slotNumber);
            ++slotNumber;

            timeTable.setClazz(aClass);
            timeTable.setArchetypeTime(archetypeTime);
            timeTableList.add(timeTable);


        }
        aClass.getTimeTables().clear();
        aClass.getTimeTables().addAll(timeTableList);
        aClass.setStatus(EClassStatus.REQUESTING);

        classRepository.save(aClass);

        return aClass.getId();
    }

    @Override
    public ApiPage<TimeTableDto> getTimeTableInDay(TimeTableSearchRequest timeTableSearchRequest, Pageable pageable) {
        Account currentUser = SecurityUtil.getCurrentUser();

        Class aClass = classRepository.findById(timeTableSearchRequest.getClassId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + timeTableSearchRequest.getClassId()));

        if (currentUser.getRole() != null) {
            if (currentUser.getRole().getCode().equals(EAccountRole.STUDENT)) {
                List<StudentClass> studentClasses = aClass.getStudentClasses();
                List<Account> studentAccount = studentClasses.stream().map(StudentClass::getAccount).collect(Collectors.toList());
                if (!studentAccount.contains(currentUser)) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Bạn không có trong lớp nay"));
                }

            }
            if (currentUser.getRole().getCode().equals(EAccountRole.TEACHER)) {
                Account account = aClass.getAccount();
                if (!currentUser.getId().equals(account.getId())) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Bạn không có dạy lớp nay"));
                }
            }
        }


        TimeTableSpecificationBuilder timeTableSpecificationBuilder = TimeTableSpecificationBuilder.specification()
                .queryTeacherInClass(currentUser)
                .queryClass(aClass)
                .date(timeTableSearchRequest.getDateFrom(), timeTableSearchRequest.getDateTo());

        List<TimeTable> timeTableInDay = timeTableRepository.findAll(timeTableSpecificationBuilder.build());
        List<TimeTableDto> timeTableDtoList = new ArrayList<>();

        timeTableInDay.forEach(timeTable -> {
            TimeTableDto timeTableDto = ObjectUtil.copyProperties(timeTable, new TimeTableDto(), TimeTableDto.class);
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
                fpt.capstone.vuondau.entity.DayOfWeek dayOfWeek = archetypeTime.getDayOfWeek();
                if (dayOfWeek != null) {
                    archetypeTimeDto.setDayOfWeek(ObjectUtil.copyProperties(dayOfWeek, new DayOfWeekDto(), DayOfWeekDto.class));
                }
            }


            timeTableDto.setArchetypeTime(archetypeTimeDto);
            timeTableDtoList.add(timeTableDto);
        });
        Page<TimeTableDto> page = new PageImpl<>(timeTableDtoList, pageable, timeTableDtoList.size());

        return PageUtil.convert(page);
    }
}
