/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    // googleMap();

});




function latitude() {
    var locationTableArray = [];
    $("#latitude").each()(function () {
        var latitude = $(this).text();
        locationTableArray.put(latitude);
    });
}
function locationArray() {
    var locationInfo = new Array();
    // var locationStuffs = [];
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperheroSightings/getRecentTenSightings',
        dataType: 'json',
        data: {format: 'json'},
        success: function (data) {
            $.each(data, function (key, value) {
                locationInfo.push(value);
            });


//            $.each(data, function (key, value) {
//
//                locationStuffs.push(value.location.latitude);
//                locationStuffs.push(value.location.longitude);
//
//            });

        },
//                    var latitude = data[0].location.latitude;
//                            var longitude = data[0].location.longitude;

        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error Calling web service. Please Try again later.'));
        }
    });
    return locationInfo;
}

function googleMap() {

    //var locations = new Array();
    var locations = locationArray();
    console.log(locations);


    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 6,
        center: new google.maps.LatLng(40.92, -80.0),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i].location.latitude, locations[i].location.longitude),
            map: map
        });

        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(locationInfo[i][0]);
                infowindow.open(map, marker);
            };
        })(marker, i));
    }
}