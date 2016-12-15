package com.disney.awing.schedule;

import org.smpte_ra.schemas._2021._2007.bxf.BxfMessage;
import org.smpte_ra.schemas._2021._2007.bxf.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ScheduleUploadController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleUploadController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/schedules",
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity uploadXML(@RequestBody BxfMessage message) {
        scheduleService.create(message);
        return ResponseEntity.ok().build();
    }
}
