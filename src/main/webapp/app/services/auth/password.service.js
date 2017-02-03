(function() {
    'use strict';

    angular
        .module('dynamicFiltrationPaginationApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/account/change_password', {}, {});

        return service;
    }
})();
