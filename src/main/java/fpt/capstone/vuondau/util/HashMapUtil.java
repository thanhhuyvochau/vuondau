package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.DayOfWeek;
import fpt.capstone.vuondau.entity.Slot;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class HashMapUtil {


    public static Map<Long, Slot> convertSlotListToMap(List<Slot> slotList) {
        Map<Long, Slot> slotMap = slotList.stream().collect(Collectors.toMap(Slot::getId, slot -> slot));
        return slotMap;
    }
    public static Map<Long, DayOfWeek> convertDoWListToMap(List<DayOfWeek> dayOfWeekList) {
        Map<Long, DayOfWeek> DayOfWeekMap = dayOfWeekList.stream().collect(Collectors.toMap(fpt.capstone.vuondau.entity.DayOfWeek::getId, dayOfWeek -> dayOfWeek));
        return DayOfWeekMap;
    }



}
