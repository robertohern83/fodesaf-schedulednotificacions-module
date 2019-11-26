/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
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
public class CampanaCanalesPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Getter @Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Idcampana", referencedColumnName = "id")
	Campanas campana;
	
	@Getter @Setter
	@Column (name = "Canal")
	String canal;

	@Override
	public String toString() {
		return "CampanaCanales [campana=" + campana + ", canal=" + canal + "]";
	}
	
	
	
	

}
