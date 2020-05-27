/*
 * DataTables - Tables
 */
/*<![CDATA[*/
$(document).ready(function() {
        var token = $("meta[name='_csrf']").attr("content");
        var company = $('#authuser_company_id').val();
        google.charts.load('current', { 'packages': ['corechart', 'controls', 'line', 'table', 'bar'] });
        var data;

        Date.prototype.toDateInputValue = (function() {
            var local = new Date(this);
            local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
            return local.toJSON().slice(0, 10);
        });

        var d = new Date();
        d.setDate(d.getDate() - 1);

        $('#startdate').val(d.toDateInputValue());
        $('#enddate').val(new Date().toDateInputValue());

        $("#filterbt").click(function() {
            var to = $('#enddate').val()
            var from = $('#startdate').val();
            var keyword = $('#keyword').val();

            var filter = {
                company: company,
                userAccount: "",
                ipAddress: "",
                keyword: keyword,
                startingDate: from,
                endingDate: to
            };
            google.charts.setOnLoadCallback(drawTable(filter));
        });

        function drawTable(filter) {

            var myUrl = "/GetDLPGmailReport";
            $.ajax({
                url: myUrl,
                headers: {
                    "X-XSRF-TOKEN": token,
                },
                data: JSON.stringify(filter),
                method: "POST",
                contentType: "application/json",
                dataType: 'json',
                success: function(result) {
                    data = new google.visualization.DataTable();

                    data.addColumn('string', "Sent From");
                    data.addColumn('string', "Sent To");
                    data.addColumn('string', "Sent On");
                    data.addColumn('string', "Subject");
                    data.addColumn('string', "Has Shared Attachment");
                    data.addColumn('string', "Has Attachment");
                    data.addColumn('string', "Download Attachment");
                    data.addColumn('string', "Message Snippet");

                    console.log(result);
                    var reportData = result.data;
                    for (var a = 0; a < reportData.length; a++) {
                        data.addRow([

                            reportData[a].from,
                            reportData[a].to,
                            reportData[a].dateSent,
                            reportData[a].subject,
                            reportData[a].hasDriveAttachment,
                            reportData[a].hasAttachment,
                            reportData[a].resourceurl,
                            'From :' + reportData[a].from + ' To :' + reportData[a].to + '/n' + ' Subject :' + reportData[a].subject + '\n' + reportData[a].snippet
                        ]);
                    }

                    var tableoptions = {
                        allowHtml: true,
                        cssClassNames: {
                            tableCell: 'font-size: 8px;width:100%;'
                        }
                    };
                    var table = new google.visualization.Table(document.getElementById('dashboard_table_mail_report'));
                    table.draw(data, tableoptions);
                },
                error: function(e) {
                    alert(e);
                }
            });
        }

        /***************************************************************************
         * SEARCH EMAILS WITH MY KEYWORD FROM AND TO DATE
         **************************************************************************/

        $(".datepicker").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });

        $("#createReport").click(function() {
            var to = $('#todate').val()
            var from = $('#fromdate').val();
            var keyword = $('#keyword').val();
            console.log(from + " : " + to);

            var myUrl = "/GetDLPDriveReport";
            var table = $('#delegatesuserlist').DataTable({
                bPaginate: false,
                bLengthChange: false,
                bFilter: true,
                bInfo: false,
                bAutoWidth: true,
                processing: true,
                bDestroy: true,
                ajax: {
                    url: myUrl,
                    headers: {
                        "X-XSRF-TOKEN": token,
                    },
                    dataSrc: ''
                },
                columns: [{
                    data: "messageId"
                }, {
                    data: "dateSent"
                }, {
                    data: "from"
                }, {
                    data: "to"
                }, {
                    data: "subject"
                }, {
                    data: "hasDriveAttachment"
                }, {
                    data: "hasAttachment"
                }, {
                    data: "resourceurl"
                }, {
                    data: "snippet"
                }]
            });

            console.log("" + to + " :  " + from);

        });
        var token = $("meta[name='_csrf']").attr("content");

        $("#download").click(function() {
            var table = $('#delegatesuserlist').DataTable();
            var data = table.rows().data();

            var datatosend = [];

            for (var x = 0; x < data.length; x++) {
                datatosend.push(data[x]);
            }
            console.log(datatosend);
            var x = document.getElementById("progress");
            var onsucessNotification = document.getElementById("onsuccess");
            x.style.display = "block";
            $.ajax({
                url: "/DownloadReport",
                headers: {
                    "X-XSRF-TOKEN": token,
                },
                data: JSON.stringify(datatosend),
                type: "POST",
                contentType: "application/json",
                dataType: 'json',
                success: function(result) {
                    x.style.display = "none";
                    $('#message').append('<p>Report Sheet saved and Downloaded on ur Google Drive Click The Link Below to view the file <a style="color:blue;" href="' + result.status + '">Sheet Click</a> Template Created Sucessfully<p>')
                    onsucessNotification.style.display = "block";
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                }
            });
        });

    })
    /* ]]> */