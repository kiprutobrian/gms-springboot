

CKEDITOR.replace( 'editor1' );
CKEDITOR.instances['editor1'].setData('<pre>FirstName'+"" +
		""+' LastName '+
		""+' EmailAddress '+
		""+' Department'+
		""+' JobTitle '+
		""+' <small>PhoneNumber</small></pre>');

function myFunction() {
	
	var token = $("meta[name='_csrf']").attr("content");
	var x = document.getElementById("progress");
	var onsucessNotification = document.getElementById("onsuccess");
	x.style.display = "block";

	var token = $("meta[name='_csrf']").attr("content");
	var templatename = $("#templatename").val();

	var signaturecreated = {
		Id : 0,
		signatureTemplate : CKEDITOR.instances['editor1'].getData(),
		signatureName : templatename,
		createdDate : new Date(),
		createdBy : ""
	}

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
			 $('#message').append('<p>template <span style="color:blue;">'+templatename+'</span> Template Created Sucessfully<p>')
			 onsucessNotification.style.display = "block";
		},
		error : function(e) {

			console.log("ERROR: ", e);
		}
	});	
	
}
