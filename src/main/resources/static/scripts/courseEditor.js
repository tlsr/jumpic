const addModuleModalBtn = $('#add-module-modal-form');
const addChapterModalBtn = $('.add-chapter-btn');
const editModuleModalBtn = $('.edit-button');
const modalWindowForModule = $('#modal-form-module-edit');
const modalWindowForChapter = $('#modal-form-chapter-edit');

console.log(modalWindowForModule);
addModuleModalBtn.click(function () {
    let link =window.location.pathname + '/addModule';
    modalWindowForModule.find('#exampleModalLabel').text("New module");
    modalWindowForModule.find('#submit-btn').text('Add');
    modalWindowForModule.find('#add-edit-form').attr('action',link);
    modalWindowForModule.modal('show');
});

editModuleModalBtn.click(function () {
    let moduleId = $(this).val();
    let link =window.location.pathname + '/editModule/'+moduleId;
    let oldModuleName = $('.module-container').find('#'+moduleId).text();
    modalWindowForModule.find('#exampleModalLabel').text("Edit module");
    modalWindowForModule.find('#module-name').val(oldModuleName);
    modalWindowForModule.find('#submit-btn').text('Save');
    modalWindowForModule.find('#add-edit-form').attr('action',link);
    modalWindowForModule.modal('show');
});
addChapterModalBtn.click(function () {
    let moduleId = $(this).val();
    let link =window.location.pathname +'/'+ moduleId+'/addChapter';
    modalWindowForChapter.find('#add-edit-form').attr('action',link);
    modalWindowForChapter.find('#modal-label-input').text('Chapter name');
    modalWindowForChapter.find('#exampleModalLabel').text("New Chapter");
    modalWindowForChapter.modal('show');
});
