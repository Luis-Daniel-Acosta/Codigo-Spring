package com.FormacionSpring.APIRest.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    /*@GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable long id) {
		return servicio.mostrarporId(id);*/
    
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {

        Cliente cliente = null;
        Map<String,Object>  response = new HashMap<>();

        try {

            cliente = servicio.mostrarporId(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null) {
            response.put("mensaje", "El cliente con ID: "+id+" no existe en la base de datos");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
    }
    
    
    
    /*@PostMapping("/clientes")
    public Cliente create(@RequestBody Cliente cliente) {
		return servicio.guardar(cliente);
    }*/
    
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        Cliente clienteNew = null;
        Map<String,Object>  response = new HashMap<>();

        try {

            clienteNew =  servicio.guardar(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }
    
    
    /*@PutMapping("/cliente/{id}")
     public Cliente update(@RequestBody Cliente cliente, @PathVariable long id) {
    	Cliente clienteupdate = servicio.mostrarporId(id);
    	clienteupdate.setNombre(cliente.getNombre());
    	clienteupdate.setApellido(cliente.getApellido());
    	clienteupdate.setEmail(cliente.getEmail());
    	clienteupdate.setTelefono(cliente.getTelefono());
    	clienteupdate.setCreateAt(cliente.getCreateAt());
    	
		return servicio.guardar(clienteupdate);
    	 	
     }*/
    
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente
            ,@PathVariable Long id) {

        Cliente clienteUpdate =  servicio.mostrarporId(id);
        Map<String,Object>  response = new HashMap<>();


        if(clienteUpdate == null) {
            response.put("mensaje","No existe el registro con id:"+id);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {


            clienteUpdate.setNombre(cliente.getNombre());
            clienteUpdate.setApellido(cliente.getApellido());
            clienteUpdate.setEmail(cliente.getEmail());
            clienteUpdate.setTelefono(cliente.getTelefono());
            clienteUpdate.setCreateAt(cliente.getCreateAt());

            //guardo y retorno los datos actualizados
            servicio.guardar(clienteUpdate);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido actualizado con éxito");
        response.put("cliente", clienteUpdate);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }

    
    /*@DeleteMapping("/cliente/{id}")
    public void delete(@PathVariable Long id) {
    	servicio.borrar(id);
    }*/
   
    /*@DeleteMapping("/cliente/{id}")
    public Cliente delete(@PathVariable Long id) {
    			Cliente clientedelete= servicio.mostrarporId(id);
    				servicio.borrar(id);
    	    		return clientedelete;	
    }*/
    
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Cliente clienteBorrado = servicio.mostrarporId(id);
        Map<String,Object>  response = new HashMap<>();

        if(clienteBorrado == null) {
            response.put("mensaje","No existe el registro con id:"+id);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {

            servicio.borrar(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido eliminado con éxito");
        response.put("cliente", clienteBorrado);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
    
    @PostMapping("/cliente/uploads")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,
            @RequestParam("id") Long id){

        Map<String,Object>  response = new HashMap<>();
        //buscar el cliente por el id recibido
        Cliente cliente = servicio.mostrarporId(id);

        //preguntamos si el archivo es distinto de vacio
        if( !archivo.isEmpty() ) {
            //guardamos el nombre del archivo en esta variable
            String nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace("","");

            //guardamos la ruta completa uploads/nombredelaimagen lo guardamos en
            //una variable de tipo path que es de java.io

            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

            try {
                //copiamos el archivo fisico a la ruta que definimos en Path
                Files.copy(archivo.getInputStream(), rutaArchivo );
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen del cliente");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }

            //guardamos el nombre de la imagen
            cliente.setImagen(nombreArchivo);
            //registramos en base de datos
            servicio.guardar(cliente);

            response.put("cliente", cliente);
            response.put("mensaje","Imagen subida correctamente :"+nombreArchivo);

        }


        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    
    
    
    	
    	
    	
 }
    
    
