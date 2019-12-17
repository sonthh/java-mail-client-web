package service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.lang3.StringUtils;

import constant.Environment;
import entity.Attachment;
import entity.Gmail;
import entity.PageGmail;

public class FetchingEmailService {
	public static String FOLDER_INBOX = "INBOX";
	public static String FOLDER_SENT = "[Gmail]/Sent Mail";
	public static String FOLDER_DRAFT = "[Gmail]/Drafts";
	public static String FOLDER_TRASH = "[Gmail]/Trash";
	public static String FOLDER_SPAM = "[Gmail]/Spam";
	private Store store;
	private Folder folder;
	private Session session;
	private Properties properties;
	private static FetchingEmailService instance;
	// private ArrayList<MimeMultipart> mimeMultiparts;
	private Message[] messages;

	public static void main(String[] args) {
		FetchingEmailService mail = FetchingEmailService.getInstance();
		System.out.println(mail.checkLogin("tigersama2205@gmail.com", "tigersama"));
//		List<Gmail> list = mail.getTrashEmail(0).getGmails();
//		 List<Gmail> list = mail.findDraftMail(0);
//		 List<Gmail> list = mail.findInboxMail();
//		 List<Gmail> list = mail.findTrashMail(); //trash loi
//		 List<Gmail> list = mail.findSpamMail();
//		for (Gmail gmail : list) {
//			gmail.printOut();
//			System.out.println(
//					"======================================================================================================");
//		}

	}

	public static FetchingEmailService getInstance() {
		if (instance == null) {
			instance = new FetchingEmailService();
		}
		return instance;
	}

	public FetchingEmailService() {
		// mimeMultiparts = new ArrayList<>();
	}

	private Properties getServerProperties(String protocol, String host, String port) {
		Properties properties = new Properties();

		// server setting
		properties.put(String.format("mail.%s.host", protocol), host);
		properties.put(String.format("mail.%s.port", protocol), port);

		// SSL setting
		properties.setProperty(String.format("mail.%s.socketFactory.class", protocol),
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));

