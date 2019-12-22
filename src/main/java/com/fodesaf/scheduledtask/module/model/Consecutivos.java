package com.fodesaf.scheduledtask.module.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table (name = "Consecutivos", schema = "dbo")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class Consecutivos implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Getter @Setter
	@Id
	@Column (name = "id")
	int id;
	
	@Getter @Setter
	@Column (name = "consecutivo")
	int consecutivo;
		
	

}
