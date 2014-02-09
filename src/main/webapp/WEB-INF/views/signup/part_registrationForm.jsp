<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<div id="signup-form" class="container" style="margin-top:75px;">
	<div class="row whitecard">
		<c:if test="${not empty registrationerror}">
			<div class="alert alert-error">${registrationerror}</div>
		</c:if>

		<form id="userForm" method="post" action="" autocomplete="off"  class="form-horizontal" role="form">

			<h3>Enter your email address</h3>
			<div class="form-group <c:if test="${not empty emailerror}">has-error</c:if>">
				<label for="email1" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-6">
					<input type="email" class="form-control" id="email1" name="email1" placeholder="Email"/>
				</div>
			</div>
			<c:if test="${not empty emailerror}">
				<div class="alert alert-info">${emailerror}</div>
			</c:if>
			<br/>
			
			<h3>Choose a password for your new account</h3>
			<div class="form-group <c:if test="${not empty passworderror}">has-error</c:if>">
				<label for="password1" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="password1" name="pword1" placeholder="Password"/>
				</div>
			</div>
			<div class="form-group <c:if test="${not empty passworderror}">has-error</c:if>">
				<label for="password2" class="col-sm-2 control-label">Repeat password</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="password2" name="pword2" placeholder="Password"/>
				</div>
			</div>
			<c:if test="${not empty passworderror}">
				<div class="alert alert-info">${passworderror}</div>
			</c:if>
			<br/>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input class="btn btn-large btn-primary" type="submit" id="submitform" tabindex="8" value="Complete Signup &raquo;" /><br/><br/>
			
		</form>
	</div>
</div>