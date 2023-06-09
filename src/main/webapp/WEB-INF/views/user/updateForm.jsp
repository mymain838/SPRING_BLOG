<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <c:if test="${empty principal.user.oauth}">
        <form>
            <input type="hidden" id="id" value="${principal.user.id}">
            <input type="hidden" id="oauth" value="${principal.user.oauth}">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" placeholder="Enter username" id="username" readonly
                       value="${principal.user.username}">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="text" class="form-control" placeholder="Enter password" id="password">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" placeholder="Enter email" id="email"
                       value="${principal.user.email}">
            </div>
        </form>
        <button id="btn-update" class="btn btn-primary">회원수정 완료</button>
    </c:if>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>




