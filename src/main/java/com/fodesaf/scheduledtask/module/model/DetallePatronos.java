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
@Table (name = "Intellect_Periodo", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class DetallePatronos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	@Getter @Setter
	DetallePatronosPK primaryKey;
	
	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;
	
	@Getter @Setter
	@Column (name = "Estado")
	String estado;
	
	@Getter @Setter
	@Column (name = "Principal")
	double principal;
	
	@Getter @Setter
	@Column (name = "Recargo")
	double recargo;
	
	@Getter @Setter
	@Column (name = "Multa")
	double multa;
	

	
	
	
	
	
	
	
	
	
	

}
