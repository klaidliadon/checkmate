$(function($){
	var headers = [],
		preview = $('#preview'),
		loading = $('#loading'),
		result = $('#result');
	result.add(loading).addClass('ready');
	result.find('tr').first().find('th').each(function(){
		headers.push($(this).text());
	});
	result.find('tr.result').click(function(){
		var row = $(this)
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
					td = $('<td/>'),
					name = headers[cell.index()-1];
				if (!name) {
					name = 'menaced';
				}
				td.addClass(name.toLowerCase()).html('<div class="piece">&nbsp;</div>')
				td.find('div').attr('data-placement', 'top').attr('title', name+' in ['+x+','+y+']').tooltip();
				tr.append(td);
			}
			table.append(tr);
		}
		var w = 60*y + 105;
		preview.find('.modal-body').empty();
		preview.find('.modal-body').append(table);
		preview.find('.modal-dialog').width(w);
		preview.modal('show');
	});
});

$(function () {
	$('[data-toggle="tooltip"]').tooltip()
});