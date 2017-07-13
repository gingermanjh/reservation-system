var productPageNum = 0;

$(function () {

    promotionModule.init();
    categoryModule.init();
    productModule.init();
});

var promotionModule = (function () {
    var selNum = 0;
    var rolling;
    var countingTime;
    var $promoList = $(".visual_img");
    var totalNum = $(".visual_img li").length;

    var init = function () {
        startRolling();

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

    };

    var stopRolling = function () {
        clearInterval(rolling);
    }

    var startRolling = function () {
        rolling = setInterval(function () { moveNext(); }, 2000);
    }

    var startCountTime = function () {
        countingTime = setInterval(function () { startRolling(); stopCountTime(); }, 4000);
    }

    var stopCountTime = function () {
        clearInterval(countingTime);
    }

    //프로모션 다음방향으로 움직이기
    var moveNext = function () {
        selNum = selNum + 1;
        if (selNum >= totalNum) {
            selNum = 0;
        }

        $promoList.stop().animate({ left: (-1) * selNum * 349 + 'px' }, { duration: 500 });
    }

    //프로모션 이전방향으로 움직이기
    var movePrev = function () {
        selNum = selNum - 1;
        if (selNum < 0) {
            selNum = totalNum - 1;
        }

        $promoList.stop().animate({ left: (-1) * selNum * 349 + 'px' }, { duration: 500 });
    }

    return {
        init: init
    };

})();

var productModule = (function () {

    var limitRow = 4;

    var init = function () {
        productByFilter(1);
        $(".more .btn").on("click", giveMoreProduct);
    };

    //카테고리 이벤트 - 통신해서 공연리스트 받기
    var productByFilter = function (id) {
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
                makeProductElementbyHandlebar(list);

                productPageNum++;
            }
        });
    };

    var clearProductElement = function () {
        $(".lst_event_box .item").remove();
    };

    var giveMoreProduct = function () {
        var id = $(".anchor.active").parent().attr("data-category");
        var str = $(".pink").get(0).innerText;
        str = String(str);
        var num = Number(str.substring(0, str.length - 1));
        if (num - productPageNum * limitRow <= 0) {
            return false;
        }
        $.ajax({
            url: './main/api/categorys/' + id + '/offset/' + productPageNum,
            success: function (res) {
                productPageNum++;
                makeProductElementbyHandlebar(res);
            }
        });
    };

    // handlebarsjs 이용해서 메인페이지 프로덕트 엘리멘트 만들기
    var makeProductElementbyHandlebar = function (list) {
        var whichSide = $(".lst_event_box");
        var source = $("#main-product-template").html();
        var template = Handlebars.compile(source);
        for (var i = 0; i < list.length; i++) {
            var html = template(list[i]);

            if (i % 2 === 0) {
                // $(whichSide).eq(0)에 추가
                $(whichSide).eq(0).append(html);
            } else {
                // $(whichSide).eq(1)에 추가
                $(whichSide).eq(1).append(html);
            }
        }

    }

    return {
        init: init,
        productByFilter: productByFilter
    };

})();

var categoryModule = (function () {

    // 통신해서 카테고리 가져오기
    // 카테고리 엘리먼트에 이벤트 핸들러 달기
    var init = function () {
        showCategories();
        $(".section_event_tab").on("click", ".anchor", changeCategoryStyle);
    };

    // 카테고리 이벤트 - active 달고 떼기
    var changeCategoryStyle = function () {
        productPageNum = 0;
        var categorys = $(".anchor");
        for (var i = 0; i < categorys.length; i++) {
            if ($(categorys[i]).hasClass("active")) {
                $(categorys[i]).removeClass("active");
            }
        }
        $(this).addClass("active");
        var categoryId = $(this).closest(".item").attr("data-category");

        productModule.productByFilter(categoryId);
    };

    // Handlebarsjs 이용해서 카테고리들 가져와서 위치시키기.
    var showCategories = function () {
        $.ajax({
            url: './main/api/categorys',
            datatype: 'json',
            type: 'GET',
            success: function (res) {
                var list = res;
                var source = $("#main-category-template").html();
                var template = Handlebars.compile(source);

                for (var i = 0; i < list.length; i++) {
                    var html = template(res[i]);
                    $(".event_tab_lst.tab_lst_min").append(html);
                }
            }

        });
    };

    return {
        init: init
    };
})();

function myReserve() {
    $.ajax({
        url: './myreservation',

    });
}

$(".btn_my").on("click", function () {
    myReserve();
});

// $(".visual_img .item > a").on("click", function() {
//     console.log("1");
//     $.ajax({
//         url: './detail'
//     });
// });