package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.common.ERequestStatus;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestFormSpecificationBuilder {

    public static RequestFormSpecificationBuilder specification() {
        return new RequestFormSpecificationBuilder();
    }

    private final List<Specification<Request>> specifications = new ArrayList<>();


    public RequestFormSpecificationBuilder queryByStudent(Long studentId) {
        if (studentId == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> root.get(Request_.ACCOUNT).in(studentId));
        return this;
    }


    public RequestFormSpecificationBuilder queryByStatus(ERequestStatus statuses) {
        if (statuses == null) {
            return this;
        }
        if (statuses.equals(ERequestStatus.ALL)) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> root.get(Request_.STATUS).in(statuses));
        return this;
    }


    public RequestFormSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> title = root.get(Request_.title);
            Expression<String> reason = root.get(Request_.reason);

            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " ", title, reason);
            String search = q.replaceAll("\\s\\s+", " ").trim();
            return criteriaBuilder.like(stringExpression, '%' + search + '%');
        });

        return this;

    }


    public RequestFormSpecificationBuilder queryByDate(Instant dateFrom, Instant dateTo) {
        if (dateFrom == null) {
            return this;
        }
        Instant dateToPlus = dateTo.plus(1, ChronoUnit.DAYS);
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Request_.lastModified), dateFrom));
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Request_.lastModified), dateToPlus));
        return this;
    }


    public Specification<Request> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Request> all() {
        return Specification.where(null);
    }


}