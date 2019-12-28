/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.util.Arrays;

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
public class CampanasBinding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	Campanas campana;
	
	@Getter @Setter
	String[] canales;
	
	@Getter @Setter
	Segmentacion segmentacion;
	
	@Getter @Setter
	int cantidadRegistros = -1;
	
	@Getter @Setter
	String[] regimenVector;
	
	@Getter @Setter
	String[] condicionesVector;

	@Override
	public String toString() {
		return "CampanasBinding [campana=" + campana + ", canales=" + Arrays.toString(canales) + ", segmentacion="
				+ segmentacion + ", cantidadRegistros=" + cantidadRegistros + ", regimenVector="
				+ Arrays.toString(regimenVector) + ", condicionesVector=" + Arrays.toString(condicionesVector) + "]";
	}

	



}
