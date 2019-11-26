/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class NotificacionesPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idsegmento", referencedColumnName = "id")
	Segmentacion segmento;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idcampana", referencedColumnName = "id")
	Campanas campana;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idserie", referencedColumnName = "serie")
	Patronos patrono;

	@Override
	public String toString() {
		return "NotificacionesPK [segmento=" + segmento + ", campana=" + campana + ", patrono=" + patrono + "]";
	}

	
	

}
