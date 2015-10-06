require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "cabinet": "mainCabinetViewModel",
        "raphael": "/resources/js/libs/raphael-min",
        "morris": "/resources/js/libs/morris",
    },
    shim: {
        "morris": ["raphael"]
    }
});

require(["cabinet", "knockout", "raphael", "morris"], function (app, ko) {


    var statistic = null;
    $.ajax({
        url: '/statistic/simple/',
        async: false,
        dataType: 'json',
        success: function (data) {
            statistic = data;
        }
    });

    Morris.Donut({
        element: 'income-chart',
        data: statistic.incoming
    });

    Morris.Donut({
        element: 'spending-chart',
        data: statistic.spending
    });

    var model =  app.cabinetViewModel(statistic);
    ko.applyBindings(model,$("#simple-statistic")[0]);
});
