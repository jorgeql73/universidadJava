
package com.challenge.university.models.services;

import com.challenge.university.models.dao.IMatterDao;
import com.challenge.university.models.entity.Matter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatterServiceImp implements IMatterService{

    @Autowired
    private IMatterDao iMatterDao;

    @Override
    public List<Matter> findAll() {
        return (List<Matter>) iMatterDao.findAll();
    }

    @Override
    public Matter findById(Long id) {
        return iMatterDao.findById(id).orElse(null);
    }

    @Override
    public Matter save(Matter matter) {
        
        return iMatterDao.save(matter);
    }

    @Override
    public void delete(Long id) {
        iMatterDao.deleteById(id);
    }

    /*@Override
    public Student_Matter addStudent(Matter matter, Student student) {
        Student_Matter studentMatter = null;
        if(matter.getStudents() == null){
           List<Student> students = new ArrayList<>();
           students.add(student);
           matter.setStudents(students);
        }
        else{
            List<Student> students= matter.getStudents();
            if(students.size()< matter.getQuota()){
                students.add(student);
                studentMatter = new Student_Matter();
                studentMatter.setDni_Student(student.getDni_student());
                studentMatter.setId_matter(matter.getId_matter());
                iStudentMatterDao.save(studentMatter);
            }//falta trabajar el error cuando se pasa de el cupo
            matter.setStudents(students);
        }
        return studentMatter;
    }*/

    @Override
    public Page<Matter> getAll(Pageable pageable) {
        return iMatterDao.findAll(pageable);
    }


   
    
}
