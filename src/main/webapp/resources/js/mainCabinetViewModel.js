﻿define(["knockout"], function (ko) {
    var app = app || {};

    app.cabinetViewModel = function (stats) {

        statistic = ko.observable(stats);
        incomings = ko.computed(function() {
            var sum = 0.0;
            $.each(statistic().incoming, function(i,val){
                sum += val.value;
            })
            return parseFloat(sum).toFixed(2) + ' ' +stats.currency;
        }, this);

        spending = ko.computed(function() {
            var sum = 0.0;
            $.each(statistic().spending, function(i,val){
                sum += val.value;
            })
            return parseFloat(sum).toFixed(2) + ' ' +stats.currency;
        }, this);



    }
    return app;
})
;