$(document).ready(function(){
    var key = sessionStorage.getItem("hideNotice");
    if(key == '_on') {
        $("#hideNotice").attr("checked", true);
    }
    toggleHideNotice();
});

function toggleHideNotice() {
    if ($('#hideNotice').is(':checked')) {
        $('#article_list_area > table > tbody').first().children().hide();
        sessionStorage.setItem("hideNotice", "_on");
    } else {
        $('#article_list_area > table > tbody').first().children().show();
        sessionStorage.removeItem("hideNotice");
    }
}
