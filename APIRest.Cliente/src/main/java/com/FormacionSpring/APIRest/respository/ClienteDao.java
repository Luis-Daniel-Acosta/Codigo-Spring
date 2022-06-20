package com.FormacionSpring.APIRest.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.FormacionSpring.APIRest.entity.Cliente;

@Repository	
public interface ClienteDao extends CrudRepository<Cliente, Long>{  

}