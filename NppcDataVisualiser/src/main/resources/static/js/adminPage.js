$( document ).ready(function() {

    $('.admin_action_button').click(function(e) {
        console.log('fire');
        e.preventDefault();
        var $link = $(this);

        $.ajax({
            url: $link.attr('href'),
            type: 'get',
            success: function(response) {
                console.log(response);
                location.reload();
            },
            error: function(response) {
                console.log(response);
            }
        });
        
        location.reload();
    });

});