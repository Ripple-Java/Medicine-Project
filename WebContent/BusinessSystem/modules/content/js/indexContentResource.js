function add_drug_ChinaOrWestaside_action(number) {//资源管理---药品列表添加选择
    $(".add_drug_ChinaOrWestaside ul li").removeClass("add_drug_ChinaOrWestaside_hadActive");
    $(".add_drug_ChinaOrWestaside ul li:nth-child(" + number + ")").addClass("add_drug_ChinaOrWestaside_hadActive");
    if (number == 1) {
        $(".addBusinessChinaDrug").css("display", "block");
        $(".addBusinessWestDrug").css("display", "none");
    } else {
        $(".addBusinessWestDrug").css("display", "block");
        $(".addBusinessChinaDrug").css("display", "none");
    }
}
function addBusinessChinaDrugButtonFunction() {//资源管理---药品列表---添加中药保存按钮
    var checkIsNull = 0;
    $.each($(".addBusinessChinaDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
        $.post("http://localhost:8080/MedicineProject/Web/admin/addChinMedicine", {
            name: $(".BusinessChinaDrug_name").val(),
            content: $(".BusinessChinaDrug_content").val(),
            efficacy: $(".BusinessChinaDrug_efficacy").val(),
            annouce: $(".BusinessChinaDrug_annouce").val(),
            preparations: $(".BusinessChinaDrug_preparations").val(),
            manual: $(".BusinessChinaDrug_manual").val(),
            store: $(".BusinessChinaDrug_store").val(),
            category: $(".BusinessChinaDrug_category").val(),
            price: $(".BusinessChinaDrug_price").val(),
            medicineType_id: 1
        }, function (data) {
            alert(data.result);
        }, "json");
    }
}
function addBusinessWestDrugButtonFunction() {//资源管理---药品列表---添加西药保存按钮
    var checkIsNull = 0;
    $.each($(".addBusinessWestDrug ul li input[type=\"text\"]"), function (times, result) {
        if ($(result).val() == "") { checkIsNull = 1; return false; }
    });
    if (checkIsNull == 0) {
        $.post("http://localhost:8080/MedicineProject/Web/admin/addWestMedicine", {
            name: $(".BusinessWestDrug_name").val(),
            other_name: $(".BusinessWestDrug_other_name").val(),
            content: $(".BusinessWestDrug_content").val(),
            current_application: $(".BusinessWestDrug_current_application").val(),
            pharmacolo: $(".BusinessWestDrug_pharmacolo").val(),
            pk_pd: $(".BusinessWestDrug_pk_pd").val(),
            pharmacokinetics: $(".BusinessWestDrug_pharmacokinetics").val(),
            warn: $(".BusinessWestDrug_warn").val(),
            ADRS: $(".BusinessWestDrug_ADRS").val(),
            interaction: $(".BusinessWestDrug_interaction").val(),
            dose_explain: $(".BusinessWestDrug_dose_explain").val(),
            manual: $(".BusinessWestDrug_manual").val(),
            adult_dose: $(".BusinessWestDrug_adult_dose").val(),
            foreign_dose: $(".BusinessWestDrug_foreign_dose").val(),
            preparations: $(".BusinessWestDrug_preparations").val(),         
            store: $(".BusinessWestDrug_store").val(),
            price: $(".BusinessWestDrug_price").val(),
            medicineType_id: 2
        }, function (data) {
            alert(data.result);
        }, "json");
    }
}