package com.ghhwer.describeit.mentors;

import com.ghhwer.describeit.access.DatamuseWordMap;
import com.ghhwer.describeit.graphUI.GraphUI;
import com.ghhwer.describeit.mentors.structures.GraphStruct;

import java.util.ArrayList;

import static com.ghhwer.describeit.CrossAppVariables.NUMBER_RANDOM_RETRIEVAL;

public abstract class GameGraphApiMentor extends GraphApiMentor {

    private boolean alreadyDecided;
    private int numOfMoves = 0;
    private int currentLayer;
    private int maxGameLayerDepth_ = 1;
    private String targetWord;
    private String startWord;
    private ArrayList<GraphStruct> fullGraph = new ArrayList<>();

    public GameGraphApiMentor(GraphUI graphUI) {
        super(graphUI);
    }

    private void resetGameVariables(){
        // Reset Variables
        alreadyDecided = false;
        targetWord = "";
        numOfMoves = 0;
        currentLayer = 0;
        // Reset GraphApiMentor Variables
        clearVariables();
    }

    @Override
    public void onDoSearch(String term) {
        onStartLoad();
        resetGameVariables();
        // Get Graph
        performApiSearch(term);
        // set start word
        startWord = term;
    }

    @Override
    public void onExpandOnTerm(String term) {
        if(term.equals(targetWord)){
            signalWin(numOfMoves);
            graphUI.clearGraph();
            graphUI.graphRefresh();
            resetGameVariables();

            return;
        }
        numOfMoves += 1;
        GraphStruct graphStruct = findInGraph(term);
        if (graphStruct != null)
            addToGraph(graphStruct);
        signalAllowanceChange();
    }

    @Override
    protected void onApiStartLoad() {
        onStartLoad();
    }

    @Override
    protected void onApiSuccess(String search, ArrayList<DatamuseWordMap> apiResult) {
        // Nodes and Links
        String[] nodes = new String[apiResult.size()];
        String[] links = new String[apiResult.size()];
        int i = 0;
        for(DatamuseWordMap e:apiResult){
            nodes[i] = e.getWord();
            links[i] = search+'-'+e.getWord();
            i++;
        }
        addToBuffer(new GraphStruct(search, nodes, links, currentLayer));
    }

    private void addToBuffer(GraphStruct gStruct){
        fullGraph.add(gStruct);

        if(((fullGraph.size()-1) % NUMBER_RANDOM_RETRIEVAL  == 0)){
            if (currentLayer == maxGameLayerDepth_){
                targetWord = fullGraph.get(fullGraph.size()-1).getNodes()[0];
                onEndLoad(false);
                addToGraph(findInGraph(startWord));
            }
            else{
                currentLayer +=1;
                //Reprocess
                for(GraphStruct e : fullGraph)
                    if(e.getLayer() == currentLayer-1)
                        for (String q : e.getNodes())
                            performApiSearch(q);
            }
        }
    }

    public String getTargetWord() {
        return targetWord;
    }

    private GraphStruct findInGraph(String name){
        for(GraphStruct e : fullGraph){
            if (e.getName() == name)
                return e;
        }
        return null;
    }

    @Override
    protected void onApiError(){
        onEndLoad(true);
    }

    public abstract void signalWin(int score);
    public abstract void onEndLoad(boolean error);
    public abstract void onStartLoad();
}
