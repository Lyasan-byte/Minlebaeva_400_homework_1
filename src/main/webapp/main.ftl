<#include "base.ftl">

<#macro title>Кабинет</#macro>

<#macro content>
    <div style="display: flex; align-items: center; gap: 20px; margin-bottom: 20px;">
        <#if userImage?? && userImage != "">
            <img src="${userImage}"
                 alt="Фото профиля"
                 style="width: 100px; height: 100px; border-radius: 50%; object-fit: cover; border: 3px solid #007bff;">
        </#if>
        <div>
            <h3 style="margin: 0 0 10px 0;">Привет, ${username!''}!</h3>
            <p style="margin: 5px 0; color: #666;">Session ID: ${sessionId!''}</p>
            <p style="margin: 5px 0; color: #666;">Cookie user: ${cookieUser!''}</p>
        </div>
    </div>

    <div style="display: flex; gap: 15px;">
        <a href="/user" style="padding: 10px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 5px;">Пользователи</a>
        <a href="/logout" style="padding: 10px 20px; background: #dc3545; color: white; text-decoration: none; border-radius: 5px;">Выйти</a>
    </div>
</#macro>