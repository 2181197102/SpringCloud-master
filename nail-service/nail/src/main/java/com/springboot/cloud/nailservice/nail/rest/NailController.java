package com.springboot.cloud.nailservice.nail.rest;

import com.springboot.cloud.nailservice.nail.clients.ApplicationClient;
import com.springboot.cloud.nailservice.nail.clients.UserClient;
import jdk.jshell.Diag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class NailController {
    private final DiagService diagService;

    @Autowired
    public DiagController(DiagService diagService) {
        this.diagService = diagService;
    }



    @Autowired
    ApplicationClient applicationClient;

    @Autowired
    UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Diag> getDiagById(@PathVariable String id) {
        Diag diag = diagService.getDiagById(id);
        if (diag != null) {
            return new ResponseEntity<>(diag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Diag>> getDiagsByUserId(@PathVariable String userId) {
        List<Diag> diags = diagService.getDiagsByUserId(userId);
        if (!diags.isEmpty()) {
            return new ResponseEntity<>(diags, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Diag> createDiag(@RequestBody Diag diag) {
        Diag createdDiag = diagService.createDiag(diag);
        return new ResponseEntity<>(createdDiag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diag> updateDiag(@PathVariable String id, @RequestBody Diag diag) {
        Diag updatedDiag = diagService.updateDiag(id, diag);
        if (updatedDiag != null) {
            return new ResponseEntity<>(updatedDiag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiag(@PathVariable String id) {
        diagService.deleteDiag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
