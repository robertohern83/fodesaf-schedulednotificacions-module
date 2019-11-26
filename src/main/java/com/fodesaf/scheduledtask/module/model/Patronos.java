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
public class Patronos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter @Setter
	@Column (name = "Segregado")
	String segregado;
	
	@Setter @Getter
	@Column (name = "Cedula")
	String cedula;
	

	@Getter @Setter
	@Column (name = "Nombre")
	String nombre;
	
	@Getter @Setter
	@Column (name= "Regimen")
	String regimen;
	
	@Setter @Getter
	@Column (name = "Condicion_Legal")
	String condicionLegal;
	
	@Getter @Setter
	@Column (name = "Alerta")
	String alerta;
	
	@Getter @Setter
	@Column (name = "Categoria_Alerta")
	String categoriaAlerta;
	
	@Getter @Setter
	@Column (name = "Situacion")
	String situacion;
	
	@Getter @Setter
	@Column (name = "Deuda_Total")
	Double deudaTotal;
	
	@Getter @Setter
	@Column (name = "Pago_Minimo")
	Double pagoMinimo;
	
	@Getter @Setter
	@Column (name = "Saldo_Arreglo")
	Double saldoArreglo;
	
	@Getter @Setter
	@Column (name = "Cuotas_Al_Cobro")
	Double cuotasAlCobro;
	
	@Setter @Getter
	@Column (name = "Principal_Arreglo")
	Double principalArreglo;
	
	@Getter @Setter
	@Column (name = "Intereses_Administrativos")
	Double interesesAdministrativos;
	
	@Getter @Setter
	@Column (name = "Intereses_Moratorios")
	Double interesesMoratorios;
	
	@Getter @Setter
	@Column (name = "Cuotas_Atraso")
	int cuotasAtraso;
	
	@Getter @Setter
	@Column (name = "Desde_Periodo_Fuera")
	Date desdePeriodoFueraDeArreglo;
	
	@Getter @Setter
	@Column (name = "Hasta_Periodo_Fuera")
	Date hastaPeriodoFueraDeArreglo;
	
	@Getter @Setter
	@Column (name = "Principal")
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
	@Column (name = "Telefono_Representante")
	String telefonoRepresentanteLegal;
	
	@Getter @Setter
	@Column (name = "Telefono")
	String telefono;
	
	@Getter @Setter
	@Column (name = "Telefax")
	String teleFax;

	@Override
	public String toString() {
		return "Patronos [segregado=" + segregado + ", cedula=" + cedula + ", nombre=" + nombre + ", regimen=" + regimen
				+ ", condicionLegal=" + condicionLegal + ", alerta=" + alerta + ", categoriaAlerta=" + categoriaAlerta
				+ ", situacion=" + situacion + ", deudaTotal=" + deudaTotal + ", pagoMinimo=" + pagoMinimo
				+ ", saldoArreglo=" + saldoArreglo + ", cuotasAlCobro=" + cuotasAlCobro + ", principalArreglo="
				+ principalArreglo + ", interesesAdministrativos=" + interesesAdministrativos + ", interesesMoratorios="
				+ interesesMoratorios + ", cuotasAtraso=" + cuotasAtraso + ", desdePeriodoFueraDeArreglo="
				+ desdePeriodoFueraDeArreglo + ", hastaPeriodoFueraDeArreglo=" + hastaPeriodoFueraDeArreglo
				+ ", principal=" + principal + ", recargos=" + recargos + ", multas=" + multas + ", correo=" + correo
				+ ", telefonoRepresentanteLegal=" + telefonoRepresentanteLegal + ", telefono=" + telefono + ", teleFax="
				+ teleFax + "]";
	}
	

	

	
	
	
	

}
