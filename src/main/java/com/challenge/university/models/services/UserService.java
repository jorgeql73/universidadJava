
package com.challenge.university.models.services;

import com.challenge.university.models.dao.IUserDao;
import com.challenge.university.models.entity.Role;
import com.challenge.university.models.entity.Usuario;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserService implements UserDetailsService{



    @Autowired
    private IUserDao userDao;
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
    
        }
        
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        for(Role rol: user.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }
        return new User(user.getUsername(), user.getPassword(), roles);
    }

    
}
