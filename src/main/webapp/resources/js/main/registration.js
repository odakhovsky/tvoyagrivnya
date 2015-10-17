$(document).ready(function(){
    var $form = $("#registration-form");
    $form.ajaxForm({
        success: function(response) {
            console.log(response)
            if(response.success){
                $("#registration-title").text(response.errorMessage);
                var email = $form.find('input[type="email"]').val();
                $("#registration-message").text('Ваш пароль надіслано на ' + email);
                $('#reg-email').val('');
                $('#reg-name').val('');
            }else{
                $("#registration-title").text(response.errorMessage);
                $("#registration-message").text('');
            }

            $("#registrationConfirmModal").modal();
        }
    });

     $("#signIn").on('click',function(){
        $("#signInModal").modal();
    });

})
