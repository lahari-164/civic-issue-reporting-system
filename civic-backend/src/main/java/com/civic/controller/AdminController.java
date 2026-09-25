package com.civic.controller;
import com.civic.entity.Officer;

import com.civic.dto.OfficerCreateRequest;
import com.civic.entity.Issue;
import com.civic.entity.User;
import com.civic.service.IssueService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IssueService issueService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(IssueService issueService, PasswordEncoder passwordEncoder) {
        this.issueService = issueService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/issues")
    public List<Issue> allIssues() {
        return issueService.getAllIssues();
    }

    @DeleteMapping("/issues/{id}")
    public void delete(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }

    @PutMapping("/issues/{id}/reassign")
    public Issue reassign(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return issueService.reassignCategory(id, body.get("category"));
    }

    @PostMapping("/officers")
    public Officer createOfficer(@RequestBody OfficerCreateRequest req) {
        return issueService.createOfficer(req, passwordEncoder);
    }

    @GetMapping("/officers")
    public List<Officer> officers() {
        return issueService.getOfficers();
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        List<Issue> all = issueService.getAllIssues();
        long resolved = all.stream().filter(i -> i.getStatus().name().equals("RESOLVED")).count();
        return Map.of(
            "total", all.size(),
            "resolved", resolved,
            "pending", all.size() - resolved
        );
    }
}