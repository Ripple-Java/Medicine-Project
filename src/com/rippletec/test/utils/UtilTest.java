package com.rippletec.test.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import com.rippletec.medicine.SMS.client.JsonReqClient;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.EmailUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.StringUtil;


public class UtilTest {
    
    @Test
    public void testGetNumber() throws Exception {
	for (int i = 0; i < 20; i++) {
	    System.out.println(StringUtil.generateCode(6));
	    System.out.println(StringUtil.getAccount());
	}
    }
    
    @Test
    public void testJsonUtil() throws Exception {
	String url = "/enterprise";
	String tip = "success";
	Enterprise enterprise = new Enterprise();
	JsonUtil jsonUtil = new JsonUtil();
	List<Object> list = new LinkedList<Object>();
	list.add(enterprise);
	list.add(tip);
	System.out.println(jsonUtil.toJsonString(url, list));
	list.remove(1);
	jsonUtil.clear();
	System.out.println(jsonUtil.setModelList(list).setResultSuccess().toJsonString(url));
	
    }
    
    @Test
    public void testReflect() throws Exception {
	BaseModel model = new Enterprise();
	Class class1 = model.getClass();
	Field field = class1.getDeclaredField("DJFLSKD");
	if(field != null)
	    System.out.println(field.get(model));
    }
    
    @Test
    public void testSMS() throws Exception {
//	SMS sms = new SMS();
//	sms.send("15622739759", "123456","1");
    }
    
    @Test
    public void testStringUttil() throws Exception {
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE,Enterprise.PHONE}));
    }
    
    @Test
    public void testMD5() throws Exception {
	System.out.println(MD5Util.validPasswd("aabbcc12345", "AE484EF236FB6B6F904E11AB91C0932C65C1C678282CC8FFF0DD477D"));
    }
    
    @Test
    public void testDate() throws Exception {
	Date date = DateUtil.getYearMonthDate(new Date());
	System.out.println(DateUtil.getDateTime(date));
    }
    
    @Test
    public void testEmail() throws Exception {
	System.out.println(EmailUtil.sendEmail("860986808@qq.com" ,StringUtil.RegisterContent("860766334@qq.com", "12345"), "医药汇测试邮件"));
    }
    
    @Test
    public void testEmailContent() throws Exception {
	System.out.println(StringUtil.RegisterContent("860986808@qq.com", "test"));
    }
    
    @Test
    public void testString() throws Exception {
	String string = "【药物成分】羌活、防风、苍术、细辛、川芎、白芷、黄芩、甘草、地黄";
	System.out.println(string.replace("【药物成分】", ""));
    }
    
    @Test
    public void testRoot() throws Exception {
	System.out.println(FileUtil.getRootPath());
	System.out.println(System.getProperty("medicine.root"));
    }

    
    @Test
    public void testPinyin() throws Exception {
	System.out.println(StringUtil.toPinYin("dsfb4中药"));
    }
    
    @Test
    public void testFormatData() throws Exception {
	String data = "【制剂与规格】盐酸丁卡因注射液：(1)3mL:30mg。(2)5mL:50mg。 (3)10mL:30mg。贮法：密闭保存注射用盐酸丁卡因。(1)10mg。 (2)15mg。(3)20mg。(4)25mg。(5)50mgp贮法：遮光，密闭保存。盐酸丁卡因滴眼液 0.5%。盐酸丁卡因眼膏 0.5%。盐酸丁卡因软膏 0.5%。盐酸丁卡因乳膏 1%。盐酸丁卡因溶液 (1)2mL:40mg。(2)5mL:50mg。盐酸丁卡因片 10mg。1贮法：遮光，密闭保存。盐酸丁卡因凝胶  1.5g:70mg。贮法：15℃以下避光贮藏，不能暴露或冷冻。 [国外用法用量参考]成人常规剂量：口服给药：(1)初始剂量：一次20mg/kg, —日1次。(2)维持剂量：如有必要，可根据血清铁蛋白浓度变化每3~6月调整剂量。推荐增量为5mg/kg或10mg/kg，视患者的反应和治疗目的调整，如果血清铁蛋白水平持续下降至低于500ug/L，应考虑暂停用药。(3)最大剂量为一日30mg/kg。肾功能不全时剂量：如血肌酸酐升高，应考虑减量或停药。肝功能不全时剂量：血清氨基转移酶水平严重或持续升髙者应调整剂量。儿童常规剂量：口服给药：2岁及以上儿童同成人。肾功能不全时剂量：同成人。肝功能不全时剂量：同成人。其他疾病时剂量：同成人。";
	System.out.println(StringUtil.formatData(data));
    }
    
    @Test
    public void testFormatData2() throws Exception {
	String data = "【用法与用量】成人常规剂量：吸入给药：1.全麻诱导：(1)引起下颌松弛、完成气管插管：本药浓度为12%~15%。也可配合应用静脉麻醉药或氧化亚氮等。(2)外科麻醉：如术前用过阿片类药，则本药常用起始浓度为3%,每隔2~3次呼吸增加0.5%^%的浓度，当吸人浓度达到4%~11%后，2~4分钟可达到麻醉效果。(3)用静脉麻醉药（如硫喷妥钠或丙泊酹）诱导后，无论是否与氧化亚氮/氧气联用，本药的起始浓度均为0.5~1MAC。2.全麻维持：(1)同氧化亚氮混合吸入，2%~6%浓度可维持在外科麻醉I同氧气或空气/氧气混合吸入，则需2.5%~8.5%浓度；单药吸入，需5.2%~10%浓度。尽管短时间应用本药的浓度可达18%，但如果同氧化亚氮混合高浓度吸入，应确保吸入氧浓度不低于25%。若需要进一步的肌肉松弛，可加用补充剂量的肌肉松弛药。3.平衡麻醉：吸入浓度可维持3%左右。4.控制性降压：吸入浓度为15%~17%。5.门诊小手术：吸入浓度为8%~14%。肾功能不全时剂量：肾功能不全患者无需调整剂量，但肾移植患者当用本药与氧化亚氮/氧气混合吸入时，本药的浓度为1%~4%。肝功能不全时剂量：肝功能不全者无需调整剂量，但慢性肝肾功能不全者当用本药与氧化亚氮/氧气混合吸入时，本药的浓度为1%~4%。儿童常规剂量：吸入给药：全麻维持：需本药浓度为5.2%~10%,才能维持外科麻醉期水平，尽管短时间应用本药的浓度可达18%,但如果同氧化亚氮混合髙浓度吸入，应确保吸人氧浓度不低于25%。";
	System.out.println(StringUtil.formatData(data));
    }

    @Test
    public void testSMS2() throws Exception {
	String accountSid = "c76945aef05a3af729b7fffd725fde47";
	String authToken = "471cece0f1876416386a690af9413dbd";
	String appId = "0b1b1aee07ce46e3a643db3ae6cf6e66";
	String templateId = "15725";
	String to = "18813756456";
	String param = StringUtil.generateCode(6)+","+"1";
	JsonReqClient client = new JsonReqClient();
	String result=client.templateSMS(accountSid, authToken, appId, templateId, to, param);
	System.out.println(result);
    }
    
   

}
