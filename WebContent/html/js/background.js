$(document).ready()
{
    initPage();
    Dropzone.autoDiscover = false;
    new Dropzone('#clickable', {
        url: "http://localhost:8080/sshDemo/UploadFile",
        previewsContainer: "#previews",
        clickable: "#clickable",
        addRemoveLinks: true,
        acceptedFiles: ".gif,.jpg,.png",
        success: function(file, data) {
            $('#publish').removeAttr("disabled");
            $('.dropzone').attr("name", data.saveFileName);
        }
    });
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    $('#save').click(save);
    $('#publish').click(function(){
        var id = $('.content').attr("id");
        var blog_brief = $('#blog_brief').val();
        var blog_tag = $('#blog_tag').val();
        var blog_img = $('.dropzone').attr("name");
        $.post("./Publish", {id:id,blog_brief:blog_brief, blog_tag:blog_tag, blog_img:blog_img}, function(result){
            alert(result.result);
            window.location.href = "http://localhost:8080/sshDemo/Administer";
        });
    });

    $('#savePublish').click(function(){
        var id = $('.content').attr("id");
        if(id == 0)
            save();

        $('#publish').attr("disabled","disabled");
    });

    $('#signOut').click(function(){
        $.post("./SignOut",function(result){
            if(result.result == "success"){
                location.href = "../";
            }
        });
    });
}
function initPage(){
    $.post("./BackgroundInfo", function (result) {
        if(result.result == "success") {
            $.each(result.tags, function (i, item) {
                $("<option></option>").text(item.tagName).attr("id", item.tagId).appendTo($('#blog_tag'));
                var li = $("<li></li>").appendTo($('.treeview-menu'));
                $("<a></a>").text(item.tagName).attr("data-id", item.tagId).attr("href","#").addClass("tagItem").appendTo(li);
            });
            $('.tagItem').click(function(){
                getAllBlogs($(this).attr("data-id"),1);
            });
        }
        else
            alert("页面初始化失败");

    });
}

$('#allBlog').click(function(){
    $('.active').removeClass("active");
    $('#allBlog').parent().parent(".treeview").addClass("active");
    $('.content-header h1').text("").append("AllBlogs<small>there are the blogs that had been published !</small>");
    $('.breadcrumb li :eq(2)').text("AllBlog");
    getAllBlogs(0,1);
});

$('#hideBlogManager').click(function(){
    $('.active').removeClass("active");
    $('#hideBlogManager').parent().addClass("active");
    getAllBlogs(0,0);
});

$('#tagManager').click(function(){
    $.post("http://localhost:8080/sshDemo/GetTag", function(result){
        $('.active').removeClass("active");
        $('#tagManager').parent().addClass("active");
        $('.content-header h1').text("").append("AllTags<small> Manager tags</small>");
        $('.breadcrumb li :eq(2)').text("AllTags");
        $('.content').remove();

        var contenntHtml = '<section class="content"><div class="table-responsive"><table class="table table-hover">' +
            '<thead> <tr> <th>id</th> <th>Name</th> <th>Count</th> </tr></thead><tbody>';
        $.each(result.tags, function(i, item){
            var blogHtml = '<tr><th scope="row">'+item.tagId+'</th>' +
                '<td id="'+item.tagId+'">'+item.tagName+'</td>' +
                '<td>'+item.tagCount+'</td>' +
                '<td><button class="btn btn-default editBtn" data-id="'+item.tagId+'">Edit</button>' +
                '<button class="btn btn-default deleteBtn" data-id="'+item.tagId+'">Delete</button></td></tr>';
            contenntHtml += blogHtml;
        });
        contenntHtml += '</tbody></table></div>';
        contenntHtml += '<div class="tagBtn-group"><div class="input-group"><input id="tagInput" type="text" class="form-control" placeholder="添加Tag..."> <span class="input-group-btn"> <button class="btn btn-default" type="button" id="addTag">确定</button> </span></div></div></section>';
        $('.content-header').append(contenntHtml);
        $("#addTag").click(addTag);
        $('.editBtn').click(editTag);
        $('.deleteBtn').click(deleteTag);
    });

});