		return properties;
	}

	private void openInbox(String arg) {
		try {
			folder = store.getFolder(arg);
			folder.open(Folder.READ_WRITE);
		} catch (MessagingException e) {
		}
	}

	private void fetchProfile(Message[] messages) {
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.CONTENT_INFO);
		try {
			folder.fetch(messages, fp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public PageGmail getListEmail(String folderName, Integer pageNumber) {
		try {
			PageGmail page = new PageGmail();
			setPropertiesWithImap();
			openInbox(folderName);
			messages = folder.getMessages();
			fetchProfile(messages);
			List<Gmail> list = fetchMail(messages, pageNumber);
			page.setPageNumber(pageNumber);
			page.setGmails(list);
			page.setTotalOfElements(messages.length);
			page.setTotalOfPages(page.getTotalOfElements() / Environment.PAGE_SIZE + 1);
			return page;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PageGmail getSpamEmail(Integer pageNumber) {
		return getListEmail(FOLDER_SPAM, pageNumber);
	}

	public PageGmail getSentEmail(Integer pageNumber) {
		return getListEmail(FOLDER_SENT, pageNumber);
	}

	public PageGmail getInboxEmail(Integer pageNumber) {
		return getListEmail(FOLDER_INBOX, pageNumber);
	}

	public PageGmail getTrashEmail(Integer pageNumber) {
		return getListEmail(FOLDER_TRASH, pageNumber);
	}

	public PageGmail getDraftEmail(Integer pageNumber) {
		return getListEmail(FOLDER_DRAFT, pageNumber);
	}

	private Gmail convertMessageToGmail(Message message) {
		Gmail gmail = new Gmail();
		try {

			// MESSAGE NUMBER
			gmail.setMessageNumber(message.getMessageNumber());

			// FROM
			Address[] addresses = null;
			gmail.setFrom(parseAddressFrom(message.getFrom()[0].toString()));

			// CC
			addresses = message.getRecipients(RecipientType.CC);
			if (addresses != null) {
				ArrayList<String> cc = new ArrayList<>();
				for (Address a : addresses)
					cc.add(a.toString());
				gmail.setCc(cc);
			}
			// BCC
			addresses = message.getRecipients(RecipientType.BCC);
			if (addresses != null) {
				ArrayList<String> bcc = new ArrayList<>();
				for (Address a : addresses)
					bcc.add(a.toString());
				gmail.setBcc(bcc);
			}
			// TO
			addresses = message.getRecipients(RecipientType.TO);
			if (addresses != null) {
				ArrayList<String> to = new ArrayList<>();
				for (Address a : addresses)
					to.add(a.toString());
				gmail.setTo(to);
			}

			gmail.setSubject(message.getSubject());
			gmail.setDate(message.getSentDate());

			Part messagePart = message;
			gmail.setContent(getText(messagePart));
			gmail.setAttachments(getAttachments(message));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return gmail;
	}

	private List<Gmail> fetchMail(Message[] messages, Integer pageNumber) {
		List<Gmail> list = new ArrayList<>();
		final Integer PAGE_SIZE = Environment.PAGE_SIZE;
		// for (Message message : messages) {
		int start = messages.length - 1 - (pageNumber - 1) * PAGE_SIZE;
		int end = start - PAGE_SIZE + 1;
		for (int i = start; i >= end && i >= 0; i--) {// TODO
			Message message = messages[i];

			Gmail gmail = convertMessageToGmail(message);

			list.add(gmail);
		}
//		try {
////			folder.close(false);
////			store.close();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
		return list;
	}

	public List<Attachment> getAttachments(Message message) throws Exception {
		Object content = message.getContent();
		if (content instanceof String)
			return null;

		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			List<Attachment> result = new ArrayList<>();

			for (int i = 0; i < multipart.getCount(); i++) {
				result.addAll(getAttachments(multipart.getBodyPart(i)));
			}
			return result;

		}
		return null;
	}

	private List<Attachment> getAttachments(BodyPart part) throws Exception {
		List<Attachment> result = new ArrayList<>();
		Object content = part.getContent();
		if (content instanceof InputStream || content instanceof String) {
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) || StringUtils.isNotBlank(part.getFileName())) {
				Attachment attachment = new Attachment();

				InputStream inputStream = part.getInputStream();
				attachment.setFileName(part.getFileName());

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				// chuyển sang ảnh dạng base64
				byte[] bytes = buffer.toByteArray();
				String base64 = Base64.getEncoder().encodeToString(bytes);
				attachment.setBase64(base64);
				attachment.setPart(part);

				result.add(attachment);
				return result;
			} else {
				return new ArrayList<>();
			}
		}

		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				result.addAll(getAttachments(bodyPart));
			}
		}
		return result;
	}

	private String getText(Part p) throws MessagingException, IOException {
		@SuppressWarnings("unused")
		boolean textIsHtml = false;
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			textIsHtml = p.isMimeType("text/html");
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	private void setPropertiesWithImap() {
		properties = getServerProperties("imap", "imap.gmail.com", "993");
		session = Session.getInstance(properties);

		try {
			store = session.getStore("imap");
			store.connect(Environment.username, Environment.password);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public boolean checkLogin(String username, String password) {
		properties = getServerProperties("imap", "imap.gmail.com", "993");
		session = Session.getInstance(properties);

		try {
			store = session.getStore("imap");
			store.connect(username, password);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}

	private String parseAddressFrom(String from) {
		if (from.contains("<"))
			return from.split("<")[1].split(">")[0];
		return from;
	}

	public Message getMessage(String folderName, Integer messageNumber) throws MessagingException {
		setPropertiesWithImap();
		openInbox(folderName);
		Message message = folder.getMessage(messageNumber);
		return message;
	}

	public Message[] getMessages(String folderName, int[] messageNumbers) throws MessagingException {
		setPropertiesWithImap();
		openInbox(folderName);
		Message[] messages = folder.getMessages(messageNumbers);
		return messages;
	}

	public void deleteMessage(Message message) throws MessagingException {
		message.setFlag(Flags.Flag.DELETED, true);
		folder.close(true);
		store.close();
	}

	public void deleteMessages(Message[] messages) throws MessagingException {
		for (Message message : messages) {
			message.setFlag(Flags.Flag.DELETED, true);
		}
		folder.close(true);
		store.close();
	}

	public static String getFolderNameByType(String type) {
		String result = null;
		switch (type) {
		case "inbox":
			result = FOLDER_INBOX;
			break;
		case "sent":
			result = FOLDER_SENT;
			break;
		case "draft":
			result = FOLDER_DRAFT;
			break;
		case "spam":
			result = FOLDER_SPAM;
			break;
		default:
			break;
		}
		return result;
	}
}
