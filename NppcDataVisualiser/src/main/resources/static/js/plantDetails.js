$( document ).ready(function() {

    $('.plant_day_tag_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantDayId = $form.find('input[name="plantDayID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                $("#plant_day_tags"+$plantDayId).html(response);
            },
            error: function(response) {
                $("#plant_day_tags"+$plantDayId).html(response);
            }
        });
    });


    $('.plant_day_attrib_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantDayId = $form.find('input[name="plantDayID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                $("#plant_day_attribs"+$plantDayId).html(response);
                $.getScript("/js/plantDetails.js");
            },
            error: function(response) {
                console.log(response);
                $("#plant_day_attribs"+$plantDayId).html(response);
            }
        });
    });

    $('.attrib_edit_button').click(function(){
        var $item = $(this).closest("tr");
        var $keyText = $item.find(".attrib_key").text();
        var $valText = $item.find(".attrib_val").text();
        var $keyInput = $item.closest('.plant_day_input_column').find(".attrib_key_edit");
        var $valInput = $item.closest('.plant_day_input_column').find(".attrib_val_edit");
        $keyInput.val($keyText);
        $valInput.val($valText);
        $valInput.focus();
    });

});