package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RevenueSpecificationBuilder {

    public static RevenueSpecificationBuilder specification() {
        return new RevenueSpecificationBuilder();
    }

    private final List<Specification<Transaction>> specifications = new ArrayList<>();


    public RevenueSpecificationBuilder queryByClasses(List<Long> classes) {

        if (classes == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            Join<Transaction, Class> classJoin = root.join(Transaction_.PAYMENT_CLASS, JoinType.INNER);
            return criteriaBuilder.or(criteriaBuilder.or(classJoin.get(Class_.ID).in(classes))

            );
        });
        return this;

    }

    public RevenueSpecificationBuilder queryByTeacherIds(List<Long> teacherIds) {
        if (teacherIds == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            Join<Transaction, Class> classJoin = root.join(Transaction_.PAYMENT_CLASS, JoinType.INNER);
            return criteriaBuilder.or(criteriaBuilder.or(classJoin.get(Class_.ID).in(teacherIds))

            );
        });

        return this;
    }

    public RevenueSpecificationBuilder date(Instant dateFrom, Instant dateTo) {
        if (dateFrom == null && dateTo == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> {
            Join<Transaction, Class> classJoin = root.join(Transaction_.PAYMENT_CLASS, JoinType.INNER);

            return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(classJoin.get(Class_.startDate), dateFrom),
                    criteriaBuilder.lessThanOrEqualTo(classJoin.get(Class_.endDate), dateTo));

        });

        return this;
    }

    public Specification<Transaction> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Transaction> all() {
        return Specification.where(null);
    }

}