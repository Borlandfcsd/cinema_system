$(function() {

    $('#singin-form-link').click(function(e) {
        $("#singin-form").delay(100).fadeIn(100);
        $("#singup-form").fadeOut(100);
        $('#singup-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#singup-form-link').click(function(e) {
        $("#singup-form").delay(100).fadeIn(100);
        $("#singin-form").fadeOut(100);
        $('#singin-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

});
