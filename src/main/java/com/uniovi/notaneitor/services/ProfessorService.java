package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    public List<Professor> getProfessors() {
        List<Professor> professorList = new ArrayList<Professor>();
        professorRepository.findAll().forEach(professorList::add);
        return professorList;
    }
    public Professor getProfessor(Long id) {
        return professorRepository.findById(id).get();
    }
    public void addProfessor(Professor professor) {
        professorRepository.save(professor);
    }
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public Professor getprofessorByDni(String dni) {
        return professorRepository.findByDni(dni);
    }
}
