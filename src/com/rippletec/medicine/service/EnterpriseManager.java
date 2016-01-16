package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;

public interface EnterpriseManager extends IManager<Enterprise>{
    public static final String NAME = "EnterpriseManager";

    List<EnterpriseMedicineType> getEnterMedicineTypes(int id) throws DaoException;

    List<Enterprise> getEnterprise(int size, int type, int page);

    void deleteByUser(int id);

    Enterprise findByUser(User user);

    Result updateInfo(int enterpriseId, EnterpriseInfoVO vo) throws DaoException;

    Result active(int id) throws DaoException;

    Result block(int id) throws DaoException;

    Result unblock(int id) throws DaoException;

    List<Enterprise> getValiatedEnterprises(PageBean pageBean);

    List<Enterprise> getEnterprise(String Param, List<Object> values,
	    PageBean pBean, String orderStr);

    void validEnterPrise(User user);

    List<Enterprise> getEnterpriseByType(int type, PageBean pageBean);


  
}
