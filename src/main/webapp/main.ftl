<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
</head>
<body>
<#assign sessionUser = Session.user!"Guest">
<#assign sessionId = Session.id!"unknown">
<#assign cookieUser = "">
<#if Request.cookies??>
    <#list Request.cookies as c>
        <#if c.name == "user">
            <#assign cookieUser = c.value>
        </#if>
    </#list>
</#if>

<h3>
    Hello, ${sessionUser}! Login successful
    <br>
    Session ID = ${sessionId}
    <br>
    Cookie user = ${cookieUser!""}
</h3>
<p><a href="/user">View Users</a></p>
<p><a href="/logout">Logout</a></p>
</body>
</html>