<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<!-- Fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Social CRM</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<c:url value='/' />">Dashboard</a></li>
				<li><a href="<c:url value='/contacts' />">Contacts</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Campaigns <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="<c:url value='/campaigns/new' />">New Campaign</a></li>
						<li><a href="<c:url value='/campaigns/stats' />">Campaign Stats</a></li>
					</ul>
				</li>
				<li>
					<a href="#" onclick="document.getElementById('logoutform').submit();" >Log out</a>
					<form id="logoutform" action="<c:url value='/logout' />" method="POST"><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>