package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
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

import java.util.*;
import java.util.stream.Collectors;

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

    private final SecurityUtil SecurityUtil ;

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

    @Override
    public Long createTimeTableClass(Long classId, TimeTableRequest timeTableRequest) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));

        if (aClass.getTimeTables().size() > 0) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class đã có thời khoá biểu"));
        }
        if (timeTableRequest.getSlotDow().size() > 3) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Đã vượt quá số buổi trong tuần"));
        }

        if (archetypeRepository.existsByIdAndCode(aClass.getId(), timeTableRequest.getArchetypeCode())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("time table code da ton tai"));
        }


        // Check Dow
        List<Long> allDayOfWeekId = timeTableRequest.getSlotDow().stream().map(SlotDowDto::getDayOfWeekId).collect(Collectors.toList());
        Set<Long> countDow = new HashSet<>();
        Set<Long> checkDuplicatesDow = allDayOfWeekId.stream().filter(n -> !countDow.add(n)).collect(Collectors.toSet());


        // Check slot
        List<Long> allSlotId = timeTableRequest.getSlotDow().stream().map(SlotDowDto::getSlotId).collect(Collectors.toList());
        Set<Long> countSlot = new HashSet<>();
        Set<Long> checkSlotDow = allDayOfWeekId.stream().filter(n -> !countDow.add(n)).collect(Collectors.toSet());


        // Dạy 2 ngày trong 1 tuần
//
//        if (timeTableRequest.getSlotDow().size()==2){ //day 2 ngay trong tuần
//                if (checkDuplicatesDow.size()>=1) {
//                    throw ApiException.create(HttpStatus.BAD_REQUEST)
//                            .withMessage(messageUtil.getLocalMessage("Số buổi dạy trong tuần là 2. Bạn không thể dạy 2 slot trong 1 ngày được. ")) ;
//                }
//        }


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

        List<DayOfWeek> dayOfWeekList = dayOfWeekRepository.findAll();
        Map<Long, DayOfWeek> dayOfWeekMap = HashMapUtil.convertDoWListToMap(dayOfWeekList);

        List<TimeTable> timeTableList = new ArrayList<>();

        int slotNumber = 1;
        for (SlotDowDto slotDowDto : slotDows) {


            // Set  Archetype_Time
            Slot slot = slotMap.get(slotDowDto.getSlotId());
            ArchetypeTime archetypeTime = new ArchetypeTime();
            archetypeTime.setSlot(slot);

            DayOfWeek dayOfWeek = dayOfWeekMap.get(slotDowDto.getDayOfWeekId());
            archetypeTime.setDayOfWeek(dayOfWeek);
            archetypeTime.setArchetype(archetype);


            //Set TimeTable
            TimeTable timeTable = new TimeTable();

            timeTable.setSlotNumber(slotNumber);
            ++slotNumber;

            timeTable.setClazz(aClass);
            timeTable.setDate(slotDowDto.getDate());
            timeTable.setArchetypeTime(archetypeTime);
            timeTableList.add(timeTable);


        }
        aClass.getTimeTables().clear();
        aClass.getTimeTables().addAll(timeTableList);


        classRepository.save(aClass);

        return aClass.getId();
    }

    @Override
    public ApiPage<TimeTableDto> getTimeTableInDay(TimeTableSearchRequest timeTableSearchRequest , Pageable pageable) {
        Account currentUser = SecurityUtil.getCurrentUser();

        Class aClass = classRepository.findById(timeTableSearchRequest.getClassId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + timeTableSearchRequest.getClassId()));

        if (currentUser.getRole()!= null){
            if (currentUser.getRole().getCode().equals(EAccountRole.STUDENT)){
                List<StudentClass> studentClasses = aClass.getStudentClasses();
                List<Account> studentAccount = studentClasses.stream().map(StudentClass::getAccount).collect(Collectors.toList());
                if (!studentAccount.contains(currentUser)) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Bạn không có trong lớp nay"));
                }

            }
            if (currentUser.getRole().getCode().equals(EAccountRole.TEACHER)){
                Account account = aClass.getAccount();
                if (!currentUser.getId().equals(account.getId())){
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
                DayOfWeek dayOfWeek = archetypeTime.getDayOfWeek();
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
