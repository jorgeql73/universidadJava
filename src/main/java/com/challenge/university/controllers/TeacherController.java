
package com.challenge.university.controllers;

import com.challenge.university.models.entity.Matter;
import com.challenge.university.models.entity.Teacher;
import com.challenge.university.models.services.IMatterService;
import com.challenge.university.models.services.ITeacherService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/teachers")
public class TeacherController {
            
    @Autowired
    private ITeacherService iTeacherService;
        
    @GetMapping(path="/show") 
    public List<Teacher> index(){
        return iTeacherService.findAll();
    }
    @GetMapping(path="/show/{id}") 
    public ResponseEntity<?> show(@PathVariable Long id){
        Teacher teacher = null;
        Map<String, Object> response = new HashMap<>();
        try{
            teacher = iTeacherService.findById(id);
        }
        catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
 
        }
        if(teacher == null){
            response.put("mensaje", "El profesor con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
    }
    @PostMapping(path="/Teacher")
    public ResponseEntity<?> create(@RequestBody Teacher teacher){
        
        Teacher TeacherNew = null;
        Map<String, Object> response = new HashMap<>();
        try{
            TeacherNew = iTeacherService.save(teacher);
        }
        catch(DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
 
        }
        response.put("mensaje", "El profesor fue creada con éxito");
        response.put("Teacher", TeacherNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
        
    @PutMapping("/Teacher/{id}")
    public ResponseEntity<?> update(@RequestBody Teacher teacher,@PathVariable Long id ){
        Teacher TeacherActual = iTeacherService.findById(id);
        Teacher TeacherUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(TeacherActual==null){
            response.put("mensaje", "El profesor con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            TeacherActual.setActive(teacher.isActive());
            TeacherActual.setName(teacher.getName());
            TeacherActual.setLast_name(teacher.getLast_name());
            TeacherActual.setDni_teacher(teacher.getDni_teacher());
            
            TeacherUpdated = iTeacherService.save(TeacherActual);
            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar el profesor en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor fue actualizado con éxito");
        response.put("teacher", TeacherUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/Teacher/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> Delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            iTeacherService.delete(id);
            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el prefesor en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El profesor fue eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
