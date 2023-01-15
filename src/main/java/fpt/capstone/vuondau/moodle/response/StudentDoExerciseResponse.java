package fpt.capstone.vuondau.moodle.response;

import java.util.List;

public class StudentDoExerciseResponse {


    private Long userId ;

    private String timecreated;
    private String  timemodified;

    private Long status;

    private List<FileExerciseStudentResponse> files ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(String timecreated) {
        this.timecreated = timecreated;
    }

    public String getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(String timemodified) {
        this.timemodified = timemodified;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<FileExerciseStudentResponse> getFiles() {
        return files;
    }

    public void setFiles(List<FileExerciseStudentResponse> files) {
        this.files = files;
    }
}
