package edu.ap.jaxrs;

import java.io.*;
import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.xml.bind.*;

@RequestScoped
@Path("/products")
public class ProductResource {
	
	@GET
	@Produces({"text/html"})
	public String getProductsHTML() {
		String htmlString = "<html><body>";
		
		try {
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			for(Product product : listOfProducts) {
				htmlString += "<b>Name : " + product.getName() + "</b><br>";
				htmlString += "Id : " + product.getId() + "<br>";
				htmlString += "Brand : " + product.getBrand() + "<br>";
				htmlString += "Description : " + product.getDescription() + "<br>";
				htmlString += "Price : " + product.getPrice() + "<br>";
				htmlString += "<br><br>";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlString;
	}
	
	@GET
	@Produces({"application/json"})
	public String getProductsJSON() {
		String jsonString = "{\"products\" : [";
		try {
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			for(Product product : listOfProducts) {
				jsonString += "{\"name\" : \"" + product.getName() + "\",";
				jsonString += "\"id\" : " + product.getId() + ",";
				jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
				jsonString += "\"description\" : \"" + product.getDescription() + "\",";
				jsonString += "\"price\" : " + product.getPrice() + "},";
			}
			jsonString = jsonString.substring(0, jsonString.length()-1);
			jsonString += "]}";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	
	@GET
	@Produces({"text/xml"})
	public String getProductsXML() {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		xmlString += "<productsXML>";
		try {
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			for(Product product : listOfProducts) {
					xmlString += "<product>";
					xmlString += "<name>" + product.getName() + "</name>";
					xmlString += "<id>" + product.getId() + "</id>";
					xmlString += "<brand>" + product.getBrand() + "</brand>";
					xmlString += "<description>" + product.getDescription() + "</description>";
					xmlString += "<price>" + product.getPrice() + "</price>";
					xmlString += "</product>";
			}
			xmlString += "</productsXML>";
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;
	}

	@GET
	@Path("/{shortname}")
	@Produces({"application/json"})
	public String getProductJSON(@PathParam("shortname") String shortname) {
		String jsonString = "";
		try {
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			// look for the product, using the shortname
			for(Product product : listOfProducts) {
				if(shortname.equalsIgnoreCase(product.getName())) {
					jsonString += "{\"name\" : \"" + product.getName() + "\",";
					jsonString += "\"id\" : " + product.getId() + ",";
					jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
					jsonString += "\"description\" : \"" + product.getDescription() + "\",";
					jsonString += "\"price\" : " + product.getPrice() + "}";
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	
	@GET
	@Path("/{shortname}")
	@Produces({"text/xml"})
	public String getProductXML(@PathParam("shortname") String shortname) {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		try {
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			// look for the product, using the shortname
			for(Product product : listOfProducts) {
				if(shortname.equalsIgnoreCase(product.getName())) {
					xmlString += "<product>";
					xmlString += "<name>" + product.getName() + "</name>";
					xmlString += "<id>" + product.getId() + "</id>";
					xmlString += "<brand>" + product.getBrand() + "</brand>";
					xmlString += "<description>" + product.getDescription() + "</description>";
					xmlString += "<price>" + product.getPrice() + "</price>";
					xmlString += "</product>";
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;
	}
	
	@POST
	@Consumes({"text/xml"})
	public void processFromXML(String productXML) {
		
		/* newProductXML should look like this :
		 *  
		 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 <product>
        	<brand>BRAND</brand>
        	<description>DESCRIPTION</description>
        	<id>123456</id>
        	<price>20.0</price>
        	<shortname>SHORTNAME</shortname>
        	<sku>SKU</sku>
		 </product>
		 */
		
		try {
			// get all products
			ProductJSONReader reader = new ProductJSONReader();
			ArrayList<Product> listOfProducts = reader.getProducts();
			
			// unmarshal new product
			JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
			Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
			StringReader reader2 = new StringReader(productXML);
			Product newProduct = (Product)jaxbUnmarshaller2.unmarshal(reader2);
			
			// add product to existing product list 
			// and update list of products in  productsXML
			listOfProducts.add(newProduct);
			reader.setProducts(listOfProducts);
			
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}