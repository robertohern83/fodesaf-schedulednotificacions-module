/**
 * 
 */
package com.fodesaf.scheduledtask.module.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author geanque
 *
 */
@XmlRootElement(name = "employee")
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Employee {
	
	@Getter @Setter
	private String employeeId;
	
	@Getter @Setter
	private String employeeName;
	
	@Getter @Setter
	private String employeeLastName;

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeLastName="
				+ employeeLastName + "]";
	}

	
	
}
