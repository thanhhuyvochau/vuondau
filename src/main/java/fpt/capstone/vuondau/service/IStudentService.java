package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Student;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;

import java.util.List;

public interface IStudentService {
    List<StudentResponse> getAll();

    List<StudentResponse> searchByName(String name);

    StudentResponse create(StudentRequest studentRequest);

    StudentResponse update(StudentRequest studentRequest, Long id);

    Boolean delete(Long id);
}
