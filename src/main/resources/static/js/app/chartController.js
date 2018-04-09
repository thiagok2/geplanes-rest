app.controller('chartController', function($scope, $http, $location, $routeParams) {
	
	
	var self = this;
	var countConcluidos;
	
	if($routeParams.unidadeId){
		$scope.unidadeId = $routeParams.unidadeId;
	}
	
	if($routeParams.ano){
		$scope.ano = $routeParams.ano;
	}
	
	
	
	
	$scope.drawCharts = function() {
		
		var dataStatus = [];
		var dataConcluidos = [];
		
		$http.get('/unidade/'+$scope.unidadeId+'/resumo').success(function(response){
			dataStatus = [{
	            label: "Concluidos",
	            value: response.countConcluidos
	        }, {
	            label: "Atrasados",
	            value: response.countAtrasados
	        }, {
	            label: "Andamento",
	            value: response.countEmAndamento
	        }, {
	            label: "Planejamento",
	            value: response.countEmPlanejamento
	        }];
			
			dataConcluidos = [{
	            label: "Sucesso",
	            value: response.countConcluidoSucesso
	        }, {
	            label: "Insucesso",
	            value: response.countConcluidoInsucesso
	        }];
			
			$scope.drawDonutChart('morris-donut-chart', dataStatus);
			$scope.drawDonutChart('morris-donut-chart2', dataConcluidos);
			
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
		$http.get('/unidade/'+$scope.unidadeId+'/resumo-unidades').success(function(response){		
			var dataResumoUnidade = [];
			for(var i = 0; i<response.length; i++){
				var objResumo = new Object();
				objResumo.campus = response[i].unidade.sigla;
				objResumo.totalIndicadores = response[i].countAtrasados + response[i].countEmAndamento + response[i].countEmPlanejamento;
				objResumo.totalConcluidos = response[i].countConcluidos;
				
				dataResumoUnidade.push(objResumo);
			}
			xkey = 'campus';
			ykeys = ['totalIndicadores', 'totalConcluidos'];
			labels = ['Indicadores', 'Concluídos'];
			
			$scope.drawBarChart('morris-bar-chart', JSON.stringify(dataResumoUnidade), xkey, ykeys, labels );
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
	};
	
	$scope.drawBarChart = function(_element, _data, _xkey, _ykeys, _labels){
		Morris.Bar({
			  barColors: ['#337ab7','#5cb85c'],
			  element: _element,
			  data: JSON.parse(_data),
			  xkey: _xkey,
			  ykeys: _ykeys,
			  labels: _labels,
			  stacked: true,
			  resize: true,
			  gridTextSize: 10,
			  hideHover: true,
			  xLabelAngle: 45
			});
	}
	
	$scope.drawDonutChart = function(_elementId, _data){
		Morris.Donut({
	        element: _elementId,
	        data: _data,
	        resize: true
	    });
	};
	
	$scope.drawChartLines = function(){
		
		var dataAcompanhamentos = [];
		var dataAcoes = [];
		var ano = $scope.ano;
		var unidadeId = $scope.unidadeId;
		
		$http.get('/unidade/'+ano+'/'+unidadeId+'/data-chart-acompanhamentos').success(function(response){		
			 
			Morris.Area({
				 element: 'area-acompahamento-chart',
				 data: response,
				 xkey: "bimestre",
			     ykeys: [
			    	 "total","com_valor"
			    	 ],
			     labels:  [
			    	 "Total","Lançados"
			    	 ],
			     pointSize: 2,
			     hideHover: 'false',
			     resize: false,
			     behaveLikeLine: true,
			     lineColors: ['#337ab7','#5cb85c']
				 
			 });
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
		$http.get('/unidade/'+ano+'/'+unidadeId+'/data-chart-acoes').success(function(response){		
			 
			Morris.Line({
				 element: 'area-acao-chart',
				 data: response,
				 xkey: "bimestre",
			     ykeys: [
			    	 "total","concluido"
			    	 ],
			     labels:  [
			    	 "Total","Concluidos"
			    	 ],
			     pointSize: 2,
			     hideHover: 'false',
			     resize: false,
			     lineColors: ['#337ab7','#5cb85c']
				 
			 });
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});

	};
});