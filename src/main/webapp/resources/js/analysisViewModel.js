define(["knockout","moment"], function (ko) {
    var app = app || {};


    var line = function(id,name) {
        var self = this;
        self.id = ko.observable(id);
        self.name = ko.observable(name);
        self.value = ko.observable(100);
    };

    app.analysisViewModel = function (cats) {
        var self = this;
        function fillCats(cats) {
            var res = [];
            $.each(cats, function (i, x) {
                var c = new line(x.id, x.name);
                res.push(c);
            });
            return res;
        }

        this.categories = ko.observableArray(fillCats(cats));
        self.selectedRange = ko.observable(moment().startOf('month').format('DD.MM.YYYY')+" - "+moment().endOf('month').format('DD.MM.YYYY'));

        function analysisBean(){
            var object ={
                range:self.selectedRange()
            }
            var list = [];
            $.each(self.categories(), function(i,val){
                var line = {
                    id:val.id(),
                    name:val.name(),
                    value:val.value()
                }
                list.push(line);
            });
            object.categories = list;
            return object;
        }

        self.sendRequest = function () {
            $.ajax({
                url: "/cabinet/analysis/",
                type: 'POST',
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                async: false,
                data: JSON.stringify(analysisBean()),
                success: function (result) {

                }, error: function (result) {
                    $.alert({
                        title: "Результат",
                        text: result.responseText
                    });
                }
            });
        }

    }
    return app;
})
;