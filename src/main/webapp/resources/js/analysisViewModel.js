define(["knockout", "moment"], function (ko) {
    var app = app || {};


    app.analysisViewModel = function () {
        var self = this;

        self.fromDate = ko.observable(null);
        self.toDate = ko.observable(null);
        self.selectedRange = ko.observable(moment().startOf('month').format('DD.MM.YYYY') + " - " + moment().endOf('month').format('DD.MM.YYYY'));


        function analysisBeanExt() {
            var object = {
                from: self.fromDate(),
                to: self.toDate()
            }
            return object;
        }

        function analysisBean() {
            var object = {
                range: self.selectedRange()
            }

            return object;
        }


        self.sendRequestExtended = function () {
            if (null == self.fromDate() || null == self.toDate() || new Date(self.fromDate()).getTime() == new Date(self.toDate()).getTime()) {
                $.alert({
                    "title": "Повідомлення",
                    "text": "Перевірте задані данні"
                });
            } else {

                $.ajax({
                    url: "/cabinet/analysis/extend/",
                    type: 'POST',
                    dataType: "json",
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    async: true,
                    data: JSON.stringify(analysisBeanExt()),
                    success: function (result) {
                        $("#analysis-result").empty();
                        $("#analysis-result").append(result.responseText);
                    }, error: function (result) {
                        console.log(result);
                        HoldOn.close();
                        $("#analysis-result").empty();
                        $("#analysis-result").append(result.responseText);
                    }
                });
            }
        }

        self.sendRequest = function () {
            console.log(JSON.stringify(analysisBean()));
            $.ajax({
                url: "/cabinet/analysis/",
                type: 'POST',
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                async: true,
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