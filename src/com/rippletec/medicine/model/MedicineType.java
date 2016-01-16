/**
 * 
 */
package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/**
 * 通用药品类型Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "medicine_type")
public class MedicineType extends BaseModel {
    
    public static final String CLASS_NAME = "MedicineType";
    public static final String TABLE_NAME = "medicine_type";
    public static final String PARENT_TYPE_ID = "parent_type_id";
    public static final String BACKGROUND_MEDICINETYPE_ID = "backGroundMedicineType_id";
    public static final String FLAG = "flag";
    public static final String NAME = "name";
    public static final String GIB_TYPE = "gib_type";
    public static final int CHINESE = 1;
    public static final int WEST = 2;

    // 最顶级分类的父类别id为-1
    public static final int DEFAULT_PARENT_ID = -1;

    private static final long serialVersionUID = -521600701786316983L;
    public static final String ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // 后台分类格式表
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = BACKGROUND_MEDICINETYPE_ID)
    private BackGroundMedicineType backGroundMedicineType;
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="medicineType")
    private Set<ChineseMedicine> chineseMedicines = new LinkedHashSet<ChineseMedicine>();
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="medicineType")
    private Set<WestMedicine> westMedicines = new LinkedHashSet<WestMedicine>();
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="medicineType")
    private Set<EnterChineseMedicine> enterChineseMedicines = new LinkedHashSet<EnterChineseMedicine>();
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="medicineType")
    private Set<EnterWestMedicine> enterWestMedicines = new LinkedHashSet<EnterWestMedicine>();
    // 分类名称
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    // 父类别id
    @Column(name = PARENT_TYPE_ID, nullable = false)
    private Integer parent_type_id;
    
    // 药品所属第一级类型：1=中药，2=西药
    @Column(name = "gib_type", length = 1, nullable = false)
    private Integer gib_type;
    
    @Column(name = "flag", length = 1, nullable=true)
    private Boolean flag;

    public MedicineType() {
    }

    public MedicineType(String name, Integer parent_type_id, Integer gib_type) {
	super();
	this.name = name;
	this.parent_type_id = parent_type_id;
	this.gib_type = gib_type;
	this.flag = false;
    }
    public MedicineType(String name, Integer parent_type_id, Integer gib_type, Boolean tag) {
   	super();
   	this.name = name;
   	this.parent_type_id = parent_type_id;
   	this.gib_type = gib_type;
   	this.flag = tag;
       }
    
    public MedicineType(String name, MedicineType parent_type) {
	super();
	this.name = name;
	this.parent_type_id = parent_type.getId();
    }

    

    public Integer getGib_type() {
        return gib_type;
    }

    public Integer getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public Integer getParent_type_id() {
	return parent_type_id;
    }

    public void setGib_type(Integer gib_type) {
        this.gib_type = gib_type;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setParent_type_id(Integer parent_type_id) {
	this.parent_type_id = parent_type_id;
    }

    public Set<ChineseMedicine> getChineseMedicines() {
        return chineseMedicines;
    }

    public Set<WestMedicine> getWestMedicines() {
        return westMedicines;
    }

    public void setChineseMedicines(Set<ChineseMedicine> chineseMedicines) {
        this.chineseMedicines = chineseMedicines;
    }

    public void setWestMedicines(Set<WestMedicine> westMedicines) {
        this.westMedicines = westMedicines;
    }
    
    

    public BackGroundMedicineType getBackGroundMedicineType() {
        return backGroundMedicineType;
    }

    public void setBackGroundMedicineType(
    	BackGroundMedicineType backGroundMedicineType) {
        this.backGroundMedicineType = backGroundMedicineType;
    }

    public Set<EnterChineseMedicine> getEnterChineseMedicines() {
        return enterChineseMedicines;
    }

    public void setEnterChineseMedicines(
    	Set<EnterChineseMedicine> enterChineseMedicines) {
        this.enterChineseMedicines = enterChineseMedicines;
    }

    public Set<EnterWestMedicine> getEnterWestMedicines() {
        return enterWestMedicines;
    }

    public void setEnterWestMedicines(Set<EnterWestMedicine> enterWestMedicines) {
        this.enterWestMedicines = enterWestMedicines;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    
    public boolean hasChild(){
	return !flag;
    }

    @Override
    public String toString() {
	return "MedicineType [id=" + id + ", name=" + name
		+ ", parent_type_id=" + parent_type_id + ", gib_type="
		+ gib_type + "]";
    }

    @Override
    public boolean equals(Object obj) {
	if(obj == null)
	    return false;
	if(obj instanceof MedicineType){
	    MedicineType medicineTypeObject = (MedicineType) obj;
	    return (medicineTypeObject.getName().equals(this.getName()) && medicineTypeObject.getParent_type_id().equals(this.getParent_type_id()) && medicineTypeObject.getGib_type().equals(this.getGib_type())) ? true : false;	    
	}
	return false;
	    
    }
    
    
    
}
