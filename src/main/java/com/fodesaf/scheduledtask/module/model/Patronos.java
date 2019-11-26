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
@Table (name = "Patronos", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Patronos implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Id 
	@Column (name = "Serie")
	int serie;
	
	@Getter @Setter
	@Column (name = "N_P")
	int n_p;
	
	@Getter @Setter
	@Column (name = "Ultima_prevencion")
	Date ultimaPrevencion;
	
	@Getter @Setter
	@Column (name = "Ultima_notificación")
	Date ultimaNotificacion;
	
	@Setter @Getter
	@Column (name = "Facturación_CCSS")
	String facturacionCCSS;
	
	@Getter @Setter
	@Column (name = "Segregado")
	String segregado;
	
	@Setter @Getter
	@Column (name = "Cédula")
	String cedula;
	

	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;
	
	@Getter @Setter
	@Column (name= "Régimen")
	String regimen;
	
	@Setter @Getter
	@Column (name = "Condición_legal")
	String condicionLegal;
	
	@Getter @Setter
	@Column (name = "Alerta")
	String alerta;
	
	@Getter @Setter
	@Column (name = "Situacion")
	String situacion;
	
	@Getter @Setter
	@Column (name = "Deuda_Total")
	Double deudaTotal;
	
	@Getter @Setter
	@Column (name = "Pago_mínimo")
	Double pagoinimo;
	
	@Getter @Setter
	@Column (name = "Saldo_arreglo")
	Double saldoArreglo;
	
	@Getter @Setter
	@Column (name = "Cuotas_al_cobro")
	Double cuotasAlCobro;
	
	@Setter @Getter
	@Column (name = "Principal_arreglo")
	Double principalArreglo;
	
	@Getter @Setter
	@Column (name = "Intereses_administrativos")
	Double interesesAdministrativos;
	
	@Getter @Setter
	@Column (name = "Intereses_moratorios")
	Double interesesMoratorios;
	
	@Getter @Setter
	@Column (name = "Cuotas_atraso")
	int cuotasAtraso;
	
	@Getter @Setter
	@Column (name = "Desde_período_Fuera_de_arreglo")
	Date desdePeriodoFueraDeArreglo;
	
	@Getter @Setter
	@Column (name = "Hasta_período_Fuera_de_arreglo")
	Date hastaPeriodoFueraDeArreglo;
	
	@Getter @Setter
	@Column (name = "Prinipal")
	Double principal;
	
	@Getter @Setter
	@Column (name = "Recargos")
	Double recargos;
	
	@Getter @Setter
	@Column (name = "Multas")
	Double multas;
	
	@Getter @Setter
	@Column (name = "Correo")
	String correo;
	
	@Getter @Setter
	@Column (name = "Teléfono_representante_legal")
	String telefonoRepresentanteLegal;
	
	@Getter @Setter
	@Column (name = "Teléfono")
	String telefono;
	
	@Getter @Setter
	@Column (name = "Tele_Fax")
	String teleFax;
	
	@Getter @Setter
	@Column (name = "Responsable")
	String responsable;
	
	@Getter @Setter
	@Column (name = "Activo_CCSS")
	String activoCCSS;
	
	@Getter @Setter
	@Column (name = "Arreglo")
	Double arreglo;
	
	@Getter @Setter
	@Column (name = "Suscrito")
	Date suscrito;

	@Override
	public String toString() {
		return "MorososEntity [serie=" + serie + ", n_p=" + n_p + ", ultimaPrevencion=" + ultimaPrevencion
				+ ", ultimaNotificacion=" + ultimaNotificacion + ", facturacionCCSS=" + facturacionCCSS + ", segregado="
				+ segregado + ", cedula=" + cedula + ", nombre=" + nombre + ", regimen=" + regimen + ", condicionLegal="
				+ condicionLegal + ", alerta=" + alerta + ", situacion=" + situacion + ", deudaTotal=" + deudaTotal
				+ ", pagoinimo=" + pagoinimo + ", saldoArreglo=" + saldoArreglo + ", cuotasAlCobro=" + cuotasAlCobro
				+ ", principalArreglo=" + principalArreglo + ", interesesAdministrativos=" + interesesAdministrativos
				+ ", interesesMoratorios=" + interesesMoratorios + ", cuotasAtraso=" + cuotasAtraso
				+ ", desdePeriodoFueraDeArreglo=" + desdePeriodoFueraDeArreglo + ", hastaPeriodoFueraDeArreglo="
				+ hastaPeriodoFueraDeArreglo + ", principal=" + principal + ", recargos=" + recargos + ", multas="
				+ multas + ", correo=" + correo + ", telefonoRepresentanteLegal=" + telefonoRepresentanteLegal
				+ ", telefono=" + telefono + ", teleFax=" + teleFax + ", responsable=" + responsable + ", activoCCSS="
				+ activoCCSS + ", arreglo=" + arreglo + ", suscrito=" + suscrito + "]";
	}

	
	
	
	

}
