package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class TimeTableDto {

    private Long id ;
    private Instant date;
    private int slotNumber ;

    private ArchetypeTimeDto archetypeTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ArchetypeTimeDto getArchetypeTime() {
        return archetypeTime;
    }

    public void setArchetypeTime(ArchetypeTimeDto archetypeTime) {
        this.archetypeTime = archetypeTime;
    }
}
