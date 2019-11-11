package com.ghhwer.describeit.access;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ghhwer.describeit.access.ApiConstants.DATAMUSE_URL_WORDS;
import static com.ghhwer.describeit.access.ApiConstants.DATA_MUSE_FULL_BLOWN_SEARCH_TERM;

public interface DatamuseGet {
    @GET(DATAMUSE_URL_WORDS)
    Call<List<DatamuseWordMap>> getFullBlownData(@Query(DATA_MUSE_FULL_BLOWN_SEARCH_TERM) String searchTerm);
}
