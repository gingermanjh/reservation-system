var url = $(location).attr('href').split('/');
var lastIdx = url.length - 1;
var id = url[lastIdx];

var userId;


$(function () {
    reservePage.init();
})

var reservePage = (function () {
    var displayTime;

    function init() {
        showProductImages();
        getProductInfo(id);
    };

    function showProductImages() {
        $(".visual_img img.img_thumb").attr("src", "/files/thumb/" + id);
    };

    function getProductInfo(id) {
        $.ajax({
            url: '/reservations/info/' + id,
            success: function (res) {
                console.log(res);

                $(".top_title span.title").text(res.name);
                $(".preview_txt .preview_txt_tit").text(res.name);
                $(".store_details .in_tit").eq(0).text(res.name);

                var productLocation = "장소 : " + res.placeStreet + "(" + res.placeLot + ")";
                var displayStart = timestampToDate(res.displayStart);
                var displayEnd = timestampToDate(res.displayEnd);
                displayTime = displayStart + '~' + displayEnd;
                $(".preview_txt .preview_txt_dsc").eq(1).text(displayTime);
                $(".store_details .dsc").eq(0).html(productLocation + "<br>" + displayTime);
                $(".store_details .dsc").eq(1).text(res.observationTime);
                $(".inline_form div.inline_control p.inline_txt").text(displayTime + ", 총 0매");
                if (res.users !== null) {
                    var $user = $(".inline_form div.inline_control input");
                    userId = res.users.id;
                    $user.eq(0).attr("value", res.users.username);
                    $user.eq(1).attr("value", res.users.tel);
                    $user.eq(2).attr("value", res.users.email);
                }
                makePriceElement(res);
            }
        });
    };

    function makePriceElement(res) {
        var priceType = ["일반", "청소년", "어린이", "대학생", "할인1", "할인2"];
        var source = $("#price-template").html();
        var template = Handlebars.compile(source);

        var priceList = res.priceList;
        var html = "";
        for (var i = 0; i < priceList.length; i++) {
            html = html + template(res[i]);
        }
        $(".ticket_body").append(html);

        var $type = $(".product_amount > span");
        var $price = $(".product_price span.price");
        var $originalPrice = $("em.product_dsc");
        for (var i = 0; i < priceList.length; i++) {
            var idx = priceList[i].priceType;
            $type.eq(i).text(priceType[idx - 1]);
            $price.eq(i).text(priceList[i].price * (1 - (priceList[i].discountRate)));
            $originalPrice.eq(i).text(priceList[i].price + "원 (" + (priceList[i].discountRate * 100) + "%할인가)");
        }
    }

    function getDisplayTime() {
        return displayTime;
    };

    function timestampToDate(val) {
        var dayOfWeek = ["(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"]
        var date = new Date(val);
        var idx = date.getDay();
        var dateString = date.getFullYear() + '.' + (date.getMonth() + 1) + '.' + date.getDate() + dayOfWeek[idx];
        return dateString;
    };

    return {
        init: init,
        getDisplayTime: getDisplayTime
    };
})();



$(".btn_agreement").on("click", function (event) {
    event.preventDefault();
    var $div = $(this).closest(".agreement");
    if ($div.hasClass("open")) {
        $div.removeClass("open");
    } else {
        $div.addClass("open");
    }
});

$(".ticket_body").on("click", "a.ico_plus3", function (event) {
    event.preventDefault();
    var $clearFix = $(this).closest(".clearfix");
    var $totalPrice = $(this).closest(".count_control").find("span.total_price");
    var count = $clearFix.find("input.count_control_input").attr("value") - 0;
    count++;
    var totalPrice = $totalPrice.text() - 0;
    var productPrice = $(this).closest(".qty").find("span.price").text() - 0;
    totalPrice = totalPrice + productPrice;
    $totalPrice.text(totalPrice + "");
    $totalPrice.closest(".individual_price").addClass("on_color");
    $clearFix.find("input.count_control_input").attr("value", count + "");
    $clearFix.find("a.ico_minus3").removeClass("disabled");
    $clearFix.find("input.count_control_input").removeClass("disabled");
    triggerCountChange();
});

