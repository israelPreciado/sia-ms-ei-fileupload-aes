package com.sia;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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
			Rectangle pageSize = new Rectangle(500, 400);
			pageSize.setBackgroundColor(new Color(7, 33, 70));
			Document document = new Document(pageSize);
			document.setMargins(0, 0, 0, 0);
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			// datos
			ReporteFotografico rf = new ReporteFotograficoDataTest().getData();
			
			// image logo
			String imgLogoUrl = "https://storage.googleapis.com/sia-ms-ei-resources/reporte-fotografico/logo-bbva-bancomer.png";
			Image imgLogo = Image.getInstance(new URL(imgLogoUrl));	
			System.out.println("imgLogo.getWidth=" + imgLogo.getWidth());			
			imgLogo.scaleToFit(169f, 33f);
			//document.add(imgLogo);
			//document.add(new Paragraph("Hola mundo PDF"));
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);			
			table1.setSpacingAfter(20);		
			
			PdfPCell t1Cell1 = new PdfPCell();
			t1Cell1.setBorder(Rectangle.NO_BORDER);
			t1Cell1.setFixedHeight(33f);
			t1Cell1.setPadding(20f);
			t1Cell1.addElement(imgLogo);
			table1.addCell(t1Cell1);
			
			PdfPTable table2 = new PdfPTable(1);
			table2.addCell(new Paragraph(rf.getS1Titulo()));
			
			document.add(table1);
			document.add(table2);
			
			document.close();
			
		} catch(DocumentException de) {
			
		}
	}	

}
