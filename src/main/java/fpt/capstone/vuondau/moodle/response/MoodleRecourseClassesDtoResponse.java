package fpt.capstone.vuondau.moodle.response;

import java.util.List;

public class MoodleRecourseClassesDtoResponse {

    private Long classId ;

    private List<MoodleRecourseDtoResponse> modules ;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<MoodleRecourseDtoResponse> getModules() {
        return modules;
    }

    public void setModules(List<MoodleRecourseDtoResponse> modules) {
        this.modules = modules;
    }
}
