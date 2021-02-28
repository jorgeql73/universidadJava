
package com.challenge.university.models.dao;

import com.challenge.university.models.entity.Matter;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IMatterDao extends JpaRepository<Matter, Long>{
    
}
