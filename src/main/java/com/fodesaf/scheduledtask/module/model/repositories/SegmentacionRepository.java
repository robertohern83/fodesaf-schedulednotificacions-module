/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Campanas;
import com.fodesaf.scheduledtask.module.model.Segmentacion;

/**
 * @author geanque
 *
 */
public interface SegmentacionRepository extends CrudRepository<Segmentacion, Integer>{
	
	Segmentacion findById(int id);
	
	List<Segmentacion> findByRegimen(String regimen);
	
	Segmentacion findByCampana(Campanas campana);

}
