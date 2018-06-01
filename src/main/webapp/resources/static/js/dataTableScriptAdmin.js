var users;
var ratings;
var restaurants;

function openImageWindow(src) {
    var image = new Image();
    image.src = src;
    var width = image.width;
    var height = image.height;
    window.open(src,"Image","width=" + width + ",height=" + height);
}

$(document).ready(function() {

    $('#restaurant').toggle();
    $('#userEdit').toggle();
    /*function explode(){
        $('#ratings').toggle();
        $('#users').toggle();
        $('#restaurant').toggle();
    }*/
    //setTimeout(explode, 1000);

    function closeAll() {
        $('#restaurants').hide();
        $('#ratings').hide();
        $('#users').hide();
        $('#restaurant').hide();
        $('#userEdit').hide();
    }

    $('#show-ratings').click(function () {
        closeAll();
        $('#ratings').toggle('hide');
    });

    $('#show-restaurants').click(function () {
        closeAll();
        $('#restaurants').toggle('hide');
    });

    $('#show-users').click(function () {
        closeAll();
        $('#users').toggle('hide');
    });

    $('#add-new-restaurant').click(function () {
        closeAll();
        $('#restaurantId').val('0');
        $('#name').val('');
        $('#description').val('');
        $('#foto').val('');
        $('#site').val('');
        $('#enabled2').prop('checked', false);
        $('#restaurant').toggle('hide');
    });

    restaurants = $('#restaurants-table').dataTable( {
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "sAjaxDataProp": "",
        "sAjaxSource": "/adminAPI/restaurants",
        "order": [[ 0, "asc" ]],
        "columns": [
            { "data": "id"},
            { "data": "name" },
            {
                "targets": 0,
                "data": "site",
                "render": function (data, type, row, meta) {
                    return '<a href="' + data + '"  target="_blank" title="' + data + '">url</a>';
                }
            },
            {
                sClass: "usersDataTable",
                "data": "description",
                "render": function (data) {
                    return '<label title="' + data + '">' + data + '</label>';
                }

            },
            {
                "data": "foto",
                "render": function (data) {
                    return '<img src="' + data + '" height="25px" style="cursor: pointer;" onclick = "openImageWindow(this.src);">';
                }
            },
            {
                "data": "enabled",
                "render": function (data) {
                    return data == 1 ? '<label style="color: green;"> enabled </label>' : '<label style="color: red;"> disabled </label>';
                }
            },
            {
                "targets": 0,
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<a href="#" onclick="editREstaurant(' + data + ')">edit</a>';
                }
            },
            {
                "targets": 0,
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<a href="#" onclick="deleteRestaurant(' + data + ')">delete</a>';
                }
            }
        ]
    } );

    ratings = $('#ratings-table').dataTable ({
        "fnInitComplete": function(oSettings, json) {
            $('#ratings').toggle();
        },
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "sAjaxDataProp": "",
        "sAjaxSource": "/adminAPI/ratings",
        "order": [[ 0, "asc" ]],
        "columns": [
            { "data": "id"},
            {
                "data": "userID",
                "render": function (data) {
                    return '<a href="adminAPI/editUser/' + data + '">' + data + '</a>';
                }
            },
            {
                "data": "restaurantId",
                "render": function (data) {
                    return '<a href="adminAPI/editRestaurant/' + data + '">' + data + '</a>';
                }
            },
            { "data": "rating" },
            /*{ "data": "date" },*/
            {
                "targets": 0,
                "data": "date",
                "render": function (data, type, row, meta) {
                    var timestamp = data;
                    var date = new Date();
                    date.setTime(timestamp);
                    date = moment(date).format("YYYY-MM-DD HH:mm:ss");
                    return date;
                }
            },
            {
                "targets": 0,
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<a href="#" onclick="deleteRating(' + data + ')">delete</a>';
                }
            }
        ]
    });

    users = $('#users-table').dataTable ({
        "fnInitComplete": function(oSettings, json) {
            $('#users').toggle();
        },
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "sAjaxDataProp": "",
        "sAjaxSource": "/adminAPI/users",
        "order": [[ 0, "asc" ]],
        "columns": [
            { "data": "id"},
            { "data": "login" },
            { "data": "email" },
            { "data": "password", sClass: "usersDataTable"},
            {
                "data": "enabled",
                "render": function (data) {
                    return data == 1 ? '<label style="color: green;"> enabled </label>' : '<label style="color: red;"> disabled </label>';
                }
            },
            {
                "targets": 0,
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<a href="#" onclick="editUser(' + data + ')">edit</a>';
                }
            },
            {
                "targets": 0,
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<a href="#" onclick="deleteUser(' + data + ')">delete</a>';
                }
            }
        ]
    });

    $('#submitUser').click(function () {
        $.post({
            url : '/adminAPI/editUser',
            data : $('form[name=userDTO]').serialize(),
            success : function(res) {
                //alert(JSON.stringify(res));
                if (res.validated) {
                    //alert("User changed");
                    users.api().ajax.reload( null, false );
                    $('#userEdit').toggle('hide');
                    $('#users').toggle('hide');
                }
            }
        })
    });

} );

function editUser(href) {
    $('#restaurants').hide();
    $('#ratings').hide();
    $('#users').hide();
    $('#restaurant').hide();
    $('#userEdit').toggle('hide');
    $.post({
        url : '/adminAPI/editUser/' + href,
        data : $('form[name=userDTO]').serialize(),
        success : function(res) {
            //alert(JSON.stringify(res));
            $.each( res.userDTO, function( key, value ) {
                if (value === true) $('#' + key + '1').prop('checked', true);
                if (value === false) $('#' + key + '1').prop('checked', false);
                $( '#' + key ).val( value );
            });
        }
    })
}

function deleteUser(href) {
    $.get({
        url : '/adminAPI/deleteUser/' + href,
        success : function(res) {
            users.api().ajax.reload( null, false );
        }
    })
}

function deleteRating(href) {
    $.get({
        url : '/adminAPI/deleteRating/' + href,
        success : function(res) {
            ratings.api().ajax.reload( null, false );
        }
    })
}

function deleteRestaurant(href) {
    $.get({
        url : '/adminAPI/deleteRestaurant/' + href,
        success : function(res) {
            restaurants.api().ajax.reload( null, false );
        }
    })
}

function editREstaurant(href) {
    $('#restaurants').hide();
    $('#ratings').hide();
    $('#users').hide();
    $('#restaurant').hide();
    $('#userEdit').hide();
    $('#restaurant').toggle('hide');
    $.post({
        url : '/adminAPI/editRestaurant/' + href,
        data : $('form[name=userForm]').serialize(),
        success : function(res) {
            //alert(JSON.stringify(res));
            $.each( res.restaurantDTO, function( key, value ) {
                //alert(key + " : " + value);
                if (value === true) $('#' + key + '2').prop('checked', true);
                if (value === false) $('#' + key + '2').prop('checked', false);
                $( '#' + key ).val( value );
                if(key == 'id') $('#restaurantId').val(value);
            });
        }
    })
}