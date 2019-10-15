package com.xiaoxinkeji.interf.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private String filePath = null;

	/**
	 * 创建构造方法
	 */
	public ExcelUtil(String filePath){
		this.filePath=filePath;
	}
	
	/**
	 * 创建workbook对象
	 */
	public Workbook getwb(){
		Workbook wb = null;
		try {
			InputStream input = new FileInputStream(filePath);
			if (filePath.endsWith(".xlsx")) {
				 wb = new XSSFWorkbook(input);
			}else {
				 wb = new HSSFWorkbook(input);
			}
		} catch (Exception e) {
		}
		
		return wb;
		
	}
	
	/**
	 * 创建sheet对象
	 */
	public Sheet getsheet(int sheetNum){
		Sheet sheet = null;
		try {
			Workbook wb = getwb();
			sheet = wb.getSheetAt(sheetNum);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sheet;
		
	}
	
	/**
	 * 创建行和列
	 */
	public Object[][] fromArrayValues(int sheetNum){
		Object[][] testCase = null;
		try {
			int LastRowNum = getsheet(sheetNum).getLastRowNum();
			testCase = new Object[LastRowNum][10];
			for (int rowIndex = 1; rowIndex <= LastRowNum; rowIndex++) {
				Row row = getsheet(sheetNum).getRow(rowIndex);
				if (row==null) {
					continue;
				}
				for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
					Cell cell = row.getCell(cellIndex);
						if (cell==null) {
							testCase[rowIndex-1][cellIndex]="";
						}else {
							testCase[rowIndex-1][cellIndex]=getcellvalues(cell);
						}
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return testCase;
		
	}
	
	/**
	 * 判断列的值
	 */
	public Object getcellvalues(Cell cell){
		Object value = null;
		try {
			if (cell.getCellType()==cell.CELL_TYPE_BLANK) {
				value = "";
			}else if (cell.getCellType()==cell.CELL_TYPE_FORMULA) {
				value=cell.getCellFormula();
			}else if (cell.getCellType()==cell.CELL_TYPE_NUMERIC) {
				value = cell.getNumericCellValue();
			}else if (cell.getCellType()==cell.CELL_TYPE_STRING) {
				value=cell.getStringCellValue();
			}else if (cell.getCellType()==cell.CELL_TYPE_BOOLEAN) {
				value = cell.getBooleanCellValue();
			}else  {
				value = cell.getDateCellValue();
			}
			
		} catch (Exception e) {
		}
		return value;
		
	}
}
