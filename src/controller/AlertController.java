package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/alert")
public class AlertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer msg = -1;
		try {
			msg = Integer.parseInt(request.getParameter("msg"));
		} catch (Exception e) {
		}
		String message = null;
		switch (msg) {
		case 1:
			message = "Đã xóa thành công";
			break;
		default:
			break;
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
