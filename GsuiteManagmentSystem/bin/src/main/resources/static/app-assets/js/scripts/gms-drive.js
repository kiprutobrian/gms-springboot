/*
 * DataTables - Tables
 */

$(function() {
	$('.modal').modal();
	

	var tablesharedbyme = $('#sharedbymeexample').DataTable({
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
	

	
	var tablepermisions = $('#sharedwithmefolderfiles').DataTable({
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
			style : 'single',
			selector : 'td:nth-child(1)',
		},
		order : [ [ 1, 'asc' ] ]
	});
	
	var tablesahredwithme = $('#example').DataTable({
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
			style : 'single',
			selector : 'td:nth-child(1)',
		},
		order : [ [ 1, 'asc' ] ]
	});
	
	var tableMydrivefiles = $('#mydrivefiles').DataTable({
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
			style : 'single',
			selector : 'td:nth-child(1)',
		},
		order : [ [ 1, 'asc' ] ]
	});
	

	$('#bt_shreadbyme').click(
			function() {

				var x = document.getElementById("progress");
			   	var onsucessNotification = document.getElementById("onsuccess");
			   	x.style.display = "block";
		    	 
				var token = $("meta[name='_csrf']").attr("content");
				var selectedUserEmail = $('#selectedUserEmail').val();

				
				var data = [];
				var files = [];

				if (tablesharedbyme.rows('.selected').data().length == 0) {

				} else {

					for (var i = 0; i < tablesharedbyme.rows('.selected')
							.data().length; i++) {
						files
								.push({
									"Id" : null,
									"fileId" : tablesharedbyme
											.rows('.selected').data()[i][1],
									"fileName" : tablesharedbyme.rows(
											'.selected').data()[i][2]
								});
					}
				}
				var driveWorkFlow = {
					Id : null,
					name : selectedUserEmail,
					key : selectedUserEmail,
					owner : true,
					urlType : null,
					token : null,
					Date : null,
					executed : false,
					gmsFiles : files

				}

				$.ajax({
					url : "/RevockFileShareWithMe",
					headers : {
						"X-XSRF-TOKEN" : token,
					},
					data : JSON.stringify(driveWorkFlow),
					type : "POST",
					contentType : "application/json",
					dataType : 'json',
					success : function(result) {
						x.style.display = "none";
						 $('#message').append('<p>'+orgUser.length+' Account Slot Updated Sucessfully<p>')
						 onsucessNotification.style.display = "block";
				  	    
					},
					error : function(e) {

						alert("Error!")
						console.log("ERROR: ", e);
					}
				});

			});

	$('#btn_sharedwithme').click(
			function() {
			
				var token = $("meta[name='_csrf']").attr("content");
				var selectedUserEmail = $('#selectedUserEmail').val();
				var data = [];
				var files = [];

				if (tablesahredwithme.rows('.selected').data().length == 0) {

				} else {
					
					var x = document.getElementById("progress");
				   	var onsucessNotification = document.getElementById("onsuccess");
				   	x.style.display = "block";
				   	
				   
			    	 
					for (var i = 0; i < tablesahredwithme.rows('.selected')
							.data().length; i++) {
						files.push({
							"Id" : null,
							"fileId" : tablesahredwithme.rows('.selected')
									.data()[i][1],
							"fileName" : tablesahredwithme.rows('.selected')
									.data()[i][3],
							"permisionId" : tablesahredwithme.rows('.selected')
									.data()[i][2],

						});

					}

					var driveWorkFlow = {
						Id : null,
						name : selectedUserEmail,
						key : selectedUserEmail,
						owner : false,
						urlType : null,
						token : null,
						Date : null,
						executed : false,
						gmsFiles : files

					}
					

					$.ajax({
						url : "/RevockFileShareWithMe",
						headers : {
							"X-XSRF-TOKEN" : token,
						},
						data : JSON.stringify(driveWorkFlow),
						type : "POST",
						contentType : "application/json",
						dataType : 'json',
						success : function(result) {
							x.style.display = "none";
							 $('#message').append('<p>'+selectedUserEmail+' Account Slot Updated Sucessfully<p>')
							 onsucessNotification.style.display = "block";
					  	    
						},
						error : function(e) {
						}
					});
				}

			});

	$(".idform").click(function() {
		var $item = $(this).closest("tr").find(".id").text(); // Retrieves the
		
		var x = document.getElementById("progress");
	   	var onsucessNotification = document.getElementById("onsuccess");
	   	x.style.display = "block";
	   	
	   	 

		var token = $("meta[name='_csrf']").attr("content");
		var selectedUserId = $('#selectedUserId').val();
		var fileId = $('#fileId').val();

		console.log("-" + $item);
		console.log(selectedUserId + " ::::::" + fileId);
		var data = {
			id : $item,
			email : selectedUserId,
			fileId : fileId
		}

		$.ajax({
			url : "/postRevockAccess",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(data),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				x.style.display = "none";
				var url="/DrivePermissions/"+selectedUserId+"/";
				window.location.replace(url);
			},
			error : function(e) {
			}
		});
	});
	
	
	
	
	
	
	
	
	$("#perm_update_access").click(function() {
			var token = $("meta[name='_csrf']").attr("content");
			var selectedUserId = $('#perm_owner').val();
			var fileId = $('#fileId').val();
			var role=$('#perm_role').val();
			var type=$('#perm_type').val();
			var newEmailAdress= $('#perm_users').val();

			$("#perm_file_id").val(fileId);
			$("#perm_owner").val(selectedUserId);
		
			
			var data = {
				id : null,
				email : selectedUserId,
				fileId : fileId,
				role:role,
				type:type,
				newEmailAdress:newEmailAdress
			}
			
			
			console.log(data);

			$.ajax({
				url : "/postChangePermisionOwnerShip",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(data),
				type : "POST",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					x.style.display = "none";
					 $('#message').append('<p> Account File/Folder Updated Sucessfully<p>')
					 onsucessNotification.style.display = "block";
			    	
				},
				error : function(e) {
				}
			});
		
	});
	
	$(".idchange").click(function() {
	
		$('#updatacces').modal('open');
		var selectedUserId = $('#selectedUserId').val();
		var fileId = $('#fileId').val();
		
		$("#perm_file_id").val(fileId);
//		$("#perm_owner").val(selectedUserId);
	
	});
	
	
	

});
