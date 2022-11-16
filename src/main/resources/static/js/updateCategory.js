$(document).ready(function() {
    var add_button = $(".add_category_button");
    var wrapper = $(".wrap");

    $(add_button).click(function(e){
        e.preventDefault();
        console.log("add category on click");
        $(wrapper).append('<div><div class="px-3 py-2 border bg-white"><form><input type="text" name="name" class="form-control"><div class="input-group-append d-inline-block"><button class="btn remove_field" type="reset">취소</button></div><div class="input-group-append d-inline-block"><button class="btn addCategoryBtn" type="button">확인</button></div></form></div></div>');
    });

    $(wrapper).on("click",".remove_field", removeField);

    $(wrapper).on("click",".addCategoryBtn", function(e){
        e.preventDefault();
        var name = $('form input[name=name]').val();
        addNewCategory(name);
        removeField();
        $('#categoryList').append('<div class="ui-sortable-handle"><div class="px-3 py-2 border bg-white fw-bold">'+name+'</div></div>');

    });
});

function removeField() {
    $('.remove_field').parent('div').parent('form').parent('div').parent('div').remove();
}

$(function() {
    $('#categoryList').sortable({
        update: function(event, ui) {
            var result = $(this).sortable('toArray');

             console.log(result);

        },
    });
});

function addNewCategory(name){
    var dataArray = [];
    var data1 = {
        "name" : name,
        "age"  : "21"
    }
    var data2 = {
       "name" : name,
       "age"  : "22"
    }
    dataArray.push(data1);
    dataArray.push(data2);
    var paramList = {
       "paramList" : JSON.stringify(dataArray)
    }
    $.ajax({
         type : "POST",
         url : "/memberInfo.do",
         data : paramList,
         success : function(data) {},
         error : function(e) {}
      });
    };


function onclickAddCategory(){
// var name = document.getElementById('newCategoryName').value;
//    var name = event.target.value
//    let categoryList = [[${categoryDto.categories}]];
//    let newCategory = {
//        id: idx--,
//        name: name,
//        depth: 1,
//        priority: 0,
//        parent: [[${categoryDto.categories[0]}]],
//    };
//    categoryList.push(newCategory);
//    console.log(categoryList);
};

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