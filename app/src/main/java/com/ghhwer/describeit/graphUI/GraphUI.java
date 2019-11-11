package com.ghhwer.describeit.graphUI;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import jp.kai.forcelayout.Forcelayout;
import jp.kai.forcelayout.properties.GraphStyle;

import static com.ghhwer.describeit.utils.utils.removeDuplicates;
import static com.ghhwer.describeit.utils.utils.removeInArray;


/**
 *
 * WRITTEN BY: GHHWER (11-7-2019)
 * --------------
 * USAGE EXAMPLE:
 * --------------
 *
 * graphUI = new GraphUI(fLayout);
 *
 * String[] nodes = {"A","B","C"};
 * String[] links = {"A-B", "A-C"};
 *
 * graphUI.pushToGraph(nodes, links);
 * graphUI.graphRefresh();
 *
 * **/

public class GraphUI {

    private Forcelayout fLayout;

    // Touch Response Variables
    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHOLD = 200;

    //Graph Props
    private ArrayList<String> nodes = new ArrayList<>();
    private ArrayList<String> edges = new ArrayList<>();

    //Nodes
    int nodeSize = 100;
    int nodeColor = Color.argb(255,100,100,100);

    //Edges
    int edgeWidth = 10;
    int edgeColor  = Color.argb(50,100,100,100);

    public GraphUI(Forcelayout fLayout){
        // Customize Fonts
        GraphStyle.INSTANCE.setFontColor(Color.argb(200,100,100,100));
        GraphStyle.INSTANCE.setFontSelectedColor(Color.argb(255,0,0,0));
        GraphStyle.INSTANCE.setNodeSelectedColor(Color.argb(255,0,0,0));
        this.fLayout = fLayout;
        // Setup Events
        setTouchEvents();
    }

    public String getSelectedNode(){
        int id = fLayout.getSelectedNode();
        try{
            return nodes.get(id);
        }
        catch (Exception e){
            return null;
        }
    }

    public void pushToGraph(String[] nodes, String[] edges){
        this.nodes.addAll(Arrays.asList(nodes));
        this.edges.addAll(Arrays.asList(edges));
        this.nodes = removeDuplicates(this.nodes);
        this.edges = removeDuplicates(this.edges);
    }

    public void removeFromGraph(String[] nodes, String[] edges){
        this.nodes = removeInArray(this.nodes, new ArrayList<>(Arrays.asList(nodes)));
        this.edges = removeInArray(this.edges, new ArrayList<>(Arrays.asList(edges)));
    }

    public void clearGraph(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    // Style Stuff
    public void setStyleNodes(int c, int size){
        this.nodeColor = c;
        this.nodeSize = size;
    }
    public void serStyleEdges(int c, int edgeWidth){
        this.edgeWidth = edgeWidth;
        this.edgeColor = c;
    }

    public void graphRefresh(){
        fLayout.node()
                .size(nodeSize)
                .style(nodeColor);
        fLayout.link()
                .style(edgeColor, edgeWidth);
        fLayout.with()
                .distance(500)
                /** distance between each nodes */
                .gravity(0.01)
                /** gravitation from center of view */
                .friction(0.004)
                /** value of gravitation between each nodes */
                .size(100)
                /** node width */
                .nodes(nodes.toArray(new String[nodes.size()]))
                /** set nodes */
                .links(edges)
                /** set links */
                .start();
    }

    private void setTouchEvents(){
        fLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int target = fLayout.getTargetNode();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastTouchDown = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHOLD) {
                            fLayout.setSelectedNode((target));
                        }
                        break;
                }
                return false;
            }
        });
    }
}
