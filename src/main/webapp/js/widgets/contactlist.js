var contactListWidget = contactListWidget || { Models: {}, Collections: {}, Views: {} };
$(function(){
	contactListWidget.Models.ContactModel = Backbone.Model.extend( {});
	contactListWidget.Collections.ContactList = Backbone.Collection.extend({
		model: contactListWidget.Models.ContactModel,
		initialize: function() {_.bindAll(this); },
		url: window.core.page.appRoot + 'api/contacts/list'
	});
	
	contactListWidget.Views.ContactRowView = Backbone.View.extend({
		template: _.template( $('#contact-row-template').html() ),
		
		initialize: function() {
			this.contactList = new contactListWidget.Collections.ContactList();
			this.contactList.on( 'reset', this.addAll, this );
			this.contactList.fetch({reset:true});
		},
		addAll: function() {
			$( "#contact-list-grid" ).html( '' );
			this.contactList.each( this.addOne, this );
		},
		addOne: function( contactRow ) {
			$( "#contact-list-grid" ).append( this.template( contactRow.toJSON() ) );
		},
		render: function() { return this; }
	});
}());