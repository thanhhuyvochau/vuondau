package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.common.ERequestStatus;
import fpt.capstone.vuondau.entity.dto.RequestTypeDto;
import fpt.capstone.vuondau.entity.dto.StudentDto;

public class RequestFormResponse {


    private Long id ;
    private String title;

    private String reason;

    private String name ;

    private String url ;

    private RequestTypeDto requestType;

    private StudentDto student ;

    private ERequestStatus status ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERequestStatus getStatus() {
        return status;
    }

    public void setStatus(ERequestStatus status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestTypeDto getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeDto requestType) {
        this.requestType = requestType;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }
}
