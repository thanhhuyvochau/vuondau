package fpt.capstone.vuondau.moodle.request;


import java.util.List;

public class GetMoodleAssignmentIdsCourseRequest {

    private List<Integer> assignmentids ;

    public List<Integer> getAssignmentids() {
        return assignmentids;
    }

    public void setAssignmentids(List<Integer> assignmentids) {
        this.assignmentids = assignmentids;
    }
}
