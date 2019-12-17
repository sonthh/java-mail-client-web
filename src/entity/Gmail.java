package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Gmail implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer messageNumber;
	private String subject;
	private String content;
	private String from;
	private Date date;
	private ArrayList<String> cc;
	private ArrayList<String> bcc;
	private ArrayList<String> to;
	private List<Attachment> attachments;

	public void printOut() {
		System.out.println("Subject: " + this.subject);
		System.out.println("From: " + this.from);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
		System.out.println("Date: " + sdf.format(this.date));
		System.out.println("CC: ");
		if (cc != null && cc.size() > 0) {
			for (String item : cc) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
		System.out.println("BCC: ");
		if (bcc != null && bcc.size() > 0) {
			for (String item : bcc) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
		System.out.println("TO: ");
		if (to != null && to.size() > 0) {
			for (String item : to) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
		System.out.println("Content: ");
		System.out.println(this.content);
		System.out.println("File");

	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getCc() {
		return cc;
	}

	public void setCc(ArrayList<String> cc) {
		this.cc = cc;
	}

	public ArrayList<String> getBcc() {
		return bcc;
	}

	public void setBcc(ArrayList<String> bcc) {
		this.bcc = bcc;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	public void setTo(ArrayList<String> to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Integer getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(Integer messageNumber) {
		this.messageNumber = messageNumber;
	}

}
