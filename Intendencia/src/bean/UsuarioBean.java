package bean;

import java.sql.SQLException;
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
	private Boolean accionModificar = false;
	private String accionEtiqueta = "Nuevo usuario";
	private String botonEtiqueta = "Crear";
	
	public UsuarioBean() {
		try {
			this.lista = UserManager.getInstance().getUsers();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
		}		
	}
	
	public String getAccionEtiqueta() {
		return accionEtiqueta;
	}

	public void setAccionEtiqueta(String accionEtiqueta) {
		this.accionEtiqueta = accionEtiqueta;
	}

	public String getBotonEtiqueta() {
		return botonEtiqueta;
	}

	public void setBotonEtiqueta(String botonEtiqueta) {
		this.botonEtiqueta = botonEtiqueta;
	}
	
	public Boolean getAccionModificar() {
		return accionModificar;
	}

	public void setAccionModificar(Boolean accionModificar) {
		this.accionModificar = accionModificar;
	}
	
	public void cargarUsuario(User usuarioLoad) {
		this.accionModificar = true;
		this.accionEtiqueta = "Usuario: " + usuarioLoad.getName();
		this.botonEtiqueta = "Modificar";
		this.usuario = usuarioLoad;
		this.usuarioId = usuarioLoad.getId();
		this.usuarioNombre = usuarioLoad.getName();
		this.usuarioClavePlana = "";
	}
	
	public void nuevoUsuario() {
		if (this.accionModificar)
			modificarUsuario();
		else {
			try {
				this.usuario = new User(usuarioId,usuarioNombre,usuarioClavePlana);
				UserManager.getInstance().addUser(this.usuario);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario creado con éxito", ""));
				limpiarVariables();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
			}
		}
	}
	
	public void modificarUsuario() {
		try {
			if (!usuarioId.isEmpty()) usuario.setId(usuarioId);
			if (!usuarioNombre.isEmpty()) usuario.setName(usuarioNombre);
			if (!usuarioClavePlana.isEmpty()) usuario.setPassword(usuarioClavePlana);
			
			UserManager.getInstance().updateUser(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario modificado con éxito",""));
			limpiarVariables();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
		}
	}
	
	public void eliminarUsuario(User dltUsuario) {
		try {
			UserManager.getInstance().deleteUser(dltUsuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario eliminado con éxito",""));
			limpiarVariables();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
		}
	}
	
	public void limpiarVariables() {
		this.accionEtiqueta = "Nuevo usuario";
		this.botonEtiqueta = "Crear";
		this.accionModificar = false;
		this.usuario = null;
		this.usuarioId = "";
		this.usuarioNombre = "";
		this.usuarioClavePlana = "";
		try {
			this.lista = UserManager.getInstance().getUsers();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
		}
	}
}
