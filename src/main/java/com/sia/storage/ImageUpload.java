/**
 * 
 */
package com.sia.storage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import beans.Image;
import net.coobird.thumbnailator.Thumbnails;
import utilities.Constants;

/**
 * @author randd1
 *
 */
public class ImageUpload {
	private Image image;
	private Storage storage;
	private String url;
	private String fileName;
	private String randomFileName;	
	private String mediaLink;	
	private String fileExt;
	private Connection conn;
	
	public interface OnUpload {
		void onUpload(String response);
	}
	
	{
		try {
			// Instance Cloud Storage
			storage = StorageOptions.getDefaultInstance().getService();
			
			try {
				url = System.getProperty("cloudsql");
				try {
					if (conn == null)
						conn = DriverManager.getConnection(url);
				} catch (SQLException e) {
					System.out.println("Unable to connect to Cloud SQL: " + e.getMessage());
				}									
			} catch (Exception ex) {
				System.out.println("BLoque de inicializacion: " + ex.getMessage());
			}			
		} finally {
			// Nothing really to do here.
		}
	}
	
	public ImageUpload(Image image, Storage storage) {		
		this.image = image;
		this.storage = storage;				
	}
	
	public void setOnUploadListener(OnUpload listener) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYHHmmss");
			Calendar c = Calendar.getInstance();
			Date date = c.getTime();
			
			this.fileName = image.getFilePart().getSubmittedFileName();		
			this.fileExt = this.fileName.substring(this.fileName.lastIndexOf('.') + 1);
			String fileNameTemp = this.fileName.substring(0, this.fileName.lastIndexOf("."));
			this.randomFileName = fileNameTemp.replace(".", "").replace("#", "") + "_" + sdf.format(date) + "." + fileExt;				
			this.mediaLink = Constants.STORAGE_GOOGLE_API_URL + image.getBucketName() + "/" + image.getFolderName() + "/"
					+ randomFileName;				
			
			uploadFile(listener);		
			
		} catch (Exception ex) {
			listener.onUpload("{\"error\": \"1005:" + ex.getMessage() + "\"}");
		}		
	}
	
	private void uploadFile(OnUpload listener) {
		try {		
			BufferedImage thumbnail = Thumbnails.of(image.getFilePart().getInputStream()).size(150, 40).asBufferedImage();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(thumbnail, fileExt, os);
			byte[] bytes = os.toByteArray();
			os.close();
			// String imgBase64 = Base64.getEncoder().encodeToString(bytes);

			// Upload to Google cloud storage >>>>>>>>>>>>>>>>>>
			boolean save = true;
			String errorMessageUploadingFile = "";										

			try {
				// Modify access list to allow all users with link to read file
				List<Acl> acls = new ArrayList<>();
				acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
				// the inputstream is closed by default, so we don't need to close it here
				
				// Los inmutabilidad de los objectos, lo que significa que un objeto subido no puede cambiar durante toda su vida útil de almacenamiento.
				// Realizamos la carga imagen tamaño original
				BlobId blobId = BlobId.of(image.getBucketName(), image.getFolderName() + "/" + randomFileName);
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setAcl(acls)
						.setContentType(image.getFilePart().getContentType()).build();
				// Blob blob = storage.create(blobInfo, "a simple blob".getBytes(UTF_8));
				Blob blob = storage.create(blobInfo, image.getFilePart().getInputStream());				
			} catch (Exception ex) {
				save = false;
				errorMessageUploadingFile = ex.getMessage();
			}

			if (!save) {
				// No persistir datos
				listener.onUpload("{\"error\": \"1002" + errorMessageUploadingFile + "\"}");
				//out.println("{\"error\": \"1002" + errorMessageUploadingFile + "\"}");
			} else {
				// Persistir datos
				updateValues(listener, mediaLink);				
			}
		} catch (Exception ex) {			
			listener.onUpload("{\"error\": \"1004:" + ex.getMessage() + "\"}");
		}	
	}
	
	private void updateValues(OnUpload listener, String mediaLink) {
		listener.onUpload("[{\"resultado\": \"OK\", \"url\": \"" + image.getUrl() + "\", \"msg_delete\": \"" + "\"}]");
		try {
			PreparedStatement ps;
			String insertSql = "";	
			String errorMessageUploadingFile = "";
								
			insertSql = "insert into files_rep_fotografico(reporte_fotografico_id, public_name, tipo, name, url, num_seccion, num_imagen) ";
			insertSql += "values(?,?,?,?,?,?,?)";						
			
			// Si se envió una imagen en la carga (en el caso de actualizar, no es obligatorio envíar una imágen para poder cambiar el nombre)
			if (image.isFileData()) {
				// asignamos las nuevas urls
				image.setUrl(mediaLink);				
			}			
																	
			ps = conn.prepareStatement(insertSql);
			ps.setInt(1, image.getReporteFotograficoId()); // reporte fotografico id						
			ps.setString(2, this.fileName); // public name
			ps.setString(3, "imagen"); // tipo
			ps.setString(4, this.randomFileName); // nombre físico
			ps.setString(5, this.mediaLink); // url
			ps.setInt(6, image.getNumSeccion());
			ps.setInt(7, image.getNumImagen());
			
			if (ps.executeUpdate() > 0) {				
				listener.onUpload("[{\"resultado\": \"OK\", \"url\": \"" + image.getUrl() + "\", \"msg_delete\": \"" + errorMessageUploadingFile + "\"}]");
			} else {						
				listener.onUpload("{\"error\": \"Ocurrió un problema al persistir la información.\"}");
			}
		} catch(Exception ex) {					
			listener.onUpload("{\"error\": \"1003:" + ex.getMessage() + "\"}");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	}
}
