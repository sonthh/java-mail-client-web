package controller;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.FetchingEmailService;

@WebServlet(urlPatterns = "/delete")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DELETE_TYPE_MUTIPLE = "multiple";
	private static final String DELETE_TYPE_ONLY = "only";

	private FetchingEmailService fetchingEmailService;

	public DeleteController() {
		fetchingEmailService = FetchingEmailService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String t = request.getParameter("t");
		String deleteType = request.getParameter("DELETE_TYPE");
		String folderName = FetchingEmailService.getFolderNameByType(t);
		if (deleteType.equals(DELETE_TYPE_MUTIPLE)) {
			String[] ids = request.getParameterValues("id");
			if (ids != null && ids.length > 0) {
				int[] messageNumbers = Arrays.asList(ids).stream().mapToInt(Integer::parseInt).toArray();
				try {
					Message[] messages = fetchingEmailService.getMessages(folderName, messageNumbers);
					fetchingEmailService.deleteMessages(messages);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		} else if (deleteType.equals(DELETE_TYPE_ONLY)) {
			Integer messageNumber = Integer.parseInt(request.getParameter("id"));
			Message message;
			try {
				message = fetchingEmailService.getMessage(folderName, messageNumber);
				fetchingEmailService.deleteMessage(message);
			} catch (MessagingException e1) {
				e1.printStackTrace();
			}
		}

		response.sendRedirect(request.getContextPath() + "/alert?msg=1");
	}
}
