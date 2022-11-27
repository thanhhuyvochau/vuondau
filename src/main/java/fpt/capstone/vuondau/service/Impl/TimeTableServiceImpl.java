package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.ArchetypeTime;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.TimeTable;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ITimeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableServiceImpl implements ITimeTableService {

private  final ClassRepository classRepository ;

    public TimeTableServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public Long createTimeTableClass(Long classId) {
        Class aClass = classRepository.findById(classId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay class" + classId));
//        TimeTable timeTable = new TimeTable() ;
//        timeTable.setClazz(aClass);
//        timeTable.setSlotNumber();
//        ArchetypeTime archetypeTime = new ArchetypeTime() ;
//
//        timeTable.setArchetypeTime();
//        List<TimeTable> timeTableList = new ArrayList<>( );
//        aClass.setTimeTables();
        return null;
    }
}
