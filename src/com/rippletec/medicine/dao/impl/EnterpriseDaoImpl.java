package com.rippletec.medicine.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

/**
 * @author Liuyi
 *
 */
@Repository(EnterpriseDao.NAME)
public class EnterpriseDaoImpl extends BaseDaoImpl<Enterprise> implements
	EnterpriseDao {

    @Override
    public String getClassName() {
	return Enterprise.CLASS_NAME;
    }

    @Override
    public Class<Enterprise> getPersistClass() {
	return Enterprise.class;
    }


}
