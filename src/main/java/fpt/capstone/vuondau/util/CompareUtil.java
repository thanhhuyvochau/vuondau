package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Module;
import fpt.capstone.vuondau.entity.Section;
import fpt.capstone.vuondau.repository.SectionRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CompareUtil {
    public static void main(String[] args) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();

        l1.add(new Integer(8));
        l1.add(new Integer(9));
        l1.add(new Integer(10));

        l2.add(new Integer(8));
        l2.add(new Integer(9));
        l2.add(new Integer(10));

        System.out.println(l1.hashCode() == l2.hashCode());


    }
}
