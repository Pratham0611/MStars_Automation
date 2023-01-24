package KeywordDriverUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class CustomFramework {
	private static final Logger logger = Logger.getLogger(CustomFramework.class.getName());
	
	public static Map<String, List<String>> fetchWithCondition(String sheetPath, String sheetName, List<String> whereClause) {
		Map<String, List<String>> excelMap = coreListToMap(sheetPath, sheetName);
		for (String clause : whereClause) {
			Map<String, List<String>> finalMap = new HashMap<String, List<String>>();
			List<Integer> addIndex = new ArrayList<Integer>();
			for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
				int k = 0;
				if (entry.getKey().equalsIgnoreCase(clause.split("::")[0])) {
					List<String> vals = new ArrayList<String>();
					for (String val : new ArrayList<String>(entry.getValue())) {
						if (val.equalsIgnoreCase(clause.split("::")[1])) {
							vals.add(val);
							addIndex.add(k);
						}
						k++;
					}
					finalMap.put(entry.getKey(), vals);
				}
			}
			for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
				List<String> vals = new ArrayList<String>();
				if (!entry.getKey().equalsIgnoreCase(clause.split("::")[0])) {
					for (int add : addIndex)
						vals.add(entry.getValue().get(add));
					finalMap.put(entry.getKey(), vals);
				}
			}
			excelMap = finalMap;
		}
		return excelMap;
	}
	
	public static Map<String, List<String>> coreListToMap(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = coreFetch(sheetPath, sheetName);
		logger.finest("tempStorage-->"+tempStorage);
		Map<String, List<String>> excelMap = new HashMap<String, List<String>>();
		
		List<List<String>> tempList = new ArrayList<List<String>>();

		for(int j=0; j<tempStorage.get(0).size() ; j++){
			List<String> eachCol = new ArrayList<String>();
			for(int i=1; i<tempStorage.size(); i++){
				try{
					eachCol.add(tempStorage.get(i).get(j));
				}catch(IndexOutOfBoundsException e){
					eachCol.add("");
				}
				
			}
			tempList.add(eachCol);
		}
		
		for(int i=0; i<tempList.size(); i++){
			excelMap.put(tempStorage.get(0).get(i), tempList.get(i));
		}
		return excelMap;
	}
	
	public static List<List<String>> coreFetch(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = new ArrayList<List<String>>();
		FileInputStream file = null;
		XSSFWorkbook workbook = null;		
		try {
			if(!(new File(sheetPath).exists()))
			{
				JOptionPane.showConfirmDialog(null, "File NOT found: "+sheetPath , "Warning", 2);
//				Driver.driver.quit();
			}
			file = new FileInputStream(new File(sheetPath));
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			Iterator<Row> rowIterator = sheet.iterator();
			int numOfHeaders = 0;
			String testScriptName = getTestScriptName(sheetPath);
			boolean addTestScritName = false;
			boolean addScriptAlias = false;
			if(sheetName.equals("TestSteps")||sheetName.equals("TestData")){
				addTestScritName = true;				
			}
			if(sheetName.equals("TestCases")){
				addScriptAlias = true;
			}
			//first row is header
			if(rowIterator.hasNext()) {
				List<String> rowWise = new ArrayList<String>();
				if(addTestScritName){
					rowWise.add("TestScript");
				}
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				int i = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
					if(i != cell.getColumnIndex()){
						rowWise.add("");
					}
					
					i = cell.getColumnIndex()+1;
					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						rowWise.add(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						rowWise.add(Integer.toString((int) (cell.getNumericCellValue())));
						break;
					}
				}
				numOfHeaders = rowWise.size();
				tempStorage.add(rowWise);				
			}
			//second row onwards, it is data
			//read data for the corresponding header index
			int scriptAliasCounter = 1;
			while (rowIterator.hasNext()) {
				List<String> rowWise = new ArrayList<String>();
				if(addTestScritName){
					rowWise.add(testScriptName);
				}
				Row row = rowIterator.next();
				for(int cellNumber=0; cellNumber < numOfHeaders; cellNumber++){
					Cell  cell = row.getCell(cellNumber);
					String dataValue = "";
					if(cell != null){						
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								String cellValue = cell.getStringCellValue();
								//TestScript column in the TestCases tab
								if(addScriptAlias && cellNumber == 2){
									cellValue = cellValue + "-TC" + scriptAliasCounter++;
								}
								rowWise.add(cellValue);
								break;
							case Cell.CELL_TYPE_NUMERIC:
								rowWise.add(Long.toString((long) (cell.getNumericCellValue())));
								break;
							default: rowWise.add("");
							  break;
						}												
					}else{
						rowWise.add(dataValue);
					}
				}
				tempStorage.add(rowWise);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING,"Error while fetching details from TestCase file-->"+sheetName,e);
		} finally {
			try {
				if (file != null)
					file.close();
				//if (workbook != null)
				//	workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (sheetName.equalsIgnoreCase("Scenarios"))
		{
//			if((!(Driver.ScenarioList == null)))
//			{
//			for(int i = 1; i<tempStorage.size();i++)
//			{
//				tempStorage.get(i).set(3, "No");
//			}
//			for (int i = 0; i< Driver.ScenarioList.size(); i++)
//			{
//				String tempScenario = Driver.ScenarioList.get(i);
//				for(int j = 1; j<tempStorage.size();j++)
//				{
//					if(tempScenario.equalsIgnoreCase(tempStorage.get(j).get(1)))
//					{
//						tempStorage.get(j).set(3, "Yes");
//					}
//				}
//			}
//			return tempStorage;
//		}
//		else
//		{
//			for(int i = 1; i<tempStorage.size();i++)
//			{
//				tempStorage.get(i).set(3, "No");
//			}	
//		return tempStorage;
//		}
	}
		return tempStorage;
	}
	public static Map<String, HashMap> getScenarioInformation(String sheetPath) {

		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		HashMap<String, HashMap> indexMap = new HashMap();
		HashMap<String, String> infoMap = new HashMap();
		HashMap<String, String> toaMap = new HashMap();
		try {
			file = new FileInputStream(new File(sheetPath));
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				String ScnName = (row.getCell(1).getStringCellValue());
				String info = StringEscapeUtils.escapeHtml4(row.getCell(2).getStringCellValue());
				String toa = StringEscapeUtils.escapeHtml4(row.getCell(3).getStringCellValue());
				info = info.replace("\n", "<br>");
				infoMap.put(ScnName, info);
				toaMap.put(ScnName, toa);
			}

		} catch (Exception e) {
		}
		indexMap.put("info", infoMap);
		indexMap.put("toa", toaMap);
		return indexMap;

	}
	
	public static String getTestScriptName(String sheetPath){
		logger.fine("sheetPath-->"+sheetPath);
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String[] pathnames = sheetPath.split(pattern);
		pattern = Pattern.quote(".");
		String name[] = pathnames[pathnames.length - 1].split(pattern);
		if(name[0].equalsIgnoreCase("TestDataSheet"))
		{
			name = null;
			pathnames = null;
			pattern = null;
//			sheetPath=dbPath;
			sheetPath=sheetPath;
			pattern = Pattern.quote(String.valueOf(File.separatorChar));
			pathnames = sheetPath.split(pattern);
			pattern = Pattern.quote(".");
			name = pathnames[pathnames.length - 1].split(pattern);
		}
		return name[0];
	}

}
