package com.markorusic.webstore.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Component
public class MappingUtils extends ModelMapper {
    public <T, K> List<T> mapList(List<K> items, Class<T> dtoClass) {
        return items.stream()
                .map(item -> this.map(item, dtoClass))
                .collect(Collectors.toList());
    }

    public <T, K> Page<T> mapPage(Page<K> items, Class<T> dtoClass, Pageable pageable) {
        var dtoItems = mapList(items.getContent(), dtoClass);
        return new PageImpl(dtoItems, pageable, items.getTotalElements());
    }
}
