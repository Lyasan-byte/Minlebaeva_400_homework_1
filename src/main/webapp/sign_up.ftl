<#include "base.ftl">

<#macro title>Регистрация</#macro>

<#macro content>
    <form method="post" action="/sign_up">
        Имя:
        <input type="text" name="name">
        Фамилия:
        <input type="text" name="lastname">
        Логин:
        <input type="text" name="login">
        Пароль:
        <input type="password" name="password">
        <br>
        <input type="submit" value="Зарегистрироваться">
    </form>

    <br>
    <a href="login.ftl">Назад к входу</a>
</#macro>