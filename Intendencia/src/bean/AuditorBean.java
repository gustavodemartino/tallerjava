package bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import data.Auditor;
import data.SaleDetail;
import model.AuditManager;
import model.SalesManager;

@javax.faces.bean.ManagedBean
@SessionScoped
public class AuditorBean {
	private Date filtroDesde = new Date();
	private Date filtroHasta = new Date();
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	private List<Auditor> lista = new ArrayList<Auditor>();
	
	public String getFiltroDesde() {
		return formatoFecha.format(filtroDesde);
	}

	public void setFiltroDesde(String filtroDesdeStr) {
		try {
			this.filtroDesde = formatoFecha.parse(filtroDesdeStr);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public String getfiltroHasta() {
		return formatoFecha.format(filtroHasta);
	}

	public void setFiltroHasta(String filtroHastaStr) {
		try {
			this.filtroHasta = formatoFecha.parse(filtroHastaStr);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public List<Auditor> getLista() {
		return lista;
	}

	public void setLista(List<Auditor> lista) {
		this.lista = lista;
	}
	
	public String listarAuditoria(){
		try {
			this.setLista(AuditManager.getInstance().reporte(this.filtroDesde, this.filtroHasta));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
