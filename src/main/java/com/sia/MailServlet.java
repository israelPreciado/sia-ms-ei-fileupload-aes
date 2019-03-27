package com.sia;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.sia.pdf.SiaPDF;

import utilities.Constants;

//[START simple_includes]
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//[END simple_includes]

//[START multipart_includes]
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
//[END multipart_includes]

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailServlet
 */
@SuppressWarnings("serial")
@WebServlet("/mail")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String emailTo;
	private String subject;
	private Constants constants = new Constants();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		try (PrintWriter out = response.getWriter()) {
			JSONObject obj = new JSONObject();
			
			emailTo = request.getParameter("email");
			subject = request.getParameter("subject");
			String type = request.getParameter("type");
			
			if (emailTo == null || "".equals(emailTo) || subject == null || "".equals(subject)) {
				out.println(obj.put("error", "Empty parameters"));
			} else {
				if (type != null && type.equals("multipart")) {
					out.print(obj.put("success", "Sending HTML email with attachment."));
					sendMultipartMail();
				} else {
					out.print(obj.put("success", "Sending simple email."));
					sendSimpleMail();
				}
			}			
		}		
	}

	private void sendSimpleMail() {
		// [START simple_example]
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(constants.EMAIL_FROM, constants.PERSONAL));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo, "Mr. User"));
			msg.setSubject(subject);
			msg.setText("This is a test");
			Transport.send(msg);
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// ...
		}
		// [END simple_example]
	}

	private void sendMultipartMail() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "...";

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(constants.EMAIL_FROM, constants.PERSONAL));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo, "Mr. User"));
			msg.setSubject(subject);
			msg.setText(msgBody);

			// [START multipart_example]
			String htmlBody = ""; // ...
			URL url = new URL("https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/cc4woq407.pdf");
			byte[] attachmentData = IOUtils.toByteArray(url.openStream());	
			InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html");
			mp.addBodyPart(htmlPart);
			MimeBodyPart attachment = new MimeBodyPart();		
			attachment.setFileName("cc4woq407.pdf");
			//attachment.setContent(attachmentDataStream, "application/pdf");
			attachment.setContent(new SiaPDF().generate(), "application/pdf");
			mp.addBodyPart(attachment);

			msg.setContent(mp);
			// [END multipart_example]

			Transport.send(msg);

		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// ...
		} catch(Exception e) {
			
		}
	}
}
