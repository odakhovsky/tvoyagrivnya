$(document).ready(function () {



        $('.editable').editable({
            placement: 'left',
            source: getCats(),
            select2: {
                width: 300,
                placeholder: 'Оберіть категорію',
                allowClear: true
            }
        });
        function getCats(){
            var arr;
            $.ajax({
                url: "/cabinet/organizer/categories/list",
                async: false,
                success:function(data){
                    arr = data;
                }
            });
            return arr;
        }

        $("#createnot").validate({
            rules: {
                text: {
                    required: true,
                    minlength: 5,
                    maxlength: 2500
                }

            },

            messages: {
                text: {
                    required: "Обов'язкове поле",
                    minlength: "Мін. довжина {0}",
                    maxlength: "Макс. довжина {0}"
                }
            },

            ignore: ':hidden:not(.chzn-done)',

            errorElement: "label",
            errorClass: "errorMessage",
            validClass: "success",
            highlight: function (element, errorClass, validClass) {
                $(element).addClass(errorClass).removeClass(validClass);
                $(element.form).find("label[for=" + element.id + "]")
                    .addClass(errorClass);
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass(errorClass).addClass(validClass);
                $(element.form).find("label[for=" + element.id + "]")
                    .removeClass(errorClass);
            }
        });
    }
);