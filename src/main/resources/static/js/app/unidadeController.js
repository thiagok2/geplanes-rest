app.controller('unidadeController', function($scope, $http, $location, $routeParams) {
	
	var self = this;
	$scope.countPendencias;
	$scope.resumo = {};
	$scope.resumoUnidades = [];
	
	
	if($routeParams.unidadeId){
		$http.get('/unidade/'+$routeParams.unidadeId+'/resumo').success(function(response){
			$scope.resumo = response;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
	}
		
	$scope.listarPendencias = function(unidadeId) {
		$http.get('/unidade/'+unidadeId+'/pendencias?nivel=ALERTA').success(function(response){
			self.dataPendencias = response;
			$scope.countPendencias = response.length;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
	};
	
	if($routeParams.unidadeId)
		$scope.listarPendencias($routeParams.unidadeId);
	
	$scope.listResumos = function(unidadeId){
		$http.get('/unidade/'+unidadeId+'/resumo-unidades').success(function(response){
			$scope.resumoUnidades = response;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
	};
	
	if($routeParams.unidadeId)
		$scope.listResumos($routeParams.unidadeId);
	
	$scope.todosCampusCollapsed = false;
	$scope.reitoriaCollapsed = false;
	
	$scope.exibirUnidades = function(unidadeId, tipo){
		
		var params = '';
		if(tipo == 'campus'){
			params += 'campus=true&reitoria=false';
			$scope.todosCampusCollapsed = !$scope.todosCampusCollapsed;
		}else if(tipo == 'reitoria'){
			params += 'campus=false&reitoria=true';
			$scope.reitoriaCollapsed = !$scope.reitoriaCollapsed;
		}
		
		$http.get('/unidade/'+unidadeId+'/resumo-unidades?pendencia=true&'+params).success(function(response){
			if(tipo == 'campus')
				$scope.unidadesCampus = response;
			else if(tipo == 'reitoria')
				$scope.unidadesReitoria = response;
		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
	}
	
	$scope.detalhes = function(pendencia){
		if(pendencia.indicador)
			$location.path('/indicador/'+pendencia.demandaId);
		if(pendencia.planoAcao)
			$location.path('iniciativa/planoacao/view/'+pendencia.demandaId);
		
	};
	
	self.detalhes = function(pendencia){
		if(pendencia.indicador)
			$location.path('/indicador/'+pendencia.demandaId);
		if(pendencia.planoAcao)
			$location.path('iniciativa/planoacao/view/'+pendencia.demandaId);
		
	};
	
});