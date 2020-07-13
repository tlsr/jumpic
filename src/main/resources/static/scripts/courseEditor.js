const addModuleModalBtn = $('#add-module-modal-form');
const addChapterModalBtn = $('.add-chapter-btn');
const editModuleModalBtn = $('.edit-module-button');
const editChapterModalBtn = $('.edit-chapter-button');
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
    let oldModuleName = $('.module-container').find('#module-id-'+moduleId).text();
    modalWindowForModule.find('#exampleModalLabel').text("Edit module");
    modalWindowForModule.find('#module-name').val(oldModuleName);
    modalWindowForModule.find('#submit-btn').text('Save');
    modalWindowForModule.find('#add-edit-form').attr('action',link);
    modalWindowForModule.modal('show');
});
editChapterModalBtn.click(function () {
    let moduleId = $(this).data("module-id");
    let chapterId = $(this).data("chapter-id");
    console.log("module "+moduleId);
    console.log("chapter "+chapterId)
    let link =window.location.pathname + '/'+moduleId+'/'+'editChapter/'+chapterId;
    let oldModuleName = $('.chapter-container').find('#'+chapterId).text();
    modalWindowForChapter.find('#exampleModalLabel').text("Edit chapter");
    modalWindowForChapter.find('#chapter-name').val(oldModuleName);
    modalWindowForChapter.find('#submit-btn').text('Save');
    modalWindowForChapter.find('#add-edit-form').attr('action',link);
    modalWindowForChapter.modal('show');
});
addChapterModalBtn.click(function () {
    let moduleId = $(this).val();
    let link =window.location.pathname +'/'+ moduleId+'/addChapter';
    modalWindowForChapter.find('#add-edit-form').attr('action',link);
    modalWindowForChapter.find('#modal-label-input').text('Chapter name');
    modalWindowForChapter.find('#exampleModalLabel').text("New Chapter");
    modalWindowForChapter.modal('show');
});
