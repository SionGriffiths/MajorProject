$( document ).ready(function() {
    $(".nav a").on("click", function(){
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
    });

    $('a.back_button').click(function(){
        parent.history.back();
        return false;
    });

    $('.confirm-required').click(function(){
        var message = $(this).attr("data-confirm-message");
        return confirm(message);
    })

});