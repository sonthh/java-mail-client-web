package entity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class Attachment {
	private String fileName;
	private String base64;
	private Part part;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getFileExtension() {
		return FilenameUtils.getExtension(this.fileName);
	}

	public boolean isImage() {
		String ext = this.getFileExtension().toLowerCase();
		List<String> imageTypes = Arrays.asList("png", "jpg", "jpeg", "ico", "gif", "bmp", "svg");
		if (imageTypes.contains(ext))
			return true;
		return false;
	}

	public String getSize() {
		try {
			int bytes = IOUtils.toByteArray(part.getInputStream()).length;
			return getSize(bytes);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(getSize(44624));
	}

	public static String getSize(long size) {
		String s = "";
		double kb = size / 1024;
		double mb = kb / 1024;
		double gb = kb / 1024;
		double tb = kb / 1024;
		if (size < 1024) {
			s = size + " B";
		} else if (size >= 1024 && size < (1024 * 1024)) {
			s = String.format("%.2f", kb) + " KB";
		} else if (size >= (1024 * 1024) && size < (1024 * 1024 * 1024)) {
			s = String.format("%.2f", mb) + " MB";
		} else if (size >= (1024 * 1024 * 1024) && size < (1024 * 1024 * 1024 * 1024)) {
			s = String.format("%.2f", gb) + " GB";
		} else if (size >= (1024 * 1024 * 1024 * 1024)) {
			s = String.format("%.2f", tb) + " TB";
		}
		return s;
	}

}
