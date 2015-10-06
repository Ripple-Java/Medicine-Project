var currentPage = $('#currentPage');
var nextPage = $('#nextPage');
var previousPage = $('#previousPage');
var currentTag = 0 ;
var currentUrl = "./Index";
//SMOOTH PAGE SCROLL
$(function() {
    $('a[href*=#]:not([href=#])').click(function() {
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
                return false;
            }
        }
    });
});

/*Preloader*/
//<![CDATA[
$(window).load(function() {
    $('#status').fadeOut();
    $('#preloader').delay(350).fadeOut('slow');
    $('body').delay(350).css({'overflow':'visible'});
    new WOW().init();
    loadPage();
});

function loadPage(){
    var maxSize = 6 ;
    $.data(currentPage, "Page", "1");
    $.data(nextPage, "Page", "2");
    $.data(previousPage, "Page", "1");
    $.getJSON("./Index",{currentPage: $.data(currentPage, "Page"), maxsize:maxSize},function(data) {
        var topContentHtml = '<ul>';
        $.each(data.headContents, function(i, item) {
            var liHtml = '<li> <h1 class="text-center">'+item.title+'</h1>' +
                '<p class="text-center">'+item.smallTitle+'</p></li>';
            topContentHtml += liHtml;
        });
        topContentHtml += '</ul>';
        $('.top-content').html(topContentHtml);
        var tagMenuHtml = '';
        $.each(data.blogTags, function(i, item) {
            tagMenuHtml += '<li class="tagClass"  data-id="'+item.tag_id+'"><a href="#" data-id="'+item.tag_id+'">'+item.tag_name+'</a> </li>';
        });
        $('.dropdown-menu').html(tagMenuHtml);
        $('.tagClass').click(function(){
            $.data(currentPage, "Page", "1");
            $.data(nextPage, "Page", "2");
            $.data(previousPage, "Page", "1");
            var tagId = $(this).attr("data-id");
            currentTag = tagId;
            currentUrl = "./GetblogByTag";
            getContents(6,tagId,"./GetblogByTag");
        });
        var blogHtml = '';

        $.each(data.blogContents, function(i, item) {
            var unitHtml = '<div class="blogUnit wow zoomIn animated" data-wow-duration="0.5s" "><div class="row">' +
                '<div class="blog-tag"></div><div class="tag-text">'+item.tag+'</div> <div class="blog-box"><div class="col-lg-6"> ' +
                '<h1 class="blog-head"> <a class="blog_title" href="./Blogs/'+item.blogName+'">'+item.blogTitle+'</a><br> ' +
                '<small class="blog_date"><span><i class="glyphicon glyphicon-time"></i> </span>'+item.blogDate+'</small>' +
                '<small class="blog_author">&nbsp;@'+item.blogAuthor+'</small>' +
                '</h1> <p class="blog_brief">'+item.blogBrief+'</p> ' +
                '<a class="read-more" href="./Blogs/'+item.blogName+'">READ MORE</a> </div>' +
                '<div class="col-lg-6"> <img class="blog-img" src="uploadImg/'+item.blogImg+'"></div> </div> </div> <div class="divide-line"></div> </div>';

            blogHtml += unitHtml;

        });

        $('#HEAD').html(blogHtml);
        initHead();
        $.data(currentPage, "Page", data.currentPage);
        $.data(nextPage, "Page", data.nextPage);
        $.data(previousPage, "Page", data.previousPage);
    });    
}


function getContents (size, tagId, url){
    $.getJSON(url,{currentPage: $.data(currentPage, "Page"), maxsize:size, tagId:tagId},function(data) {
        var blogHtml = '';
        $.each(data.blogContents, function(i, item) {
            var unitHtml = '<div class="blogUnit wow zoomIn animated" data-wow-duration="0.5s" "><div class="row">' +
                '<div class="blog-tag"></div><div class="tag-text">'+item.tag+'</div> <div class="blog-box"><div class="col-lg-6"> ' +
                '<h1 class="blog-head"> <a class="blog_title" href="./Blogs/'+item.blogName+'">'+item.blogTitle+'</a><br> ' +
                '<small class="blog_date"><span><i class="glyphicon glyphicon-time"></i> </span>'+item.blogDate+'</small>' +
                '<small class="blog_author">&nbsp;@'+item.blogAuthor+'</small>' +
                '</h1> <p class="blog_brief">'+item.blogBrief+'</p> ' +
                '<a class="read-more" href="./Blogs/'+item.blogName+'">READ MORE</a> </div>' +
                '<div class="col-lg-6"> <img class="blog-img" src="uploadImg/'+item.blogImg+'"></div> </div> </div> <div class="divide-line"></div> </div>';

            blogHtml += unitHtml;
        });

        $('#HEAD').html(blogHtml);
        $.data(currentPage, "Page", data.currentPage);
        $.data(nextPage, "Page", data.nextPage);
        $.data(previousPage, "Page", data.previousPage);
    });
}

function initHead(){
    $('.top-content').unslider({
        speed: 1000,
        delay: 4500,
        keys: true,
        dots: true,
        fluid: true
    });

}

nextPage.click(function(){
    if($.data(currentPage, "Page") == $.data(nextPage,"Page"))
	return;
 
    $.data(currentPage, "Page", $.data(nextPage,"Page"));
    getContents(6,currentTag,currentUrl);
});

previousPage.click(function(){
	 if($.data(currentPage, "Page") == $.data(previousPage,"Page"))
			return;
	
    $.data(currentPage, "Page", $.data(previousPage,"Page"));
    getContents(6,currentTag,currentUrl);
});

