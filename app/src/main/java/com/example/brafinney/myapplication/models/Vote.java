package com.example.brafinney.myapplication.models;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class Vote {
    String id, contestant_id, voteCount;

    public Vote(String id, String contestant_id, String voteCount) {
        this.id = id;
        this.contestant_id = contestant_id;
        this.voteCount = voteCount;
    }

    public Vote(String contestant_id, String voteCount) {
        this.contestant_id = contestant_id;
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public String getContestant_id() {
        return contestant_id;
    }

    public String getVoteCount() {
        return voteCount;
    }
}
