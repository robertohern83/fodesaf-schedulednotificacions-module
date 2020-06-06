package com.fodesaf.scheduledtask.module.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.DatosPatrono;
import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.model.repositories.DatosPatronoRepository;
import com.fodesaf.scheduledtask.module.model.repositories.PatronosRepository;

@Service
public class PatronosService {

	@Resource
	PatronosRepository repository;
	

	@Resource
	DatosPatronoRepository datosPatronoRepo;
	
	
	public DatosPatrono obtenerDatosPatronoPorCedula(String cedula){
		return datosPatronoRepo.findByCedula(cedula);
	}
	
	public Patronos obtenerPorSegregado(String segregado){
		return repository.findBySegregado(segregado);
	}
	
	public String obtenerTelefonoPatrono(Patronos patrono, boolean smsCompatible){
		String telefonoResultado = null;
		
		DatosPatrono datosPatrono = this.obtenerDatosPatronoPorCedula(patrono.getCedula());
		if(null != datosPatrono && (null != datosPatrono.getCelular() || null != datosPatrono.getTelefono())) {
			
			if(!smsCompatible) {
				telefonoResultado = null != datosPatrono.getCelular()?datosPatrono.getCelular():datosPatrono.getTelefono();
			}
			else if(smsCompatible && isSMSCompatible(datosPatrono.getCelular())) {
				telefonoResultado = datosPatrono.getCelular();
			}
			else if(smsCompatible && isSMSCompatible(datosPatrono.getTelefono())) {
				telefonoResultado = datosPatrono.getTelefono();
			}
		}
		
		if(null == telefonoResultado) {
			if(null != patrono) {
				telefonoResultado = 
						obtenerTelefonoValidado(patrono.getTelefono(), smsCompatible) != null ?
								obtenerTelefonoValidado(patrono.getTelefono(), smsCompatible) :
								obtenerTelefonoValidado(patrono.getTelefonoRepresentanteLegal(), smsCompatible);
			}
		}
		
		return null != telefonoResultado ? telefonoResultado.trim() : null;
	}
	
	public List<String> getEmployerPhoneList(Patronos employer, boolean smsCompatible){
		List<String> phoneList = new ArrayList<String>();
		
		DatosPatrono employerData = this.obtenerDatosPatronoPorCedula(employer.getCedula());
		if(null != employerData) {
			this.addValidatedPhone(phoneList, employerData.getCelular(), smsCompatible);
			this.addValidatedPhone(phoneList, employerData.getTelefono(), smsCompatible);
		}

		if(null != employer) {
			this.addValidatedPhone(phoneList, employer.getTelefono(), smsCompatible);
			this.addValidatedPhone(phoneList, employer.getTelefonoRepresentanteLegal(), smsCompatible);
					 
		}
		return phoneList;
	}

	private String obtenerTelefonoValidado(String telefono, boolean smsCompatible) {
		if(null != telefono) {
			if(!smsCompatible) {
				return telefono;
			}
			else if(isSMSCompatible(telefono)) {
				return telefono;
			}
		} 
		return null;
	}
	
	private void addValidatedPhone(List<String> phoneList, String phone, boolean smsCompatible) {
		if(null != phone) {
			if(!smsCompatible) {
				phoneList.add(formatTelephone(phone));
			}
			else if(isSMSCompatible(phone)) {
				phoneList.add(formatTelephone(phone));
			}
		}
	}

	private boolean isSMSCompatible(String telefono) {
		//De acuerdo a Fodesaf, solo son celulares los que inician con 6, 7 y 8
		return telefono.trim().matches("[6,7,8][0-9]{7}");
	}	
	
	public List<String> obtenerCorreoPatrono(Patronos patrono){
		List<String> emailList = new ArrayList<String>();
		DatosPatrono datosPatrono = this.obtenerDatosPatronoPorCedula(patrono.getCedula());
		
		if(null != datosPatrono && null != datosPatrono.getEmail()) {
			emailList.add(datosPatrono.getEmail());
		}
		
		if(null != patrono && null != patrono.getCorreo()) {
			emailList.add(patrono.getCorreo());
		}
		
		return emailList;
	}
	
	private String formatTelephone(String telefono) {
		return String.format("+506%s",telefono);
	}

}
