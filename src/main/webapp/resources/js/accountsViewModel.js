define(["knockout"], function (ko) {
    var app = app || {};

    app.accountsViewModel = function () {

        var self = this;
        self.accounts = ko.observableArray([]);
        self.currency = ko.observable();
        self.loadAccounts = function () {
            $.get("/cabinet/accounts/sidebar/list", function (data) {
                self.accounts(data);
            });
        }
        self.loadAccounts();
        self.totalCount = ko.computed(function () {
            var total = 0;
            $.each(this.accounts(), function (index, value) {
                total += value.total;
                self.currency(value.currency);
            });
            return total;
        }, this);


        self.url = function (id) {
            return '/cabinet/accounts/accmanage/' + id + '/info/';
        };

    }
    return app;
})
;