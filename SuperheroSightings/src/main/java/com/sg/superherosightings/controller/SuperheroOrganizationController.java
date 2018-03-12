/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhero;
import com.sg.superherosightings.model.SuperheroOrganizations;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SuperheroLinkExistsInSuperOrganizationException;
import com.sg.superherosightings.service.SuperheroOrganizationService;
import com.sg.superherosightings.service.SuperheroService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author jswan
 */
@Controller

public class SuperheroOrganizationController {

    SuperheroOrganizationService superOrgService;
    OrganizationService orgService;
    SuperheroService superService;

    @Inject
    public SuperheroOrganizationController(SuperheroOrganizationService superOrgService,
            OrganizationService orgService, SuperheroService superService) {
        this.orgService = orgService;
        this.superOrgService = superOrgService;
        this.superService = superService;
    }

    @RequestMapping(value = "/superheroOrganization/superheroOrganization", method = RequestMethod.GET)
    public String superheroOrganizationPage(Model model) {

        List<SuperheroOrganizations> superOrgList = superOrgService.getAllSuperheroOrganizations();

        model.addAttribute("superOrgList", superOrgList);

        return "/superheroOrganization/superheroOrganization";
    }

    @RequestMapping(value = "/superheroOrganization/deleteSuperheroOrganization", method = RequestMethod.GET)
    public String deleteSuperheroOrganization(HttpServletRequest request) {
        String orgIdParameter = request.getParameter("organizationId");
        String superIdParameter = request.getParameter("superheroId");

        SuperheroOrganizations ourSuperOrg = new SuperheroOrganizations();
        ourSuperOrg.getOrganization().setOrganizationId(Integer.parseInt(orgIdParameter));
        ourSuperOrg.getSuperhero().setSuperheroId(Integer.parseInt(superIdParameter));

        superOrgService.deleteSuperheroOrganizations(ourSuperOrg);
        return "redirect:/superheroOrganization/superheroOrganization";

    }

    //Details showing Superhero Organization Affiliation 
    @RequestMapping(value = "/superheroOrganization/displaySuperheroOrganizationDetails", method = RequestMethod.GET)
    public String displaySuperheroOrganizationDetails(HttpServletRequest request, Model model) {
        String orgIdParameter = request.getParameter("organizationId");
        String superIdParameter = request.getParameter("superheroId");

        SuperheroOrganizations ourSuperOrg = new SuperheroOrganizations();

        ourSuperOrg.getOrganization().setOrganizationId(Integer.parseInt(orgIdParameter));
        ourSuperOrg.getSuperhero().setSuperheroId(Integer.parseInt(superIdParameter));
        ourSuperOrg = superOrgService.getSuperOrgBySuperIdAndOrgId(ourSuperOrg);

        model.addAttribute("superheroOrganization", ourSuperOrg);
        return "/superheroOrganization/superheroOrganizationDetails";
    }

//create new Superhero Organization Affiliation
    @RequestMapping(value = "/superheroOrganization/displayCreateSuperheroOrganizationForm", method = RequestMethod.GET)
    public String createNewSuperheroOrganization(Model model) {

        List<Organization> orgList = orgService.getAllOrganizations();
        model.addAttribute("orgList", orgList);

        List<Superhero> superList = superService.getAllSuperheros();
        model.addAttribute("superList", superList);

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/superheroOrganization/createSuperheroOrganizationError";
        }
        return "/superheroOrganization/createSuperheroOrganization";
    }

    @RequestMapping(value = "/superheroOrganization/createSuperheroOrganization", method = RequestMethod.POST)
    public String createSuperheroOrganization(HttpServletRequest request, RedirectAttributes ra) throws SuperheroLinkExistsInSuperOrganizationException {
        String superIdParameter = request.getParameter("superhero-by-name");
        String orgIdParameter = request.getParameter("organization-by-name");

        try {
            SuperheroOrganizations newSuperOrg = new SuperheroOrganizations();
            newSuperOrg.getSuperhero().setSuperheroId(Integer.parseInt(superIdParameter));
            newSuperOrg.getOrganization().setOrganizationId(Integer.parseInt(orgIdParameter));

            superOrgService.addSuperheroOrganizations(newSuperOrg.getSuperhero(), newSuperOrg.getOrganization());
        } catch (SuperheroLinkExistsInSuperOrganizationException e) {
            ra.addFlashAttribute("errorMsg", "Superhero Organization Affiliation Already Exists");
            return "redirect:displayCreateSuperheroOrganizationForm";
        }
        return "redirect:/superheroOrganization/superheroOrganization";
    }

}
