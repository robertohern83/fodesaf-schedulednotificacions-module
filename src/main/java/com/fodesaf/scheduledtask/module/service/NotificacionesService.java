/**
 * 
 */
package com.fodesaf.scheduledtask.module.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */

@Service
public class NotificacionesService {
	
	@Resource
	NotificacionesRepository notificacionesRepo;
	
	public Page<Notificaciones> findByCriteria(String notificacionEstatus, String estadoCampana, Pageable pageable){
		
		return notificacionesRepo.findAll(new Specification<Notificaciones>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Notificaciones> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				//Notificaciones que estén PENDIENTES
				if(null != notificacionEstatus) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("estatus"), notificacionEstatus)));
				}
				
				//Campañas que estén En Proceso
				if(null != estadoCampana) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("primaryKey").get("campana").get("estado"), estadoCampana)));
				}
				
				//Path<LocalDate> start = root.get("primaryKey").get("campana").<LocalDate>get("fechaInicio");
				//Path<LocalDate> end = root.get("primaryKey").get("campana").<LocalDate>get("fechaFin");
				
				
				//Que se encuentre dentro del rando de fechas de la campaña
				predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.currentDate(), root.get("primaryKey").get("campana").get("fechaInicio"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.currentDate(), root.get("primaryKey").get("campana").get("fechaFin"))));
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		
		
		
	}
	

}
