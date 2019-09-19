<%--
  Created by IntelliJ IDEA.
  User: 30133
  Date: 2019/3/12
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>南航行李托运</title>
    <h1 align="center">南航行李托运</h1>
  </head>
  <body>
  <form action="tranport" method="post">
    <table align="center">
      <tr>
        <td>始发站：</td>
        <%--<td><input type="text" name="start" id="start"></td>--%>
        <td>
          <select id="start" name="start">
            <option>中国大陆（除兰州/乌鲁木齐）</option>
            <option>兰州/乌鲁木齐</option>
            <option>日本，澳洲，俄罗斯，新加坡</option>
              <option>美洲</option>
            <option>迪拜</option>
            <option>中西亚地区</option>
            <option>内罗毕（除毛里求斯）</option>
            <option>韩国</option>
            <option>其他</option>
          </select>
        </td>

      </tr>
        <br>
      <tr>
        <td>终点站：</td>
        <%--<td><input type="text" name="end" id="end"></td>--%>
        <td>
          <select id="end" name="end">
            <option>中国大陆（除兰州/乌鲁木齐）</option>
            <option>兰州/乌鲁木齐</option>
            <option>日本，澳新，俄罗斯</option>
              <option>美洲</option>
            <option>迪拜</option>
            <option>中西亚地区</option>
            <option>内罗毕（除毛里求斯）</option>
            <option>其他</option>
          </select>
        </td>
      </tr>
        <br>
      <tr>
        <td>舱位类型：</td>
        <%--<td><input type="text" name="roomtype" id="roomtype"></td>--%>
          <td>
              <select id="roomtype" name="roomtype">
                  <option>头等舱</option>
                  <option>公务舱</option>
                  <option>经济舱</option>
                  <option>明珠经济舱</option>
              </select>
          </td>
      </tr>
        <br>
        <tr>
            <td>票价：</td>
            <td><input type="text" name="price" value="1000"></td>
        </tr>
        <br>
      <tbody id="td">
      <tr>
        <td>行李：</td>
          <td>
              <select id="packagenum" name="packagenum">
                  <option>1</option>
                  <option>2</option>
                  <option>3</option>
              </select>
          </td>
      </tr>
      <tr>
        <td>重量和尺寸：</td>
        <td><input type="text" id="weight" name="weight" placeholder="重量" value="0"></td>
        <td><input type="text" id="x" name="x" placeholder="长" value="0"></td>
        <td><input type="text" id="y" name="y" placeholder="宽" value="0"></td>
        <td><input type="text" id="z" name="z" placeholder="高" value="0"></td>
      </tr>
      <tr>
          <td>重量和尺寸：</td>
          <td><input type="text" id="weight1" name="weight1" placeholder="重量" value="0"></td>
          <td><input type="text" id="x1" name="x1" placeholder="长" value="0"></td>
          <td><input type="text" id="y1" name="y1" placeholder="宽" value="0"></td>
          <td><input type="text" id="z1" name="z1" placeholder="高" value="0"></td>
      </tr>
      <tr>
          <td>重量和尺寸：</td>
          <td><input type="text" id="weight2" name="weight2" placeholder="重量" value="0"></td>
          <td><input type="text" id="x2" name="x2" placeholder="长" value="0"></td>
          <td><input type="text" id="y2" name="y2" placeholder="宽" value="0"></td>
          <td><input type="text" id="z2" name="z2" placeholder="高" value="0"></td>
      </tr>
      <tr>
          <td>重量和尺寸：</td>
          <td><input type="text" id="weight3" name="weight3" placeholder="重量" value="0"></td>
          <td><input type="text" id="x3" name="x3" placeholder="长" value="0"></td>
          <td><input type="text" id="y3" name="y3" placeholder="宽" value="0"></td>
          <td><input type="text" id="z3" name="z3" placeholder="高" value="0"></td>
      </tr>
      <tr>
          <td>重量和尺寸：</td>
          <td><input type="text" id="weight4" name="weight4" placeholder="重量" value="0"></td>
          <td><input type="text" id="x4" name="x4" placeholder="长" value="0"></td>
          <td><input type="text" id="y4" name="y4" placeholder="宽" value="0"></td>
          <td><input type="text" id="z4" name="z4" placeholder="高" value="0"></td>
      </tr>
      </tbody>
        <br>
        <tr>
            <td>乘客类型</td>
            <td>
                <select id="peopletype" name="peopletype">
                    <option>adult</option>
                    <option>child</option>
                    <option>baby with seat</option>
                    <option>baby without seat</option>
                </select>
            </td>
        </tr>
        <br>
        <tr>
            <td>会员类型</td>
            <td>
                <select id="isvip" name="isvip">
                    <option>无</option>
                    <option>南航明珠金卡会员，天台联盟超级精英</option>
                    <option>南航明珠银卡会员，天台联盟精英</option>
                    <option>留学生，劳务，海员</option>
                </select>
            </td>
        </tr>
    </table>
      <div align="center">
        <input type="submit" name="submit" id="submit" value="提交">
      </div>
  </form>
  </body>
</html>
<%--<%--%>
    <%--int money = (Integer)session.getAttribute("money");--%>
    <%--if(money != 0){--%>

<%--%>--%>
<%--<script type="javascript">--%>
    <%--alert("托运行李的费用为"+"<%=money%>");--%>
<%--</script>--%>

<%--<%--%>
        <%--session.setAttribute("money",0);--%>
    <%--}--%>
<%--%>--%>


<script type="javascript">
    function del(obj){
      var trId = obj.parentNode.parentNode.id;
      var trobj = document.getElementById(trId);
      document.getElementById("td").removeChild(trobj);
    }
    function add(){
        if(document.getElementById("packagenum").value >= 2) {
            var trobj = document.createElement("tr");
            trobj.id = new Date().getTime();
            trobj.innerHTML = "<td>重量和尺寸：</td><td><input type='text' id=\"weight1\" placeholder='重量'></td><td><input type=\"text\" id=\"x1\" placeholder=\"长\"></td>" +
                "<td><input type=\"text\" id=\"y1\" placeholder=\"宽\"></td><td><input type=\"text\" id=\"z1\" placeholder=\"高\"></td>";
            document.getElementById("td").appendChild(trobj);
        }
        if(document.getElementById("packagenum").value >= 3) {
            var trobj = document.createElement("tr");
            trobj.id = new Date().getTime();
            trobj.innerHTML = "<td>重量和尺寸：</td><td><input type='text' id=\"weight2\" placeholder='重量'></td><td><input type=\"text\" id=\"x2\" placeholder=\"长\"></td>" +
                "<td><input type=\"text\" id=\"y2\" placeholder=\"宽\"></td><td><input type=\"text\" id=\"z2\" placeholder=\"高\"></td>";
            document.getElementById("td").appendChild(trobj);
        }
    }
</script>
