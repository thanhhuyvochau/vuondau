package fpt.capstone.vuondau.repository;

import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findAllByStudentClassKeyId(StudentClassKey studentClassKey);

    List<Attendance> findAllByStudentClassKeyIdIn(List<StudentClassKey> studentClassKeys);
//    List<Attendance> findAllByStudentClassKeyIdIn(List<StudentClassKey> studentClassKeys ) ;

    List<Attendance> findAllByTimeTableIn(List<TimeTable> timeTables);

}
