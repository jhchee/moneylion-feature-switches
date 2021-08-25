package com.github.jhchee.moneylionfeatureswitches.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jhchee.moneylionfeatureswitches.model.Feature;
import com.github.jhchee.moneylionfeatureswitches.model.User;
import com.github.jhchee.moneylionfeatureswitches.repository.IFeatureRepo;
import com.github.jhchee.moneylionfeatureswitches.repository.IUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FeatureSwitchController.class)
public class FeatureSwitchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserRepo userRepository;

    @MockBean
    private IFeatureRepo featureRepository;

    @Test
    void givenSwitchIsOn_whenGetStatus_thenReturnsCanAccess() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);

        user.getFeatures().add(feature);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        mockMvc.perform(
                get("/feature")
                        .param("email", email)
                        .param("featureName", feature_name)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(true))
                .andReturn();

    }

    @Test
    void givenSwitchIsOff_whenGetStatus_thenReturnsCannotAccess() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        mockMvc.perform(
                get("/feature")
                        .param("email", email)
                        .param("featureName", feature_name)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(false))
                .andReturn();

    }

}
