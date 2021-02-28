
package com.challenge.university.controllers;

import com.challenge.university.models.dao.IUserDao;
import com.challenge.university.models.entity.Matter;
import com.challenge.university.models.entity.Teacher;
import com.challenge.university.models.entity.Usuario;
import com.challenge.university.models.services.IMatterService;
import com.challenge.university.models.services.ITeacherService;
import com.challenge.university.models.services.IUserService;
import com.challenge.university.utils.RenderPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MatterController {
        
    @Autowired
    private IMatterService iMatterService;

    @Autowired
    private IUserService iUserService;
         
    @Autowired
    private ITeacherService iTeacherService;
    @Autowired
    private IUserDao iUserDao;
   
   
    
    @GetMapping(path="/") 
    public String index(Model model){
        return "index";
        
    }
   /* @GetMapping(path="inscriptions") 
    public String inscriptions(@RequestParam Map<String, Object> params, Model model){
        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Matter> pageMatter = iMatterService.getAll(pageRequest);
        int totalPage = pageMatter.getTotalPages();
        if(totalPage > 0){
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());// combertimos los resulados en una lista de numeros para la paginación
            model.addAttribute("pages", pages);

        }
        model.addAttribute("list", pageMatter.getContent());
        List<Matter> matters = iMatterService.findAll();
        model.addAttribute("matters", matters);
        return "inscriptions";
        
    }*/
    @GetMapping(path="inscriptions") 
    public String inscriptions(@RequestParam(name="page", defaultValue = "0") int page, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String name = auth.getName();
        Usuario u = iUserDao.findByUsername(name);
        Pageable matterPegeable = PageRequest.of(page, 6);
        Page<Matter> matterPag = iMatterService.getAll(matterPegeable);
        RenderPage<Matter> renderPag = new RenderPage<Matter>("/inscriptions", matterPag);
        List<Matter> mats = iMatterService.findAll();
        List<Long> inscripciones = new ArrayList();
        for(Matter m : mats){
            for(Usuario student : m.getStudents()){
                if(student.getId_user()== u.getId_user()){
                    inscripciones.add(m.getId_matter());
                }
            }
        }
        
        
        model.addAttribute("inscripciones", inscripciones);
        model.addAttribute("page", renderPag);
        model.addAttribute("matters", matterPag);

        return "inscriptions";
   
        
    }
    
   /* public void students(){
       List<MatterUser> mattersUser = iMatterUserService.findByMatterIdMatter(id);
       int cantstudents = mattersUser.size();
       model.addAttribute(cantstudents);
       
    }*/
    Matter m = null;
    @GetMapping("teacher/{id_matter}") 
    public String teachers(Model model, Matter matter){
        List<Teacher> teachers = iTeacherService.findAll();
        m = iMatterService.findById(matter.getId_matter());
        model.addAttribute("teachers", teachers);
        //model.addAttribute("matter", m);
        return "teachers";
        
    }
    @GetMapping("description/{id_matter}") 
    public String description(Model model, Matter matter){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String name = auth.getName();
        Usuario u = iUserDao.findByUsername(name);
        m = iMatterService.findById(matter.getId_matter());
        
        List<Long> inscripciones = new ArrayList();
        
        for(Usuario student : m.getStudents()){
            if(student.getId_user()== u.getId_user()){
                inscripciones.add(m.getId_matter());
            }
        }
        
        model.addAttribute("inscripciones", inscripciones);
        model.addAttribute("matter", m);
        //model.addAttribute("matter", m);
        return "description";
        
    }
    @GetMapping("formCreate") 
    public String formCreate(Matter matter){
        return "create";
        
    }
    @GetMapping("teacher/formCreateTeacher") 
    public String formCreate(Teacher teacher){
        return "createTeacher";
        
    }
    @GetMapping("delete/{id_matter}") 
    public String delete(Matter matter, Model model){
        iMatterService.delete(matter.getId_matter());
        //model.addAttribute(matter);
        return "redirect:/inscriptions";
        
    }
    @GetMapping("edit/{id_matter}") 
    public String edit(Matter matter, Model model){
        matter = iMatterService.findById(matter.getId_matter());
        model.addAttribute(matter);
        return "edit";
        
    }
    @GetMapping(path="/show/{id}") 
    public ResponseEntity<?> show(@PathVariable Long id){
        Matter matter = null;
        Map<String, Object> response = new HashMap<>();
        try{
            matter = iMatterService.findById(id);
        }
        catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
 
        }
        if(matter == null){
            response.put("mensaje", "La materia con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Matter>(matter, HttpStatus.OK);
    }
    @PostMapping("create")
    public String create(@Valid Matter matter, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("Hubo errores en el formulario");
             return "create";
        }
        iMatterService.save(matter);
        
        return "redirect:/inscriptions";

        
    }
    @PostMapping("teacher/createTeacher")
    public String create(@Valid Teacher teacher, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Hubo errores en el formulario");
             return "createTeacher";
        }
        teacher.setActive(false);
        iTeacherService.save(teacher);
        return "redirect:/inscriptions";

        
    }
    @PostMapping("edit/modificar/{id_matter}")
    public String modificar(@Valid Matter matter, BindingResult result, Model model){
        if(result.hasErrors()){
            matter = iMatterService.findById(matter.getId_matter());
            model.addAttribute(matter);
            System.out.println("Hubo errores en el formulario");
             return "edit";
        }
        Matter matterActual = iMatterService.findById(matter.getId_matter());
        Matter matterUpdated = null;
        Teacher teacher = null;
        Teacher teacherUpdated = null;
                    
        matterActual.setName(matter.getName());
        matterActual.setSchedule(matter.getSchedule());
        matterActual.setDescription(matter.getDescription());
        matterActual.setQuota(matter.getQuota());
        if(matter.getTeacher()!= null){
            teacher = matterActual.getTeacher();
            teacherUpdated = iTeacherService.findById(teacher.getId_teacher());
            teacherUpdated.setActive(false);
            iTeacherService.save(teacherUpdated);
            
        }
        matterActual.setTeacher(matter.getTeacher());
        matterUpdated = iMatterService.save(matterActual);
      
        return "redirect:/inscriptions";
    }
    @PostMapping("teacher/add/{id_teacher}")
    public String addTeacher(Teacher teacher){
        Matter matterActual = iMatterService.findById(m.getId_matter());
        
        Matter matterUpdated = null;
        Teacher teacherUpdated = iTeacherService.findById(teacher.getId_teacher());
        Teacher teacherUpdated1 = null;
        if(teacher.isActive()){
           return "redirect:/inscriptions"; 
        }
        else{
            teacherUpdated.setActive(true);
            teacherUpdated1 = iTeacherService.save(teacherUpdated);
            matterActual.setTeacher(iTeacherService.findById(teacherUpdated1.getId_teacher()));

            matterUpdated = iMatterService.save(matterActual);

            return "redirect:/inscriptions"; 
        }     
        
    }
    @PostMapping("description/add")
    public String addStudent(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String name = auth.getName();
        Usuario u = iUserDao.findByUsername(name);
        Matter matterActual = iMatterService.findById(m.getId_matter());       
        Matter matterUpdated = null;
        Usuario studentUpdated = iUserService.findById(u.getId_user());
        Usuario studentUpdated1 = null;
        
        try{
            
            if(matterActual.getQuota()== matterActual.getStudents().size()){
                //retornaremos un mensaje de cupo lleno
                return "404"; 
            }
            else{ 
                matterActual.addStudent(iUserService.findById(studentUpdated.getId_user()));
                //studentUpdated1 = iUserService.save(studentUpdated);
                matterUpdated = iMatterService.save(matterActual);
                return "redirect:/inscriptions";
            }
           
        }catch(DataAccessException e){
            return "error";
        }


        }  
        
    
    /*@PutMapping("edit/matter/{id}")
    public ResponseEntity<?> update(@RequestBody Matter matter,@PathVariable Long id){
        Matter matterActual = iMatterService.findById(id);
        Matter matterUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(matterActual==null){
            response.put("mensaje", "La materia con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            
            matterActual.setName(matter.getName());
            matterActual.setSchedule(matter.getSchedule());
            matterActual.setDescription(matter.getDescription());
            matterActual.setQuota(matter.getQuota());

            matterUpdated = iMatterService.save(matterActual);
            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar la materia en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La materia fue actualizado con éxito");
        response.put("matter", matterUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }*/
        
    @PutMapping("/matterTeacher/{id}/{id_teacher}")
    public ResponseEntity<?> update(@RequestBody Matter matter,@PathVariable Long id, @PathVariable Long id_teacher, Model model){
        Matter matterActual = iMatterService.findById(id);
        Matter matterUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(matterActual==null){
            response.put("mensaje", "La materia con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            
            matterActual.setTeacher(iTeacherService.findById(id_teacher));

            matterUpdated = iMatterService.save(matterActual);
            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al asignar el profesor");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor con éxito");
        response.put("matter", matterUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
        
    /*@PutMapping("/matter/{id}/{id_student}")
    public ResponseEntity<?> addStudent(@RequestBody Matter matter,@PathVariable Long id, @PathVariable Long id_user){
        Matter matterActual = iMatterService.findById(id);
        Matter matterUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(matterActual==null){
            response.put("mensaje", "La materia con el id: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            
            if(matterActual.getQuota()== matterActual.getStudents().size()){
                response.put("mensaje", "Cupo excedido ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.LOCKED); 
            }
            else{
                
                Usuario student = iUserService.findById(id_user);   
                matterActual.addStudent(student);
                matterUpdated = iMatterService.save(matterActual);
            }

            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al inscribir el estudiante en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El estudiante fue inscripto con éxito");
        response.put("matter", matterUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }



    @DeleteMapping("matter/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String Delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            iMatterService.delete(id);
            
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar la materia en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage())); 
            new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return "materia no encontrada";
        }
        response.put("mensaje", "La materia fue eliminado con éxito");
        new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        return "redirect:/index";
    }*/

