<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Đăng nhập</title>
  <link rel="shortcut icon" type="image/x-icon" href="/assets/dist/img/favicon.ico" />
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="/assets//bower_components/bootstrap/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="/assets/bower_components/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/assets/bower_components/Ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="/assets/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body class="hold-transition login-page">
  <div class="login-box">
    <div class="login-logo">
      <a href="javascript:void(0)" style="cursor: text;"><b>Mail Client</b></a>
    </div>
    <div class="login-box-body">
      <c:if test="${param.msg eq 'error'}">
        <p class="login-box-msg" style="color: #e81212">Sai email hoặc mật khẩu</p>
      </c:if>
      <form action="/login" method="post">
        <input type="hidden" name="ReturnUrl" value="<%=request.getHeader(" referer")%>" />
        <input type="hidden" name="a" value="<%=request.getHeader(" Referer")%>" />
        <div class="form-group has-feedback">
          <input autocomplete="off" name="username" required type="email" class="form-control" placeholder="Email"
            value="tranhuuhongson@gmail.com">
          <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
          <input autocomplete="off" name="password" required type="password" class="form-control" placeholder="Mật khẩu"
            value="tranhuuhongSon1998@">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="row">
          <div class="col-xs-12">
            <button type="submit" class="btn btn-primary btn-block btn-flat">Đăng nhập</button>
          </div>
        </div>
      </form>
    </div>
  </div>
  <script src="/assets/bower_components/jquery/dist/jquery.min.js"></script>
  <script src="/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
</body>

</html>