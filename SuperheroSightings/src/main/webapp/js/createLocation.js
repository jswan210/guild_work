/* 
 * createLocation.js 
 * designed to send ajax call to GoogleApi to generate Latitude and Longitude
 * so as to submit info to our Controller to create a new Location... 
 */

/* global google, results, adressCompArray */

$(document).ready(function () {
    $('#lat-long').hide();
    showGetCoordinate();
    getAddress();
    $('#submit-location').hide();
    $('#errorMessages').hide();
    $('#submit-update').hide();

});


function showGetCoordinate() {
    $('#cord-btn').show();
    $('#lat-long').hide();
    $('#submit-location').hide();
    $('#submit-update').hide();
    $('#submit-w-new-location').hide();
}


function getAddress() {
    $('#get-coordinates').click(function (event) {
        var address = $('#add-address').val();
        var city = $('#add-city').val();
        var state = $('#add-state').val();
        var fullAddress = address + ", " + city + ", " + state;
        if (address.valueOf() !== "" && city.valueOf() !== "" && state.valueOf() !== "") {
            searchAddress(fullAddress);
            hideCordBtn();
            $('#errorMessages').empty();


        } else {
            $('#errorMessages').empty();
            $('#errorMessages').show();
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('To Get Coordinates Address, City, and State must be filled in...'));
        }

    });
}

function searchAddress(fullAddress) {
    var addressInput = fullAddress;
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({address: addressInput}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            var lat = results[0].geometry.location.lat;
            var lng = results[0].geometry.location.lng;
            var address = results[0].formatted_address;
            var addressArray = address.split(",");
            var stateZipArray = addressArray[2].split(" ");

            $('#add-address').val(addressArray[0]);
            $('#add-city').val(addressArray[1]);
            $('#add-state').val(stateZipArray[1]);
            $('#add-zip').val(stateZipArray[2]);
            $('#add-latitude').val(lat);
            $('#add-longitude').val(lng);

            $('#errorMessages').empty();

        }
        else if (status === google.maps.GeocoderStatus.ZERO_RESULTS) {
            $('#errorMessages').show();
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Not A Valid Address'));
            $('#add-latitude').val(0);
            $('#add-longitude').val(0);
            $('#add-zip').val('');
        }
    });
}

function hideCordBtn() {
    $('#lat-long').show();
    $('#cord-btn').hide();
    $('#submit-location').show();
    $('#submit-update').show();
    $('#submit-w-new-location').show();

}

function checkAndDisplayValidationErrors(status) {
    $('#errorMessages').empty();
    var errorMessages = [];

    status.each(function () {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);

        }
    });

    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        return true;
    } else {
        return false;
    }
}