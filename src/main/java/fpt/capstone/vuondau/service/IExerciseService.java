package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ClassExerciseDto;
import org.springframework.data.domain.Pageable;


public interface IExerciseService {


    ApiPage<ClassExerciseDto> getExerciseOfLClass(Pageable pageable);
}
