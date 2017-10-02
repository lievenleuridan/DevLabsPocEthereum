/**
 * Created by vandebroeck.k on 2/10/2017.
 */
angular.module("ethereumApp", [])
    .controller("MainCtrl", function ($scope, $http) {
            $scope.searchButtonClick = function () {
                $http.get('http://localhost:8080/persons/' + $scope.person.name).then(function successCallback(response) {
                    $scope.foutMelding = null;
                    $scope.person = response.data;
                }, function errorCallback(response) {
                    $scope.person = null;
                    $scope.foutMelding = "Geen person met naam " + $scope.person.name + " gevonden.";
                });
            };
        }
    )