$( document ).ready(function() {


    $(".add_tag_btn").click( function(e) {
        e.preventDefault();
        var $btn = $(this);
        var $form = $btn.parent().parent();
        var $plantId = $form.find('input[name="plantDayID"]').val();
        var $tagContent = $form.find('input[name="tagContent"]').val();
        var $url = "/plants/addTag";

        if($plantId != '' && $tagContent != ''){
            $url += '/' + $plantId + '/' + $tagContent;
        }


        $("#plant_day_tags"+$plantId).load($url);

    });


});