/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");

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

	$('#edit').click(function() {
		$('.modal').modal();
		$('#product_module').modal('open');
		// var sd=data;

		var data = newrm.row('.selected').data();

		console.log(data[6]);
		$('#setting_id').val(data[1]);
		$('#setting_name').val(data[2]);
		$('#setting_updatedOn').val(data[8]);
		$('#setting_updatedBy').val(data[7]);
		$('#setting_description').val(data[3]);
		$('#setting_value').val(data[5]);
		var cheked = data[6];
		if (cheked == 'true') {
			$("#setting_state").prop('checked', true);
		} else {
			$("#setting_state").prop('checked', false);
		}

	});

	$('#update_btn').click(function() {

		var id = $('#setting_id').val();
		var active = $('#setting_state').is(":checked");
		var value = $('#setting_value').val();

		var settingUpdate;

		settingUpdate = {
			id : id,
			value : value,
			active : active
		}

		if (value.length < 5) {
			alert("PROVIDE PRODUCT NAME")
		} else {
			$.ajax({
				url : "/PostUpdateSettingsOptions",
				headers : {
					"X-XSRF-TOKEN" : token,
				},
				data : JSON.stringify(settingUpdate),
				type : "POST",
				contentType : "application/json",
				dataType : 'json',
				success : function(result) {
					$('#product_module').modal('close');

					location.reload();
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}

		console.log(product);

	});

})
