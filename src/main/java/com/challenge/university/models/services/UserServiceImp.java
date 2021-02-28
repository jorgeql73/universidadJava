
package com.challenge.university.models.services;
import com.challenge.university.models.dao.IUserDao;
import com.challenge.university.models.entity.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements IUserService{

    @Autowired
    private IUserDao userDao;
    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) userDao.findAll();
    }

    @Override
    public Usuario findById(Long id) {

        return userDao.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario user) {
        return userDao.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.existsById(id);

    }

    

  
    
}
