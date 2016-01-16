package com.rippletec.test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.test.dao.IBaseDaoTest;
/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ChineseMedicineManagerTest implements IBaseDaoTest{
    
    @Resource(name=ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;

    @Override
    @Test
    public void testDelete() throws Exception {
	chineseMedicineManager.delete(2);
	chineseMedicineManager.delete(3);
	chineseMedicineManager.delete(30);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	List<ChineseMedicine> chineseMedicines = chineseMedicineManager.findByParam("annouce", "【用药监护】");
	for (ChineseMedicine chineseMedicine : chineseMedicines) {
	    chineseMedicineManager.delete(chineseMedicine.getId());
	}
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
	List<ChineseMedicine> chineseMedicines = chineseMedicineManager.findByPage(new PageBean(0, 50));
	for (ChineseMedicine chineseMedicine : chineseMedicines) {
	    System.out.println(chineseMedicine.toString());
	}
    }

    @Override
    @Test
    public void testSave() throws Exception {
//	Medicine medicine = new Medicine(Medicine.CHINESE ,null );
//	MedicineType medicineType = new MedicineType("测试typename", -1, Medicine.CHINESE);
//	ChineseMedicine chineseMedicine = new ChineseMedicine(medicine, medicineType, "中药", "中药组成", "efficacy", "annouce", "preparations", "manual", "store", "category", ChineseMedicine.ON_PUBLISTH, "sortKey");
//	chineseMedicineManager.save(chineseMedicine);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }
    

}
