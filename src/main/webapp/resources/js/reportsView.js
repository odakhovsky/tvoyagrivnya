require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "reportView": "reportViewModel",
        "raphael": "/resources/js/libs/raphael-min",
        "morris": "/resources/js/libs/morris",
        "paging":"/resources/js/libs/paging"
    },
    shim: {
        "morris": ["raphael"]
    }
});

require(["reportView", "knockout", "raphael", "morris","paging"], function (app, ko) {
    var categories = [];
    $.ajax({
        url: "/cabinet/operations/categories/",
        async: false,
        success: function (data) {
            categories = data;
        }
    });
    ko.applyBindings(new app.reportViewModel(categories));

});
