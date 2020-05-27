/*
 * DataTables - Tables
 */
/*<![CDATA[*/
$(document).ready(function() {
	 var token = $("meta[name='_csrf']").attr("content");
     
	google.charts.load('current', {'packages':['corechart', 'controls','line','table','bar']}); 
	var data;
	  
	Date.prototype.toDateInputValue = (function() {
	    var local = new Date(this);
	    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
	    return local.toJSON().slice(0,10);
	});

	var token = $("meta[name='_csrf']").attr("content");
	var company = $('#authuser_company_id').val()
	var d = new Date();
	
	d.setDate(d.getDate() - 1);
	$('#startdate').val(d.toDateInputValue());
	$('#enddate').val(new Date().toDateInputValue());
	
	$("#filteredReport").click(function() {
		var to = $('#enddate').val()
		var from = $('#startdate').val();
		var keyword = $('#keyword').val();
		var keyword=$('#type_event :selected').val();
		
		var filter={
			company:company,
			userAccount:"",
			ipAddress:"",
			keyword:keyword,
			startingDate:from,
			endingDate:to
		};
		google.charts.setOnLoadCallback(drawTable(filter));
	});
	
    function drawTable(filter) { 	
    
    	var myUrl = "/GetDLPDriveReport";
		$.ajax({
			url : myUrl,
	    headers : {
			"X-XSRF-TOKEN" : token,
		},
		data : JSON.stringify(filter),
	    method: "POST",
		contentType : "application/json",
		dataType : 'json',
		success : function(result) {	
		data = new google.visualization.DataTable();
		data.addColumn('string', "itemName");
		data.addColumn('string', "user");
		data.addColumn('string', "date");
		data.addColumn('string', "eventName");
		data.addColumn('string', "itemType");
		data.addColumn('string', "owner");
		data.addColumn('string', "visibility");
		data.addColumn('string', "iPAddress");
		data.addColumn('string', "lastViewIp");
		data.addColumn('string', "viewDate");
	
		console.log(result);
		var reportData=result.data;
		for(var a=0;a<reportData.length;a++){
			 data.addRow([
				 reportData[a].itemName,
				 reportData[a].user,
				 reportData[a].date,
				 reportData[a].eventName,
				 reportData[a].itemType,
				 reportData[a].owner,
				 reportData[a].visibility,
				 reportData[a].iPAddress,
				 reportData[a].lastViewIp,
				 reportData[a].viewDate]);	
		}	
		
		var tableoptions = {
			    allowHtml: true,
			    cssClassNames: {
			      tableCell: 'font-size: 8px;height: 10px;width:100%;'
			    }
			  };
		 var table = new google.visualization.Table(document.getElementById('dashboard_table_drive_report'));
		 table.draw(data, tableoptions); 	 
		},
		error : function(e) {
			alert(e);
		}
	});
  }
	
	
	
	
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
