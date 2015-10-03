package com.rippletec.test.utils;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.utils.ExcelUtil;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ExcelUtilTest {
    
    @Resource()
    private ExcelUtil excelUtil;
    
    @Test
    public void testWestToDB() throws Exception {
	String excelPath = "E:\\Desktop\\MedicineProject\\西药汇总数据（正确）.xlsx";
	String sheetName = "Sheet1";
	excelUtil.setExcelPath(excelPath)
		.setSheetName(sheetName);
	System.out.println(excelUtil.setWestTypeToDatabase());
    }
    
    @Test
    public void testChineseToDB() throws Exception {
	String excelPath = "E:\\Desktop\\MedicineProject\\中成药汇总数据(新).xlsx";
	String sheetName = "中成药";
	excelUtil.setExcelPath(excelPath)
		.setSheetName(sheetName);
	System.out.println(excelUtil.setChineseTypeToDatabase());
    }

}
