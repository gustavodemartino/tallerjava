package bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Constants;
import model.UserManager;
import data.User;

@javax.faces.bean.ManagedBean
public class LoginBean {
	private String username;
	private String password;

	public LoginBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("user");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		try {
			User user = UserManager.getInstance().getUser(this.username, this.password);
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", user);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Constants.ERROR_MSG_INVALID_LOGIN));
			return "";
		}
		return "restricted/menu.jsf";
	}
}
