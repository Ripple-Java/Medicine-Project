function loadingBusinessManagement_businessList(page, size) {//加载药品列表
    var Message="";
    var checkbox="";
    var Name="";
    var Type = "";
    var Function="";   
    $.ajax({
    	url:"http://112.74.131.194:8080/MedicineProject/Web/admin/getEnterprises",
    	data:{
            page:page,
            size: size
        }, 
        success:function (data,status) {
           if(data.result.trim()=="success"){
            	$.each(data.Enterprises, function (times, result) {
                    checkbox = "<td><input type=\"checkbox\" value=\""+result.id +"\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></td>";
                    Name = "<td>" + result.name + "</td>";
                    switch(result.type){
                    case 0:Type='内资';break;
                    case 1:Type='外资';break;
                    case 2:Type='合资';break;
                    }
                    Type = "<td>" + Type +"</td>";
                    Function = "<td class=\"list_button businessList_button\"><ul><li class=\"businessList_list_editButton iconfont icon\"><a>&#xe630;</a></li><li class=\"businessList_list_freezeButton iconfont icon\"><a>&#xe647;</a></li></ul></td>"
                    Message = Message + "<tr>" + checkbox + Name + Type + Function + "</tr>";
                });
            	$(".businessList_tbody").empty().prepend(Message);
            }   
        },
        dataType:"json",
        type:"GET"
    }).error(function (data,status) {
            alert(data+"--"+status);
        });
}