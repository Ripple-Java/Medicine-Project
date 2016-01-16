/**
 * 
 */
package com.rippletec.medicine.model;

import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.ChMedicineVO;


/**
 * 通用中药Model类
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "chinese_medicine")
public class ChineseMedicine extends BaseModel {

    protected static final long serialVersionUID = -7463751072679829110L;

    public static final String CLASS_NAME = "ChineseMedicine";
    public static final String TABLE_NAME = "chinese_medicine";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";

    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;

    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    // 关联药品表
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = MEDICINE_ID)
    protected Medicine medicine;

    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_TYPE_ID)
    protected MedicineType medicineType;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chineseMedicine")
    @Cascade(CascadeType.ALL)
    protected Set<EnterChineseMedicine> enterChineseMedicines = new LinkedHashSet<EnterChineseMedicine>();

    // 中药药品名称
    @Column(name = "name", length = 50, nullable = false)
    protected String name;

    // 药物成分
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    protected String content;

    // 功效主治
    @Column(name = "efficacy", columnDefinition = "TEXT", nullable = true)
    protected String efficacy;

    // 用药监护
    @Column(name = "annouce", columnDefinition = "TEXT", nullable = true)
    protected String annouce;

    // 制剂规格
    @Column(name = "preparations", columnDefinition = "TEXT", nullable = true)
    protected String preparations;

    // 用法用量
    @Column(name = "manual", columnDefinition = "TEXT", nullable = true)
    protected String manual;

    // 贮法
    @Column(name = "store", columnDefinition = "TEXT", nullable = true)
    protected String store;

    // 管理分类
    @Column(name = "category", columnDefinition = "TEXT", nullable = true)
    protected String category;


    // 药品发布状态
    @Column(name = "status", length = 1, nullable = false)
    protected Integer status;
    
    
    //排序关键字
    @Column(name = "sortKey", length = 255, nullable =false)
    protected String sortKey;

    public ChineseMedicine() {
    }

    public ChineseMedicine(Integer id, String name, String content,
	    String efficacy, String annouce, String preparations,
	    String manual, String store, String category, Integer status,
	    String sortKey) {
	super();
	this.id = id;
	this.name = name;
	this.content = content;
	this.efficacy = efficacy;
	this.annouce = annouce;
	this.preparations = preparations;
	this.manual = manual;
	this.store = store;
	this.category = category;
	this.status = status;
	this.sortKey = sortKey;
    }






    public ChineseMedicine(ChMedicineVO chMedicineVO, Medicine medicine, MedicineType medicineType) {
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.name = chMedicineVO.name;
	this.content = chMedicineVO.content;
	this.efficacy = chMedicineVO.efficacy;
	this.annouce = chMedicineVO.annouce;
	this.preparations = chMedicineVO.preparations;
	this.manual = chMedicineVO.manual;
	this.store = chMedicineVO.store;
	this.category = chMedicineVO.category;
	this.status = ChineseMedicine.ON_PUBLISTH;
	this.sortKey = StringUtil.toPinYin(chMedicineVO.name);
    }
    

    public void setUpdate(ChMedicineVO chMedicineVO, MedicineType medicineType) {
	this.medicineType = medicineType;
	this.name = chMedicineVO.name;
	this.content = chMedicineVO.content;
	this.efficacy = chMedicineVO.efficacy;
	this.annouce = chMedicineVO.annouce;
	this.preparations = chMedicineVO.preparations;
	this.manual = chMedicineVO.manual;
	this.store = chMedicineVO.store;
	this.category = chMedicineVO.category;
	this.status = ChineseMedicine.ON_PUBLISTH;
	this.sortKey = StringUtil.toPinYin(chMedicineVO.name);
    }




    @Override
    public String toString() {
	return "ChineseMedicine [id=" + id + ", name=" + name + ", content="
		+ content + ", efficacy=" + efficacy + ", annouce=" + annouce
		+ ", preparations=" + preparations + ", manual=" + manual
		+ ", store=" + store + ", category=" + category  + ", status=" + status + "]";
    }

    public String getAnnouce() {
	return annouce;
    }

    public String getCategory() {
	return category;
    }

    public String getContent() {
	return content;
    }

    public String getEfficacy() {
	return efficacy;
    }

    public Integer getId() {
	return id;
    }

    public String getManual() {
	return manual;
    }

    public Medicine getMedicine() {
	return medicine;
    }

    public MedicineType getMedicineType() {
	return medicineType;
    }

    public String getName() {
	return name;
    }

    public String getPreparations() {
	return preparations;
    }



    public String getStore() {
	return store;
    }

    public void setAnnouce(String annouce) {
	this.annouce = annouce;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setEfficacy(String efficacy) {
	this.efficacy = efficacy;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setManual(String manual) {
	this.manual = manual;
    }

    public void setMedicine(Medicine medicine) {
	this.medicine = medicine;
    }

    public void setMedicineType(MedicineType medicineType) {
	this.medicineType = medicineType;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPreparations(String preparations) {
	this.preparations = preparations;
    }


    public void setStore(String store) {
	this.store = store;
    }

    public Integer getStatus() {
        return status;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public Set<EnterChineseMedicine> getEnterChineseMedicines() {
        return enterChineseMedicines;
    }

    public void setEnterChineseMedicines(
    	Set<EnterChineseMedicine> enterChineseMedicines) {
        this.enterChineseMedicines = enterChineseMedicines;
    }







    
    
    
    
    

}