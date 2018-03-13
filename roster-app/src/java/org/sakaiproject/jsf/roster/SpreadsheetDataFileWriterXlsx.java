package org.sakaiproject.jsf.roster;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.jsf.spreadsheet.SpreadsheetDataFileWriter;

import lombok.extern.slf4j.Slf4j;

/**
	 *
	 */
@Slf4j
public class SpreadsheetDataFileWriterXlsx implements SpreadsheetDataFileWriter {

		public void writeDataToResponse(List<List<Object>> spreadsheetData, String fileName, HttpServletResponse response) {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			setEscapedAttachmentHeader(response, fileName + ".xlsx");

			OutputStream out = null;
			try {
				out = response.getOutputStream();
				getAsWorkbook(spreadsheetData).write(out);
				out.flush();
			} catch (IOException e) {
				log.error(e.getMessage());
			} finally {
				try {
					if (out != null) out.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		
		private SXSSFWorkbook getAsWorkbook(List<List<Object>> spreadsheetData) {
			
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet sheet = wb.createSheet();
			CellStyle headerCs = wb.createCellStyle();
			Iterator<List<Object>> dataIter = spreadsheetData.iterator();
			
			// Set the header style
			//headerCs.setBorderBottom(CellStyle.BORDER_THIN);
			headerCs.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);

			// Set the font
			CellStyle cellStyle = null;
			String fontName = ServerConfigurationService.getString("spreadsheet.font");
			if (fontName != null) {
				Font font = wb.createFont();
				font.setFontName(fontName);
				headerCs.setFont(font);
				cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);
			}

			// By convention, the first list in the list contains column headers.
			Row headerRow = sheet.createRow((short)0);
			List<Object> headerList = dataIter.next();
			for (short i = 0; i < headerList.size(); i++) {
				Cell headerCell = createCell(headerRow, i);
				headerCell.setCellValue((String)headerList.get(i));
				headerCell.setCellStyle(headerCs);
				//log.info("i: " + i);
				//sheet.autoSizeColumn(i);
			}
			
			short rowPos = 1;
			while (dataIter.hasNext()) {
				List<Object> rowData = dataIter.next();
				Row row = sheet.createRow(rowPos++);
				for (short i = 0; i < rowData.size(); i++) {
					Cell cell = createCell(row, i);
					Object data = rowData.get(i);
					if (data != null) {
						if (data instanceof Double) {
							cell.setCellValue(((Double)data).doubleValue());
						} else {
							cell.setCellValue(data.toString());
						}
						if (cellStyle != null) {
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}
			
			return wb;
		}

		private Cell createCell(Row row, short column) {
			Cell cell = row.createCell(Integer.valueOf(column).intValue());
			return cell;
		}

		/**
		 * Convenience method for setting the content-disposition:attachment header with escaping a file name.
		 * @param response
		 * @param fileName unescaped file name of the attachment
		 */
		protected static void setEscapedAttachmentHeader(final HttpServletResponse response, final String fileName) {
			String escapedFilename;
			try {
				escapedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			} catch (UnsupportedEncodingException e) {
				escapedFilename = fileName;
			}

			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces.getExternalContext().getRequest();
			String userAgent = request.getHeader("User-Agent");
			if (userAgent != null && userAgent.contains("MSIE")) {
				response.setHeader("Content-Disposition", "attachment" +
						((!StringUtils.isEmpty(escapedFilename)) ? ("; filename=\"" + escapedFilename + "\"") : ""));
			} else {
				response.setHeader("Content-Disposition", "attachment" +
						((!StringUtils.isEmpty(escapedFilename)) ? ("; filename*=utf-8''" + escapedFilename) : ""));
			}
		}
}
