package com.rivera.denunciaservice.validator;

import com.rivera.denunciaservice.entity.Denuncia;
import com.rivera.denunciaservice.exceptions.ValidateServiceException;

public class DenunciaValidator {
	public static void save(Denuncia denuncia) {
		if (denuncia.getDni() == null || denuncia.getDni().isEmpty()) {
            throw new ValidateServiceException("El dni es requerido");
        }
        if (denuncia.getFecha() == null) {
            throw new ValidateServiceException("La fecha es requerida");
        }
        if (denuncia.getTitulo() == null || denuncia.getTitulo().length()>3) {
        	throw new ValidateServiceException("El titulo es requerido");
        }
        if(denuncia.getDireccion() == null || denuncia.getDireccion().length()>200)  {
        	throw new ValidateServiceException("La dirección es requerida");
        }
        if(denuncia.getDescripcion() == null || denuncia.getDescripcion().length()>255) {
        	throw new ValidateServiceException("La descripción es requerida");
        }
    }
}

