function prepare()
{
	var mymap = L.map('map-id').setView([44.363133, 17.572632], 7);
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1
	}).addTo(mymap);

	mymap.on('click', function onMapClick(e) {
		var posString = e.latlng.toString();
	    var submitString = posString.split('(')[1].split(')')[0];
	    $('input[name="form-id:location"]').val(submitString);
	    var marker = L.marker([parseFloat(submitString.split(',')[0]), parseFloat(submitString.split(',')[1])]).addTo(mymap);
	});
}
