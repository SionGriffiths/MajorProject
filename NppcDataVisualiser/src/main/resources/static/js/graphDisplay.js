$( document ).ready(function() {

    $('.make_graph').click(function() {


        $.ajax({
            url:'O7-01111/fromData/Growth Stage/date',
            type: 'get',
            success: function(response) {
                console.log(response);
                $("div[id='Growth Stage']").addClass('graphDiv');
                var div = $("div[id='Growth Stage']")[0];
                //div.addClass('graphDiv');
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