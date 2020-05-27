var toolbarOptions = [ [ 'bold', 'italic', 'underline', 'strike' ], // toggled
																	// buttons
[ 'blockquote', 'code-block' ],
// custom button values
[ {
	'list' : 'ordered'
}, {
	'list' : 'bullet'
} ], [ {
	'script' : 'sub'
}, {
	'script' : 'super'
} ], // superscript/subscript
[ {
	'indent' : '-1'
}, {
	'indent' : '+1'
} ], // outdent/indent
[ {
	'direction' : 'rtl'
} ], // text direction
[ 'clean' ], [ 'link', 'image' ] // remove
// formatting
// button
];
var options = {
	theme : 'snow',
	modules : {
		toolbar : toolbarOptions,
		imageResize : true,
	},
	placeholder : 'Create Advertising Templates',
	imageHandler : imageHandler
};
var editor = new Quill('#editor', options);
editor.getModule("toolbar").addHandler("image", imageHandler);
function imageHandler(image, callback) {
	var range = editor.getSelection();
	var value = prompt('Provide The Image URL');
	editor.insertEmbed(range.index, 'image', value, Quill.sources.USER);
}
function myFunction() {
	var token = $("meta[name='_csrf']").attr("content");
	var templatename = $("#templatename").val();
	var x = document.getElementById("progress");
	var onsucessNotification = document.getElementById("onsuccess");
	x.style.display = "block";
	
	var Marketing = {
		id : 0,
		marketingTemplate : editor.root.innerHTML,
		marketingName : templatename,
		createdDate : null,
		createdBy : null,
	}

	$.ajax({
		url : "/createdMarketing",
		headers : {
			"X-XSRF-TOKEN" : token,
		},
		data : JSON.stringify(Marketing),
		type : "POST",
		contentType : "application/json",
		dataType : 'json',
		success : function(result) {
			x.style.display = "none";
			$('#message').append('<p> <span Marketing Template style="color:blue;">' + templatename + '</span> Created Sucessfully<p>')
			onsucessNotification.style.display = "block";
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});

}
