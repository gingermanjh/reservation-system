function deleteItem(item) {
	var id = item.attr("id");
	$.ajax({
		url: '/categorys/api/remove/' + id,
		datatype: 'json',
		headers: {
			'content-Type': 'application/json'
		},
		type: 'DELETE',
		success: function () {
			item.parent().parent().remove();
		}
	})
}

$(".table").on("click", ".delete", function () {
	deleteItem($(this));
});

