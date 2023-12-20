package com.rivera.denunciaservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rivera.denunciaservice.entity.Denuncia;
import com.rivera.denunciaservice.exceptions.GeneralServiceException;
import com.rivera.denunciaservice.exceptions.NoDataFoundException;
import com.rivera.denunciaservice.exceptions.ValidateServiceException;
import com.rivera.denunciaservice.repository.DenunciaRepository;
import com.rivera.denunciaservice.service.DenunciaService;
import com.rivera.denunciaservice.validator.DenunciaValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DenunciaServiceImpl implements DenunciaService {
	@Autowired
	private DenunciaRepository repository;
	
	@Override
	@Transactional(readOnly=true) // Metodo de solo lectura 
	public List<Denuncia> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Denuncia> finByDni(String dni, Pageable page) {
		try {
			return repository.findByDniContaining(dni,page);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Denuncia findById(int id) {
		try {
			Denuncia registro=repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID"));
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public Denuncia save(Denuncia denuncia) {
		try {
			DenunciaValidator.save(denuncia);
			if(repository.findByDni(denuncia.getDni())!=null) {
				throw new ValidateServiceException("Ya existe un registro con el nombre: " + denuncia.getDni());
			}
			denuncia.setActivo(true);
			Denuncia registro=repository.save(denuncia);
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public Denuncia update(Denuncia denuncia) {
		try {
			DenunciaValidator.save(denuncia);
			Denuncia registro=repository.findById(denuncia.getId()).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID"));
			Denuncia registroD = repository.findByDni(denuncia.getDni());
			if(registroD!=null && registroD.getId()!=denuncia.getId()) {
				throw new ValidateServiceException("Ya existe un registro con el nombre: "+ denuncia.getDni());
			}
			registro.setDni(denuncia.getDni());
			registro.setFecha(denuncia.getFecha());
			registro.setTitulo(denuncia.getTitulo());
			registro.setDireccion(denuncia.getDireccion());
			registro.setDescripcion(denuncia.getDescripcion());
			
			repository.save(registro);
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			Denuncia registro=repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID"));
			repository.delete(registro);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(),e); //Mostrar
			throw e;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Denuncia finByDni(String dni) {
	    try {
	        return repository.findByDniContaining(dni).orElseThrow(() -> new NoDataFoundException("No existe el DNI"));
	    } catch (ValidateServiceException | NoDataFoundException e) {
	        log.info(e.getMessage(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        throw new GeneralServiceException(e.getMessage(), e);
	    }
	}
}