/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity
@Table (name = "Destinonotificaciones", schema = "dbo")
@DynamicUpdate
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class DestinoNotificaciones implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@EmbeddedId
	DestinoNotificacionesPK primaryKey;
	
	@Getter
	@Setter
	@Column (name = "Fechaenvio")
	Date fechaEnvio;
	
	@Getter
	@Setter
	@Column (name = "Messageid")
	String messageId;

	
	

	

}
