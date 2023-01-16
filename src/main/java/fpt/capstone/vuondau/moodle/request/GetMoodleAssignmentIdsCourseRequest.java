package fpt.capstone.vuondau.moodle.request;


import java.util.List;

public class GetMoodleAssignmentIdsCourseRequest {

    private List<Long> assignmentids ;

    public List<Long> getAssignmentids() {
        return assignmentids;
    }

    public void setAssignmentids(List<Long> assignmentids) {
        this.assignmentids = assignmentids;
    }
}
