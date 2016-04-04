$( document ).ready(function() {

    $('.plant_tag_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantId = $form.find('input[name="plantID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                console.log(response);
                $("#plant_tags"+$plantId).html(response);
            },
            error: function(response) {
                console.log(response);
                $("#plant_tags"+$plantId).html(response);
            }
        });
    }); 

});