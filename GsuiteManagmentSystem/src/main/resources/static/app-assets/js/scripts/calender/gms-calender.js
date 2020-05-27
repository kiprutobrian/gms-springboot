/*
 * DataTables - Tables
 */

$(document).ready(function(){
	
			var token = $("meta[name='_csrf']").attr("content");
			
					var table = $('#calenderuserlist').DataTable({
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
											$('#add_div').val(""+ table.rows(".selected").data().length+" Accounts Selected");
											var result = table.rows(".selected").data();
											var output = [];
											for (var i = 0; i < result.length; i++) {
												var item = '<li><div class="col s12 user-content"><p class="line-height-0">'+ table.rows(".selected").data()[i][2]+ '</p></div></li>'
												output.push(item);
											}
											$('#selected_list').empty().append(output);
										}
									});

					$("#removeSelected").click(function() {
										if (table.rows('.selected').data().length == 0) {
											alert('NO ACCOUNT IS SELECTED');
										} else {

											$('.modal').modal();
											$('#modal2').modal('open');

											$('#remove_div').val(""+ table.rows(".selected").data().length+ " Accounts Selected.");
											var result = table
													.rows(".selected").data();
											var output = [];
											for (var i = 0; i < result.length; i++) {
												var item = '<li class="card"> <div class="col s9 user-content"><p class="line-height-0">'
														+ table.rows(".selected").data()[i][2]+ '</p></div></li>'
												output.push(item);
											}
											$('#selected_list_rmv').empty().append(output);
										}
									});

					
					
					
					
/* *************************************************************************************************************************************************************
 * 				**************************   SET CALENDER APPOINTMENT         **********************************************
 * *************************************************************************************************************************************************************/

					$("#addtemplate").click(
							function() {	
								var orgUser = [];
								for (var i = 0; i < table.rows('.selected').data().length; i++) {
									orgUser.push({
										"memberId" : table.rows('.selected').data()[i][1],
										"emailAdress" : table.rows('.selected').data()[i][2],
										"company" : 1
									});
								}
								
					    	  var x = document.getElementById("progress");
					    	  var onsucessNotification = document.getElementById("onsuccess");
					    	  x.style.display = "block";
					    	 
					    	 
					    	  $('#modal1').modal('close');
								$.ajax({
									url : "/updateCalenderSignature",
									headers : {
										"X-XSRF-TOKEN" : token,
									},
									data : JSON.stringify(orgUser),
									type : "POST",
									contentType : "application/json",
									dataType : 'json',
									success : function(result) {
										 x.style.display = "none";
//										 location.reload();
										 $('#message').append('<p>'+orgUser.length+' Account Slot Updated Sucessfully<p>')
										 onsucessNotification.style.display = "block";
									},
									error : function(e) {
										alert("Error!")
									}
								});
								    	  
								      
							});
	
					
					
/* *************************************************************************************************************************************************************
 * 						REMOVING CALENDER APPOINTMENT
 * *************************************************************************************************************************************************************/

					$("#removetemplate").click(
							function() {

								var orgUser = [];
								for (var i = 0; i < table.rows('.selected')
										.data().length; i++) {
									orgUser.push({
										"memberId" : table.rows('.selected').data()[i][1],
										"emailAdress" : table.rows('.selected').data()[i][2],
										"company" : 1
									});
								}
								
								
									 var x = document.getElementById("progress");
							    	 var onsucessNotification = document.getElementById("onsuccess");
							    	 x.style.display = "block";
								    	  
							    	  
							    	  $('#modal2').modal('close');
										$.ajax({
											url : "/removeCalenderAppointment",
											headers : {
												"X-XSRF-TOKEN" : token,
											},
											data : JSON.stringify(orgUser),
											type : "POST",
											contentType : "application/json",
											dataType : 'json',
											success : function(result) {
												 x.style.display = "none";
												 $('#message').append('<p>'+orgUser.length+' Appointment slot sucessfully updated<p>')
												 onsucessNotification.style.display = "block";
												 setTimeout( function(){
													  location.reload();
												    //do something special
												  }, 7000);
											},
											error : function(e) {
												
											}
										});
							  
						   });
					

				})
