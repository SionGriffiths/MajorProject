$( document ).ready(function() {

    $('.make_graph').click(function() {

        var $elem = $(this);
        var $attrib = $elem.attr("data-attrib");
        var $barCode = $elem.attr("data-barcode");
        var $targetElement = $('div[id="' + $attrib + '"]');
        var $url = $barCode + '/fromData/' + $attrib + '/date';

        $.ajax({
            url:$url,
            type: 'get',
            success: function(response) {
                console.log(response);
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div);
            },
            error: function(response) {

            }
        });


    });



    function makePlotlyGraph(responseData,div){

        var data = [
            {
                x: responseData.x,
                y: responseData.y }
        ];

        var layout = {
            margin: {
                width: 500,
                height: 500,
                t: 10
            }
        };

        Plotly.newPlot(div,data,layout);
    }




});