/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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

@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class DetallePatronosPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "segregado", referencedColumnName = "segregado")
	Patronos segregado;
	
	
	@Getter @Setter
	@Column (name = "cedula")
	String cedula;

	@Getter @Setter
	@Column (name = "Periodo")
	String periodo;
	
	@Getter @Setter
	@Column (name = "Tipo")
	String tipo;

	@Override
	public String toString() {
		return "DetallePatronosPK [segregado=" + segregado + ", cedula=" + cedula + ", periodo=" + periodo + ", tipo="
				+ tipo + "]";
	}

	
	
	
	
	

}
