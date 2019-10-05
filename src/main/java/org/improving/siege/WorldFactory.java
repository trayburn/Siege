package org.improving.siege;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import org.improving.siege.domain.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WorldFactory {
    private Location startingLocation = null;
    private List<Location> locations = new ArrayList<>();

    public Location getStartingLocation() {
        if (startingLocation != null) return startingLocation;

        try {
            URL res = getClass().getClassLoader().getResource("map.dot");
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getAbsolutePath();
            GraphParser parser = new GraphParser(new FileInputStream(absolutePath));
            createLocations(parser.getNodes());
            createExits(parser.getEdges());
            Map<String, GraphEdge> edges = parser.getEdges();
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }

        var forest = find("Forest");
        var pond = find("The Pond");
        forest.getItems().add(new StatisticItem(
                "A golden ring",
                0, 1, 0,
                "golden", "ring"));

        var lady = new Enemy("The Lady of the Lake", 2, 5, 10);
        lady.getItems().add(new Item("Excalibur"));
        pond.setEnemy(lady);

        return forest;
    }

    private void createExits(Map<String, GraphEdge> edges) {
        for (var key : edges.keySet()) {
            var edge = edges.get(key);
            var from = find(edge.getNode1().getAttribute("label").toString());
            var to = find(edge.getNode2().getAttribute("label").toString());
            var aliases = edge.getAttribute("aliases").toString().split(",");
            var exit = new Exit(edge.getAttribute("label").toString(), to, aliases);
            from.getExits().add(exit);
        }
    }

    private Location find(String name) {
        return locations.stream()
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElse(null);
    }

    private void createLocations(Map<String, GraphNode> nodes) {
        for (var key : nodes.keySet()) {
            var node = nodes.get(key);
            var location = new Location(node.getAttribute("label").toString());
            locations.add(location);
        }
    }
}
