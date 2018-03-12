/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    initialHide();
    $('#organization-dropdown').attr("required", true);
    showCreateNewOrganizationForm();

});


function showCreateNewOrganizationForm() {
    $('#create-new-org-btn').click(function (event) {
        addRequiredPropToOrganization();
        $('#organization-dropdown').get(0).selectedIndex = 0;
        $('#organization-dropdown').attr("required", false);
        $('#location-dropdown').attr("required", true);
        $('#add-new-org').show();
        $('#first-submit-section').hide();
        showCreateNewLocationForm();
    });
}

function addRequiredPropToOrganization() {
    $('#add-org-name').prop('required', true);
    $('#add-org-description').prop('required', true);
    $('#add-org-contact').prop('required', true);

}

function showCreateNewLocationForm() {
    $('#create-new-location-btn').click(function (event) {
        $('#div-for-new-location-btn').hide();
        $('#location-dropdown').get(0).selectedIndex = 0;
        $('#div-for-add-location').show();
        $('#location-dropdown').attr("required", false);
        $('#submit-superhero-new-orgloc').hide();

    });
}

function initialHide() {
    $('#add-new-org').hide();
    $('#div-for-add-location').hide();

}