$('#indexTitle').click(function(){
    $.post("http://localhost:8080/sshDemo/IndexTitle", function(result){
        $('.active').removeClass("active");
        $('#indexTitle').parent().addClass("active");
        $('.content-header h1').text("").append("IndexTitles");
        $('.breadcrumb li :eq(2)').text("AllTags");
        $('.content').remove();

        var contenntHtml = '<section class="content"><div class="table-responsive"><table class="table table-hover">' +
            '<thead> <tr> <th>id</th> <th>Title</th> <th>SmallTitle</th> </tr></thead><tbody>';

        $.each(result.headContents, function(i, item){
            var blogHtml = '<tr><th scope="row">'+item.infoId+'</th>' +
                '<td>'+item.title+'</td>' +
                '<td>'+item.smallTitle+'</td>' +
                '<td><button class="btn btn-default editBtn" data-id="'+item.infoId+'">Edit</button>' +
                '<button class="btn btn-default deleteBtn" data-id="'+item.infoId+'">Delete</button></td></tr>';
            contenntHtml += blogHtml;
        });

        contenntHtml += '</tbody></table></div>';
        contenntHtml += '<div class="tagBtn-group"><input id="titleInput" type="text" class="form-control" placeholder="添加Title..."> <input id="smallTitleInput" type="text" class="form-control" placeholder="SmallTitle..."><span class="input-group-btn"> <button class="btn btn-default" type="button" id="addTitleInfo">确定</button> </span></div></section>';
        $('.content-header').append(contenntHtml);
        $("#addTitleInfo").click(addTitleInfo);
        $('.deleteBtn').click(deleteTitleInfo);
    });
});

$('#commentManager').click(function(){
    $('.active').removeClass("active");
    $('#commentManager').parent().addClass("active");
    $('.content-header h1').text("").append("CommentManager");
    $('.breadcrumb li :eq(2)').text("AllTags");
    $('.content').remove();
    var contenntHtml = '<section class="content"><br><div class="tagBtn-group"><div class="input-group"><input id="commentAuthor" type="text" class="form-control" placeholder="评论用户名..."> <span class="input-group-btn"> <button class="btn btn-default" type="button" id="searchComment">查找</button> </span></div></div></section>';
    $('.content-header').append(contenntHtml);
    $("#searchComment").click(searchComment);
});

$('#newBlog').click(function(){
    window.location.href = "http://localhost:8080/sshDemo/Administer";
});


function save(){
    var blog_content =  UE.getEditor('editor').getContent();
    var blog_title = $('#title').val();
    var blog_brief = UE.getEditor('editor').getContentTxt().substring(0,250)+"...";

    $.post("http://localhost:8080/sshDemo/SaveBlog", {blog_content: blog_content, blog_title: blog_title, blog_brief:blog_brief}, function (result) {
        alert(result.result);
        $('.content').attr("id",result.id);
    });
}

function editBlog(){
    var blogId = $(this).attr("data-id");
    $.post("./EditBlog",{blogId:blogId}, function(result){
        if(result.result == "success"){
            var blog = result.blog;
            var tagHtml = '';
            $.each(result.tags, function (i, item) {
                tagHtml+= '<option id="'+item.tagId+'">'+item.tagName+'</option>';
            });
            var contentHtml = '';
            contentHtml = '<div class="titleInput"> <label>标题：</label> <input  id="title" > </div> <div class="textEditor"> <div id="editor'+blogId+'" type="text/plain" style="width:100%;height:500px;"></div></div>'+
                '<div class="row"><button class="button button--aylen " id="update">更新</button><button class="button button--aylen " id="rePublish"  data-toggle="modal" data-target="#myModal">重新发布</button></div>' +
                '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                '<h4 class="modal-title" style="color:  rgba(130, 141, 163, 0.92)" id="myModalLabel">博客发布</h4></div>' +
                '<div class="modal-body"><form class="form-horizontal"  method="get" id="loginForm"><label id="" class="control-label">简介:</label><textarea id="blog_brief" class="form-control " row="8" placeholder="不填，默认选取第一段为简介。"></textarea>' +
                '<div class="dropzone"><label  class="control-label">封面:</label><div id="previews" class="dz-previews"></div>' +
                '<div class="row"><button id="clickable'+blogId+'"  type="button" style="margin-left: 15px;padding: 10px 12px;min-width: 120px;color:rgba(88, 96, 116, 0.92);" class="button button--aylen">上传封面</button></div></div><div class="form-group">' +
                '<label  class="control-label">Tag:</label><select class="form-control" id="blog_tag">'+tagHtml+'</select></div></form></div>' +
                '<div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-primary" id="publish">发布</button></div></div></div></div>'
            $('.content').html(contentHtml).attr("id",blogId);

            new Dropzone('#clickable'+blogId, { // Make the whole body a dropzone
                url: "http://localhost:8080/sshDemo/UploadFile", // Set the url
                previewsContainer: "#previews", // Define the container to display the previews
                clickable: '#clickable'+blogId, // Define the element that should be used as click trigger to select files.
                addRemoveLinks: true,
                acceptedFiles: ".gif,.jpg,.png",
                success: function(file, data) {
                    $('#publish').removeAttr("disabled");
                    $('.dropzone').attr("name", data.saveFileName);
                }
            });

            var ue = UE.getEditor('editor'+blogId);
            ue.ready(function() {
                //设置编辑器的内容
                ue.setContent(blog.content);
            });
            $('#title').val(blog.title);

            $('#update').click(function(){
                var content = ue.getContent();
                var title = $('#title').val();
                $.post("./UpdateBlog", {blogId:blogId,content:content, title:title}, function(result){
                    if(result.result == "success")
                        location.reload();
                });
            });

            $('#publish').click(function(){
                var content = ue.getContent();
                var title = $('#title').val();
                var id = $('.content').attr("id");
                var blog_brief = $('#blog_brief').val();
                var blog_tag = $('#blog_tag').val();
                var blog_img = $('.dropzone').attr("name");
                $.post("./RePublish", {blogId:blogId,content:content, title:title,blog_brief:blog_brief, blog_tag:blog_tag, blog_img:blog_img}, function(result){
                    alert(result.result);
                    location.reload();
                });
            });

        }
    });
}

