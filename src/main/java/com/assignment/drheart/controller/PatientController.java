package com.assignment.drheart.controller;

import com.assignment.drheart.business.Medication;
import com.assignment.drheart.business.Patient;
import com.assignment.drheart.business.PatientFull;
import com.assignment.drheart.service.MedicationService;
import com.assignment.drheart.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;
    private MedicationService medicationService;

    public PatientController(PatientService patientService, MedicationService medicationService){
        this.patientService = patientService;
        this.medicationService = medicationService;
    }

    @GetMapping("{id}")
    public PatientFull getPatient(@PathVariable Integer id){
        return patientService.getPatientById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Patient savePatient(@Valid @RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    @GetMapping("{patientId}/medication")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Medication> getMedicationForPatient(@PathVariable Integer patientId){
        boolean exists = patientService.existsById(patientId);
        if (!exists){
            throw new EntityNotFoundException();
        }
        return medicationService.getMedicationByPatientId(patientId);
    }

    @PostMapping("{patientId}/medication")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Medication> addMedicationForPatient(@PathVariable Integer patientId, @Valid @RequestBody List<Medication> medicationList){
        boolean exists = patientService.existsById(patientId);
        if (!exists){
            throw new EntityNotFoundException();
        }
        return medicationService.createMedication(patientId, medicationList);
    }

    @PutMapping("{patientId}/medication")
    public Medication updateMedicationForPatient(@PathVariable Integer patientId, @Valid @RequestBody Medication medication){
        boolean exists = patientService.existsById(patientId);
        if (!exists){
            throw new EntityNotFoundException();
        }
        return medicationService.updateMedication(patientId, medication);
    }

    @DeleteMapping("{patientId}/medication/{medicationId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMedicationForPatient(@PathVariable Integer patientId, @PathVariable Integer medicationId){
        boolean exists = patientService.existsById(patientId);
        if (!exists){
            throw new EntityNotFoundException();
        }

        medicationService.deleteMedicationById(medicationId);
    }

    @GetMapping("/list")
    public List<Patient> getAllPatients(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String query){
        return patientService.getAllPatients(sortBy,query);
    }

    @PutMapping
    public Patient updatePatient(@Valid @RequestBody Patient patient){
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Integer id){
        patientService.deletePatientById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
