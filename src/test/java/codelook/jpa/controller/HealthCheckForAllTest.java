package codelook.jpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckForAllTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "admin")
    public void testGetAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/allBooks").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/allGenres").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllListings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/allListings").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/view").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void testGetCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void testGetCheckout() throws Exception {
        // Add an item to the cart
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(csrf())
                        .param("listingId", "1")  // Replace with valid listingId
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        // Perform the checkout
        mockMvc.perform(MockMvcRequestBuilders.get("/checkout").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("order"))  // Verify the 'order' attribute exists
                .andExpect(view().name("checkout"));  // Verify the correct view is returned
    }

    @Test
    @WithUserDetails(value = "admin")
    public void testGetEditAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/edit").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genre/horror").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetListing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/listing/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetLogOut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetNewBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/newBook").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetNewListing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/newListing").with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void testGetSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search-results?keyword=1984"))
                .andExpect(status().isOk());
    }

}
