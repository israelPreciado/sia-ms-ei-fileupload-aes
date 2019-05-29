/**
 * 
 */
package com.sia.services;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sia.model.ReporteFotografico;
import com.sia.services.interfaces.ReporteFotograficoService;
import com.sia.utilities.ProcessData;

/**
 * @author randd1
 *
 */
public class ReporteFotograficoServiceImpl implements ReporteFotograficoService {
	
	private static final Logger log = Logger.getLogger(ReporteFotograficoServiceImpl.class.getName());

	@Override
	public ReporteFotografico findById(Connection conn, BigInteger id) {
		ReporteFotografico rf = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select reporte_fotografico_id, s1_titulo, s1_division_region, s1_objetivo, s1_supervisor, s1_oficina, s2_planta_emergencia, s2_azotea_prop_compartida, s2_local_prop_rentado");
			sql.append(", s2_cuenta_estacionamiento, s2_num_cajas_ventanilla, s2_num_bancas_3_plazas, s2_num_bancas_2_plazas, s2_num_ejecutivos, s2_lugares_ejec_disponibles, s2_cuenta_banca_personal, s3_cuenta_senalizacion_prot_civ_completa");
			sql.append(", s3_num_extintores, s3_cuenta_llave_switch, s3_cuenta_contactores_alumbrado_aa, s3_num_atms, s3_num_pc, s3_cuenta_piso_alfombra, s4_capacidad_ups_marca");
			sql.append(", s4_cuenta_calefaccion, s4_garantia_impermeabilizacion, s4_facilidad_acceso_azotea, s4_cuenta_con_podio, s4_num_pantallas, fecha_creacion");
			sql.append(" from reporte_fotografico");
			sql.append(" where reporte_fotografico_id = ?");
			
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id.intValue());
			JSONArray result = new ProcessData().toJSONArray(ps);
			
