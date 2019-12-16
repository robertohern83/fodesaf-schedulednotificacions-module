/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table (name = "Segmentacion", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Segmentacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter @Setter
	@Column (name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Idcampana", referencedColumnName = "id")
	@Getter @Setter
	private Campanas campana;
	
	@Getter @Setter
	@Column (name = "Regimen")
	String regimen;
	
	@Setter @Getter
	@Column (name = "Situacion")
	String situacion;
	
	@Getter @Setter
	@Column (name = "Categoriaalerta")
	Integer categoriaAlerta;
	
	@Getter @Setter
	@Column (name = "Sinnotificacion")
	Integer sinNotificacion;
	
	@Getter @Setter
	@Column (name = "Montominimo")
	Double montoMinimo;
	
	@Getter @Setter
	@Column (name = "Montomaximo")
	Double montoMaximo;
	
	@Getter @Setter
	@Column (name = "Cuotasdesde", nullable = true)
	Integer cuotasDesde;
	
	@Getter @Setter
	@Column (name = "Cuotashasta", nullable = true)
	Integer cuotasHasta;
	
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
		return "Segmentacion [id=" + id + ", campana=" + campana + ", regimen=" + regimen + ", situacion=" + situacion
				+ ", categoriaAlerta=" + categoriaAlerta + ", sinNotificacion=" + sinNotificacion + ", montoMinimo="
				+ montoMinimo + ", montoMaximo=" + montoMaximo + ", cuotasDesde=" + cuotasDesde + ", cuotasHasta="
				+ cuotasHasta + ", usuarioIngresa=" + usuarioIngresa + ", fechaIngreso=" + fechaIngreso
				+ ", usuarioModifica=" + usuarioModifica + ", fechaModificacion=" + fechaModificacion + "]";
	}

	

	
	
}
