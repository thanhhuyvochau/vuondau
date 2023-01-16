package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.BaseEntity;
import fpt.capstone.vuondau.entity.dto.MarkDto;
import fpt.capstone.vuondau.entity.dto.ModuleDto;


public class MarkResponse{
    private ModuleDto module;
    private MarkDto markDto;

    public ModuleDto getModule() {
        return module;
    }

    public void setModule(ModuleDto module) {
        this.module = module;
    }

    public MarkDto getMarkDto() {
        return markDto;
    }

    public void setMarkDto(MarkDto markDto) {
        this.markDto = markDto;
    }
}
