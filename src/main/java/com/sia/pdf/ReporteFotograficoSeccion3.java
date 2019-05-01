/**
 * 
 */
package com.sia.pdf;

import java.awt.Color;
import java.net.URL;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.sia.model.ReporteFotografico;

import utilities.Constants;

/**
 * @author randd1
 *
 */
public class ReporteFotograficoSeccion3 {
	private static final Logger log = Logger.getLogger(ReporteFotograficoSeccion3.class.getName());
	
	public boolean generate(ReporteFotografico rf, Document document, boolean newPage, PdfPCell cell, Font fontBlackDefault, Font fontBlackBold18, float PADDING) {
		try {
			if(newPage) {
				Rectangle pageSize = new Rectangle(600, 500);			
				pageSize.setBackgroundColor(new Color(255, 255, 255));
				document.setPageSize(pageSize);
				document.newPage();
				document.add(pageSize);
			}
			
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			table1.setWidths(new float[] { 1.3f, 0.7f });
			table1.setSpacingAfter(20);

			// cell 1
			cell = new PdfPCell(new Phrase("MORFOLOGIA DE LA OFICINA", fontBlackBold18));			
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingLeft(PADDING);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			table1.addCell(cell);
						
			// image logo
			String imgLogoUrl = Constants.STORAGE_GOOGLE_API_URL + Constants.RECURSOS_BUCKET_NAME + "/reporte-fotografico/logo-bbva-blanco.png";
			Image imgLogo = Image.getInstance(new URL(imgLogoUrl));
			imgLogo.scaleToFit(173f, 51f);

			// cell 2
			cell = new PdfPCell(imgLogo);			
			cell.setBorder(Rectangle.NO_BORDER);			
			cell.setPadding(PADDING);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			
			table1.addCell(cell);			
			
			// cell 3
			cell = new PdfPCell(new Phrase("Cuenta con señalización protección civil completa: " + rf.getS3CuentaSenalizacionProtCivCompleta(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);			
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);

			// cell 4
			cell = new PdfPCell(new Phrase("Número de extintores: " + rf.getS3NumExtintores(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);
			
			// cell 5
			cell = new PdfPCell(new Phrase("Cuenta con llave switch: " + rf.getS3CuentaLlaveSwitch(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);
			
			// cell 6
			cell = new PdfPCell(new Phrase("Cuenta con contactores de alumbrado/ A/A: " + rf.getS3CuentaContactoresAlumbradoAA(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);
			
			// cell 7
			cell = new PdfPCell(new Phrase("Número de ATMS: " + rf.getS3NumAtms(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);
			
			// cell 8
			cell = new PdfPCell(new Phrase("Número de PC: " + rf.getS3NumPc(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);
			
			// cell 9
			cell = new PdfPCell(new Phrase("Cuenta con piso/alfombra: " + rf.getS3CuentaPisoAlfombra(), fontBlackDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(30f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);									

			document.add(table1);
		} catch (Exception e) {
			log.warning(e.getMessage());
			return false;
		}
		
		return true;
	}
}
