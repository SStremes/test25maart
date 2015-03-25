'use strict';

angular.module('ProductApp.controllers', []).

    controller('ProductsController', ['$scope', 'ProductService',
        function($scope, ProductService){
    		$scope.products = [];
    		
    		$scope.searchfilter = function(product){
    			var keyword = new RegExp($scope.namefilter, 'i');
    			return keyword.test(product.shortname);
    		}
    		
    		ProductService.getProductsJSON().success(function(response){
    			$scope.products = response.products;
    		});
    	}
        
    ]).
    
    controller('ProductController', ['$scope', '$routeParams', 'ProductService',
        function($scope, $routeParams, ProductService){
			$scope.product = null;
			var shortname = $routeParams.shortname;
			
			ProductService.getProductJSON(shortname).success(function(response){
				$scope.product = response;
			});
    	}
    
    ]).
//    
    controller('NewProductController', ['$scope', 'ProductService',
        function($scope, ProductService){
    		$scope.addProduct = function(){
    			var productXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
    				+ "<product><brand>" + $scope.newProduct.brand + "</brand><description>" + $scope.newProduct.description
    				+ "</description><id>" + $scope.newProduct.id + "</id><price>" + $scope.newProduct.price
    				+ "</price><shortname>" + $scope.newProduct.shortname + "</shortname></product>";
    			ProductService.addProduct(productXML);
    		}
		
			
		}
        
    ]);