			for(int i = 0; i < result.length(); i++) {
				JSONObject obj = result.getJSONObject(i);				
				rf = new ReporteFotografico();
				rf.setReporteFotograficoId(BigInteger.valueOf(obj.getInt("reporte_fotografico_id")));
				rf.setS1Titulo(obj.getString("s1_titulo"));
				rf.setS1DivisionRegion(obj.getString("s1_division_region"));
				rf.setS1Objetivo(obj.getString("s1_objetivo"));
				rf.setS1Supervisor(obj.getString("s1_supervisor"));
				rf.setS1Oficina(obj.getString("s1_oficina"));
				rf.setS2PlantaEmergencia(obj.getString("s2_planta_emergencia"));
				rf.setS2AzoteaPropCompartida(obj.getString("s2_azotea_prop_compartida"));
				rf.setS2LocalPropRentado(obj.getString("s2_local_prop_rentado"));
				rf.setS2CuentaEstacionamiento(obj.getString("s2_cuenta_estacionamiento"));
				rf.setS2NumCajasVentanilla(obj.getString("s2_num_cajas_ventanilla"));
				rf.setS2NumBancas3Plazas(obj.getString("s2_num_bancas_3_plazas"));
				rf.setS2NumBancas2Plazas(obj.getString("s2_num_bancas_2_plazas"));
				rf.setS2NumEjecutivos(obj.getString("s2_num_ejecutivos"));
				rf.setS2LugaresEjecDisponibles(obj.getString("s2_lugares_ejec_disponibles"));
				rf.setS2CuentaBancaPersonal(obj.getString("s2_cuenta_banca_personal"));
				rf.setS3CuentaSenalizacionProtCivCompleta(obj.getString("s3_cuenta_senalizacion_prot_civ_completa"));
				rf.setS3NumExtintores(obj.getString("s3_num_extintores"));
				rf.setS3CuentaLlaveSwitch(obj.getString("s3_cuenta_llave_switch"));
				rf.setS3CuentaContactoresAlumbradoAA(obj.getString("s3_cuenta_contactores_alumbrado_aa"));
				rf.setS3NumAtms(obj.getString("s3_num_atms"));
				rf.setS3NumPc(obj.getString("s3_num_pc"));
				rf.setS3CuentaPisoAlfombra(obj.getString("s3_cuenta_piso_alfombra"));
				rf.setS4CapacidadUpsMarca(obj.getString("s4_capacidad_ups_marca"));
				rf.setS4CuentaCalefaccion(obj.getString("s4_cuenta_calefaccion"));
				rf.setS4GarantiaImpermeabilizacion(obj.getString("s4_garantia_impermeabilizacion"));
				rf.setS4FacilidadAccesoAzotea(obj.getString("s4_facilidad_acceso_azotea"));
				rf.setS4CuentaConPodio(obj.getString("s4_cuenta_con_podio"));
				rf.setS4NumPantallas(obj.getString("s4_num_pantallas"));
				//rf.setS1Titulo(obj.getString("fecha_creacion"));								
			}			
		} catch(Exception e) {
			log.warning(e.getMessage());
		}
		
		return rf;
	}

	@Override
	public ReporteFotografico create(Connection conn, ReporteFotografico object) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into reporte_fotografico(");
		sql.append("s1_titulo, s1_division_region, s1_objetivo, s1_supervisor, s1_oficina, s2_planta_emergencia, s2_azotea_prop_compartida ");
		sql.append(", s2_local_prop_rentado, s2_cuenta_estacionamiento, s2_num_cajas_ventanilla, s2_num_ejecutivos, s2_lugares_ejec_disponibles, s2_cuenta_banca_personal ");
		sql.append(", s3_cuenta_senalizacion_prot_civ_completa, s3_num_extintores, s3_cuenta_llave_switch, s3_cuenta_contactores_alumbrado_aa, s3_num_atms ");
		sql.append(", s3_num_pc, s3_cuenta_piso_alfombra, s4_capacidad_ups_marca, s4_cuenta_calefaccion, s4_garantia_impermeabilizacion, s4_facilidad_acceso_azotea ");
		sql.append(", s4_cuenta_con_podio, s4_num_pantallas");
		sql.append(")");
		sql.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");		
		log.warning("sql=" + sql.toString());
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, object.getS1Titulo());
			ps.setString(2, object.getS1DivisionRegion());
			ps.setString(3, object.getS1Objetivo());
			ps.setString(4, object.getS1Supervisor());
			ps.setString(5, object.getS1Oficina());
			ps.setString(6, object.getS2PlantaEmergencia());
			ps.setString(7, object.getS2AzoteaPropCompartida());
			ps.setString(8, object.getS2LocalPropRentado());
			ps.setString(9, object.getS2CuentaEstacionamiento());
			ps.setString(10, object.getS2NumCajasVentanilla());
			ps.setString(11, object.getS2NumEjecutivos());
			ps.setString(12, object.getS2LugaresEjecDisponibles());
			ps.setString(13, object.getS2CuentaBancaPersonal());
			ps.setString(14, object.getS3CuentaSenalizacionProtCivCompleta());
			ps.setString(15, object.getS3NumExtintores());
			ps.setString(16, object.getS3CuentaLlaveSwitch());
			ps.setString(17, object.getS3CuentaContactoresAlumbradoAA());
			ps.setString(18, object.getS3NumAtms());
			ps.setString(19, object.getS3NumPc());
			ps.setString(20, object.getS3CuentaPisoAlfombra());
			ps.setString(21, object.getS4CapacidadUpsMarca());
			ps.setString(22, object.getS4CuentaCalefaccion());
			ps.setString(23, object.getS4GarantiaImpermeabilizacion());
			ps.setString(24, object.getS4FacilidadAccesoAzotea());
			ps.setString(25, object.getS4CuentaConPodio());
			ps.setString(26, object.getS4NumPantallas());
			
			if (ps.executeUpdate() > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next()) object.setReporteFotograficoId(BigInteger.valueOf(rs.getInt(1)));
			} else {
				log.warning("No se grabo");
			}
		} catch (Exception e) {
			log.warning("ReporteFotografico.create.error=" + e.getMessage());
		}

		return object;
	}

	@Override
	public ReporteFotografico update(Connection conn, ReporteFotografico object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReporteFotografico delete(Connection conn, ReporteFotografico object) {
		// TODO Auto-generated method stub
		return null;
	}

}
