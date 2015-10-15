define(["knockout","moment"], function (ko) {
    var app = app || {};


    app.analysisViewModel = function () {
        var self = this;


        self.selectedRange = ko.observable(moment().startOf('month').format('DD.MM.YYYY')+" - "+moment().endOf('month').format('DD.MM.YYYY'));


        function analysisBean(){
            var object ={
                range:self.selectedRange()
            }

            return object;
        }

        self.sendRequest = function () {
            console.log(JSON.stringify(analysisBean()));
            $.ajax({
                url: "/cabinet/analysis/",
                type: 'POST',
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                async: false,
                data: JSON.stringify(analysisBean()),
                success: function (result) {
                    $("#analysis-result").empty();
                    $("#analysis-result").append(result.responseText);
                }, error: function (result) {
                    console.log(result);
                    $("#analysis-result").empty();
                    $("#analysis-result").append(result.responseText);
                }
            });
        }

    }
    return app;
});