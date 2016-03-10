$( document ).ready(function() {



    $(".dayTag").on('submit', function(e) {
        var $form = $(this);
        e.preventDefault();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                // if the response contains any errors, replace the form
                console.log(response);
                console.log($form);
                console.log($form.closest(".col-xs-6").children(".tagTextDiv"));
                console.log($form.parent().children());
                console.log($form.parent());
                $form.parent().siblings(".plant_day_tags").html("DICK DICK DICKHOOOOLE!");
                if ($(response).find('.has-error').length) {
                    $form.replaceWith(response);
                } else {
                    // in this case we can actually replace the form
                    // with the response as well, unless we want to
                    // show the success message a different way
                }
            }
        });
    });
});