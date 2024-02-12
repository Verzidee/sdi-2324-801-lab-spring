package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.SecurityService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.SignUpFormValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {
    private final UsersService usersService;
    private final SecurityService securityService;
    private final SignUpFormValidator signUpFormValidator;

    public UsersController(UsersService usersService, SecurityService securityService, SignUpFormValidator signUpFormValidator) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;

    }

    //Obtener el listado de users
    @RequestMapping("/user/list")
    public String getListado(Model model){
        model.addAttribute("usersList",usersService.getUsers());
        return "user/list";
    }
    //Formulario para añadir usuario
    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("usersList",usersService.getUsers());
        return "user/add";
    }
    //Añade un usuario
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    //Muestra los detalles de un user
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    //Eliminar user mediante id
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    //Muestra formulario para editar user
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    //Edita el user
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/details/" + id;
    }
    //Procedimiento Register
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {

        signUpFormValidator.validate(user,result);
        if(result.hasErrors()) {
            return "signup";
        }
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(),user.getPasswordConfirm());
        return "redirect:home";
    }
    //Procedimiento login no es necesario el POST ya que esta incluido en SecurityService de Spring Security
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }
    @RequestMapping("/user/list/update")
    public String updateList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list :: usersTable";
    }

}
