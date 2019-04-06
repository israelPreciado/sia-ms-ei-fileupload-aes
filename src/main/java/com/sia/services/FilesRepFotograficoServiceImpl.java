/**
 * 
 */
package com.sia.services;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sia.model.FilesRepFotografico;
import com.sia.services.interfaces.FilesRepFotograficoService;
import com.sia.utilities.ProcessData;

/**
 * @author randd1
 *
 */
public class FilesRepFotograficoServiceImpl implements FilesRepFotograficoService {
	
	private static final Logger log = Logger.getLogger(FilesRepFotograficoServiceImpl.class.getName());

	@Override
	public FilesRepFotografico findById(Connection conn, BigInteger id) {
		FilesRepFotografico frf = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select file_rep_fotografico_id, reporte_fotografico_id, public_name, tipo, name");
			sql.append(", url, num_seccion, num_imagen");
			sql.append(" from files_rep_fotografico");
			sql.append(" where file_rep_fotografico_id = ?");
			
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id.intValue());
			JSONArray result = new ProcessData().toJSONArray(ps);
			
			for(int i = 0; i < result.length(); i++) {
				JSONObject obj = result.getJSONObject(i);	
				frf = new FilesRepFotografico();
				frf.setFileRepFotograficoId(BigInteger.valueOf(obj.getInt("file_rep_fotografico_id")));
				frf.setReporteFotograficoId(BigInteger.valueOf(obj.getInt("reporte_fotografico_id")));
				frf.setPublicName(obj.getString("public_name"));
				frf.setTipo(obj.getString("tipo"));
				frf.setName(obj.getString("name"));
				frf.setUrl(obj.getString("url"));
				frf.setNumSeccion(obj.getInt("num_seccion"));
				frf.setNumImagen(obj.getInt("num_imagen"));
			}
		} catch(Exception e) {
			log.warning(e.getMessage());
		}
		
		return frf;
	}

	@Override
	public FilesRepFotografico create(Connection conn, FilesRepFotografico object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilesRepFotografico update(Connection conn, FilesRepFotografico object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilesRepFotografico delete(Connection conn, FilesRepFotografico object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilesRepFotografico> findAllByReporteFotograficoId(Connection conn, BigInteger id) {
		List<FilesRepFotografico> files = new ArrayList<>();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select file_rep_fotografico_id, reporte_fotografico_id, public_name, tipo, name");
			sql.append(", url, num_seccion, num_imagen");
			sql.append(" from files_rep_fotografico");
			sql.append(" where reporte_fotografico_id = ?");
			sql.append(" order by num_seccion, num_imagen");
			
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id.intValue());
			JSONArray result = new ProcessData().toJSONArray(ps);
			
			for(int i = 0; i < result.length(); i++) {
				JSONObject obj = result.getJSONObject(i);	
				FilesRepFotografico frf = new FilesRepFotografico();
				frf.setFileRepFotograficoId(BigInteger.valueOf(obj.getInt("file_rep_fotografico_id")));
				frf.setReporteFotograficoId(BigInteger.valueOf(obj.getInt("reporte_fotografico_id")));
				frf.setPublicName(obj.getString("public_name"));
				frf.setTipo(obj.getString("tipo"));
				frf.setName(obj.getString("name"));
				frf.setUrl(obj.getString("url"));
				frf.setNumSeccion(obj.getInt("num_seccion"));
				frf.setNumImagen(obj.getInt("num_imagen"));
				files.add(frf);
			}
		} catch(Exception e) {
			log.warning(e.getMessage());
		}
		
		return files;
	}

}
