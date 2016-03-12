$( document ).ready(function() {


    //$(".add_tag_btn").click( function(e) {
    //    e.preventDefault();
    //    var $btn = $(this);
    //    var $form = $btn.parent().parent();
    //    var $plantId = $form.find('input[name="plantDayID"]').val();
    //    var $tagContent = encodeURIComponent($form.find('input[name="tagContent"]').val());
    //    var $url = "/plants/addTag";
    //
    //    if($plantId != '' && $tagContent != ''){
    //        $url += '/' + $plantId + '/' + $tagContent;
    //        $("#plant_day_tags"+$plantId).load($url);
    //    }
    //});

    $(".add_attrib_btn").click( function(e) {
        e.preventDefault();
        var $btn = $(this);
        var $form = $btn.parent().parent();
        var $plantId = $form.find('input[name="plantDayID"]').val();
        var $aName = encodeURIComponent($form.find('input[name="attribName"]').val());
        var $aVal = encodeURIComponent($form.find('input[name="attribValue"]').val());
        var $url = "/plants/addTag";

        if($plantId != '' && $aName != '' && $aVal != ''){
            $url += '/' + $plantId + '/' + $aName +'/' +$aVal;
            $("#plant_day_attribs"+$plantId).load($url);
        }
    });





    //var $form = $('.tag_form');
    $('.tag_form').on('submit', function(e) {
        e.preventDefault();
        var $form = $(this);
        var $plantId = $form.find('input[name="plantDayID"]').val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                //if ($(response).find('.has-error').length) {
                //    $form.replaceWith(response);
                //} else {
                    $("#plant_day_tags"+$plantId).html(response);
                //}
            },
            error: function(response) {
                console.log(response);
                $("#plant_day_tags"+$plantId).html(response);
            }
        });
    });

});