package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;


@Service
public class CityService {
	
	@Autowired
	private CityRepository repository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		return repository.findAll(Sort.by("name")).stream().map(city -> new CityDTO(city))
				.collect(Collectors.toList());
		 	
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " +id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violation integrity");
		}
	}
	
}
