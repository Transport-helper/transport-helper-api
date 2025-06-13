package com.transport.utils;

import com.transport.model.Location;
import com.transport.repository.LocationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrieSearch {
    private final Node root = new Node();
    private final LocationRepository locationRepository;

    public TrieSearch(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    private static class Node {
        String word = null;
        Map<Character, Node> children = new HashMap<>();
        boolean isEnd = false;
    }

    @PostConstruct
    private void initializeTrie() {
        List<Location> locations = locationRepository.findAll();
        for (Location location : locations) {
            insert(location.getName());
        }
    }

    public void insert(String word) {
        Node currentNode = root;
        for (char ch : word.toCharArray()) {
            currentNode = currentNode.children.computeIfAbsent(ch, k -> new Node());
        }
        currentNode.isEnd = true;
        currentNode.word = word;
    }

    public List<String> search(String word) {
        Node currentNode = root;
        for (char ch : word.toCharArray()) {
            currentNode = currentNode.children.get(ch);
            if (currentNode == null) {
                return new ArrayList<>();
            }
        }
        List<String> result = new ArrayList<>();
        dfs(currentNode, result);
        return result;
    }

    private void dfs(Node currentNode, List<String> res) {
        if (currentNode.isEnd) {
            res.add(currentNode.word);
        }

        for (Map.Entry<Character, Node> entry : currentNode.children.entrySet()) {
            dfs(entry.getValue(), res);
        }
    }
}
