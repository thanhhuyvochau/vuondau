package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Archetype;
import fpt.capstone.vuondau.entity.ArchetypeTime;
import fpt.capstone.vuondau.entity.TimeTable;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ArchetypeRepository;
import fpt.capstone.vuondau.service.IArchetypeService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArchetypeServiceImpl implements IArchetypeService {

    private final AccountRepository accountRepository;

    private final ArchetypeRepository archetypeRepository;

    public ArchetypeServiceImpl(AccountRepository accountRepository, ArchetypeRepository archetypeRepository) {
        this.accountRepository = accountRepository;
        this.archetypeRepository = archetypeRepository;
    }


    @Override
    public List<ArchetypeTeacherDto> getArchetypeOfTeacher(long teacherId) {
        Account teacher = accountRepository.findById(teacherId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay account" + teacherId));

        List<Archetype> archetypeOfTeacher = archetypeRepository.findByCreatedByTeacherId(teacherId);

        List<ArchetypeTeacherDto> archetypeTeacherDtoList = new ArrayList<>();


        archetypeOfTeacher.stream().map(archetype -> {

            List<ArchetypeTimeTeacherDto> archetypeTimeTeacherDtoArrayList = new ArrayList<>();

            ArchetypeTeacherDto archetypeTeacherDto = new ArchetypeTeacherDto() ;

            //set archetype
            archetypeTeacherDto.setId(archetype.getId());
            archetypeTeacherDto.setCode(archetype.getCode());
            archetypeTeacherDto.setName(archetype.getName());


            List<ArchetypeTime> archetypeTimes = archetype.getArchetypeTimes();

            archetypeTimes.stream().map(archetypeTime -> {

                ArchetypeTimeTeacherDto archetypeTimeTeacherDto = new ArchetypeTimeTeacherDto();
                archetypeTimeTeacherDto.setId(archetypeTime.getId());
                archetypeTimeTeacherDto.setDayOfWeek(ObjectUtil.copyProperties(archetypeTime.getDayOfWeek(), new DayOfWeekDto(), DayOfWeekDto.class));
                archetypeTimeTeacherDto.setSlot(ObjectUtil.copyProperties(archetypeTime.getSlot(), new SlotDto(), SlotDto.class));

                List<TimeTable> timeTables = archetypeTime.getTimeTables();

                timeTables.stream().map(timeTable -> {
                    TimeTableDto timeTableDto = ObjectUtil.copyProperties(timeTable, new TimeTableDto(), TimeTableDto.class);
                    archetypeTimeTeacherDto.setTimeTable(timeTableDto);
                    return timeTable;
                }).collect(Collectors.toList());


                archetypeTimeTeacherDtoArrayList.add(archetypeTimeTeacherDto) ;

                return archetypeTime;

            }).collect(Collectors.toList());
            archetypeTeacherDto.setArchetypeTime(archetypeTimeTeacherDtoArrayList);
            archetypeTeacherDtoList.add(archetypeTeacherDto);
            return archetype;
        }).collect(Collectors.toList());


        return archetypeTeacherDtoList;
    }
}
