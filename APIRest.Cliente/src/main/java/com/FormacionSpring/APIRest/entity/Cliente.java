package com.FormacionSpring.APIRest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="clientes")
public class Cliente implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="nombre")
	private String nombre;
	private String apellido;
	private String email;
	private int telefono;
	@Column(name= "create_At")
	private Date createAt;
	
	
	public long getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getEmail() {
		return email;
	}
	public int getTelefono() {
		return telefono;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5453610773681156707L;
	
}
