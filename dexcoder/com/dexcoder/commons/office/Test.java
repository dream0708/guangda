package com.dexcoder.commons.office;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("sheet1");
		
		//添加表头
		List<String> rowTitles = new ArrayList<String>();
		rowTitles.add("姓名");
		rowTitles.add("性别");
		excelSheet.setRowTitles(rowTitles);
		
		//添加数据
		List<ExcelRow> rows = new ArrayList<ExcelRow>();
		ExcelRow r1 = new ExcelRow();
		List<ExcelCell> ec = new ArrayList<ExcelCell>();
		ec.add(new ExcelCell("张三"));
		ec.add(new ExcelCell("男"));
		r1.setCells(ec);
		rows.add(r1);
		excelSheet.setRows(rows);
		
		List<ExcelSheet> excelSheetList = new ArrayList<ExcelSheet>();
		excelSheetList.add(excelSheet);
		
		ExcelWriteTools.write(excelSheetList, new File("d:\\excelsheet1.xls"));
		//ExcelWriteTools.addSheet(excelSheet, new File("d:\\excelsheet.xls"));
	}
}
