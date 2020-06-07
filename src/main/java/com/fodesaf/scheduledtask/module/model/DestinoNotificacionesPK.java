/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class DestinoNotificacionesPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idsegmento", referencedColumnName = "Idsegmento")
	@JoinColumn(name = "Idcampana", referencedColumnName = "Idcampana")
	@JoinColumn(name = "Segregado", referencedColumnName = "Segregado")
	@JoinColumn(name = "Cedula", referencedColumnName = "Cedula")
	@JoinColumn(name = "Canal", referencedColumnName = "Canal")
	@JoinColumn(name = "Intento", referencedColumnName = "Intento")
	Notificaciones notificacion;

	
	@Getter
	@Setter
	@Column (name = "Destino")
	String destino;
	
	
	
	

}
