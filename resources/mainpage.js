var selNum = 0;
var rolling;
var countingTime;
var $promoList = $(".visual_img");
var totalNum = $(".visual_img li").length;
var productPageNum = 0;
var limitRow = 4;

$(function () {

    startRolling();
    showCategorys();
    productByFilter(1);
});

// 카테고리들 가져와서 위치시키기.
function showCategorys() {
    $.ajax({
        url: './main/api/categorys',
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var list = res;
            for (var i = 0; i < list.length; i++) {
                var li = $("<li></li>", {
                    class: 'item',
                    'data-category': list[i].id
                });
                var span = $("<span></span>").text(list[i].name);
                if (i === list.length - 1) {
                    var a = $("<a></a>").addClass("anchor last");
                } else {
                    var a = $("<a></a>").addClass("anchor");
                }
                a.html(span);
                li.html(a);
                $(".event_tab_lst.tab_lst_min").append(li);
            }
        }

    });
}

//카테고리 이벤트 - 통신해서 공연리스트 받기
function productByFilter(id) {
    $.ajax({
        url: './main/api/categorys/count/' + id,
        success: function (res) {
            $(".pink").text(res.count + '개');
        }
    });

    $.ajax({
        url: './main/api/categorys/' + id,
        success: function (res) {
            var list = res;

            //Element만들기
            clearProductElement();
            makeProductElement(list);

            productPageNum++;
        }
    });
}

function clearProductElement() {
    $(".lst_event_box .item").remove();
}

function makeProductElement(list) {
    var whichSide = $(".lst_event_box");
    
    for (var i = 0; i < list.length; i++) {
        var li = $("<li></li>", {
            class: 'item',
        });
        
        var a = $("<a></a>", {
            href: '#',
            class: 'item_book',
        });
        var div_item_preview = $("<div></div>", {
            class: 'item_preview'
        });
        var img = $("<img>", {
            alt: "뮤지컬 로미오와 줄리엣",
            class: 'img_thumb',
            src: "https://ssl.phinf.net/naverbooking/20170111_225/1484116579024rNkXW_JPEG/2016_%B9%C2%C1%F6%C4%C3_%C0%CE_%B4%F5_%C7%CF%C0%CC%C3%F7_%C6%F7%BD%BA%C5%CD%2820MB%29.jpg?type=l591_945" 
        });
        var span_img_border = $('<span></span', {
            class: 'img_border'
        });
        var div_event_txt = $('<div></div>', {
            class: 'event_txt'
        });
        var h4_event_txt_tit = $('<h4></h4>', {
            class: 'event_txt_tit'
        });
        var span_event_txt_tit = $('<span></span>').text(list[i].name);
        var small = $('<small></small>', {
            class: 'sm'
        }).text('location text');
        var p_event_txt_dsc = $('<p></p>', {
            class: 'event_txt_dsc'
        }).text(list[i].description);

        h4_event_txt_tit.append(span_event_txt_tit);
        h4_event_txt_tit.append(small);
        div_event_txt.append(h4_event_txt_tit);
        div_event_txt.append(p_event_txt_dsc);
        div_item_preview.append(img);
        div_item_preview.append(span_img_border);
        a.append(div_item_preview);
        a.append(div_event_txt);
        li.append(a);


        if (i % 2 === 0) {
            // $(whichSide).get(0)에 추가
            $(whichSide).eq(0).append(li);
        } else {
            // $(whichSide).get(1)에 추가
            $(whichSide).eq(1).append(li);
        }
    }
}

//카테고리 이벤트 - active 달고 떼기
$(".section_event_tab").on("click", ".anchor", function () {
    productPageNum = 0;
    var categorys = $(".anchor");
    for (var i = 0; i < categorys.length; i++) {
        if ($(categorys[i]).hasClass("active")) {
            $(categorys[i]).removeClass("active");
        }
    }
    $(this).addClass("active");
    var categoryId = $(this).closest(".item").attr("data-category");

    productByFilter(categoryId);
});

//프로모션 다음방향으로 움직이기
function moveNext() {
    selNum = selNum + 1;
    if (selNum >= totalNum) {
        selNum = 0;
    }

    $promoList.stop().animate({ left: (-1) * selNum * 349 + 'px' }, { duration: 500 });
}

//프로모션 이전방향으로 움직이기
function movePrev() {
    selNum = selNum - 1;
    if (selNum < 0) {
        selNum = totalNum - 1;
    }

    $promoList.stop().animate({ left: (-1) * selNum * 349 + 'px' }, { duration: 500 });
}

function stopRolling() {
    clearInterval(rolling);
}

function startRolling() {
    rolling = setInterval(function () { moveNext(); }, 2000);
}

function startCountTime() {
    countingTime = setInterval(function () { startRolling(); stopCountTime(); }, 4000);
}

function stopCountTime() {
    clearInterval(countingTime);
}

function giveMoreProduct() {
    var id = $(".anchor.active").parent().attr("data-category");
    var str = $(".pink").get(0).innerText;
    str = String(str);
    var num = Number(str.substring(0, str.length-1));
    if(num-productPageNum*limitRow <= 0) {
        return false;
    }
    $.ajax({
         url: './main/api/categorys/' + id +'/offset/' + productPageNum,
         success: function (res) {
             productPageNum++;
             makeProductElement(res);
        }
    });
}

function myReserve() {
    $.ajax({
		url:'./myreservation',
		
	});		
}

$(".btn_pre_e").on("click", function () {
    if ($promoList.is(":animated")) {
        return false;
    }
    stopCountTime();
    stopRolling();
    movePrev();
    startCountTime();
});

$(".btn_nxt_e").on("click", function () {
    if ($promoList.is(":animated")) {
        return false;
    }
    stopCountTime();
    stopRolling();
    moveNext();
    startCountTime();
});

$(".more .btn").on("click", giveMoreProduct);

$(".btn_my").on("click", function() {
		myReserve();
});

// $(".visual_img .item > a").on("click", function() {
//     console.log("1");
//     $.ajax({
//         url: './detail'
//     });
// });