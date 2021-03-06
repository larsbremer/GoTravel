var app = angular.module('tutorialApp.modalcontroller', []);

app.controller('ModalDemoCtrl', function($uibModal, $log, $document) {
  var $ctrl = this;
  $ctrl.items = [ 'item1', 'item2', 'item3' ];

  $ctrl.open = function(size, parentSelector, id) {
    var parentElem = parentSelector ? angular.element($document[0].querySelector('.modal-demo ' + parentSelector))
	: undefined;
    var modalInstance = $uibModal.open({
      animation : true,
      ariaLabelledBy : 'modal-title',
      ariaDescribedBy : 'modal-body',
      templateUrl : 'myModalContent.html',
      controller : 'ModalInstanceCtrl',
      controllerAs : '$ctrl',
      size : size,
      appendTo : parentElem,
      resolve : {
	items : function() {
	  return $ctrl.items;
	}
      }
    });

    modalInstance.result.then(function(selectedItem) {
      $ctrl.selected = selectedItem;
    }, function() {
      $log.info('Modal dismissed at: ' + new Date());
    });
  };

  $ctrl.openComponentModal = function() {
    var modalInstance = $uibModal.open({
      animation : true,
      component : 'modalComponent',
      resolve : {
	items : function() {
	  return $ctrl.items;
	}
      }
    });

    modalInstance.result.then(function(selectedItem) {
      $ctrl.selected = selectedItem;
    }, function() {
      $log.info('modal-component dismissed at: ' + new Date());
    });
  };

});

// Please note that $uibModalInstance represents a modal window (instance)
// dependency.
// It is not the same as the $uibModal service used above.

app.controller('ModalInstanceCtrl', function($uibModalInstance, items) {
  var $ctrl = this;
  $ctrl.items = items;
  $ctrl.selected = {
    item : $ctrl.items[0]
  };

  $ctrl.ok = function() {
    $uibModalInstance.close($ctrl.selected.item);
  };

  $ctrl.cancel = function() {
    $uibModalInstance.dismiss('cancel');
  };
});
