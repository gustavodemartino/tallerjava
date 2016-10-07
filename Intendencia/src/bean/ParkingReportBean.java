package bean;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import data.SaleDetail;
import model.SalesManager;

@javax.faces.bean.ManagedBean
@SessionScoped
public class ParkingReportBean {
	private Date reportFrom = new Date();
	private Date reportTo = new Date();
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private List<SaleDetail> lista = new ArrayList<SaleDetail>();
	
	public ParkingReportBean() {
	}

	public List<SaleDetail> getSales() {
		return this.lista;
	}

	public String getReportFrom() {
		return formater.format(reportFrom);
	}

	public void setReportFrom(String reportFrom) {
		try {
			this.reportFrom = formater.parse(reportFrom);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public String getReportTo() {
		return formater.format(reportTo);
	}

	public void setReportTo(String reportTo) {
		try {
			this.reportTo = formater.parse(reportTo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public String update(){
		try {
			this.lista = SalesManager.getInstance().getSales(reportFrom, reportTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
