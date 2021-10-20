package com.assignment.drheart.controller;

import com.assignment.drheart.business.Medication;
import com.assignment.drheart.service.MedicationService;
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
@RequestMapping("/medication")
public class MedicationController {

    private MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Medication> getMedication(@PathVariable Integer id){
        return ResponseEntity.ok(medicationService.getMedicationById(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Medication saveMedication(@Valid @RequestBody Medication medication){
        return medicationService.createMedication(medication);
    }

    @GetMapping("/list")
    public List<Medication> getAllMedications(){
        return medicationService.getAllMedications();
    }

    @PutMapping
    public Medication updateMedication(@Valid @RequestBody Medication medication){
        return medicationService.updateMedication(medication);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMedication(@PathVariable Integer id){
        medicationService.deleteMedicationById(id);
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
