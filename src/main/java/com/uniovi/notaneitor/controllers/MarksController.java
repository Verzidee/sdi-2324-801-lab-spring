package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.MarkAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MarksController {
    // Inyectamos el servicio por inyección basada en constructor
    private final MarksService marksService;
    private final UsersService usersService;
    private final MarkAddValidator markAddValidator;
    private final HttpSession httpSession;
    public MarksController(MarksService marksService, UsersService usersService, MarkAddValidator markAddValidator, HttpSession httpSession) {
        this.marksService = marksService;
        this.usersService = usersService;
        this.markAddValidator = markAddValidator;
        this.httpSession = httpSession;
    }
    @RequestMapping("/mark/list")
    public String getList(Model model) {
        Set<Mark> consultedList = (Set<Mark>)(httpSession.getAttribute("consultedList") != null ? httpSession.getAttribute("consultedList") : new HashSet<>());
        model.addAttribute("consultedList",consultedList);
        model.addAttribute("markList",marksService.getMarks());
        return "mark/list";
    }
    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }
    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList",usersService.getUsers());
        model.addAttribute("mark", new Mark());
        return "mark/add";
    }
    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result,Model model) {
        markAddValidator.validate(mark,result);
        if(result.hasErrors()) {
            model.addAttribute("usersList",usersService.getUsers());
            model.addAttribute("mark",mark);
            return "mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping(value="/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark",marksService.getMark(id));
        model.addAttribute("usersList",usersService.getUsers());
        return "mark/edit";
    }
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        //Modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }
    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("markList", marksService.getMarks() );
        return "mark/list :: marksTable";
    }


}






/*Vamos a hacer que getDetail sea una peticion GET hay dos formas
1-.Definimos el parámetro que va a recibir utilizando la anotación @RequestParam asociada
al parámetro Long id, esto indica que la URL va a recibir un paarametro con clave "id"
http://localhost:8090/mark/details?id=4 http://localhost:8090/mark/details?anotherParam=45&id=4

@RequestMapping("/mark/details")
    public String getDetail(@RequestParam Long id) {
        return "Getting Details =>"+ id;
    }


2-.Otra forma común de incluir parámetros GET es en el propio Path, sin tener que indicar
la clave del parámetro, sino que la clave viene dada por su posición en el Path.
http://localhost:8090/mark/details/5
@RequestMapping("/mark/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return "Getting Details =>"+ id;
    }
*/

/* Vamos a hacer que getDetail sea una peticion post
1-. -Primero vamos a indicar que la URL admite peticiones POST por defecto esta puesto que admite peticiones GET
    añadiendo al @RequestMapping "method = RequestMethod.POST"
    -En segundo lugar vamos a declarar los parametros que pueden venir contenidos en el cuerpo (Body) de la peticion
    POST, como sabemos que el body va a contener un parametro email y otro score (puntuacion) los agregamos
    @RequestMapping(value = "/mark/add",method = RequestMethod.POST)
    public String setMark(@RequestParam String description, @RequestParam String score) {
        return "Added: "+ description + "with score: " + score;
    }
2-. Pero existe otra forma más directa de recibir parámetros. Cuando todos los parámetros
corresponden a una misma entidad, podemos mapearlos directamente a un objeto.

    @RequestMapping(value = "/mark/add",method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        return "added: " + mark.getDescription()
                + " with score : " + mark.getScore()
                + " id: " + mark.getId();
    }
*/

