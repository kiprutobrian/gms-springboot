/*
 * DataTables - Tables
 */

$(document).ready(function() {
	
	
	
	var table = $('#signatureuserlist').DataTable({
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
	


					$("#updateSelected").click(function() {
						if (table.rows('.selected').data().length == 0) {
							alert('NO ACCOUNT IS SELECTED');
						} else {
							$('.modal').modal();
							$('#modal1').modal('open');

						}
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
							style : 'single',
							selector : 'td:nth-child(3)' // this line is the
															// most importan!
						},
						order : [ [ 1, 'asc' ] ]
					});

			$(".idform").click(function() {
					var $item = $(this).closest("tr").find(".id").text();										
					var orgUser = [];
					
					for (var i = 0; i < table.rows('.selected').data().length; i++) {
						var id = JSON.stringify(table.rows('.selected').data()[i][1]);
						var emailAdresses = JSON.stringify(table.rows('.selected').data()[i][2]);
						orgUser.push({
							"memberId" : id,
							"emailAdress" : emailAdresses,
							"company" : 1
						});
					}
					
					var KeyValue = {
						key : $item,
						orgUser : orgUser
					}
					
					$("#applytemplate").click(function() {
							var token = $("meta[name='_csrf']").attr("content");
							var x = document.getElementById("progress");
						   	var onsucessNotification = document.getElementById("onsuccess");
						   	x.style.display = "block";
						   	
					  	    $('#modal1').modal('close');	
							$.ajax({
								url : "/postSelectedUpdateSignature",
								headers :{
								     "X-XSRF-TOKEN" : token,
								},
								data : JSON.stringify(KeyValue),
								type : "POST",
								contentType : "application/json",
								dataType : 'json',
								success : function(result) {
									x.style.display = "none";
									 $('#message').append('<p>'+orgUser.length+' Account Signature Updated Sucessfully<p>')
									 onsucessNotification.style.display = "block";	  
								},
								error : function(e) {																			
								}
						});
					});
			});
		})
				
