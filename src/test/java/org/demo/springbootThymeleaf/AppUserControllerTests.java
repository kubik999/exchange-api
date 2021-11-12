package org.demo.springbootThymeleaf;

import org.demo.springbootThymeleaf.controller.UserController;
import org.demo.springbootThymeleaf.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
public class AppUserControllerTests {


    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserController controller;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void contexLoadsUserControllerTest() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void UserControllerTest() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/form");
        this.mockMvc.perform(builder)
                .andExpect(ok);
    }
}
