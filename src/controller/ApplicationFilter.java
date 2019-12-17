package controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.UserLogin;

@WebFilter("/*")
public class ApplicationFilter implements Filter {
	public ApplicationFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();

		// xử lí auth
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
			if (uri.startsWith("/login")) {
				response.sendRedirect(request.getContextPath() + "/compose");
				return;
			}
		} else {
			String[] URI = { "/compose", "/inbox", "/sent", "/draft", "/spam", "/download", "/read" };
			boolean check = false;
			for (String k : URI) {
				if (uri.startsWith(k)) {
					check = true;
					break;
				}
			}
			if (check) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
		}

		// xử lí khác
		String key = "linkActive";
		String titleKey = "title";
		if (uri.startsWith("/sent")) {
			request.setAttribute(key, "sent");
			request.setAttribute(titleKey, "Thư đã gửi");
		} else if (uri.startsWith("/draft")) {
			request.setAttribute(key, "draft");
			request.setAttribute(titleKey, "Thư nháp");
		} else if (uri.startsWith("/inbox")) {
			request.setAttribute(key, "inbox");
			request.setAttribute(titleKey, "Hộp thư đến");
		} else if (uri.startsWith("/trash")) {
			request.setAttribute(key, "trash");
			request.setAttribute(titleKey, "Thùng rác");
		} else if (uri.startsWith("/spam")) {
			request.setAttribute(key, "spam");
			request.setAttribute(titleKey, "Spam");
		} else if (uri.startsWith("/compose")) {
			request.setAttribute(titleKey, "Soạn thư mới");
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
