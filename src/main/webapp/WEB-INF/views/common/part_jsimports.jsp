<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<!-- Third party stuff -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery/jquery-1.9.1.min.js"><\/script>')</script>
<script src="<c:url value='/resources/js/vendor/bootstrap/bootstrap.min.js' />"></script>
<script src="<c:url value='/resources/js/vendor/underscore/underscore.dev.js' />"></script>
<script src="<c:url value='/resources/js/vendor/backbone/backbone.dev.js' />"></script>

<!-- Core custom js stuff -->
<script src="<c:url value='/resources/js/core.js' />"></script>
<script src="<c:url value='/resources/js/config.js' />"></script>

<script>
	var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
	(function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
	g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
	s.parentNode.insertBefore(g,s)}(document,'script'));
</script>