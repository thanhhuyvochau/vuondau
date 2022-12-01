package fpt.capstone.vuondau.entity.request;

import fpt.capstone.vuondau.entity.dto.SlotDowDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;


public class TimeTableRequest implements Serializable {



    private String archetypeName  ;

    private String archetypeCode  ;


    private List<SlotDowDto> slotDow ;





    public String getArchetypeName() {
        return archetypeName;
    }

    public void setArchetypeName(String archetypeName) {
        this.archetypeName = archetypeName;
    }

    public String getArchetypeCode() {
        return archetypeCode;
    }

    public void setArchetypeCode(String archetypeCode) {
        this.archetypeCode = archetypeCode;
    }



    public List<SlotDowDto> getSlotDow() {
        return slotDow;
    }

    public void setSlotDow(List<SlotDowDto> slotDow) {
        this.slotDow = slotDow;
    }
}
