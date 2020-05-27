/*
 * DataTables - Tables
 */
/*<![CDATA[*/
$(document).ready(function() {
	
	
	
	Date.prototype.toDateInputValue = (function() {
	    var local = new Date(this);
	    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
	    return local.toJSON().slice(0,10);
	});

	var token = $("meta[name='_csrf']").attr("content");
	var company = $('#authuser_company_id').val()
	var myUrl = "/getDriveDlpReporting/"+company;
	
	var d = new Date();
	d.setDate(d.getDate() - 1);
	
	$('#startdate').val(d.toDateInputValue());
	$('#enddate').val(new Date().toDateInputValue());
	
	
//	$.ajax({
//		url : myUrl,
//    headers : {
//		"X-XSRF-TOKEN" : token,
//	},
//    method: "GET",
//    contentType: 'application/json'
//	}).done( function(data) {
//		console.log(data);
//		var x=data.data;
//		
//		var $thead = $('#delegatesuserlist').find('thead');
//		var tr = $("<tr>");
//		$thead.append(tr);
//		var columns = [];
////		if(){
//	
//
//		$('#delegatesuserlist').DataTable({
//		
//		  dataSrc: "",
//		  mDataProp:"",
//		  data: x,
//		  columns : [ {
//			  	title:"User",
//				data : "user"
//			}, {
//				title:"Item ID",
//				data : "itemId"
//			}, {
//				title:"Item Type",
//				data : "itemType"
//			}, {
//				title:"Item Name",
//				data : "itemName"
//			}, {
//				title:"Item Owner",
//				data : "owner"
//			}, {
//				title:"Event Name",
//				data : "eventName"
//			}, {
//				title:"Event Date",
//				data : "date"
//			}, {
//				title:"Event IP address",
//				data : "iPAddress"
//			},]
//		});
//
//	});
	
	
	
	
	
	$("#filteredReport").click(function() {
		var to = $('#enddate').val()
		var from = $('#startdate').val();
		
		
		var keyword = $('#keyword').val();
		console.log(token + " : " + to);
		
		var keyword=$('#type_event :selected').val();
		
		var filter={
			company:company,
			userAccount:"",
			ipAddress:"",
			keyword:keyword,
			startingDate:from,
			endingDate:to
		}
		
		
		console.log(filter);
		
		var myUrl = "/GetDLPDriveReport";
		$.ajax({
			url : myUrl,
	    headers : {
			"X-XSRF-TOKEN" : token,
		},
		data : JSON.stringify(filter),
	    method: "POST",
	    contentType: 'application/json'
		}).done( function(data) {
			console.log(data);
			var x=data.data;
			
			var $thead = $('#delegatesuserlist').find('thead');
			var tr = $("<tr>");
			$thead.append(tr);

			$('#delegatesuserlist').DataTable({
			  bDestroy: true,
			  dataSrc: "",
			  mDataProp:"",
			  data: x,
			  columns : [ {
				  	title:"User",
					data : "user"
				}, {
					title:"Item Type",
					data : "itemType"
				}, {
					title:"Item Name",
					data : "itemName"
				}, {
					title:"Item Owner",
					data : "owner"
				}, {
					title:"Event Name",
					data : "eventName"
				}, {
					title:"Event Date",
					data : "date"
				}, {
					title:"Event IP address",
					data : "iPAddress"
				},]
			});
			
//			$('#delegatesuserlist').data.reload();

		});

		

	});
	
	
	
	
	/***************************************************************************
	 * SEARCH EMAILS WITH MY KEYWORD FROM AND TO DATE
	 **************************************************************************/


	$("#createReport").click(function() {
		var x = document.getElementById("progress");
		var onsucessNotification = document.getElementById("onsuccess");
		x.style.display = "block";
			$.ajax({
				url : "/getDriveDlpReporting",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : null,
				type : "GET",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					x.style.display = "none";
					 $('#message').append('<p>Report Sheet saved and Downloaded on ur Google Drive Click The Link Below to view the file <a style="color:blue;" href="'+result.status+'">Sheet Click</a> Template Created Sucessfully<p>')
					 onsucessNotification.style.display = "block";
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
	});
	
	
	
	
})
/* ]]> */
