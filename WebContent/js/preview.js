$(function($){
	var headers = [],
		preview = $('#preview'),
		result = $('#result');
	result.find('tr').first().find('th').each(function(){
		headers.push($(this).text());
	});
	result.find('a').click(function(){
		var preview = $('#preview'),
			row = $(this).parents('tr')
			table = $('<table/>').addClass("table table-bordered table-compact"),
			first = $('<tr><td></td></tr>');
		table.append(first);
		for (var y = 0; y<height; y++) {
			first.append($('<th class="header">'+(y)+'</th>'));
		}
		for (var x = 0; x<width; x++) {
			var tr = $('<tr><th class="header">'+(x)+'</th></tr>');
			for (var y = 0; y<height; y++) {
				var cell = row.find(':contains("['+x+','+y+']")'),
					name = cell?headers[cell.index()-1]:null,
					title = name?name+' in ['+x+','+y+']':'';
				tr.append($('<td/>').addClass(name?'info '+name.toLowerCase():'').html('&nbsp;').attr('title', title));
			}
			table.append(tr);
		}
		var w = 60*y + 105;
		preview.find('.modal-body').empty();
		preview.find('.modal-body').append(table);
		preview.find('.modal-dialog').width(w);
		preview.modal('show');
	})
});