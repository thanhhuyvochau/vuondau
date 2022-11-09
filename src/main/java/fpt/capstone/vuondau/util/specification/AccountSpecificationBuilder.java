package fpt.capstone.vuondau.util.specification;


import fpt.capstone.vuondau.entity.Account;

import fpt.capstone.vuondau.entity.Account_;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

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

        specifications.add((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.concat(criteriaBuilder.coalesce(root.get(Account_.FIRST_NAME), Strings.EMPTY),
                                criteriaBuilder.coalesce(root.get(Account_.LAST_NAME), Strings.EMPTY)),
                        "%" + q + "%"));


                return this ;
    }

    public Specification<Account> build() {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(all(), Specification::and);
    }

    private Specification<Account> all() {
        return Specification.where(null);
    }


}