/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.DuplicateUserNameException;
import com.sg.superherosightings.dao.UserDao;
import com.sg.superherosightings.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author jswan
 */
@Controller
public class UserController {

    private UserDao dao;
    private PasswordEncoder encoder;

    public UserController(UserDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    // This endpoint retrieves all users from the database and puts the
    // List of users on the model
    @RequestMapping(value = "/displayUserList", method = RequestMethod.GET)
    public String displayUserList(Model model) {
        List users = dao.getAllUsers();
        model.addAttribute("users", users);

        if (model.asMap().containsKey("errorMsg")) {
            model.asMap().get("errorMsg");
            return "/displayUserList";
        }
        return "displayUserList";
    }
    // This endpoint just displays the Add User form

    @RequestMapping(value = "/displayUserForm", method = RequestMethod.GET)
    public String displayUserForm(Model model) {

        model.asMap().get("errorMsg");

        return "addUserForm";
    }
    // This endpoint processes the form data and creates a new User

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req, RedirectAttributes ra) throws DuplicateUserNameException {
        User newUser = new User();
        // This example uses a plain HTML form so we must get values 
        // from the request
        newUser.setUsername(req.getParameter("username"));
        String clearPw = req.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        newUser.setPassword(hashPw);

        newUser.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");

        if (null != req.getParameter("isUser")) {
            newUser.addAuthority("ROLE_USER");
        }
        if (null != req.getParameter("isAdmin")) {

            newUser.addAuthority("ROLE_USER");
            newUser.addAuthority("ROLE_ADMIN");
        }

        try {
            dao.addUser(newUser);
        } catch (DuplicateUserNameException e) {
            ra.addFlashAttribute("errorMsg", "USER NAME ALREADY EXISTS, CANNOT DUPLICATE");
            return "redirect:/displayUserForm";
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMsg", "Error, Error...not sure which one but here we are: " + ex.toString());
            return "redirect:/displayUserForm";
        }
        return "redirect:displayUserList";
    }
    // This endpoint deletes the specified User

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deletUser(HttpServletRequest request, Model model) {
        String userIdParameter;
        userIdParameter = request.getParameter("userId");
        int userId = Integer.parseInt(userIdParameter);

        dao.deleteUser(userId);
        return "redirect:displayUserList";
    }

    //Displays info Of User to be edited...
    @RequestMapping(value = "/displayEditUser", method = RequestMethod.GET)
    public String displayEditUser(HttpServletRequest request, RedirectAttributes ra,
            Model model) {
        String userIdParameter;

        if (!model.asMap().isEmpty()) {
            userIdParameter = model.asMap().get("userId").toString();
        } else {
            userIdParameter = request.getParameter("userId");
        }

        int userId = Integer.parseInt(userIdParameter);
        User user = dao.getUserById(userId);

        if (user == null) {
            ra.addFlashAttribute("errorMsg", "Pick Another user, this one does not exist...");
            return "redirect:displayUserList";
        }


        model.addAttribute("user", user);
        model.asMap().get("errorMsg");

        return "/editUser";
    }

    //Edit User
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request,
            @ModelAttribute("user") User user,
            BindingResult result,
            RedirectAttributes ra) throws DuplicateUserNameException {

        if (!request.getParameter("editPassword").equals("")) {
            String clearPw = request.getParameter("editPassword");
            String hashPw = encoder.encode(clearPw);
            user.setPassword(hashPw);

        }
        if (null != request.getParameter("editIsUser")) {
            dao.deleteAuthorityList(user);
            user.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
            user.addAuthority("ROLE_USER");
        }
        if (null != request.getParameter("editIsAdmin")) {
            dao.deleteAuthorityList(user);
            user.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
            user.addAuthority("ROLE_USER");
            user.addAuthority("ROLE_ADMIN");
        }
        if (null != request.getParameter("disable")) {
            dao.deleteAuthorityList(user);
            user.addAuthority("IS_AUTHENTICATED_ANONYMOUSLY");
        }
        try {
            dao.updateUser(user);
        } catch (DuplicateUserNameException e) {
            ra.addFlashAttribute("errorMsg", "USER NAME ALREADY EXISTS, CANNOT DUPLICATE");
            ra.addFlashAttribute("userId", user.getId());
            return "redirect:/displayEditUser";
        }

        return "redirect:/displayUserList";
    }
}
