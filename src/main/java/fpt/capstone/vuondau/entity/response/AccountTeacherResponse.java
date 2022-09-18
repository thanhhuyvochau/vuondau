package fpt.capstone.vuondau.entity.response;


import lombok.Data;

@Data
public class AccountTeacherResponse {
    private String username;
    private boolean isActive ;
    private Long teacherId ;
}
