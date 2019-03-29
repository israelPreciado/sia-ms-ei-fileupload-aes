/**
 * 
 */
package com.sia.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author randd1
 *
 */
public class FilesRepFotografico {
	private BigInteger fileRepFotograficoId;
	private BigInteger reporteFotograficoId;	
	private String publicName;
	private String tipo;
	private String name;
	private String url;
	private int numSeccion;
	private int numImagen;
	private Date fecha;
	private short status;
	
	public BigInteger getFileRepFotograficoId() {
		return fileRepFotograficoId;
	}
	public void setFileRepFotograficoId(BigInteger fileRepFotograficoId) {
		this.fileRepFotograficoId = fileRepFotograficoId;
	}
	public BigInteger getReporteFotograficoId() {
		return reporteFotograficoId;
	}
	public void setReporteFotograficoId(BigInteger reporteFotograficoId) {
		this.reporteFotograficoId = reporteFotograficoId;
	}
	public String getPublicName() {
		return publicName;
	}
	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}	
}
