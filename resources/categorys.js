function deleteItem(item) {
	var id = item.attr("id");
	$.ajax({
		url: '/categorys/api/' + id,
		datatype: 'json',
		type: 'DELETE',
		success: function () {
			$("#"+id).remove();
		}
	})
}

$(".table").on("click", ".delete", function () {
	deleteItem($(this));
});

