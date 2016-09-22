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

import data.User;

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
		if (!url.contains("login")) {
			User user = (User) session.getAttribute("userSession");
			if (user == null) {
				((HttpServletResponse) response).sendRedirect("login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
