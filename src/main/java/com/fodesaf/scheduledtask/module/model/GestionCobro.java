/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table (name = "Gestioncobro", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class GestionCobro implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "Id")
	int id;
	
	@Getter @Setter
	@Column (name = "Cedula")
	String cedula;
	
	@Getter @Setter
	@Column (name = "Fechacontacto")
	Date fechaContacto;
	
	@Getter @Setter
	@Column (name = "Tipocontacto")
	String tipoContacto;
	
	@Getter @Setter
	@Column (name = "Razonllamada")
	String razonLlamada;
	
	@Getter @Setter
	@Column (name = "Fechapromesapago")
	LocalDate fechaPromesaPago;
	
	@Getter @Setter
	@Column (name = "Montoprometido")
	Double montoPrometido;
	
	@Getter @Setter
	@Column (name = "Comentarios")
	String comentarios;
	
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
		return "GestionCobro [id=" + id + ", cedula=" + cedula + ", fechaContacto=" + fechaContacto + ", tipoContacto="
				+ tipoContacto + ", razonLlamada=" + razonLlamada + ", fechaPromesaPago=" + fechaPromesaPago
				+ ", montoPrometido=" + montoPrometido + ", comentarios=" + comentarios + ", usuarioIngresa="
				+ usuarioIngresa + ", fechaIngreso=" + fechaIngreso + ", usuarioModifica=" + usuarioModifica
				+ ", fechaModificacion=" + fechaModificacion + "]";
	}

	
	
	
	
	

}
