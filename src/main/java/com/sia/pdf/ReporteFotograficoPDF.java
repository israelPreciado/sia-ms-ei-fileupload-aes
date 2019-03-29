/**
 * 
 */
package com.sia.pdf;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author randd1
 *
 */
public class ReporteFotograficoPDF {
	public InputStream generate() throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Rectangle pageSize = new Rectangle(500, 400);
			pageSize.setBackgroundColor(new Color(26, 22, 73));
			Document document = new Document(pageSize);
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(new Paragraph("Hola mundo PDF " + new Date().toString()));
			document.close();
			
			InputStream in = new ByteArrayInputStream(out.toByteArray());
			return in;
		} catch(DocumentException de) {
			throw new Exception(de.getMessage());
		}
	}
}
