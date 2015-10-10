angular.module("doctor_app", []).run(function () {
    resize();
}).controller('my', ['$scope', function (scope) {
    scope.pageContent = 'modules/content/index_resourceManagement.html';
    scope.sidebarClick = function (value, pageName) {
        $(".aside_sidebar li").removeClass("sidebar_click");
        $(".aside_sidebar li:nth-child(" + value + ")").addClass("sidebar_click");
        $(".aside_sidebar li .aside_sidebar_icon").removeClass("aside_sidebar_click");
        $(".aside_sidebar li:nth-child(" + value + ") .aside_sidebar_icon").addClass("aside_sidebar_click");
        scope.pageContent = pageName;
        if (value == 1) {
            scope.resourceMangementPageHtml = 'modules/content/index_modules/button/resourceManagement_drugListButton.html';
            scope.resourceManagementList = 'modules/content/index_modules/list/resourceManagement_drugList.html';
        }
    };
    scope.resourceMangementPageHtml = 'modules/content/index_modules/button/resourceManagement_drugListButton.html';
    scope.resourceManagementList = 'modules/content/index_modules/list/resourceManagement_drugList.html';
    scope.resourceManagementPageClick = function (value, pageButton,pageList) {
        $(".resourceContent_nav li").removeClass("content_nav_onclick");
        $(".resourceContent_nav li").addClass("bg_color");
        $(".resourceContent_nav li:nth-child(" + value + ")").addClass("content_nav_onclick").removeClass("bg_color");
        scope.resourceMangementPageHtml = pageButton;
        scope.resourceManagementList = pageList;
        if (value == 1 || value == 2 || value == 3) {
            $(".content_nav_search").css("display", "block");
        } else {
            $(".content_nav_search").css("display", "none");
        }
    };
}]);
window.onresize = function () {
    resize();
    $(".content_content_content").css({
        "height": window.innerHeight - 290+ "px"
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
function header_user_Click() {//header用户单击效果
    if ($(".header_userFunction").css("display") == "block") {
        $(".header_userFunction").css("display", "none");
    } else {
        $(".header_userFunction").css("display", "block");
    }
}
