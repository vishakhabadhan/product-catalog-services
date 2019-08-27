# ProductCatalogService

# API end points -
## POST - localhost:8080/productCatalog/createProduct

	{
		"productId" :"7",
		"productBrand": "BRAND_2",
		"productColor": "CLOOR_1",
		"productDescription": "DESCRIPTION_1",
		"productPrice" : "22.10",
		"productTitle" :"TITLE_1"
	}

## POST - localhost:8080/productCatalog/createProductList
	[	
		{
			"productId" :"727",
			"productBrand": "BRAND_2",
			"productColor": "CLOOR_1",
			"productDescription": "DESCRIPTION_1",
			"productPrice" : "22.10",
			"productTitle" :"TITLE_1"
		},
		{
			"productId" :"717",
			"productBrand": "BRAND_2",
			"productColor": "CLOOR_1",
			"productDescription": "DESCRIPTION_1",
			"productPrice" : "22.10",
			"productTitle" :"TITLE_1"
		},
		{
			"productId" :"27",
			"productBrand": "BRAND_2",
			"productColor": "CLOOR_1",
			"productDescription": "DESCRIPTION_1",
			"productPrice" : "22.10",
			"productTitle" :"TITLE_1"
		},
		{
			"productId" :"17",
			"productBrand": "BRAND_2",
			"productColor": "CLOOR_1",
			"productDescription": "DESCRIPTION_1",
			"productPrice" : "22.10",
			"productTitle" :"TITLE_1"
		}
	]
	
## POST - localhost:8080/productCatalog/searchProduct
	{
		"productCatalogSearchModel" : 
			{	
				"productCatalogTitle" : "TITLE_1",
				"productCatalogDescription": ""
			},
		"productCatalogSortModel" : {
			"productCatelogSortAttributes" :["color", "brand"],
			"productCatelogSortOrder" :"ASC",
			"pageIndex":"0",
			"pageSize" : "5"
		}
	}
	
## GET - localhost:8080/productCatalog/productDetail?productId=777

	{
	    "id": 1,
	    "productId": 777,
	    "title": "TITLE_1",
	    "description": "DESCRIPTION_1",
	    "brand": "BRAND_2",
	    "color": "CLOOR_1",
	    "price": 22.1
	}
	
## DELETE - localhost:8080/productCatalog/deleteProduct?productId=777

## POST - localhost:8080/productCatalog/productListByTitleAndDescription
	
	{	
		"productCatalogTitle" : "TITLE_1",
		"productCatalogDescription": "DESCRIPTION_1"
	}
	
# Design

### ProductCatalogServiceStarter.java is project starter class.

### The project is designed to create a product in a product catalog. 

### ProductModel.java is a persistence entity for product and ProductHistoryModel.java provides persistence for product history.

### ProductCatalogRepository.java and ProductCatalogHistoryRepository.java are respective jpa repositories for entity classes. 

### ProductCatalogService.java and ProductCatalogHistoryService.java are service classes for Product and its history. 

### ProductCatalogController.java is a controller class.

### SpringSecurityConfiguration.java class configures authentication for application.
username - product 
password - product

### SwaggerConfiguration.java enables swagger to play around the api.

Add product -
productId is unique and not null which identifies a product. 

# ProductCatalogService.java
### "addProductToProductCatalog" in ProductCatalogService stores product as well as its history at the same time. For each individual product insertion or update the same function 

### "addProductToProductCatalog" is used. It evaluates the input product from history and accordingly stores it in the db.

### Similarly "addProductToProductCatalogList" provides batch processing inorder to store several products at a time and used to store a list of products to the database.
I have considered a special case where the input product list may have duplicate products that is the reason each individual project is stored and evaluated individually.

### deleteProductByProductId is used to delete a product from product catalog but I have kept in consideration that history is created for the product while deleting it.

### Generic searching and sorting functionalities are implemented using sprinf JPA capabilities. "searchProductByProductCatalogFilter" uses wrapper filter 
objects for search as well as sort parameters and provides page<Product>.

### Product content can be find by product id using "getProductByProductId".

# H2 DATABASE -
### Database can be connected on http://localhost:8080/h2-console
### JDBC URL - jdbc:h2:mem:productdb
### User name- sa
### password No password required.
DB configuration is available in application.properties inside src/main/resources folder structure.

#TESTING Folder -

Integration testing -
src/test/java
Spring runner is used in "Transactional" mode.

SpringRunner together with RestTemplate is used.

# POM.XML
H2 DB dependency.
Springforx for swagger api.
Spring boot jpa, starter, tomcat etc.
Project build -
a) mvn package OR mvn clean install. It will create ProductCatalogServices.jar in target folder.

b) java -jar ProductCatalogServices.jar Execute it using command prompt. OR a)It is a SpringBoot project and can be simply executed as a java project from ProductCatalogServiceStarter.java class.

#Docker
a) Create a Dockerfile
b) Build an image using command ==> docker build -t product-catalog-services .
c) Run docker container ==> docker run -d -p 8080:8080 product-catalog-services
d) List of built Docker images ==> docker ps -a
e) Container Logs==> docker logs <container_name or container_id>












































