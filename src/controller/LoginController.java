package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constant.Environment;
import entity.UserLogin;
import service.FetchingEmailService;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		FetchingEmailService mail = FetchingEmailService.getInstance();
		boolean isCorrectAccount =  mail.checkLogin(username, password);
		if (isCorrectAccount) {
			HttpSession session = request.getSession();
			UserLogin userLogin = new UserLogin(username, password);
			session.setAttribute(UserLogin.USER_LOGIN_SESSION_KEY, userLogin);
			Environment.setAccount(userLogin);
			response.sendRedirect(request.getContextPath() + "/compose");
		} else {
			response.sendRedirect(request.getContextPath() + "/login?msg=error");
		}
	}
}
