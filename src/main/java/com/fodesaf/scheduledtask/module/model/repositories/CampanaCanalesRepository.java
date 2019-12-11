package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.CampanaCanales;
import com.fodesaf.scheduledtask.module.model.CampanaCanalesPK;
import com.fodesaf.scheduledtask.module.model.Campanas;

/**
 * @author geanque
 *
 */
public interface CampanaCanalesRepository extends CrudRepository<CampanaCanales, Integer>{
	
	CampanaCanales findByPrimaryKey(CampanaCanalesPK pk);
	
	List<CampanaCanales> findByPrimaryKeyCampana(Campanas campana);
	
	void deleteByPrimaryKeyCampana(Campanas campana);

}
