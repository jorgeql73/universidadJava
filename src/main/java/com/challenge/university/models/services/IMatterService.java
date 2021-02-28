
package com.challenge.university.models.services;

import com.challenge.university.models.entity.Matter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMatterService {
       
    public List<Matter> findAll();
    
    public Matter findById(Long id);
    
    public Matter save(Matter matter);
    
    public void delete(Long id);
    
    public Page<Matter> getAll(Pageable pageable);

    
    
}
