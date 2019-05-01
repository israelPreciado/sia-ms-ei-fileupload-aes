/**
 * 
 */
package com.sia.pdf;

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
public class ReporteFotograficoSeccion1 {
	private static final Logger log = Logger.getLogger(ReporteFotograficoSeccion1.class.getName());
	
	public boolean generate(ReporteFotografico rf, Document document, boolean newPage, PdfPCell cell, Font fontWhiteDefault, Font fontWhite34, float PADDING) {
		try {
			// tabla 1
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			table1.setWidths(new float[] { 0.7f, 1.3f });
			table1.setSpacingAfter(20);

			// image logo
			String imgLogoUrl = Constants.STORAGE_GOOGLE_API_URL + Constants.RECURSOS_BUCKET_NAME + "/reporte-fotografico/logo-bbva-bancomer.png";
			Image imgLogo = Image.getInstance(new URL(imgLogoUrl));
			imgLogo.scaleToFit(169f, 33f);

			// celda 1 - imagen logo
			cell = new PdfPCell(imgLogo);
			cell.setColspan(2);
			cell.setBorder(Rectangle.NO_BORDER);
			// cell.setFixedHeight(33f);
			cell.setPadding(PADDING);
			// add cell
			table1.addCell(cell);
			// end logo

			// start titulo
			cell = new PdfPCell(new Phrase(rf.getS1Titulo(), fontWhite34));
			cell.setColspan(2);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingLeft(PADDING);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			// add cell
			table1.addCell(cell);

			// cell 5
			cell = new PdfPCell(new Phrase("División o Región:", fontWhiteDefault));
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(36f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			table1.addCell(cell);

			// cell 6
			cell = new PdfPCell(new Phrase(rf.getS1DivisionRegion(), fontWhiteDefault));
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingTop(36f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);

			// cell 7
			cell = new PdfPCell(new Phrase("Objetivo:", fontWhiteDefault));
			cell.setPaddingTop(10f);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table1.addCell(cell);

			// cell 8
			cell = new PdfPCell(new Phrase(rf.getS1Objetivo(), fontWhiteDefault));
			cell.setPaddingTop(10f);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);

			// cell 9
			cell = new PdfPCell(new Phrase("Supervisor/Consultor:", fontWhiteDefault));
			cell.setPaddingTop(10f);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table1.addCell(cell);

			// cell 10
			cell = new PdfPCell(new Phrase(rf.getS1Supervisor(), fontWhiteDefault));
			cell.setPaddingTop(10f);
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);

			// cell 11
			cell = new PdfPCell(new Phrase(rf.getS1Oficina(), fontWhiteDefault));
			cell.setColspan(2);
			cell.setPaddingLeft(PADDING);
			cell.setPaddingTop(40f);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);

			document.add(table1);
		} catch (Exception e) {
			log.warning(e.getMessage());
			return false;
		}
		
		return true;
	}
}
