package br.com.api.converter;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.ClassDTO;
import br.com.api.dto.ClassroomDTO;
import br.com.api.dto.ProductDTO;
import br.com.api.dto.ScheduleDetailClassDTO;
import br.com.api.entity.Class;
import br.com.api.entity.Classroom;
import br.com.api.entity.Product;
import br.com.api.entity.ScheduleDetailClass;

@Mapper(componentModel = "spring")
public interface ClassMapper {

	public Class toEntity(ClassDTO dto);

	@Mapping(source = "scheduleDetailClassList", target = "scheduleDetailClassList", qualifiedByName = "toScheduleDetailClassDTOList")
	@Mapping(source = "product", target = "product", qualifiedByName = "toProductDTO")
	public ClassDTO toDTO(Class entity);
	
	@Named("toProductDTO")
	public default ProductDTO toProductDTO(Product source) {
        if ( source == null ) {
            return null;
        }

        ProductDTO target = ProductDTO.builder().build();

        target.setIdentifier( source.getIdentifier() );
        target.setCode(source.getCode());
        target.setDescription(source.getDescription());

        return target;
    }
	
	@Named("toScheduleDetailClassDTOList")
	@IterableMapping(qualifiedByName = "toScheduleDetailClassDTO")
	public List<ScheduleDetailClassDTO> toGrateItemListDTO(List<ScheduleDetailClass> source);

	@Named("toScheduleDetailClassDTO")
	@Mapping(source = "classroom", target = "classroom", qualifiedByName = "toClassroomDTO")
	public default ScheduleDetailClassDTO toScheduleDetailClassDTO(ScheduleDetailClass source) {
        if ( source == null ) {
            return null;
        }

        ScheduleDetailClassDTO target = ScheduleDetailClassDTO.builder().build();

        target.setIdentifier( source.getIdentifier() );
        target.setOften( source.getOften() );
        target.setOftenDay( source.getOftenDay() );
        target.setInitTime( source.getInitTime() );
        target.setEndTime( source.getEndTime() );
        target.setClassroom(toClassroomDTO(source.getClassroom()));

        return target;
    }
	
	@Named("toClassroomDTO")
	public default ClassroomDTO toClassroomDTO(Classroom source) {
        if ( source == null ) {
            return null;
        }

        ClassroomDTO target = ClassroomDTO.builder().build();

        target.setIdentifier( source.getIdentifier() );
        target.setDescription( source.getDescription() );

        return target;
    }
}
