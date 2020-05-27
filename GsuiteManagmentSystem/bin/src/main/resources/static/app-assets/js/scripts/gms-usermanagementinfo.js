$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	$('.modal').modal();
	
	var aliasesTable = $('#aliases_tb').DataTable(
			{
				bPaginate : false,
				bLengthChange : false,
				bFilter : false,
				bInfo : false,
				bAutoWidth : false
			});
	
	var delegatesTable = $('#delegate_tb').DataTable(
			{
				bPaginate : false,
				bLengthChange : false,
				bFilter : false,
				bInfo : false,
				bAutoWidth : false
			});
	
	
	var selected="";
	
	
	 
	  
	 var alias_div = document.getElementById("alias_div");
	 var delegate_div = document.getElementById("delegate_div");
	  
	 $("#addalias").click(function() {
			$('#adding_model').modal('open');
			delegate_div.style.display = "none";
			 alias_div.style.display = "block";
			 
			 $('#headermessage').html('');
			 $('#headermessage').html('<p>Add aliases To the This Account</p>');
			 selected="alias"
	 });
	 
	 $("#addDelegate").click(function() {
			$('#adding_model').modal('open');
			 delegate_div.style.display = "block";
			 alias_div.style.display = "none";
			
			 selected="delegate";
			 $('#headermessage').html('');
			 $('#headermessage').html('<p>Selecte a Delegate To Delegate this Account</p>');
				
	 });
	 
	 $("#add_acount").click(function() {

		 if(selected =='alias'){
			 var x = document.getElementById("progress");
			   	var onsucessNotification = document.getElementById("onsuccess");
			   	x.style.display = "block";
			   	
			 var accountaddress= $('#accountemailaddress').val();
			 var aliasemail= $('#alias_address').val();
			 
			 var Appointments = {
						id : accountaddress,
						email : aliasemail,
						appointmentSlot : false			
					}
			  
				$.ajax({
					url : "/postCreateAlias",
					headers : {
						"X-XSRF-TOKEN" : token,
					},
					data : JSON.stringify(Appointments),
					type : "POST",
					contentType : "application/json",
					dataType : 'json',
					success : function(result) {
						 x.style.display = "none";
						 $('#message').append('<p>Account <span style="color:blue;">'+aliasemail+'</span> Sucessfuly Added as an Alias <p>')
						 onsucessNotification.style.display = "block";
					},
					error : function(e) {

					}
				});
			 
			 
		 }else if(selected =='delegate'){
			  
		 }
		 
	 });
	 
	 $('#delegate_tb tbody').on( 'click', '.delegatedelete', function () {
	        var data = delegatesTable.row( $(this).parents('tr') ).data();	        
	        console.log(data[0]);
	    });
	 
	 $("#resetpass").click(function() {
			var familyname = $("#familyname").val();
			var givenName = $("#givenName").val();
			var emailName = $("#emailName").val();
			var domain = $("#domain").val();
			var jobtitle = $("#jobtitle").val();
			var phonenumber = $("#phonenumber").val();
			var accountId = $("#accountId").val();
			var fullnames = givenName + " " + familyname;
			var passsword=$("#newpassword_reset").val();

			console.log(token);
			console.log(familyname);
			console.log(givenName);
			console.log(emailName);
			console.log(domain);
			console.log(jobtitle);
			console.log(phonenumber);
			
			
			var updateDirectory = {
				id : accountId,
				givenName : givenName,
				fullname : fullnames,
				familyname : familyname,
				emailAdress : emailName,
				imageUrl : null,
				createdById : null,
				passsword : passsword
			}
			var x = document.getElementById("progress");
		   	var onsucessNotification = document.getElementById("onsuccess");
		   	x.style.display = "block";
		   	
			$.ajax({
				url : "/ResetUserPassword",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(updateDirectory),
				type : "POST",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					 x.style.display = "none";
					 $('#message').append('<p>Account <span style="color:blue;">'+familyname+""+givenName+'</span> Password Rest Sucessfuly and new password sent to'+phonenumber+'<p>')
					 onsucessNotification.style.display = "block";
				},
				error : function(e) {

				}
			});
			
		 
	 });
	 
	 
	 
	 
//	 $('#delegate_tb tbody').on( 'click', '.delegatedelete', function () {
//	        var data = delegatesTable.row( $(this).parents('tr') ).data();	        
//	        console.log(data[0]);
//	    });
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	$("#update_account").click(function() {
		
		var familyname = $("#familyname").val();
		var givenName = $("#givenName").val();
		var emailName = $("#emailName").val();
		var domain = $("#domain").val();
		var jobtitle = $("#jobtitle").val();
		var phonenumber = $("#phonenumber").val();
		var accountId = $("#accountId").val();
		var fullnames = givenName + " " + familyname;

		console.log(token);
		console.log(familyname);
		console.log(givenName);
		console.log(emailName);
		console.log(domain);
		console.log(jobtitle);
		console.log(phonenumber);
		
		
		var updateDirectory = {
				id : accountId,
				givenName : givenName,
				fullname : fullnames,
				familyname : familyname,
				emailAdress : emailName,
				imageUrl : null,
				createdById : null
			}

			var x = document.getElementById("progress");
		   	var onsucessNotification = document.getElementById("onsuccess");
		   	x.style.display = "block";
		   	
			$.ajax({
				url : "/processUpdateForm",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(updateDirectory),
				type : "POST",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					 x.style.display = "none";
					 $('#message').append('<p>Account <span style="color:blue;">'+fullnames+'</span> Sucessfuly Updated <p>')
					 onsucessNotification.style.display = "block";
				},
				error : function(e) {

				}
			});
	});
	
});
