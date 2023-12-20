package com.rivera.denunciaservice.entity;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "denuncias")
public class Denuncia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //campo autogenerado -autoingremental
	private Integer id;
	
	@Column(unique = true,nullable = false, length = 8)
	private String dni;
	
	@Column(name = "fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate fecha;
	
	@Column(name="titulo",nullable = false,length = 3)
	private String titulo;
	
	@Column(name="direccion",nullable = false,length = 200)
	private String direccion;
	
	@Column(name="descripcion",nullable = false,length = 255)
	private String descripcion;
	
	@Column(name="created_at",nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt; //Agregar
	
	@Column(name="updated_at",nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;//Modificar
	
	@Column(name="activo",nullable=false)
	private Boolean activo;
}