/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

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
@Table (name = "Campañacanales", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class CampanaCanales implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@EmbeddedId
	CampanaCanalesPK primaryKey;

	@Override
	public String toString() {
		return "CampanaCanales [primaryKey=" + primaryKey + "]";
	}
	

	
	
	
	
	

}
