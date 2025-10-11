<#include "base.ftl">

<#macro title>Регистрация</#macro>

<#macro content>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <form method="post" action="/sign_up">
        Имя: <input type="text" name="name" required><br>
        Фамилия: <input type="text" name="lastname" required><br>
        Логин: <input type="text" name="login" id="login" required><br>
        <span id="status"></span><br>
        Пароль: <input type="password" name="password" required><br>
        <input type="submit" value="Зарегистрироваться" id="submitBtn">
    </form>

    <a href="login.ftl">Назад к входу</a>

    <script>
        $(document).ready(function() {
            $("#login").on("change", function() {
                var login = $(this).val();

                $.get("/check_login?login=" + login, function(response) {
                    if (response === "taken") {
                        $("#status").text("Логин занят").css("color", "red");
                        $("#submitBtn").prop("disabled", true);
                    } else {
                        $("#status").text("Логин свободен").css("color", "green");
                        $("#submitBtn").prop("disabled", false);
                    }
                });
            });
        });
    </script>
</#macro>