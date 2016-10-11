package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import data.User;
import model.UserManager;

@javax.faces.bean.ManagedBean
@SessionScoped
public class UsuarioBean {
	private User usuario;
	private String usuarioId;
	private String usuarioNombre;
	private String usuarioClavePlana;
	private List<User> lista = new ArrayList<User>();
	
	public UsuarioBean() {
		try {
			this.lista = UserManager.getInstance().getUsers();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public String getUsuarioClavePlana() {
		return usuarioClavePlana;
	}

	public void setUsuarioClavePlana(String usuarioClavePlana) {
		this.usuarioClavePlana = usuarioClavePlana;
	}
	
	public List<User> getLista() {
		return this.lista;
	}

	public void setLista(List<User> lista) {
		try {
			this.lista = UserManager.getInstance().getUsers();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}		
	}
	
	public void nuevoUsuario() {
		try {
			this.usuario = new User(usuarioId,usuarioNombre,usuarioClavePlana);
			UserManager.getInstance().addUser(this.usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario creado con éxito"));
			usuarioId = "";
			usuarioNombre = "";
			usuarioClavePlana = "";
			try {
				this.lista = UserManager.getInstance().getUsers();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
	public void cargarUsuario(User usuario) {
		
	}
	
	public void modificarUsuario() {
		try {
			UserManager.getInstance().addUser(this.usuario);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
	public void eliminarUsuario(User dltUsuario) {
		try {
			UserManager.getInstance().deleteUser(dltUsuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario eliminado con éxito"));
			try {
				this.lista = UserManager.getInstance().getUsers();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
}
