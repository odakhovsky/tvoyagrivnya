define(["knockout","moment"], function (ko) {
    var app = app || {};


    app.feedbackViewModel = function () {
        var self = this;


        self.selectedRange = ko.observable(moment().startOf('month').format('DD.MM.YYYY')+" - "+moment().startOf('month').format('DD.MM.YYYY'));
        self.email = ko.observable('');
        self.feeds = ko.observableArray([]);

        function feedbackBean(){
            var object ={
                email:self.email(),
                date:self.selectedRange()
            }

            return object;
        }

        self.sendRequest = function () {
            console.log(JSON.stringify(feedbackBean()));
            $.ajax({
                url: "/admin/feedback/?email="+ self.email()+"&date="+self.selectedRange(),
                type: 'POST',
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                async: false,
                success: function (result) {
                    self.feeds(result);
                }, error: function (result) {
                    self.feeds(result);
                }
            });
        }

    }
    return app;
});