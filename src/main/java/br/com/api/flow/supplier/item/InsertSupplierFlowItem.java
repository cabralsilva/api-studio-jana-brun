package br.com.api.flow.supplier.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.SupplierMapper;
import br.com.api.dto.SupplierDTO;
import br.com.api.entity.repository.SupplierRepository;

@Component
public class InsertSupplierFlowItem {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private UpdateSupplierFlowItem updateSupplierFlowItem;

	@Autowired
	private SupplierMapper supplierMapper;

	public SupplierDTO insert(@NonNull SupplierDTO supplier) {

		if (Objects.nonNull(supplier.getIdentifier())) {
			return updateSupplierFlowItem.update(supplier);
		}

		return supplierMapper.toDTO(supplierRepository.save(supplierMapper.toEntity(supplier)));
	}
}
