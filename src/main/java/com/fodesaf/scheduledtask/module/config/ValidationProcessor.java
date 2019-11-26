/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import org.springframework.batch.item.ItemProcessor;

import com.fodesaf.scheduledtask.module.model.Employee;

/**
 * @author geanque
 *
 */
public class ValidationProcessor implements ItemProcessor<Employee,Employee>{

	@Override
	public Employee process(Employee employee) throws Exception {
		if (employee.getEmployeeId() == null){
            System.out.println("Missing employee id : " + employee.getEmployeeId());
            return null;
        } 
         
        try
        {
            if(Integer.valueOf(employee.getEmployeeId()) <= 0) {
                System.out.println("Invalid employee id : " + employee.getEmployeeId());
                return null;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid employee id : " + employee.getEmployeeId());
            return null;
        }
        return employee;
    }
	

}
