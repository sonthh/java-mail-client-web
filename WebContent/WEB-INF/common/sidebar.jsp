<%@page import="constant.Environment"%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<div class="col-md-3">
  <a href="/compose" class="btn btn-primary btn-block margin-bottom">Soạn thư</a>
  <div class="box box-solid">
    <div class="box-header with-border">
      <h3 class="box-title">Thư mục</h3>

      <div class="box-tools">
        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
        </button>
      </div>
    </div>
    <div class="box-body no-padding">
      <ul class="nav nav-pills nav-stacked">
        <li class="${linkActive == 'inbox' ? 'active' : ''}"><a href="/inbox"><i class="fa fa-inbox"></i> Hộp thư đến</a></li>
        <li class="${linkActive == 'sent' ? 'active' : ''}"><a href="/sent"><i class="fa fa-envelope-o"></i> Đã gửi</a></li>
        <li class="${linkActive == 'draft' ? 'active' : ''}"><a href="/draft"><i class="fa fa-file-text-o"></i> Thư nháp</a></li>
        <%-- <li class="${linkActive == 'trash' ? 'active' : ''}"><a href="/trash"><i class="fa fa-trash-o"></i> Thùng rác</a></li> --%>
        <li class="${linkActive == 'spam' ? 'active' : ''}"><a href="/spam"><i class="fa fa-exclamation-triangle"></i> Spam</a></li>
        <li class="" style="border-top: 1px solid #3c8dbc87;">
        	<a href="/logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
       	</li>
       	<li class="" style="border-top: 1px solid #3c8dbc87;">
        	<a href="javascript:void(0)" style="cursor: default;"><%=Environment.username%></a>
       	</li>
      </ul>
    </div>
  </div>

</div>