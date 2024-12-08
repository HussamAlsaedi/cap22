package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.RequestProperty;
import com.example.cap1.Services.RequestPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requestProperties")
@RequiredArgsConstructor
public class RequestPropertyController {
    @Autowired
    private RequestPropertyService requestPropertyService;
    @GetMapping("/get")
    public List<RequestProperty> getAllRequestProperties() {
        return requestPropertyService.getAll();
    }
   // endpoint  # 10
   // add and send mail to owner
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRequestProperty(@RequestBody @Valid RequestProperty requestProperty, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }

        requestPropertyService.addRequestProperty(requestProperty);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully added request property"));
    }

    @PutMapping("/update/{requestPropertyId}")
    public ResponseEntity<ApiResponse> updateRequestProperty(@PathVariable Long requestPropertyId ,@RequestBody @Valid RequestProperty requestProperty, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
        requestPropertyService.updateRequestProperty(requestPropertyId, requestProperty);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated request property"));
    }

    @DeleteMapping("/delete/{requestPropertyId}")
    public ResponseEntity<ApiResponse> deleteRequestProperty(@PathVariable Long requestPropertyId) {
        requestPropertyService.deleteRequestProperty(requestPropertyId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted request property"));
    }



}
