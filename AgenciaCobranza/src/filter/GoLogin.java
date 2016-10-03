package filter;

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

import data.Login;
import model.Constants;

@WebFilter(value = "/*")
public class GoLogin implements Filter {

	public GoLogin() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String url = ((HttpServletRequest) request).getServletPath();
		// TODO Debug
		// System.out.println(url);
		if (!url.contains("login")) {
			Login loginInfo = (Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO);
			if (loginInfo == null) {
				// TODO Debug
				// System.out.println("Redirect to login.jsp");
				((HttpServletResponse) response).sendRedirect("login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}
}
