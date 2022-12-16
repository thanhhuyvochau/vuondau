package fpt.capstone.vuondau.entity.response;


import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseDtoResponse;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.ClassTypeDto;
import fpt.capstone.vuondau.entity.dto.TimeTableDto;

import java.math.BigDecimal;
import java.time.Instant;
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
