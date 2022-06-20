package com.FormacionSpring.APIRest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FormacionSpring.APIRest.entity.Cliente;
import com.FormacionSpring.APIRest.service.ClienteService;


@RestController
@RequestMapping("/api")
public class ClienteRestController {

	
	@Autowired
    private ClienteService servicio;

    @GetMapping("/usuarios")
    public List<Cliente> index(){
        return servicio.mostrarTodos();
    }

    @GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable long id) {
		return servicio.mostrarporId(id);
    	
    }@PostMapping("/clientes")
    public Cliente create(@RequestBody Cliente cliente) {
		return servicio.guardar(cliente);
    }
    
    @PutMapping("/cliente/{id}")
     public Cliente update(@RequestBody Cliente cliente, @PathVariable long id) {
    	Cliente clienteupdate = servicio.mostrarporId(id);
    	clienteupdate.setNombre(cliente.getNombre());
    	clienteupdate.setApellido(cliente.getApellido());
    	clienteupdate.setEmail(cliente.getEmail());
    	clienteupdate.setTelefono(cliente.getTelefono());
    	clienteupdate.setCreateAt(cliente.getCreateAt());
    	
		return servicio.guardar(clienteupdate);
    	 	
     }
    
    /*@DeleteMapping("/cliente/{id}")
    public void delete(@PathVariable Long id) {
    	servicio.borrar(id);
    }*/
    @DeleteMapping("/cliente/{id}")
    public Cliente delete(@PathVariable Long id) {
    			Cliente clientedelete= servicio.mostrarporId(id);
    				servicio.borrar(id);
    	    		return clientedelete;
    	    		
    	    		
    }
    
   
    
    
    
}