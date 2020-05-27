/*
 * DataTables - Tables
 */

$(document).ready(function() {
	
	
	
	
	var table = $('#marketinguserlist').DataTable({
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
	
	
	
	
	var tablelables = $('#lableslist').DataTable({
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
	
	var folderslist = $('#folderslist').DataTable({
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
	
	var marketingTemplate = $('#template_table').dataTable({
		searching : false,
		paging : false,
		info : false,
		select : true,
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox',
			targets : 2
		} ],
		select : {
			style : 'multi',
			selector : 'td:nth-child(3)' // this line is the
		// most importan!
		},
		order : [ [ 1, 'asc' ] ]
	});			
				

	$("#updateSelected").click(function() {
		if (table.rows('.selected').data().length == 0) {
			alert('NO ACCOUNT IS SELECTED');
		} else {
			$('.modal').modal();
			$('#modal1').modal('open');
		}
	});
	
	
	$('#refreshdata').click(function () {
		var number=table.rows('.selected').data().length;	
		$("#selecedaccounts").html('');
		$("#selecedaccounts").html('<span class="m-0">'+(number)+'</span>');
	});
	
	$('#marketinguserlist').click(function () {
		var number=table.rows('.selected').data().length;	
		$("#selecedaccounts").html('');
		$("#selecedaccounts").html('<span class="m-0">'+(number)+'</span>');
	});

			

/*======================================================================================================================================================================================================
 * 
 * 																DATA BACKUP START HERE
 * 
 ======================================================================================================================================================================================================*/
					 
		$("#backupemails").click(function() {
					var x = document.getElementById("progress");
				   	var onsucessNotification = document.getElementById("onsuccess");
				   	x.style.display = "block";
				   	
				   	var email=$("#email").is(":checked");
					var drive=$("#drive").is(":checked");
				   	var backupaccount=$("#backupaccount").val();
				   	
					var DataMigrationAccount = [];			
					for (var i = 0; i < table.rows('.selected').data().length; i++) {
					
						var id = JSON.stringify(table.rows('.selected').data()[i][1]);
						var emailAdresses = JSON.stringify(table.rows('.selected').data()[i][2]);
						
						DataMigrationAccount.push({
							"accountId" : id,
							"emailAddress" : emailAdresses,
							"email" : email,
							"drive" : drive,
							"backupaccount" : backupaccount
						});
					}
									
					var token = $("meta[name='_csrf']").attr("content");

					$.ajax({
						url : "/BackupEmailData",
						headers : {
							"X-XSRF-TOKEN" : token,
						},
						data : JSON.stringify(DataMigrationAccount),
						type : "POST",
						contentType : "application/json",
						dataType : 'json',
						success : function(result) {
							x.style.display = "none";
							$('#message').append('<p>'+ DataMigrationAccount.length+ ' Account DatabackedUp  Sucessfully<p>')
							onsucessNotification.style.display = "block";
						},
						error : function(e) {
						}
					});

				});
		
/*======================================================================================================================================================================================================
 * 
 * 																DATA RESTORATION START HERE
 * 
 ======================================================================================================================================================================================================*/
		
		$("#restoreemail").click(function() {
			
			var x = document.getElementById("progress");
		   	var onsucessNotification = document.getElementById("onsuccess");
		   	x.style.display = "block";
		   	
			var DataRestore = [];			
			var labelId = JSON.stringify(tablelables.rows('.selected').data()[0][1]).replace(/^"(.*)"$/, '$1');;
			var accountEmail = $('#äccount').find(":selected").text();
			
			DataRestore.push({
				"lableId" : labelId,
				"account" : accountEmail
			});
						
			var token = $("meta[name='_csrf']").attr("content");
		
			$.ajax({
				url : "/RestoreEmailData",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(DataRestore),
				type : "POST",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					x.style.display = "none";
					$('#message').append('<p>'+ DataRestore.length+ 'Account Data Restored  Sucessfully<p>')
					onsucessNotification.style.display = "block";
				},
				error : function(e) {
				}
			});

		});
		
		
	})
