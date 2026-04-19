package com.kcarcare.controller;

import com.kcarcare.dto.CreateBranchRequest;
import com.kcarcare.entity.Branch;
import com.kcarcare.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/branches")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody CreateBranchRequest request) {
        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setLocation(request.getLocation());
        return ResponseEntity.ok(branchRepository.save(branch));
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchRepository.findAll());
    }
}
