<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br/>
遍历列表
<br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stus as stu>
        <tr>
            <td>${stu_index + 1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.money}</td>
        </tr>
    </#list>
</table>

<br/>
遍历map
<br/>

姓名：${stuMap["stu1"].name}
姓名：${stuMap["stu1"].age}
姓名：${stuMap.stu1.money}

<#list stuMap?keys as k>
    <tr>
        <td>${k_index + 1}</td>
        <td>${stuMap[k].name}</td>
        <td>${stuMap[k].age}</td>
        <td >${stuMap[k].money}</td>
    </tr>
</#list>

</body>
</html>
