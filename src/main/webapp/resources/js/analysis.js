require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "analysisView": "analysisViewModel",
        "moment":"/resources/js/libs/moment"
    }
});

require(["analysisView", "knockout","moment"], function (app, ko) {
    var categories = [];
    $.ajax({
        url: "/cabinet/analysis/categories/",
        async: false,
        success: function (data) {
            categories = data;
        }
    });
    ko.applyBindings(new app.analysisViewModel(categories));

});
