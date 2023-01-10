package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.EClassLevel;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionSpecificationBuilder {

    public static QuestionSpecificationBuilder specification() {
        return new QuestionSpecificationBuilder();
    }

    private final List<Specification<Question>> specifications = new ArrayList<>();


    public QuestionSpecificationBuilder queryByContent(String q) {
        if (q == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Question_.CONTENT), "%" + q + "%"));
        return this;
    }

    public QuestionSpecificationBuilder queryByTitle(String q) {
        if (q == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Question_.TITLE), "%" + q + "%"));
        return this;
    }

    public QuestionSpecificationBuilder queryByForum(Forum forum) {
        if (forum == null) {
            return this;
        }
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Question_.FORUM), forum));
        return this;
    }

    public Specification<Question> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Question> all() {
        return Specification.where(null);
    }


}