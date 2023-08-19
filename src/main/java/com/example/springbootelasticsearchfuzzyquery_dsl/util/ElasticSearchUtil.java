package com.example.springbootelasticsearchfuzzyquery_dsl.util;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;

public class ElasticSearchUtil {

    public static Supplier<Query> createSupplierQuery(String approximateProductName){
        Supplier<Query> supplier = ()->Query.of(q->q.fuzzy(createFuzzyQuery(approximateProductName)));
        return  supplier;
    }

    // FUZZY
    public static FuzzyQuery createFuzzyQuery(String approximateProductName){
        val fuzzyQuery  = new FuzzyQuery.Builder();
        return  fuzzyQuery.field("name").value(approximateProductName).build();
    }

    // DSL
    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery(){
        val  matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithNameField(String fieldValue){
        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithNameField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithNameField(String fieldValue){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field("name").query(fieldValue).build();
    }

    public static Supplier<Query> createSupplierAutoSuggest(String partialProductName){
        Supplier<Query> supplier = ()->Query.of(q->q.match(createAutoSuggestMatchQuery(partialProductName)));
        return  supplier;
    }
    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName){
        val autoSuggestQuery = new MatchQuery.Builder();
        return  autoSuggestQuery.field("name").query(partialProductName).analyzer("standard").build();
    }
}
