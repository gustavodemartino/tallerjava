package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Location;
import data.Login;
import data.User;
import model.Constants;
import model.UserManager;

@WebServlet(value = { "/new_user", "/get_user", "/modify_user", "/permit_user", "/delete_user" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	// ========================
	// Crea y modifica usuarios
	// ========================
	private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userid = Long.parseLong(request.getParameter("userid"));
		String shortName = request.getParameter("shortName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		boolean isAdmin = request.getParameter("admin") != null;

		User user = new User(userid, shortName, name, password, isAdmin);
		HttpSession session = request.getSession();
		Login loginInfo = (Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO);
		session.setAttribute(Constants.SESSION_IDENTFIER_USER_INFO, user);
		if (user.getId() == 0) {
			try {
				UserManager.getInstance().addUser(loginInfo.getUser().getShortName(), loginInfo.getUser().getPassword(),
						user);
				user = UserManager.getInstance().getUser(user.getShortName(), user.getPassword());
				session.setAttribute(Constants.SESSION_IDENTFIER_USER_INFO, user);
				session.setAttribute("mensaje", "El usuario se ingresó correctamente");

			} catch (Exception e) {
				session.setAttribute("mensaje", e.getMessage());
			}
		} else {
			try {
				UserManager.getInstance().modUser(loginInfo.getUser().getShortName(), loginInfo.getUser().getPassword(),
						user);
				session.setAttribute("mensaje", "El usuario se modificó correctamente");
			} catch (Exception e) {
				session.setAttribute("mensaje", e.getMessage());
			}
		}
		response.sendRedirect("usermod.jsp");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Login loginInfo = (Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO);
		long userid = Long.parseLong(request.getParameter("userid"));
		try {
			UserManager.getInstance().deleteUser(loginInfo.getUser().getShortName(), loginInfo.getUser().getPassword(),
					userid);
			session.setAttribute("mensaje", "El usuario se eliminó correctamente");
			response.sendRedirect("new_user");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("mensaje", Constants.ERROR_MSG_CANNOT_DELETE_USER);
			response.sendRedirect("usermod.jsp");
		}
	}

	private void getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = Long.parseLong(request.getParameter("selected_user"));
		User user = UserManager.getInstance().getUser(userId);
		request.getSession().setAttribute(Constants.SESSION_IDENTFIER_USER_INFO, user);
		request.getSession().setAttribute("mensaje", "");
		response.sendRedirect("usermod.jsp");
	}

	private void newUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User(0, "", "", false);
		request.getSession().setAttribute(Constants.SESSION_IDENTFIER_USER_INFO, user);
		response.sendRedirect("usermod.jsp");
	}

	private void permitUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserManager um = UserManager.getInstance();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_IDENTFIER_USER_INFO);
		String action = request.getParameter("action");

		if (action != null && user.getId() != 0) {
			if (action.equals("add_one")) {
				String add = request.getParameter("to_add_location");
				if (add != null) {
					um.grantPermission(user.getId(), Long.parseLong(add));
				}
			} else if (action.equals("add_all")) {
				for (Location l : um.getMissingPermission(user.getId())) {
					um.grantPermission(user.getId(), l.getId());
				}
			} else if (action.equals("rem_one")) {
				String rem = request.getParameter("to_remove_location");
				if (rem != null) {
					um.revokePermission(user.getId(), Long.parseLong(rem));
				}
			} else if (action.equals("rem_all")) {
				for (Location l : um.getPermissions(user.getId())) {
					um.revokePermission(user.getId(), l.getId());
				}
			}
		}
		request.getSession().setAttribute("mensaje", "");
		response.sendRedirect("usermod.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			if (action.equals("/new_user")) {
				newUser(request, response);
			} else if (action.equals("/get_user")) {
				getUser(request, response);
			} else if (action.equals("/modify_user")) {
				modifyUser(request, response);
			} else if (action.equals("/permit_user")) {
				permitUser(request, response);
			} else if (action.equals("/delete_user")) {
				deleteUser(request, response);
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
