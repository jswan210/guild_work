/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.SightingAlreadyExistsException;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperheroService;
import com.sg.superherosightings.service.SuperheroTwoPlacesAtOnceException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author jswan
 */
@Controller

public class SuperheroSightingsController {

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    SightingService sightingService;
    SuperheroService superService;
    LocationService locService;

    @Inject
    public SuperheroSightingsController(SightingService sightingService, SuperheroService superService, LocationService locService) {
        this.sightingService = sightingService;
        this.superService = superService;
        this.locService = locService;
    }

    //DISPLAY MAIN PAGE
    @RequestMapping(value = {"/superheroSighting/superheroSighting"}, method = RequestMethod.GET)
    public String sightingPage(Model model) {
        List<Sighting> sightList = sightingService.getAllSightings();

        model.addAttribute("sightList", sightList);
        return "/superheroSighting/superheroSighting";
    }

//DELETE SIGHTING
    @RequestMapping(value = "/superheroSighting/deleteSuperheroSigthing", method = RequestMethod.GET)
    public String deleteSuperheroSigthing(HttpServletRequest request) {

        String sightIdParameter = request.getParameter("sightId");

        sightingService.deleteSighting(Integer.parseInt(sightIdParameter));
        return "redirect:/superheroSighting/superheroSighting";
    }

    //CREATE NEW SUPERHERO-SIGHTING
    @RequestMapping(value = "/superheroSighting/displayCreateSuperheroSightingForm", method = RequestMethod.GET)
    public String displayCreateNewSuperheroSightingForm(Model model) {

        List<Location> locList = locService.getAllLocations();
        model.addAttribute("locList", locList);

        List<Superhero> superList = superService.getAllSuperheros();
        model.addAttribute("superList", superList);

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/superheroSighting/createSuperheroSightingError";
        }

        return "/superheroSighting/createSuperheroSighting";
    }

    @RequestMapping(value = "/superheroSighting/createSuperheroSighting", method = RequestMethod.POST)
    public String createSuperheroSighting(HttpServletRequest request, RedirectAttributes ra) throws
            SightingAlreadyExistsException, SuperheroTwoPlacesAtOnceException {
        String superIdParameter = request.getParameter("superhero-by-name");
        String locIdParameter = request.getParameter("location-by-name");

        try {
            Sighting newSight = new Sighting();
            newSight.getSuperhero().setSuperheroId(Integer.parseInt(superIdParameter));
            newSight.getLocation().setLocationId(Integer.parseInt(locIdParameter));
            String dateParameter = request.getParameter("date");
            dateParameter = dateParameter.replace(" ", "T");
            newSight.setDate(Timestamp.valueOf(LocalDateTime.parse(dateParameter, formatter)));
            sightingService.addSighting(newSight);
        } catch (SightingAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO SIGHTING ALREADY EXISTS");
            return "redirect:displayCreateSuperheroSightingForm";
        } catch (SuperheroTwoPlacesAtOnceException ex) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO CANNOT BE SEEN AT THE SAME TIME IN TWO DIFFERENT PLACES, CAN THEY?");
            return "redirect:displayCreateSuperheroSightingForm";
        }
        return "redirect:/superheroSighting/superheroSighting";
    }

    //SIGHTING DISPLAY INFO PAGE
    @RequestMapping(value = "/superheroSighting/displaySuperheroSightingDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String sightIdParameter = request.getParameter("sightId");
        Sighting sighting = new Sighting();
        sighting.setSightId(Integer.parseInt(sightIdParameter));
        sighting = sightingService.getSightingById(sighting.getSightId());
        model.addAttribute("ourSight", sighting);
        return "/superheroSighting/superheroSightingDetails";
    }

    //DISPLAYS EDIT SIGHTINGS PAGE
    @RequestMapping(value = "/superheroSighting/displayEditSuperheroSighting", method = RequestMethod.GET)
    public String displaysEditSighting(HttpServletRequest request, Model model) {
        String sightIdParameter;
        if (!model.asMap().isEmpty()) {
            sightIdParameter = model.asMap().get("sightId").toString();

        } else {
            sightIdParameter = request.getParameter("sightId");
        }
        int sightId = Integer.parseInt(sightIdParameter);
        Sighting ourSighting = sightingService.getSightingById(sightId);
        List< Location> locList = locService.getAllLocations();
        List< Superhero> superList = superService.getAllSuperheros();

        model.addAttribute("ourSighting", ourSighting);
        model.addAttribute("locList", locList);
        model.addAttribute("superList", superList);
        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/superheroSighting/editSuperheroSightingError";
        }
        return "/superheroSighting/editSuperheroSighting";

    }

    //UPDATES(edits) SIGHTING
    @RequestMapping(value = "/superheroSighting/editSuperheroSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request,
            @ModelAttribute("ourSighting") Sighting ourSighting,
            BindingResult result, RedirectAttributes ra) throws
            SightingAlreadyExistsException {

        String sightIdParameter = request.getParameter("sightId");

        Sighting newSight = sightingService.getSightingById(Integer.parseInt(sightIdParameter));
        String dateParameterNew = request.getParameter("change-date");
        String superIdParameterNew = request.getParameter("change-superhero-by-name");
        String locIdParameterNew = request.getParameter("change-location-by-name");

        if (!superIdParameterNew.equals("")) {
            newSight.getSuperhero().setSuperheroId(Integer.parseInt(superIdParameterNew));
        }
        if (!locIdParameterNew.equals("")) {
            newSight.getLocation().setLocationId(Integer.parseInt(locIdParameterNew));
        }
        if (!dateParameterNew.equals("")) {
            dateParameterNew = dateParameterNew.replace(" ", "T");
            newSight.setDate(Timestamp.valueOf(LocalDateTime.parse(dateParameterNew, formatter)));
        }
        try {
            sightingService.updateSighting(newSight);

        } catch (SightingAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO SIGHTING ALREADY EXISTS");
            ra.addFlashAttribute("sightId", newSight.getSightId());
            return "redirect:displayEditSuperheroSighting";
        }
        return "redirect:superheroSighting";
    }

}
