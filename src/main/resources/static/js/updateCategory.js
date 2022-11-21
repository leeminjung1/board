var idx = -1;
var dataArray = [];
var appendArr = [];
var deleteArr = [];
var updateArr = [];
var result = [];

$(document).ready(function() {
    var add_button = $(".add_category_button");
    var wrapper = $(".wrap");

    $(add_button).click(function(e){
        e.preventDefault();
        $(wrapper).append('<div><div class="px-3 py-2 border bg-white"><form><input type="text" name="name" class="form-control"><div class="input-group-append d-inline-block"><button class="btn remove_field" type="reset">취소</button></div><div class="input-group-append d-inline-block"><button class="btn addCategoryBtn" type="button">확인</button></div></form></div></div>');
    });

    $(wrapper).on("click",".remove_field", removeField);

    $(wrapper).on("click",".addCategoryBtn", function(e){
        e.preventDefault();
        var categoryName = $('form input[name=name]').val();
        addNewCategory(categoryName);
        removeField();
        $('#categoryList').append('<div class="ui-sortable-handle" id="' + idx-- + '"><div class="px-3 py-2 border bg-white fw-bold">'+categoryName+'</div></div>');
    });


    $(".submitUpdateCategory").on("click", function(e){
        var paramList = {
            "delete": deleteArr,
            "append": appendArr,
            "update": updateArr
        }

        $.ajax({
             type : "POST",
             url : "/manage/category",
             contentType: "application/json",
             data : JSON.stringify(paramList)
        }).done(res=>{
            console.log("성공");
            window.location.reload();
        }).fail(error=>{
            console.log("오류", error);
        });
    });

});

function removeField() {
    $('.remove_field').parent('div').parent('form').parent('div').parent('div').remove();
}

$(function() {
    $('#categoryList').sortable({
        update: function(event, ui) {
            result = $(this).sortable('toArray');

            console.log(result);
            for(let i = 0; i < result.length; i++){
                updateArr[i].priority = result.indexOf(i);
            }

        },
    });
});

function addNewCategory(name){
    var category = {
        "id" : idx,
        "name"  : name,
        "priority" : result.indexOf(idx.toString()),
        "parentId" : 1,
    }
    appendArr.push(category);
}

function editName(){
    document.getElementById("info_btn").style.display = "none";
    document.getElementById("category_item").style.display = "none";
    document.getElementById("edit_btn").style.display = "block";
    return false;
}

function cancelEditName(){
    document.getElementById("info_btn").style.display = "block";
    document.getElementById("category_item").style.display = "block";
    document.getElementById("edit_btn").style.display = "none";
}

function deleteCategory(id) {
    deleteArr.push(id);
    $('#'+ id ).hide();
}