/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperheroService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jswan
 */
@Controller
public class MainPageController {

    SuperheroService superService;
    LocationService locationService;
    SightingService sightingService;

    @Inject
    public MainPageController(SuperheroService superService,
            LocationService locationService, SightingService sightingService) {

        this.superService = superService;
        this.locationService = locationService;
        this.sightingService = sightingService;
    }


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String superheroPage(Model model) {

        List<Sighting> sightList = sightingService.getRecentTenSightings();
        model.addAttribute("sightList", sightList);

        return "index";
    }


}
