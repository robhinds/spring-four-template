<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sidebar-left" style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Connect your accounts</h3>
		<ul class="nav nav-tabs nav-stacked main-menu">
			
			<!-- FACEBOOK IMPORT -->
			<li class="facebook-connect">
				<form id="connect-facebook-accounts" action="<c:url value="/connect/facebook" />" method="POST">
				    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<c:if test="${facebookConnected== true}">
					<a href="javascript:;" onclick="document.getElementById('connect-facebook-accounts').submit();"><i class="fa fa-lg fa-fw fa-facebook"></i>
						Import Facebook Contacts
					</a>
				</c:if>
				<c:if test="${facebookConnected== false}">
					<a href="javascript:;" onclick="document.getElementById('connect-facebook-accounts').submit();"><i class="fa fa-lg fa-fw fa-facebook"></i>
						Connect to Facebook
					</a>
				</c:if>
			</li>
			
			<!-- TWITTER IMPORT -->
			<li class="twitter-connect">
				<form id="connect-twitter-accounts" action="<c:url value="/connect/twitter" />" method="POST">
				    <input type="hidden" name="scope" value="publish_stream,offline_access" />
				    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<c:if test="${twitterConnected== true}">
					<a href="javascript:;" class="connected" onclick="document.getElementById('connect-twitter-accounts').submit();"><i class="fa fa-lg fa-fw fa-twitter"></i>
						Import Twitter Contacts
					</a>
				</c:if>
				<c:if test="${twitterConnected== false}">
					<a href="javascript:;" onclick="document.getElementById('connect-twitter-accounts').submit();"><i class="fa fa-lg fa-fw fa-twitter"></i>
						Connect to Twitter
					</a>
				</c:if>
			</li>
			
			<!-- GOOGLE IMPORT -->
			<li class="google-connect">
				<form id="connect-google-accounts" action="<c:url value="/connect/googleplus" />" method="POST">
				    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<c:if test="${googleConnected== true}">
					<a href="javascript:;" onclick="document.getElementById('connect-google-accounts').submit();"><i class="fa fa-lg fa-fw fa-google-plus"></i>
						Import Google Contacts
					</a>
				</c:if>
				<c:if test="${googleConnected== false}">
					<a href="javascript:;" onclick="document.getElementById('connect-google-accounts').submit();"><i class="fa fa-lg fa-fw fa-google-plus"></i>
						Connect to Google
					</a>
				</c:if>
			</li>
			
			<!-- LINKEDIN IMPORT -->
			<li class="linkedin-connect">
				<form id="connect-linkedin-accounts" action="<c:url value="/connect/linkedin" />" method="POST">
				    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<c:if test="${linkedinConnected== true}">
					<a href="javascript:;" onclick="document.getElementById('connect-linkedin-accounts').submit();"><i class="fa fa-lg fa-fw fa-linkedin"></i>
						Import LinkedIn Contacts
					</a>
				</c:if>
				<c:if test="${linkedinConnected== false}">
					<a href="javascript:;" onclick="document.getElementById('connect-linkedin-accounts').submit();"><i class="fa fa-lg fa-fw fa-linkedin"></i>
						Connect to LinkedIn
					</a>
				</c:if>
			</li>
		
		</ul>
	</div>
</div>