<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息通知</title>
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
            <th>MessageCode</th>
            <th>MessageStatus</th>
            <th>Cause</th>
        </tr>
        <tr>
            <td>${(messageCode)!""}</td>
            <td>${(messageStatus)!""}</td>
            <td>${(cause)!""}</td>
        </tr>
    </table>
</div>
</body>
</html>