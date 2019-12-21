/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table (name = "TipoCampana", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class TipoCampana implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Column (name ="id")
	Integer id;
	
	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;

	@Override
	public String toString() {
		return "TipoCampana [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
