package codelook.jpa.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BookstoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // creating a new author
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateNewAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").with(csrf())
                        .param("name", "John Doe")
                        .param("bio", "An author bio")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())  // Expecting a redirection after creation
                .andExpect(header().string("Location", "/authors"));  // Redirects back to /authors page
    }

    // Test for creating a new book with multiple authors
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateNewBook() throws Exception {
        // create authors to add
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").with(csrf())
                        .param("name", "Author One")
                        .param("bio", "Author One bio")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.post("/authors").with(csrf())
                        .param("name", "Author Two")
                        .param("bio", "Author Two bio")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        // create a new book with authors from above
        mockMvc.perform(MockMvcRequestBuilders.post("/books").with(csrf())
                        .param("name", "Sample Book")
                        .param("description", "A sample book description")
                        .param("publisher", "Sample Publisher")
                        .param("pageCount", "200")
                        .param("genre", "Fiction")
                        .param("authorIds", "1")
                        .param("authorIds", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())  // Expecting a redirection after creation
                .andExpect(header().string("Location", "/allBooks"));  // Redirects to /allBooks page
    }
}