package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.DegreeRequest;
import fpt.capstone.vuondau.entity.request.GradeRequest;
import fpt.capstone.vuondau.entity.response.DegreeResponse;
import fpt.capstone.vuondau.entity.response.GradeResponse;

public interface IDegreeService {
    DegreeResponse createNewDegree(DegreeRequest degreeRequest);
    DegreeResponse updateDegree(Long degreeId, DegreeRequest degreeRequest);

    Long deleteDegree(Long degreeId);

    DegreeResponse getDegree(Long degreeId);
}
