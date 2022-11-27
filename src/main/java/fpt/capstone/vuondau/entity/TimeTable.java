package fpt.capstone.vuondau.entity;


import fpt.capstone.vuondau.entity.common.EClassStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "time_table")
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "date")
    private Instant date ;

    @Column(name = "slot_number")
    private int slotNumber ;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;


    @ManyToOne
    @JoinColumn(name = "archetype_time_id")
    private ArchetypeTime archetypeTime;

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

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public ArchetypeTime getArchetypeTime() {
        return archetypeTime;
    }

    public void setArchetypeTime(ArchetypeTime archetypeTime) {
        this.archetypeTime = archetypeTime;
    }
}
