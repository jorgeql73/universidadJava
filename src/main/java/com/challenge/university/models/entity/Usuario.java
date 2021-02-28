package com.challenge.university.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="user")
public class Usuario implements Serializable{

    public Usuario(String username, String password, String name, String last_name, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.last_name = last_name;
        this.roles = roles;
    }

    public Usuario() {
    }
    
    private static final long serialVersionUID =1L;
    @Id       
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private String username;
    @Column(length= 128)
    private String password;
    
    @Column(length= 40)
    private String name;
        
    @Column(length= 40)
    private String last_name;
    
    @OneToMany
    @JoinColumn(name="id_user")
    private List<Role> roles;

    public Usuario(String username, String password, ArrayList<GrantedAuthority> roles) {
        
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
