<#include "base.ftl">

<#macro title>Регистрация</#macro>

<#macro content>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            let loginCheckTimeout;

            $('#login-input').on('input', function() {
                const login = $(this).val();
                $('#login-status').text('Проверка...').css('color', 'orange');

                clearTimeout(loginCheckTimeout);
                loginCheckTimeout = setTimeout(() => {
                    if (login.length >= 3) {
                        checkLoginAvailability(login);
                    } else {
                        $('#login-status').text('Логин должен содержать минимум 3 символа').css('color', 'red');
                        $('#submit-btn').prop('disabled', true);
                    }
                }, 500);
            });

            $('#image-input').on('change', function(e) {
                const file = e.target.files[0];
                if (file) {

                    if (file.size > 5 * 1024 * 1024) {
                        alert('Файл слишком большой. Максимальный размер: 5MB');
                        $(this).val('');
                        $('#image-preview').hide();
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function(e) {
                        $('#image-preview').attr('src', e.target.result).show();
                    }
                    reader.readAsDataURL(file);
                }
            });

            function checkLoginAvailability(login) {
                $.ajax({
                    url: '/check_login',
                    type: 'GET',
                    data: { login: login },
                    success: function(response) {
                        try {
                            let result;
                            if (typeof response === 'string') {
                                result = JSON.parse(response);
                            } else {
                                result = response;
                            }

                            $('#login-status').text(result.message).css('color', result.available ? 'green' : 'red');
                            $('#submit-btn').prop('disabled', !result.available);

                        } catch (e) {
                            console.error('JSON parse error:', e);
                            $('#login-status').text('Ошибка формата ответа').css('color', 'red');
                            $('#submit-btn').prop('disabled', true);
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX error:', status, error);
                        $('#login-status').text('Ошибка сервера: ' + status).css('color', 'red');
                        $('#submit-btn').prop('disabled', true);
                    }
                });
            }
            $('#submit-btn').prop('disabled', true);
        });
    </script>

    <#if RequestParameters.error??>
        <div style="color: red; margin-bottom: 15px; padding: 10px; background: #ffe6e6; border: 1px solid red; border-radius: 5px;">
            <#if RequestParameters.error == "empty_fields">
                Логин и пароль обязательны для заполнения
            <#elseif RequestParameters.error == "registration_failed">
                Ошибка при регистрации. Попробуйте еще раз.
            </#if>
        </div>
    </#if>

    <#if RequestParameters.success??>
        <div style="color: green; margin-bottom: 15px; padding: 10px; background: #e6ffe6; border: 1px solid green; border-radius: 5px;">
            <#if RequestParameters.success == "registered">
                Регистрация успешна! Теперь вы можете войти.
            </#if>
        </div>
    </#if>

    <form method="post" action="/sign_up" id="registration-form" enctype="multipart/form-data">
        Имя:
        <input type="text" name="name" required>
        <br>
        Фамилия:
        <input type="text" name="lastname" required>
        <br>
        Логин:
        <input type="text" name="login" id="login-input" required>
        <span id="login-status" style="margin-left: 10px; font-size: 12px;"></span>
        <br>
        Пароль:
        <input type="password" name="password" id="password-input" required>
        <br>
        Фото профиля:
        <input type="file" name="image" id="image-input" accept="image/*">
        <small style="display: block; color: #666;">Максимальный размер: 5MB</small>
        <br>
        <img id="image-preview" src="#" alt="Предпросмотр" style="max-width: 200px; max-height: 200px; display: none; margin-top: 10px;">
        <br>
        <input type="submit" value="Зарегистрироваться" id="submit-btn" disabled>
    </form>

    <br>
    <a href="login">Назад к входу</a>
</#macro>