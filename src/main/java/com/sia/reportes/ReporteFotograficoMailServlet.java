package com.sia.reportes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.apphosting.api.ApiProxy;
import com.sia.pdf.ReporteFotograficoPDF;
import utilities.Constants;

import java.util.Map;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
//[END multipart_includes]

/**
 * Servlet implementation class MailServlet
 */
@SuppressWarnings("serial")
@WebServlet("/mail")
public class ReporteFotograficoMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private String emailTo;
	private String subject;	

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
			System.out.println("Constants.EMAIL_FROM=" + Constants.EMAIL_FROM);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Constants.EMAIL_FROM, Constants.PERSONAL));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo, "Mr. User"));
			msg.setSubject(subject);
			msg.setText("This is a test");
			Transport.send(msg);
		} catch (AddressException e) {
			System.out.println("catch:" + e.getMessage());
		} catch (MessagingException e) {
			System.out.println("catch:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("catch:" + e.getMessage());
		}
		// [END simple_example]
	}

	private void sendMultipartMail() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "...";

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Constants.EMAIL_FROM, Constants.PERSONAL));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo, "Mr. User"));
			msg.setSubject(subject);
			msg.setText(msgBody);

			// [START multipart_example]
			String htmlBody = ""; // ...
			/*URL url = new URL("https://storage.googleapis.com/sia-ms-ei/reportes-fotograficos/cc4woq407.pdf");
			byte[] attachmentData = IOUtils.toByteArray(url.openStream());	
			InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);*/
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html");
			mp.addBodyPart(htmlPart);
			MimeBodyPart attachment = new MimeBodyPart();		
			attachment.setFileName("cc4woq407.pdf");
			//attachment.setContent(attachmentDataStream, "application/pdf");
			attachment.setContent(new ReporteFotograficoPDF().generate(), "application/pdf");
			mp.addBodyPart(attachment);

			msg.setContent(mp);
			// [END multipart_example]

			Transport.send(msg);

		} catch (AddressException e) {
			System.out.println("catch:" + e.getMessage());
		} catch (MessagingException e) {
			System.out.println("catch:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("catch:" + e.getMessage());
		} catch(Exception e) {
			System.out.println("catch:" + e.getMessage());
		}
	}
}
