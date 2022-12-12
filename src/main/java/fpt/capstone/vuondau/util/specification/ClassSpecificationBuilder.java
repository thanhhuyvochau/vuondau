package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.EClassLevelCode;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSpecificationBuilder {

    public static ClassSpecificationBuilder specification() {
        return new ClassSpecificationBuilder();
    }

    private final List<Specification<Class>> specifications = new ArrayList<>();

    public ClassSpecificationBuilder queryStatusClass(EClassStatus status) {
        if (status == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Class_.STATUS), status));
        return this;
    }

    public ClassSpecificationBuilder queryByStartDate(Instant instant) {
        if (instant == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Class_.START_DATE), instant));
        return this;
    }

    public ClassSpecificationBuilder queryByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null || maxPrice == null) {
            return this;
        }
        if (maxPrice.compareTo(minPrice) < 0) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Class_.UNIT_PRICE), minPrice, maxPrice));
        return this;
    }

    public ClassSpecificationBuilder queryByEndDate(Instant instant) {
        if (instant == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Class_.START_DATE), instant));
        return this;
    }

    public ClassSpecificationBuilder queryLikeByClassName(String q) {
        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> classname = root.get(Class_.name);
            return criteriaBuilder.like(classname, '%' + q + '%');
        });
        return this;
    }

    public ClassSpecificationBuilder queryLikeByTeacherName(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            Path<Account> objectPath = root.get(Class_.ACCOUNT);
            Expression<String> stringExpression = objectPath.get(Account_.FIRST_NAME);
            return criteriaBuilder.like(stringExpression, '%' + q + '%');
        });
        return this;
    }

    public Specification<Class> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Class> all() {
        return Specification.where(null);
    }

    public ClassSpecificationBuilder queryLevelClass(EClassLevelCode level) {
        if (level == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Class_.CLASS_LEVEL), level));
        return this;
    }

    public ClassSpecificationBuilder queryTeacherClass(List<Account> teachers) {
        if (teachers == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.and(root.get(Class_.account).in(teachers)))
        );
        return this;
    }

    public ClassSpecificationBuilder querySubjectClass(List<Subject> subjects) {
        if (subjects == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            Path<Course> coursePath = root.get(Class_.course);
            CriteriaBuilder.In<Subject> inClause = criteriaBuilder.in(coursePath.get(Course_.subject));
            for (Subject subject : subjects) {
                inClause.value(subject);
            }
            return inClause;
        });


        return this;
    }
}