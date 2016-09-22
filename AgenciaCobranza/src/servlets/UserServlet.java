package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.User;
import model.UserManager;

@WebServlet("/user_servlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String shortName = request.getParameter("shortName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String admin = request.getParameter("admin");
		boolean isAdmin = admin == null ? false : true;

		User newUser = new User(shortName, name, password, isAdmin);
		HttpSession session = request.getSession();
		
		try {

			User user = (User)session.getAttribute("userSession");
			UserManager.getInstance().addUser(user.getShortName(), user.getPassword(), newUser);
			session.setAttribute("mensaje", "El usuario se ingresó correctamente");
		} catch (Exception e) {
			session.setAttribute("mensaje", e.getMessage());
			// session.setAttribute("userError", e.getMessage());
			// response.sendRedirect("login.jsp");
		}
		response.sendRedirect("admin.jsp");
	}

}
