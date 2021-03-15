package com.springbootbase.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbase.app.entity.User;
import com.springbootbase.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	// SpringFramework usa injección de dependencias,
	// De esta forma, gracias al Autowired estamos pudiendo utilizar
	// la clase del repositorio que necesitamos inyectada sin necesidad
	// de hacer un new.
	@Autowired
	private UserRepository userRepository;

	
	@Override
	@Transactional(readOnly = true)
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Long id) {
		userRepository.deleteById(id);		
	}
	

}
