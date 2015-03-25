package edu.ap.jaxrs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import java.io.OutputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;

public class ProductJSONReader {

	public static final String JSON_FILE="/Users/Stijn/workspace/REST/products.json";
	
	public ArrayList<Product> getProducts() throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);
         
        //create JsonReader object
        JsonReader jsonReader = Json.createReader(fis);
         
        //get JsonObject from JsonReader
        JsonObject jsonObject = jsonReader.readObject();
         
        //we can close IO resource and JsonReader now
        jsonReader.close();
        fis.close();
         
        //Retrieve data from JsonObject and create Employee bean
//        Product product = new Product();
//         
//        product.setId(jsonObject.getInt("id"));
//        product.setName(jsonObject.getString("name"));
//        product.setBrand(jsonObject.getString("brand"));
//        product.setDescription(jsonObject.getString("description"));
//        product.setPrice(jsonObject.getInt("price"));
         
        //reading arrays from json
        JsonArray jsonArray = jsonObject.getJsonArray("products");
        ArrayList<Product> products = new ArrayList <Product>();
        for(int i = 0; i<jsonArray.size(); i++){
        	JsonObject object = jsonArray.getJsonObject(i);
        	Product product = new Product();
            product.setId(object.getString("id"));
            product.setName(object.getString("name"));
            product.setBrand(object.getString("brand"));
            product.setDescription(object.getString("description"));
            product.setPrice(object.getInt("price"));
            products.add(product);
        }
         
        //reading inner object from json object
//        JsonObject innerJsonObject = jsonObject.getJsonObject("address");
//        Address address = new Address();
//        address.setStreet(innerJsonObject.getString("street"));
//        address.setCity(innerJsonObject.getString("city"));
//        address.setZipcode(innerJsonObject.getInt("zipcode"));
//        emp.setAddress(address);
         
        //print employee bean information
        return products;
         
    }
	
	public void setProducts(ArrayList<Product> list) throws FileNotFoundException {
 
        JsonObjectBuilder productBuilder = Json.createObjectBuilder();
        JsonObjectBuilder productBuilder2 = Json.createObjectBuilder();
        JsonArrayBuilder productsBuilder = Json.createArrayBuilder();
 
        for (int i = 0; i < list.size(); i++){
        	Product product = list.get(i);
        	productBuilder.add("id", product.getId());
        	productBuilder.add("brand", product.getBrand());
        	productBuilder.add("name", product.getName());
        	productBuilder.add("description", product.getDescription());
        	productBuilder.add("price", product.getPrice());
        	JsonObject object = productBuilder.build();
        	productsBuilder.add(object);
        }
        	productBuilder2.add("products", productsBuilder);
        	JsonObject object = productBuilder2.build();
         
        //write to file
        OutputStream os = new FileOutputStream("/Users/Stijn/workspace/REST/products.json");
        JsonWriter jsonWriter = Json.createWriter(os);
        /**
         * We can get JsonWriter from JsonWriterFactory also
        JsonWriterFactory factory = Json.createWriterFactory(null);
        jsonWriter = factory.createWriter(os);
        */
        jsonWriter.writeObject(object);
        jsonWriter.close();
    }
	
}
