/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    loadUpHide();
    displayToChangeLocation();
    $('#location-dropdown').attr("required", false);

});

function displayToChangeLocation() {
    $('#change-location-btn').click(function (event) {
        $('#loc-edit').show();
        $('#add-location-name-block').hide();
        $('#change-location-btn').hide();
        $('#update-location-heading').show();
        $("#or-option").show();
        $("#create-new-btn").show();
        $('#location-form-btn').show();
        $("#left-cancel-submit-div").hide();
        $('#location-dropdown').attr("required", true);
        $('#right-cancel-submit-div').show();

    });
}
function displayNewLocationForm() {
    $('#choose-or-create-new').hide();
}


function loadUpHide() {
    $('#loc-edit').hide();
    $('#right-cancel-submit-div').hide();
}