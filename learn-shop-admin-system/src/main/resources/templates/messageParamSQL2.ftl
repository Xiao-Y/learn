<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息通知-混合-多行sql</title>
</head>

<style type="text/css">
    table {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        width: 100%;
        border-collapse: collapse;
    }

    td, th {
        font-size: 1em;
        border: 1px solid #5B4A42;
        padding: 3px 7px 2px 7px;
    }

    th {
        font-size: 1.1em;
        text-align: center;
        padding-top: 5px;
        padding-bottom: 4px;
        background-color: #24A9E1;
        color: #ffffff;
    }
</style>
<body>
<div>
    <h2>邮件消息通知</h2>
    <table id="customers">
        <tr>
            <th>MailCode</th>
            <th>TemplatePath</th>
            <th>messageCode</th>
            <th>MessageStatus</th>
        </tr>
        <tr>
            <td>${(mailCode)!""}</td>
            <td>${(templatePath)!""}</td>
            <td>${(messageCode)!""}</td>
            <td>${(messageStatus)!""}</td>
        </tr>
    </table>
    <br/>
    <h2>邮件消息通知2</h2>
    <table id="customers">
        <tr>
            <th>MailCode</th>
            <th>TemplatePath</th>
        </tr>
        <#list root as mailTemplate>
            <tr>
                <td>${(mailTemplate.mailCode)!""}</td>
                <td>${(mailTemplate.templatePath)!""}</td>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>