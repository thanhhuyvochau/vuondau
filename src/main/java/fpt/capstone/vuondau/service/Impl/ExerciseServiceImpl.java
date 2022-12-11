package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.EModuleType;
import fpt.capstone.vuondau.entity.Module;
import fpt.capstone.vuondau.entity.Section;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EFileType;
import fpt.capstone.vuondau.entity.dto.ClassExerciseDto;
import fpt.capstone.vuondau.entity.dto.ExerciseDto;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.IExerciseService;
import fpt.capstone.vuondau.util.PageUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements IExerciseService {

    private final  ModuleRepository moduleRepository ;

    public ExerciseServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public ApiPage<ClassExerciseDto> getExerciseOfLClass(Pageable pageable) {
        List<Module> exercises  = moduleRepository.findAllByType(EModuleType.ASSIGN);
        List<ClassExerciseDto>classExerciseList = new ArrayList<>();
        ClassExerciseDto classExerciseDto = new ClassExerciseDto() ;
        exercises.forEach(module -> {
            List<ExerciseDto>exerciseList = new ArrayList<>() ;
            Section section = module.getSection();


            if (section.getClazz()!= null) {
                Class clazz = section.getClazz();
                classExerciseDto.setId( clazz.getId());
                classExerciseDto.setName(clazz.getName());
                classExerciseDto.setCode(clazz.getCode());
                ExerciseDto exerciseDto = new ExerciseDto() ;
                exerciseDto.setId(module.getId());
                exerciseDto.setName(module.getName());
                exerciseDto.setUrl(module.getName());
                exerciseDto.setFileType(EFileType.CREATE);
                exerciseList.add(exerciseDto) ;
            }

            classExerciseDto.setExercises(exerciseList);
            classExerciseList.add(classExerciseDto) ;
        });

        Page<ClassExerciseDto> page = new PageImpl<>(classExerciseList, pageable, classExerciseList.size());
        return PageUtil.convert(page);
    }
}
