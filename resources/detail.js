var url = $(location).attr('href').split('/');
var lastIdx = url.length - 1;
var id = url[lastIdx];

var commentImgList;

var salesFlag;
var productEvent;
var description;
var name;

var selNum = 0;
var productImageList = $(".visual_img");

var PageModule = (function() {

    return {

    };
})();

$(function () {
    pageInit();
});

/*
처음에 url을 얻어내서 
*/
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

/*
그 상품의 리뷰를 3개 받아
리뷰 사진들 모아보는 레이어 팝업에 사진을 띄우기 위해
response를 commentImgList에 저장해둠.

모든 리뷰 갯수와 그 리뷰들의 점수 총합을 내려받아
화면처리.
*/
function showComments() {
    $.ajax({
        url: '/detail/api/comments/' + id,
        success: function (res) {
            commentImgList = res;
            // $(".img_item img.review_img").attr("src", res[0].imgList[0].saveFileName);
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

// handlebarjs를 이용해 리뷰 엘리먼트들을 만듬
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

// 상품 사진 다음으로 이동
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

// 상품 사진 이전으로 이동
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


// handlebarjs를 이용해서 상품사진 엘리먼트를 만듬
function makeProductImageElement(res) {
    var source = $("#detail-product-image-template").html();
    var template = Handlebars.compile(source);
    for (var i = 0; i < res.length; i++) {
        var html = template(res[i]);
        productImageList.append(html);
    }
}

// 통신해서 상품 이미지들 받아내림
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

// 펼쳐보기 접기 이벤트
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

// 예매 버튼 이벤트
$(".bk_btn").on("click", function () {
    if (salesFlag === 0) {
        console.log("예매~~");
        location.href="/reservations/book/" + id;
    } else if (salesFlag === 1) {
        alert("판매종료");
    } else {
        alert("매진");
    }
});

// 이벤트 정보가 있는지 없는지 체크
function hasEvent() {
    if (productEvent === "none") {
        $(".section_event").css("display", "none");
        console.log("빔");
    } else {
        console.log("안빔");
        $(".event_info .in_dsc").text(productEvent);
    }
}

productImageList.on({
    touchstart: singleTouchStart,    
    touchmove: singleTouchMove,
    touchend: singleTouchEnd
});

var startX;
var endX;

// 처음 터치했을때 좌표 저장
function singleTouchStart(event) {
    startX = event.touches[0].pageX;
}

// 터치한채로 멈춘 곳의 좌표 저장
function singleTouchMove(event) {
    endX = event.touches[0].pageX;
}

// 처음 좌표와 이동한 좌표의 방향을 구해서 그림 이동
// 한번 터치만 하는 경우가 있으므로 좌표 초기화
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


// 상품 사진 이전방향으로 이동
$(".prev_inn").on("click", movePrev);

// 상품 사진 다음방향으로 이동
$(".nxt_inn").on("click", moveNext);

// 상세정보와 오시는 길 이벤트
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

// handlebarjs사용하여 모든 리뷰 이미지들 엘리먼트 만듬.
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

// 레이어 팝업 이벤트
$(".list_short_review").on("click", "a.thumb", function (event) {
    event.preventDefault();
    popupSelNum = 0;
    popupImageList.css("left", "0px");
    $(".my_popup_layer").css("display", "inline");
    showReviewImages($(this).attr("id"));
});

// 레이어 팝업의 창닫기 버튼 이벤트
$("#btn_x").on("click", function () {
    $(".my_popup_layer").css("display", "none");
});


var popupSelNum = 0;
var popupImageList = $(".img_list");

// 레이어 팝업의 이전방향 이벤트
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

// 레이어 팝업의 다음 방향 이벤트
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

// 주소지를 입력받아 좌표를 계산하고 이미지를 그림
function addressToXy(queryAddress) {
    naver.maps.Service.geocode({
        address: queryAddress
    }, function (status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('Something wrong!');
        }

        var mapImgPath = "https://openapi.naver.com/v1/map/staticmap.bin?clientId=xG6IjQpP8c8ePRi3w__i&w=640&h=300&baselayer=default&url=http://localhost:8080/&level=11";
        var center = "&center="+(response.result.items[0].point.x)+","+(response.result.items[0].point.y);
        var markers = "&markers="+(response.result.items[0].point.x)+","+(response.result.items[0].point.y);
        mapImgPath = mapImgPath + center + markers;
        $(".store_location").attr("href", "http://map.naver.com/?query="+queryAddress);
        $(".store_location > img").attr("src", mapImgPath);
    });
}



/*
페이지 관련
상품 사진
리뷰
상세정보
지도
*/