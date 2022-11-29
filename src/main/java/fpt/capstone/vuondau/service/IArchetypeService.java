package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.ArchetypeTeacherDto;
import fpt.capstone.vuondau.entity.dto.SlotDto;

import java.util.List;

public interface IArchetypeService {


    List<ArchetypeTeacherDto> getArchetypeOfTeacher(long teacherId);
}
