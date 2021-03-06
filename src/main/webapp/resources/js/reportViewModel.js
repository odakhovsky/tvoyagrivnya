﻿define(["knockout", "raphael", "morris", "paging"], function (ko) {
  var app = app || {};

  app.reportViewModel = function (cats) {

    var plus = 'plus';
    var minus = 'minus';

    var self = this;
    self.visibleblock = ko.observable(false);

    this.categories = ko.observableArray(cats);
    self.category = ko.observable();
    this.viewCategories = ko.observableArray([]);
    this.operations = ko.observableArray([]);

    this.reporting = ko.observable();
    this.currency = ko.observable();

    self.selectedType = ko.observable();
    self.selectedRange = ko.observable(moment().format('DD.MM.YYYY - DD.MM.YYYY'));

    self.selectedCategory = ko.observable();
    self.selectedCategory.subscribe(function (operationType) {
      self.category(operationType.id);
    });

    $('input[type=radio][name=reportView]').change(function () {
      if (this.value == 'rect') {
        showRect();
      }
      else if (this.value == 'circle') {
        showCircle();
      }
    });

    function filterBean() {
      var object = {
        type: self.selectedType(),
        period: self.selectedRange(),
        category: self.selectedCategory().id,
        user: $("#user-id").val()
      }
      return object;
    }

    function showRect() {
      console.log("show rect");
      $("#income-report").html('');
      $("#spending-report").html('');

      Morris.Bar({
        element: 'income-report',
        data: self.reporting().incomings,
        xkey: 'label',
        ykeys: ['value'],
        labels: ['Сума'],
        barRatio: 0.4,
        xLabelAngle: 90,
        hideHover: 'auto'
      });

      Morris.Bar({
        element: 'spending-report',
        data: self.reporting().spendings,
        xkey: 'label',
        ykeys: ['value'],
        labels: ['Сума'],
        barRatio: 0.4,
        xLabelAngle: 90,
        hideHover: 'auto'
      });
    }

    function showCircle() {
      console.log("show circle");

      $("#income-report").html('');
      $("#spending-report").html('');
      Morris.Donut({
        element: 'income-report',
        data: self.reporting().incomings
      });
      Morris.Donut({
        element: 'spending-report',
        data: self.reporting().spendings
      });
    }

    self.report = function () {

      $.ajax({
        url: "/cabinet/reports/make/",
        type: 'POST',
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        async: false,
        data: JSON.stringify(filterBean()),
        success: function (result) {
          if (result.incomings.length == 0 && result.spendings == 0) {
            self.visibleblock(false);
          } else {

            self.visibleblock(true);

            self.reporting(result);

            //clear donuts
            $("#income-report").empty();
            $("#spending-report").empty();

            $('input[type=radio][name=reportView][value=circle]').prop('checked', true);
            showCircle();

            self.operations(result.operations);
            self.currency(self.reporting().currency);
            $("#operation-list-q").paging({

              limit: 20,
              rowDisplayStyle: 'block',
              activePage: 0,
              rows: []

            });
          }


        }, error: function (result) {
          $.alert({
            title: "Помилка",
            text: result.responseText
          });
        }
      });
    }

    self.selectedType.subscribe(function (operationType) {
      if (operationType == 1) {
        self.viewCategories(self.categories());
      } else {
        self.viewCategories(jQuery.grep(self.categories(), function (a) {
          return a.operation == operationType;
        }));
      }
      Array.prototype.contains = function (k) {
        for (var i = 0; i < this.length; i++) {
          if (this[i].id == k.id) {
            return true;
          }
        }
        return false;
      }

      var addAll = {id: -1, name: "Всі", parentName: ""}
      var a = self.viewCategories().contains(addAll);
      if (!a) {
        self.viewCategories.unshift(addAll);
      }
    });

    self.incomings = ko.computed(function () {
      if (typeof(self.reporting()) == 'undefined') return 0;
      var sum = 0.0;
      $.each(self.reporting().incomings, function (i, val) {
        sum += val.value;
      })
      return parseFloat(sum).toFixed(2) + " " + self.currency();
    }, this);

    self.spending = ko.computed(function () {
      if (typeof(self.reporting()) == 'undefined') return 0;
      var sum = 0.0;
      $.each(self.reporting().spendings, function (i, val) {
        sum += val.value;
      })
      return parseFloat(sum).toFixed(2) + " " + self.currency();
    }, this);

  }
  return app;
})
;