package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.DayOfWeek;
import fpt.capstone.vuondau.entity.dto.DayOfWeekDto;
import fpt.capstone.vuondau.repository.DayOfWeekRepository;
import fpt.capstone.vuondau.service.IDayOfWeekService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DayOfWeekServiceImpl implements IDayOfWeekService {

    private final DayOfWeekRepository dayOfWeekRepository ;

    public DayOfWeekServiceImpl(DayOfWeekRepository dayOfWeekRepository) {
        this.dayOfWeekRepository = dayOfWeekRepository;
    }

    @Override
    public List<DayOfWeekDto> getAllDayOfWeek() {
        List<DayOfWeek> allDayOfWeek = dayOfWeekRepository.findAll();
        List<DayOfWeekDto> dayOfWeekDtoList = new ArrayList<>( );
        allDayOfWeek.stream().map(dayOfWeek -> {
                dayOfWeekDtoList.add(ObjectUtil.copyProperties(dayOfWeek , new DayOfWeekDto() , DayOfWeekDto.class)) ;
            return dayOfWeek ;
        }).collect(Collectors.toList()) ;
        return dayOfWeekDtoList;
    }
}
