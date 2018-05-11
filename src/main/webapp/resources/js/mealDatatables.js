var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        paging: false,
        info: true,
        columns: [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Update",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    var form = $("#filter");
    $.ajax({
        type: "GET",
        url: ajaxUrl+"between",
        data: form.serialize(),
        success: function (data) {
            datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        }
    })
}

function resetFilter() {
    $("#filter").find(":input").val("");
    updateTable();
}