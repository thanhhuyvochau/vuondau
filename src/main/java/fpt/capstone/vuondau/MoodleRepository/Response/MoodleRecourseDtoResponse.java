package fpt.capstone.vuondau.MoodleRepository.Response;

import java.util.List;

public class MoodleRecourseDtoResponse {


    private Long id;
    private String name;

    private List<ResourceDtoMoodleResponse> modules;

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

    public List<ResourceDtoMoodleResponse> getModules() {
        return modules;
    }

    public void setModules(List<ResourceDtoMoodleResponse> modules) {
        this.modules = modules;
    }
}
