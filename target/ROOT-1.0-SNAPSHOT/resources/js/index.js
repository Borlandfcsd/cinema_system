$(".link").click(function(e) {
    var target = $(this).attr('rel');
    $("#"+target).show().siblings("div").hide();
});