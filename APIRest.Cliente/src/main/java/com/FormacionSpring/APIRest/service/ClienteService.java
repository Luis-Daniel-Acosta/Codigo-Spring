package com.FormacionSpring.APIRest.service;

import java.util.List;

import com.FormacionSpring.APIRest.entity.Cliente;

public interface ClienteService {
	
	//metodo para mostar todos los clientes
	 public List<Cliente> mostrarTodos();
	 
	//metodo para mostar un cliente por Id
	 public Cliente mostrarporId(long id);
	 
	//metodo para guardar un cliente
	 public Cliente guardar(Cliente cliente);
	 
	//metodo para Borrar un cliente
	 public void borrar(long id);
	 
	 
	 
	 
}
