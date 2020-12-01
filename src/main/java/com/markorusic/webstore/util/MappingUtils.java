package com.markorusic.webstore.util;

import com.markorusic.webstore.dto.category.CategoryPageDto;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Primary
@Component
public class MappingUtils extends ModelMapper {
    public <T, K> Page<T> mapPage(Page<K> items, Class<T> dtoClass, Pageable pageable) {
        return new PageImpl<T>(items.stream()
                .map(item -> this.map(item, dtoClass))
                .collect(Collectors.toList()), pageable, items.getTotalElements());
    }
}
