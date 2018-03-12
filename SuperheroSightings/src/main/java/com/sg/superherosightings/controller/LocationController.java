/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.service.LocationAlreadyExistsException;
import com.sg.superherosightings.service.LocationExistsInOrganizationException;
import com.sg.superherosightings.service.LocationExistsInSightingException;
import com.sg.superherosightings.service.LocationService;
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
 * @author jswan SuperheroSightings Location Controller...
 */
@Controller
public class LocationController {

    LocationService locationService;

    @Inject
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    //  LOCATION MAIN PAGE
    @RequestMapping(value = {"/location/location"}, method = RequestMethod.GET)
    public String locationPage(Model model) {
        List<Location> locationList = locationService.getAllLocations();
        model.addAttribute("locationList", locationList);

        //checks model map for key 'errorMsg' and displays the page with the error if it exists...
        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/location/deleteLocationError";
        }

        return "/location/location";
    }

    // CREATE LOCATION FORM
    @RequestMapping(value = "/location/displayCreateLocationForm", method = RequestMethod.GET)
    public String createNewLocation(Model model) {

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/location/createLocationError";
        }
        return "/location/createLocation";
    }

    // CREATE LOCATION 
    @RequestMapping(value = "/location/createLocation", method = RequestMethod.POST)
    public String createLocation(HttpServletRequest request, RedirectAttributes ra) throws LocationAlreadyExistsException {

        Location location = new Location();
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setAddress(request.getParameter("address"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        location.setZip(request.getParameter("zip"));
        location.setLatitude(Double.parseDouble(request.getParameter("latitude")));
        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));
        try {

            locationService.addLocation(location);

        } catch (LocationAlreadyExistsException e) {
            ra.addFlashAttribute("errorMsg", "Location Already Exists");
            return "redirect:/location/displayCreateLocationForm";
        }
        return "redirect:/location/location";
    }

    // DETAIL LOCATION PAGE
    @RequestMapping(value = "/location/displayLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        Location location = new Location();
        location.setLocationId(locationId);
        location = locationService.getLocationById(location);
        model.addAttribute("location", location);
        return "/location/locationDetails";
    }

    //DELETE LOCATION
    @RequestMapping(value = "/location/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request,
            RedirectAttributes ra) throws
            LocationExistsInOrganizationException,
            LocationExistsInSightingException {
        String locationIdParameter = request.getParameter("locationId");
        Location ourlocal = new Location();
        ourlocal.setLocationId(Integer.parseInt(locationIdParameter));
        try {
            locationService.deleteLocation(ourlocal);

        } catch (LocationExistsInOrganizationException e) {
            ra.addFlashAttribute("errorMsg", "Cannot Delete, Location Exists In An Organization: Must Delete Organization First!");
            return "redirect:/location/location";
        } catch (LocationExistsInSightingException ex) {
            ra.addFlashAttribute("errorMsg", "Cannot Delete, Location Exists In Sighting: Must Delete Sighting(s) Affililated with Location First");
            return "redirect:/location/location";
        }
        return "redirect:/location/location";
    }

    // EDIT LOCATION DISPLAY
    @RequestMapping(value = "/location/displayEditLocation", method = RequestMethod.GET)
    public String displayEditLocation(HttpServletRequest request, Model model) {
        String locationIdParameter;
        int locationId;
        if (!model.asMap().isEmpty()) {
            locationIdParameter = model.asMap().get("locationId").toString();
        } else {
            locationIdParameter = request.getParameter("locationId");
        }
        locationId = Integer.parseInt(locationIdParameter);
        Location location = new Location();
        location.setLocationId(locationId);
        location = locationService.getLocationById(location);
        model.addAttribute("location", location);
        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/location/editLocationError";
        }

        return "/location/editLocation";
    }

    //EDIT LOCATION 
    @RequestMapping(value = "/location/editLocation", method = RequestMethod.POST)
    public String editLocation(@ModelAttribute("location") Location location,
            BindingResult result,
            RedirectAttributes ra) throws
            LocationAlreadyExistsException {
        try {
            locationService.updateLocation(location);

        } catch (LocationAlreadyExistsException ex) {
            ra.addFlashAttribute("errorMsg", "Location already exists with attempted changes.");
            ra.addFlashAttribute("locationId", location.getLocationId());
            return "redirect:/location/displayEditLocation";
        }

        return "redirect:/location/location";
    }
}
