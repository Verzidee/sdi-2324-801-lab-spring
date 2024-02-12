package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor,Long> {
    Professor findByDni(String dni);
}