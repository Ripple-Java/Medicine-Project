/**
 * 
 */
package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

/**
 * 药品Model，为所有通用药品和企业药品提供唯一的id引索
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "medicine")
public class Medicine extends BaseModel {

    private static final long serialVersionUID = 6854826662685735308L;

    public static final String CLASS_NAME = "Medicine";
    public static final String TABLE_NAME = "medicine";
    public static final String MEDICINE_TYPE_ID = "medicine_type_id";
    public static final String ENTER_MEDICINE_TYPE_ID = "enter_medicine_type_id";
    public static final String CHINESE_MEDICINE_ID = "chinese_medicine_id";
    public static final String WEST_MEDICINE_ID = "west_medicine_id";
    public static final String ENTERPRISE_ID = "enterpriseId";
    
    public static final int CHINESE = 1;
    public static final int WEST = 2;
    public static final int ENTER_CHINESE = 3;
    public static final int ENTER_WEST = 4;

    // 药品Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 关联药物文章
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicine")
    @Cascade(CascadeType.ALL)
    @OrderBy(value="id asc")
    private Set<MedicineDocument> medicineDocuments = new LinkedHashSet<MedicineDocument>();

    // 药品所属第一级类型：1=中药，2=西药
    @Column(name = "parentType", length = 1, nullable = false)
    private Integer parentType;
    
    // 关联企业id
    @Column(name = ENTERPRISE_ID, nullable = true)
    private Integer enterpriseId;
    
    
    public Medicine() {
    }


    public Medicine(Integer parentType, Integer enterpriseId) {
	super();
	this.parentType = parentType;
	this.enterpriseId = enterpriseId;
    }


    public Integer getId() {
        return id;
    }

    public Set<MedicineDocument> getMedicineDocuments() {
        return medicineDocuments;
    }


    public Integer getParentType() {
        return parentType;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setMedicineDocuments(Set<MedicineDocument> medicineDocuments) {
        this.medicineDocuments = medicineDocuments;
    }


    public void setParentType(Integer parentType) {
        this.parentType = parentType;
    }


    public Integer getEnterpriseId() {
        return enterpriseId;
    }


    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    
    

}
