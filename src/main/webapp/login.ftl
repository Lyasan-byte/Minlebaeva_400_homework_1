<#include "base.ftl">

<#macro title>Вход</#macro>

<#macro content>
    <form method="post" action="/login">
        Логин:
        <input type="text" name="login">
        Пароль:
        <input type="password" name="password">
        <br>
        <input type="submit" value="Войти">
    </form>

    <br>
    <a href="sign_up.ftl">Регистрация</a>
</#macro>