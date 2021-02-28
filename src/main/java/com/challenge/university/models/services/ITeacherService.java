
package com.challenge.university.models.services;

import com.challenge.university.models.entity.Teacher;
import java.util.List;

public interface ITeacherService {
        
    public List<Teacher> findAll();
    
    public Teacher findById(Long id);
    
    public Teacher save(Teacher teacher);
    
    public void delete(Long id);
}
