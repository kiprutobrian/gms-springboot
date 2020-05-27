/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var newrm = $('#pagelengthoption').DataTable({
		bPaginate : true,
		bLengthChange : false,
		bFilter : true,
		bInfo : false,
		bAutoWidth : false,
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox ',
			targets : 0
		} ],
		select : {
			style : 'multi',
			selector : 'td:nth-child(1)',
		},
		order : [ [ 1, 'asc' ] ]
	});
	
	

	

//	$("#submit").click(function() {
//
//		var familyname = $("#familyname").val();
//		var givenName = $("#givenName").val();
//		var emailName = $("#emailName").val();
//		var domain = $("#domain").val();
//		var jobtitle = $("#jobtitle").val();
//		var phonenumber = $("#phonenumber").val();
//		var defaultpassword = $("#defaultpassword").val();
//		var token = $("meta[name='_csrf']").attr("content");
//
//		var x = document.getElementById("progress");
//	   	var onsucessNotification = document.getElementById("onsuccess");
//	   	x.style.display = "block";
//	     
//		var myuser = {
//			familyName : familyname,
//			otherName : givenName,
//			emailName : emailName,
//			phoneNumberOne : phonenumber,
//			phoneNumberTwo : phonenumber,
//			jobPosition : jobtitle,
//			defaultPassword : defaultpassword,
//			changePasswordAtNextLogin : true,
//			cretedById : null,
//			domain : domain
//		}
//
//		$.ajax({
//			url : "/userRegistrations",
//			headers : {
//				"X-XSRF-TOKEN" : token,
//			},
//			data : JSON.stringify(myuser),
//			type : "POST",
//			contentType : "application/json",
//			dataType : 'json',
//			success : function(result) {
//				x.style.display = "none";
//				 $('#message').append('<p>'+emailName+' Account Slot Updated Sucessfully<p>')
//				 onsucessNotification.style.display = "block";
//		  	  
//			},
//			error : function(e) {				
//			}
//		});
//
//	});

})
