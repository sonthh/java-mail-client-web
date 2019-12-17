<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<div class="col-md-9">
  <div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">Soạn thư mới</h3>
      <c:if test="${not empty message}">
        <div class="text-blue">${message}</div>
      </c:if>
    </div>
    <form class="" action="/compose" enctype="multipart/form-data" method="post">
      <!-- /.box-header -->
      <div class="box-body">
        <div class="form-group">
          <input name="to" class="form-control" placeholder="To:" value="${to}">
        </div>
        <div class="form-group">
          <input name="cc" class="form-control" placeholder="Cc:" value="${cc}">
        </div>
        <div class="form-group">
          <input name="bcc" class="form-control" placeholder="Bcc:" value="${bcc}">
        </div>
        <div class="form-group">
          <input name="subject" class="form-control" placeholder="Subject:" value="${subject}">
        </div>
        <div class="form-group">
          <textarea name="content" id="compose-textarea" class="form-control" style="height: 300px">${content}</textarea>
        </div>
        <div class="form-group">
          <div class="btn btn-default btn-file">
            <i class="fa fa-paperclip"></i> Đính kèm
            <input name="attachments" multiple="multiple" type="file" name="attachment">
          </div>
        </div>
      </div>
      <div class="box-footer">
        <div class="pull-right">
          <!-- <button type="button" class="btn btn-default"><i class="fa fa-pencil"></i> Draft</button> -->
          <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i> Gửi</button>
        </div>
        <button type="reset" class="btn btn-default"><i class="fa fa-times"></i> Xóa</button>
      </div>
    </form>
  </div>
</div>
<content tag="stylesheet">
  <link rel="stylesheet" href="/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
  <script src="/assets/dropzone.js"></script>
	<link rel="stylesheet" href="/assets/dropzone.css">
</content>
<content tag="script">
  <script src="/assets/plugins/iCheck/icheck.min.js"></script>
  <script src="/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
  <script>
    $(function () {
      $("#compose-textarea").wysihtml5();
    });
  </script>
</content>