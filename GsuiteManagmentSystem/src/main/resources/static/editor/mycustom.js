


CKEDITOR.replace( 'editor1' );
CKEDITOR.instances.my_editor.getData();



//var toolbarOptions = [
//    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
//    ['blockquote', 'code-block'],
//    ['table'],
//                // custom button values
//    [{ 'list': 'ordered' }, { 'list': 'bullet' }],
//    [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
//    [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
//    [{ 'direction': 'rtl' }],                         // text direction
//    ['clean'], ['link', 'image']                                       // remove
//																		// formatting
//																		// button
// ];




// var options = {
//    theme: 'snow',
//    modules: {
//       toolbar: toolbarOptions,
//       imageResize: true,
//      
//    },
//    placeholder: 'SIGNATURE',
//    imageHandler: imageHandler
// };
// var editor = new Quill('#editor', options);
// editor.getModule("toolbar").addHandler("image", imageHandler);
// function imageHandler(image, callback) {
//    var range = editor.getSelection();
//    var value = prompt('Provide The Image URL');
//    editor.insertEmbed(range.index, 'image', value, Quill.sources.USER);
// }
// function myFunction() {
//     console.log("TESTING "+editor.root.innerHTML);
//     
//     var token = $("meta[name='_csrf']").attr("content");
//     var templatename = $("#templatename").val();
//		
//     console.log("Signature ====================" + editor.root.innerHTML);
//	 console.log("Temp Name====================" + templatename);
//
//
//		var signaturecreated = {
//			Id: 0,
//			signatureTemplate: editor.root.innerHTML,
//			signatureName: templatename,
//			createdDate: new Date(),
//			createdBy: ""
//
//		}
//
//		$.ajax({
//			url: "/createdSignature",
//			headers: {
//				"X-XSRF-TOKEN": token,
//			},
//			data: JSON.stringify(signaturecreated),
//			type: "POST",
//			contentType: "application/json",
//			dataType: 'json',
//			success: function (result) {
//				location.reload();
//			},
//			error: function (e) {
//
//				console.log("ERROR: ", e);
//			}
//		});
//
//  }
