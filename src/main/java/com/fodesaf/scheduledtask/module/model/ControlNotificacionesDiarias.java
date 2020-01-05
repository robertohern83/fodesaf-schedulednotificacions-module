/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table (name = "Controlnotificacionesdiarias", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class ControlNotificacionesDiarias implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@EmbeddedId
	ControlNotificacionesDiariasPK primaryKey;
	
	@Getter @Setter
	@Column (name = "Restantes")
	Long restantes;
	
	

}
