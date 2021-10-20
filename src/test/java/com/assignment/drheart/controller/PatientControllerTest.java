package com.assignment.drheart.controller;

import com.assignment.drheart.business.Medication;
import com.assignment.drheart.business.Patient;
import com.assignment.drheart.business.PatientFull;
import com.assignment.drheart.entity.Gender;
import com.assignment.drheart.service.MedicationService;
import com.assignment.drheart.service.PatientService;
import com.assignment.drheart.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @MockBean
    private PatientService patientService;

    @MockBean
    private MedicationService medicationService;

    @Autowired
    PatientController patientController;

    @Autowired
    private MockMvc mvc;

    @Test
    void getPatient() throws Exception {
        Medication medication = new Medication();
        medication.setPatientId(1);
        medication.setDescription("paracetamol");

        PatientFull alex = new PatientFull();
        alex.setId(1);
        alex.setGender(Gender.MALE);
        alex.setMedicationList(Collections.singletonList(medication));

        given(patientService.getPatientById(1)).willReturn(alex);

        mvc.perform(get("/patient/1"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(alex.getId())))
                .andExpect(jsonPath("$.medicationList", hasSize(1)));
    }

    @Test
    void savePatient_withoutErrors() throws Exception {
        Patient alex = new Patient();
        alex.setId(1);
        alex.setFirstName("Alex");
        alex.setLastName("Test");
        alex.setGender(Gender.MALE);
        alex.setDob(LocalDate.of(1996,12,12));

        given(patientService.createPatient(alex)).willReturn(alex);

        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(alex)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(alex.getId())));
    }

    @Test
    void savePatient_withValidationErrors() throws Exception {
        Patient alex = new Patient();
        alex.setFirstName("");
        alex.setLastName("");
        alex.setGender(Gender.MALE);
        alex.setDob(LocalDate.of(2020,12,12));

        given(patientService.createPatient(alex)).willReturn(alex);

        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(alex)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName", is("First name should not be blank")))
                .andExpect(jsonPath("$.lastName", is("Last name should not be blank")))
                .andExpect(jsonPath("$.dob", is("Must be older than 18")));
    }

    @Test
    void getMedicationForPatient() throws Exception {

        Medication medication = new Medication();
        medication.setPatientId(1);
        medication.setDescription("paracetamol");

        given(patientService.existsById(1)).willReturn(true);
        given(medicationService.getMedicationByPatientId(1)).willReturn(Collections.singletonList(medication));

        mvc.perform(get("/patient/1/medication"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(medication.getDescription())));
    }

    @Test
    void addMedicationForPatient() throws Exception {

        Medication medication = new Medication();
        medication.setPatientId(1);
        medication.setDescription("paracetamol");

        given(patientService.existsById(1)).willReturn(true);
        List<Medication> medicationList = Collections.singletonList(medication);
        given(medicationService.createMedication(1, medicationList)).willReturn(medicationList);

        mvc.perform(post("/patient/1/medication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(medicationList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(medication.getDescription())));

    }

    @Test
    void updateMedicationForPatient() throws Exception {

        Medication medication = new Medication();
        medication.setPatientId(1);
        medication.setDescription("paracetamol");

        Medication medication2 = new Medication();
        medication2.setPatientId(1);
        medication2.setDescription("paracetamol2");

        given(patientService.existsById(1)).willReturn(true);
        given(medicationService.updateMedication(1, medication)).willReturn(medication2);

        mvc.perform(put("/patient/1/medication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(medication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(medication2.getDescription())));

    }

    @Test
    void deleteMedicationForPatient() throws Exception {
        given(patientService.existsById(1)).willReturn(true);

        mvc.perform(delete("/patient/1/medication/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void getAllPatients() throws Exception {
        Patient alex = new Patient();
        alex.setId(1);
        alex.setGender(Gender.MALE);

        Patient oana = new Patient();
        oana.setId(2);
        oana.setGender(Gender.FEMALE);

        List<Patient> patients = Arrays.asList(alex,oana);

        given(patientService.getAllPatients(null,null)).willReturn(patients);

        mvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(alex.getId())))
                .andExpect(jsonPath("$[1].id", is(oana.getId())));
    }

    @Test
    void updatePatient() throws Exception {
        Patient alex = new Patient();
        alex.setId(1);
        alex.setFirstName("Alex");
        alex.setLastName("Test");
        alex.setGender(Gender.MALE);
        alex.setDob(LocalDate.of(1996,12,12));

        Patient alex2 = new Patient();
        alex2.setId(1);
        alex2.setFirstName("Alex");
        alex2.setLastName("Test");
        alex2.setGender(Gender.MALE);
        alex2.setDob(LocalDate.of(1996,12,13));

        given(patientService.updatePatient(alex)).willReturn(alex2);

        mvc.perform(put("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(alex)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dob", is(alex2.getDob().toString())));
    }

    @Test
    void deletePatient() throws Exception {

        mvc.perform(delete("/patient/1"))
                .andExpect(status().isNoContent());
    }

}