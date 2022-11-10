package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.Dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.Dto.TeacherCourseDto;
import fpt.capstone.vuondau.entity.common.EGradeType;

import java.util.List;

public class RequestFormResponese {
    private Long id;

    private String title;

    private String createDate ;

    private RequestTypeDto requestType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public RequestTypeDto getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeDto requestType) {
        this.requestType = requestType;
    }
}
