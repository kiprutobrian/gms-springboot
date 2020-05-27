/*
 * DataTables - Tables
 */

$(document).ready(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var ArchiveAccount;
	$('.modal').modal();

	$('.setting_backup_link').click(function() {
		$('#setting_migration_modal').modal('open');
		var href = $(this).attr('href');
		var url = href.replace('#', '/GetDataMigrationSettings');
		
		console.log("SUCCESS: ", url);
		
		$.ajax({
			url : url,
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			type : "GET",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				console.log("SUCCESS: ", result);
				
				ArchiveAccount=result.data;
				$("#backupaccount_value").val(ArchiveAccount.account);
				$("#migration_account_state").prop('checked', ArchiveAccount.active);
				
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	});
	
	$('#update_setting_migration_btn').click(function() {
		
		ArchiveAccount.account=$("#backupaccount_value").val();
		ArchiveAccount.active=$("#migration_account_state").is(":checked");
		ArchiveAccount.updatedOn=new Date();
		ArchiveAccount.updatedBy=$("#authUserId").val();
		
		$.ajax({
			url : "/PostDataMigrationSetting",
			headers : {
				"X-XSRF-TOKEN" : token,
			},
			data : JSON.stringify(ArchiveAccount),
			type : "POST",
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				$('#setting_migration_modal').modal('close');
				if(result.isPresent){
					alert('Success'+result.message);
				}else{
					alert(''+result.message);
				}					
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	});

	
	
});
