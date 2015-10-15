require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "analysisView": "analysisViewModel",
        "moment":"/resources/js/libs/moment"
    }
});

require(["analysisView", "knockout","moment"], function (app, ko) {

    ko.applyBindings(new app.analysisViewModel());

});
