/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table (name = "Datospatrono", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class DatosPatrono implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Id
	@Column (name = "Cedula")
	String cedula;
	
	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;
	
	@Getter @Setter
	@Column (name ="Email")
	String email;
	
	@Getter @Setter
	@Column (name = "Telefono")
	String telefono;
	
	@Getter @Setter
	@Column (name = "Celular")
	String celular;
	
	@Getter @Setter
	@Column (name = "Usuarioingresa")
	String usuarioIngresa;
	
	@Getter @Setter
	@Column (name = "Fechaingreso")
	Date fechaIngreso;
	
	@Getter @Setter
	@Column (name = "Usuariomodifica")
	String usuarioModifica;
	
	@Getter @Setter
	@Column (name = "Fechamodificacion")
	Date fechaModificacion;

	@Override
	public String toString() {
		return "DatosPatrono [cedula=" + cedula + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
				+ ", celular=" + celular + ", usuarioIngresa=" + usuarioIngresa + ", fechaIngreso=" + fechaIngreso
				+ ", usuarioModifica=" + usuarioModifica + ", fechaModificacion=" + fechaModificacion + "]";
	}

	
}
