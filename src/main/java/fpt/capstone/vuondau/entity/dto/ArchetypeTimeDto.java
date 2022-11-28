package fpt.capstone.vuondau.entity.dto;


import fpt.capstone.vuondau.entity.DayOfWeek;

public class ArchetypeTimeDto {

    private ArchetypeDto archetype;
    private SlotDto slot ;
    private DayOfWeekDto dayOfWeek ;

    public ArchetypeDto getArchetype() {
        return archetype;
    }

    public void setArchetype(ArchetypeDto archetype) {
        this.archetype = archetype;
    }

    public SlotDto getSlot() {
        return slot;
    }

    public void setSlot(SlotDto slot) {
        this.slot = slot;
    }

    public DayOfWeekDto getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeekDto dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
