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

@WebServlet("/login_servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append(Constants.ERROR_MSG_UNSUPORTED_METHOD);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		try {
			Login loginInfo = LoginManager.getInstance().login(userId, password, Constants.IDENTFIER_WEB_LOCATION_NAME);
			if (loginInfo != null) {
				loginInfo.getUser().setPassword(password);
				session.setAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO, loginInfo);
				AuditManager.getInstance().register(loginInfo.getUser().getId(), loginInfo.getLocation().getId(),
						AuditManager.AUDIT_EVENT_LOGIN, AuditManager.EVENT_LEVEL_INFO, null);
			}
			response.sendRedirect("admin.jsp");
		} catch (Exception e) {
			session.setAttribute("userError", e.getMessage());
			try {
				AuditManager.getInstance().register(-1, -1, AuditManager.AUDIT_EVENT_LOGIN_ERROR,
						AuditManager.EVENT_LEVEL_WARNING, "{\"nickname\":\"" + userId + "\",\"location\": \""
								+ Constants.IDENTFIER_WEB_LOCATION_NAME + "\"}");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			response.sendRedirect("login.jsp");
		}
	}

}
