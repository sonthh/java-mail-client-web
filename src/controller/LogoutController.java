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

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		boolean isAuthenticated = false;
		try {
			UserLogin userLogin = (UserLogin) session.getAttribute(UserLogin.USER_LOGIN_SESSION_KEY);
			if (userLogin != null) {
				isAuthenticated = true;
			}
		} catch (Exception e) {
			isAuthenticated = false;
		}
		if (isAuthenticated) {
			session.removeAttribute(UserLogin.USER_LOGIN_SESSION_KEY);
			Environment.resetAccount();
		}
		response.sendRedirect(request.getContextPath() + "/login");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
