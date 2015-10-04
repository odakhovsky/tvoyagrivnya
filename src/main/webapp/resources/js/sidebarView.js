require.config({
    paths:{
        "knockout":"/resources/js/libs/knockout.min",
        "sidebar":"sidebarViewModel",
    }
});

require(["sidebar","knockout"],function(app,ko){
        var sidebar = new app.sidebarViewModel();
        ko.applyBindings(sidebar);
});
