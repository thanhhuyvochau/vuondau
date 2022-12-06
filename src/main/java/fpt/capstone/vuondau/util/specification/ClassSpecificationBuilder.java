package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
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

    public ClassSpecificationBuilder queryLikeByClassName(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

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
                .reduce(all(), Specification::or);
    }

    private Specification<Class> all() {
        return Specification.where(null);
    }


}