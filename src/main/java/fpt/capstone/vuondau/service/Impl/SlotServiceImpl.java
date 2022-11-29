package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.Slot;
import fpt.capstone.vuondau.entity.dto.SlotDto;
import fpt.capstone.vuondau.repository.SlotRepository;
import fpt.capstone.vuondau.service.ISlotService;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.RequestUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SlotServiceImpl implements ISlotService {

    final private RequestUtil requestUtil;

    final private SlotRepository slotRepository ;

    public SlotServiceImpl(RequestUtil requestUtil, SlotRepository slotRepository) {
        this.requestUtil = requestUtil;
        this.slotRepository = slotRepository;
    }


    @Override
    public List<SlotDto> getAllSlot() {
        List<Slot> allAlot = slotRepository.findAll();
        List<SlotDto>slotList = new ArrayList<>();
        allAlot.stream().map(slot -> {
            slotList.add(ObjectUtil.copyProperties(slot, new SlotDto() , SlotDto.class)) ;
            return slot ;
        }).collect(Collectors.toList());
        return slotList;
    }
}
