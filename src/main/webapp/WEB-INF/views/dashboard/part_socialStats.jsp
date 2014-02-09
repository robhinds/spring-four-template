<script type="text/javascript">
	$(document).ready(function() { new contactListWidget.Views.ContactRowView(); });
</script>

<div id="sidebar-left" style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Stats</h3>
		<div class="whitecard">
			<div id="contact-list-grid"></div>
		</div>
	</div>
</div>


<script type="text/template" id="contact-row-template">
	<img src='{{ socialProfiles[0].thirdPartyImageUrl }}' title='{{ socialProfiles[0].displayName }}' alt='{{ socialProfiles[0].displayName }}' />
</script>