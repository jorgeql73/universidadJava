
package com.challenge.university.models.dao;

import com.challenge.university.models.entity.Teacher;
import org.springframework.data.repository.CrudRepository;


public interface ITeacherDao extends CrudRepository<Teacher, Long>{
    
}
