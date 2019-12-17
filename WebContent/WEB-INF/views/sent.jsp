<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<div class="col-md-9">
  <div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">Thư đã gửi</h3>
    </div>
    <div class="box-body no-padding">
      <div class="mailbox-controls">
        <button type="button" class="btn btn-default btn-sm checkbox-toggle"><i class="fa fa-square-o"></i>
        </button>
        <form id="deleteAll" class="btn-group" action="/delete?t=sent" method="post">
          <button type="submit" name="DELETE_TYPE" value="multiple" class="btn btn-default btn-sm" data-toggle="tooltip"
            data-container="body" title="Delete">
            <i class="fa fa-trash-o"></i>
          </button>
        </form>
        <div class="pull-right">
          ${page.pageNumber*3-2}-${page.pageNumber*3-2+page.gmails.size()-1}/${page.totalOfElements}
          <div class="btn-group">
            <a href="/sent?page=${page.pageNumber-1}" class="btn btn-default btn-sm ${page.pageNumber==1?'disabled':''}">
              <i class="fa fa-chevron-left"></i></a>

            <a href="/sent?page=${page.pageNumber+1}" class="btn btn-default btn-sm ${page.pageNumber==page.totalOfPages?'disabled':''}">
              <i class="fa fa-chevron-right"></i></a>
          </div>
        </div>
      </div>
      <div class="table-responsive mailbox-messages">
        <table class="table table-hover table-striped">
          <tbody>
            <c:forEach items="${page.gmails}" var="gmail" varStatus="loop">
              <tr data-href="/read?t=sent&o=${loop.index}">
                <td><input name="id" form="deleteAll" value="${gmail.messageNumber}" type="checkbox"></td>
                <td class="mailbox-name"><a href="#">${gmail.from}</a></td>
                <td class="mailbox-subject">${gmail.subject}</td>
                <td class="mailbox-attachment">
                  <c:if test="${not empty gmail.attachments}">
                    <i class="fa fa-paperclip"></i>
                  </c:if>
                </td>
                <td class="mailbox-date">
                  <fmt:formatDate value="${gmail.date}" pattern="hh:mm:ss dd/MM/yyyy" />
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
<content tag="stylesheet">
  <style>
    tr {
      cursor: pointer;
    }
  </style>
</content>
<content tag="script">
  <script src="/assets/plugins/iCheck/icheck.min.js"></script>
  <script>
    $(function () {
      //Enable iCheck plugin for checkboxes
      //iCheck for checkbox and radio inputs
      $('.mailbox-messages input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
      });

      //Enable check and uncheck all functionality
      $(".checkbox-toggle").click(function () {
        var clicks = $(this).data('clicks');
        if (clicks) {
          //Uncheck all checkboxes
          $(".mailbox-messages input[type='checkbox']").iCheck("uncheck");
          $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
        } else {
          //Check all checkboxes
          $(".mailbox-messages input[type='checkbox']").iCheck("check");
          $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
        }
        $(this).data("clicks", !clicks);
      });

      //Handle starring for glyphicon and font awesome
      $(".mailbox-star").click(function (e) {
        e.preventDefault();
        //detect type
        var $this = $(this).find("a > i");
        var glyph = $this.hasClass("glyphicon");
        var fa = $this.hasClass("fa");

        //Switch states
        if (glyph) {
          $this.toggleClass("glyphicon-star");
          $this.toggleClass("glyphicon-star-empty");
        }

        if (fa) {
          $this.toggleClass("fa-star");
          $this.toggleClass("fa-star-o");
        }
      });
    });
  </script>
  <script>
    $("table tbody tr").click(function () {
      window.location = $(this).data("href");
    });
  </script>
</content>