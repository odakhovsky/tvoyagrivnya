require.config({
    paths:{
        "knockout":"/resources/js/libs/knockout.min",
        "accounts":"accountsViewModel",
    }
});

require(["accounts","knockout"],function(app,ko){
        var accounts = new app.accountsViewModel();
        ko.applyBindings(accounts);
});
