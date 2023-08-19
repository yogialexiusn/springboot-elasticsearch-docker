package com.example.springbootelasticsearchfuzzyquery_dsl.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.springbootelasticsearchfuzzyquery_dsl.entity.Product;
import com.example.springbootelasticsearchfuzzyquery_dsl.service.ElasticSearchService;
import com.example.springbootelasticsearchfuzzyquery_dsl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/apis")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ElasticSearchService elasticSearchService;


    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/findAll")
    Iterable<Product> findAll(){
       return productService.getProducts();

    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody  Product product){
       return productService.insertProduct(product);
    }

    //  Fuzzy
    @GetMapping("/fuzzySearch/{approximateProductName}")
    List<Product> fuzzySearch( @PathVariable String approximateProductName ) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.fuzzySearch(approximateProductName);
       List<Hit<Product>> hitList = searchResponse.hits().hits();
        System.out.println(hitList);
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit :hitList){
            productList.add(hit.source());
        }
        return productList;
    }

    //  DSL Master
    @GetMapping("/matchAll")
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }
    //matchAllProducts video content
    @GetMapping("/matchAllProducts")
    public List<Product> matchAllProducts() throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchAllProductsServices();
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/matchAllProducts/{fieldValue}")
    public List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    // Auto Suggest
    @GetMapping("/autoSuggest/{partialProductName}")
    List<String> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.autoSuggestProduct(partialProductName);
        List<Hit<Product>> hitList  =  searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        List<String> listOfProductNames = new ArrayList<>();
        for(Product product : productList){
            listOfProductNames.add(product.getName())  ;
        }
        return listOfProductNames;
    }


}
