$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	 $('.modal').modal();
	 
	 
		var marketingTemplate = $('#signaturetemplate_table').dataTable({
			searching : false,
			paging : false,
			info : false,
			select : true,
			
			columnDefs : [ {
				orderable : false,
				className : 'select-checkbox',
				targets : 3
			} ],
			select : {
				style : 'single',
				selector : 'td:nth-child(4)' // this line is the
												// most importan!
			},
			order : [ [ 1, 'asc' ] ]
		});
		
		
		var marketingtable = $('#marketingtemplate_table').dataTable({
			searching : false,
			paging : false,
			info : false,
			select : true,	
			columnDefs : [ {
				orderable : false,
				className : 'select-checkbox',
				targets : 3
			} ],
			select : {
				style : 'single',
				selector : 'td:nth-child(4)' // this line is the
												// most importan!
			},
			order : [ [ 1, 'asc' ] ]
		});
		
	 $("#signatureUpdate").click(function() {
		 $('#signatureupdate_model').modal('open');
		 
	 });
	 
	 $("#marketingUpdate").click(function() {
		 $('#marketingupdate_model').modal('open');
		 
	 });
	 
	 $("#password_reset").click(function() {
		 $('#passwordreset_model').modal('open');
		 
		 $('#phonenumber_reset').val($('#phonenumber').val())
	 });
	 $("#resetpass").click(function() {
		 	var x = document.getElementById("progress");
		   	var onsucessNotification = document.getElementById("onsuccess");
		   	x.style.display = "block";
		   	$('#passwordreset_model').modal('close');
		 
	 });
	 
	 
	 
	 
	 
	 
	 
	 var body;
	 $('#marketingtemplate_table tbody').on( 'click', 'tr', function () {
		 var table = $('#marketingtemplate_table').DataTable();
		 body="<"+table.row( this ).data()[2]+">";
		 var insertData = String(body);
	});
	 
	
	
});
