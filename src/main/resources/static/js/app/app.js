var app = angular.module('geplanes', [ 'ngRoute', 'ngResource', 'ngAnimate', 'ngMessages', 'ngLoadingSpinner','ui.bootstrap','angularUtils.directives.dirPagination']);


app.filter('range', function() {
  return function(input, min, max) {
    min = parseInt(min); //Make string input int
    max = parseInt(max);
    for (var i=min; i<max; i++)
      input.push(i);
    return input;
  };
});

app.filter('range_date_now', function() {
  return function(input, min) {
    min = parseInt(min); //Make string input int
    max = parseInt(new Date().getFullYear());
    for (var i=min; i<=max; i++)
      input.push(i);
    return input;
  };
});

//configurando rotas
app.config(function($routeProvider) {
	$routeProvider.when("/dashboard", {
		controller : "unidadeController",
		templateUrl : "/unidade/dashboard"
	});
	
	$routeProvider.when("/dashboard/:ano/:unidadeId", {
		controller : "unidadeController",
		templateUrl : "/unidade/dashboard"
	});
	
	$routeProvider.when("/home", {
		controller : "unidadeController",
		templateUrl : "/unidade/home"
	});
	
	$routeProvider.when("/pendencias/:unidadeId", {
		controller : "unidadeController",
		templateUrl : "/unidade/pendencias"
	});
	
	$routeProvider.when("/indicador/:indicadorId", {
		controller : "indicadorController",
		templateUrl : function(params) {
		    return "/indicador/view" +'/'+ params.indicadorId ;
		}
	});
	
	$routeProvider.when("/iniciativa/planoacao/:planoacaoId", {
		controller : "iniciativaController",
		templateUrl : "/iniciativa/planoacao/view"
	});

});

