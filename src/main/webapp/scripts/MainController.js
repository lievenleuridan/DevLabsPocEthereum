/**
 * Created by vandebroeck.k on 2/10/2017.
 */
angular.module("ethereumApp", [])
    .controller("MainCtrl", function ($scope, $http) {
            $scope.searchButtonClick = function () {
                $http.get('http://localhost:8080/persons/' + $scope.person.firstName +"/"+ $scope.person.lastName).then(function successCallback(response) {
                // $http.get('http://localhost:8080/persons/kwintne/vanùmdsf').then(function successCallback(response) {
                    $scope.foutMelding = null;
                    $scope.person = response.data;
                }, function errorCallback(response) {
                    $scope.person = null;
                    $scope.foutMelding = "Geen person  gevonden.";
                });
            };
        }
    );