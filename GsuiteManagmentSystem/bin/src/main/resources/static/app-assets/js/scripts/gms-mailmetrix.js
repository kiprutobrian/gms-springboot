/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");
	
	
	var table = $('#accountmetrixlist').DataTable({
		bPaginate : true,
		bLengthChange : false,
		bFilter : true,
		bInfo : false,
		bAutoWidth : false,
		order : [ [ 1, 'asc' ] ]
	});
	
//	
	 google.charts.load('current', {'packages':['bar']});
     google.charts.setOnLoadCallback(drawChart);

     function drawChart() {
       var data = google.visualization.arrayToDataTable([
         ['Period', 'Total Messages', 'Response Time (Hours)'],
         ['2020', 1000, 24],
         ['2019', 1000, 24],
         ['2018', 1000, 24],
         ['2017', 1000, 24]
       ]);

       var options = {
         chart: {
           title: 'Company Performance',
           subtitle: 'Sales, Expenses, and Profit: 2014-2017',
         },
         bars: 'horizontal' // Required for Material Bar Charts.
       };

       var chart = new google.charts.Bar(document.getElementById('barchart_material'));

       chart.draw(data, google.charts.Bar.convertOptions(options));
     }
     
     
     
 	function drawStuff() {
		$.ajax({
			url : "/GetMetrixData",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			type : "GET",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
			
				 var data = new google.visualization.DataTable();
				
				 data.addColumn('string', 'Period'); // Implicit domain label
														// col.
			     data.addColumn('number', 'Total Messages'); // Implicit
																// series 1 data
																// col.
			     data.addColumn('number', 'Response Time (Hours)'); // Implicit
																	// series 1
																	// data col.
			        	
				var itemdata = [];
				
				var items = [];
				var newRows = "";
				for(var a=0;a<result.length;a++){
				
					var time=0;
					if(result[a].responseTime>60){
						time=Math.round(result[a].responseTime/60);
					}
							
					data.addRow( [''+result[a].month+'.'+result[a].year, result[a].messagessize, time],);
				}
				
				 var options = {
				         chart: {
				           title: 'Mail Response Performance',
				           subtitle: 'Period, Messegaes, and Response Time:',
				         },
				         bars: 'horizontal' // Required for Material Bar Charts.
				       };

				 var chart = new google.charts.Bar(document.getElementById('barchart_material'));
				 chart.draw(data, google.charts.Bar.convertOptions(options));
			    
			},
			error : function(e) {
			}
		});
	};
	
	
	 google.charts.load('current', {'packages':['bar']}); 
	 google.charts.setOnLoadCallback(drawStuff);   
	 

	
})
