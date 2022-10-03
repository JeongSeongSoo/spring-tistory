package tistory.petoo.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PetooMapper {

    List<Map> loadContentsAndItemId() throws Exception;

}
