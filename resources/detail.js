var salesFlag;
var productEvent;
var description;
var name;
var selNum = 0;
// var totalNum = $(".visual_img li").length;
var productImageList = $(".visual_img");
var url = $(location).attr('href').split('/');
var lastIdx = url.length - 1;
var id = url[lastIdx];
var commentImgList;


$(function () {
    pageInit();
});

function pageInit() {
    $.ajax({
        url: '/detail/api/' + id,
        success: function (res) {
            salesFlag = res.salesFlag;
            productEvent = res.event;
            name = res.name;
            description = res.description;
            $(".dsc").text(description);
            hasEvent();
            showProductImages();
            showComments();
        }
    });
}

function showComments() {
    $.ajax({
        url: '/detail/api/comments/' + id,
        success: function (res) {
            commentImgList = res;
            $(".img_item img.review_img").attr("src", res[0].imgList[0].saveFileName);
            makeCommentElement(res);
        }
    });

    $.ajax({
        url: '/review/api/' + id,
        success: function (res) {
            $(".grade_area .text_value > span").text(res.score);
            var graphMask = res.score / 5.0 * 100;
            $(".graph_value").css("width", graphMask + "%");
            $(".green").text(res.count + "건");
            if (res.count < 3) {
                $(".btn_review_more").css("display", "none");
            } else {
                $(".btn_review_more").attr("href", "/review/" + id);
            }
        }
    });
}

function makeCommentElement(res) {
    var commentList = $(".list_short_review");
    var source = $("#detail-comment-template").html();
    var template = Handlebars.compile(source);
    for (var i = 0; i < res.length; i++) {
        var html = template(res[i]);
        commentList.append(html);
        $(".review_area img.img_vertical_top").eq(i).attr("src", res[i].imgList[0].saveFileName);
    }
}

function moveNext() {
    var totalNum = $(".visual_img li").length;
    selNum = selNum + 1;
    if (selNum >= totalNum) {
        selNum = 0;
    }
    $(".num").eq(0).text(selNum + 1);

    productImageList.stop().animate({
        left: (-1) * selNum * 414 + 'px'
    }, {
        duration: 500
    });
}

function movePrev() {
    var totalNum = $(".visual_img li").length;
    selNum = selNum - 1;
    if (selNum < 0) {
        selNum = totalNum - 1;
    }
    $(".num").eq(0).text(selNum + 1);

    productImageList.stop().animate({
        left: (-1) * selNum * 414 + 'px'
    }, {
        duration: 500
    });
}

function makeProductImageElement(res) {
    var source = $("#detail-product-image-template").html();
    var template = Handlebars.compile(source);
    for (var i = 0; i < res.length; i++) {
        var html = template(res[i]);
        productImageList.append(html);
    }
}

function showProductImages() {
    $.ajax({
        url: '/files/' + id,
        success: function (res) {
            makeProductImageElement(res);
            $(".num.off > span").text($(".visual_img li").length);
            $(".visual_txt_tit > span").eq(0).text(name);
            $(".visual_txt_dsc").eq(0).text(description);
        }
    });
}

$(".bk_more").on("click", function (event) {
    event.preventDefault();
    if ($(this).hasClass("_open")) {
        $(".store_details").removeClass("close3");
        $(".bk_more._open").css("display", "none");
        $(".bk_more._close").removeAttr("style");
    } else {
        $(".store_details").addClass("close3");
        $(".bk_more._close").css("display", "none");
        $(".bk_more._open").removeAttr("style");
    }
});

$(".bk_btn").on("click", function () {
    if (salesFlag === 0) {
        alert("예매~~");
    } else if (salesFlag === 1) {
        alert("판매종료");
    } else {
        alert("매진");
    }
});

function hasEvent() {
    if (productEvent === "none") {
        $(".section_event").css("display", "none");
        console.log("빔");
    } else {
        console.log("안빔");
        $(".event_info .in_dsc").text(productEvent);
    }
}

