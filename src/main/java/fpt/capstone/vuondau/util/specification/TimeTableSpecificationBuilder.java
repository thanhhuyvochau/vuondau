package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeTableSpecificationBuilder {

    public static TimeTableSpecificationBuilder specification() {
        return new TimeTableSpecificationBuilder();
    }

    private final List<Specification<TimeTable>> specifications = new ArrayList<>();



    public Specification<TimeTable> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }


    public TimeTableSpecificationBuilder queryClass(Class  aClass) {
        if (aClass == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(root.get(TimeTable_.clazz).in(aClass));
        });

        return this;
    }


    public TimeTableSpecificationBuilder queryTeacherInClass(Account account) {
        if (account == null) {
            return this;
        }
        if (account.getRole().getCode().equals(EAccountRole.TEACHER)){
            specifications.add((root, query, criteriaBuilder) -> {

                Join<TimeTable, Class> classJoin = root.join(TimeTable_.clazz, JoinType.INNER);

                return criteriaBuilder.and(classJoin.get(Class_.account).in(account));

            });
        }


        return this;
    }


//    public TimeTableSpecificationBuilder queryStudentInClass(Account account) {
//        if (account == null) {
//            return this;
//        }
//
//
//            specifications.add((root, query, criteriaBuilder) -> {
//
//                Join<TimeTable, Class> classJoin = root.join(TimeTable_.clazz, JoinType.INNER);
//                Join<StudentClass, Class> studentClassClassJoin = classJoin.join(Class_.STUDENT_CLASSES, JoinType.INNER);
//                Path<Account> accountPath = studentClassClassJoin.get(StudentClass_.ACCOUNT);
//                return criteriaBuilder.and(accountPath.get(StudentClass_.ACCOUNT).in(account));
//
//            });
//
//
//
//        return this;
//    }



    public TimeTableSpecificationBuilder date(Instant dateFrom, Instant dateTo) {
        if (dateFrom == null && dateTo == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(TimeTable_.date), dateFrom));
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(TimeTable_.date), dateTo));
        return this;
    }


    private Specification<TimeTable> all() {
        return Specification.where(null);
    }


}