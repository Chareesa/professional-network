package com.chareesa.exam3.coontrollers;

import com.chareesa.exam3.models.User;
import com.chareesa.exam3.services.UserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private final UserService userService;

    public MainController(UserService userService) {
        super();
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(HttpSession session, @ModelAttribute("user") User user) {
        Boolean isIncorrect = (Boolean) session.getAttribute("isIncorrect");
        Boolean loginFailed = (Boolean) session.getAttribute("loginFailed");

        if (isIncorrect == null || isIncorrect == true) {
            session.setAttribute("isIncorrect", false);
        }
        if (loginFailed == null || loginFailed == true) {
            session.setAttribute("loginFailed", false);
        }

        return "index";
    }

    @PostMapping("/register/user")
    public String user_handler(HttpSession session,
                               @RequestParam("c_password") String c_password,
                               @Valid @ModelAttribute("user") User user,
                               BindingResult result) {
        //Did result return any errors?
        if (result.hasErrors()) {
            session.setAttribute("isIncorrect", true);
            session.setAttribute("message", "Sorry, there was an error registering.. " +
                    "please check your information and try again. ");
            return "index";
        }

        //Is there a password? Does password == confirm password?
        if (user.getPassword() == null || !user.getPassword().equals(c_password)) {
            session.setAttribute("isIncorrect", true);
            session.setAttribute("message", "Passwords don't match. ");
            return "index";
        }

        final Long userId = userService.saveUser(user);

        //Does ID have an error?
        if (userId == -1L) {
            session.setAttribute("isIncorrect", true);
            session.setAttribute("message", "User with that email already exists. ");
            return "index";
        }

        session.setAttribute("user", user);
        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login/user")
    public String userLogin(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session) {
        final User user = userService.findUser(email);
        if (userService.login(user, password, session)) {//sends info to service to check if it exists in database
            session.setAttribute("user", user);
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String index2(HttpSession session, @ModelAttribute("user") User user) {
        session.setAttribute("loginFailed", true);
        session.setAttribute("loginMessage", "Log in failed. ");
        return "index";
    }
}