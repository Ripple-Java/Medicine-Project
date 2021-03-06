package com.rippletec.test.dao;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class MedicineTypeDaoTest implements IBaseDaoTest {
    
    @Resource(name="MedicineTypeDao")
    public MedicineTypeDao medicineTypeDao;

    @Override
    @Test
    public void testDelete() throws Exception {
	medicineTypeDao.delete(1);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	System.out.println(medicineTypeDao.find(6).toString());
    }
    
    @Test
    public void testSearch() throws Exception {
	List<MedicineType> medicineTypes = medicineTypeDao.findByParam(MedicineType.NAME, "内酰胺酶抑制药");
	for (MedicineType medicineType : medicineTypes) {
	    Iterator< WestMedicine> iterator = medicineType.getWestMedicines().iterator();
	    while (iterator.hasNext()) {
		WestMedicine westMedicine = iterator.next();
		System.out.println(westMedicine.toString());
	    }
	}
    }

//    @Test
//    public void testFind2() throws Exception {
//	MedicineType medicineType = medicineTypeDao.find(15);
//	while (iterator.hasNext()) {
//	    Medicine medicine = iterator.next();
//	}
//    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
	List<MedicineType> medicineTypes = medicineTypeDao.findByPage(new PageBean(0, 10));
	for (MedicineType medicineType : medicineTypes) {
	    System.out.println(medicineType.toString());
	}
    }
    
    @Override
    @Test
    public void testSave() throws Exception {
	MedicineType medicineType = new MedicineType("西药一级分类", MedicineType.DEFAULT_PARENT_ID, MedicineType.WEST);
	medicineTypeDao.save(medicineType);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
	MedicineType medicineType = medicineTypeDao.find(6);
	medicineType.setParent_type_id(3);
	medicineTypeDao.update(medicineType);
    }

}
