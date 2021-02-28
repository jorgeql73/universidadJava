
package com.challenge.university.models.dao;

import com.challenge.university.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserDao extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
}
