package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.Subject_;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SuggestSubjectSpecificationBuilder {

    public static SuggestSubjectSpecificationBuilder specification() {
        return new SuggestSubjectSpecificationBuilder();
    }

    private final List<Specification<Subject>> specifications = new ArrayList<>();


    public SuggestSubjectSpecificationBuilder querySubjectCode(String subjectCode) {
        if (subjectCode == null || subjectCode.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> courseCode = root.get(Subject_.CODE);


            return criteriaBuilder.like(courseCode, subjectCode);
        });


        return this;
    }

    public Specification<Subject> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Subject> all() {
        return Specification.where(null);
    }


}