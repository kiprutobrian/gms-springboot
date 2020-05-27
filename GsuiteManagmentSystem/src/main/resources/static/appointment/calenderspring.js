
$('.myCalendar').calendar(
    {
        date: new Date(),
        autoSelect: false, // false by default
        select: function (date) {

            var mydate = new Date(date);
            var myEmailAddress =/*[[${myEmailAddress}]]*/'';
            
            console.log('EMAIL ADDRESS', myEmailAddress);

            
            consoul.log("");

            var selectedDate = mydate.getTime();

            console.log('SELECT', selectedDate);

            $.ajax({
                url: "/getDateUpdate/" + myEmailAddress + "/" + selectedDate + "/",
                type: "GET",
                contentType: "application/json",
                dataType: 'json',
                success: function (result) {
                    console.log("Results: "
                        + result);
                    var moring = "";
                    var noon = "";

                    if (JSON.stringify(result)) {
                        console.log("EMPTY===============");

                    } else {
                        console.log("ELSE===============");
                    }

                    var divide = (result.length) / 3;

                    console.log("ELSE===============" + divide);

                    for (var i = 0; i < result.length; i++) {

                        if (result[i].isPm) {
                            noon += '<tr><td><button type="button" >'
                                + result[i].day
                                + '</button></td><td style="display:none;">'
                                + result[i].day
                                + '</td></tr>';
                        } else {
                            moring += '<tr><td><button type="button" >'
                                + result[i].day
                                + '</button></td> <td style="display:none;">'
                                + result[i].day
                                + '</td></tr>';
                        }
                    }
                    $("#mornings tr").remove();
                    $('#mornings').append(moring);
                    $("#noons tr").remove();
                    $('#noons').append(noon);
                },
                error: function (e) {
                    alert("Error!")
                    console.log("ERROR: ", e);
                }
            });
        },
        toggle: function (y, m) {
            console.log('TOGGLE', y, m)
        }
    });

$(document).ready(function () {
    document.getElementById('mornings').onclick = function (event) {
        event = event || window.event; //for IE8 backward compatibility
        var target = event.target || event.srcElement; //for IE8 backward compatibility
        while (target && target.nodeName != 'TR') {
            target = target.parentElement;
        }
        var cells = target.cells; //cells collection
        //var cells = target.getElementsByTagName('td'); //alternative
        if (!cells.length || target.parentNode.nodeName == 'THEAD') { // if clicked row is within thead
            return;
        }

        var x = document.getElementById("reg_form");
        x.style.display = "block";

        var newcxcxc = cells[1].innerHTML;
        var d = new Date(newcxcxc);

        $('#mydate').val(cells[1].innerHTML);

        if (d.getMinutes() == 0) {
            if (d.getHours() >= 12) {
                $('#starting').val(d.getHours() + ":" + "00");
                $('#ending').val(d.getHours() + ":" + 30);
            } else {
                $('#starting').val(d.getHours() + ":" + "00");
                $('#ending').val(d.getHours() + ":" + 30);
            }

        } else {
            if (d.getHours() >= 12) {
                $('#starting').val(d.getHours() + ":" + 30);
                $('#ending').val((d.getHours() + 1) + ":" + "00");
            } else {
                $('#starting').val(d.getHours() + ":" + 30);
                $('#ending').val((d.getHours() + 1) + ":" + "00");
            }
        }

    }

    document.getElementById('noons').onclick = function (event) {
        event = event || window.event; //for IE8 backward compatibility
        var target = event.target || event.srcElement; //for IE8 backward compatibility
        while (target && target.nodeName != 'TR') {
            target = target.parentElement;
        }
        var cells = target.cells; //cells collection
        //var cells = target.getElementsByTagName('td'); //alternative
        if (!cells.length || target.parentNode.nodeName == 'THEAD') { // if clicked row is within thead
            return;
        }

        var x = document.getElementById("reg_form");
        x.style.display = "block";

        var newcxcxc = cells[1].innerHTML;
        var d = new Date(newcxcxc);

        $('#mydate').val(cells[1].innerHTML);

        if (d.getMinutes() == 0) {
            if (d.getHours() >= 12) {
                $('#starting').val(d.getHours() + ":" + "00");
                $('#ending').val(d.getHours() + ":" + 30);
            } else {
                $('#starting').val(d.getHours() + ":" + "00");
                $('#ending').val(d.getHours() + ":" + 30);
            }

        } else {
            if (d.getHours() >= 12) {
                $('#starting').val(d.getHours() + ":" + 30);
                $('#ending').val((d.getHours() + 1) + ":" + "00");
            } else {
                $('#starting').val(d.getHours() + ":" + 30);
                $('#ending').val((d.getHours() + 1) + ":" + "00");
            }
        }

    }

    $("#make_appointment").click(function () {

        var token = $("meta[name='_csrf']").attr("content");

        var startingTime = $('#starting').val();
        var endingTime = $('#ending').val();
        var myDate = $('#mydate').val();
        var description = $('#description').val();
        var emailAddress = $('#email').val();
        var fullname = $('#fullname').val();
        var phoneNumber = $('#phonenumber').val();
        var title = $('#title').val();

        var organisation = $('#organisation').val();

        var selectdate = new Date(myDate);


        var myEmailAddress =/*[[${myEmailAddress}]]*/'';

        alert("Appointment Booked")

        var Appointment = {
            id: null,
            myEmail: null,
            selectedDate: selectdate.getTime(),
            selectedTo: endingTime,
            titile: title,
            email: emailAddress,
            name: fullname,
            phoneNumber: phoneNumber,
            organisation: organisation,
            description: description
        }

        $.ajax({
            url: "/makeAppointment/" + myEmailAddress + "/test",
            headers: {
                "X-XSRF-TOKEN": token,
            },
            data: JSON.stringify(Appointment),
            type: "POST",
            contentType: "application/json",
            dataType: 'json',
            success: function (result) {
                location.reload();
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });

        console.log("++++" + selectdate.getTime() + "++++");
        console.log(startingTime);
        console.log(endingTime);
        console.log(myDate);
        console.log(description);
        console.log(emailAddress);
        console.log(fullname);
        console.log(phoneNumber);

    });
});
		/*]]>*/
