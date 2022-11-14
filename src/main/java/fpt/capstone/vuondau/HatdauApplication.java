package fpt.capstone.vuondau;

import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.ESubjectCode;
import fpt.capstone.vuondau.repository.RoleRepository;
import fpt.capstone.vuondau.repository.SubjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

import static fpt.capstone.vuondau.entity.common.EAccountRole.STUDENT;
import static fpt.capstone.vuondau.entity.common.EAccountRole.TEACHER;
import static fpt.capstone.vuondau.entity.common.ESubjectCode.*;


@SpringBootApplication
public class HatdauApplication {

    private final RoleRepository roleRepository;

    private final SubjectRepository subjectRepository;

    public HatdauApplication(RoleRepository roleRepository, SubjectRepository subjectRepository) {
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(HatdauApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiDataRole() {

        List<Role> allRole = roleRepository.findAll();
//        Boolean existTeacherRole = allRole.stream().map(role -> role.getCode().equals(TEACHER)).findFirst().orElse(Boolean.FALSE);
//        Boolean existStudentRole = allRole.stream().map(role -> role.getCode().equals(STUDENT)).findFirst().orElse(Boolean.FALSE);
        Boolean existTeacherRole = false;
        Boolean existStudentRole = false;
        for (Role role : allRole) {
            if (role.getCode().equals(TEACHER)) {
                existTeacherRole = true;
            }
            if (role.getCode().equals(STUDENT)) {
                existStudentRole = true;
            }
        }

        List<Role> roleList = new ArrayList<>();
        if (!existTeacherRole) {
            Role teacherRole = new Role();
            teacherRole.setCode(EAccountRole.TEACHER);
            teacherRole.setName("teacher");
            roleList.add(teacherRole);
        }
        if (!existStudentRole) {
            Role roleStudent = new Role();
            roleStudent.setCode(EAccountRole.STUDENT);
            roleStudent.setName("student");
            roleList.add(roleStudent);
        }
        roleRepository.saveAll(roleList);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void intiDataSubject() {
        List<Subject> allSubject = subjectRepository.findAll();
        Boolean existToan = false;
        Boolean existVatLy= false;
        Boolean existHoaHoc= false;
        Boolean existTiengAnh= false;
        Boolean existSinhHoc= false;
        Boolean existNguVan= false;
        Boolean existTinHoc = false;

        for (Subject subject : allSubject) {
            if (subject.getCode().equals(Toan)) {
                existToan = true;
            }
            if (subject.getCode().equals(VatLy)) {
                existVatLy = true;
            }
            if (subject.getCode().equals(HoaHoc)) {
                existHoaHoc = true;
            }
            if (subject.getCode().equals(TiengAnh)) {
                existTiengAnh = true;
            }
            if (subject.getCode().equals(SinhHoc)) {
                existSinhHoc = true;
            }
            if (subject.getCode().equals(NguVan)) {
                existNguVan = true;
            }
            if (subject.getCode().equals(TinHoc)) {
                existTinHoc = true;
            }
        }
        List<Subject> subjectList = new ArrayList<>();
        if (!existToan) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.Toan);
            subject.setName(Toan.label);
            subjectList.add(subject);
        }
        if (!existVatLy) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.VatLy);
            subject.setName(VatLy.label);
            subjectList.add(subject);
        }
        if (!existHoaHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.HoaHoc);
            subject.setName(HoaHoc.label);
            subjectList.add(subject);
        }
        if (!existTiengAnh) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.TiengAnh);
            subject.setName(TiengAnh.label);
            subjectList.add(subject);
        }
        if (!existSinhHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.SinhHoc);
            subject.setName(SinhHoc.label);
            subjectList.add(subject);
        }
        if (!existNguVan) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.NguVan);
            subject.setName(NguVan.label);
            subjectList.add(subject);
        }
        if (!existTinHoc) {
            Subject subject = new Subject();
            subject.setCode(ESubjectCode.TinHoc);
            subject.setName(TinHoc.label);
            subjectList.add(subject);
        }
        subjectRepository.saveAll(subjectList);
    }
}