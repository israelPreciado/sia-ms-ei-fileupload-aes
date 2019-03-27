package com.sia;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sia.storage.ImageUpload;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import beans.Image;
import utilities.ConfigProperties;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String BUCKET_NAME = "sia-ms-ei";
	private static Storage storage = null;
	private String strResponse = "";	
       
	@Override
	public void init() throws ServletException {
		try {
			// Instance Cloud Storage
			storage = StorageOptions.getDefaultInstance().getService();			
		} finally {
			// Nothing really to do here.
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		try (PrintWriter out = response.getWriter()) {
			try {
				ConfigProperties configProperties = new ConfigProperties("api-key.properties");
				String apiKey = configProperties.getProperty("key");
				String strApiKey = request.getParameter("api_key");
				final Part filePart = request.getPart("file");
				final String strReporteFotograficoId = request.getParameter("rfid");	
				final String strNumSeccion = request.getParameter("num_seccion");
				final String strNumImagen = request.getParameter("num_imagen");
				final String fileName = filePart.getSubmittedFileName().toLowerCase();		
				final String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);				
				String type = request.getParameter("type");
				final String folderName = request.getParameter("folder");																												
				String url = request.getParameter("url");																		
				String bucketName = request.getParameter("bucket");	
				Integer reporteFotograficoId = 0, numSeccion = 0, numImagen = 0;
				boolean isImage = false;
				
				if (strReporteFotograficoId != null && !"".equals(strReporteFotograficoId)) reporteFotograficoId = Integer.valueOf(strReporteFotograficoId);
				if (strNumSeccion != null && !"".equals(strNumSeccion)) numSeccion = Integer.valueOf(strNumSeccion);
				if (strNumImagen != null && !"".equals(strNumImagen)) numImagen = Integer.valueOf(strNumImagen);
				if (bucketName != null && !"".equals(bucketName)) BUCKET_NAME = bucketName; 
								
				if (type.equals("imagen")) {
					isImage = true;
				}
				
				if ( strApiKey == null || !apiKey.equals(strApiKey) ) {						
					out.println("{\"error\": \"access denied\"}");						
				} else if (filePart == null || reporteFotograficoId == 0 || numSeccion == 0 || numImagen == 0) {
					out.println("{\"error\": \"1022: Empty parameters\"}");
				} else {
					if (isImage) {
						Image image = new Image();
						image.setFilePart(filePart);	
						image.setReporteFotograficoId(reporteFotograficoId);
						image.setNombreLogotipo("");
						image.setFolderName(folderName);
						image.setFileData(true);
						image.setUrl(""); // se asigna en la clase ImageUpload otro valor si es una imagen nueva	
						image.setNumSeccion(numSeccion);
						image.setNumImagen(numImagen);
						image.setBucketName(BUCKET_NAME);
						
						ImageUpload imageUpload = new ImageUpload(image, storage);
						imageUpload.setOnUploadListener(new ImageUpload.OnUpload() {
							
							@Override
							public void onUpload(String response) {
								strResponse = response;
							}
						});
					}
					
					out.println(strResponse);
				}
				
			} catch (Exception ex) {
				out.println("{\"error\": \"1000:" + ex.getMessage() + "\"}");
			}
		}
	}

}
