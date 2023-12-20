package com.rivera.denunciaservice.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.rivera.denunciaservice.converter.DenunciaConverter;
import com.rivera.denunciaservice.dto.DenunciaDTO;
import com.rivera.denunciaservice.entity.Denuncia;
import com.rivera.denunciaservice.service.DenunciaService;
import com.rivera.denunciaservice.utils.WrapperResponse;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {
	@Autowired
	private DenunciaService service;
	
	@Autowired 
	private DenunciaConverter converter;
	
	//Metodos 
	@GetMapping()
	public ResponseEntity<List<DenunciaDTO>> findAll( // el Metodo findAll va a devolver los DTO
			//Parametros
			@RequestParam (value = "dni",required = false, defaultValue ="") String dni,
			@RequestParam (value = "offset",required = false, defaultValue ="0") int pageDni,
			@RequestParam (value = "limit",required = false, defaultValue ="5") int pageSize
			){
		Pageable page= PageRequest.of(pageDni,pageSize);
		List<Denuncia> infraciones;
		if(dni==null) {
			infraciones=service.findAll(page);	
		}else {
			infraciones=service.finByDni(dni, page);
		}
		
		List<DenunciaDTO> infraccionesDTO=converter.fromEntity(infraciones); //convirte una lista de entidades a una lista de DTO
		return new WrapperResponse(true,"success",infraccionesDTO).createResponse(HttpStatus.OK); // Devuelve la lista
	}
	
	@GetMapping(value="/{id}") //Notaciòn
	public ResponseEntity<WrapperResponse<DenunciaDTO>> findById(@PathVariable("id")int id){
		Denuncia denuncia = service.findById(id);
		DenunciaDTO denunciaDTO=converter.fromEntity(denuncia); // se envia una entidad y lo convierte a un DTO
		return new WrapperResponse<DenunciaDTO>(true,"success",denunciaDTO).createResponse(HttpStatus.OK); //retorna un DTO
	}
	
	@GetMapping(value = "usuario/{dni}")
	public ResponseEntity<WrapperResponse<DenunciaDTO>> findByDni(@PathVariable("dni") String dni) {
	    Denuncia denuncia = service.finByDni(dni);
	    DenunciaDTO denunciaDTO = converter.fromEntity(denuncia);
	    return new WrapperResponse<>(true, "success", denunciaDTO)
	            .createResponse(HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<DenunciaDTO> create(@RequestBody DenunciaDTO denunciasDTO){ //esperando un articulo DTO
		Denuncia registro=service.save(converter.fromDTO(denunciasDTO)); // convertir de un DTO a una entidad
		DenunciaDTO registroDTO=converter.fromEntity(registro);
		return new WrapperResponse(true,"success",registroDTO).createResponse(HttpStatus.CREATED); //devolver el registro DTO
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<DenunciaDTO> update(@PathVariable("id")int id,@RequestBody DenunciaDTO infraccionDTO){
		Denuncia registro = service.update(converter.fromDTO(infraccionDTO));
		DenunciaDTO registroDTO=converter.fromEntity(registro); // se envia una entidad y lo convierte a un DTO
		return new WrapperResponse(true,"success",registro).createResponse(HttpStatus.OK);
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<DenunciaDTO> delete(@PathVariable("id")int id){
		service.delete(id);
		return new WrapperResponse(true,"success",null).createResponse(HttpStatus.OK);
	}
}