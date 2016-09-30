package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Login;
import model.AuditManager;
import model.Constants;
import model.LoginManager;

@WebServlet(value ={"/login", "/logout"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		try {
			Login loginInfo = LoginManager.getInstance().login(userId, password,
					Constants.DB_IDENTFIER_WEB_LOCATION_NAME);
			if (loginInfo != null) {
				loginInfo.getUser().setPassword(password);
				session.setAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO, loginInfo);
				AuditManager.getInstance().register(loginInfo.getUser().getId(), loginInfo.getLocation().getId(),
						AuditManager.AUDIT_EVENT_LOGIN, AuditManager.EVENT_LEVEL_INFO, null);
			}
			response.sendRedirect("menu.jsp");
			session.setAttribute("userError", "");
		} catch (Exception e) {
			session.setAttribute("userError", e.getMessage());
			try {
				AuditManager.getInstance().register(-1, -1, AuditManager.AUDIT_EVENT_LOGIN_ERROR,
						AuditManager.EVENT_LEVEL_WARNING, "{\"nickname\":\"" + userId + "\",\"location\": \""
								+ Constants.DB_IDENTFIER_WEB_LOCATION_NAME + "\"}");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			response.sendRedirect("login.jsp");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Login loginInfo = (Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO);
		session.removeAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO);
		try {
			AuditManager.getInstance().register(loginInfo.getUser().getId(), loginInfo.getLocation().getId(),
					AuditManager.AUDIT_EVENT_LOGOUT, AuditManager.EVENT_LEVEL_INFO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("login.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			if (action.equals("/login")) {
				login(request, response);

			} else if (action.equals("/logout")) {
				logout(request, response);

			}
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}