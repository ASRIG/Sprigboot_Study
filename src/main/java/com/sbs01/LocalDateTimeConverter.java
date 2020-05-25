//package com.sbs01;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//
//@Converter(autoApply = true)
//public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp>{
//	@Override
//	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
//		return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
//	}
//	
//	@Override
//	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
//		return timestamp != null ? timestamp.toLocalDateTime() : null;
//	}
//	
//}

// 최신버전에서는 필요없는듯??/