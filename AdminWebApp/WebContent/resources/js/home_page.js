function prepare()
{
	refreshChartHours();
	
	$("#switch-button").click(function() {
		var isHidden = $('#posts-div').is(":hidden");
		if(isHidden == true)
		{
			$('#users-div').toggle(750);
			$('#posts-div').toggle(1500);
			$('#switch-button').html('Prikaži informacije o korisnicima');
		}
		else{
			$('#posts-div').toggle(750);
			$('#users-div').toggle(1500);
			$('#switch-button').html('Prikaži postove');
		}
	});
	
	setInterval(function refreshContent()
	{
		$.ajax({
            type: 'GET',
            url: 'homePage.xhtml',
            success: function(htmlString) {
            	var html = $(htmlString);
            	var newNumberActive = $("#number-active-id",html);
            	var newNumberReg = $("#number-registered-id",html);
            	
            	$("#number-active-id").html($(newNumberActive).html());
            	$("#number-registered-id").html($(newNumberReg).html());
            }
		});
	},5000);
}
