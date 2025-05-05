package com.transport.utils;

import com.transport.model.Location;
import com.transport.repository.LocationRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FuzzySearch {
    private final Node root;
    private final LevenshteinDistance distanceMetric;

    public FuzzySearch(LocationRepository locationRepository) {
        List<Location> locations = locationRepository.findAll();
        this.root = new Node(locations.getFirst().getName());
        this.distanceMetric = new LevenshteinDistance();

        locations.stream()
                .skip(1)
                .map(Location::getName)
                .forEach(this::add);
    }

    private static class Node {
        String word;
        Map<Integer, Node> children = new HashMap<>();

        Node(String word) {
            this.word = word;
        }
    }

    public void add(String word) {
        Node current = root;
        int dist;

        while (true) {
            dist = distanceMetric.apply(word, current.word);
            Node child = current.children.get(dist);

            if (child != null) {
                current = child;
            } else {
                current.children.put(dist, new Node(word));
                break;
            }
        }
    }

    public List<String> search(String query, int maxDistance) {
        List<String> results = new ArrayList<>();
        searchRecursive(root, query, maxDistance, results);
        return results;
    }

    private void searchRecursive(Node node, String query, int maxDistance, List<String> results) {
        int dist = distanceMetric.apply(query, node.word);
        if (dist <= maxDistance) {
            results.add(node.word);
        }

        for (int i = dist - maxDistance; i <= dist + maxDistance; i++) {
            Node child = node.children.get(i);
            if (child != null) {
                searchRecursive(child, query, maxDistance, results);
            }
        }
    }
}
