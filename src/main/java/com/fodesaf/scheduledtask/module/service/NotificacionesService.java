/**
 * 
 */
package com.fodesaf.scheduledtask.module.service;

import java.time.LocalDate;
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

import com.fodesaf.scheduledtask.module.model.Campanas;
import com.fodesaf.scheduledtask.module.model.ControlNotificacionesDiarias;
import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.ControlNotificacionesDiariasRepository;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */

@Service
public class NotificacionesService {
	
	@Resource
	NotificacionesRepository notificacionesRepo;
	
	@Resource 
	ControlNotificacionesDiariasRepository controlRepo;
	
	public Page<Notificaciones> findByCriteria(String notificacionEstatus, String estadoCampana, List<Campanas> campanasExcluidas, Pageable pageable){
		
		return notificacionesRepo.findAll(new Specification<Notificaciones>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Notificaciones> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				
				//Excluir notificaciones a campañas que ya hayan llegado a su limite diario
				if(null != campanasExcluidas && !campanasExcluidas.isEmpty()) {
					//root.get("primaryKey").get("campana").in(campanasExcluidas);
					predicates.add(criteriaBuilder.and(criteriaBuilder.not(root.get("primaryKey").get("campana").in(campanasExcluidas))));
				}
				
				//Notificaciones que estén PENDIENTES
				if(null != notificacionEstatus) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("estatus"), notificacionEstatus)));
				}
				
				//Campañas que estén En Proceso
				if(null != estadoCampana) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("primaryKey").get("campana").get("estado"), estadoCampana)));
				}
				//Que se encuentre dentro del rando de fechas de la campaña
				predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.currentDate(), root.get("primaryKey").get("campana").get("fechaInicio"))));
				predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.currentDate(), root.get("primaryKey").get("campana").get("fechaFin"))));
				
				//Que la fecha programada sea nula o sea hoy (para el segundo intento)
				Predicate p1 = criteriaBuilder.isNull(root.get("fechaProgramada"));
				Predicate p2 =criteriaBuilder.equal(root.get("fechaProgramada"), LocalDate.now());
            	predicates.add(criteriaBuilder.and(criteriaBuilder.or(p1,p2)));
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

			
		}, pageable);
		
		
		
	}
	
	public List<ControlNotificacionesDiarias> getControlDiarioHoy(LocalDate fecha) {
		return controlRepo.findByPrimaryKeyFechaControl(fecha);
		
	}

	
	public int cleanPatronos() {
		
		return notificacionesRepo.cleanPatronos();
	}
	
	

}
