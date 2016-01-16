package com.rippletec.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.test.dao.IBaseDaoTest;

/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class MedicineTypeManagerTest implements IBaseDaoTest {
    
    @Resource(name=MedicineTypeManager.NAME)
    private MedicineTypeManager medicineTypeManager;

    @Override
    public void testDelete() throws Exception {
    }

    @Override
    @Test
    public void testFind() throws Exception {
	MedicineType medicineType = medicineTypeManager.find(1);
	List<MedicineType> medicineTypes = medicineTypeManager.getAllChild(medicineType);
	for (MedicineType medicineType2 : medicineTypes) {
	    System.out.println(medicineType2.toString());
	}
    }

    @Override
    public void testFindByPage() throws Exception {
    }

    @Test
    public void testFindByParam() throws Exception {
	List<MedicineType> medicineTypes = medicineTypeManager.getTypeByParentId(13);
	for (MedicineType medicineType : medicineTypes) {
	    System.out.println(medicineType.toString());
	}
    }

    @Override
    public void testSave() throws Exception {
    }
    
    @Override
    public void testUpdate() throws Exception {
    }
    
    @Test
    public void testSearch() throws Exception {
	List<BackGroundMedicineVO> backGroundMedicineVOs = medicineTypeManager.searchBackGroundVO("儿科");
	JsonUtil jsonUtil = new JsonUtil();
	System.out.println(jsonUtil.setModelList(backGroundMedicineVOs).toJsonString());
    }

    @Test
    public void testFlush() throws Exception {
	System.out.println(medicineTypeManager.flushJsonString());
    }

}
