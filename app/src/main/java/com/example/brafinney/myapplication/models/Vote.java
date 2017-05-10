package com.example.brafinney.myapplication.models;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class Vote {
    String id, voterId, votedFor, numberOfVotes;

    public Vote(String voterId, String votedFor, String numberOfVotes) {
        this.voterId = voterId;
        this.votedFor = votedFor;
        this.numberOfVotes = numberOfVotes;
    }

    public Vote(String id, String voterId, String votedFor, String numberOfVotes) {
        this.id = id;
        this.voterId = voterId;
        this.votedFor = votedFor;
        this.numberOfVotes = numberOfVotes;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVotedFor() {
        return votedFor;
    }

    public void setVotedFor(String votedFor) {
        this.votedFor = votedFor;
    }

    public String getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(String numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
