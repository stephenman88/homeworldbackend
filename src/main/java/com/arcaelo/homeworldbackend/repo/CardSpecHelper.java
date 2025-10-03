package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import com.arcaelo.homeworldbackend.model.Card;
import jakarta.persistence.criteria.Predicate;

public class CardSpecHelper {
    public static class FIELDS{
        private FIELDS(){}
        public static class INTEGER{
            private INTEGER(){}
            public static final String COST_MEMORY = "costMemory";
            public static final  String COST_RESERVE="costReserve";
            public static final  String DURABILITY="durability";
            public static final  String LEVEL="level";
            public static final  String LIFE="life";
            public static final  String POWER="power";
        }
        public static class STRING{
            private STRING(){}
            public static final  String EFFECT="effect";
            public static final  String EFFECT_RAW="effectRaw";
            public static final  String FLAVOR="flavor";
            public static final  String NAME="name";
            public static final  String SLUG="slug";
        }
    }

    public static Specification<Card> hasCardClasses(List<String> cardClasses){
        return (root, query, criteriaBuilder) -> {
            if(cardClasses == null || cardClasses.isEmpty()) return null;

            Predicate[] predicates = cardClasses.stream()
                .map(c -> criteriaBuilder.isMember(c, root.get("classes")))
                .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<Card> processInteger(String operator, Integer searchInt, String fieldName){
        return (root, query, criteriaBuilder) -> {
            if(operator == null || searchInt == null) return null;

            if(operator.matches("^>$")){
                return criteriaBuilder.greaterThan(root.get(fieldName), searchInt);
            }else if(operator.matches("^>=$")){
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), searchInt);
            }else if(operator.matches("^=$")){
                return criteriaBuilder.equal(root.get(fieldName), searchInt);
            }else if(operator.matches("^<=$")){
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), searchInt);
            }else if(operator.matches("<")){
                return criteriaBuilder.lessThan(root.get(fieldName), searchInt);
            }else{
                return null;
            }
        };
    }

    public static Specification<Card> hasElements(List<String> elements){
        return (root, query, criteriaBuilder) -> {
            if(elements == null || elements.isEmpty()){return null;}

            Predicate[] predicates = elements.stream()
                .map(e -> criteriaBuilder.isMember(e, root.get("elements")))
                .toArray(Predicate[]::new);
            
            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<Card> containsText(String searchText, String fieldName){
        return(root, query, criteriaBuilder) -> {
            if(searchText == null || searchText.isEmpty()) return null;

            return criteriaBuilder.like(root.get(fieldName), "%" + searchText + "%");
        };
    }

    public static Specification<Card> notContainsText(String searchText, String fieldName){
        return(root, query, criteriaBuilder) -> {
            if(searchText == null || searchText.isEmpty()) return null;

            return criteriaBuilder.notLike(root.get(fieldName), "%" + searchText + "%");
        };
    }

    public static Specification<Card> isFast(Boolean speed){
        return(root,query, criteriaBuilder) -> {
            if(speed == null) return null;
            if(speed){
                return criteriaBuilder.isTrue(root.get("speed"));
            }else{
                return criteriaBuilder.isFalse(root.get("speed"));
            }
        };
    }

    public static Specification<Card> hasSubtypes(List<String> subtypes){
        return(root,query, criteriaBuilder) -> {
            if(subtypes == null || subtypes.isEmpty())return null;

            Predicate[] predicates = subtypes.stream()
                .map(subtype -> criteriaBuilder.isMember(subtype, root.get("subtypes")))
                .toArray(Predicate[]::new);
            
            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<Card> hasTypes(List<String> types){
        return(root, query, criteriaBuilder) -> {
            if(types == null || types.isEmpty()) return null;

            Predicate[] predicates = types.stream()
                .map(type -> criteriaBuilder.isMember(type, root.get("types")))
                .toArray(Predicate[]::new);
            
            return criteriaBuilder.or(predicates);
        };
    }
}

