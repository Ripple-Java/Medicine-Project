/**
 * Created by Liuyi on 2015/8/7.
 */

$(document).ready(function(){
    $('.replyBtn').click(replayBtnClick);
    var blogId = $('.blog_page-box').attr("id");
    $.post("./GetNextBlog",{blogId:blogId}, function(item){
        $('#blogRead').text(item.viewCount);
       if(item.result == "success"){
           $('#nextPage').attr("href","./"+item.blogName);

       }
    });
    $.post("./GetComments",{blogId:blogId},function(item){
        if(item.comments != null){
            var commentsHtml = "";
            $.each(item.comments, function(i, comment){
                var commentHtml = '';
                var subCommentHtml = '';
                if(comment.subComments.length > 0)
                {

                    $.each(comment.subComments, function(i, subComment){
                        subCommentHtml += '<div class="subComment"><img src="../images/portrait.jpg" class="portrait"> <div class="comment-text">' +
                            ' <a href="#">'+subComment.author+':</a> ' +
                            '<small>'+subComment.date+'</small> ' +
                            '<p>'+subComment.content+'</p></div></div>';
                    });
                }

                commentHtml += '<div class="comment"> <img src="../images/portrait.jpg" class="portrait"> <div class="comment-text">' +
                    ' <a href="#" data-id="'+comment.id+'">'+comment.author+':</a>' +
                    '<small>'+comment.date+'<a href="#nameLabel" class="replyUser">&nbsp;回复</a></small> ' +
                    '<p>'+comment.content+'</p></div></div>';

                commentsHtml += (commentHtml+subCommentHtml);
            });
            $('.comments').append(commentsHtml);
            $('.replyUser').click(replyOther);
        }
    });

});
function replayBtnClick(){
    var formData = {};
    btn  = $(this);
    formData["blogId"] = $('.blog_page-box').attr("id");
    var authorInput = $('.authorInput');
    formData["author"] = authorInput.val();
    authorInput.val("");
    var emailInut = $('.emailInput');
    formData["authorEmail"] = emailInut.val();
    emailInut.val("");
    var contentInput = $('.mytextarea');
    formData["content"] = contentInput.val();
    contentInput.val("");
    formData["parentId"] = $('#nameLabel').attr("data-id");

    $.post("./AddComment", formData, function(result){
        if(result.result == "success"){
            location.reload();
        }
        return false;
    });
}

function replyOther(){
    var User =  $(this).parent().prev();
    var toUser = User.text();
    var parentId = User.attr("data-id");
    toUser = toUser.substring(0,toUser.length-1);
    $('#nameLabel').append('<a href="#" style="color: #70b3f0;float: left">@'+toUser+'&nbsp;</a>').attr("data-id",parentId);
    $('.authorInput').focus();
}




