$( document ).ready(function() {

    $('.make_graph').click(function() {

        var $elem = $(this);
        var $attrib = $elem.attr("data-attrib");
        var $barCode = $elem.attr("data-barcode");
        var $targetElement = $('div[id="' + $attrib + '"]');
        var $url = $barCode + '/fromData/' + $attrib + '/date';
        var graphtype = '';
        var mode ='lines+markers';
        var xFormat = "";
        var yFormat = ":04,2f";
        $.ajax({
            url:$url,
            type: 'get',
            success: function(response) {
                console.log(response);
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div,graphtype, mode, 'date',$attrib,xFormat,yFormat);
            },
            error: function(response) {

            }
        });


    });


    $('.graph_create_form').on('submit', function(e) {
        e.preventDefault();

        var $form = $(this);
        var type = 'scatter';
        var mode = 'markers';
        var $targetElement = $("#mainGraph");
        var yLabel = $("#simpleY").val();
        var xLabel = $("#simpleX").val();
        var xFormat = ":04,2f";
        var yFormat = ":04,2f";
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                console.log(response);
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div,type, mode, xLabel, yLabel,xFormat,yFormat);
            },
            error: function(response) {

            }
        });


    });

    function makePlotlyGraph(responseData,div,graphType, modeOptions, xLabel, yLabel, xFormat, yFormat){

        if(graphType == null || graphType == ""){
            graphType = 'scatter';
        }

        if(modeOptions == null || modeOptions == "" ){
            modeOptions = 'markers';
        }

        var data = [
            {
                x: responseData.x,
                y: responseData.y,
                type: graphType,
                mode: modeOptions
            }
        ];

        var layout = {
            margin: {
                width: 500,
                height: 500,
                t: 10
            },
            xaxis: {
                title: xLabel,
                tickformat : xFormat
            },
            yaxis: {
                title: yLabel,
                tickformat : yFormat
            }

        };

        Plotly.newPlot(div,data,layout,{showLink: false});
    }
});