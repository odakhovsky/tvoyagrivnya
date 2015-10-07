require.config({
    paths: {
        "knockout": "/resources/js/libs/knockout.min",
        "exchange": "exchangeViewModel",

    }
});

require(["exchange", "knockout"], function (app, ko) {
    var currencies = [];
    $.ajax({
        url: "/cabinet/accounts/exchange/currencies/",
        async: false,
        success: function (data) {
            currencies = data;
        }
    });
    ko.applyBindings(new app.exchangeViewModel(currencies));

});
