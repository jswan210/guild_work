/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import com.sg.superherosightings.service.LocationAlreadyExistsException;
import com.sg.superherosightings.service.LocationExistsInOrganizationException;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationLinksExistsInSuperOrgException;
import com.sg.superherosightings.service.OrganizationNameAlreadyExistsException;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SuperheroAlreadyExistsException;
import com.sg.superherosightings.service.SuperheroLinkExistsInSightingException;
import com.sg.superherosightings.service.SuperheroLinkExistsInSuperOrganizationException;
import com.sg.superherosightings.service.SuperheroOrganizationService;
import com.sg.superherosightings.service.SuperheroService;
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
 * Superhero Sightings Superhero Controller
 *
 * @author jswan
 *
 */
@Controller

public class SuperheroController {

    OrganizationService orgService;
    LocationService locationService;
    SuperheroService superService;
    SuperheroOrganizationService superOrgService;

    @Inject
    public SuperheroController(SuperheroService superService,
            OrganizationService orgService, LocationService locationService,
            SuperheroOrganizationService superOrgService) {
        this.orgService = orgService;
        this.locationService = locationService;
        this.superService = superService;
        this.superOrgService = superOrgService;
    }

    //MAIN SUPERHERO PAGE (SHOWS LIST OF ALL SUPERHEROS)
    @RequestMapping(value = {"/superhero/superhero"}, method = RequestMethod.GET)
    public String superheroPage(Model model) {
        List<Superhero> superList = superService.getAllSuperheros();
        model.addAttribute("superList", superList);
        if (model.asMap().size() > 1) {
            model.asMap().get("errorMsg");
            return "/superhero/deleteSuperheroError";
        }
        return "/superhero/superhero";
    }

    //DISPLAY DETAILED INFO ON SELECTED SUPERHERO 
    @RequestMapping(value = "/superhero/displaySuperheroDetails", method = RequestMethod.GET)
    public String displaySuperheroDetails(HttpServletRequest request, Model model) {
        String superIdParameter = request.getParameter("superheroId");
        Superhero ourSuper = new Superhero();

        ourSuper.setSuperheroId(Integer.parseInt(superIdParameter));
        ourSuper = superService.getSuperheroById(ourSuper.getSuperheroId());
        List<SuperheroOrganizations> orgListForOurSuper = superOrgService
                .getSuperheroOrganizationsBySuperhero(ourSuper.getSuperheroId());

        model.addAttribute("orgListForOurSuper", orgListForOurSuper);
        model.addAttribute("superhero", ourSuper);
        return "/superhero/superheroDetails";
    }

