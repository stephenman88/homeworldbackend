package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverters{
    @Converter
    public static class HashMapListConverter implements AttributeConverter<List<HashMap<String, String>>, String>{
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<HashMap<String, String>> attribute){
            if(attribute == null) return "[]";
            try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert list to JSON", e);
        }
        }

        @Override
        public List<HashMap<String, String>> convertToEntityAttribute(String data){
            if(data == null || data.isEmpty()) return new ArrayList<HashMap<String, String>>();
            try {
            return mapper.readValue(data, new TypeReference<List<HashMap<String, String>>>(){});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert JSON to list", e);
        }
        }
    }

    @Converter
    public static class HashMapSetConverter implements AttributeConverter<Set<HashMap<String, String>>, String>{
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Set<HashMap<String, String>> attribute){
            if (attribute == null) return "[]";
            try{
                return objectMapper.writeValueAsString(attribute);
            }catch(JsonProcessingException e){
                throw new IllegalArgumentException("Could not convert set to JSON", e);
            }
        }

        @Override
        public Set<HashMap<String, String>> convertToEntityAttribute(String data){
            if(data == null || data.isEmpty()) return new HashSet<HashMap<String, String>>();
            try{
                List<HashMap<String, String>> list = objectMapper.readValue(data, new TypeReference<List<HashMap<String, String>>>(){});
                return new HashSet<>(list);
            }catch(JsonProcessingException e){
                throw new IllegalArgumentException("Could not convert JSON to list", e);
            }
        }
    }

    @Converter
public static class HashMapHashMapConverter implements AttributeConverter<HashMap<String, HashMap<String, Integer>>, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HashMap<String, HashMap<String, Integer>> attribute) {
        try {
            return attribute == null ? null : mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting HashMap to JSON", e);
        }
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null :
                mapper.readValue(dbData, new TypeReference<HashMap<String, HashMap<String, Integer>>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to HashMap", e);
        }
    }
}
}