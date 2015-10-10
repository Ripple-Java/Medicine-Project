angular.module("doctor_app", []).run(function () {
    if (document.cookie == "") { alert("用户未登录！"); location.href = "./backgroundLogin.html"; }
    resize();
}).controller('my', ['$scope', function (scope) {
    scope.pageContent = 'modules/content/index_content_statisticalHome.html';//左侧导航栏按钮功能
    scope.sidebarClick = function (value, pageName) {
        $(".aside_sidebar li").removeClass("sidebar_click");
        $(".aside_sidebar li:nth-child(" + value + ")").addClass("sidebar_click");
        $(".aside_sidebar li .aside_sidebar_icon").removeClass("aside_sidebar_click");
        $(".aside_sidebar li:nth-child(" + value + ") .aside_sidebar_icon").addClass("aside_sidebar_click");
        scope.pageContent = pageName;
        if (value == 2) {
            scope.resourceMangementPageHtml = 'modules/content/index_modules/button/resourceManagement_drugListButton.html';
            scope.resourceManagementList = 'modules/content/index_modules/list/resourceManagement_drugList.html';
            loadingResourceManagement_drugList(15, 0);//取药品列表
        } else if (value == 3) {
            scope.businesscontentpage = 'modules/content/index_modules/button/businessManagementButton_businessList.html';
            scope.businessList = 'modules/content/index_modules/list/business_list.html';
        }
    };
    scope.resourceManagementPageClick = function (value, pageName, pageList) {//资源管理菜单导航栏功能
        $(".resourceContent_nav li").removeClass("content_nav_onclick");
        $(".resourceContent_nav li").addClass("bg_color");
        $(".resourceContent_nav li:nth-child(" + value + ")").addClass("content_nav_onclick").removeClass("bg_color");
        scope.resourceMangementPageHtml = pageName;
        scope.resourceManagementList = pageList;
        if (value == 1||value==2||value==3) {
            $(".content_nav_search").css("display", "block");
        } else {
            $(".content_nav_search").css("display", "none");
        }
    };
    scope.businessContent_nav_click = function (value, pageName) {//企业管理菜单导航栏功能
        $(".businessContent_nav li").removeClass("content_nav_onclick");
        $(".businessContent_nav li").addClass("bg_color");
        $(".businessContent_nav li:nth-child(" + value + ")").addClass("content_nav_onclick").removeClass("bg_color");
        scope.businesscontentpage = pageName;
        if (value == 1) {
            $(".content_nav_search").css("display", "block");
            scope.businessList = 'modules/content/index_modules/list/business_list.html';
        } else {
            $(".content_nav_search").css("display", "none");
            scope.businessList = 'modules/content/index_modules/list/businessList_registerCheck.html';
        }
    };
    scope.addDrugForm = "";//资源管理-药品列表-添加按钮
    scope.addDrug = function (isAdd) {
        if (isAdd == 1)
            scope.addDrugForm = 'modules/content/index_modules/add/resourceManagement_addDrugList.html';
        else scope.addDrugForm = "";
    }
}]);
window.onresize = function () {
    resize();
    $(".content_content_content").css({
        "height": window.innerHeight - 290 + "px"
    });
}
function resize() {
    $("aside").css({
        "height": window.innerHeight - 55 + "px"
    });
    $("body").css({
        "width": window.innerWidth
    });
    $(".index_content").css({
        "width": window.innerWidth - 280 + "px"
    });
    if (window.innerWidth < 1230) {
        $("body").css({
            "width": "1230px"
        });
    } else {
        $(".content_footer").css({
            "width": window.innerWidth - 280 + "px"
        });
    }
}
function statisticalContent_nav_click(value) {//年月周日统计首页按钮
    $(".statisticalContent_nav li").removeClass("content_nav_onclick");
    $(".statisticalContent_nav li").addClass("bg_color");
    $(".statisticalContent_nav li:nth-child(" + value + ")").addClass("content_nav_onclick").removeClass("bg_color");
}
