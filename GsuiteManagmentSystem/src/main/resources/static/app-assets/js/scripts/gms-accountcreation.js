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

	//	
	
	
	
	

	$("#submit").click(function() {

		var familyname = $("#familyname").val();
		var givenName = $("#givenName").val();
		var emailName = $("#emailName").val();
		var domain = $("#domain").val();
		var jobtitle = $("#jobtitle").val();
		var phonenumber = $("#phonenumber").val();
		var defaultpassword = $("#defaultpassword").val();
		var token = $("meta[name='_csrf']").attr("content");

		var x = document.getElementById("progress");
	   	var onsucessNotification = document.getElementById("onsuccess");
	   	x.style.display = "block";
	     
		var myuser = {
			familyName : familyname,
			otherName : givenName,
			emailName : emailName,
			phoneNumberOne : phonenumber,
			phoneNumberTwo : phonenumber,
			jobPosition : jobtitle,
			defaultPassword : defaultpassword,
			changePasswordAtNextLogin : true,
			cretedById : null,
			domain : domain
		}

		$.ajax({
			url : "/userRegistrations",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(myuser),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				x.style.display = "none";
				 $('#message').append('<p>'+emailName+' Account Slot Updated Sucessfully<p>')
				 onsucessNotification.style.display = "block";
		  	  
			},
			error : function(e) {				
			}
		});
	});

});