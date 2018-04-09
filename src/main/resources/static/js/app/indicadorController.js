app.controller('indicadorController', function($scope, $http, $location, $routeParams) {
	
	if($routeParams.indicadorId){
		$http.get('/indicador/'+$routeParams.indicadorId).success(function(response){
			$scope.indicador = response;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
		
		$http.get('/indicador/'+$routeParams.indicadorId+'/pendencias').success(function(response){
			$scope.dataPendencias = response;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
	}
	
	$scope.closeAlert = function(index) {
		$scope.dataPendencias.pendencias.splice(index, 1);
	};
	
	$scope.progressBarType =  function(indicador){
		var meta = indicador.valorMetaSuperiorTotal;
		var valor = indicador.valorRealTotal;
		var relacao = valor/meta;
		
		if(relacao >= 0.85)
			return "success";
		if(relacao >= 0.40 && relacao < 0.85)
			return "warning";
		if(relacao < 0.40)
			return "danger";
		
	};
	
	
});