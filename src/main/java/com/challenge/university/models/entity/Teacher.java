
package com.challenge.university.models.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "teacher")
public class Teacher implements Serializable{
        
    private static final long serialVersionUID =1L;
    @Id       
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_teacher;
    
    private int dni_teacher;
    @Column(length = 40)
    @NotEmpty(message="El nombre del profesor es obligatorio")
    private String name;
    @Column(length = 40)
    @NotEmpty(message="El apellido del profesor es obligatorio")
    private String last_name;
    private boolean active;
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Matter matter;

    public Matter getMatter() {
        return matter;
    }

    public void setMatter(Matter matter) {
        this.matter = matter;
    }

    public Long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(Long id_teacher) {
        this.id_teacher = id_teacher;
    }
    

    public int getDni_teacher() {
        return dni_teacher;
    }

    public void setDni_teacher(int dni_teacher) {
        this.dni_teacher = dni_teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
