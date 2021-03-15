package com.springbootbase.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbase.app.entity.User;
import com.springbootbase.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//Create a new user ---> POST verb
	@PostMapping // Seria lo mismo que poner @RequestMapping(method=(POST))
	public ResponseEntity<?> create (@RequestBody User user){  // Le indicamos que en el cuerpo de la petición va el dato a guardar
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user)); // Le estamos diciendo que devuelva un 201 cuando
																					   // se guarde el usuario que le pasamos como parámetro	
	}
	
	
	// Read an user by id
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value="id") Long userId){ //Con esto le estamos diciendo que el parámetro que esperamos en
																// la ruta vendrá como id pero su valor lo vamos a tratar como userId
		Optional<User> oUser = userService.findById(userId);
		if(!oUser.isPresent()) { // Si no encuentra un objeto user queremos que devuelva una excepción 
			return ResponseEntity.notFound().build(); // Devolvería un 404
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId)); //Devuolvería un 200 OK
	}
	
	
	// Update an user
	@PutMapping("/{id}")
	public ResponseEntity<?> update (@RequestBody User userDetails, @PathVariable(value="id") Long userId){ //Con esto le estamos diciendo que el parámetro que esperamos en
																// la ruta vendrá como id pero su valor lo vamos a tratar como userId, en 
																// userDetails viaja el objeto a guardar
		Optional<User> oUser = userService.findById(userId);
		if(!oUser.isPresent()) { // Si no encuentra un objeto user queremos que devuelva una excepción 
			return ResponseEntity.notFound().build(); // Devolvería un 404
		}
		
		//Para copiar un objeto en otro
		// BeanUtils.copyProperties(userDetails, oUser);
		
		oUser.get().setName(userDetails.getName());
		oUser.get().setSurname(userDetails.getSurname());
		oUser.get().setEmail(userDetails.getEmail());
		oUser.get().setEnable(userDetails.getEnable());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(oUser.get()));
	}
	
	
	// Delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable (value = "id") Long userId) {
		if(!userService.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build(); // Devolvería un 404
		}
		userService.deleteById(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	// Read all users
	@GetMapping
	public List<User> readAll(){
		List<User> users = StreamSupport.stream(userService.findAll().spliterator(), false).collect(Collectors.toList());
		return users;
	}
}
