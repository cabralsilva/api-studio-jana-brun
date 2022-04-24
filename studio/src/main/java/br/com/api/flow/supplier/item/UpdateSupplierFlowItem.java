package br.com.api.flow.supplier.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.SupplierMapper;
import br.com.api.dto.SupplierDTO;
import br.com.api.entity.repository.SupplierRepository;

@Component
public class UpdateSupplierFlowItem {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierMapper supplierMapper;

	public SupplierDTO update(SupplierDTO supplier) {

		return supplierMapper.toDTO(supplierRepository.save(supplierMapper.toEntity(supplier)));
	}
}
