package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.Mark;
import fpt.capstone.vuondau.entity.response.MarkResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IMarkService {
    List<MarkResponse> getStudentMark(Long clazzId,Long studentId);
    void synchronizeMark();
}
