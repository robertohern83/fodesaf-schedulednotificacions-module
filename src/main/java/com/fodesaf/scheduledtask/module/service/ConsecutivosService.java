package com.fodesaf.scheduledtask.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.Consecutivos;
import com.fodesaf.scheduledtask.module.model.repositories.ConsecutivosRepository;

@Service
public class ConsecutivosService {

	@Autowired
	ConsecutivosRepository repository;
	
	public synchronized int getNextConsecutive() {
		Consecutivos resultado = repository.findById(1);
		int resultadoConsecutivo = -1;
		if(null != resultado) {
			resultadoConsecutivo = resultado.getConsecutivo();
			resultadoConsecutivo++;
			resultado.setConsecutivo(resultadoConsecutivo);
			repository.save(resultado);
		}
		return resultadoConsecutivo;
	}
}
