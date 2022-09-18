package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.ClassType;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;
import fpt.capstone.vuondau.repository.ClassTypeRepository;
import fpt.capstone.vuondau.service.IClassTypeService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassTypeServiceImpl implements IClassTypeService {
    private final ClassTypeRepository classTypeRepository;

    public ClassTypeServiceImpl(ClassTypeRepository classTypeRepository) {
        this.classTypeRepository = classTypeRepository;
    }

    @Override
    public ClassTypeResponse create(ClassTypeRequest classTypeRequest) {
        ClassType classType = ObjectUtil.copyProperties(classTypeRequest, new ClassType(), ClassType.class, true);
        classType = classTypeRepository.save(classType);
        return ObjectUtil.copyProperties(classType, new ClassTypeResponse(), ClassTypeResponse.class, true);
    }

    @Override
    public ClassTypeResponse update(ClassTypeRequest classTypeRequest, Long id) {
        ClassType newClassType = ObjectUtil.copyProperties(classTypeRequest, new ClassType(), ClassType.class, true);
        ClassType oldClassType = classTypeRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Class Type Not Found!"));
        oldClassType = ObjectUtil.copyProperties(newClassType, oldClassType, ClassType.class, true);
        return ObjectUtil.copyProperties(oldClassType, new ClassTypeResponse(), ClassTypeResponse.class, true);
    }

    @Override
    public Boolean delete(Long id) {
        ClassType classType = classTypeRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Class Type Not Found!"));
        classTypeRepository.delete(classType);
        return true;
    }

    @Override
    public List<ClassTypeResponse> getAll() {
        List<ClassType> classTypes = classTypeRepository.findAll();
        List<ClassTypeResponse> classTypeResponses = classTypes.stream().map(classType -> {
            ClassTypeResponse classTypeResponse = ObjectUtil.copyProperties(classType, new ClassTypeResponse(), ClassTypeResponse.class, true);
            return classTypeResponse;
        }).collect(Collectors.toList());
        return classTypeResponses;
    }
}
