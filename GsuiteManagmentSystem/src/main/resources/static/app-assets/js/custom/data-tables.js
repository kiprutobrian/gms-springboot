$(document).ready(function () {
    $('#dataTable').DataTable({
        columnDefs: [{
            orderable: false,
            className: 'select-checkbox',
            targets: 0
        }],
        select: {
            style: 'multi',
            selector: 'td:first-child'
        },
        order: [
            [1, 'asc']
        ],
        rowGroup: {
            dataSrc: 2
        }
    });
});