package com.assignment.drheart.controller;

import com.assignment.drheart.business.Patient;
import com.assignment.drheart.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Integer id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Patient savePatient(@Valid @RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    @GetMapping("/list")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
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
