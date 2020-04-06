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

function fillMap(location,mapId)
{	
    var x = location.split(",")[0];
	var y = location.split(",")[1];
	console.log(x + " " +y + " "+mapId);
	var currentMap = L.map(mapId).setView([x, y], 7);
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1
	}).addTo(currentMap);

	L.marker([x, y]).addTo(currentMap);
}
