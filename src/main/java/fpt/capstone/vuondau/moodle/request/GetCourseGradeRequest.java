package fpt.capstone.vuondau.moodle.request;

public class GetCourseGradeRequest {
    private Long courseid;
    private Long userid;

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
