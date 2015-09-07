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
    public static final String NAME = "name";
    public static final int CHINESE = 1;
    public static final int WEST = 2;

    private static final long serialVersionUID = -7346972843349376711L;

    public EnterpriseMedicineType() {
    }

    

    public EnterpriseMedicineType(String name, Integer gib_type,
	    Enterprise enterprise) {
	super();
	this.name = name;
	this.gib_type = gib_type;
	this.enterprise = enterprise;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 关联药物
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseMedicineType")
    @Cascade(CascadeType.ALL)
    @OrderBy(value="id asc")
    private Set<Medicine> medicines = new LinkedHashSet<Medicine>();

    // 企业药品类别名称
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    
    // 药品所属第一级类型：1=中药，2=西药
    @Column(name = "gib_type", length = 1, nullable = false)
    private Integer gib_type;
    
    // 关联企业
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;

    

    @Override
    public String toString() {
	return "EnterpriseMedicineType [id=" + id + ", name=" + name
		+ ", gib_type=" + gib_type + ", enterprise=" + enterprise + "]";
    }

    public Integer getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public Enterprise getEnterprise() {
	return enterprise;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setEnterprise(Enterprise enterprise) {
	this.enterprise = enterprise;
    }

    public Set<Medicine> getMedicines() {
	return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
	this.medicines = medicines;
    }

    public Integer getGib_type() {
        return gib_type;
    }

    public void setGib_type(Integer gib_type) {
        this.gib_type = gib_type;
    }
    
    

}
