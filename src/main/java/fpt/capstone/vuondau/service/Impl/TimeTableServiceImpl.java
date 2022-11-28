package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.SlotDowDto;
import fpt.capstone.vuondau.entity.request.TimeTableRequest;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ITimeTableService;
import fpt.capstone.vuondau.util.HashMapUtil;
import fpt.capstone.vuondau.util.MessageUtil;
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

    private final TimeTableRepository timeTableRepository ;

    public TimeTableServiceImpl(ClassRepository classRepository, SlotRepository slotRepository, DayOfWeekRepository dayOfWeekRepository, ArchetypeRepository archetypeRepository, ArchetypeTimeRepository archetypeTimeRepository, MessageUtil messageUtil, TimeTableRepository timeTableRepository) {
        this.classRepository = classRepository;
        this.slotRepository = slotRepository;
        this.dayOfWeekRepository = dayOfWeekRepository;
        this.archetypeRepository = archetypeRepository;
        this.archetypeTimeRepository = archetypeTimeRepository;
        this.messageUtil = messageUtil;
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    public Long createTimeTableClass(Long classId, TimeTableRequest timeTableRequest) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));

        if (aClass.getTimeTables().size() > 0) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("class đã có thời khoá biểu"));
        }
        if (timeTableRequest.getSlotDow().size()> 3){
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Đã vượt quá số buổi trong tuần"));
        }

        if (archetypeRepository.existsByIdAndCode(aClass.getId(),timeTableRequest.getArchetypeCode())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("time table code da ton tai"));
        }



        List<Long> allDayOfWeekDuplicate = new ArrayList<>();

        // Check Dow
        List<Long> allDayOfWeekId = timeTableRequest.getSlotDow().stream().map(SlotDowDto::getDayOfWeekId).collect(Collectors.toList());
        Set<Long> countDow = new HashSet<>();
        Set<Long> checkDuplicatesDow = allDayOfWeekId.stream().filter(n -> !countDow.add(n)).collect(Collectors.toSet());


        // Check slot
        List<Long> allSlotId = timeTableRequest.getSlotDow().stream().map(SlotDowDto::getSlotId).collect(Collectors.toList());
        Set<Long> countSlot = new HashSet<>();
        Set<Long> checkSlotDow = allDayOfWeekId.stream().filter(n -> !countDow.add(n)).collect(Collectors.toSet());




        if (timeTableRequest.getSlotDow().size()==2){ //day 2 ngay trong tuần
                if (checkDuplicatesDow.size()>=1) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST)
                            .withMessage(messageUtil.getLocalMessage("Số buổi dạy trong tuần là 2. Bạn không thể dạy 2 slot trong 1 ngày được. ")) ;
                }
        }



//        Set<Long> duplicatesDayOfWeek = collect.stream().filter(n-> !allDayOfWeekDuplicate.add(n)).collect(Collectors.toSet()) ;
        System.out.println(allDayOfWeekDuplicate);


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
        aClass.getTimeTables().addAll(timeTableList) ;
//        aClass.setTimeTables(timeTableList);




        classRepository.save(aClass);

        return aClass.getId();
    }
}
