package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.request.RequestTypeDto;
import fpt.capstone.vuondau.entity.request.StudentDto;

public class RequestFormResponse {

    private String title;

    private String reason;

    private String name ;

    private String url ;

    private RequestTypeDto requestType;

    private StudentDto student ;

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
