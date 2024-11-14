package codelook.jpa.controller;
import codelook.jpa.StaticData;
import codelook.jpa.request.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;


    @Test
    public void testNewUserRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StaticData.validRegistrationRequest)))
                .andExpect(status().isCreated());  // Expect HTTP 201 (Created)
    }

    @Test
    public void testBlankUserRegistration()  {
        ErrorResponse errorResponse = userController.validateUserRegistration(StaticData.allBlankRegistrationRequest);
        assertNotNull(errorResponse);
        assertEquals("Invalid user registration request", errorResponse.errorMessage());
        assert errorResponse.errorDetails().size() == 3;
    }

    @Test
    public void testNullUserRegistration()  {
        ErrorResponse errorResponse = userController.validateUserRegistration(StaticData.allNullRegistrationRequest);
        assertNotNull(errorResponse);
        assertEquals("Invalid user registration request", errorResponse.errorMessage());
        assert errorResponse.errorDetails().size() == 3;
    }

    @Test
    public void testBadFieldsRegistration()  {
        ErrorResponse errorResponse = userController.validateUserRegistration(StaticData.badPasswordAndEmailRegistrationRequest);
        assertNotNull(errorResponse);
        assertEquals("Invalid user registration request", errorResponse.errorMessage());
        assert errorResponse.errorDetails().size() == 2;
    }
}