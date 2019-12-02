package com.ghhwer.describeit.mentors;

import com.ghhwer.describeit.access.DatamuseWordMap;
import com.ghhwer.describeit.graphUI.GraphUI;
import com.ghhwer.describeit.mentors.structures.GraphStruct;

import java.util.ArrayList;

public abstract class ExploreGraphApiMentor extends GraphApiMentor {

    public ExploreGraphApiMentor(GraphUI graphUI){
        super(graphUI);
    }

    @Override
    public void onDoSearch(String term){
        performApiSearch(term);
    }

    @Override
    public void onExpandOnTerm(String term){
        performApiSearch(term);
    }

    @Override
    protected void onApiStartLoad(){
        onStartLoad();
    }

    @Override
    public void onApiSuccess(String search, ArrayList<DatamuseWordMap> apiResult){
        ArrayList<String> links = new ArrayList<>();
        ArrayList<String> nodes = new ArrayList<>();

        for (DatamuseWordMap e:apiResult){
            links.add(search+'-'+e.getWord());
            nodes.add(e.getWord());
        }

        GraphStruct gStruct = new GraphStruct(
                search,
                nodes.toArray(new String[nodes.size()]),
                links.toArray(new String[links.size()])
        );
        addToGraph(gStruct);
        onEndLoad(false);
    }


    @Override
    protected void onApiError(){
        onEndLoad(true);
    }

    public abstract void onEndLoad(boolean error);
    public abstract void onStartLoad();
}
