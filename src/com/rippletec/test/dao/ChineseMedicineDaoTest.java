package com.rippletec.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.MedicineDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ChineseMedicineDaoTest implements IBaseDaoTest {
    
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    @Resource(name=MedicineTypeDao.NAME)
    private MedicineTypeDao medicineTypeDao;
    @Resource(name=EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;
    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;
    @Resource(name=MedicineDao.NAME)
	private MedicineDao medicineDao;
    
    @Override
    @Test
    public void testDelete() throws Exception {
	chineseMedicineDao.delete(10);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	ChineseMedicine chineseMedicine = chineseMedicineDao.find(10);
	System.out.println(chineseMedicine.toString());
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
	List<ChineseMedicine> chineseMedicines  = chineseMedicineDao.findByPage(new PageBean(0, 10));
	for (ChineseMedicine chineseMedicine : chineseMedicines) {
	    System.out.println(chineseMedicine.toString());
	}
    }

    @Override
    @Test
    public void testSave() throws Exception {
	MedicineType medicineType = new MedicineType("test1", MedicineType.DEFAULT_PARENT_ID, MedicineType.CHINESE);
	Medicine medicine = new Medicine(Medicine.CHINESE);
	ChineseMedicine chineseMedicine = new ChineseMedicine(medicine, medicineType,"name", "content", "efficacy", "annouce", "preparations", "manual", "store", "category", 100.0, ChineseMedicine.ON_PUBLISTH, "key");
	chineseMedicineDao.save(chineseMedicine);
    }

    @Test
    public void testSearch() throws Exception {
	   List<ChineseMedicine> chineseMedicines = chineseMedicineDao.search(ChineseMedicine.NAME, "一");
	   for (ChineseMedicine chineseMedicine : chineseMedicines) {
	    System.out.println(chineseMedicine.toString());
	}
    }
    
    @Override
    @Test
    public void testUpdate() throws Exception {
	ChineseMedicine chineseMedicine = chineseMedicineDao.find(11);
	chineseMedicine.setName("updateName");
	chineseMedicineDao.update(chineseMedicine);
    }

}
