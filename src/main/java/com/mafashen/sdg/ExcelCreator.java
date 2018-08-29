package com.mafashen.sdg;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelCreator {
	private String dataFormat = "yyyy/MM/dd hh:mm:ss";
	private String[] title;

	public ExcelCreator(String[] title){
		this.title = title;
	}

	public SXSSFWorkbook makeObjectToExcel(List<Object[]> dataList, String sheetname) {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetname);
		createExcelHeader(sheet);
		createExcel(wb, sheet, dataList);
		return wb;
	}

	private void createExcelHeader(SXSSFSheet sheet) {
		for (int i = 0; i < this.title.length; i = (i + 1)) {
			setStringValue(sheet, 0, i, this.title[i]);
		}
	}

	private void createExcel(SXSSFWorkbook wb, SXSSFSheet sheet, List<Object[]> dataList) {
		for (int i = 1; i <= dataList.size(); i = (i + 1)) {
			Object[] object = (Object[]) dataList.get(i - 1);
			for (int j = 0; j < object.length; j = (j + 1)) {
				doSetCell(wb, sheet, i, j, object[j]);
			}
		}
	}

	private void doSetCell(SXSSFWorkbook wb, SXSSFSheet sheet, int rowNum, int colNum, Object value) {
		if (value != null) {
			if ((value instanceof Number)) {
				setDoubleValue(sheet, rowNum, colNum, Double.valueOf(value.toString()));
			} else if ((value instanceof String)) {
				setStringValue(sheet, rowNum, colNum, value.toString());
			} else if ((value instanceof Date)) {
				setStringValue(sheet, rowNum, colNum, DateFormatUtils.format((Date) value, dataFormat));
			}
		}
	}

	private void setDoubleValue(SXSSFSheet sheet, int rowNum, int colNum, Double value) {
		SXSSFCell cell = getMyCell(sheet, rowNum, colNum);
		cell.setCellType(0);
		cell.setCellValue(value.doubleValue());
	}

	private void setStringValue(SXSSFSheet sheet, int rowNum, int colNum, String value) {
		SXSSFCell cell = getMyCell(sheet, rowNum, colNum);
		cell.setCellType(1);
		cell.setCellValue(value);
	}

	private SXSSFCell getMyCell(SXSSFSheet sheet, int rowNum, int colNum) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(colNum);
		if (cell == null) {
			cell = row.createCell(colNum);
		}
		return (SXSSFCell) cell;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public static void main(String[] args){
		String[] title = {"skuId" , "京东商品id"};
		ExcelCreator excelCreator = new ExcelCreator(title);
		List<Object[]> data = new ArrayList<Object[]>();
		List<Object> row = new ArrayList<Object>();
		row.add("6921763206055");
		row.add("2011005876;2011005877;2011005874");
		data.add(row.toArray());
		SXSSFWorkbook wb = excelCreator.makeObjectToExcel(data, "name");
		File file = new File("excel.xls");
		OutputStream os = null;
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
