/*
 * DataTables - Tables
 */

$(document).ready(function() {
					
	
	var table = $('#delegatesuserlist').DataTable({
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
	

					
/****************************************************************************************************************************************************************************************************************
 * 															END OF DATATABLE
****************************************************************************************************************************************************************************************************************/

					$("#updateSelected").click(
						function() {
							if (table.rows('.selected').data().length == 0) {
								alert('NO ACCOUNT IS SELECTED');
							} else {
								$('.modal').modal();
								$('#multydelegate').modal('open');
								$('#message').val(""+ table.rows(".selected").data().length+ " Accounts Selected for delegation");
								var result = table.rows(".selected").data();
								var output = [];
								for (var i = 0; i < result.length; i++) {
									var item = '<li > <div class="col s9 user-content"><p class="line-height-0">'+ table.rows(".selected").data()[i][2]+ '</p><hr></div></li>';
									output.push(item);
								}
								$('#selected_list').empty().append(output);
							}
						});

/****************************************************************************************************************************************************************************************************************
 * 															MULTY ACCOUNT DELEGATION
****************************************************************************************************************************************************************************************************************/
					
					$('#delegate_all').click(
							function() {
								
								$('#multydelegate').modal('close');
								var token = $("meta[name='_csrf']").attr("content");
								var delegate_name = $('#delegate_name').val();
								var orgUser = [];
								for (var i = 0; i < table.rows('.selected')
										.data().length; i++) {
									orgUser.push({
										"memberId" : table.rows('.selected')
												.data()[i][1],
										"emailAdress" : table.rows('.selected')
												.data()[i][2],
										"company" : 1
									});
								}
								var KeyValue = {
									key : delegate_name,
									orgUser : orgUser
								}
								   
								 var x = document.getElementById("progress");
						    	 var onsucessNotification = document.getElementById("onsuccess");
						    	 x.style.display = "block";
						    	  
					    	  
					    	  $.ajax({
									url : "/postAccountDelegation",
									headers : {
										"X-XSRF-TOKEN" : token,
									},
									data : JSON.stringify(KeyValue),
									type : "POST",
									contentType : "application/json",
									dataType : 'json',
									success : function(result) {
										 x.style.display = "none";
										 $('#message').append('<p>'+orgUser.length+' Account Slot Updated Sucessfully<p>')
										 onsucessNotification.style.display = "block";
										
									},
									error : function(e) {
										console.log("ERROR: ", e);
									}
								});								    	
								
							});

					var rowDatas = [];

					
/****************************************************************************************************************************************************************************************************************
 * 															VIEW SINGLE ACCOUNT DELEGATES
 ****************************************************************************************************************************************************************************************************************/
		
					$('.viewselect').click(
									function() {
										var table = $('#delegatesuserlist').DataTable();
										var email = table.rows(".selected").data()[0][2];
										var username = table.rows(".selected").data()[0][3];
										var id = table.rows(".selected").data()[0][1];
										var token = $("meta[name='_csrf']").attr("content");
										var delegate;										
										$('#email_md').val(email);
										$('#name_md').val(username);
										$('#selected_id').val(id);
										var x = document.getElementById("progress");
										 x.style.display = "block";
									    	
							    	  $.ajax({
											url : '/DelegateAccount',
											headers : {
												"X-XSRF-TOKEN" : token,
											},
											data : email,
											type : "POST",
											contentType : "application/json",
											dataType : 'json',
											success : function(result) {
												delegate = result;
												var output = [];
												if (result.length >= 1) {
												for (var i = 0; i < result.length; i++) {
													
													if(result[i].delegateEmail=="empty"){
													}else{
														var item = '<li"><div class="col s9 user-content"><p class="line-height-0">'+ result[i].delegateEmail+ '</p></div><div class="col s2"></div> <div class="col s2 delete-task right"><a class="material-icons" href="/RemoveDelegateAccount/'+email+'/'+result[i].delegateEmail+'/">delete</a></div><br><hr></li>'
														output.push(item);
													}
													
													}
												$('#list').empty().append(output);
												}
												$('.modal').modal();
												 x.style.display = "none";
												    
												$('#modal1').modal('open');

											},
											error : function(e) {
												console.log("ERROR: ",e);
											}
										});
							    	  
							    	 
							    	  $('#delegatebutton').click(function() {
							    		  
							    		$('#modal1').modal('close');
										var token = $("meta[name='_csrf']").attr("content");
										var delegate_name = $('#deleate_input').val();
										var selectedemail = $('#email_md').val();
										var selectedeId = $('#selected_id').val();										
										var orgUser = [];
										orgUser.push({
											"memberId" : selectedeId,
											"emailAdress" : selectedemail ,
											"company" : 1
										});
										
										var KeyValue = {
												key : delegate_name,
												orgUser : orgUser
										}
										
										 var x = document.getElementById("progress");
								    	 var onsucessNotification = document.getElementById("onsuccess");
								    	 x.style.display = "block";
								    	
										  $.ajax({
												url : "/postAccountDelegation",
												headers : {
													"X-XSRF-TOKEN" : token,
												},
												data : JSON.stringify(KeyValue),
												type : "POST",
												contentType : "application/json",
												dataType : 'json',
												success : function(result) {
													x.style.display = "none";
													onsucessNotification.style.display = "block";
													 $('#message').append('<p>'+orgUser.length+' Account Slot Updated Sucessfully<p>')
														
													console.log(result);
												},
												error : function(e) {
													console.log("ERROR: ", e);
												}
											});
													
									});
					});
		})
