package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

/**
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = EnterpriseMedicineType.TABLE_NAME)
public class EnterpriseMedicineType extends BaseModel {

    public static final String CLASS_NAME = "EnterpriseMedicineType";
    public static final String TABLE_NAME = "enterprise_medicine_type";
    public static final String ENTERPRISE_ID = "enterprise_id";
    public static final String GIB_TYPE = "gib_type";
    public static final String BACKGROUND_MEDICINETYPE_ID = "backGroundMedicineType_id";
    public static final String NAME = "name";
    public static final int CHINESE = 1;
    public static final int WEST = 2;

    private static final long serialVersionUID = -7346972843349376711L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    // 关联药物
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseMedicineType")
//    @Cascade(CascadeType.ALL)
//    @OrderBy(value = "id asc")
//    private Set<EnterChineseMedicine> enterChineseMedicines = new LinkedHashSet<EnterChineseMedicine>();
//
//    // 关联药物
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseMedicineType")
//    @Cascade(CascadeType.ALL)
//    @OrderBy(value = "id asc")
//    private Set<EnterWestMedicine> enterWestMedicines = new LinkedHashSet<EnterWestMedicine>();


    // 药品所属第一级类型：1=中药，2=西药
    @Column(name = "gib_type", length = 1, nullable = false)
    private Integer gib_type;
    
    // 关联企业
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    // 后台分类格式表
    @OneToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = BACKGROUND_MEDICINETYPE_ID)
    private BackGroundMedicineType backGroundMedicineType;

    public EnterpriseMedicineType() {
    }

   


    public EnterpriseMedicineType(MedicineType medicineType, Enterprise enterprise) {
	super();
	this.gib_type = medicineType.getGib_type();
	this.enterprise = enterprise;
    }




    @Override
    public String toString() {
	return "EnterpriseMedicineType [id=" + id + ", gib_type=" + gib_type  + "]";
    }

    public Enterprise getEnterprise() {
	return enterprise;
    }

    public Integer getGib_type() {
	return gib_type;
    }

    public Integer getId() {
	return id;
    }


    public void setEnterprise(Enterprise enterprise) {
	this.enterprise = enterprise;
    }

    public void setGib_type(Integer gib_type) {
	this.gib_type = gib_type;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public BackGroundMedicineType getBackGroundMedicineType() {
        return backGroundMedicineType;
    }

    public void setBackGroundMedicineType(
    	BackGroundMedicineType backGroundMedicineType) {
        this.backGroundMedicineType = backGroundMedicineType;
    }
    
  

}
