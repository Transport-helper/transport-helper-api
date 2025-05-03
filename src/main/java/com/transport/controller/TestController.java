package com.transport.controller;

import com.transport.model.TestNode;
import com.transport.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<TestNode> findAll() {
        return testService.findAll();
    }

    @PostMapping
    public TestNode save(@RequestBody TestNode testNode) {
        return testService.save(testNode);
    }
}
