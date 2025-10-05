<#include "base.ftl">

<#macro title>Greeting</#macro>

<#macro content>
    <h3>
        Welcome, ${username}!
        <br>
        Session ID = ${sessionId}
        <br>
        Cookie user = ${cookieUser}
    </h3>

    <a href="/user">Пользователи</a>
    <br>
    <a href="/logout">Выйти</a>
</#macro>