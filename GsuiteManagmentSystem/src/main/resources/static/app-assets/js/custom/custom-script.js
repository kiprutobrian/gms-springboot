/*================================================================================
	Item Name: Materialize - Material Design Admin Template
	Version: 5.0
	Author: PIXINVENT
	Author URL: https://themeforest.net/user/pixinvent/portfolio
================================================================================

NOTE:
------
PLACE HERE YOUR OWN JS CODES AND IF NEEDED.
WE WILL RELEASE FUTURE UPDATES SO IN ORDER TO NOT OVERWRITE YOUR CUSTOM SCRIPT IT'S BETTER LIKE THIS. */

$(document).ready(function() {
	
	
	
//	$('header-search-input').autocomplete({
//		data : {
//			"Apple" : null,
//			"Microsoft" : null,
//			"Google" : 'https://placehold.it/250x250'
//		},
//	});
	
	 $('input.autocomplete').autocomplete({
	     data: {
	        Apple: null,
	        Microsoft: null,
	        Google: "https://placehold.it/250x250"
	     },
	     limit: 20, // The max amount of results that can be shown at once. Default: Infinity.
	     onAutocomplete: function(val) {
	        // Callback function when value is autcompleted.
	     },
	     minLength: 1 // The minimum length of the input for the autocomplete to start. Default: 1.
	  });
	
//	var dataCountry= {
//			"Apple" : null,
//			"Microsoft" : null,
//			"Google" : 'https://placehold.it/250x250'
//		};
//		
//	console.log("xxxxx");
//	
//	$('header-search-input').autocomplete({
//		
//		
//		data : dataCountry,
//		limit : 5, // The max amount of results that
//	// can be shown at once.
//	});
	
//	$("#header-search-input").on("click", function() {
//	    console.log("EDWIN")
//	    // ...
//	});
//
//	$('#header-search-input').on('autocompletechange change', function() {
//		console.log(""+$('#input.autocomplete').val());
//	});

	// $.ajax({
	// type : 'GET',
	// url : '/getClients',
	// headers : {
	// "X-XSRF-TOKEN" : token,
	// },
	// success : function(response) {
	// var client = response;
	// var dataCountry = {};
	// for (var i = 0; i < client.length; i++) {
	// // console.log(countryArray[i].name);
	// dataCountry[client[i].name] = null;
	// }
	// $('#explore_gms').autocomplete({
	// data : dataCountry,
	// limit : 5, // The max amount of results that
	// // can be shown at once.
	// });
	// }
	// });

});