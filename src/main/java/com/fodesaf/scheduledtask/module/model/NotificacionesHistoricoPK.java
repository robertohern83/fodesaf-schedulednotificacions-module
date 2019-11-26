/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author geanque
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotificacionesHistoricoPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column (name = "Idsegmento")
	int segmento;

	@Getter
	@Setter
	@Column (name = "Idcampana")
	int campana;

	@Getter
	@Setter
	@Column (name = "segregado")
	int patrono;

	@Override
	public String toString() {
		return "NotificacionesHistoricoPK [segmento=" + segmento + ", campana=" + campana + ", patrono=" + patrono
				+ "]";
	}

	

	

	

	
	

}
