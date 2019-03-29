package com.sia.pdf;

import java.math.BigInteger;

import com.sia.model.ReporteFotografico;

public class ReporteFotograficoDataTest {
	public ReporteFotografico getData() {
		ReporteFotografico obj = new ReporteFotografico();
		obj.setReporteFotograficoId(BigInteger.valueOf(1));
		obj.setS1Titulo("El titulo");
		obj.setS1DivisionRegion("division");
		obj.setS1Objetivo("objetivo");
		obj.setS1Supervisor("supervisor");
		obj.setS1Oficina("oficina");
		
		obj.setS2PlantaEmergencia("planta emergencia");				
	    obj.setS2AzoteaPropCompartida("azotea prop comp");
	    obj.setS2LocalPropRentado("local prop rentado");
	    obj.setS2CuentaEstacionamiento("cuenta estacionamiento");
	    obj.setS2NumCajasVentanilla("num cajas vent");
	    obj.setS2NumEjecutivos("num ejec");
	    obj.setS2LugaresEjecDisponibles("lugares ejec disp");
	    obj.setS2CuentaBancaPersonal("cuenta banca per");
	    
	    obj.setS3CuentaSenalizacionProtCivCompleta("cuenta senalizacion prot civ comp");
	    obj.setS3NumExtintores("num extintores");
	    obj.setS3CuentaLlaveSwitch("cuenta llave switch");
	    obj.setS3CuentaContactoresAlumbradoAA("cuenta conectores alumb");
	    obj.setS3NumAtms("num atms");
	    obj.setS3NumPc("num pcs");
	    obj.setS3CuentaPisoAlfombra("cuenta piso alf");
	    
	    obj.setS4CapacidadUpsMarca("capacidad ups marca");
	    obj.setS4CuentaCalefaccion("cuenta calefaccion");
	    obj.setS4GarantiaImpermeabilizacion("garantia impermeabilizacion");
	    obj.setS4FacilidadAccesoAzotea("facilidad azotea");
	    obj.setS4CuentaConPodio("cuenta con podio");
	    obj.setS4NumPantallas("num pantallas");
		
		return obj;
	}
}
