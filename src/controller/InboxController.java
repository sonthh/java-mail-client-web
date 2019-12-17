package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Gmail;
import entity.PageGmail;
import service.FetchingEmailService;

@WebServlet(urlPatterns = "/inbox")
public class InboxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FetchingEmailService fetchingEmailService;

	public InboxController() {
		fetchingEmailService = FetchingEmailService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer pageNumber = null;
		try {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			pageNumber = 1;
		}
		
		PageGmail page = fetchingEmailService.getInboxEmail(pageNumber);
		request.setAttribute("page", page);
		
		HttpSession session = request.getSession();
		List<Gmail> gmails = page.getGmails();
		session.setAttribute("gmails-inbox", gmails);
		
		request.getRequestDispatcher("/WEB-INF/views/inbox.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
