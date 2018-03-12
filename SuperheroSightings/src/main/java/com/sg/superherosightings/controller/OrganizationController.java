/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.service.LocationAlreadyExistsException;
import com.sg.superherosightings.service.LocationExistsInOrganizationException;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationLinksExistsInSuperOrgException;
import com.sg.superherosightings.service.OrganizationNameAlreadyExistsException;
import com.sg.superherosightings.service.OrganizationService;
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
 * @author jswan SuperheroSightings Organization Controller
 */
@Controller

public class OrganizationController {

    OrganizationService orgService;
    LocationService locationService;

    @Inject
    public OrganizationController(OrganizationService orgService, LocationService locationService) {
        this.orgService = orgService;
        this.locationService = locationService;
    }

    //MAIN ORGANIZATION PAGE LISTING ALL ORGANIZATIONS
    @RequestMapping(value = {"/organization/organization"}, method = RequestMethod.GET)
    public String organizationPage(Model model) {

        List<Organization> orgList = orgService.getAllOrganizations();
        model.addAttribute("orgList", orgList);

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/organization/organizationDeleteError";
        }
        return "/organization/organization";
    }

    //DELETE ORGANIZATION
    @RequestMapping(value = "/organization/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request, RedirectAttributes ra)
            throws OrganizationLinksExistsInSuperOrgException {

        String orgIdParameter = request.getParameter("organizationId");
        Organization ourOrg = new Organization();
        ourOrg.setOrganizationId(Integer.parseInt(orgIdParameter));
        try {
            orgService.deleteOrganization(ourOrg);

        } catch (OrganizationLinksExistsInSuperOrgException e) {
            ra.addFlashAttribute("errorMsg", "ORGANIZATION LINK EXSISTS WITH A SUPERHERO MUST REMOVE THIS LINK.");
            return "redirect:/organization/organization";
        }
        return "redirect:/organization/organization";
    }

    //ORGANIZATION DISPLAY INFO PAGE
    @RequestMapping(value = "/organization/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String orgIdParameter = request.getParameter("organizationId");
        Organization ourOrg = new Organization();
        ourOrg.setOrganizationId(Integer.parseInt(orgIdParameter));
        ourOrg = orgService.getOrganizationById(ourOrg.getOrganizationId());
        model.addAttribute("organization", ourOrg);
        return "/organization/organizationDetails";
    }

    //DISPLAYS NEW ORGANIATION FORM FOR CREATION
    @RequestMapping(value = "/organization/displayCreateOrganizationForm", method = RequestMethod.GET)
    public String createNewOrganization(Model model) {

        List<Location> locList = locationService.getAllLocations();
        model.addAttribute("locList", locList);

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/organization/createOrganizationError";
        }
        return "/organization/createOrganization";
    }

    //CREATE NEW ORGANIZATION AND LOCATION IF NECESSARY
    @RequestMapping(value = "/organization/createOrganization", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request, RedirectAttributes ra)
            throws LocationExistsInOrganizationException, LocationAlreadyExistsException,
            OrganizationNameAlreadyExistsException {
        try {
            Organization organization = new Organization();
            organization.setOrganizationName(request.getParameter("organizationName"));
            organization.setOrganizationDescription(request.getParameter("organizationDescription"));
            organization.setOrganizationContact(request.getParameter("organizationContact"));
            Location loc = new Location();

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
                organization.setLocation(loc);
            } else {
                loc.setLocationId(Integer.parseInt(locIdParameter));
                organization.setLocation(locationService.getLocationById(loc));
            }

            orgService.addOrganization(organization);

        } catch (OrganizationNameAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "Organization Name Already Exists");
            return "redirect:displayCreateOrganizationForm";

        } catch (LocationAlreadyExistsException ex) {
            ra.addFlashAttribute("errorMsg", "Location Already Exists");
            return "redirect:displayCreateOrganizationForm";

        } catch (LocationExistsInOrganizationException exc) {
            ra.addFlashAttribute("errorMsg", "Organization Affiliated with your Location Choice");
            return "redirect:displayCreateOrganizationForm";
        }
        return "redirect:/organization/organization";
    }

    //DISPLAYS EDIT PAGE WITH INFO: "pre-inserted"
    @RequestMapping(value = "/organization/displayEditOrganization", method = RequestMethod.GET)
    public String displayEditOrganization(HttpServletRequest request, Model model) {

        String organizationIdParameter;
        if (!model.asMap().isEmpty()) {
            organizationIdParameter = model.asMap().get("organizationId").toString();
        } else {
            organizationIdParameter = request.getParameter("organizationId");
        }

        int organizationId = Integer.parseInt(organizationIdParameter);
        Organization organization = new Organization();
        organization.setOrganizationId(organizationId);
        organization = orgService.getOrganizationById(organizationId);
        List<Location> locList = locationService.getAllLocations();

        model.addAttribute("locList", locList);
        model.addAttribute("organization", organization);
        model.addAttribute("location", organization.getLocation());

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/organization/editOrganizationError";
        }
        return "/organization/editOrganization";
    }

    @RequestMapping(value = "/organization/editOrganization", method = RequestMethod.POST)
    public String editOrganization(HttpServletRequest request,
            @ModelAttribute("organization") Organization organization,
            @ModelAttribute("location") Location location,
            BindingResult result, RedirectAttributes ra) throws LocationAlreadyExistsException,
            OrganizationNameAlreadyExistsException,
            LocationExistsInOrganizationException {

        String locIdParameter = request.getParameter("location-by-name");
//        try {
        if (!locIdParameter.equals("")) {

            location.setLocationId(Integer.parseInt(locIdParameter));
            location = locationService.getLocationById(location);
            organization.setLocation(location);

        } else {
            location = locationService.getLocationById(organization.getLocation());
            organization.setLocation(location);
        }

        try {
            orgService.updateOrganization(organization);

        } catch (LocationExistsInOrganizationException e) {
            ra.addFlashAttribute("errorMsg", "Location is Already affiliated with an Organization");
            ra.addFlashAttribute("organizationId", organization.getOrganizationId());
            return "redirect:/organization/displayEditOrganization";

        } catch (OrganizationNameAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "ORGANIZATION NAME ALREADY EXISTS!!!");
            ra.addFlashAttribute("organizationId", organization.getOrganizationId());
            return "redirect:/organization/displayEditOrganization";
        }
        return "redirect:/organization/organization";
    }

}
