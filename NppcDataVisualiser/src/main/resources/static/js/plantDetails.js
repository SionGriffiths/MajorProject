$( document ).ready(function() {



    $(".dayTag").on('submit', function(e) {
        var $form = $(this);
        e.preventDefault();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {

              var targetElem =  $form.closest(".col-xs-6").children(".plant_day_tags");
               targetElem.html(parseReponseToHtml(response));
            }
        });
    });


    function parseReponseToHtml(jsonResponse){
        var htmlString = "";
        $(jsonResponse).each(function(){
            console.log(this.tagContent);
          htmlString += "<span class='tag_text'>"+this.tagContent+"</span> ";
        });
        return htmlString;
    }

});