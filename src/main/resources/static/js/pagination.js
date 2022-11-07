const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
});
let _currentPage = params.page;
if (_currentPage == null) {
    _currentPage = 1;
}

let _size = params.size;
if (_size == null) {
    _size = 20;
}

let _tab = params.tab;
if (_tab == null) {
    _tab = "articles";
}

const href = window.location.href;
const pathname = window.location.pathname;
console.log(href);
console.log(pathname);

$(document).ready(function() {
    console.log( _totalCount + "개의 글");
    renderPagination(_currentPage);
});

function renderPagination(_currentPage) {
    if (_totalCount <= _size) return;

    var totalPage = Math.ceil(_totalCount / _size);
    var pageGroup = Math.ceil(_currentPage / 10);

    var last = pageGroup * 10;
    if (last > totalPage) last = totalPage;
    var first = last - (10 - 1) <= 0 ? 0 : last - (10 - 1);
    var next = last + 1;
    var prev = first - 1;

    const fragmentPage = document.createDocumentFragment();
    if (prev > 0) {
        var allpreli = document.createElement('li');
        allpreli.classList.add('page-item');
        allpreli.insertAdjacentHTML("beforeend", `<a href="${pathname}?tab=${_tab}&page=${1}&size=${_size}" id='allprev' class='page-link'>&lt;&lt;</a>`);

        var preli = document.createElement('li');
        preli.classList.add('page-item');
        preli.insertAdjacentHTML("beforeend", `<a href="${pathname}?tab=${_tab}&page=${prev}&size=${_size}" id='prev' class='page-link'>&lt;</a>`);

        fragmentPage.appendChild(allpreli);
        fragmentPage.appendChild(preli);
    }

    for (var i = first; i <= last; i++) {
        const li = document.createElement("li");
        li.classList.add('page-item');
        li.insertAdjacentHTML("beforeend", `<a href="${pathname}?tab=${_tab}&page=${i}&size=${_size}" id='page-${i}' data-num='${i}' class='page-link'>${i}</a>`);
        fragmentPage.appendChild(li);
    }

    if (last < totalPage) {
        var allendli = document.createElement('li');
        allendli.classList.add('page-item');
        allendli.insertAdjacentHTML("beforeend", `<a href="${pathname}?tab=${_tab}&page=${totalPage}&size=${_size}" id='allnext' class='page-link'>&gt;&gt;</a>`);

        var endli = document.createElement('li');
        endli.classList.add('page-item');
        endli.insertAdjacentHTML("beforeend", `<a href="${pathname}?tab=${_tab}&page=${next}&size=${_size}" id='next' class='page-link'>&gt;</a>`);

        fragmentPage.appendChild(endli);
        fragmentPage.appendChild(allendli);
    }

    document.getElementById('js-pagination').appendChild(fragmentPage);
    // 페이지 목록 생성

    $(`#js-pagination a`).removeClass("active");
    $(`#js-pagination a#page-${_currentPage}`).addClass("active");

};

    function toggleCheckbox(element) {
    if (element.checked) {
        document.getElementById("noticeArticles").style.display = "none";
    } else {
        document.getElementById("noticeArticles").style.display = "table-row-group";
    }
}

function selectSize() {
    var size = document.getElementById('size');
    if(size.value) location.href=(pathname + "?page=1&size=" + size.value);
}

