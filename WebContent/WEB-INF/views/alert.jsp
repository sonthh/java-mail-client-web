<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<div class="col-md-9">
  <div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">Thư nháp</h3>
    </div>
    <div class="box-body no-padding">
    	<h1 class="alert-message">${message}</h1>
    </div>
  </div>
</div>
<content tag="stylesheet">
  <style>
    h1.alert-message {
    	padding: 115px 00px;
	    text-align: center;
	    color: #3c8dbc;
    }
  </style>
</content>
