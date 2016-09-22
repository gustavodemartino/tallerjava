package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.User;
import model.LoginManager;

@WebServlet("/login_servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Unsupported method");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		try{
			User user = LoginManager.getInstance().webLogin(userId, password);
			user.setPassword(password);
			session.setAttribute("userSession", user);
			response.sendRedirect("admin.jsp");
		} catch (Exception e) {
			session.setAttribute("userError", e.getMessage());
            response.sendRedirect("login.jsp");
		}		
	}

}
