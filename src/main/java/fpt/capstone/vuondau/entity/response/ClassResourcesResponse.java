package fpt.capstone.vuondau.entity.response;


import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;

import java.util.List;

public class ClassResourcesResponse {



    private List<MoodleRecourseDtoResponse> resources;

    public List<MoodleRecourseDtoResponse> getResources() {
        return resources;
    }

    public void setResources(List<MoodleRecourseDtoResponse> resources) {
        this.resources = resources;
    }
}
