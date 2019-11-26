package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
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
@Table (name = "Campanas", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Campanas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "Id")
	int id;
	
	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;
	
	@Getter @Setter
	@Column (name = "Activa")
	int activa;
	
	@Getter @Setter
	@Column (name = "Estado")
	String estado;

	
	@Getter @Setter
	@Column (name = "Tipo")
	String tipo;
	
	@Getter @Setter
	@Column (name = "Fechainicio")
	Date fechaInicio;
	
	@Getter @Setter
	@Column (name = "Fechafin")
	Date fechaFin;
	
	@Getter @Setter
	@Column (name = "Maximoresultados")
	int maximoResultados;
	
	@Getter @Setter
	@Column (name = "Maximonotificacionesdiarias")
	int maximoNotificacionesDiarias;
	
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
		return "Campanas [id=" + id + ", nombre=" + nombre + ", activa=" + activa + ", estado=" + estado + ", tipo="
				+ tipo + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", maximoResultados="
				+ maximoResultados + ", maximoNotificacionesDiarias=" + maximoNotificacionesDiarias
				+ ", usuarioIngresa=" + usuarioIngresa + ", fechaIngreso=" + fechaIngreso + ", usuarioModifica="
				+ usuarioModifica + ", fechaModificacion=" + fechaModificacion + "]";
	}
	

	
	
	
	
	

}
