package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.service.IClassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    private final IClassService iClassService ;

    public ClassController(IClassService iClassService) {
        this.iClassService = iClassService;
    }




}
