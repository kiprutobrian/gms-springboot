/*
 * DataTables - Tables
 */

$(document)
		.ready(
				function() {
					var table = $('#marketinguserlist')
							.DataTable(
									{
										bPaginate : false,
										bLengthChange : false,
										bFilter : true,
										bInfo : false,
										bAutoWidth : false,
										columnDefs : [ {
											targets : 0,
											render : function(data, type, row,
													meta) {
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

					$('#marketinguserlist tbody tr').click(function() {
						var table = $('#marketinguserlist').DataTable();
						if ($(this).hasClass('selected')) {
							var chekboxid = $('#' + table.row(this).data()[1]);
							chekboxid.prop('checked', false);
						} else {
							var chekboxid = $('#' + table.row(this).data()[1])
							chekboxid.prop('checked', true);
						}
					});

					$('#selectedAll').on('click',function() {
						if ($(this).is(":checked")) {
							table.rows().select();
							for (var i = 0; i < table.rows('.selected').data().length; i++) {
								var chekboxid = $('#'+ table.rows('.selected').data()[i][1])
								chekboxid.prop('checked', true);
							}
						} else {
							table.rows().select();
							for (var i = 0; i < table.rows('.selected').data().length; i++) {
								var chekboxid = $('#'+ table.rows('.selected').data()[i][1])
								chekboxid.prop('checked', false);
							}
							table.rows().deselect();
						}
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
				
					var $item = $(this).closest("tr").find(".id").text(); // Retrieves the					
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

					marketingTemplate
					var KeyValue = {
						key : $item,
						orgUser : orgUser
					}
					
					$("#applytemplate").click(function() {
						
						var x = document.getElementById("progress");
					   	var onsucessNotification = document.getElementById("onsuccess");
					   	x.style.display = "block";
					   	 
				  	    
				  	    $('#modal1').modal('close');
						var token = $("meta[name='_csrf']").attr("content");
						
						$.ajax({
								url : "/postSelectedUpdateMarketing",
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
								}
						});

					});
				});
})
