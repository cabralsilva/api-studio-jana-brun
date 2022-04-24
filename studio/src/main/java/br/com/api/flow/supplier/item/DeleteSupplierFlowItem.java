package br.com.api.flow.supplier.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.SupplierRepository;

@Component
public class DeleteSupplierFlowItem {

	@Autowired
	private SupplierRepository supplierRepository;

	public void delete(Integer identifier) {

		supplierRepository.deleteById(identifier);
	}
}
