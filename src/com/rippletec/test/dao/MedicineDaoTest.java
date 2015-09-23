/**
 * 
 */
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
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.MedicineDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterpriseMedicineType;
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
public class MedicineDaoTest implements IBaseDaoTest{
    
    	@Resource(name=MedicineDao.NAME)
	private MedicineDao medicineDao;
    	
    	@Resource(name=MedicineTypeDao.NAME)
        public MedicineTypeDao medicineTypeDao;
    	
    	@Resource(name=ChineseMedicineDao.NAME)
        private ChineseMedicineDao chineseMedicineDao;
    	
    	@Resource(name=EnterpriseMedicineTypeDao.NAME)
        private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;

	@Override
	@Test
	public void testDelete() throws Exception {
		medicineDao.delete(1);
	    
	}
	
	@Override
	@Test
	public void testFind() throws Exception {
	    medicineDao.find(1);
	}
	
	@Test
	public void testFind2() throws Exception {
	    System.out.println(medicineDao.find(1).getMedicineDocuments().toString());
	}


	@Override
	@Test
	public void testFindByPage() throws Exception {
	    List<Medicine> medicines = medicineDao.findByPage(new PageBean(0, 10));
	    for (Medicine medicine : medicines) {
	    }
	}

	@Override
	@Test
	public void testSave() throws Exception {
	    EnterpriseMedicineType enterpriseMedicineType = enterpriseMedicineTypeDao.find(11);
	    MedicineType medicineType = medicineTypeDao.find(15);
	    Medicine medicine = new Medicine();
	    medicineDao.save(medicine);
	}

	@Test
	public void testSave2 () throws Exception {
	    EnterpriseMedicineType enterpriseMedicineType = enterpriseMedicineTypeDao.find(14);
	    MedicineType medicineType = medicineTypeDao.find(18);
	    for (int i = 0; i < 10; i++) {
		ChineseMedicine chineseMedicine = new ChineseMedicine();
		    Medicine medicine = new Medicine();
		    chineseMedicine.setMedicine(medicine);
//		    medicine.getChineseMedicine().add(chineseMedicine);
		    medicineDao.save(medicine);
	    }
	    
	}
	
	
	@Test
	public void testSave3 () throws Exception {
	    EnterpriseMedicineType enterpriseMedicineType = enterpriseMedicineTypeDao.find(15);
	    MedicineType medicineType = medicineTypeDao.find(19);
	    for (int i = 0; i < 10; i++) {
//		WestMedicine westMedicine = new WestMedicine(null,medicineType, "测哈哈", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west", "west",888.0);
//		    Medicine medicine = new Medicine();
//		    westMedicine.setMedicine(medicine);
////		    medicine.getWestMedicine().add(westMedicine);
//		    medicineDao.save(medicine);
	    }
	    
	}

	@Test
	public void testSearch() throws Exception {
	    List<Medicine> medicines = medicineDao.search(Medicine.ENTER_MEDICINE_TYPE_ID, 14);
	    for (Medicine medicine : medicines) {
		System.out.println(medicine.toString());
	    }
	}
	
	@Override
	@Test
	public void testUpdate() throws Exception {
	    MedicineType medicineType = new MedicineType("testMedicineType",1,MedicineType.CHINESE);
	    medicineType.setId(6);
	    Medicine medicine = new Medicine();
	    medicine.setId(15);
	    medicineDao.update(medicine);
	}
	
}
