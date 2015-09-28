$(function () {
    $('#_date').datetimepicker({
        format: 'DD.MM.YYYY'
    })


    var submitForm = $("#edit-user-form")
    var oldLogin = submitForm.find("input[name='email']").val();


    $.validator.addMethod("pattern", function (value, element, param) {
        return this.optional(element) || value.match(param);
    }, "Invalid format.");

    $.validator.addMethod("uniqueLogin", function (value, element) {
        if (!$(element).attr("readonly")) {
            return isLoginUnique(value);
        }
        return true;
    }, "Користувач з такою поштою вже існує");

    submitForm.validate({
        rules: {
            editusername: {
                required: true,
                minlength: 2,
                maxlength: 30,
                pattern: /^(([a-zа-яіїєґ]+[\s\-`'’]?)[a-zа-яіїєґ]+)+$/i
            },
            email: {
                required: {
                    depends: function() {
                        $(this).val($.trim($(this).val()));
                        return true;
                    }
                },
                email: true,
                uniqueLogin: true

            }
        },

        messages: {
            editusername: {
                pattern: "Should contain letters - ` ' ’</br> symbols and space",
                required:"Поле обов'язкове до заповнення"
            },
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

    function isLoginUnique(value) {
        var isSuccess = true;
        var isSuccess = true;

        if (oldLogin != value) {
            $.ajax({
                url: "/isLoginFree",
                type: "POST",
                async: false,
                data: {login: value},
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                cache: false,
                success: function (data) {
                    isSuccess = data;
                }, error: function (data) {
                    isSuccess = data;
                }
            });
        }
        return isSuccess;
    }

    function validSelects(elements) {
        var b = true;
        $.each(elements, function (index, element) {
            if (isNaN(parseInt(element.value))) {
                $(element).addClass("has-error");

                setTimeout(function () {
                    $(element).removeClass("has-error");
                }, 2000);

                b = false;
                return;
            }
        });
        return b;
    }
});


$("#edit-user-pass").validate({
    rules: {
        password: {
            pattern: /^[a-zA-Z0-9\.,\-_=!@#$%^&*()+?/'";:~`* ]+$/,
            required: true,
            minlength: 5,
            maxlength: 30
        },
        confirmPass: {
            pattern: /^[a-zA-Z0-9\.,\-_=!@#$%^&*()+?/'";:~`* ]+$/,
            required: true,
            equalTo: "#_password"
        }

    },

    messages: {
        password: {
            pattern: "Перевірте структуру пароля",
            required:"Поле обов'язкове до заповнення",
            minlength:"Мін. довжина {0}",
            maxlength:"Макс. довжина {0}"
        },
        confirmPass: {
            pattern: "Should contain letters - ` ' ’</br> symbols and space",
            required:"Поле обов'язкове до заповнення",
            minlength:"Мін. довжина {0}",
            maxlength:"Макс. довжина {0}",
            equalTo:"Паролі мають бути однакові"
        },
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