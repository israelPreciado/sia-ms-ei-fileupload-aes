package com.sia.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProcessData {
	public JSONArray toJSONArray(PreparedStatement ps) throws Exception {
		try {
			JSONArray jArray = new JSONArray();
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				int totalColumns = rs.getMetaData().getColumnCount();
				
				for (int i = 0; i < totalColumns; i++) {
					obj.put(rs.getMetaData().getColumnLabel(i + 1), rs.getObject(i + 1));
				}
				jArray.put(obj);
			}
			
			return jArray;
		} catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}		
	}
}
