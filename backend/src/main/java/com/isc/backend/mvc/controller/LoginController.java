package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.entity.Regulator;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.service.IOrganizerService;
import com.isc.backend.mvc.service.IRegulatorService;
import com.isc.backend.mvc.service.IVolunteerService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private IVolunteerService volunteerService;

    @Resource
    private IOrganizerService organizerService;

    @Resource
    private IRegulatorService regulatorService;

    @ApiOperation("志愿者用户注册接口")
    @PostMapping("/addvolunteer")
    public Result<?> addVolunteer(@RequestBody Volunteer volunteer){
        return volunteerService.addVolunteer(volunteer);
    }

    @ApiOperation("组织者注册接口")
    @PostMapping("/addorganizer")
    public Result<?> addOrganizer(@RequestBody Organizer organizer){
        return organizerService.addOrganizer(organizer);
    }

    @ApiOperation("管理者注册接口")
    @PostMapping("/addregulator")
    public Result<?> addRegulator(@RequestBody Regulator regulator){
        return regulatorService.addRegulator(regulator);
    }

}