    //DELETE A SUPERHERO
    @RequestMapping(value = "/superhero/deleteSuperhero", method = RequestMethod.GET)
    public String deleteSuperhero(HttpServletRequest request,
            RedirectAttributes ra) throws
            SuperheroLinkExistsInSightingException,
            OrganizationLinksExistsInSuperOrgException {
        String superIdParameter = request.getParameter("superheroId");
        Superhero ourSuper = new Superhero();
        ourSuper.setSuperheroId(Integer.parseInt(superIdParameter));
        try {
            superService.deleteSuperhero(ourSuper.getSuperheroId());
        } catch (OrganizationLinksExistsInSuperOrgException e) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO IS LINKED TO AN ORGANIZAITON, REMOVE LINK BEFORE DELETING!");
            return "redirect:/superhero/superhero";
        } catch (SuperheroLinkExistsInSightingException ex) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO IS LINKED TO A SIGHTING PLEASE REMOVE SIGHTING(S) BEFORE DELETING");
            return "redirect:/superhero/superhero";
        }

        return "redirect:/superhero/superhero";
    }

    //DISPLAYS EDIT PAGE WITH INFO
    @RequestMapping(value = "/superhero/displayEditSuperhero", method = RequestMethod.GET)
    public String displayEditSuperhero(HttpServletRequest request, Model model) {
        String superheroIdParameter;
        int superheroId;
        if (!model.asMap().isEmpty()) {
            superheroIdParameter = model.asMap().get("superheroId").toString();
        } else {
            superheroIdParameter = request.getParameter("superheroId");
        }

        superheroId = Integer.parseInt(superheroIdParameter);
        Superhero ourSuper = new Superhero();

        ourSuper = superService.getSuperheroById(superheroId);
        model.addAttribute("superhero", ourSuper);
        if (model.asMap().size() > 1) {
            model.asMap().get("errorMsg");
            return "/superhero/editSuperheroError";
        }
        return "/superhero/editSuperhero";
    }

    //UPDATES(edits) superhero information....
    @RequestMapping(value = "/superhero/editSuperhero", method = RequestMethod.POST)
    public String editSuperhero(HttpServletRequest request,
            @ModelAttribute("superhero") Superhero superhero,
            BindingResult result, RedirectAttributes ra)
            throws SuperheroAlreadyExistsException {
        try {
            superService.updateSuperhero(superhero);
        } catch (SuperheroAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "A superhero by this name already exists");
            ra.addFlashAttribute("superheroId", superhero.getSuperheroId());
            return "redirect:/superhero/displayEditSuperhero";
        }
        return "redirect:/superhero/superhero";
    }

    //DISPLAYS NEW SUPERHERO FORM FOR CREATION
    @RequestMapping(value = "/superhero/displayCreateSuperheroForm", method = RequestMethod.GET)
    public String createNewSuperhero(Model model) {
        List<Organization> orgList = orgService.getAllOrganizations();
        List<Location> locList = locationService.getAllLocations();

        model.addAttribute("locList", locList);
        model.addAttribute("orgList", orgList);
        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/superhero/createSuperheroError";
        }
        return "/superhero/createSuperhero";
    }

    //CREATES THE SUPERHERO, ORGANIZATION, ORG-LINK, & LOCATION
    @RequestMapping(value = "/superhero/createSuperhero", method = RequestMethod.POST)
    public String createSuperhero(HttpServletRequest request, RedirectAttributes ra)
            throws LocationExistsInOrganizationException,
            LocationAlreadyExistsException,
            SuperheroLinkExistsInSuperOrganizationException,
            OrganizationNameAlreadyExistsException,
            SuperheroAlreadyExistsException {
        Superhero newSuper = new Superhero();
        Organization org = new Organization();
        Location loc = new Location();
        org.setLocation(loc);
        try {

            String orgIdParameter = request.getParameter("organization-by-name");
            newSuper.setSuperheroName(request.getParameter("superheroName"));
            newSuper.setSuperheroDescription(request.getParameter("superheroDescription"));
            newSuper.setSuperpower(request.getParameter("superpower"));

            if (orgIdParameter.equals("")) {

                org.setOrganizationName(request.getParameter("organizationName"));
                org.setOrganizationDescription(request.getParameter("organizationDescription"));
                org.setOrganizationContact(request.getParameter("organizationContact"));

                String locIdParameter = request.getParameter("location-by-name");

                if (locIdParameter.equals("")) {

                    loc.setLocationName(request.getParameter("locationName"));
                    loc.setLocationDescription(request.getParameter("locationDescription"));
                    loc.setAddress(request.getParameter("address"));
                    loc.setCity(request.getParameter("city"));
                    loc.setState(request.getParameter("state"));
                    loc.setZip(request.getParameter("zip"));
                    loc.setLatitude(Double.parseDouble(request.getParameter("latitude")));
                    loc.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                    loc = locationService.addLocation(loc);
                    org.setLocation(loc);
                    org = orgService.addOrganization(org);

                } else {
                    org.getLocation().setLocationId(Integer.parseInt(locIdParameter));
                    org = orgService.addOrganization(org);
                }

            } else {
                org.setOrganizationId(Integer.parseInt(orgIdParameter));
            }

            superService.addSuperhero(newSuper);
            superOrgService.addSuperheroOrganizations(newSuper, org);

        } catch (SuperheroAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO, AT LEAST IN NAME, ALREADY EXISTS!");
            return "redirect:displayCreateSuperheroForm";
        } catch (SuperheroLinkExistsInSuperOrganizationException e) {
            ra.addFlashAttribute("errorMsg", "SUPERHERO IS ALREADY ATTACHED TO THIS ORGANIZATION");
            return "redirect:displayCreateSuperheroForm";
        } catch (LocationExistsInOrganizationException e) {
            ra.addFlashAttribute("errorMsg", "LOCATION EXISTS AS AN ORGANIZATION HEADQUARTERS ALREADY");
            return "redirect:displayCreateSuperheroForm";
        } catch (LocationAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "LOCATION ALREADY EXSITS, NO NEED TO DUPLICATE");
            return "redirect:displayCreateSuperheroForm";
        }

        return "redirect:/superhero/superhero";
    }
}
