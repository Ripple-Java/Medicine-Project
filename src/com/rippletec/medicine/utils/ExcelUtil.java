package com.rippletec.medicine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.SubjectManager;
import com.rippletec.medicine.service.WestMedicineManager;


@Repository(ExcelUtil.NAME)
public class ExcelUtil {
    
    public static final String NAME = "ExcelUtil";

    private  String excelPath = "";
    private  String sheetName = "";
    
    @Resource(name = MedicineTypeManager.NAME)
    private  MedicineTypeManager medicineTypeManager; 
    
    @Resource(name = WestMedicineManager.NAME)
    private WestMedicineManager westMedicineManager;
    
    @Resource(name = ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;
    
    @Resource(name = BackGroundMedicineTypeManager.NAME)
    private BackGroundMedicineTypeManager backGroundMedicineTypeManager;
    
    @Resource(name = SubjectManager.NAME)
    private SubjectManager subjectManager;
    
    

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
    
    
    public  boolean setWestTypeToDatabase() throws DaoException {
	XSSFWorkbook xssfWorkbook;
	try {
	    xssfWorkbook = getExcelDom(excelPath);
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	int bigTypeId = medicineTypeManager.uniqueSave(new MedicineType("西药", MedicineType.DEFAULT_PARENT_ID, MedicineType.WEST,false));
	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);	
	Iterator<Row> rowIterator = xssfSheet.iterator();
	rowIterator.next();
	while (rowIterator.hasNext()) {
	    Row row = rowIterator.next();
	    int parent_id = bigTypeId;
	    for (int i = 1; i < 3; i++) {
		String typeString = getCellString(row, i);
		MedicineType medicineType = new MedicineType(typeString, parent_id, MedicineType.WEST);
		MedicineType mType = medicineTypeManager.isExist(medicineType);
		if(mType != null){
		    parent_id = mType.getId();
		    continue;
		}
		if(i == 2)
		    medicineType.setFlag(true);
		parent_id = medicineTypeManager.uniqueSave(medicineType);
	    }
	    Medicine medicine = new Medicine(Medicine.WEST, null);
	    MedicineType childMedicineType = null;
	    try {
		childMedicineType = medicineTypeManager.find(parent_id);
	    } catch (DaoException e) {
		e.printStackTrace();
	    }
	    WestMedicine westMedicine = 
			new WestMedicine(medicine, childMedicineType, 
				getCellString(row, 3), getCellString(row, 4), getCellString(row, 5,true),
				getCellString(row, 6,true),getCellString(row, 8,true)+"\n\n"+getCellString(row, 9,true), getCellString(row, 10,true), getCellString(row, 11,true),
				getCellString(row, 12,true), getCellString(row, 13,true), getCellString(row, 14,true),getCellString(row, 15,true), WestMedicine.ON_PUBLISTH, StringUtil.toPinYin(getCellString(row, 3)));
	    
	    westMedicineManager.save(westMedicine);
	    
	}
	Logger.getLogger(ExcelUtil.class)
	      .info("import WestMedicine："+xssfSheet.getLastRowNum());
   	return true;
    }
    
    public  boolean setSubjectToDatabase() throws DaoException {
   	XSSFWorkbook xssfWorkbook;
   	try {
   	    xssfWorkbook = getExcelDom(excelPath);
   	} catch (IOException e) {
   	    e.printStackTrace();
   	    return false;
   	}
   	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
   	Subject currentParent = null;
   	Iterator<Row> rowIterator = xssfSheet.iterator();
   	while (rowIterator.hasNext()) {
   	    Row row = rowIterator.next();
   	    String parentName = getCellString(row, 0);
   	    if(StringUtil.hasText(parentName)){
   		currentParent = new Subject(Subject.DEFAULT_PARENT, parentName, null);
   		subjectManager.save(currentParent);
   		currentParent = subjectManager.findByParam(Subject.NAME, parentName).get(0);
   	    }
   	    String subjectName = getCellString(row, 1);
   	    if(StringUtil.hasText(subjectName)){
           	 Subject subject = new Subject(currentParent.getId(), subjectName, currentParent.getName());
           	 subjectManager.save(subject);
   	    }
   	}
   	Logger.getLogger(ExcelUtil.class).info("import Suject："+xssfSheet.getLastRowNum());
      	return true;
       }
    
    public  boolean setChineseTypeToDatabase() throws DaoException {
	XSSFWorkbook xssfWorkbook;
	try {
	    xssfWorkbook = getExcelDom(excelPath);
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	int bigTypeId = medicineTypeManager.uniqueSave(new MedicineType("中药", MedicineType.DEFAULT_PARENT_ID, MedicineType.CHINESE,false));
	
	XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
	Iterator<Row> rowIterator = xssfSheet.iterator();
	while (rowIterator.hasNext()) {
	    ChineseMedicine chineseMedicine = new ChineseMedicine();
	    Row row = rowIterator.next();
	    int parent_id = bigTypeId;
	    for (int i = 0; i < 3; i++) {
		String typeString = getCellString(row, i);
		if (i>0 && !StringUtil.hasText(typeString)) {
		    typeString = "其它";
		    parent_id = medicineTypeManager.uniqueSave(new MedicineType(typeString, parent_id, MedicineType.CHINESE,true));
		    break;
		}
		MedicineType medicineType = new MedicineType(typeString, parent_id, MedicineType.CHINESE);
		MedicineType mType = medicineTypeManager.isExist(medicineType);
		if(mType != null){
		    parent_id = mType.getId();
		    continue;
		}
		if(i == 2)
		    medicineType.setFlag(true);
		parent_id = medicineTypeManager.uniqueSave(medicineType);
	    }
	    
	    MedicineType type = null;
	    try {
		type = medicineTypeManager.find(parent_id);
	    } catch (DaoException e) {
		e.printStackTrace();
	    }
	    chineseMedicine.setName(getCellString(row, 4));
	    chineseMedicine.setContent(getCellString(row, 5));
	    chineseMedicine.setEfficacy(getCellString(row, 6,true));
	    chineseMedicine.setAnnouce(getCellString(row, 7,true));
	    chineseMedicine.setManual(getCellString(row, 8,true));
	    chineseMedicine.setPreparations(getCellString(row, 9,true));
	    chineseMedicine.setStore(getCellString(row, 10,true));
	    chineseMedicine.setCategory(getCellString(row, 11,true));
	    chineseMedicine.setMedicineType(type);
	    Medicine medicine = new Medicine(Medicine.CHINESE, null);
	    chineseMedicine.setMedicine(medicine);
	    chineseMedicine.setSortKey(StringUtil.toPinYin(getCellString(row, 4)));
	    chineseMedicine.setStatus(ChineseMedicine.ON_PUBLISTH);
	    chineseMedicineManager.save(chineseMedicine);
	}
	Logger.getLogger(ExcelUtil.class).info("import ChineseMedicine："+xssfSheet.getLastRowNum());
   	return true;
    }
    
    
    
    public  String getCellString(Row row , int index) {
	Cell cell = row.getCell(index);
	if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) 
	    return "";
	return cell.getStringCellValue();
    }
    
    public  String getCellString(Row row , int index , boolean filter) {
	if(filter == false)
	    return getCellString(row, index);
	String str =  getCellString(row, index);
	return StringUtil.formatData(str);
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
