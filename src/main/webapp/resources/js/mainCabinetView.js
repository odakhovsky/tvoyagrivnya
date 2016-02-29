require.config({
  paths: {
    "knockout": "/resources/js/libs/knockout.min",
    "cabinet": "mainCabinetViewModel",
    "raphael": "/resources/js/libs/raphael-min",
    "morris": "/resources/js/libs/morris",
  },
  shim: {
    "morris": ["raphael"]
  }
});

require(["cabinet", "knockout", "raphael", "morris"], function (app, ko) {


  var statistic = null;
  $.ajax({
    url: '/statistic/simple/',
    async: false,
    dataType: 'json',
    success: function (data) {
      statistic = data;
      init();
    }
  });

  function init() {
    var viewMode = $('input[type=radio][name=graphView]').val();
    if (viewMode == 'rect') {
      showRect();
    }
    else if (viewMode == 'circle') {
      showCircle();
    }
  }

  function showRect() {
    $("#income-chart").html('');
    $("#spending-chart").html('');

    Morris.Bar({
      element: 'income-chart',
      data: statistic.incoming,
      xkey: 'label',
      ykeys: ['value'],
      labels: ['Сума'],
      barRatio: 0.4,
      xLabelAngle: 35,
      hideHover: 'auto'
    });

    Morris.Bar({
      element: 'spending-chart',
      data: statistic.spending,
      xkey: 'label',
      ykeys: ['value'],
      labels: ['Сума'],
      barRatio: 0.4,
      xLabelAngle: 35,
      hideHover: 'auto'
    });
  }

  function showCircle() {
    $("#income-chart").html('');
    $("#spending-chart").html('');
    Morris.Donut({
      element: 'income-chart',
      data: statistic.incoming
    });
    Morris.Donut({
      element: 'spending-chart',
      data: statistic.spending
    });
  }


  $('input[type=radio][name=graphView]').change(function () {
    if (this.value == 'rect') {
      showRect();
    }
    else if (this.value == 'circle') {
      showCircle();
    }
  });


  var model = app.cabinetViewModel(statistic);
  ko.applyBindings(model, $("#simple-statistic")[0]);
});
