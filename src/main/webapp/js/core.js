/** This is a central JS library that will be used for common/shared functions through out the application.
 *  Mostly utility/helper stuff - object literal pattern in the app namespace
 */
(function( window , document , undefined ){
	window.core = (function core() {
		
		/**
		 * Anything page related - anything to do with urls or other generic stuff that might be needed
		 * on any/every page
		 */
		var page = (function(){
			//Get the url of the application root - e.g. ig on localhost get the deployed app name
			var getAppRoot = (function(){
				var url = location.href;  // entire url including querystring - also: window.location.href;
			    var baseURL = url.substring(0, url.indexOf('/', 14));
			    if (baseURL.indexOf('http://localhost') != -1) {
			        var url = location.href;				// window.location.href;
			        var pathname = location.pathname;		// window.location.pathname;
			        var index1 = url.indexOf(pathname);
			        var index2 = url.indexOf("/", index1 + 1);
			        var baseLocalUrl = url.substr(0, index2);
			        return baseLocalUrl + "/";
			    } else {
			        return baseURL + "/";
			    }
			}());
			
			return {
				appRoot: getAppRoot
			};
		}());
		
		return {
			page: page
		};
	}());
})( this, this.document );