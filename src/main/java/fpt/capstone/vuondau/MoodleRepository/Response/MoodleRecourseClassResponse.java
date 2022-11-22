package fpt.capstone.vuondau.MoodleRepository.Response;

import java.util.List;

public class MoodleRecourseClassResponse {


    private Long id;
    private String name;
    private Long visible;
    private String summary;
    private Long summaryformat ;

    private Long section;

    private Long hiddenbynumsections;

    private boolean uservisible;

    private List<ResourceMoodleResponse> modules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVisible() {
        return visible;
    }

    public void setVisible(Long visible) {
        this.visible = visible;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(Long summaryformat) {
        this.summaryformat = summaryformat;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public Long getHiddenbynumsections() {
        return hiddenbynumsections;
    }

    public void setHiddenbynumsections(Long hiddenbynumsections) {
        this.hiddenbynumsections = hiddenbynumsections;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    public List<ResourceMoodleResponse> getModules() {
        return modules;
    }

    public void setModules(List<ResourceMoodleResponse> modules) {
        this.modules = modules;
    }
}
