<%@page import="util.StringUtil"%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<div class="col-md-9">
  <div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">Đọc thư</h3>
    </div>
    <div class="box-body no-padding">
   	  <div class="mailbox-read-info">
        <h3>${gmail.subject}</h3>
        <h5>From: ${gmail.from}
          <span class="mailbox-read-time pull-right">
            <fmt:formatDate value="${gmail.date}" pattern="hh:mm:ss dd/MM/yyyy" /></span></h5>
      </div>
      <div class="mailbox-controls with-border text-center">
        <form class="btn-group" action="/delete?t=${param.t}" method="post">
          <input type="hidden" name="id" value="${gmail.messageNumber}" />
          <button type="submit" class="btn btn-default btn-sm" name="DELETE_TYPE" value="only" data-toggle="tooltip"
            data-container="body" title="Delete">
            <i class="fa fa-trash-o"></i></button>
        </form>
      </div>
      <div class="mailbox-read-message">
        ${StringUtil.getEmailContent(gmail.content)}
      </div>
    </div>
    <c:if test="${not empty gmail.attachments}">
      <div class="box-footer">
        <ul class="mailbox-attachments clearfix">
          <c:forEach items="${gmail.attachments}" var="attachment" varStatus="loop">
            <c:set var="urlDownload" value="/download?t=${param.t}&o=${param.o}&f=${loop.index}" />

            <c:choose>
              <c:when test="${attachment.isImage() == true}">
                <li>
                  <span class="mailbox-attachment-icon has-img">
                    <img src="data:image/*;base64,${attachment.base64}" alt="Attachment"></span>
                  <div class="mailbox-attachment-info">
                    <a href="${urlDownload}" class="mailbox-attachment-name"><i class="fa fa-camera"></i>
                      ${attachment.fileName}</a>
                    <span class="mailbox-attachment-size">
                      ${attachment.getSize()}
                      <a href="${urlDownload}" class="btn btn-default btn-xs pull-right"><i class="fa fa-cloud-download"></i></a>
                    </span>
                  </div>
                </li>
              </c:when>
              <c:otherwise>
                <li>
                  <span class="mailbox-attachment-icon"><i class="fa fa-file"></i></span>
                  <div class="mailbox-attachment-info">
                    <a href="${urlDownload}" class="mailbox-attachment-name">
                      <i class="fa fa-paperclip"></i> ${attachment.fileName}</a>
                    <span class="mailbox-attachment-size">
                      ${attachment.getSize()}
                      <a href="${urlDownload}" class="btn btn-default btn-xs pull-right"><i class="fa fa-cloud-download"></i></a>
                    </span>
                  </div>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </ul>
      </div>
    </c:if>
    <!-- <div class="box-footer">
      <div class="pull-right">
        <button type="button" class="btn btn-default"><i class="fa fa-reply"></i> Reply</button>
        <button type="button" class="btn btn-default"><i class="fa fa-share"></i> Forward</button>
      </div>
    </div> -->
  </div>
</div>