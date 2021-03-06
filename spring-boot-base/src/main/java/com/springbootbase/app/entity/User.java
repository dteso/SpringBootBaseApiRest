package com.springbootbase.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users") 
public class User implements java.io.Serializable{
	
	private static final long serialVersionUID = -3659638582243741589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private Long id;
	
	@Column(length = 50)
	private String name;
	
	private String surname;
	
	@Column(name = "mail", nullable=false, length=50, unique = true ) // unique --> implica que no puede repetirse el valor de la propiedad
	private String email;
	
	private Boolean enable;
	
	public User(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}	
	
}
