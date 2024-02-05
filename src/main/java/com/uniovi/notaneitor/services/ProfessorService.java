package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    private List<Professor> professorList = new LinkedList<>();
    @PostConstruct
    public void init() {
        professorList.add(new Professor(1L,"70027988Z","Alberto","Fernandez","Profesor Titular"));
        professorList.add(new Professor(2L,"93027988Z","Mariano","Garcia","Catedratico"));
    }
    public List<Professor> getProfessors() {
        return professorList;
    }
    public Professor getProfessor(Long id) {
        return professorList.stream().
                filter(professor -> professor.getId().equals(id)).findFirst().get();
    }
    public void addProfessor(Professor professor) {
        //Si en ID es null le asignamos el ultimo +1 de la lista
        if(professor.getId() == null) {
            professor.setId(professorList.get(professorList.size()-1).getId()+1);
        }
        professorList.add(professor);
    }
    public void deleteProfessor(Long id) {
        professorList.removeIf(professor -> professor.getId().equals(id));
    }
}
