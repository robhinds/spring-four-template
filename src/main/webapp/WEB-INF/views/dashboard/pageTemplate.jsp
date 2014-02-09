<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="container" style="padding-left: 5px;">

	<h1>Welcome to your control center.. </h1>

	<div class="col-md-3" style="padding-left: 0px;">
		<tiles:insertAttribute name="social-connect-menu" />
		<tiles:insertAttribute name="recent-activity" />
	</div>
	<div class="col-md-6" >
		<tiles:insertAttribute name="social-stats" />
	</div>
	<div class="col-md-3" >
		<tiles:insertAttribute name="recent-campaigns" />
	</div>
</div>