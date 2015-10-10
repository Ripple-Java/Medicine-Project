package com.rippletec.test.utils;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.utils.ExcelUtil;
import com.rippletec.medicine.utils.InitDBUtil;
import com.sun.corba.se.spi.orb.StringPair;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ExcelUtilTest {
    
    @Resource
    private ExcelUtil excelUtil;
    
    @Resource
    private InitDBUtil initDBUtil;
    
    @Resource(name=EnterpriseManager.NAME)
    private EnterpriseManager enterpriseManager;
    
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
	String excelPath = "E:\\Desktop\\MedicineProject\\中药完整.xlsx";
	String sheetName = "中药";
	excelUtil.setExcelPath(excelPath)
		.setSheetName(sheetName);
	System.out.println(excelUtil.setChineseTypeToDatabase());
    }
    
    @Test
    public void testAddBackVo() throws Exception {
	System.out.println(initDBUtil.setBackGroundVoToDatabase());
    }
    
    @Test
    public void testAddEnterprise() throws Exception {
	String str = "修正药业,白云山制药,双鹤药业,亚宝药业,正大天晴药业,扬子江药业,仁和集团,北京同仁堂,云南白药,健奥科技";
	String str2 = "鱼跃,汤臣倍健,三九医药,养生堂,千林,辅仁药业集团,九芝堂,合生元,太极集团四川绵阳制药有限公司,哈药集团制药六厂";
	String str3 = "北京协和药厂,扬子江药业集团有限公司,哈药集团三精制药股份有限公司,云南白药集团股份有限公司,浙江新和成股份有限公司,天士力制药集团股份有限公司,齐鲁制药有限公司,修正药业集团股份有限公司,神威药业有限公司,石药集团有限公司";
	String[] strs  = str.split(",");
	for (String string : strs) {
	    Enterprise enterprise = new Enterprise(Enterprise.DOMESTIC, string, "00000000");
	    enterprise.setEmail("123456@ripplic.com");
	    enterprise.setLogo("/images/enter/defaultLogo.png");
	    enterprise.setPhone("1234567");
	    enterpriseManager.save(enterprise);
	}
	String[] str2s = str2.split(",");
	for (String string : str2s) {
	    Enterprise enterprise = new Enterprise(Enterprise.FOREIGN, string, "00000000");
	    enterprise.setEmail("123456@ripplic.com");
	    enterprise.setLogo("/images/enter/defaultLogo.png");
	    enterprise.setPhone("1234567");
	    enterpriseManager.save(enterprise);
	}
	String[] str3s = str3.split(",");
	for (String string : str3s) {
	    Enterprise enterprise = new Enterprise(Enterprise.JOINT, string, "00000000");
	    enterprise.setEmail("123456@ripplic.com");
	    enterprise.setLogo("/images/enter/defaultLogo.png");
	    enterprise.setPhone("1234567");
	    enterpriseManager.save(enterprise);
	}
	
	
    }

}
