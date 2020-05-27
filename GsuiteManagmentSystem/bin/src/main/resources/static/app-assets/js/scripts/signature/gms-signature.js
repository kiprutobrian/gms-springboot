/*
 * DataTables - Tables
 */

$(document).ready(function() {
	$('.modal').modal();
	var token = $("meta[name='_csrf']").attr("content");
	var table = $('#signatureuserlist').DataTable();
	var templateTable = $('#template_table').DataTable();
	
	$('#mymodal').modal({
		  dismissible: false, // Modal can be dismissed by clicking outside of the modal
		  opacity: .5, // Opacity of modal background
		  inDuration: 300, // Transition in duration
		  outDuration: 200, // Transition out duration
		  width:3, // probably not needed
        height:3,
		  startingTop: '4%', // Starting top style attribute
		  endingTop: '10%', // Ending top style attribute
		});
	
	$("#updateSelected").click(function() {
		if (table.rows('.selected').data().length == 0) {
			swal({
				  title: 'Signature Update',
				  icon: 'warning',
				  text: "No account(s) selected for signature update !",
				});
		} else {
			$('.modal').modal();
			$('#modal1').modal('open');
		}
	});

	$("#applysignaturetemplate").click(function() {
		$('#mymodal').modal('open');
		var orgUser = [];
		var templateId=templateTable.rows('.selected').data()[0][0];
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
				key : templateId,
				orgUser : orgUser
			}
		
		$.ajax({ 
			url : "/postSelectedUpdateSignature",
			headers : {
				"X-XSRF-TOKEN" : token,
				},
			data : JSON.stringify(KeyValue),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				
				 swal({
					    title: ""+result.status,
					    text: ""+result.message,
					    icon: 'success',
					    dangerMode: true,
					    buttons: {
					      delete: 'Ok,'
					    }
					  }).then(function (willDelete) {
					    if (willDelete) {
					    	$('#mymodal').modal('open');
					     	location.reload();
					    } 
					  });
			},
			error : function(e) {
			}
		});
		
	});
	
	
	
	

// $(".idform").click(function() {
//					
// var $item = $(this).closest("tr").find(".id").text();
// var orgUser = [];
// for (var i = 0; i < table.rows('.selected').data().length; i++) {
// var id = JSON.stringify(table.rows('.selected').data()[i][1]);
// var emailAdresses = JSON.stringify(table.rows('.selected').data()[i][2]);
// orgUser.push({
// "memberId" : id,
// "emailAdress" : emailAdresses,
// "company" : 1
// });
// }
//
// var KeyValue = {
// key : $item,
// orgUser : orgUser
// }
//
// $("#applytemplate").click(function() {
// var token = $("meta[name='_csrf']").attr("content");
// var x = document.getElementById("progress");
// var onsucessNotification = document.getElementById("onsuccess");
// x.style.display = "block";
//
// $('#modal1').modal('close');
// $.ajax({
// url : "/postSelectedUpdateSignature",
// headers : {
// "X-XSRF-TOKEN" : token,
// },
// data : JSON.stringify(KeyValue),
// type : "POST",
// contentType : "application/json",
// dataType : 'json',
// success : function(result) {
// console.log(result);
// },
// error : function(e) {
// }
// });
// });
// });
	})
