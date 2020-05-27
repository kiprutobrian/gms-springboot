/*
 * DataTables - Tables
 */

$(document).ready(function() {
			var table = $('#portalusers').DataTable({
										bPaginate : false,
										bLengthChange : false,
										bFilter : true,
										bInfo : false,
										bAutoWidth : false,
										columnDefs : [ {
											targets : 0,
											render : function(data, type, row,meta) {
												if (type === 'display') {
													return '<div class="checkbox"><label><input type="checkbox" id="'
															+ row[1]
															+ '" class="filled-in" style="width: 50px;"><span>.</span></label></div>';
												} else {
													return '<div class="checkbox"><label><input type="checkbox" id="'
															+ row[1]
															+ '" class="filled-in" style="width: 50px;" checked="checked"><span>.</span></label></div>';
												}
											},
											checkboxes : {
												selectRow : true,
												selectAllRender : '<div class="checkbox"><label><input id="selectedAll" type="checkbox" class="filled-in" style="width: 50px;"><span>.</span></label></div>'
											}
										} ],
										select : {
											style : 'multi',
										},
										order : [ [ 1, 'asc' ] ]
									});

					$('#portalusers tbody tr').click(function() {
						var table = $('#portalusers').DataTable();
						if ($(this).hasClass('selected')) {
							var chekboxid = $('#' + table.row(this).data()[1]);
							chekboxid.prop('checked', false);
						} else {
							var chekboxid = $('#' + table.row(this).data()[1])
							chekboxid.prop('checked', true);
						}
					});

					$('#selectedAll').on('click',
							function() {
								if ($(this).is(":checked")) {
									table.rows().select();
									for (var i = 0; i < table.rows('.selected')
									.data().length; i++) {
										var chekboxid = $('#' + table.rows('.selected').data()[i][1])
										chekboxid.prop('checked', true);
									}									
								} else {
									table.rows().select();
									for (var i = 0; i < table.rows('.selected').data().length; i++) {
										var chekboxid = $('#' + table.rows('.selected').data()[i][1])
										chekboxid.prop('checked', false);
									}
									table.rows().deselect();
								}
							});

/***********************************************************************************************************************************************************************************************
 * 										END OF DATATABLES
 ***********************************************************************************************************************************************************************************************/					

					
					
					
/***********************************************************************************************************************************************************************************************
 * 										GMS SUSPEND USER ACCOUNT
***********************************************************************************************************************************************************************************************/					
					
					$('#gmsusersuspend').click(
							function() {
								var token = $("meta[name='_csrf']").attr("content");
								var delegate_name = $('#delegate_name').val();
								
								
						  	  var x = document.getElementById("progress");
							   	var onsucessNotification = document.getElementById("onsuccess");
							   	x.style.display = "block";
							   
						  	    
						  	    
						  	    
						  	    var userApp = [];
								for (var i = 0; i < table.rows('.selected')
										.data().length; i++) {
									userApp.push({id : table.rows('.selected').data()[i][1],
												email : table.rows('.selected').data()[i][2],
												appointmentSlot : false
											});
								}
								
								$.ajax({
									url : "/SuspendGMSAccount",
									headers : {
										"X-XSRF-TOKEN" : token,
									},
									data : JSON.stringify(userApp),
									type : "POST",
									contentType : "application/json",
									dataType : 'json',
									success : function(result) {
										x.style.display = "none";
										 $('#message').append('<p>'+userApp.length+' Account Slot Updated Sucessfully<p>')
										 onsucessNotification.style.display = "block";
								  	   
									},
									error : function(e) {

										alert("Error!")
										console.log("ERROR: ", e);
									}
								});

							});

/***********************************************************************************************************************************************************************************************
 * 										GMS UNSUSPEND USER ACCOUNT
***********************************************************************************************************************************************************************************************/									
					$('#gmsusersunsuspend').click(
							function() {
								var token = $("meta[name='_csrf']").attr("content");
								var delegate_name = $('#delegate_name').val();								
								var x = document.getElementById("progress");
							   	var onsucessNotification = document.getElementById("onsuccess");
							   	x.style.display = "block";
							   
								var userApp = [];
								for (var i = 0; i < table.rows('.selected')
										.data().length; i++) {
									userApp.push({
												id : table.rows('.selected').data()[i][1],
												email : table.rows('.selected').data()[i][2],
												appointmentSlot : true
											});
								}
								$.ajax({
									url : "/UnsuspendGMSAccount",
									headers : {
										"X-XSRF-TOKEN" : token,
									},
									data : JSON.stringify(userApp),
									type : "POST",
									contentType : "application/json",
									dataType : 'json',
									success : function(result) {
										x.style.display = "none";
										 $('#message').append('<p>'+userApp.length+' Account Slot Updated Sucessfully<p>')
										 onsucessNotification.style.display = "block"; 
									},
									error : function(e) {
										console.log("ERROR: ", e);
									}
								});

							});
	})
