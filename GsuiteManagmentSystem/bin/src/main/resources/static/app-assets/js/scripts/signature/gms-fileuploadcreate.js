$(document)
		.ready(
				function() {

					var base64String = "";
					$('input[type="file"]').change(function(e) {
//						var myJSON = JSON.stringify(e.target.files[0]);
						var isupdated = false;
						var file = event.srcElement.files[0];
						var reader = new FileReader();
						reader.readAsBinaryString(file);
						reader.onload = function() {
							base64String = btoa(reader.result);
							isupdated = true;
							var fileName = e.target.files[0].name;
							const lastDot = fileName
									.lastIndexOf('.');
							const fileExtension = fileName.substring(lastDot + 1);
							var fileSize = e.target.files[0].size;
							var filePath = e.target.files[0].path;
							// extension,path,size
							var x = document.getElementById("stateupdate");

							var imagurl = $("#imagurl").val();

							console.log("IMAGE URL" + imagurl);

							var SigCsvFile = {
								id : imagurl,
								basefile : base64String

							}
							var token = $("meta[name='_csrf']").attr("content");

							console.log('there are some problems'+ base64String);
							$.ajax({
								url : "/UploadSignatureCSVFile",
								headers : {
									"X-XSRF-TOKEN" : token,
								},
								data : JSON.stringify(SigCsvFile),
								type : "POST",
								contentType : "application/json",
								dataType : 'json',
								success : function(result) {

									var output = [];
									for (var i = 0; i < result.length; i++) {
										var item = '<li class="col s6"><div class="card"> <div class="box-body">'+ result[i].basefile+ '</div></div></li>'
										output.push(item);
									}
									$('#list').empty().append(output);

									$('#apply').click(function() {
											$.ajax({
												url : "/ApplySignature",
												data : JSON.stringify(result),
												type : "POST",
												contentType : "application/json",
												dataType : 'json',
												success : function(results) {
															location.reload();
														}
													});
											});

											console.log("Results: "+ result);
										},
										error : function(e) {
											alert("Error!")
											console.log("ERROR: ",e);
										}
									});

								};
							reader.onerror = function() {
								console
										.log('there are some problems');
							};

					});

			});
