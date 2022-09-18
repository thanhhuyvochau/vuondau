package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;

import java.util.List;

public interface IClassTypeService {
    ClassTypeResponse create(ClassTypeRequest classTypeRequest);

    ClassTypeResponse update(ClassTypeRequest classTypeRequest, Long id);

    Boolean delete(Long id);

    List<ClassTypeResponse> getAll();
}
