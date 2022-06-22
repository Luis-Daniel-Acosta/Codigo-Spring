package com.FormacionSpring.APIRest.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.FormacionSpring.APIRest.entity.Cliente;
import com.FormacionSpring.APIRest.respository.ClienteDao;

@Service
public class ClienteServiceImpl implements ClienteService{ 
	
	  @Autowired
	    private ClienteDao clienteDao;

	    @Override
	    @Transactional(readOnly = true)
	    public List<Cliente> mostrarTodos() {

	        return (List<Cliente>) clienteDao.findAll();
	    }

		@Override
		@Transactional(readOnly = true)
		public Cliente mostrarporId(long id) {
			// TODO Auto-generated method stub
			return clienteDao.findById(id).orElse(null);
		}

		@Override
		@Transactional
		public Cliente guardar(Cliente cliente) {
			// TODO Auto-generated method stub
			return clienteDao.save(cliente);
		}

		@Override
		@Transactional
		public void borrar(long id) {
			// TODO Auto-generated method stub
			clienteDao.deleteById(id);
		}

		

	
}
