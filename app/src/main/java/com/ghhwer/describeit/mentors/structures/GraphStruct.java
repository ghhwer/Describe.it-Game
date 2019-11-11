package com.ghhwer.describeit.mentors.structures;

public class GraphStruct {
    private String name;
    private String[] nodes;
    private String[] links;
    private int layer = 0;

    public GraphStruct(String Name, String[] Nodes, String[] Links){
        this.name = Name;
        this.nodes = Nodes;
        this.links = Links;
    }

    public GraphStruct(String Name, String[] Nodes, String[] Links, int Layer){
        this.name = Name;
        this.nodes = Nodes;
        this.links = Links;
        this.layer = Layer;
    }

    public String getName() {
        return name;
    }

    public String[] getNodes() {
        return nodes;
    }

    public String[] getLinks() {
        return links;
    }

    public int getLayer() {
        return layer;
    }
}
