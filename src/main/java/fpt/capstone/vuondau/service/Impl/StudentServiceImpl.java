package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.Student;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.repository.StudentRepository;
import fpt.capstone.vuondau.service.IStudentService;
import fpt.capstone.vuondau.util.Constants;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;


    public StudentServiceImpl(StudentRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<StudentResponse> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::convertStudentToStudentResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getAllByActive(Boolean isActive) {
        List<Student> students = studentRepository.findAllByAccount_IsActive(isActive);
        return students.stream().map(this::convertStudentToStudentResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> searchByName(String name) {
        List<Student> students = studentRepository.findAllByFirstNameLikeOrLastNameLike(name);
        return students.stream().map(this::convertStudentToStudentResponse).collect(Collectors.toList());
    }

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        return null;
    }

//    @Override
//    public StudentResponse create(StudentRequest studentRequest) {
//        AccountRequest accountRequest = Optional.ofNullable(studentRequest.getAccount()).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("There no account in request!"));
//        Account account = accountRepository.findByUsername(accountRequest.getUsername()).orElse(null);
//        if (account != null) {
//            throw ApiException.create(HttpStatus.CONFLICT).withMessage("Username already exist, try another username");
//        }
//        Role role = roleRepository.findRoleByCode(Constants.DefaultData.STUDENT_ROLE_CODE);
//        account = ObjectUtil.copyProperties(accountRequest, new Account(), Account.class, true);
//        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//        account.setActive(true);
//        account.setRole(role);
//
//        Student student = ObjectUtil.copyProperties(studentRequest, new Student(), Student.class, true);
//        student.setAccount(account);
//
//        studentRepository.save(student);
//        return convertStudentToStudentResponse(student);
//    }

    @Override
    public StudentResponse update(StudentRequest studentRequest, Long id) {
        String firstName = studentRequest.getFirstName().trim();
        String lastName = studentRequest.getLastName().trim();
        String email = studentRequest.getEmail().trim();
        String phoneNumber = studentRequest.getPhoneNumber().trim();
        Student student = studentRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Not found student with id:" + id));
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        return convertStudentToStudentResponse(student);
    }

    @Override
    public Boolean delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Not found student with id:" + id));
        student.getAccount().setActive(false);
        return true;
    }

    private StudentResponse convertStudentToStudentResponse(Student student) {
        StudentResponse studentResponse = ObjectUtil.copyProperties(student, new StudentResponse(), StudentResponse.class, true);
        AccountResponse accountResponse = ObjectUtil.copyProperties(student.getAccount(), new AccountResponse(), AccountResponse.class, true);
        studentResponse.setAccount(accountResponse);
        return studentResponse;
    }

}
