/*
 * DataTables - Tables
 */

$(function() {

	var tablesharedbyme = $('#orgdrivesharedlist')
			.DataTable(
					{
						bPaginate : false,
						bLengthChange : false,
						bFilter : true,
						bInfo : false,
						bAutoWidth : false,
						columnDefs : [ {
							targets : 0,
							render : function(data, type, row, meta) {
								if (type === 'display') {
									return '<div class="checkbox"><label><input type="checkbox" class="filled-in" style="width: 50px;"><span></span></label></div>';
								} else {
									return '<div class="checkbox"><label><input type="checkbox" class="filled-in" style="width: 50px;" checked="checked"><span></span></label></div>';
								}
							},
							checkboxes : {
								selectRow : true,
								selectAllRender : '<div class="checkbox"><label><input type="checkbox" class="filled-in" style="width: 50px;"><span></span></label></div>'
							}
						} ],
						select : {
							style : 'multi',
						},
						order : [ [ 1, 'asc' ] ]
					});

});
