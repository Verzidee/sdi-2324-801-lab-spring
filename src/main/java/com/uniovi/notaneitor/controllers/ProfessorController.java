package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @RequestMapping("/professor/list")
    public String getList() {
        return professorService.getProfessors().toString();
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute
    Professor professor) {
        professorService.addProfessor(professor);
        return professor.toString();
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.GET)
    public String getProfessor() {
        return "/professor/add";
    }
    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return professorService.getProfessor(id).toString();
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return "Deleted";
    }

    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id){
        professor.setId(id);
        professorService.addProfessor(professor);
        return "Professor edited";
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor",professorService.getProfessor(id));
        return "Teacher edited";
    }
}
