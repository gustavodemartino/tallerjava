package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Login;
import data.User;
import model.Constants;
import model.UserManager;

@WebServlet("/user_servlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append(Constants.ERROR_MSG_UNSUPORTED_METHOD);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String shortName = request.getParameter("shortName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		boolean isAdmin = request.getParameter("admin") != null;

		User newUser = new User(shortName, name, password, isAdmin);
		HttpSession session = request.getSession();

		try {
			Login loginInfo = (Login) session.getAttribute(Constants.IDENTFIER_SESSION_LOGIN_INFO);
			UserManager.getInstance().addUser(loginInfo.getUser().getShortName(), loginInfo.getUser().getPassword(), newUser);
			session.setAttribute("mensaje", "El usuario se ingresó correctamente");
		} catch (Exception e) {
			session.setAttribute("mensaje", e.getMessage());
			response.sendRedirect("login.jsp");
		}
		response.sendRedirect("userlist.jsp");
	}

}
