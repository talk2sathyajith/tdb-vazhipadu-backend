package com.temple.tdb_vazhipadu_backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TdbVazhipaduBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateOrderWithTotalOf110() throws Exception {

        // Create Pushpanjali for ₹30
        mockMvc.perform(post("/api/offerings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "Pushpanjali",
                      "price": 30.00,
                      "active": true
                    }
                    """))
            .andExpect(status().isCreated());

        // Create Archana for ₹50
        mockMvc.perform(post("/api/offerings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "Archana",
                      "price": 50.00,
                      "active": true
                    }
                    """))
            .andExpect(status().isCreated());

        // This test assumes a clean database and generated IDs 1 and 2.
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "templeId": 1,
                      "devoteeName": "Rahul",
                      "phone": "9876543210",
                      "nakshatra": "Rohini",
                      "bookingDate": "2026-10-27",
                      "items": [
                        {
                          "offeringId": 1,
                          "quantity": 2
                        },
                        {
                          "offeringId": 2,
                          "quantity": 1
                        }
                      ]
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.totalAmount").value(110.0))
            .andExpect(jsonPath("$.items.length()").value(2));
    }
}