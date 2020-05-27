/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var base64P12String = "";
	var base64JSONString = "";

	$('#jsonfile').change(function(e) {
		var myJSON = JSON.stringify(e.target.files[0]);
		var isupdated = false;
		var file = event.srcElement.files[0];
		var reader = new FileReader();
		reader.readAsBinaryString(file);
		reader.onload = function() {
			base64JSONString = btoa(reader.result);
			isupdated = true;
			var fileName = e.target.files[0].name;
			const lastDot = fileName.lastIndexOf('.');
			const fileExtension = fileName.substring(lastDot + 1);
			var fileSize = e.target.files[0].size;
			var filePath = e.target.files[0].path;
			// extension,path,size
			
		};
		reader.onerror = function() {
			console.log('there are some problems');
		};
	});
	
	
	$('#p12file').change(function(e) {
		var myJSON = JSON.stringify(e.target.files[0]);
		var isupdated = false;
		var file = event.srcElement.files[0];
		var reader = new FileReader();
		reader.readAsBinaryString(file);
		reader.onload = function() {
			base64P12String = btoa(reader.result);
			isupdated = true;
			var fileName = e.target.files[0].name;
			const lastDot = fileName.lastIndexOf('.');
			const fileExtension = fileName.substring(lastDot + 1);
			var fileSize = e.target.files[0].size;
			var filePath = e.target.files[0].path;
			// extension,path,size
		};
		reader.onerror = function() {
			console.log('there are some problems');
		};
	});
	
	

	$("#onsubmit").click(function() {

		var packageId = $('#package').find(":selected").val();
		var compId = $('#companyId').val();
		var userId = $('#userId').val();
		var domain = $('#domain').val();
		
		console.log('USERID' + userId);
		console.log('COMPANYID' + compId);
		console.log('PACKAGE' + packageId);
		console.log('DOMAIN' + domain);

		
		var RegistrationForm = 
			{	userId:userId,
				packageId:packageId,
				base64JsonFile:base64JSONString,
				base64P12File:base64P12String,
				companyId:compId,
				domain:domain
									}
		
		 $.ajax({
			url : "/postRegestartionForm",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(RegistrationForm),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				if(result.present){
					 swal({
						    title: "Domain Registration Sucessfull",
						    text: ""+result.status,
						    icon: 'success',
						    buttons: {
						      delete: 'Ok'
						    }
						  }).then(function (willDelete) {
							  window.location.replace("/DashBoard");
							});
				}else{	
				 swal({
					    title: "Domain Registration Fail",
					    text: ""+result.status,
					    icon: 'error',
					    buttons: {
					    	cancel: 'Ok'
					    }
					  });
				}
			},
			error : function(e) {

				alert("Error!")
				console.log("ERROR: ", e);
			}
		});

	});


})
