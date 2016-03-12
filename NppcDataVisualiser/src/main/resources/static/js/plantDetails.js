$( document ).ready(function() {

    $('.plant_day_tag_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantId = $form.find('input[name="plantDayID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                $("#plant_day_tags"+$plantId).html(response);
            },
            error: function(response) {
                console.log(response);
                $("#plant_day_tags"+$plantId).html(response);
            }
        });
    });


    $('.plant_day_attrib_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantId = $form.find('input[name="plantDayID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                $("#plant_day_attribs"+$plantId).html(response);
            },
            error: function(response) {
                console.log(response);
                $("#plant_day_attribs"+$plantId).html(response);
            }
        });
    });

});