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

    <#if photoUrl??>
        <img src="${photoUrl}" alt="Фото профиля" width="200">
    <#else>
        <p>Фото не загружено</p>
    </#if>

    <br><br>
    <a href="/user">Пользователи</a>
    <br>
    <a href="/logout">Выйти</a>


</#macro>