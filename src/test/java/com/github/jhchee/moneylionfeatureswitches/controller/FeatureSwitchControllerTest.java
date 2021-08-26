package com.github.jhchee.moneylionfeatureswitches.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jhchee.moneylionfeatureswitches.model.Feature;
import com.github.jhchee.moneylionfeatureswitches.model.User;
import com.github.jhchee.moneylionfeatureswitches.repository.IFeatureRepo;
import com.github.jhchee.moneylionfeatureswitches.repository.IUserRepo;
import com.github.jhchee.moneylionfeatureswitches.viewModel.SwitchRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(jsonPath("$.canAccess").value(true));

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
                .andExpect(jsonPath("$.canAccess").value(false));

    }

    @Test
    void givenUserAndFeatureAreAbsent_whenGetStatus_thenReturnsCannotAccess() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        mockMvc.perform(
                get("/feature")
                        .param("email", email)
                        .param("featureName", feature_name)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(false));

    }

    @Test
    void givenSwitchIsOff_whenEnablingSwitch_thenReturnsUpdateSuccess() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        SwitchRequest request = new SwitchRequest(email, feature_name, true);

        mockMvc.perform(
                post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isOk());

        // user feature is modified
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenSwitchIsOn_whenDisablingSwitch_thenReturnsUpdateSuccess() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);
        user.getFeatures().add(feature);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        SwitchRequest request = new SwitchRequest(email, feature_name, false);

        mockMvc.perform(
                post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isOk());

        // user feature is modified
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenSwitchIsOn_whenEnablingSwitch_thenReturnsNotModified() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);
        user.getFeatures().add(feature);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        SwitchRequest request = new SwitchRequest(email, feature_name, true);

        mockMvc.perform(
                post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isNotModified());

        // no resource is modified
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void givenSwitchIsOff_whenDisablingSwitch_thenReturnsNotModified() throws Exception {
        String email = "random@mail.com";
        String feature_name = "random_feature";

        User user = new User(1, email);
        Feature feature = new Feature(1, feature_name);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(featureRepository.findFeatureByName(feature_name)).thenReturn(feature);

        SwitchRequest request = new SwitchRequest(email, feature_name, false);

        mockMvc.perform(
                post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isNotModified());

        // no resource is modified
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void givenInvalidEmailOrFeatureName_whenGetStatus_thenReturnsBadRequest() throws Exception {
        String invalidEmail = "123";
        String invalidFeatureName = "";

        mockMvc.perform(
                get("/feature")
                        .param("email", invalidEmail)
                        .param("featureName", "valid_name")
        ).andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                get("/feature")
                        .param("email", "random@gmail.com")
                        .param("featureName", invalidFeatureName)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
