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
import com.rippletec.medicine.vo.web.WestMedicineVO;

/**
 * 通用西药信息Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "west_medicine")
public class WestMedicine extends BaseModel {

    public static final String CLASS_NAME = "WestMedicine";
    public static final String TABLE_NAME = "west_medicine";
    public static final String MEDICINE_ID = "medicine_id";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    
    protected static final long serialVersionUID = 5451907990648871088L;
    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    // 关联药品表
    @OneToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = MEDICINE_ID)
    protected Medicine medicine;


    // 关联所属类别
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_TYPE_ID)
    protected MedicineType medicineType;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "westMedicine")
    @Cascade(CascadeType.ALL)
    protected Set<EnterWestMedicine> enterWestMedicines = new LinkedHashSet<EnterWestMedicine>();
    
    // 西药药品名称
    @Column(name = "name", length = 255, nullable = false)
    protected String name;
    
    // 别名
    @Column(name = "other_name", columnDefinition = "TEXT", nullable = false)
    protected String other_name;

    // 组成成分
    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    protected String content;

    // 临床运用
    @Column(name = "current_application", columnDefinition = "TEXT", nullable = true)
    protected String current_application;

    // 药理学
    @Column(name = "pharmacolo", columnDefinition = "TEXT", nullable = true)
    protected String pharmacolo;

    // 注意事项
    @Column(name = "warn", columnDefinition = "TEXT", nullable = true)
    protected String warn;

    // 不良反应
    @Column(name = "ADRS", columnDefinition = "TEXT", nullable = true)
    protected String adrs;

    // 药物相互作用
    @Column(name = "interaction", columnDefinition = "TEXT", nullable = true)
    protected String interaction;

    // 给药说明
    @Column(name = "dose_explain", columnDefinition = "TEXT", nullable = true)
    protected String dose_explain;

    // 用法用量
    @Column(name = "manual", columnDefinition = "TEXT", nullable = true)
    protected String  manual;

    // 制剂与规格
    @Column(name = "preparations", columnDefinition = "TEXT", nullable = true)
    protected String preparations;

    
    // 药品发布状态
    @Column(name= "status", length=1, nullable=false)
    protected Integer status;
    
    @Column(name="sortKey", length=255, nullable=false)
    protected String sortKey;
    

    public WestMedicine() {
    }
    public WestMedicine(Integer id, String name, String other_name,
	    String content, String current_application, String pharmacolo,
	    String warn, String adrs, String interaction, String dose_explain,
	    String manual, String preparations, Integer status, String sortKey) {
	super();
	this.id = id;
	this.name = name;
	this.other_name = other_name;
	this.content = content;
	this.current_application = current_application;
	this.pharmacolo = pharmacolo;
	this.warn = warn;
	this.adrs = adrs;
	this.interaction = interaction;
	this.dose_explain = dose_explain;
	this.manual = manual;
	this.preparations = preparations;
	this.status = status;
	this.sortKey = sortKey;
    }



    public WestMedicine(Medicine medicine, MedicineType medicineType,
	    String name, String other_name, String content,
	    String current_application, String pharmacolo, String warn, String adrs,
	    String interaction, String dose_explain, String manual, String preparations, Integer status, String sortKey) {
	super();
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.name = name;
	this.other_name = other_name;
	this.content = content;
	this.current_application = current_application;
	this.pharmacolo = pharmacolo;
	this.warn = warn;
	this.adrs = adrs;
	this.interaction = interaction;
	this.dose_explain = dose_explain;
	this.manual = manual;
	this.preparations = preparations;
	this.status = status;
	this.sortKey = sortKey;
    }

    public WestMedicine(WestMedicineVO westMedicineVO, Medicine medicine,
	    MedicineType medicineType) {
	this.medicine = medicine;
	this.medicineType = medicineType;
	this.name = westMedicineVO.name;
	this.other_name = westMedicineVO.other_name;
	this.content = westMedicineVO.content;
	this.current_application = westMedicineVO.current_application;
	this.pharmacolo = westMedicineVO.pharmacolo;
	this.warn = westMedicineVO.warn;
	this.adrs = westMedicineVO.adrs;
	this.interaction = westMedicineVO.interaction;
	this.dose_explain = westMedicineVO.dose_explain;
	this.manual=  westMedicineVO.manual;
	this.preparations = westMedicineVO.preparations;
	this.status = WestMedicine.ON_PUBLISTH;
	this.sortKey = StringUtil.toPinYin(westMedicineVO.name);
    }
    
    public void setUpdate(WestMedicineVO westMedicineVO,
	    MedicineType medicineType) {
	this.medicineType = medicineType;
	this.name = westMedicineVO.name;
	this.other_name = westMedicineVO.other_name;
	this.content = westMedicineVO.content;
	this.current_application = westMedicineVO.current_application;
	this.pharmacolo = westMedicineVO.pharmacolo;
	this.warn = westMedicineVO.warn;
	this.adrs = westMedicineVO.adrs;
	this.interaction = westMedicineVO.interaction;
	this.dose_explain = westMedicineVO.dose_explain;
	this.manual=  westMedicineVO.manual;
	this.preparations = westMedicineVO.preparations;
	this.status = WestMedicine.ON_PUBLISTH;
	this.sortKey = StringUtil.toPinYin(westMedicineVO.name);
    }


    public String getContent() {
	return content;
    }

    public String getCurrent_application() {
	return current_application;
    }

    public String getDose_explain() {
	return dose_explain;
    }


    public Integer getId() {
	return id;
    }

    public String getInteraction() {
	return interaction;
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

    public String getOther_name() {
	return other_name;
    }


    public String getPharmacolo() {
	return pharmacolo;
    }


    public String getPreparations() {
	return preparations;
    }


    public String getWarn() {
	return warn;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setCurrent_application(String current_application) {
	this.current_application = current_application;
    }

    public void setDose_explain(String dose_explain) {
	this.dose_explain = dose_explain;
    }


    public void setId(Integer id) {
	this.id = id;
    }

    public void setInteraction(String interaction) {
	this.interaction = interaction;
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

    public void setOther_name(String other_name) {
	this.other_name = other_name;
    }


    public void setPharmacolo(String pharmacolo) {
	this.pharmacolo = pharmacolo;
    }


    public void setPreparations(String preparations) {
	this.preparations = preparations;
    }


    public void setWarn(String warn) {
	this.warn = warn;
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
    
    

    public Set<EnterWestMedicine> getEnterWestMedicines() {
        return enterWestMedicines;
    }

    public void setEnterWestMedicines(Set<EnterWestMedicine> enterWestMedicines) {
        this.enterWestMedicines = enterWestMedicines;
    }
    
    

    public String getAdrs() {
        return adrs;
    }
    public void setAdrs(String adrs) {
        this.adrs = adrs;
    }
    @Override
    public String toString() {
	return "WestMedicine [name=" + name + ", other_name=" + other_name
		+ ", content=" + content + ", current_application="
		+ current_application + ", pharmacolo=" + pharmacolo
		+ ", warn=" + warn + ", ADRS=" + adrs + ", interaction="
		+ interaction + ", dose_explain=" + dose_explain + ", manual="
		+ manual + ", preparations=" + preparations + ", status=" + status + ", sortKey=" + sortKey + "]";
    }



  

    
    

}
