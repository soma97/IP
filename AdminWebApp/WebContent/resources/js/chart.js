function refreshChartHours()
{
	allLabels = getLabels();
	dataSet = [];
	$.ajax({
		  url: "api/activity",
		  type: "get",
		  success: function(response) {
			var json = response;
			for(var i=0;i<24;++i)
			{
				var currentHour = allLabels[i].toString();
				dataSet.push(json[currentHour]);
			}
			drawChart(allLabels,dataSet);
		  },
		  error: function(xhr) {
		  }
	});
}

function drawChart(allLabels,dataSet)
{
	var canvas = document.getElementById("hoursCanvas");
	var ctx = canvas.getContext("2d");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: allLabels,
			datasets: [{
				label: 'Broj aktivnih korisnika u prethodna 24 časa po satima',
				data: dataSet,
				backgroundColor: [
					'rgba(56, 147, 245, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)'
				],
				borderColor: [
					'rgba(4, 53, 143, 1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)'
				],
				borderWidth: 1
				}]
		},
		options: {
			responsive: true,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
}

function getLabels()
{
	result = [];
	var d = new Date();
	var n = d.getHours();
	for(var i=1;i<=24;++i)
	{
		var numToPush = (n+i)%24;
		result.push(numToPush);
	}
	return result;
}

