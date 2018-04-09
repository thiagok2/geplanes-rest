app.controller('pendenciaController', function($scope, $http, $location, $routeParams) {
	
	var self = this;
	
	var unidadeId;
	self.totalItems = 0;
	self.dataPendencias = [];
	
	self.itensPorPagina = 10;
	self.pagenum = 1;
	
	
	
	if($routeParams.unidadeId){
		unidadeId = $routeParams.unidadeId;
		
		$http.get('/unidade/'+unidadeId+'/pendencias').success(function(response){
			self.dataPendencias = response;
			self.totalItems = self.dataPendencias.length;

		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		

	}
	
	
	self.detalhesResolver = function(pendencia){
		if(pendencia.indicador)
			$location.path('/indicador/'+pendencia.demandaId);
		if(pendencia.planoAcao)
			$location.path('iniciativa/planoacao/view/'+pendencia.demandaId);
		
	};
	
	$scope.pageChangeHandler = function(pageNumber) {
		$http.get('/unidade/'+unidadeId+'/pendencias').success(function(response){
			self.dataPendencias = response;
			self.totalItems = self.dataPendencias.length;

		}).error(function(data, status, headers, config) {
			messagealert('danger',  data + '. Status: '+ status);
		});
		
		self.dataPendencias = self.dataPendencias.slice(pageNumber*self.itensPorPagina, (pageNumber +1)* self.itensPorPagina);
	};
	
});
