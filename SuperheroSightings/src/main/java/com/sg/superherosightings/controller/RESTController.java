/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.service.SightingService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jswan
 */
@Controller
@CrossOrigin
public class RESTController {

    private SightingService sightService;

    @Inject
    public RESTController(SightingService sightService) {
        this.sightService = sightService;
    }

    @RequestMapping(value = "/getRecentTenSightings", method = RequestMethod.GET)
    @ResponseBody
    public List<Sighting> getRecentTenSightings() {
        return sightService.getRecentTenSightings();
    }

}
