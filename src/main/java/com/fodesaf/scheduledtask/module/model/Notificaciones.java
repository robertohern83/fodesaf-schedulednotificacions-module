/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author geanque
 *
 */
@Entity
@Table (name = "Notificaciones", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class Notificaciones implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@EmbeddedId
	NotificacionesPK primaryKey;
	
	@Getter
	@Setter
	@Column(name = "Estatus")
	String estatus;
	
	@Getter @Setter
	@Column (name = "Fechacreacion")
	Date fechaCreacion;
	
	
	@Getter @Setter
	@Column (name = "Fechaenvio")
	Date fechaEnvio;

	@Getter @Setter
	@Column (name = "Messageid")
	String messageId;
	
	@Getter @Setter
	@Column (name = "Fechaprogramada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate fechaProgramada;

	

}
