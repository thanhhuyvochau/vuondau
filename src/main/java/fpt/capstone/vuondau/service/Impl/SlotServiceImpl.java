package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.Slot;
import fpt.capstone.vuondau.entity.dto.SlotDto;
import fpt.capstone.vuondau.repository.SlotRepository;
import fpt.capstone.vuondau.service.ISlotService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SlotServiceImpl implements ISlotService {


    final private SlotRepository slotRepository ;

    public SlotServiceImpl( SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }


    @Override
    public List<SlotDto> getAllSlot() {
        List<Slot> allSlots = slotRepository.findAll();
        List<SlotDto>slotList = new ArrayList<>();
        allSlots.stream().map(slot -> {
            slotList.add(ObjectUtil.copyProperties(slot, new SlotDto() , SlotDto.class)) ;
            return slot ;
        }).collect(Collectors.toList());
        return slotList;
    }
}
