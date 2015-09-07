package com.rippletec.medicine.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.service.EnterpriseManager;

@Service(EnterpriseManager.NAME)
public class EnterpriseManagerImpl extends BaseManager<Enterprise> implements EnterpriseManager{

    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;
    
    @Override
    protected FindByPageDao<Enterprise> getDao() {
	return this.enterpriseDao;
    }

    @Override
    public List<Enterprise> getEnterprise(int size, int type, int currentPage) {
	return findByPage(Enterprise.TYPE, type, new PageBean(currentPage, 0, size));
    }

    @Override
    public List<EnterpriseMedicineType> getEnterMedicineTypes(int id) {
	Enterprise enterprise = find(id);
	if(enterprise == null)
	    return new LinkedList<EnterpriseMedicineType>();
	return new LinkedList<EnterpriseMedicineType>(enterprise.getMedicineTypeEnterprises());
    }

}
