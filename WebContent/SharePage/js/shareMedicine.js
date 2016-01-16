
function getMedicineMessage(){
	var medicineMessage="";
	var annouce="";
	var category="";
	var content="";
	var efficacy="";
	var manual="";
	var name="";
	var preparations="";
	var store="";
	var current_application="";
	var dose_explain="";
	var interaction="";
	var other_name="";
	var pharmacolo="";
	var warn="";
	var adrs="";
	var businessName="";
	$.ajax({
		url:"http://112.74.131.194:8080/MedicineProject/share/medicine/get",
		type:"GET",
		data:{
			type:$(".medicineType").val(),
			id:$(".medicineId").val()
		},
		dataType:"json",
		success:function(data){
			switch(parseInt($(".medicineType").val())){//1：中药；2：西药；3：企业中药；4企业西药
			case 1:
			case 3:{
				if(data.medicine.name!="") name="<li class=\"title\">药品名称</li><li>"+data.medicine.name+"</li>";
				if(data.medicine.content!="") content="<li class=\"title\">药品成分</li><li>"+data.medicine.content+"</li>";
				if(data.medicine.efficacy!="") efficacy="<li class=\"title\">功效主治</li><li>"+data.medicine.efficacy+"</li>";
				if(data.medicine.annouce!="") annouce="<li class=\"title\">用药监护</li><li>"+data.medicine.annouce+"</li>";
				if(data.medicine.preparations!="") preparations="<li class=\"title\">制剂规格</li><li>"+data.medicine.preparations+"</li>";
				if(data.medicine.manual!="") manual="<li class=\"title\">用法用量</li><li>"+data.medicine.manual+"</li>";
				if(data.medicine.store!="") store="<li class=\"title\">贮法</li><li>"+data.medicine.store+"</li>";
				if(data.medicine.category!="") category="<li class=\"title\">管理分类</li><li>"+data.medicine.category+"</li>";
				medicineMessage=name+content+efficacy+annouce+preparations+manual+store+category;
				break;
			}
			case 2:
			case 4:{
				if(data.medicine.name!="") name="<li class=\"title\">药品名称</li><li>"+data.medicine.name+"</li>";
				if(data.medicine.content!="") content="<li class=\"title\">组成成分</li><li>"+data.medicine.content+"</li>";
				if(data.medicine.current_application!="") current_application="<li class=\"title\">【临床运用】</li><li>"+data.medicine.current_application+"</li>";
				if(data.medicine.other_name!="") other_name="<li class=\"title\">其他名称</li><li>"+data.medicine.other_name+"</li>";
				if(data.medicine.preparations!="") preparations="<li class=\"title\">制剂规格</li><li>"+data.medicine.preparations+"</li>";
				if(data.medicine.manual!="") manual="<li class=\"title\">用法用量</li><li>"+data.medicine.manual+"</li>";
				if(data.medicine.pharmacolo!="") pharmacolo="<li class=\"title\">药理学</li><li>"+data.medicine.pharmacolo+"</li>";
				if(data.medicine.dose_explain!="") dose_explain="<li class=\"title\">给药说明</li><li>"+data.medicine.dose_explain+"</li>";
				if(data.medicine.warn!="") warn="<li class=\"title\">注意事项</li><li>"+data.medicine.warn+"</li>";
				if(data.medicine.interaction!="") interaction="<li class=\"title\">药物相互作用</li><li>"+data.medicine.interaction+"</li>";
				if(data.medicine.adrs!="") adrs="<li class=\"title\">不良反应</li><li>"+data.medicine.adrs+"</li>";
				medicineMessage=name+other_name+content+current_application+pharmacolo+warn+adrs+interaction+dose_explain+manual+preparations;
				break;
			}
			default:break;
			}
			if(parseInt($(".medicineType").val())==3||parseInt($(".medicineType").val())==4){
				businessName="<li class=\"title\">企业名称</li><li>"+data.medicine.enterprise_name+"</li>";
				medicineMessage=businessName+medicineMessage;
			}
			$(".shareMedicineContent ul").empty().append(medicineMessage);
		}
	}).error(function(data){
		console.log(data);
	});	
}