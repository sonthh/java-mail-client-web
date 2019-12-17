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

@WebServlet(urlPatterns = "/read")
public class ReadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String t = request.getParameter("t");
		Integer o = Integer.parseInt(request.getParameter("o"));
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
		request.setAttribute("gmail", gmail);
		String title = "Mail Client";
		if (gmail.getSubject() != null && !gmail.getSubject().trim().equals("")) {
			title = gmail.getSubject();
		}
		request.setAttribute("title", title);
        request.getRequestDispatcher("/WEB-INF/views/read.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
