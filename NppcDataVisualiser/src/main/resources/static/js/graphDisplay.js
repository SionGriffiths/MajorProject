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
        ajaxCreateMainGraph($(this));

    });


    function ajaxCreateMainGraph(element){
        var $form = element;
        var type = $('input[name=plotType]:checked').val().toLowerCase();
        var mode = 'markers';
        var $targetElement = $("#mainGraph");
        var yLabel = $("#graph_axis_Y").val();
        var xLabel = $("#graph_axis_X").val();
        var xFormat = ":04,2f";
        var yFormat = ":04,2f";
        $.ajax({
            url: $form.attr('action'),
            type: 'post',
            data: $form.serialize(),
            success: function(response) {
                $targetElement.addClass('graphDiv');
                var div = $targetElement[0];
                makePlotlyGraph(response,div,type, mode, xLabel, yLabel,xFormat,yFormat);
            },
            error: function(response) {

            }
        });
    }

    $('#axis-swap').click( function() {
        var tempY = $("#graph_axis_Y").val();
        $("#graph_axis_Y").val($("#graph_axis_X").val());
        $("#graph_axis_X").val(tempY);
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
            },
            hovermode:'closest'

        };

        Plotly.newPlot(div,data,layout,{showLink: false});

        var closestX ='';
        var closestY ='';

        div.on('plotly_click', function(data){
            var pts = '';
            for(var i=0; i < data.points.length; i++){
                pts = 'x = '+data.points[i].x +'\ny = '+
                    data.points[i].y + '\n\n';
                closestX = data.points[i].x;
                closestY = data.points[i].y;
            }
            ajaxPlantQuery(xLabel, closestX, yLabel, closestY);
        });
    }

    function ajaxPlantQuery(xLabel, closestX, yLabel, closestY){
        // xLabel = encodeURIComponent(xLabel);
        // closestX = encodeURIComponent(closestX);
        // yLabel = encodeURIComponent(yLabel);
        // closestY = encodeURIComponent(closestY);
        var path = "graphs/graphClickQuery?keyX="+xLabel+"&keyY="+yLabel+"&valX="+closestX+"&valY="+closestY;
        console.log(path);
        $.ajax({
            url: path ,
            type: 'get',
            // data: $form.serialize(),
            success: function(response) {
                $("#graph_result_div").html(response);
            },
            error: function(response) {

            }
        });
    }
});