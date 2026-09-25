package com.civic.controller;

import com.civic.dto.IssueRequest;
import com.civic.dto.PublicIssueResponse;
import com.civic.entity.Issue;
import com.civic.service.IssueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public Issue create(
            @RequestPart("issue") String issueJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            Authentication auth) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        IssueRequest req = objectMapper.readValue(issueJson, IssueRequest.class);

        return issueService.createIssue(req, photo, auth.getName());
    }

    @GetMapping
    public List<PublicIssueResponse> getAll() {
        return issueService.getAllIssuesPublic();
    }

    @GetMapping("/{id}")
    public PublicIssueResponse getOne(@PathVariable Long id) {
        return issueService.getIssuePublic(id);
    }

    @GetMapping("/my")
    public List<Issue> myIssues(Authentication auth) {
        return issueService.getMyIssues(auth.getName());
    }

    @PostMapping("/{id}/upvote")
    public Map<String, Object> upvote(@PathVariable Long id, Authentication auth) {
        boolean upvoted = issueService.toggleUpvote(id, auth.getName());
        long count = issueService.getUpvoteCount(issueService.getIssue(id));
        return Map.of("upvoted", upvoted, "count", count);
    }

    @GetMapping("/{id}/upvotes")
    public Map<String, Object> upvoteInfo(@PathVariable Long id, Authentication auth) {
        long count = issueService.getUpvoteCount(issueService.getIssue(id));
        boolean upvotedByMe = issueService.hasUserUpvoted(id, auth.getName());
        return Map.of("count", count, "upvotedByMe", upvotedByMe);
    }

    @PostMapping("/{id}/comments")
    public void addComment(@PathVariable Long id, @RequestBody Map<String, String> body, Authentication auth) {
        issueService.addComment(id, body.get("text"), auth.getName());
    }

    @GetMapping("/{id}/comments")
    public List<?> getComments(@PathVariable Long id) {
        return issueService.getComments(id);
    }
}