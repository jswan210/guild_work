/* 
 * Superhero Organization app
 *      createOrganization.js 
 */

$(document).ready(function () {
    $('#add-location-form').hide();
    showCreateNewLocationForm();
    cancelCreateNewLocation();
    $('#location-dropdown').attr("required", true);

});

function showCreateNewLocationForm() {
    $('#create-new-location-btn').click(function (event) {
        $('#loc-dropdown').hide();
        addRequired();
        $('#new-organization').hide();  //submit button after right side form
        $('#create-new-location-btn').hide();
        $('#add-location-form').show();
        $('#location-dropdown').get(0).selectedIndex = 0;
        $("#close-new-location").show();
        $('#location-dropdown').attr("required", false);
    });
}

function cancelCreateNewLocation() {
    $("#close-new-location").click(function (event) {
        $('#loc-dropdown').show();
        $('#create-new-location-btn').show();
        $('#add-location-form').hide();
        $("#close-new-location").hide();
        $('#new-organization').show();
        $('#location-dropdown').attr("required", true);
    });
}

function addRequired() {
    $('#add-zip').prop('required', true);
    $('#add-state').prop('required', true);
    $('#add-city').prop('required', true);
    $('#add-address').prop('required', true);
    $('#add-description').prop('required', true);
    $('#add-name').prop('required', true);

}
