define(["knockout"], function (ko) {
    var app = app || {};

    app.operationViewModel = function (cats) {

        var plus = 'plus';
        var minus = 'minus';

        var self = this;
        this.categories = ko.observableArray(cats);
        self.category = ko.observable();
        this.viewCategories = ko.observableArray([]);

        self.selectedType = ko.observable();
        self.money = ko.observable(0).extend({ numeric: 2 });

        self.selectedCategory = ko.observable();
        self.selectedCategory.subscribe(function (operationType) {
            self.category(operationType.id);
        });



        self.selectedType.subscribe(function (operationType) {
            var items = [];
            if (operationType == plus) {
                $.each(self.categories(), function (i, val) {
                    if (val.parentName == null){
                        val.parentName='';
                    }
                    if (val.operation =='plus'){
                        items.push(val);
                    };
                });
                self.viewCategories(items);
            } else if (operationType == minus) {
                $.each(self.categories(), function (i, val) {
                    if (val.parentName == null){
                        val.parentName='';
                    }
                    if (val.operation =='minus'){
                        items.push(val);
                    };
                });
                self.viewCategories(items);
            }
        });



        ko.extenders.numeric = function(target, precision) {
            //create a writable computed observable to intercept writes to our observable
            var result = ko.pureComputed({
                read: target,  //always return the original observables value
                write: function(newValue) {
                    var current = target(),
                        roundingMultiplier = Math.pow(10, precision),
                        newValueAsNum = isNaN(newValue) ? 0 : parseFloat(+newValue),
                        valueToWrite = Math.round(newValueAsNum * roundingMultiplier) / roundingMultiplier;

                    //only write if it changed
                    if (valueToWrite !== current) {
                        target(valueToWrite);
                    } else {
                        //if the rounded value is the same, but a different value was written, force a notification for the current field
                        if (newValue !== current) {
                            target.notifySubscribers(valueToWrite);
                        }
                    }
                }
            }).extend({ notify: 'always' });

            //initialize with current value to make sure it is rounded appropriately
            result(target());

            //return the new computed observable
            return result;
        };

    }
    return app;
})
;