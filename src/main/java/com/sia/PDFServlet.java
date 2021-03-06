package com.sia;

import java.awt.Color;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.apphosting.api.ApiProxy;
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
import com.sia.model.FilesRepFotografico;
import com.sia.model.ReporteFotografico;
import com.sia.pdf.ReporteFotograficoDataTest;
import com.sia.pdf.ReporteFotograficoSeccion1;
import com.sia.pdf.ReporteFotograficoSeccion2;
import com.sia.pdf.ReporteFotograficoSeccion3;
import com.sia.pdf.ReporteFotograficoSeccion4;
import com.sia.services.FilesRepFotograficoServiceImpl;
import com.sia.services.ReporteFotograficoServiceImpl;
import com.sia.services.interfaces.FilesRepFotograficoService;
import com.sia.services.interfaces.ReporteFotograficoService;

/**
 * Servlet implementation class PDFServlet
 */
@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		
		try {							
			Rectangle pageSize = new Rectangle(600, 500);
			pageSize.setBackgroundColor(new Color(7, 33, 70));
			Document document = new Document(pageSize);
			document.setMargins(0, 0, 0, 0);
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			// parametro
			String strReporteFotograficoId = request.getParameter("rfid");
			Integer reporteFotograficoId = 0;
			if(strReporteFotograficoId != null && !"".equals(strReporteFotograficoId)) reporteFotograficoId = Integer.valueOf(strReporteFotograficoId);
			
			// datos
			ReporteFotograficoService reporteFotograficoService = new ReporteFotograficoServiceImpl();	
			FilesRepFotograficoService filesRepFotograficoService = new FilesRepFotograficoServiceImpl();
			ReporteFotografico rf = reporteFotograficoService.findById(conn, BigInteger.valueOf(reporteFotograficoId));
			List<FilesRepFotografico> files = filesRepFotograficoService.findAllByReporteFotograficoId(conn, BigInteger.valueOf(reporteFotograficoId));
			//ReporteFotografico rf = new ReporteFotograficoDataTest().getData();
									
			// [START constants]
			final float PADDING = 20f;			
			Font fontWhiteDefault = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.WHITE);
			Font fontWhite34 = FontFactory.getFont(FontFactory.HELVETICA, 34, Font.NORMAL, Color.WHITE);			
			Font fontBlackDefault = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.BLACK);
			Font fontBlackBold18 = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, Color.BLACK);
			Font fontBlack24 = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.NORMAL, Color.BLACK);
			Font fontBlack34 = FontFactory.getFont(FontFactory.HELVETICA, 34, Font.NORMAL, Color.BLACK);
			// [END constants]
			
			PdfPCell cell = null;
			
			// [START seccion 1]
			new ReporteFotograficoSeccion1().generate(rf, document, false, cell, fontWhiteDefault, fontWhite34, PADDING);								
			// [END seccion 1]
			
			// [START seccion 2]
			new ReporteFotograficoSeccion2().generate(rf, document, true, cell, fontBlackDefault, fontBlackBold18, PADDING);								
			// [END seccion 2]
			
			// [START seccion 3]
			new ReporteFotograficoSeccion3().generate(rf, document, true, cell, fontBlackDefault, fontBlackBold18, PADDING);								
			// [END seccion 3]
			
			// [START seccion 4]
			new ReporteFotograficoSeccion4().generate(rf, document, true, cell, fontBlackDefault, fontBlackBold18, PADDING);								
			// [END seccion 4]
			
			// [START imagenes]
			PdfPTable tableImages = null;
			int seccion = 0;
			if(!files.isEmpty()) seccion = files.get(0).getNumSeccion();
			int imagenesPorSeccion = 0;
			
			for(int i = 0; i < files.size(); i++) {
				if(i == 0) {
					Rectangle pageSize2 = new Rectangle(600, 600);			
					pageSize2.setBackgroundColor(new Color(255, 255, 255));
					document.setPageSize(pageSize2);
					document.newPage();
					document.add(pageSize2);
					
					generaEncabezadoTabla(document, cell, fontBlackBold18, PADDING, files, i);
					
					tableImages = new PdfPTable(2);
					tableImages.setWidthPercentage(100);
				}
				if(seccion != files.get(i).getNumSeccion()) {					
					if(imagenesPorSeccion % 2 == 0) {
						// celda par solo se añade al documento
						document.add(tableImages);
					} else {						
						// celda impar, entonces se agrega una celda vacía para completar la tabla y se añade al documento
						cell = new PdfPCell();			
						cell.setBorder(Rectangle.NO_BORDER);							
						tableImages.addCell(cell);
						document.add(tableImages);
						
						imagenesPorSeccion = 0;
					}
					
					//seccion = files.get(i).getNumSeccion();
					Rectangle pageSize2 = new Rectangle(600, 600);			
					pageSize2.setBackgroundColor(new Color(255, 255, 255));
					document.setPageSize(pageSize2);
					document.newPage();
					document.add(pageSize2);
					
					generaEncabezadoTabla(document, cell, fontBlackBold18, PADDING, files, i);
					
					tableImages = new PdfPTable(2);
					tableImages.setWidthPercentage(100);
				}
																								
				// cell foto 			
				String imgUrl = files.get(i).getUrl();
				Image img = Image.getInstance(new URL(imgUrl));					
				img.scaleToFit(250f, 250f);	
				
				cell = new PdfPCell(img);			
				cell.setBorder(Rectangle.NO_BORDER);	
				cell.setPadding(4);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tableImages.addCell(cell);													
				
				if(seccion != files.get(i).getNumSeccion()) {
					seccion = files.get(i).getNumSeccion();					
				}
				
				imagenesPorSeccion += 1;
			}
			
			if(files.size() == 1) {
				if(imagenesPorSeccion % 2 != 0) {
					// celda impar, entonces se agrega una celda vacía para completar la tabla y se añade al documento
					cell = new PdfPCell();			
					cell.setBorder(Rectangle.NO_BORDER);							
					tableImages.addCell(cell);										
				}
				document.add(tableImages);
			}
			if(files.size() > 1) {
				if(imagenesPorSeccion % 2 != 0) {
					// celda impar, entonces se agrega una celda vacía para completar la tabla y se añade al documento
					cell = new PdfPCell();			
					cell.setBorder(Rectangle.NO_BORDER);							
					tableImages.addCell(cell);										
				}
				document.add(tableImages);
			}
			// [END imagenes]
						
			document.close();
			
		} catch(DocumentException de) {
			
		}
	}	

	private void generaEncabezadoTabla(Document document, PdfPCell cell, Font fontBlackBold18, float PADDING, List<FilesRepFotografico> files, int i) throws MalformedURLException, IOException {		
		try {
			PdfPTable tableHeader = new PdfPTable(2);
			tableHeader.setWidthPercentage(100);
			tableHeader.setWidths(new float[] {1.4f, 0.6f});
			
			String titulo = "";
			
			switch(files.get(i).getNumSeccion()) {
				case 5: titulo = "EVIDENCIA FOTOGRAFICA AUTOSERVICIO MAXIMO 6 FOTOS";
				break;
				case 6: titulo = "EVIDENCIA FOTOGRAFICA PATIO PUBLICO MAXIMO 4 FOTOS";
				break;
				case 7: titulo = "EVIDENCIA FOTOGRAFICA AREA EJECUTIVOS MAXIMO 4 FOTOS";
				break;
				case 8: titulo = "EVIDENCIA FOTOGRAFICA AREA DE VENTANILLA MAXIMO 4 FOTOS";
				break;
				case 9: titulo = "EVIDENCIA FOTOGRAFICA BANCA PERSONAL MAXIMO 4 FOTOS";
				break;
				case 10: titulo = "EVIDENCIA FOTOGRAFICA AREA DE PASILLOS Y VESTIBULOS MAXIMO 4 FOTOS";
				break;
				case 11: titulo = "EVIDENCIA FOTOGRAFICA AREAS DE IMPRESION MAXIMO 4 FOTOS";
				break;
				case 12: titulo = "EVIDENCIA FOTOGRAFICA AREA DE SANITARIOS MAXIMO 9 FOTOS";
				break;
				case 13: titulo = "EVIDENCIA FOTOGRAFICA AREA DE COCINA MAXIMO 4 FOTOS";
				break;
				case 14: titulo = "EVIDENCIA FOTOGRAFICA AREA DE ARCHIVO MAXIMO 4 FOTOS";
				break;
				case 15: titulo = "EVIDENCIA FOTOGRAFICA AREA DE PAPELERIA MAXIMO 4 FOTOS";
				break;
				case 16: titulo = "EVIDENCIA FOTOGRAFICA SEÑALIZACION PROTECCION CIVIL MAXIMO 6 FOTOS";
				break;
				case 17: titulo = "EVIDENCIA FOTOGRAFICA EXTINTORES INSTALADOS MAXIMO 6 FOTOS";
				break;
				case 18: titulo = "EVIDENCIA FOTOGRAFICA AREA DE TABLEROS GENERALES MAXIMO 6 FOTOS";
				break;
				case 19: titulo = "EVIDENCIA FOTOGRAFICA TABLEROS ELECTRICOS REGULADOS MAXIMO 4 FOTOS";
				break;
				case 20: titulo = "EVIDENCIA FOTOGRAFICA ON-LINE MAXIMO 6 FOTOS";
				break;
				case 21: titulo = "EVIDENCIA FOTOGRAFICA AREA DE MEDIDORES CFE-AGUA";
				break;
				case 22: titulo = "EVIDENCIA FOTOGRAFICA AREA DE AZOTEA MAXIMO 9 FOTOS";
				break;
				case 23: titulo = "EVIDENCIA FOTOGRAFICA PLANTA DE EMERGENCIA SI CUENTA CON EQUIPOS MAXIMO 4 FOTOS";
				break;
				case 24: titulo = "EVIDENCIA FOTOGRAFICA AREA DE EQUIPOS AIRE ACONDICIONADO MAXIMO 12 FOTOS";
				break;
				case 25: titulo = "EVIDENCIA FOTOGRAFICA NECESIDADES BAJO CONSERVACION";
				break;
			}
			
			// cell 1
			cell = new PdfPCell(new Phrase(titulo, fontBlackBold18));
			cell.setPaddingLeft(PADDING);
			cell.setBorder(Rectangle.NO_BORDER);				
			cell.setPadding(PADDING);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			tableHeader.addCell(cell);
			
			// cell 2
			String imgLogoWhiteUrl = "https://storage.googleapis.com/sia-ms-ei-resources/reporte-fotografico/logo-bbva-blanco.png";
			Image imgLogoWhite = Image.getInstance(new URL(imgLogoWhiteUrl));					
			imgLogoWhite.scaleToFit(173f, 51f);	
			
			cell = new PdfPCell(imgLogoWhite);
			cell.setBorder(Rectangle.NO_BORDER);			
			cell.setPaddingTop(PADDING);			
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
			tableHeader.addCell(cell);
			
			document.add(tableHeader);
		} catch(DocumentException de) {
			
		}
	}
}
