var logout = function(){
    window.location.replace('/logout');
};



function initMonthRange(div){
    $(div).daterangepicker(
        {
            locale: {
                format: "DD.MM.YYYY",
                startView: "year",
                minViewMode: "months",
                "separator": " - ",
                "applyLabel": "Приняти",
                "cancelLabel": "Відміна",
                "fromLabel": "Від",
                "toLabel": "До",
                "customRangeLabel": "Свій діапазон",
                "daysOfWeek": [
                    "Нд",
                    "Пн",
                    "Вт",
                    "Ср",
                    "Чт",
                    "Пт",
                    "Сб"
                ],
                "monthNames": [
                    "Січень",
                    "Лютий",
                    "Березень",
                    "Квітень",
                    "Травень",
                    "Червень",
                    "Липень",
                    "Серпень",
                    "Вересень",
                    "Жовтень",
                    "Листопад",
                    "Грудень"
                ],
                "firstDay": 1
            }
        });
}

function initDateRange(div,isFilther){
    var budgets = {
        'Сьогодні': [moment(), moment()],
        'Наступні 7 днів': [moment(), moment().add(7, 'days')],
        'Наступні 30 днів': [moment(), moment().add(29, 'days')],
        'Цей місяць': [moment().startOf('month'), moment().endOf('month')],
    };
    var filters =  {
        'Сьогодні': [moment(), moment()],
        'Попередні 7 днів': [ moment().subtract(7, 'days'),moment()],
        'Попередні 30 днів': [moment().subtract(29, 'days'),moment()],
        'Цей місяць': [moment().startOf('month'), moment().endOf('month')],
    };
    $(div).daterangepicker(
        {
            locale: {
                "format": "DD.MM.YYYY",
                "separator": " - ",
                "applyLabel": "Приняти",
                "cancelLabel": "Відміна",
                "fromLabel": "Від",
                "toLabel": "До",
                "customRangeLabel": "Свій діапазон",
                "daysOfWeek": [
                    "Нд",
                    "Пн",
                    "Вт",
                    "Ср",
                    "Чт",
                    "Пт",
                    "Сб"
                ],
                "monthNames": [
                    "Січень",
                    "Лютий",
                    "Березень",
                    "Квітень",
                    "Травень",
                    "Червень",
                    "Липень",
                    "Серпень",
                    "Вересень",
                    "Жовтень",
                    "Листопад",
                    "Грудень"
                ],
                "firstDay": 1
            },
            ranges: (isFilther)? filters : budgets
            ,"opens": "center"
        });
}

