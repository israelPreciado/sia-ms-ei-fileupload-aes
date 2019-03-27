package com.sia.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class SiaPDF {
	public InputStream generate() throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Document document = new Document();
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
