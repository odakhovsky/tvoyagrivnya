define(["knockout"], function (ko) {
    var app = app || {};

    app.exchangeViewModel = function (currs) {


        var self = this;
        this.currencies = ko.observableArray(currs);
        self.category = ko.observable();

        self.money = ko.observable(0).extend({ numeric: 2 });
        self.moneyTo = ko.observable(0).extend({ numeric: 2 });

        self.selectedCurrFrom = ko.observable();
        self.selectedCurrTo = ko.observable();

        self.selectedAccFrom = ko.observable();
        self.selectedAccTo = ko.observable();

        self.selCurrTo = ko.observable();
        self.selCurrFrom = ko.observable();

        self.selectedCurrTo.subscribe(function (data) {
            self.selCurrTo(data.id);
        });
        self.selectedCurrFrom.subscribe(function (data) {
            self.selCurrFrom(data.id);
        });

        self.changeAccountFrom = function(){
            self.validateform();
        }

        self.changeAccountTo = function(){
            self.validateform();
        }

        self.changeCurrFrom  = function(){
            recalcMoney();
            self.validateform();
        };

        self.changeCurrTo  = function(){
            recalcMoney();
            self.validateform();
        };

        self.money.subscribe(function(value){
            recalcMoney();
            self.validateform();
        })

        self.validvalues = ko.observable(false);

        self.validateform = function(){
            var from = self.selectedAccFrom();
            var to = self.selectedAccTo();
            var curFrom = self.selectedCurrFrom().id;
            var curTo = self.selectedCurrTo().id;
            var condition = (from == to && curFrom == curTo);
            self.validvalues((condition)? false: true);
        }

        function recalcMoney(){
            var money = self.money();
            var crossFrom = self.selectedCurrFrom().crossRate;
            var crossTo = self.selectedCurrTo().crossRate;
            var formula = parseFloat(money / crossTo * crossFrom).toFixed(4);
            self.moneyTo(formula);
        }

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