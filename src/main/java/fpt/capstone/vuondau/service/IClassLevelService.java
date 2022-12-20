package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.request.ClassLevelRequest;
import fpt.capstone.vuondau.entity.response.ClassLevelResponse;

import java.util.List;

public interface IClassLevelService {
    ClassLevelResponse create(ClassLevelRequest classLevelRequest);

    ClassLevelResponse update(ClassLevelRequest classLevelRequest, Long id);

    Boolean delete(Long id);

    List<ClassLevelResponse> getAll();
}
