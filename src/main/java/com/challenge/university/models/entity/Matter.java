
package com.challenge.university.models.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="matter")
public class Matter implements Serializable{


    
    private static final long serialVersionUID =1L;
    @Id       
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_matter;
    @Column(length = 40)
    @NotEmpty(message="El nombre de la materia es obligatorio")
    private String name;
    
    private Time schedule;
    //private Teacher teacher;
    @Column(length = 255)
    
    private int Quota;
    @NotEmpty(message="La descripción de la materia es obligatoria")
    private String description;
    
   
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Usuario> users;
        
    public void addStudent(Usuario user){
        if(this.users == null){
            this.users = new ArrayList<>();
        }
        
        this.users.add(user);
    }
    @OneToOne
    @JoinColumn(name = "fk_teacher", referencedColumnName="id_teacher")
    private Teacher teacher; 

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Usuario> getStudents() {
        return users;
    }

    public void setStudents(List<Usuario> students) {
        this.users = students;
    }
    
    public long getId_matter() {
        return id_matter;
    }

    public void setId_matter(long id_matter) {
        this.id_matter = id_matter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getSchedule() {
        return schedule;
    }

    public void setSchedule(Time schedule) {
        this.schedule = schedule;
    }

    
    public int getQuota() {
        return Quota;
    }

    public void setQuota(int Quota) {
        this.Quota = Quota;
    }
    
}
