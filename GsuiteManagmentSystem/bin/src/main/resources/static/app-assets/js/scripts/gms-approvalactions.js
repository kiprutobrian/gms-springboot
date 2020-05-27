/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var url = $("#approvalurl").val();

	$("#Approve").click(function() {
		var x = document.getElementById("progress");
		var onsucessNotification = document.getElementById("onsuccess");
		x.style.display = "block";

		var comments = "APPROVED";
		$.ajax({
			url : url,
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(comments),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				x.style.display = "none";
				$('#message').append('<p>Action Approved succesfully<p>')
				onsucessNotification.style.display = "block";
			},
			error : function(e) {
			}
		});
	});

	$("#revock").click(function() {
		var onsucessNotification = document.getElementById("onsuccess");
		$('#message').append('<p>Action Revocked Succesfully<p>')
		onsucessNotification.style.display = "block";
	});

});

//
// $.ajax({
// url : "/userRegistrations",
// headers : {
// "X-XSRF-TOKEN" : token,
// },
// data : JSON.stringify(myuser),
// type : "POST",
// contentType : "application/json",
// dataType : 'json',
// success : function(result) {
// x.style.display = "none";
// $('#message').append('<p>'+emailName+' Account Slot Updated Sucessfully<p>')
// onsucessNotification.style.display = "block";
//		  	  
// },
// error : function(e) {
// }
// });
//
// });

// })
