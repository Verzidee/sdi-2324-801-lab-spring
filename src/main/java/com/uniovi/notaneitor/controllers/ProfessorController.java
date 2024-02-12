package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorService;
import com.uniovi.notaneitor.validators.ProfessorAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorAddValidator professorAddValidator;

    public ProfessorController(ProfessorService professorService, ProfessorAddValidator professorAddValidator) {
        this.professorService = professorService;
        this.professorAddValidator = professorAddValidator;
    }

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorList",professorService.getProfessors());
        return "professor/list";
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@Validated Professor professor, BindingResult result,Model model) {
        professorAddValidator.validate(professor,result);
        if(result.hasErrors()) {
            model.addAttribute("professor",professor);
            return "/professor/add";
        }
        professorService.addProfessor(professor);
        return "redirect:/professor/list";

    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.GET)
    public String getProfessor(Model model) {
        model.addAttribute("professor",new Professor());
        return "/professor/add";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor",professorService.getProfessor(id));
        return "/professor/details";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return "redirect:/professor/list";
    }

    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id){
        professor.setId(id);
        professorService.addProfessor(professor);
        return "redirect:/professor/details/"+id;
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor",professorService.getProfessor(id));
        return "professor/edit";
    }
}
