/* 
 *      Superhero Sightings
 *      createSuperhero.js
 *      
 */

$(document).ready(function () {
    showCreateNewOrganizationForm();
    initialHide();
    //  showCreateNewLocationForm();

});

function showCreateNewOrganizationForm() {
    $('#create-new-org-btn').click(function (event) {
        $('#add-org-btns').hide();
        $('#organization-dropdown').get(0).selectedIndex = 0;
        $('#add-new-org').show();
        addRequiredPropToOrganization();

 });
}

function showCreateNewLocationForm() {
    $('#create-new-location-btn').click(function (event) {
        $('#div-for-new-location-btn').hide();
        $('#organization-dropdown').get(0).selectedIndex = 0;
        $('#div-for-add-location').show();
    });
}
   
function initialHide() {
    $('#add-new-org').hide();
    $('#div-for-add-location').hide();

}

function addRequiredPropToOrganization() {
    $('#add-org-name').prop('required', true);
    $('#add-org-description').prop('required', true);
    $('#add-org-contact').prop('required', true);

}