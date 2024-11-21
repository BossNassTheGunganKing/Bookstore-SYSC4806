package codelook.jpa.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test viewing the cart.
     */
    @Test
    @WithMockUser
    public void testViewCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cart"))
                .andExpect(view().name("cart"));
    }

    /**
     * Test displaying the form to add an item to the cart.
     */
    @Test
    @WithMockUser
    public void testAddItemForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("listings"))
                .andExpect(view().name("addItem"));
    }

    /**
     * Test adding an item to the cart.
     */
    @Test
    @WithMockUser
    public void testAddItemToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(csrf())
                        .param("listingId", "1")
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    /**
     * Test removing an item from the cart.
     */
    /**
     * Test removing an item from the cart.
     */
    @Test
    @WithMockUser
    public void testRemoveItemFromCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(csrf())
                        .param("listingId", "1")
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/remove").with(csrf())
                        .param("itemId", "1")  // Replace with valid itemId
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cart", hasProperty("items", empty())));
    }

    /**
     * Test updating an item's quantity in the cart.
     */
    @Test
    @WithMockUser
    public void testUpdateCartItemQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .param("listingId", "1")
                        .param("quantity", "2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());


        mockMvc.perform(MockMvcRequestBuilders.post("/cart/update")
                        .param("itemId", "1")
                        .param("quantity", "5")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection()); // Expect redirection after update
    }

//    /**
//     * Test checking out the cart.
//     */
//@Test
//@WithMockUser
//public void testCheckout() throws Exception {
//    // Add an item to the cart
//    mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(csrf())
//                    .param("listingId", "1")  // Replace with valid listingId
//                    .param("quantity", "2")
//                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/cart"));
//
//    // Perform the checkout
//    mockMvc.perform(MockMvcRequestBuilders.get("/checkout"))
//            .andExpect(status().isOk())
//            .andExpect(model().attributeExists("order"))  // Verify the 'order' attribute exists
//            .andExpect(view().name("checkout"));  // Verify the correct view is returned
//}
}
