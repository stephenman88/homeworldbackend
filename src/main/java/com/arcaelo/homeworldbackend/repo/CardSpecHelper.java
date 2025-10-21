package com.arcaelo.homeworldbackend.repo;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import com.arcaelo.homeworldbackend.model.Card;
import com.arcaelo.homeworldbackend.model.CardSet;
import com.arcaelo.homeworldbackend.model.Edition;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;

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

            public static final String THEMA_CHARM_FOIL="themaCharmFoil";
            public static final String THEMA_FEROCITY_FOIL="themaFerocityFoil";
            public static final String THEMA_GRACE_FOIL="themaGraceFoil";
            public static final String THEMA_MYSTIQUE_FOIL="themaMystiqueFoil";
            public static final String THEMA_VALOR_FOIL="themaValorFoil";
            public static final String THEMA_FOIL="themaFoil";

            public static final String THEMA_CHARM_NONFOIL="themaCharmNonfoil";
            public static final String THEMA_FEROCITY_NONFOIL="themaFerocityNonfoil";
            public static final String THEMA_GRACE_NONFOIL="themaGraceNonfoil";
            public static final String THEMA_MYSTIQUE_NONFOIL="themaMystiqueNonfoil";
            public static final String THEMA_VALOR_NONFOIL="themaValorNonfoil";
            public static final String THEMA_NONFOIL="themaNonfoil";

            public static final String THEMA_CHARM="themaCharm";
            public static final String THEMA_FEROCITY="themaFerocity";
            public static final String THEMA_GRACE="themaGrace";
            public static final String THEMA_MYSTIQUE="themaMystique";
            public static final String THEMA_VALOR="themaValor";
            public static final String THEMA = "thema";
            public static final String RARITY = "rarity";
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

            Join<Card, String> classJoin = root.joinSet("classes");

            return classJoin.in(cardClasses.stream().map(c -> c.toUpperCase()).toList());
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

            Join<Card, String> elementJoin = root.joinSet("elements");

            return elementJoin.in(elements.stream().map(e -> e.toUpperCase()).toList());
        };
    }

    public static Specification<Card> containsText(String searchText, String fieldName){
        return(root, query, criteriaBuilder) -> {
            if(searchText == null || searchText.isEmpty()) return null;

            return criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + searchText.toLowerCase() + "%");
        };
    }

    public static Specification<Card> notContainsText(String searchText, String fieldName){
        return(root, query, criteriaBuilder) -> {
            if(searchText == null || searchText.isEmpty()) return null;

            return criteriaBuilder.notLike(criteriaBuilder.lower(root.get(fieldName)), "%" + searchText.toLowerCase() + "%");
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
            
            Join<Card, String> subtypesJoin = root.joinSet("subtypes");
            
            return subtypesJoin.in(subtypes.stream().map(s -> s.toUpperCase()).toList());
        };
    }

    public static Specification<Card> hasTypes(List<String> types){
        return(root, query, criteriaBuilder) -> {
            if(types == null || types.isEmpty()) return null;

            Join<Card, String> typesJoin = root.joinSet("types");
            
            return typesJoin.in(types.stream().map(t -> t.toUpperCase()).toList());
        };
    }

    public static Specification<Card> ofCardSet(List<String> cardSetPrefix){
        return(root, query, criteriaBuilder) -> {
            if(cardSetPrefix == null || cardSetPrefix.isEmpty()) return null;

            Join<Card, Edition> editionJoin = root.join("editions");
            Join<Edition, CardSet> cardsetJoin = editionJoin.join("cardSet");
            
            return criteriaBuilder.lower(cardsetJoin.get("prefix")).in(
                cardSetPrefix.stream().map(cs -> cs.toLowerCase()).toList()
            );
        };
    }

    public static Specification<Card> processThema(String operator, Integer searchInt, String fieldName){
        if(operator == null || searchInt == null) return null;

        if(fieldName.equals(FIELDS.INTEGER.THEMA_CHARM)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_CHARM_FOIL).or(processThema(operator, searchInt, FIELDS.INTEGER.THEMA_CHARM_NONFOIL));
        }else if(fieldName.equals(FIELDS.INTEGER.THEMA_FEROCITY)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_FEROCITY_FOIL).or(processThema(operator, searchInt, FIELDS.INTEGER.THEMA_FEROCITY_NONFOIL));
        }else if(fieldName.equals(FIELDS.INTEGER.THEMA_GRACE)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_GRACE_FOIL).or(processThema(operator, searchInt, FIELDS.INTEGER.THEMA_GRACE_NONFOIL));
        }else if(fieldName.equals(FIELDS.INTEGER.THEMA_MYSTIQUE)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_MYSTIQUE_FOIL).or(processThema(operator, searchInt, FIELDS.INTEGER.THEMA_MYSTIQUE_NONFOIL));
        }else if(fieldName.equals(FIELDS.INTEGER.THEMA_VALOR)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_VALOR_FOIL).or(
                processThema(operator, searchInt, FIELDS.INTEGER.THEMA_VALOR_NONFOIL)
            );
        }else if(fieldName.equals(FIELDS.INTEGER.THEMA)){
            return processThema(operator, searchInt, FIELDS.INTEGER.THEMA_FOIL).or(
                processThema(operator, searchInt, FIELDS.INTEGER.THEMA_NONFOIL)
            );
        }else{
            return(root, query, criteriaBuilder)-> {

                Join<Card, Edition> editionJoin = root.join("editions");

                if(operator.matches("^>$")){
                    return criteriaBuilder.greaterThan(editionJoin.get(fieldName), searchInt);
                }else if(operator.matches("^>=$")){
                    return criteriaBuilder.greaterThanOrEqualTo(editionJoin.get(fieldName), searchInt);
                }else if(operator.matches("^=$")){
                    return criteriaBuilder.equal(editionJoin.get(fieldName), searchInt);
                }else if(operator.matches("^<=$")){
                    return criteriaBuilder.lessThanOrEqualTo(editionJoin.get(fieldName), searchInt);
                }else if(operator.matches("<")){
                    return criteriaBuilder.lessThan(editionJoin.get(fieldName), searchInt);
                }else{
                    return null;
                }
            };
        }
    }

    public static Specification<Card> processRarity(List<Integer> rarities){
        return (root, query, criteriaBuilder) -> {
            if(rarities == null || rarities.isEmpty()) return null;

            Join<Card, Edition> cardJoin = root.join("editions");
            return cardJoin.get("rarity").in(rarities);
        };
    }

    public static Specification<Card> processLegality(String format, String operator, Integer limit){
        return (root, query, criteriaBuilder) -> {
            if(format == null || format.isEmpty() || operator == null || operator.isEmpty() || limit == null) return null;
            
            Expression<String> jsonValue = criteriaBuilder.function(
                    "jsonb_extract_path_text", 
                    String.class,
                    root.get("legality"),
                    criteriaBuilder.literal(format.toUpperCase()),
                    criteriaBuilder.literal("limit")
                 );

            if(operator.matches("^>$")){
                if(limit >= 4) return criteriaBuilder.disjunction();
                return criteriaBuilder.or(criteriaBuilder.greaterThan(jsonValue, limit.toString()), criteriaBuilder.isNull(root.get("legality")));
            }else if(operator.matches("^>=$")){
                if(limit > 4) return criteriaBuilder.disjunction();
                return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(jsonValue, limit.toString()), criteriaBuilder.isNull(root.get("legality")));
            }else if(operator.matches("^=$")){
                if(limit > 4 || limit < 0) criteriaBuilder.disjunction();
                if(limit == 4) criteriaBuilder.isNull(root.get("legality"));
                return criteriaBuilder.equal(jsonValue, limit.toString());
            }else if(operator.matches("^<=$")){
                if(limit < 0) return criteriaBuilder.disjunction();
                if(limit >= 4) return criteriaBuilder.conjunction();
                return criteriaBuilder.lessThanOrEqualTo(jsonValue, limit.toString());
            }else if(operator.matches("<")){
                if(limit <= 0) return criteriaBuilder.disjunction();
                if(limit > 4) return criteriaBuilder.conjunction();
                return criteriaBuilder.lessThan(jsonValue, limit.toString());
            }else{
                return null;
            }
        };
    }
}

