/* 
 * editLocation.js
 * 
 * ---I THINK IM GOING TO DELETE THIS FILE ALTOGETHER AND JUST USE THE createLocation.js instead...
 */



//$(document).ready(function () {
//    showGetCoordinate();
//
//
//});
//
////function latLongReadOnly() {
////    $('#add-latitude').attr('readonly');
////    $('#add-longitude').attr('readonly');
////}
//
//function showGetCoordinate() {
//
//    $('#cord-btn').show();
//    $('#lat-long').hide();
//    $('#update-location').hide();
//    $('#errorMessages').empty();
//
//}
//
//
//function getAddress() {
//    $('#get-coordinates').click(function (event) {
//        var address = $('#add-address').val();
//        var city = $('#add-city').val();
//        var state = $('#add-state').val();
//        var fullAddress = address + ", " + city + ", " + state;
//        if (address.valueOf() !== "" && city.valueOf() !== "" && state.valueOf() !== "") {
//            searchAddress(fullAddress);
//            hideCordBtn();
//            $('#errorMessages').empty();
//
//
//        } else {
//            $('#errorMessages').show();
//            $('#errorMessages').empty();
//            $('#errorMessages')
//                    .append($('<li>')
//                            .attr({class: 'list-group-item list-group-item-danger'})
//                            .text('To Get Coordinates Address, City, and State must be filled in...'));
//        }
//    });
//}
//
//
//function searchAddress(fullAddress) {
//    var addressInput = fullAddress;
//    var geocoder = new google.maps.Geocoder();
//    geocoder.geocode({address: addressInput}, function (results, status) {
//        if (status === google.maps.GeocoderStatus.OK) {
//            var lat = results[0].geometry.location.lat;
//            var lng = results[0].geometry.location.lng;
//            var streetNumber = results[0].address_components[0].long_name;
//            var route = results[0].address_components[1].long_name;
//            var locality = results[0].address_components[2].long_name;
//            var adminArea1 = results[0].address_components[4].short_name;
//            var zipcode = results[0].address_components[6].long_name;
//
//            $('#errorMessages').empty();
//            //i add these from the google geocode api so that the response is standardized...
//            $('#add-address').val(streetNumber + ' ' + route);
//            $('#add-latitude').val(lat);
//            $('#add-longitude').val(lng);
//            $('#add-city').val(locality);
//            $('#add-state').val(adminArea1);
//            $('#add-zip').val(zipcode);
//        } else if (status === google.maps.GeocoderStatus.ZERO_RESULTS) {
//            $('#errorMessages').show();
//            $('#errorMessages').empty();
//            $('#errorMessages').append($('<li>')
//                    .attr({class: 'list-group-item list-group-item-danger'})
//                    .text('Not A Valid Address'));
//            $('#add-latitude').val(0);
//            $('#add-longitude').val(0);
//            $('#add-zip').val('');
//        }
//    });
//}
//
//function hideCordBtn() {
//    $('#lat-long').show();
//    $('#cord-btn').hide();
//    $('#update-location').show();
//
//}