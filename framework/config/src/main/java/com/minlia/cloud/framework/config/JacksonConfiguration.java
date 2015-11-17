/**
 * Copyright (C) 2004-2015 http://oss.minlia.com/license/framework/2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package com.minlia.cloud.framework.config;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.time.*;
//
//@Configuration
//public class JacksonConfiguration {
//
//    @Bean
//    Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
//        JavaTimeModule module = new JavaTimeModule();
//        module.addSerializer(OffsetDateTime.class, JSR310DateTimeSerializer.INSTANCE);
//        module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
//        module.addSerializer(LocalDateTime.class, JSR310DateTimeSerializer.INSTANCE);
//        module.addSerializer(Instant.class, JSR310DateTimeSerializer.INSTANCE);
//        module.addDeserializer(LocalDate.class, JSR310LocalDateDeserializer.INSTANCE);
//        return new Jackson2ObjectMapperBuilder()
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .findModulesViaServiceLoader(true)
//                .modulesToInstall(module);
//    }
//}
