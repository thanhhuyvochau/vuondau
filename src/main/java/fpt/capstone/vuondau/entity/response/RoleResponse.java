package fpt.capstone.vuondau.entity.response;

import java.util.List;

public class RoleResponse {
    // NOTE về sau trả thêm cả user trong role.
    private Long id;
    private String name;
    private List<AccountResponse> accountResponseList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AccountResponse> getAccountResponseList() {
        return accountResponseList;
    }

    public void setAccountResponseList(List<AccountResponse> accountResponseList) {
        this.accountResponseList = accountResponseList;
    }
}
