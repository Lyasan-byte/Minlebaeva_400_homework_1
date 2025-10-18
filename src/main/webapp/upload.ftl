<html lang="en">

<#include "base.ftl">
<#macro title>Upload File</#macro>

<#macro content>
    <p>Upload File</p>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <br>
        <br>
        <input type="submit" value="Upload">
    </form>
</#macro>
</html>