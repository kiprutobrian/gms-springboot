/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var DataLossPreventionReport;
	$('.modal').modal();

	var newrm = $('#products_table').DataTable({
		bPaginate : true,
		bLengthChange : true,
		bFilter : true,
		bInfo : false,
		bAutoWidth : false,
		columnDefs : [ {
			orderable : false,
			className : 'filled-in select-checkbox card',
			targets : 0
		} ],
		select : {
			style : 'os',
			selector : 'td:nth-child(1)',
		},
		order : [ [ 1, 'asc' ] ]
	});

	$("#key_lable").empty();
	
	
	$('.settinglink').click(function() {
		$('#setting_modal').modal('open');
		$("#key_lable").append("some Text");
		
		var href = $(this).attr('href');
		var url = href.replace('#', '/GetDLPSettings');		
		$.ajax({
			url : url,
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			type : "GET",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				DataLossPreventionReport=result.data;
				$("#key_value").val(''+DataLossPreventionReport.keyValue);
				$("#address_value").val(DataLossPreventionReport.reportAddress);
				$("#drive_filter_option").val(DataLossPreventionReport.typeFilter);
				$("#sheduled_daily").prop('checked', DataLossPreventionReport.daily);
				$("#sheduled_weekly").prop('checked', DataLossPreventionReport.weekly);
				$("#sheduled_monthly").prop('checked', DataLossPreventionReport.monthly);
				$("#report_state").prop('checked', DataLossPreventionReport.active);
				
				console.log(DataLossPreventionReport.dataLossPreventionReportType.id);
				if(DataLossPreventionReport.dataLossPreventionReportType.id !=2){
					$('#driveoption').hide();
					
				}else{
					$('#driveoption').show();
				}
				
				var stat=DataLossPreventionReport.active;
				if(!stat){
					$("#key_value").prop('disabled', true);
					$("#address_value").prop('disabled', true);
					$("#drive_filter_option").prop('disabled', true);
					$("#sheduled_daily").prop('disabled', true);
					$("#sheduled_weekly").prop('disabled', true);
					$("#sheduled_monthly").prop('disabled', true);
				}else{
					$("#key_value").prop('disabled', false);
					$("#address_value").prop('disabled', false);
					$("#drive_filter_option").prop('disabled', false);
					$("#sheduled_daily").prop('disabled', false);
					$("#sheduled_weekly").prop('disabled', false);
					$("#sheduled_monthly").prop('disabled', false);
				}
			},
			error : function(e) {
				if(e.status==401){
					window.location.href = "/";;
				}
			}
		});

		console.log(href);
	

	});

	
	
	$('#report_state').click(function() {
		var stat=$("#report_state").is(":checked");
		if(!stat){
			$("#key_value").prop('disabled', true);
			$("#address_value").prop('disabled', true);
			$("#drive_filter_option").prop('disabled', true);
			$("#sheduled_daily").prop('disabled', true);
			$("#sheduled_weekly").prop('disabled', true);
			$("#sheduled_monthly").prop('disabled', true);
		}else{
			$("#key_value").prop('disabled', false);
			$("#address_value").prop('disabled', false);
			$("#drive_filter_option").prop('disabled', false);
			$("#sheduled_daily").prop('disabled', false);
			$("#sheduled_weekly").prop('disabled', false);
			$("#sheduled_monthly").prop('disabled', false);
		}
	});
	
	
	
	
	$('#update_setting_btn').click(function() {
		DataLossPreventionReport.keyValue=$("#key_value").val();
		DataLossPreventionReport.reportAddress=$("#address_value").val();
		DataLossPreventionReport.typeFilter=$("#drive_filter_option").val();
		DataLossPreventionReport.daily=$("#sheduled_daily").is(":checked");
		DataLossPreventionReport.weekly=$("#sheduled_weekly").is(":checked");
		DataLossPreventionReport.monthly=$("#sheduled_monthly").is(":checked");
		DataLossPreventionReport.active=$("#report_state").is(":checked");
		DataLossPreventionReport.updatedOn=new Date();
		DataLossPreventionReport.updatedBy=$("#authUserId").val();
		
		$.ajax({
			url : "/PostDataLossPreventionReportSetting",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(DataLossPreventionReport),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				$('#setting_modal').modal('close');
				location.reload();	
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	});
});
