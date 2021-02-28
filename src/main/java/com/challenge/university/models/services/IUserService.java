
package com.challenge.university.models.services;
import com.challenge.university.models.entity.Usuario;
import java.util.List;


public interface IUserService {
        
    public List<Usuario> findAll();
    
    public Usuario findById(Long id);
    
    public Usuario save(Usuario student);
    
    public void delete(Long id);
}
