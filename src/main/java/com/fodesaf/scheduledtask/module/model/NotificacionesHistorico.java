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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author geanque
 *
 */
@Entity
@Table (name = "Notificacioneshistorico", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class NotificacionesHistorico implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	@EmbeddedId
	NotificacionesHistoricoPK primaryKey;
	
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


	@Override
	public String toString() {
		return "NotificacionesHistorico [primaryKey=" + primaryKey + ", estatus=" + estatus + ", fechaCreacion="
				+ fechaCreacion + ", fechaEnvio=" + fechaEnvio + "]";
	}

	

	


	
	

}
