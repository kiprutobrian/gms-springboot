$(document).ready(function() {
	
	var table = $('#signatureuserlist').DataTable({
		bPaginate : true,
		bLengthChange : false,
		bFilter : true,
		bInfo : false,
		bAutoWidth : false,
		buttons : [ 'selectAll', 'selectNone' ],
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
});