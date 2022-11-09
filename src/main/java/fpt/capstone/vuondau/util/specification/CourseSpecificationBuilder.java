package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Course;
import fpt.capstone.vuondau.entity.Course_;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSpecificationBuilder {

    public static CourseSpecificationBuilder specification() {
        return new CourseSpecificationBuilder();
    }

    private final List<Specification<Course>> specifications = new ArrayList<>();


    public CourseSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(criteriaBuilder.coalesce(root.get(Course_.NAME), Strings.EMPTY),
                                criteriaBuilder.coalesce(root.get(Course_.CODE), Strings.EMPTY)),
                        "%" + q + "%"));


                return this ;
    }

    public Specification<Course> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Course> all() {
        return Specification.where(null);
    }


}