require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "feedbackView": "feedbackViewModel",
        "moment":"/resources/js/libs/moment"
    }
});

require(["feedbackView", "knockout","moment"], function (app, ko) {

    ko.applyBindings(new app.feedbackViewModel());

});
