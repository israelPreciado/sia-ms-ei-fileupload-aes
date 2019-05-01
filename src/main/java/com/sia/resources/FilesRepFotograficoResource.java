package com.sia.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.apphosting.api.ApiProxy;

import utilities.ConfigProperties;
import utilities.Constants;

/**
 * Servlet implementation class FilesRepFotograficoServlet
 */
@WebServlet(name = "files-rep-fotografico", urlPatterns = { "/files-rep-fotografico" })
public class FilesRepFotograficoResource extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private Connection conn;

	{
		try {
			ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
			Map<String, Object> attr = env.getAttributes();
			String hostname = (String) attr.get("com.google.appengine.runtime.default_version_hostname");

			String url = hostname.contains("localhost:") ? System.getProperty("cloudsql-local") : System.getProperty("cloudsql");
			System.out.println("connecting to: " + url);
			try {
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				System.out.println("Unable to connect to Cloud SQL: " + e);
			}
		} finally {

		}
	}		
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// pre-flight request processing	 
	    resp.setContentType("application/json;charset=UTF-8");
	    resp.addHeader("Access-Control-Allow-Origin", "*");
	    resp.setHeader("Access-Control-Allow-Methods", "POST");
	    resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization" /* "Content-Type, Authorization, uuid, ramo" */);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("UTF-8");
		
		try (PrintWriter out = response.getWriter())  {
			JSONObject jMessage = new JSONObject();
			
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				
				// Get post parameters
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) sb.append(line);
                reader.close();

                //Conversion de buffer a JSONObject             
                JSONObject params = new JSONObject(sb.toString());
                
				String strApiKey = params.getString("api_key");
				
				
				final String strReporteFotograficoId = String.valueOf(params.getInt("rfid"));	
				final String strNumSeccion = String.valueOf(params.getInt("num_seccion"));
				final String strNumImagen = String.valueOf(params.getInt("num_imagen"));
				final String fileName = params.getString("name");						
				final String type = params.getString("type");			
				Integer reporteFotograficoId = 0, numSeccion = 0, numImagen = 0;
				boolean isImage = false;
								
				if (strReporteFotograficoId != null && !"".equals(strReporteFotograficoId)) reporteFotograficoId = Integer.valueOf(strReporteFotograficoId);				
				if (strNumSeccion != null && !"".equals(strNumSeccion)) numSeccion = Integer.valueOf(strNumSeccion);
				
				if (strNumImagen != null && !"".equals(strNumImagen)) numImagen = Integer.valueOf(strNumImagen);				
								
				if (type.equals("imagen")) {
					isImage = true;
				}
								
				//out.println(jMessage.put("result", "numSeccion=" + numSeccion).put("strNumSeccion", "->" + strNumSeccion).put("strApiKey", "->" + strApiKey));
								
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {						
					out.println("{\"error\": \"access denied\"}");						
				} else if (reporteFotograficoId == 0 || numSeccion == 0 || numImagen == 0) {
					out.println("{\"error\": \"1022: Empty parameters\"}");
				} else {
					if (isImage) {
						String mediaLink = Constants.STORAGE_GOOGLE_API_URL + Constants.BUCKET_NAME + "/" + fileName;
						
						PreparedStatement ps;
						String insertSql = "";							
											
						insertSql = "insert into files_rep_fotografico(reporte_fotografico_id, public_name, tipo, name, url, num_seccion, num_imagen) ";
						insertSql += "values(?,?,?,?,?,?,?)";																			
						
						if(conn == null) {
							out.println(jMessage.put("error", "1023: Connection null"));
						} else {
							ps = conn.prepareStatement(insertSql);
							ps.setInt(1, reporteFotograficoId); // reporte fotografico id						
							ps.setString(2, fileName); // public name
							ps.setString(3, "imagen"); // tipo
							ps.setString(4, fileName); // nombre físico
							ps.setString(5, mediaLink); // url
							ps.setInt(6, numSeccion);
							ps.setInt(7, numImagen);
							
							if (ps.executeUpdate() > 0) {											
								jMessage.put("result", "OK").put("url", mediaLink);
							} else {													
								jMessage.put("error", "Ocurrió un problema al persistir la información");
							}
							
							out.println(jMessage);
						}
						
					} else {						
						out.println(jMessage.put("error", "1024: File is not image"));
					}
				}				
			} catch (Exception ex) {
				out.println(jMessage.put("error", "1000:" + ex.getMessage()));				
			}			
		}
	}

}
