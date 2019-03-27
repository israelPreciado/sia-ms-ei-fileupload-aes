package com.sia;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

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
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			document.add(new Paragraph("Hola mundo PDF"));
			document.close();
			
		} catch(DocumentException de) {
			
		}
	}	

}
