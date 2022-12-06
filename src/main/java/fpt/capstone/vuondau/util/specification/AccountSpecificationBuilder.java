package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Account_;
import fpt.capstone.vuondau.util.SpecificationUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountSpecificationBuilder {

    public static AccountSpecificationBuilder specification() {
        return new AccountSpecificationBuilder();
    }

    private final List<Specification<Account>> specifications = new ArrayList<>();


    public AccountSpecificationBuilder queryLike(String q) {
        if (q == null || q.trim().isEmpty()) {
            return this;
        }

        specifications.add((root, query, criteriaBuilder) -> {
            Expression<String> lastName = root.get(Account_.lastName);
            Expression<String> firstName = root.get(Account_.firstName);
            Expression<String> username = root.get(Account_.username);
            Expression<String> stringExpression = SpecificationUtil.concat(criteriaBuilder, " ", lastName, firstName, username);
            return criteriaBuilder.like(stringExpression, '%' + q + '%');
        });
        return this;
    }

    public Specification<Account> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Account> all() {
        return Specification.where(null);
    }


}