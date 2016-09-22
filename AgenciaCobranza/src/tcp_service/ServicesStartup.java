package tcp_service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1)
public class ServicesStartup extends HttpServlet {
	private static final long serialVersionUID = -3168004362123603351L;
	private Thread t;

	public ServicesStartup() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		t = new Thread(new TerminalServer());
		t.start();
	}

	@Override
	public void destroy() {
		super.destroy();
		t.interrupt();
	}
}
