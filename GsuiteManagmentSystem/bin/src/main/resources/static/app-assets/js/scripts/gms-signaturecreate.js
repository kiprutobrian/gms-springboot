

CKEDITOR.replace( 'editor1' );
CKEDITOR.instances['editor1'].setData('<pre style="color:red;">FirstName'+"" +
		""+' LastName '+
		""+' EmailAddress '+
		""+' Department'+
		""+' JobTitle '+
		""+' PhoneNumber</pre>');

function myFunction() {
	var token = $("meta[name='_csrf']").attr("content");
	var templatename = $("#templatename").val();

	var signaturecreated = {
		Id : 0,
		signatureTemplate : CKEDITOR.instances['editor1'].getData(),
		signatureName : templatename,
		createdDate : new Date(),
		createdBy : ""
	}

	if(templatename.length==0){
		swal({
		    title: 'Error',
		    text:	'Template Name Required',
		    icon: 'error'
		  })
		        
	}else{
		var x = document.getElementById("progress");
		x.style.display = "block";

	$.ajax({
		url : "/createdSignature",
		headers : {
			"X-XSRF-TOKEN" : token,
		},
		data : JSON.stringify(signaturecreated),
		type : "POST",
		contentType : "application/json",
		dataType : 'json',
		success : function(result) {
			x.style.display = "none";
			
			swal({
			    title: "Signature Template",
			    text:  "Signature created succesfully",
			    icon: 'success',
			    buttons: {
			      delete: 'Ok'
			    }
			  }).then(function (willDelete) {
				  $('#mymodal').modal('open');
			     	location.reload();
				});
			
		},
		error : function(e) {

			console.log("ERROR: ", e);
		}
	});	}
	
}
