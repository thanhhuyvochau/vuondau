package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.GradeRequest;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.GradeResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

public interface IGradeService {

    GradeResponse createNewGrade(GradeRequest gradeRequest);
    GradeResponse updateGrade(Long gradeId, GradeRequest gradeRequest);

    Long deleteGrade(Long gradeId);

    GradeResponse getGrade(Long gradeId);
}
