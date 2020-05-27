/*
 * DataTables - Tables
 */

$(document).ready(function() {

	$("#allowAccess").click(function() {

		 var x = document.getElementById("progress");
    	 var onsucessNotification = document.getElementById("onsuccess");
    	 x.style.display = "block";
    	
		 
		var token = $("meta[name='_csrf']").attr("content");
		var selectedRoleId = $("#selectedRoleId").val();
		var selecteduser = $("#domainaccountmaker :selected").val();
		var RoleAccess = {
			id : "",
			emailAdress : selecteduser,
			roleId : selectedRoleId
		}

		$.ajax({
			url : "/AddRoleAccess",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(RoleAccess),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				x.style.display = "none";
				 $('#message').append('<p> Account <span style="color:blue;">'+selecteduser+'</span> Account Has Access Now<p>')
				 onsucessNotification.style.display = "block";
				
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	});

	$("#updateChecker").click(function() {

		
		 var x = document.getElementById("progress");
    	 var onsucessNotification = document.getElementById("onsuccess");
    	 x.style.display = "block";
    	 
		
		var token = $("meta[name='_csrf']").attr("content");
		var selectedRoleId = $("#selectedRoleId").val();
		var selectedchecker = $("#checkergmsaccounts :selected").text();

		var state = $("#isActive").is(":checked");

		console.log("selecteduser: " + selectedchecker);
		console.log("isActive: " + state);

		var Checkers = {
			id : selectedRoleId,
			checkerEmailAddress : selectedchecker,
			active : state
		}
		$.ajax({
			url : "/AddMarkerChecker",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(Checkers),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				
				x.style.display = "none";
				 $('#message').append('<p> <span style="color:blue;">'+selectedchecker+'</span> Account Has Access Now<p>')
				 onsucessNotification.style.display = "block";
				

			},
			error : function(e) {

				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

	});

	$("#isActive").click(function() {

		var x = document.getElementById("progress");
	   	var onsucessNotification = document.getElementById("onsuccess");
	   	x.style.display = "block";
	   	 
		var token = $("meta[name='_csrf']").attr("content");
		var selectedRoleId = $("#selectedRoleId").val();
		var state = $("#isActive").is(":checked");

		var Checkers = {
			id : selectedRoleId,
			checkerEmailAddress : '*',
			active : state
		}

		$.ajax({
			url : "/makerChekerState",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(Checkers),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
			
		    	 x.style.display = "none";
		    	 if(state){
		    		 $('#message').append('<p>Approver Activated <p>') 
		    	 }else{
		    		 $('#message').append('<p>Approver Deactivate <p>')
		    	 }
				 onsucessNotification.style.display = "block";
			},
			error : function(e) {

				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

	});

})
