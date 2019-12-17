package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Attachment;
import entity.Gmail;

@WebServlet("/download")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String t = request.getParameter("t");
		Integer o = Integer.parseInt(request.getParameter("o"));
		Integer f = Integer.parseInt(request.getParameter("f"));
		
		String sessionKey = "gmails-inbox";
		switch (t) {
		case "inbox":
			sessionKey = "gmails-inbox";
			break;
		case "sent":
			sessionKey = "gmails-sent";
			break;
		case "draft":
			sessionKey = "gmails-draft";
			break;
		case "spam":
			sessionKey = "gmails-spam";
			break;
		default:
			break;
		}
		@SuppressWarnings("unchecked")
		List<Gmail> gmails = (List<Gmail>) session.getAttribute(sessionKey);
		Gmail gmail = gmails.get(o);
		List<Attachment> attachments = gmail.getAttachments();
		if (attachments != null && attachments.size() > 0) {
			Attachment attachment = attachments.get(f);
			Part part = attachment.getPart();
			OutputStream out = response.getOutputStream();
			InputStream in = null;
			try {
				in = part.getInputStream();
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			try {
				response.setContentType(part.getContentType());
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			response.setHeader("Content-disposition",
					String.format("attachment; filename=\"%s\"", attachment.getFileName()));

			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
		}

	}

}