function getAllBlogs(tagId, status){
    $.post("./AllBlog",{tagId:tagId, status:status}, function(result){
        $('.content').remove();
        var contenntHtml = '<section class="content"><div class="table-responsive"><table class="table table-hover">' +
            '<thead> <tr> <th>id</th> <th>Title</th> <th>Author</th> <th>Date</th><th>Tag</th></tr></thead><tbody>';
        $.each(result.blogs, function(i, item){
            var blogHtml = '<tr class="blogTr"><th scope="row">'+item.id+'</th>' +
                '<td>'+item.blogTitle+'</td>' +
                '<td>'+item.blogAuthor+'</td>' +
                '<td>'+item.blogDate+'</td>' +
                '<td>'+item.tag+'</td>' +
                '<td><button class="btn btn-default editBtn" data-id="'+item.id+'">Edit</button>' +
                '<button class="btn btn-default deleteBtn" data-id="'+item.id+'">Delete</button></td></tr>';
            contenntHtml += blogHtml;
        });
        contenntHtml += '</tbody></table></div></section>';
        $('.content-header').append(contenntHtml);
        $('.editBtn').click(editBlog);
        $('.deleteBtn').click(deleteBlog);
    });
}

function deleteBlog(){
    var btn  = $(this);
    var blogId = btn.attr("data-id");
    $.post("./DeleteBlog",{blogId:blogId}, function(result){
        if(result.result == "success"){
            btn.parent().parent(".blogTr").remove();
        }
    });
}

function addTag(){
    var tagName = $('#tagInput').val();
    $.post("http://localhost:8080/sshDemo/AddTag",{"tagName":tagName}, function(result){
        alert(result.result);
        if(result.result == "success"){
            var blogHtml = '<tr><th scope="row">'+result.tagId+'</th>' +
                '<td>'+result.tagName+'</td>' +
                '<td>'+result.tagCount+'</td>' +
                '<td><button class="btn btn-default" data-id="'+result.tagId+'">Edit</button>' +
                '<button class="btn btn-default" data-id="'+result.tagId+'">Delete</button></td></tr>';
            $('tbody').append(blogHtml);
        }
    });
}

function editTag(){
    var btn = $(this);
    var tagId = btn.attr("data-id");
    btn.parent().after('<td><div class="updateBtn-group"><div class="input-group"><input type="text" class="form-control" placeholder="Update..."><span class="input-group-btn"> <button class="btn btn-default updateTag" type="button" >确定</button><button class="btn btn-default concel" type="button" >取消</button></span></div></td></div> ');
    btn.parent().hide();
    $('.updateTag').click(function(){
        var tagName = $(this).parent().prev().val();
        $.post("./UpdateTag", {tagId:tagId, tagName:tagName}, function(result){
            if(result.result == "success"){
                $('#'+tagId+'').text(tagName);
            }
        });
    });

    $('.concel').click(function(){
        btn.parent().next().remove();
        btn.parent().show();
    });

}

function deleteTag(){
    var btn = $(this);
    var tagId = btn.attr("data-id");
    $.post("http://localhost:8080/sshDemo/DeleteTag", {"tagId":tagId}, function(result){
        alert(result.result);
        if(result.result == "success"){
            btn.parent().parent().remove();
        }
    })

}

function deleteTitleInfo(){
    var btn = $(this);
    var infoId = btn.attr("data-id");
    $.post("http://localhost:8080/sshDemo/DeleteTitleInfo", {"infoId":infoId}, function(result){
        alert(result.result);
        if(result.result == "success"){
            btn.parent().parent().remove();
        }
    })

}

function addTitleInfo(){
    var title = $('#titleInput').val();
    var smallTitle = $('#smallTitleInput').val();
    $.post("http://localhost:8080/sshDemo/AddTitleInfo",{"title":title, "smallTitle":smallTitle}, function(result){
        alert(result.result);
        if(result.result == "success"){
            var blogHtml = '<tr><th scope="row">'+result.tagId+'</th>' +
                '<td>'+result.title+'</td>' +
                '<td>'+result.smallTitle+'</td>' +
                '<td><button class="btn btn-default" data-id="'+result.infoId+'">Edit</button>' +
                '<button class="btn btn-default" data-id="'+result.infoId+'">Delete</button></td></tr>';
            $('tbody').append(blogHtml);
        }
    });
}


