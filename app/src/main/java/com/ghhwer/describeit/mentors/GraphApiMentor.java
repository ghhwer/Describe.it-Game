package com.ghhwer.describeit.mentors;

import android.util.Log;

import com.ghhwer.describeit.access.DatamuseWordMap;
import com.ghhwer.describeit.graphUI.GraphUI;
import com.ghhwer.describeit.mentors.structures.GraphStruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ghhwer.describeit.CrossAppVariables.ADJECTIVE_KEY;
import static com.ghhwer.describeit.CrossAppVariables.NOUN_KEY;
import static com.ghhwer.describeit.CrossAppVariables.NUMBER_RANDOM_RETRIEVAL;
import static com.ghhwer.describeit.CrossAppVariables.PROP_KEY;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_ADJECTIVE;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_NOUNS;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_PROP;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_SYMNS;
import static com.ghhwer.describeit.CrossAppVariables.SHOULD_GET_VERBS;
import static com.ghhwer.describeit.CrossAppVariables.SYNOM_KEY;
import static com.ghhwer.describeit.CrossAppVariables.VERB_KEY;
import static com.ghhwer.describeit.access.DatamuseRetrofit.getWordMapService;
import static com.ghhwer.describeit.utils.utils.arrayListFromArray;
import static com.ghhwer.describeit.utils.utils.pickSample;

public abstract class GraphApiMentor {
    protected GraphUI graphUI;
    protected ArrayList<String[]> nodesHistory = new ArrayList<>();
    protected ArrayList<String[]> linksHistory = new ArrayList<>();
    public int historyPointer = -1;

    public GraphApiMentor(GraphUI graphUI){
        this.graphUI = graphUI;
    }

    public void doSearch(String term){
        allowanceStateChange(false,false,false);
        clearVariables();
        onDoSearch(term);
    }
    public void expandOnTerm(String term){
        onExpandOnTerm(term);
    }

    protected void addToGraph(GraphStruct graphStruct){
        ArrayList<String> links;
        ArrayList<String> nodes;
        if (nodesHistory.size() > 0){
            links = arrayListFromArray(linksHistory.get(historyPointer));
            nodes = arrayListFromArray(nodesHistory.get(historyPointer));
        }
        else{
            links = new ArrayList<>();
            nodes = new ArrayList<>();
        }

        nodes.add(graphStruct.getName());
        nodes.addAll(Arrays.asList(graphStruct.getNodes()));
        links.addAll(Arrays.asList(graphStruct.getLinks()));
        graphUI.clearGraph();
        graphUI.pushToGraph(
                nodes.toArray(new String[nodes.size()]),
                links.toArray(new String[links.size()])
        );
        graphUI.graphRefresh();


        historyPointer += 1;

        if(historyPointer > nodesHistory.size()-1){
            nodesHistory.add(nodes.toArray(new String[nodes.size()]));
            linksHistory.add(links.toArray(new String[links.size()]));
        }
        else{
            nodesHistory.set(historyPointer, nodes.toArray(new String[nodes.size()]));
            linksHistory.set(historyPointer, links.toArray(new String[links.size()]));
        }


        allowanceStateChange(allowUndo(), allowRedo(), graphIsLoaded());
    }

    public void signalAllowanceChange(){
        allowanceStateChange(allowUndo(),allowRedo(),graphIsLoaded());
    }

    protected void clearVariables(){
        nodesHistory = new ArrayList<>();
        linksHistory = new ArrayList<>();
        historyPointer = -1;
        graphUI.clearGraph();
    }

    // Abstract functions
    public abstract void onDoSearch(String term);
    public abstract void onExpandOnTerm(String term);
    public abstract void allowanceStateChange(boolean allowUndo, boolean allowRedo, boolean graphIsLoaded);
    protected abstract void onApiStartLoad();
    protected abstract void onApiSuccess(String search, ArrayList<DatamuseWordMap> apiResult);
    protected abstract void onApiError();

    // Navigation Callers
    public boolean allowUndo(){
        if (historyPointer > 0 && historyPointer < nodesHistory.size())
            return true;
        else
            return false;
    }
    public boolean allowRedo(){
        if (historyPointer >= 0 && historyPointer < nodesHistory.size()-1)
            return true;
        else
            return false;
    }
    public boolean graphIsLoaded(){
        if (nodesHistory.size() > 0)
            return true;
        else
            return false;
    }
    public void undo(){
        if(!allowUndo())
            return;
        historyPointer -= 1;
        graphUI.clearGraph();
        graphUI.pushToGraph(
                nodesHistory.get(historyPointer),
                linksHistory.get(historyPointer)
        );
        graphUI.graphRefresh();
        allowanceStateChange(allowUndo(), allowRedo(), true);
    }
    public void redo(){
        if(!allowRedo())
            return;
        historyPointer += 1;
        graphUI.clearGraph();
        graphUI.pushToGraph(
                nodesHistory.get(historyPointer),
                linksHistory.get(historyPointer)
        );
        graphUI.graphRefresh();
        allowanceStateChange(allowUndo(), allowRedo(), graphIsLoaded());
    }

    private List<DatamuseWordMap> performFilterOnSearch(List<DatamuseWordMap> data){
        ArrayList<DatamuseWordMap> r = new ArrayList<>(data);

        for(DatamuseWordMap e : data){
            if(e.getTags() != null){
                if (
                        Arrays.asList(e.getTags()).contains(SYNOM_KEY) == !SHOULD_GET_SYMNS &&
                                Arrays.asList(e.getTags()).contains(ADJECTIVE_KEY) == !SHOULD_GET_ADJECTIVE &&
                                Arrays.asList(e.getTags()).contains(VERB_KEY) == !SHOULD_GET_VERBS &&
                                Arrays.asList(e.getTags()).contains( PROP_KEY) == !SHOULD_GET_PROP &&
                                Arrays.asList(e.getTags()).contains(NOUN_KEY) == !SHOULD_GET_NOUNS
                )
                    r.remove(e);
            }
            else{
                r.remove(e);
            }
        }
        return r;
    }

    protected void performApiSearch(final String search){
        onApiStartLoad();
        Call<List<DatamuseWordMap>> call = getWordMapService()
                .getFullBlownData(search);
        call.enqueue(new Callback<List<DatamuseWordMap>>() {
            @Override
            public void onResponse(Call<List<DatamuseWordMap>> call, Response<List<DatamuseWordMap>> response) {
                ArrayList<DatamuseWordMap> resp = new ArrayList<>(response.body());
                if(resp != null){
                    ArrayList<DatamuseWordMap> body = (ArrayList<DatamuseWordMap>) performFilterOnSearch(resp);
                    List<DatamuseWordMap> data = pickSample(body, NUMBER_RANDOM_RETRIEVAL);
                    onApiSuccess(search, (ArrayList<DatamuseWordMap>) data);
                }
            }

            @Override
            public void onFailure(Call<List<DatamuseWordMap>> call, Throwable t) {
                Log.e("DatamuseWordMap Error", " API GET ERROR:" + t.getMessage());
                onApiError();
            }
        });
    }

}
