package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import service.SendingEmailService;

@WebServlet(urlPatterns = "/compose")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ComposeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SendingEmailService sendingEmailService;
	public ComposeController() {
		this.sendingEmailService = SendingEmailService.getInstance();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/compose.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String to = request.getParameter("to");
		String cc = request.getParameter("cc");
		String bcc = request.getParameter("bcc");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");

		List<String> toList = new ArrayList<>();
		String[] arTmp = to.split(",");
		for (int i = 0; i < arTmp.length; i++) {
			if (!arTmp[i].trim().equals("")) {
				toList.add(arTmp[i].trim());
			}
		}

		List<String> ccList = new ArrayList<>();
		arTmp = cc.split(",");
		for (int i = 0; i < arTmp.length; i++) {
			if (!arTmp[i].trim().equals("")) {
				ccList.add(arTmp[i].trim());
			}
		}

		List<String> bccList = new ArrayList<>();
		arTmp = bcc.split(",");
		for (int i = 0; i < arTmp.length; i++) {
			if (!arTmp[i].trim().equals("")) {
				bccList.add(arTmp[i].trim());
			}
		}

		List<File> listFiles = new ArrayList<>();
		Collection<Part> multiparts = request.getParts();
		if (multiparts.size() > 0) {
			for (Part part : request.getParts()) {
				String fileName = part.getSubmittedFileName();
				if (fileName == null || fileName.equals("")) {
					continue;
				}
				final String UPLOAD_DIRECTORY = request.getServletContext().getRealPath("") + "files";
				File dir = new File(UPLOAD_DIRECTORY);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
				File saveFile = new File(filePath);
				part.write(filePath);
				listFiles.add(saveFile);
			}
		}

		String message = "";
		try {
			sendingEmailService.sendMail(toList, ccList, bccList, subject, content, listFiles);
			message = "Email được gởi thành công";
		} catch (Exception ex) {
			ex.printStackTrace();
			message = "Có lỗi xảy ra trong quá trình gởi email, xin kiểm tra lại thông tin.";
		} finally {
			if (listFiles != null && listFiles.size() > 0) {
				for (File file : listFiles) {
					file.delete();
				}
			}
			request.setAttribute("to", to);
			request.setAttribute("cc", cc);
			request.setAttribute("bcc", bcc);
			request.setAttribute("content", content);
			request.setAttribute("subject", subject);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/views/compose.jsp").forward(request, response);
		}
	}
}
