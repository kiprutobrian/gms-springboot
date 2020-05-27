/*
 * DataTables - Tables
 */
/*<![CDATA[*/
$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var company = $('#authuser_company_id').val()
	
	
//	
//	$.ajax({
//		url : "/getReportUsingKeyWord/"+company,
//    headers : {
//		"X-XSRF-TOKEN" : token,
//	},
//    method: "GET",
//    contentType: 'application/json'
//	}).done( function(data) {
//		console.log(data);
//		var x=data.data;
//		
//		
//		var $thead = $('#delegatesuserlist').find('thead');
//		var tr = $("<tr>");
//		$thead.append(tr);
//		var columns = [];
//		$.each(x[0], function(name, value) {
//		  var column = {
//		    "data": value,
//		    "title":name
//		  };
//		  columns.push(column);
//		});

//		$('#delegatesuserlist').DataTable({
//		
//		  dataSrc: "",
//		  mDataProp:"",
//		  data: x,
//		  aoColumns: columns
//		});
//		
//	    $('#delegatesuserlist').dataTable( {
//	        "aaData": x,
//	        "columns": [
//	            { "data": "id" },
//	            { "data": "name" },
//	            { "data": "detail_alias" },
//	            { "data": "points" }
//	        ]
//	    })
//	})
	
		
	var table = $('#delegatesuserlist').DataTable({
		bPaginate : false,
		bLengthChange : false,
		bFilter : true,
		bInfo : false,
		bAutoWidth : true,
		processing : true,
		bDestroy : true,
		ajax : {
			url : "/getReportUsingKeyWord/"+company,
			headers : {
				"X-XSRF-TOKEN" : token,
			},
//			data: dataSet,
		},
		columns : [ {
			title:"Message Titile",
			data : "messageId"
		}, {
			title:"Sent On",
			data : "dateSent"
		}, {
			title:"Sent From",
			data : "from"
		}, {
			title:"Sent To",
			data : "to"
		}, {
			title:"Subject",
			data : "subject"
		}, {
			title:"Has Shared Attachment",
			data : "hasDriveAttachment"
		}, {
			title:"Has Attachment",
			data : "hasAttachment"
		}, {
			title:"Download Attachment",
			data : "resourceurl"
		}, {
			title:"Message Snippet",
			data : "snippet"
		} ]
	});

	/***************************************************************************
	 * SEARCH EMAILS WITH MY KEYWORD FROM AND TO DATE
	 **************************************************************************/

	$(".datepicker").datepicker({
		autoclose : true,
		format : "yyyy-mm-dd"
	});

	$("#createReport").click(function() {
		var to = $('#todate').val()
		var from = $('#fromdate').val();
		var keyword = $('#keyword').val();
		console.log(from + " : " + to);
		
		var myUrl = /GetDLPDriveReport";
		var table = $('#delegatesuserlist').DataTable({
			bPaginate : false,
			bLengthChange : false,
			bFilter : true,
			bInfo : false,
			bAutoWidth : true,
			processing : true,
			bDestroy : true,
			ajax : {
				url : myUrl,
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				dataSrc : ''
			},
			columns : [ {
				data : "messageId"
			}, {
				data : "dateSent"
			}, {
				data : "from"
			}, {
				data : "to"
			}, {
				data : "subject"
			}, {
				data : "hasDriveAttachment"
			}, {
				data : "hasAttachment"
			}, {
				data : "resourceurl"
			}, {
				data : "snippet"
			} ]
		});

		console.log("" + to + " :  " + from);

	});
	var token = $("meta[name='_csrf']").attr("content");
	
	$("#download").click(function() {
		var table = $('#delegatesuserlist').DataTable();
		var data = table.rows().data();
		
		var datatosend=[];
		
		for(var x=0;x<data.length;x++){	
			datatosend.push(data[x]);
		}
		console.log(datatosend);
		var x = document.getElementById("progress");
		var onsucessNotification = document.getElementById("onsuccess");
		x.style.display = "block";
			$.ajax({
				url : "/DownloadReport",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(datatosend),
				type : "POST",
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
