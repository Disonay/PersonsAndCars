package com.trainee.app.controller;

import com.trainee.app.exceptions.PersonNotFoundException;
import com.trainee.app.service.AppService;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(AppController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppService appService;

    @Test
    void testCorrectPerson() throws Exception {

        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person")
                        .content("""
                                {
                                "id": 1,
                                "name":"Сергей",
                                "birthdate":"25.10.2001"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": "1",
                                "name":"Сергей",
                                "birthdate":"25.10.2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPersonWithWrongId() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": "asdfas",
                                "name":"Сергей",
                                "birthdate":"25.10.2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonWithNullName() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": 1,
                                "birthdate":"25.10.2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonWIthNullBirthdate() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": 1,
                                "name":"Сергей"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonWithWrongBirthdate() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String future = format.format(DateUtil.tomorrow());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .param("id", "1")
                                .param("name", "Сергей")
                                .param("birthdate", future)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonWithNullId() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "name":"Сергей",
                                "birthdate":"25.10.2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonWithWrongBirthdateFormat() throws Exception {
        Mockito.doNothing().when(appService).savePerson(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": 1,
                                "name":"Сергей",
                                "birthdate":"25-10-2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/person")
                                .content("""
                                {
                                "id": 1,
                                "name":"Сергей",
                                "birthdate":"10.25.2001"
                                }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithWrongId() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "model":"BMW-X5",
                                       "horsepower":2,
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCarWithNullId() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "model":"BMW-X5",
                                       "horsepower":2,
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithNullModel() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "horsepower":2,
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithNullHorsePower() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "model":"BMW-X5",
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithNullOwnerId() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "model":"BMW-X5",
                                       "horsepower":2,
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithWrongModelFormat() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "model":"BMWX5",
                                       "horsepower":2,
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCarWithWrongHorsePower() throws Exception {
        Mockito.doNothing().when(appService).saveCar(any());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/car")
                                .content("""
                                       {
                                       "id":1,
                                       "model":"BMW-X5",
                                       "horsepower":-2,
                                       "ownerId":1
                                       }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testPersonNotFound() throws Exception {
        Mockito.when(appService.getPerson(any())).thenThrow(new PersonNotFoundException());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/personwithcars")
                                .queryParam("personId", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
