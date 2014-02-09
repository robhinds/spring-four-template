<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<header class="full">
	<div class="container">
		<div class="row">
			<div class="col-lg-8" style="padding-top: 120px;">
				<h1 style="font-weight: 700; font-size: 3.2em;"><span class="header-highlight">Social CRM</span></h1>
				<h2 style="font-weight: 100; font-size: 2.8em;"><span class="header-highlight">Is your network working hard enough?</span></h2>
				<h2 style="font-weight: 100; font-size: 2.8em;"><span class="header-highlight">Manage your friends like leads.</span></h2>
			</div>
			<div class="col-lg-4" style="padding-top: 140px;">
				<div class="card-panel transparent">
					<form class="form-signin" action="loginprocess" method="POST">
				    	<h3 class="form-signin-heading">Already Registered?</h3>
				    	<div>
							<c:if test="${not empty param.success}">
								<div>
									<div class="alert alert-info"><h4>Thanks for registering!</h4> Sign in below to start making stuff</div>
								</div>
							</c:if>
							<c:if test="${not empty param.loginFailure}">
								<div>
									<div class="alert alert-danger"><h4>Error Signing In!</h4> Check your username and password is correct</div>
								</div>
							</c:if>
						</div>
						<input type="text" class="form-control" name='username' placeholder="User name">
						<input type="password" class="form-control" name='password' placeholder="Password"><br/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input class="btn btn-lg btn-primary" name="submit" type="submit" value='Sign in' >
						<a class="btn btn-lg btn-primary" href="sign-up" >Sign up</a>
					</form>
				</div>
			</div>
		</div>
	</div>
</header>
<div id="bground-header"></div>