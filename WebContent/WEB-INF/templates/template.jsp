<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${title == null ? 'Mail Client' : title}</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="shortcut icon" type="image/x-icon" href="/assets/dist/img/favicon.ico" />
  <link rel="stylesheet" href="/assets/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="/assets/bower_components/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/assets/bower_components/Ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="/assets/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="/assets/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="/assets/plugins/iCheck/flat/blue.css">
  <decorator:getProperty property="page.stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
    <div class="content-wrapper" style="margin: 0">
      <section class="content">
        <div class="row">

          <%@ include file="/WEB-INF/common/sidebar.jsp" %>

          <decorator:body />

        </div>
      </section>
    </div>
  </div>

  <script src="/assets/bower_components/jquery/dist/jquery.min.js"></script>
  <script src="/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
  <script src="/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
  <script src="/assets/bower_components/fastclick/lib/fastclick.js"></script>
  <script src="/assets/dist/js/adminlte.min.js"></script>
  <script src="/assets/dist/js/demo.js"></script>
  <decorator:getProperty property="page.script" />
</body>

</html>