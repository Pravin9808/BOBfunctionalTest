package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static  HSSFWorkbook wb;
	public static HSSFSheet ws;
	public static HSSFRow row;
	public static HSSFCell cell;
	public static CellStyle style;
	static String path;
	public XLUtility(String path)
	{
		XLUtility.path=path;
	}
	
	public static int getRowCount(String xlfileName,String xlsheetName) throws IOException 
	{
		fis=new FileInputStream(xlfileName);
		wb=new HSSFWorkbook(fis);
		ws=wb.getSheet(xlsheetName);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fis.close();
		return rowcount;		
	}
	
	
	public static int getCellCount(String xlfileName,String xlsheetName,int rownum) throws IOException
	{
		fis=new FileInputStream(xlfileName);
		wb=new HSSFWorkbook(fis);
		ws=wb.getSheet(xlsheetName);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fis.close();
		return cellcount;
	}
	
	
	public static String getCellData(String xlfileName,String xlsheetName,int rownum,int colnum) throws IOException
	{
		fis=new FileInputStream(xlfileName);
		wb=new HSSFWorkbook(fis);
		ws=wb.getSheet(xlsheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		String data;
		try 
		{
			DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
		}
		catch (Exception e) 
		{
			data="";
		}
		wb.close();
		fis.close();
		return data;
	}
	public static void fillGreenColor(String xlsheetName, int rownum, int colnum) throws IOException
	{
		fis=new FileInputStream(path);
		wb=new HSSFWorkbook(fis);
		ws=wb.getSheet(xlsheetName);
		
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=wb.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		wb.write(fos);
		wb.close();
		fis.close();
		fos.close();

	}
	
	public static void fillRedColor(String xlsheetName,int rownum, int colnum) throws IOException
	{
		fis=new FileInputStream(path);
		wb=new HSSFWorkbook(fis);
		ws=wb.getSheet(xlsheetName);
		
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=wb.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		wb.write(fos);
		wb.close();
		fis.close();
		fos.close();
	}
	
	public static void setCellData(String xlfileName,String xlsheetName,int rownum,int colnum,String data) throws IOException
	{
		//if file not exists then create new file
		File xlfile = new File(path);
		
		
		
		if(!xlfile.exists())   	
		{
			wb = new HSSFWorkbook();
				fos = new FileOutputStream(path);
			wb.write(fos);
		}
		
		
		fis=new FileInputStream(path);
		wb=new HSSFWorkbook(fis);
		
		if(wb.getSheetIndex(xlsheetName)==-1)   //if sheet not exists then create new sheet
			wb.createSheet(xlsheetName);
		ws= wb.getSheet(xlsheetName);
		
		if(ws.getRow(rownum)==null)  // if row not exists then create new Row
			ws.createRow(rownum);
		row=ws.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fos=new FileOutputStream(path);
		wb.write(fos);
		fis.close();
		fos.close();
		 
	}

}
