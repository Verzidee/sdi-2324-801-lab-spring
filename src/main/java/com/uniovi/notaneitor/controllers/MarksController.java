package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MarksController {
    @Autowired
    private MarksService marksService;
    @RequestMapping("/mark/list")
    public String getList(Model model) {
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
    public String getMark() {
        return "mark/add";
    }
    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping(value="/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark",marksService.getMark(id));
        return "mark/edit";
    }
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        mark.setId(id);
        marksService.addMark(mark);
        return "redirect:/mark/details/"+id;
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

