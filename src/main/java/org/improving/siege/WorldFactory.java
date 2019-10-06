package org.improving.siege;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import org.improving.siege.domain.*;
import org.improving.siege.exceptions.ItemNotFoundGameException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class WorldFactory {
    private Location startingLocation = null;
    private Location master = new Location("Master Room");

    public Location buildMasterLocation() {
        if (startingLocation != null) return startingLocation;

        loadLocations();
        loadItems();
        loadEnemies();
        loadLoots();

        return master;
    }

    private void loadLoots() {
        try {
            var lines = Files.readAllLines(Path.of(getResource("loots.txt")));
            lines.stream().forEach(l -> {
                var arr = l.split("\\|");
                if (arr.length == 7) {
                    var locationId = arr[0];
                    var name = arr[1];
                    var str = Integer.parseInt(arr[2]);
                    var dex = Integer.parseInt(arr[3]);
                    var con = Integer.parseInt(arr[4]);
                    var aliases = arr[5].split(",");
                    var equip = Integer.parseInt(arr[6]);
                    Enemy location = null;
                    try {
                        location = master.find(Enemy.class, locationId);
                    } catch (ItemNotFoundGameException e) {
                        e.printStackTrace();
                    }
                    createAt(location, name, str, dex, con, aliases, equip);
                }
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEnemies() {
        try {
            var lines = Files.readAllLines(Path.of(getResource("enemies.txt")));
            lines.stream().forEach(l -> {
                var arr = l.split("\\|");
                if (arr.length == 6) {
                    var locationId = arr[0];
                    var name = arr[1];
                    var str = Integer.parseInt(arr[2]);
                    var dex = Integer.parseInt(arr[3]);
                    var con = Integer.parseInt(arr[4]);
                    var aliases = arr[5].split(",");
                    Location location = null;
                    try {
                        location = master.findById(Location.class, locationId);
                    } catch (ItemNotFoundGameException e) {
                        e.printStackTrace();
                    }
                    var enemy = new Enemy(name,str,dex,con);
                    location.getItems().add(enemy);
                    master.getItems().add(enemy);
                }
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItems() {
        try {
            var lines = Files.readAllLines(Path.of(getResource("items.txt")));
            lines.stream().forEach(l -> {
                var arr = l.split("\\|");
                if (arr.length == 7) {
                    var locationId = arr[0];
                    var name = arr[1];
                    var str = Integer.parseInt(arr[2]);
                    var dex = Integer.parseInt(arr[3]);
                    var con = Integer.parseInt(arr[4]);
                    var aliases = arr[5].split(",");
                    var equip = Integer.parseInt(arr[6]);
                    Location location = null;
                    try {
                        location = master.findById(Location.class, locationId);
                    } catch (ItemNotFoundGameException e) {
                        e.printStackTrace();
                    }
                    createAt(location, name, str, dex, con, aliases, equip);
                }
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createAt(Container container, String name, int str, int dex, int con, String[] aliases, int equip) {
        WorldItem item = null;
        if (equip == 0) {
            item = new WorldItem(name, str, dex, con, aliases);
        } else {
            item = new EquippableWorldItem(name, str, dex, con, aliases);
        }
        container.getItems().add(item);
        master.getItems().add(item);
    }


    private void loadLocations() {
        try {
            GraphParser parser = new GraphParser(new FileInputStream(getResource("map.dot")));
            createLocations(parser.getNodes());
            createExits(parser.getEdges());
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getResource(String resourceName) throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource(resourceName);
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }

    private void createExits(Map<String, GraphEdge> edges) {
        for (var key : edges.keySet()) {
            var edge = edges.get(key);
            var from = find(edge.getNode1().getAttribute("label").toString());
            var to = find(edge.getNode2().getAttribute("label").toString());
            var aliases = edge.getAttribute("aliases").toString().split(",");
            var exit = new Exit(edge.getAttribute("label").toString(), to, aliases);
            from.getItems().add(exit);
            master.getItems().add(exit);
        }
    }

    private Location find(String name) {
        return master.findAll(Location.class)
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElse(null);
    }

    private void createLocations(Map<String, GraphNode> nodes) {
        for (var key : nodes.keySet()) {
            var node = nodes.get(key);
            var location = new Location(node.getAttribute("label").toString());
            location.setId(node.getId());
            master.getItems().add(location);
        }
    }
}
