$( document ).ready(function() {

    $('.make_graph').click(function() {

        var $elem = $(this);
        var $attrib = $elem.attr("data-attrib");
        var $barCode = $elem.attr("data-barcode");
        var $targetElement = $('div[id="' + $attrib + '"]');
        var $url = $barCode + '/fromData/' + $attrib + '/date';
        var graphtype = '';
        $.ajax({
            url:$url,
            type: 'get',
            success: function(response) {
                console.log(response);
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div,graphtype,'date',$attrib);
            },
            error: function(response) {

            }
        });


    });


    $('.graph_create_form').on('submit', function(e) {
        e.preventDefault();

        var $form = $(this);
        var type = 'scatter';
        var $targetElement = $("#mainGraph");
        var yLabel = $("#simpleY").val();
        var xLabel = $("#simpleX").val();
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                console.log(response);
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div,type, xLabel, yLabel);
            },
            error: function(response) {

            }
        });


    });

    function makePlotlyGraph(responseData,div,graphType, xLabel, yLabel){

        if(graphType == null || graphType == ""){
            graphType = 'scatter';
        }

        var data = [
            {
                x: responseData.x,
                y: responseData.y,
                type: graphType,
                mode: 'markers'
            }
        ];

        var layout = {
            margin: {
                width: 500,
                height: 500,
                t: 10
            },
            xaxis: {
                title: xLabel
            },
            yaxis: {
                title: yLabel
            }
        };

        Plotly.newPlot(div,data,layout,{showLink: false});
    }
});