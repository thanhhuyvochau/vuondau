package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.StudentRepository;
import fpt.capstone.vuondau.service.IStudentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements IStudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentResponse> getAll() {
        return null;
    }

    @Override
    public StudentResponse searchByName(String name) {
        return null;
    }

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        return null;
    }

    @Override
    public StudentResponse update(StudentRequest studentRequest, Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

}
