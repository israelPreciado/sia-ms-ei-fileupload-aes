package com.sia;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPage;
import com.lowagie.text.pdf.PdfWriter;

import com.sia.model.ReporteFotografico;
import com.sia.pdf.ReporteFotograficoDataTest;

/**
 * Servlet implementation class PDFServlet
 */
@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;           

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		
		try {	
			/*PdfDocument pdf = new PdfDocument(new PdfWriter());
			Rectangle ps = new Rectangle(500, 400);
			ps.setBackgroundColor(new Color(216, 91, 65));
			//PdfPage page1 = pdf.add(ps);
			pdf.add(ps);
			
			Document document = new Document();
			document.newPage();*/
			
			
			Rectangle pageSize = new Rectangle(600, 500);
			pageSize.setBackgroundColor(new Color(7, 33, 70));
			Document document = new Document(pageSize);
			document.setMargins(0, 0, 0, 0);
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			// datos
			ReporteFotografico rf = new ReporteFotograficoDataTest().getData();
									
			// [START constants]
			final float PADDING = 20f;			
			Font fontWhiteDefault = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.WHITE);
			Font fontWhite34 = FontFactory.getFont(FontFactory.HELVETICA, 34, Font.NORMAL, Color.WHITE);			
			Font fontBlackDefault = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.BLACK);
			Font fontBlackBold18 = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, Color.BLACK);
			Font fontBlack34 = FontFactory.getFont(FontFactory.HELVETICA, 34, Font.NORMAL, Color.BLACK);
			// [END constants]
			
			PdfPCell cell = null;
			
			// [START seccion 1]
			
			// tabla 1
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);		
			table1.setWidths(new float[] {0.7f, 1.3f});
			table1.setSpacingAfter(20);		
			
			// image logo
			String imgLogoUrl = "https://storage.googleapis.com/sia-ms-ei-resources/reporte-fotografico/logo-bbva-bancomer.png";
			Image imgLogo = Image.getInstance(new URL(imgLogoUrl));					
			imgLogo.scaleToFit(169f, 33f);			
						
			// celda 1 - imagen logo
			cell = new PdfPCell(imgLogo);
			cell.setColspan(2);
			cell.setBorder(Rectangle.NO_BORDER);
			//cell.setFixedHeight(33f);
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
			
			// [END seccion 1]
			
			
			// [START seccion 5 EVIDENCIA FOTOGRAFICA AREA EXTERIOR]						
			Rectangle pageSize2 = new Rectangle(600, 600);			
			pageSize2.setBackgroundColor(new Color(255, 255, 255));
			document.setPageSize(pageSize2);
			document.newPage();
			document.add(pageSize2);
			
			PdfPTable tableS5Header = new PdfPTable(2);
			tableS5Header.setWidthPercentage(100);
			tableS5Header.setWidths(new float[] {1.4f, 0.6f});
			
			// cell 1
			cell = new PdfPCell(new Phrase("EVIDENCIA FOTOGRAFICA AREA EXTERIOR", fontBlackBold18));
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);				
			cell.setPadding(PADDING);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			tableS5Header.addCell(cell);
			
			// cell 2
			String imgLogoWhiteUrl = "https://storage.googleapis.com/sia-ms-ei-resources/reporte-fotografico/logo-bbva-blanco.png";
			Image imgLogoWhite = Image.getInstance(new URL(imgLogoWhiteUrl));					
			imgLogoWhite.scaleToFit(173f, 51f);	
			
			cell = new PdfPCell(imgLogoWhite);
			cell.setBorder(Rectangle.NO_BORDER);			
			cell.setPaddingTop(PADDING);			
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			tableS5Header.addCell(cell);
			
			document.add(tableS5Header);
			
			PdfPTable tableS5Images = new PdfPTable(2);
			tableS5Images.setWidthPercentage(100);
			//tableS5Images.setWidths(new float[] {1.4f, 0.6f});
			
			// cell foto 1			
			String imgUrl = "https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/867D4997-3486-4C07-A6CE-C943C73C651F_24032019224219.png";
			Image img = Image.getInstance(new URL(imgUrl));					
			img.scaleToFit(250f, 250f);	
			
			cell = new PdfPCell(img);			
			cell.setBorder(Rectangle.NO_BORDER);	
			cell.setPadding(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableS5Images.addCell(cell);
			
			// cell foto 2
			imgUrl = "https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/2f5003b6-8844-4c8a-85e1-24c810ba3ea8_25032019233613.jpg";
			img = Image.getInstance(new URL(imgUrl));					
			img.scaleToFit(250f, 250f);	
			
			cell = new PdfPCell(img);			
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPadding(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableS5Images.addCell(cell);
			
			// cell foto 3			
			imgUrl = "https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/2380B0F4-2A89-47F2-9D18-C742FB14E823_24032019011932.png";
			img = Image.getInstance(new URL(imgUrl));					
			img.scaleToFit(250f, 250f);	
			
			cell = new PdfPCell(img);			
			cell.setBorder(Rectangle.NO_BORDER);	
			cell.setPadding(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableS5Images.addCell(cell);
			
			// cell foto 4
			imgUrl = "https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/estimacio%20costo%20sia_24032019075000.png";
			img = Image.getInstance(new URL(imgUrl));					
			img.scaleToFit(250f, 250f);	
			
			cell = new PdfPCell(img);			
			cell.setBorder(Rectangle.NO_BORDER);	
			cell.setPadding(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableS5Images.addCell(cell);
			
			document.add(tableS5Images);
			
			// [END seccion 5]
			
			document.close();
			
		} catch(DocumentException de) {
			
		}
	}	

}
