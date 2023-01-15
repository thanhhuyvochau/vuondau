package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Mark;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.response.MarkResponse;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.repository.ClassRepository;
import fpt.capstone.vuondau.repository.MarkRepository;
import fpt.capstone.vuondau.service.IMarkService;
import fpt.capstone.vuondau.util.ClassUtil;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MarkServiceImpl implements IMarkService {
    private final MarkRepository markRepository;
    private final AccountRepository accountRepository;
    private final ClassRepository classRepository;
    private final MessageUtil messageUtil;
    private final SecurityUtil securityUtil;

    public MarkServiceImpl(MarkRepository markRepository, AccountRepository accountRepository, ClassRepository classRepository, MessageUtil messageUtil, SecurityUtil securityUtil) {
        this.markRepository = markRepository;
        this.accountRepository = accountRepository;
        this.classRepository = classRepository;
        this.messageUtil = messageUtil;
        this.securityUtil = securityUtil;
    }

    @Override
    public List<MarkResponse> getStudentMark(Long clazzId, Long studentId) {
        Class clazz = classRepository.findById(clazzId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Class not found by id:" + clazzId));
        Account student = accountRepository.findById(studentId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Student not found by id:" + studentId));
        Account currentUser = securityUtil.getCurrentUserThrowNotFoundException();
        Boolean isValidMember = ClassUtil.isValidClassMember(clazz, student);
        if (!isValidMember)
            throw ApiException.create(HttpStatus.METHOD_NOT_ALLOWED).withMessage("Class is not valid to you!");
        Role role = currentUser.getRole();
        EAccountRole roleCode = role.getCode();
        boolean isValidToGetMark = false;
        switch (roleCode) {
            case STUDENT:
                if (Objects.equals(currentUser.getId(), student.getId())) {
                    isValidToGetMark = true;
                }
                break;
            case MANAGER:
            case TEACHER:
                isValidToGetMark = true;
                break;
        }
        if (isValidToGetMark) {
            List<Mark> studentMarks = markRepository.findAllByStudentAndModule_Section_Clazz(student, clazz);

        }
        return null;
    }

    @Override
    public void synchronizeMark() {

    }
}
