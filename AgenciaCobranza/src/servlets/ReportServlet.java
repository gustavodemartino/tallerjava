package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import model.SalesManager;
import data.ParkingDetail;


@WebServlet("/reportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		try {
			String excelFileName = "reporteEstacionamiento.xls";
			FileOutputStream fileOut = new FileOutputStream(excelFileName);   //${pageContext.request.contextPath}/
			File xlsFile = new File(excelFileName);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Reporte de prueba");
			
			/*
			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("Hello");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);

			HSSFCell cellB1 = row1.createCell((short) 1);
			cellB1.setCellValue("Goodbye");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);

			HSSFCell cellC1 = row1.createCell((short) 2);
			cellC1.setCellValue(true);

			HSSFCell cellD1 = row1.createCell((short) 3);
			cellD1.setCellValue(new Date());
			cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			cellD1.setCellStyle(cellStyle);
*/
			
			String filter = request.getParameter("filter");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dfdt = new SimpleDateFormat("dd-MM-yyyy kk:mm");
			String date_from = request.getParameter("date_from");		
			String date_to = request.getParameter("date_to");
			
			Date from = new Date(sdf.parse(date_from).getTime() + 86400000);
			Date to = new Date(sdf.parse(date_to).getTime() + 86400000);
			
			int nFirstRecord = 0;
			int nShow = 1000000;
		
			List<ParkingDetail> sales;
			// index from 0,0... cell A1 is cell(0,0)
			HSSFRow row1 = worksheet.createRow((short) 0);
			
			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("Venta");
			HSSFCell cellB1 = row1.createCell((short) 1);
			cellB1.setCellValue("Matrícula");
			HSSFCell cellC1 = row1.createCell((short) 2);
			cellC1.setCellValue("Inicio");
			HSSFCell cellD1 = row1.createCell((short) 3);
			cellD1.setCellValue("Minutos");
			HSSFCell cellE1 = row1.createCell((short) 4);
			cellE1.setCellValue("Ticket");
			HSSFCell cellF1 = row1.createCell((short) 5);
			cellF1.setCellValue("Anulación");
			HSSFCell cellG1 = row1.createCell((short) 6);
			cellG1.setCellValue("Autorización");
			HSSFCell cellH1 = row1.createCell((short) 7);
			cellH1.setCellValue("Pago");
			HSSFCell cellI1 = row1.createCell((short) 8);
			cellI1.setCellValue("Crédito");
			
			if (filter.equals("active")) {
				sales = SalesManager.getInstance().getActiveSales(from, to, nFirstRecord, nShow);
			} else if (filter.equals("canceled")) {
				sales = SalesManager.getInstance().getCanceledSales(from, to, nFirstRecord, nShow);
			} else {
				sales = SalesManager.getInstance().getSales(from, to, nFirstRecord, nShow);
			}
			
			short ind_row = 1;
			HSSFCell cell;
			
			for (ParkingDetail p : sales) {
				HSSFRow row = worksheet.createRow(ind_row);
				
				cell = row.createCell(0);
				cell.setCellValue(dfdt.format(p.getSaleDateTime()));
				
				cell = row.createCell(1);
				cell.setCellValue(p.getPlate());
				
				cell = row.createCell(2);
				cell.setCellValue(dfdt.format(p.getParkingStart()));
				
				cell = row.createCell(3);
				cell.setCellValue(p.getDuration());
				
				cell = row.createCell(4);
				cell.setCellValue(p.getSaleTicket());
				
				//dfdt.format() + "</td><td>" +  + "</td><td>"
			
				if (p.getIsCanceled()) {
					cell = row.createCell(5);
					cell.setCellValue(dfdt.format(p.getCancelationDateTime()));
					
					cell = row.createCell(6);
					cell.setCellValue(p.getCancelationNumber());
					
					cell = row.createCell(7);
					cell.setCellValue(p.getAmount());
					
					cell = row.createCell(8);
					cell.setCellValue(p.getCredit());
					
				} else {
					
					cell = row.createCell(5);
					cell.setCellValue(" ");
					
					cell = row.createCell(6);
					cell.setCellValue(" ");
					
					cell = row.createCell(7);
					cell.setCellValue(p.getAmount());
					
					cell = row.createCell(8);
					cell.setCellValue(" ");
				}
				
				ind_row++;
			}
				
			workbook.write(fileOut);
			
			fileOut.flush();
			fileOut.close();
			
			String contentType = "application/xls";      
            response.setHeader("Content-disposition", "attachment; filename=" + excelFileName);
            response.setContentType(contentType);        
            
            OutputStream output = response.getOutputStream();
            response.setContentLength((int) xlsFile.length());
           
            FileInputStream fileInputStream = new FileInputStream(xlsFile);
              OutputStream responseOutputStream = response.getOutputStream();
              int bytes;
              while ((bytes = fileInputStream.read()) != -1) {
                    responseOutputStream.write(bytes);
              }
             
            output.close();    
            fileInputStream.close();
           
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
