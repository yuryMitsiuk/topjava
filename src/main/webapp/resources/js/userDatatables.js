var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function enable(cb, id) {
    var enabled = cb.is(":checked");
    $.ajax({
        type: "POST",
        url: ajaxUrl+id,
        data: "enabled=" + enabled
    }).done(function () {
        cb.closest("tr").attr("data-userEnabled", enabled);
        // row.attr("data-userEnabled", enabled);
        successNoty(enabled ? "Enabled" : "Disabled");
    })
}