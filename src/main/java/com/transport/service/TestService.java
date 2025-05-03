package com.transport.service;

import com.transport.model.TestNode;
import com.transport.repository.TestRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepo testRepo;
    public TestService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public List<TestNode> findAll() {
        return testRepo.findAll();
    }

    public TestNode save(TestNode testNode) {
        return testRepo.save(testNode);
    }
}