productImageList.on("touchstart", singleTouchStart);
productImageList.on("touchmove", singleTouchMove);
productImageList.on("touchend", singleTouchEnd);

var startX;
var endX;

function singleTouchStart(event) {
    startX = event.touches[0].pageX;
}

function singleTouchMove(event) {
    endX = event.touches[0].pageX;
}

function singleTouchEnd(event) {
    if (endX === null) {
        return false;
    }
    if (startX - endX > 0) {
        moveNext();
    } else if (startX - endX < 0) {
        movePrev();
    } else {
        return false;
    }
    endX = null;
    endY = null;
}



$(".prev_inn").on("click", function () {
    movePrev();
});

$(".nxt_inn").on("click", function () {
    moveNext();
});

$(".info_tab_lst .item .anchor").on("click", function (event) {
    event.preventDefault();
    var items = $(".info_tab_lst .anchor");
    for (var i = 0; i < items.length; i++) {
        if ($(items[i]).hasClass("active")) {
            $(items[i]).removeClass("active");

        }
        $(this).addClass("active");
    }

    if ($(".info_tab_lst .anchor.active > span").text() === "상세정보") {
        $.ajax({
            url: "/detail/api/info/" + id,
            success: function (res) {
                console.dir(res);
            }
        });
        $(".detail_location").addClass("hide");
        $(".detail_area_wrap").removeClass("hide");
    } else {
        $.ajax({
            url: "/detail/api/path/" + id,
            success: function (res) {
                $(".store_name").text(res.name);
                $(".store_addr.store_addr_bold").text(res.placeStreet);
                $(".store_addr .addr_old_detail").text(res.placeLot);
                $(".store_addr.addr_detail").text(res.placeName);
                $(".item_rt > a").attr("href", "tel:" + res.tel);
                $(".item_rt > a").text(res.tel);
                addressToXy(res.placeStreet);
            }
        });
        $(".detail_area_wrap").addClass("hide");
        $(".detail_location").removeClass("hide");
    }
});

function showReviewImages(cmtId) {
    $(".sec_img ul.img_list").children().remove();
    var source = $("#review-images-template").html();
    var template = Handlebars.compile(source);

    for (var i = 0; i < commentImgList[cmtId - 1].imgList.length; i++) {
        var html = template(commentImgList[cmtId - 1].imgList[i]);
        $(".img_list").append(html);
    }
    $(".img_list .img_item").eq(0).css("margin-left", "0px");
}

$(".list_short_review").on("click", "a.thumb", function (event) {
    event.preventDefault();
    popupSelNum = 0;
    popupImageList.css("left", "0px");
    $(".my_popup_layer").css("display", "inline");
    showReviewImages($(this).attr("id"));
});

$("#btn_x").on("click", function () {
    $(".my_popup_layer").css("display", "none");
});


var popupSelNum = 0;
var popupImageList = $(".img_list");
$("#btn_popup_prev").on("click", function () {
    var totalNum = $(".img_list li").length;

    popupSelNum = popupSelNum - 1;
    if (popupSelNum < 0) {
        popupSelNum = totalNum - 1;
    }

    popupImageList.stop().animate({
        left: (-1) * popupSelNum * 414 + 'px'
    }, {
        duration: 500
    });
});

$("#btn_popup_next").on("click", function () {
    var totalNum = $(".img_list li").length;
    popupSelNum = popupSelNum + 1;
    if (popupSelNum >= totalNum) {
        popupSelNum = 0;
    }

    popupImageList.stop().animate({
        left: (-1) * popupSelNum * 414 + 'px'
    }, {
        duration: 500
    });
});

function addressToXy(queryAddress) {
    naver.maps.Service.geocode({
        address: queryAddress
    }, function (status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('Something wrong!');
        }

        var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열

        // do Something
        console.dir(response.result.items[0].point.x);
        console.dir(response.result.items[0].point.y);
    });
}