const addModuleModalBtn = $('#add-module-modal-form');
const editModuleModalBtn = $('.edit-button');
const modalWindow = $('#modal-form');


addModuleModalBtn.click(function () {
    let link =window.location.pathname + '/addModule';
    modalWindow.find('#exampleModalLabel').text("New module");
    modalWindow.find('#submit-btn').text('Add');
    modalWindow.find('#add-edit-form').attr('action',link);
    modalWindow.modal('show');
});

editModuleModalBtn.click(function () {
    let moduleId = $(this).val();
    let link =window.location.pathname + '/editModule/'+moduleId;
    let oldModuleName = $('.module-container').find('#'+moduleId).text();
    modalWindow.find('#exampleModalLabel').text("Edit module");
    modalWindow.find('#module-name').val(oldModuleName);
    modalWindow.find('#submit-btn').text('Save');
    modalWindow.find('#add-edit-form').attr('action',link);
    modalWindow.modal('show');
})
