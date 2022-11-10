package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Course_;
import fpt.capstone.vuondau.entity.Request;
import fpt.capstone.vuondau.entity.Request_;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestFormSpecificationBuilder {

    public static RequestFormSpecificationBuilder specification() {
        return new RequestFormSpecificationBuilder();
    }

    private final List<Specification<Request>> specifications = new ArrayList<>();


    public RequestFormSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> requestCode = root.get(Request_.title);

            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " ", requestCode);
            return criteriaBuilder.like(stringExpression, '%' + q + '%');
        });


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