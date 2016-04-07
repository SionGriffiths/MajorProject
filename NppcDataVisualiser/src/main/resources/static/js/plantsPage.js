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


    $('.plant_attrib_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantId = $form.find('input[name="plantID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            async: true,
            success: function(response) {
                $("#plant_attribs"+$plantId).html(response);
                $.getScript("/js/plantsPage.js");
            },
            error: function(response) {
                console.log(response);
                $("#plant_attribs"+$plantId).html(response);
            }
        });
    });

    $('.attrib_edit_button').click(function(){
        var $item = $(this).closest("tr");
        var $keyText = $item.find(".attrib_key").text();
        var $valText = $item.find(".attrib_val").text();
        console.log($valText);
        var $keyInput = $item.closest('.plant_input_column').find(".attrib_key_edit");
        var $valInput = $item.closest('.plant_input_column').find(".attrib_val_edit");
        $keyInput.val($keyText);
        $valInput.val($valText);
        $valInput.focus();
    });

});