/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class NotificacionesPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idsegmento", referencedColumnName = "id")
	Segmentacion segmento;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Idcampana", referencedColumnName = "id")
	Campanas campana;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "segregado", referencedColumnName = "segregado")
	@NotFound(action=NotFoundAction.IGNORE)
	Patronos patrono;
	
	@Getter
	@Setter
	String cedula;
	
	@Getter
	@Setter
	String canal;
	
	@Getter
	@Setter
	int intento;

	/*@PostLoad
	public void postLoad(){
	    try {
	        if(getPatrono() != null){
	            setPatrono(null);
	        }
	    }
	    catch (EntityNotFoundException e){
	    	setPatrono(null);
	    }
	} */
	

}
