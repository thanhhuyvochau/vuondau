package fpt.capstone.vuondau.entity.response;


import lombok.Data;

@Data
public class AccountTeacherResponse {
    private String username;
    private String password;
    private boolean isActive ;
    private Long teacherId ;
}
