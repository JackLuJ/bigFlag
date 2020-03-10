package com.jackluan.bigflag.share.convert;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * @Author: jack.luan
 * @Date: 2020/3/10 17:36
 */
@Mapper
public interface DemoConvert {

    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);

    @Mappings({
            @Mapping(source = "info", target = "description"),
            @Mapping(source = "child.name", target = "childName"),
            @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "privateInfo", ignore = true),
            @Mapping(target = "registerDate", expression = "java(sourceA.getRegisterDate() == null ? null : new java.util.Date(sourceA.getRegisterDate()))")
    })
    TargetA convert(SourceA sourceA);

    void update(@MappingTarget SourceA sourceA1, SourceA sourceA2);

    @Data
    class SourceA {
        private Long id;

        private String name;

        private String info;

        private Date birthday;

        private String privateInfo;

        private Long registerDate;

        private SourceB child;
    }


    @Data
    class SourceB {

        private Long id;

        private String name;

        private String info;

    }


    @Data
    class TargetA {
        private Long id;

        private String name;

        private String description;

        private String childName;

        private String birthday;

        private String privateInfo;

        private Date registerDate;
    }
}
