require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "operation": "operationViewModel",

    }
});

require(["operation", "knockout"], function (app, ko) {
    var categories = [];
    $.ajax({
        url: "/cabinet/operations/categories/",
        async: false,
        success: function (data) {
            categories = data;
        }
    });
    ko.applyBindings(new app.operationViewModel(categories));

});
