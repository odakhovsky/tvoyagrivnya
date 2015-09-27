<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="signInModal" tabindex="-1" role="dialog" aria-labelledby="dataConfirmLabel"
     aria-hidden="true">
    <div class="modal-dialog ">
        <div class="modal-content sign-in-modal">
            <div class="modal-body comfirmFont row">
                <button type="button" id="close-sign-in-modal" class="pull-right" data-dismiss="modal" aria-hidden="true"><i
                        class="fa fa-close"></i></button>
                <h4 align="center" class="sign-in-tittle">Вхід до системи</h4>

                <form method="post" action="/j_spring_security_check" style="padding: 25px">
                    <div class="form-group">
                        <input name="j_username" type="text" class="registration-input form-control"
                               placeholder="Ваша пошта" value="" placeholder="Введіть  пошту" id="login-name">
                        <label class="login-field-icon fui-mail" for="login-name"></label>
                    </div>

                    <div class="form-group">
                        <input name="j_password" type="password" class="registration-input form-control" value=""
                               placeholder="Введіть пароль" id="login-pass">
                        <label class="login-field-icon fui-lock" for="login-pass"></label>
                    </div>

                    <button type="submit" class="btn btn-default btn-lg btn-block">Увійти в систему</button>
                    <a  id="forgot-password-go" class="page-scroller" href="#forgot-password"><span class="remember-password-text pull-right">Забув пароль?</span></a>
                </form>
            </div>

        </div>
    </div>
</div>