$(".ticket_body").on("click", "a.ico_minus3", function (event) {
    event.preventDefault();
    var $clearFix = $(this).closest(".clearfix");
    var $totalPrice = $(this).closest(".count_control").find("span.total_price");
    var count = $clearFix.find("input.count_control_input").attr("value") - 0;
    if (count === 0) {
        return false;
    }
    count--;
    $clearFix.find("input.count_control_input").attr("value", count + "");
    var totalPrice = $totalPrice.text() - 0;
    var productPrice = $(this).closest(".qty").find("span.price").text() - 0;
    totalPrice = totalPrice - productPrice;
    $totalPrice.text(totalPrice + "");
    if (count === 0) {
        $clearFix.find("a.ico_minus3").addClass("disabled");
        $clearFix.find("input.count_control_input").addClass("disabled");
        $totalPrice.closest(".individual_price").removeClass("on_color");
    }
    triggerCountChange();
});

$(".inline_form.last").on("countChanged", function (event, type, count) {
    // var displayTime = "2017.1.1(일)~2017.1.10(화)";
    var displayTime = reservePage.getDisplayTime();
    var typeStr = "";
    var totalNum = 0;
    for (var i = 0; i < count.length; i++) {
        if (count[i] - 0 !== 0) {
            typeStr = typeStr + type[i] + "(" + (count[i] - 0) + ") ";
        }
        totalNum += count[i] - 0;
    }
    var total = ", 총 " + totalNum + "매 ";
    $(this).find(".inline_txt").text(displayTime + total + typeStr);
})

function triggerCountChange() {
    var type = [];
    var count = [];
    var $type = $(".qty_info_icon .product_amount > span");
    var $count = $(".clearfix input.count_control_input");
    for (var i = 0; i < $type.length; i++) {
        type.push($type.eq(i).text());
        count.push($count.eq(i).attr("value"));
    }
    $(".inline_form.last").trigger("countChanged", [type, count]);
    isDone();
}

function isAgree() {
    return $("#chk3").is(":checked");
}

function isCountZero() {
    var count = 0;
    var $count = $(".count_control_input");
    for (var i = 0; i < $count.length; i++) {
        count += $count.eq(i).val() - 0;
    }
    return count !== 0;
}

function isFormFilled() {
    var $userInfo = $(".inline_form div.inline_control input");
    for (var i = 0; i < $userInfo.length; i++) {
        if ($userInfo.eq(i).val() === "") {
            return false;
        }
    }
    return true;
}

function isDone() {
    var $submitBtn = $(".box_bk_btn .bk_btn_wrap");
    if (isAgree() && isCountZero() && isFormFilled()) {
        $submitBtn.removeClass("disable");
    } else {
        $submitBtn.addClass("disable");
    }
}

$(".inline_form div.inline_control input").on("keyup", isDone)

$("#chk3").change(isDone);

$("button.bk_btn").on("click", function () {
    if ($(this).closest(".bk_btn_wrap").hasClass("disable")) {
        return false;
    }
    if (!checkReg()) {
        return;
    } else {
        submitReservation();
    }
});

function checkReg() {
    var $user = $(".inline_form div.inline_control input");
    var tel = $user.eq(1).val();
    var email = $user.eq(2).val();

    var emailReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var telReg = /^\d{3}-\d{3,4}-\d{4}$/;

    if (!telReg.test(tel)) {
        alert("000-0000-0000형태로 입력");
        return false;
    }

    if (!emailReg.test(email)) {
        alert("invalid email");
        return false;
    }

    return true;
}

function submitReservation() {
    var $user = $(".inline_form div.inline_control input");
    var name = $user.eq(0).val();
    var tel = $user.eq(1).val();
    var email = $user.eq(2).val();

    var $count = $(".count_control_input");
    var general = $count.eq(0).val();
    var youth = $count.eq(1).val();
    var child = $count.eq(2).val();

    var buyInfo = {
        productId: id,
        userId: 9,
        generalTicketCount: general,
        youthTicketCount: youth,
        childTicketCount: child,
        reservationName: name,
        reservationTel: tel,
        reservationEmail: email
    };
    $.ajax({
        url: '/reservations',
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(buyInfo),
        dataType: 'json',
        success: function (res) {
            console.log(res);
        }
    });

}