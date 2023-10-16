package com.awesome.pizza.controller;

import com.awesome.pizza.AnotherOrderInProcesssException;
import com.awesome.pizza.service.PizzaChefOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PizzaChefOrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PizzaChefOrderService service;

    @Test
    @DisplayName("When admin resource is requested without authorization then return forbidden")
    public void whenUnauthenticatedThenReturnForbidden() throws Exception {
        mockMvc.perform(post("/v1/admin/order/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("When admin resource is requested with authorization then return ok")
    public void whenAuthenticatedThenReturnOk() throws Exception {
        mockMvc.perform(get("/v1/admin/order/all")
                        .header("Authorization", "Basic YWRtaW46YWRtaW4="))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When order is in process than can't pick another one")
    public void whenOrderIsRequestedAndAnotherInProgressThenError() throws Exception {
        AnotherOrderInProcesssException exception = new AnotherOrderInProcesssException("Another order already in process");
        doThrow(exception).when(service).getNextOrder();

        mockMvc.perform(get("/v1/admin/order/")
                        .header("Authorization", "Basic YWRtaW46YWRtaW4="))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Another order already in process"));
    }

}