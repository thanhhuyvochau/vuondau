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





//    public static Boolean compareSection(Section o1, Section o2) {
//        if (o1 == o2) return true;
//        if (o1 == null || o2 == null) return false;
//        return Objects.equals(o2.getClazz(), o1.getClazz()) && Objects.equals(o1.getName(), o2.getName()) && Objects.equals(o1.getVisible(), o2.getVisible()) && compareModule(o1.getModules(),o2.getModules());
//    }
//
//    public static Boolean compareModule(Module o1, Module o2) {
//        if (o1 == o2) return true
//        if (o1 == null || o2 == null) return false;
//        return Objects.equals(o1.getName(), o2.getName()) && Objects.equals(o1.getUrl(), o2.getUrl()) && o1.getType() == o1.getType();
//    }
//    public static Boolean compareList(List o1, List o2){
//        if (o1 == null || o2 == null) return false;
//        Collections.sort(o1);
//        ArrayList<Object> objects = new ArrayList<>();
//        objects.equals();
//        return o1.hashCode()
//    }
}
