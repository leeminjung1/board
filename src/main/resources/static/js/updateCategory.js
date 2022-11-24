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

        for(let i = 0; i < result.length; i++){
//                updateArr[i].priority = result.indexOf(i);
            categories.find(element => element.id == Number(result[i])).priority = i;
//                categories[Number(result[i])].priority = i;
        }
        for(let i = 0; i < categories.length; i++) {
            updateArr.push(categories[i]);
            console.log(categories[i]);
        }

        $.ajax({
             type : "POST",
             url : "/manage/category",
             contentType: "application/json",
             data : JSON.stringify(paramList)
        }).done(res=>{
            console.log("성공");
//            window.location.reload();
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
        },
    });
});

function addNewCategory(name){
    var category = {
        "id" : idx,
        "name"  : name,
        "priority" : Math.max(...categories.map(o => o.priority)) + 1,
        "parentId" : 1,
    }
    appendArr.push(category);
    categories.push(category);
}

function editName(id){
    var category = categories.find(element => element.id == Number(id));
    $('#'+ id).children().children().hide();
    $('#'+ id).children().append('<input type="text" name="categoryName" placeholder=' + category.name + '></input>');
    $('#'+ id).children().append('<a class="btn" href="javascript:;" onclick="cancelEditName(' + id + ')">취소</a>');
    $('#'+ id).children().append('<a class="btn" href="javascript:;" onclick="submitEditName(' + id + ')">확인</a>');
}

function cancelEditName(id){
    $('#'+ id).children().children('input').remove();
    $('#'+ id).children().children('a').remove();
    $('#'+ id).children().children().removeAttr("style");
}

function submitEditName(id){
    var name =  $('input[name=categoryName]').val();
    categories.find(element => element.id == Number(id)).name = name;
    console.log(categories.find(element => element.id == Number(id)));

    $('#'+ id).children().children('input').remove();
    $('#'+ id).children().children('a').remove();
    $('#'+ id).children().children().removeAttr("style");
    $('#'+ id).children().children('.fw-bold').text(name);
}

function deleteCategory(id) {
    deleteArr.push(id);
//    const idx = categories.indexOf(element=>element.id = id);
//    if (idx > -1) a.splice(idx, 1)
    categories.splice(categories.indexOf(element=>element.id = id), 1);
    $('#'+ id ).hide();
}