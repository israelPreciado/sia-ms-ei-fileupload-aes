package beans;

import javax.servlet.http.Part;

public class Image {
	private Part filePart;
	private Integer reporteFotograficoId;	
	private String nombreLogotipo;	
	private String folderName;
	private String url;	
	private String bucketName;	
	private int numSeccion;
	private int numImagen;
	private boolean fileData;

	public Image() {
		super();
	}

	public Image(Part filePart, Integer reporteFotograficoId, String nombreLogotipo, String folderName,
			String url, String bucketName, int numSeccion, int numImagen, boolean fileData) {
		super();
		this.filePart = filePart;
		this.reporteFotograficoId = reporteFotograficoId;
		this.nombreLogotipo = nombreLogotipo;
		this.folderName = folderName;
		this.url = url;
		this.bucketName = bucketName;
		this.numSeccion = numSeccion;
		this.numImagen = numImagen;
		this.fileData = fileData;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}	

	public Integer getReporteFotograficoId() {
		return reporteFotograficoId;
	}

	public void setReporteFotograficoId(Integer reporteFotograficoId) {
		this.reporteFotograficoId = reporteFotograficoId;
	}

	public String getNombreLogotipo() {
		return nombreLogotipo;
	}

	public void setNombreLogotipo(String nombreLogotipo) {
		this.nombreLogotipo = nombreLogotipo;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}			

	public int getNumSeccion() {
		return numSeccion;
	}

	public void setNumSeccion(int numSeccion) {
		this.numSeccion = numSeccion;
	}

	public int getNumImagen() {
		return numImagen;
	}

	public void setNumImagen(int numImagen) {
		this.numImagen = numImagen;
	}

	public boolean isFileData() {
		return fileData;
	}

	public void setFileData(boolean fileData) {
		this.fileData = fileData;
	}			
}
