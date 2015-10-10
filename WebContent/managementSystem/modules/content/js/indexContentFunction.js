function checkboxFunction(id) {  
    if ($('#checkbox-'+id).is(':checked')) {
        $('#checkbox-'+id+':checked + label').css({
            "background-image":"url(images/checkbox_checked.png)"
        });
    } else {
        $('#checkbox-'+id+'+ label').css({
            "background-image": "images/checkbox_unchecked.png"
        });
    }
}
function loadingResourceManagement_drugList(pageSize, pageNum) {//加载药品列表
    var drugMessage="";
    var checkbox="";
    var drugName="";
    var drugBusiness="";
    var drugFunction="";
    var drugType = "";
    $.getJSON("http://112.74.131.194:8080/MedicineProject/Web/admin/getMedicine", {
        pageSize: pageSize,
        pageNum: pageNum,
        type: 5
    }, function (data,status) {
        alert(data.tip);
        if(data.result.trim()!="fail")
        $.each(data, function (times, result) {
            checkbox = "<td><input type=\"checkbox\" id=\"checkbox-" + result.id + "\" onclick=\"checkboxFunction(" + result.id + ");\" /><label for=\"checkbox-" + result.id + "\"></label></td>";
            drugName = "<td>" + result.name + "</td>";
            drugBusiness = "<td>" + result.enterpriseName + "</td>";
            drugType = "<td>" + result.type.firstType + "-" + result.type.secondType + "-" + result.type.thirdType + "-" + result.type.forthType;
            drugFunction = "<td class=\"list_button resourceList\"><ul><li class=\"drugList_list_editButton iconfont \"><a>&#xe630;</a></li><li class=\"drugList_list_deleteButton iconfont \"><a>&#xe61f;</a></li></ul></td>"
            drugMessage = drugmessage + "<tr>" + checkbox + drugName + drugType + drugBusiness + drugFunction + "</tr>";
            $(".list_list").prepend(drugMessage);
        });
    }).error(function (data,status) {
            alert(data+"--"+status);
        });
}
function add_drug_ChinaOrWestaside_action(number) {//资源管理---药品列表添加选择
    $(".add_drug_ChinaOrWestaside ul li").removeClass("add_drug_ChinaOrWestaside_hadActive");
    $(".add_drug_ChinaOrWestaside ul li:nth-child(" + number + ")").addClass("add_drug_ChinaOrWestaside_hadActive");
    if (number == 1) {
        $(".addCommonChinaDrug").css("display", "block");
        $(".addCommonWestDrug").css("display", "none");
    } else {
        $(".addCommonWestDrug").css("display", "block");
        $(".addCommonChinaDrug").css("display", "none");
    }
}
function addCommonChinaDrugButtonFunction() {//资源管理---药品列表---添加中药保存按钮
    var checkIsNull = 0;
    $.each($(".addCommonChinaDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
        $.post("http://112.74.131.194:8080/MedicineProject/Web/admin/addChinMedicine", {
            name: $(".CommonChinaDrug_name").val(),
            content: $(".CommonChinaDrug_content").val(),
            efficacy: $(".CommonChinaDrug_efficacy").val(),
            annouce: $(".CommonChinaDrug_annouce").val(),
            preparations: $(".CommonChinaDrug_preparations").val(),
            manual: $(".CommonChinaDrug_manual").val(),
            store: $(".CommonChinaDrug_store").val(),
            category: $(".CommonChinaDrug_category").val(),
            price: $(".CommonChinaDrug_price").val(),
            medicineType_id: 1
        }, function (data) {
            alert(data.result);
        }, "json");
    }
}
function addCommonWestDrugButtonFunction() {//资源管理---药品列表---添加西药保存按钮
    var checkIsNull = 0;
    $.each($(".addCommonWestDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
        $.post("http://112.74.131.194:8080/MedicineProject/Web/admin/addWestMedicine", {
            name: $(".CommonWestDrug_name").val(),
            other_name: $(".CommonWestDrug_other_name").val(),
            content: $(".CommonWestDrug_content").val(),
            current_application: $(".CommonWestDrug_current_application").val(),
            pharmacolo: $(".CommonWestDrug_pharmacolo").val(),
            pk_pd: $(".CommonWestDrug_pk_pd").val(),
            pharmacokinetics: $(".CommonWestDrug_pharmacokinetics").val(),
            warn: $(".CommonWestDrug_warn").val(),
            ADRS: $(".CommonWestDrug_ADRS").val(),
            interaction: $(".CommonWestDrug_interaction").val(),
            dose_explain: $(".CommonWestDrug_dose_explain").val(),
            manual: $(".CommonWestDrug_manual").val(),
            adult_dose: $(".CommonWestDrug_adult_dose").val(),
            foreign_dose: $(".CommonWestDrug_foreign_dose").val(),
            preparations: $(".CommonWestDrug_preparations").val(),         
            store: $(".CommonWestDrug_store").val(),
            price: $(".CommonWestDrug_price").val(),
            medicineType_id: 2
        }, function (data) {
            alert(data.result);
        }, "json");
    }
}