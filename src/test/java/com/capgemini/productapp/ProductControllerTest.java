package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RestController
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;

	@Mock
	ProductService productService;
	MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testAddProductWhichReturnsProduct() throws Exception {
		Product product = new Product(12345,"Abi","asdfg",900);
		
		when(productService.addProduct(Mockito.isA(Product.class)))
		.thenReturn(product);
		
		
		mockMvc.perform(post("/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + 
						"	\"productId\":12345,\r\n" + 
						"	\"productCategory\":\"asdfg\",\r\n" + 
						"	\"productName\":\"Abi\",\r\n" + 
						"	\"productPrice\":900\r\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(12345))
				.andExpect(jsonPath("$.productCategory").value("asdfg"))
				.andExpect(jsonPath("$.productName").value("Abi"))
				.andExpect(jsonPath("$.productPrice").value(900))
				.andDo(print());
	
      }
	  
	  @Test
	  public void testDeleteProduct() throws Exception {
	  mockMvc.perform(delete("/product/").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value(200))
		.andExpect(jsonPath("$.message").value("Product has been deleted"))
		.andDo(print());
	}
	  

}