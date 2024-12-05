package codelook.jpa.controller;

import codelook.jpa.order.OrderInfo;
import codelook.jpa.order.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    @WithMockUser(roles = "DEFAULT")  // Use a valid role for this test
    public void testViewCart() throws Exception {
        OrderInfo mockCart = new OrderInfo();
        when(shoppingCartService.getCart()).thenReturn(mockCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cart"))
                .andExpect(view().name("cart"));
    }

    @Test
    @WithMockUser(roles = "DEFAULT")
    public void testAddItemToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .with(csrf())
                        .param("listingId", "1")
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));


        verify(shoppingCartService).addItemToCart(1L, 2);
    }

    @Test
    @WithMockUser(roles = "DEFAULT")
    public void testCheckout() throws Exception {
        OrderInfo mockOrder = new OrderInfo();
        when(shoppingCartService.checkout()).thenReturn(mockOrder);

        mockMvc.perform(MockMvcRequestBuilders.get("/checkout"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("order"))
                .andExpect(view().name("checkout"));
    }
}

