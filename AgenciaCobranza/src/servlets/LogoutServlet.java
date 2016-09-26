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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Login loginInfo = (Login) session.getAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO);
		session.removeAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO);
		try {
			AuditManager.getInstance().register(loginInfo.getUser().getId(), loginInfo.getLocation().getId(),
					AuditManager.AUDIT_EVENT_LOGOUT, AuditManager.EVENT_LEVEL_INFO, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
