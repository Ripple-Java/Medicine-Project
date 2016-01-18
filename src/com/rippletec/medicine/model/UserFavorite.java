package com.rippletec.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.stereotype.Repository;

/**
 * 用户收藏Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name=UserFavorite.TABLE_NAME)
public class UserFavorite extends BaseModel{
    
    private static final long serialVersionUID = -4947333524040803889L;
    public static final String CLASS_NAME = "UserFavorite";
    public static final String TABLE_NAME = "userfavorite";
    
    public static final String USER_ID = "user_id";
    public static final String ID = "id";
    public static final String OBJECT_ID = "object_Id";
    public static final String TYPE = "type";
    public static final String INFO = "info";
    public static final String NAME = "name";
    
    
    
    public static final int MEDICINE_CHINESE = 1;	//中药
    public static final int MEDICINE_WEST = 2;		//西药
    public static final int ENTER_CHINESE = 3;		//企业中药
    public static final int ENTER_WEST = 4;			//企业西药
    public static final int VIDEO = 5 ;			//视频
    public static final int MEETING = 6 ;			//会议
    public static final int ENTERPRISE = 7 ;			//企业
    
    public static final int CASES = 8 ;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Min(0)
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name=USER_ID)
    private User user;
    
    @Column(name = NAME)
    private String name;
    
    @Column(name=OBJECT_ID,length=1,nullable=false)
    private Integer objectId;
    
    @Column(name=TYPE,length = 1, nullable=false)
    private Integer type;
    
    @Column(name = INFO, columnDefinition = "TEXT", nullable = true)
    private String info;
    
    public UserFavorite() {
    }
   
    public UserFavorite(User user, Integer objectId, Integer type, String info) {
	super();
	this.user = user;
	this.objectId = objectId;
	this.type = type;
	this.info = info;
    }

   

    @Override
    public String toString() {
	return "UserFavorite [id=" + id + ", objectId=" + objectId + ", type="
		+ type + "]";
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


    public Integer getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    

  

}
