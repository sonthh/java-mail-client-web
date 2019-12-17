package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import constant.Environment;

public class SendingEmailService {
	private static SendingEmailService instance;
	private Message mimeMessage;
	
	public static SendingEmailService getInstance() {
		if (instance == null) {
			instance = new SendingEmailService();
		}
		return instance;
	}

	public SendingEmailService() {
	}

	public static void main(String[] args) throws MessagingException, IOException {
		ArrayList<String> toList = new ArrayList<>();
		toList.add("tranhuuhongson@gmail.com");
		
		ArrayList<String> ccList = new ArrayList<>();
		ccList.add("tranhuuhongson+1@gmail.com");
		
		ArrayList<String> bccList = new ArrayList<>();
		bccList.add("tranhuuhongson+2@gmail.com");
		
		String subject = "test subject";
		String content = "test content";
		// File[] attachFiles;
		SendingEmailService.getInstance().sendMail(toList, ccList, bccList, subject, content, null);
	}

	public Properties smtpProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", String.valueOf(587));
//		properties.setProperty("mail.user", "tranhuuhongson@gmail.com");
//		properties.setProperty("mail.password", "tranhuuhongSon1998@");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.mime.charset", "utf-8");

		return properties;
	}

	private Session createSession() {
		Properties properties = smtpProperties();
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Environment.username, Environment.password);
			}
		});
		return session;
	}

	public void sendMail(List<String> toList, List<String> ccList, List<String> bccList, String subject, String content,
			List<File> attachFiles) throws MessagingException, IOException {
		Session session = createSession();
		if (session != null)
			mimeMessage = new MimeMessage(session);
		prepare(toList, ccList, bccList, subject, content, attachFiles);
		Transport.send(mimeMessage);
	}

	public void prepare(List<String> toList, List<String> ccList, List<String> bccList, String subject, String content,
			List<File> attachFiles) throws AddressException, MessagingException, IOException {
		mimeMessage.setFrom(new InternetAddress(Environment.username));

		setRecipientType(toList, Message.RecipientType.TO);

		if (ccList != null && ccList.size() > 0) {
			setRecipientType(ccList, Message.RecipientType.CC);
		}
		if (bccList != null && bccList.size() > 0) {
			setRecipientType(bccList, Message.RecipientType.BCC);
		}

		// mimeMessage.setSubject(subject);
		mimeMessage.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		mimeMessage.setSentDate(new Date());

		addContent(content, attachFiles);

	}

	private void setRecipientType(List<String> list, javax.mail.Message.RecipientType to) throws MessagingException {
		InternetAddress[] toAddresses = new InternetAddress[list.size()];
		for (int i = 0; i < list.size(); i++) {
			toAddresses[i] = new InternetAddress(list.get(i).trim());
		}

		for (int i = 0; i < list.size(); i++) {
			mimeMessage.addRecipient(to, toAddresses[i]);
		}
	}

	public void addContent(String content, List<File> attachFiles) throws MessagingException, IOException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html;charset=UTF-8");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		if (attachFiles != null && attachFiles.size() > 0) {
			for (File aFile : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(aFile);
				} catch (IOException ex) {
					throw ex;
				}

				multipart.addBodyPart(attachPart);
			}
		}
		mimeMessage.setContent(multipart);
	}

}
