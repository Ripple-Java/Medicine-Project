package com.rippletec.medicine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.WestMedicineManager;


@Repository(ExcelUtil.NAME)
public class ExcelUtil {
    
    public static final String NAME = "Excelutil";

    private  String excelPath = "";
    private  String sheetName = "";
    
    @Resource(name = MedicineTypeManager.NAME)
    private  MedicineTypeManager medicineTypeManager; 
    
    @Resource(name = WestMedicineManager.NAME)
    private WestMedicineManager westMedicineManager;
    
    @Resource(name = ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;
    

    public ExcelUtil() {
    }
    
    public ExcelUtil(String excelPath, String sheetName) {
	super();
	this.excelPath = excelPath;
	this.sheetName = sheetName;
    }


    public  XSSFWorkbook getExcelDom(String path) throws FileNotFoundException, IOException {
	return (new XSSFWorkbook(new FileInputStream(new File(path))));
    }
    
    
    public  List<ChineseMedicine> getChineseModels() {
	return null;
    }
    
    public  List<WestMedicine> getWestModels() {
	List<WestMedicine> westMedicines = new LinkedList<WestMedicine>();
	XSSFWorkbook xssfWorkbook;
	try {
	    xssfWorkbook = getExcelDom(excelPath);
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
	Iterator<Row> rowIterator = xssfSheet.iterator();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	
	}
	return null;
    }
    
    public  boolean setWestTypeToDatabase() {
	XSSFWorkbook xssfWorkbook;
	try {
	    xssfWorkbook = getExcelDom(excelPath);
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	int bigTypeId = medicineTypeManager.save(new MedicineType("西药", MedicineType.DEFAULT_PARENT_ID, MedicineType.WEST));
	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
	
	Iterator<Row> rowIterator = xssfSheet.iterator();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    int parent_id = bigTypeId;
	    for (int i = 1; i < 4; i++) {
		String typeString = getCellString(row, i);
		if (!StringUtil.hasText(typeString)) {
		    typeString = "其他";
		    parent_id = medicineTypeManager.uniqueSave(new MedicineType(typeString, parent_id, MedicineType.WEST));
		    break;
		}
		MedicineType medicineType = new MedicineType(typeString, parent_id, MedicineType.WEST);
		MedicineType mType = medicineTypeManager.isExist(medicineType);
		if(mType != null){
		    parent_id = mType.getId();
		    continue;
		}
		parent_id = medicineTypeManager.uniqueSave(medicineType);
	    }
	    if (row.getCell(18) == null || row.getCell(17)==null || row.getCell(16) == null)
		continue;  
	    Medicine medicine = new Medicine(Medicine.WEST);
	    WestMedicine westMedicine = 
			new WestMedicine(medicine, medicineTypeManager.find(parent_id), 
				getCellString(row, 4), getCellString(row, 5), getCellString(row, 6),
				getCellString(row, 7), getCellString(row, 8), getCellString(row, 9),
				getCellString(row, 10), getCellString(row, 11), getCellString(row, 12),
				getCellString(row, 13), getCellString(row, 14), getCellString(row, 15),
				getCellString(row, 16), getCellString(row, 17), getCellString(row, 18),
				getCellString(row, 18), 1.00, WestMedicine.ON_PUBLISTH, "sortkey");
	    westMedicineManager.save(westMedicine);
	    
	}
	System.out.println(xssfSheet.getLastRowNum());
   	return true;
    }
    
    public  boolean setChineseTypeToDatabase() {
	XSSFWorkbook xssfWorkbook;
	try {
	    xssfWorkbook = getExcelDom(excelPath);
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	int bigTypeId = medicineTypeManager.save(new MedicineType("中药", MedicineType.DEFAULT_PARENT_ID, MedicineType.CHINESE));
	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
	
	Iterator<Row> rowIterator = xssfSheet.iterator();
	String Announce = "";
	ChineseMedicine chineseMedicine = new ChineseMedicine();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    int parent_id = bigTypeId;
	    for (int i = 0; i < 4; i++) {
		String typeString = getCellString(row, i);
		if (!StringUtil.hasText(typeString)) {
		    typeString = "其他";
		    parent_id = medicineTypeManager.uniqueSave(new MedicineType(typeString, parent_id, MedicineType.CHINESE));
		    break;
		}
		MedicineType medicineType = new MedicineType(typeString, parent_id, MedicineType.CHINESE);
		MedicineType mType = medicineTypeManager.isExist(medicineType);
		if(mType != null){
		    parent_id = mType.getId();
		    continue;
		}
		parent_id = medicineTypeManager.uniqueSave(medicineType);
	    }
	    if(row.getCell(4) == null){
		String value = getCellString(row, 5);
		if (value.contains("【功效主治】"))
		    chineseMedicine.setEfficacy(value);
		else if (value.contains("【用药监护】")){
		    Announce = "【用药监护】";
		}
		else if(value.contains("【用法与用量】")){
		    chineseMedicine.setAnnouce(Announce);
		    chineseMedicine.setManual(value);
		}    
		else if(value.contains("【制剂量】") || value.contains("【制剂】"))
		    chineseMedicine.setPreparations(value);
		else if(value.contains("【贮法】"))
		    chineseMedicine.setStore(value);
		else if (value.contains("【管理分类】")){
		    chineseMedicine.setCategory(value);
		    Medicine medicine  = new Medicine(Medicine.CHINESE);
		    chineseMedicine.setMedicine(medicine);
		    System.out.println(chineseMedicine);
		    chineseMedicineManager.save(chineseMedicine);
		}
		else {
		    Announce +=value;
		}   
	    }
	    else {
		chineseMedicine = new ChineseMedicine();
		chineseMedicine.setMedicineType(medicineTypeManager.find(parent_id));
		chineseMedicine.setPrice(1.0);
		chineseMedicine.setSortKey("sortKey");
		chineseMedicine.setStatus(ChineseMedicine.ON_PUBLISTH);
		chineseMedicine.setName(getCellString(row, 4));
		chineseMedicine.setContent(getCellString(row, 5));
	    }
	      
	    
	}
	System.out.println(xssfSheet.getLastRowNum());
   	return true;
    }
    
    public  String getCellString(Row row , int index) {
	Cell cell = row.getCell(index);
	if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) 
	    return "";
	return cell.getStringCellValue();
    }

    public String getExcelPath() {
        return excelPath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ExcelUtil setExcelPath(String excelPath) {
        this.excelPath = excelPath;
        return this;
    }

    public ExcelUtil setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }
    
    
    

}
