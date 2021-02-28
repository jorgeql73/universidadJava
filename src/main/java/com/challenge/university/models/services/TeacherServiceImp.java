
package com.challenge.university.models.services;

import com.challenge.university.models.dao.ITeacherDao;
import com.challenge.university.models.entity.Teacher;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImp implements ITeacherService{

    @Autowired
    private ITeacherDao iTeacherDao;
    @Override
    public List<Teacher> findAll() {
        return (List<Teacher>) iTeacherDao.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return iTeacherDao.findById(id).orElse(null);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return iTeacherDao.save(teacher);
    }

    @Override
    public void delete(Long id) {
        iTeacherDao.deleteById(id);
    }
    
}
