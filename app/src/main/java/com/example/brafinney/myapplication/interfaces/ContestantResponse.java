package com.example.brafinney.myapplication.interfaces;

import com.example.brafinney.myapplication.models.Contestant;

import java.util.List;

/**
 * Created by pianoafrik on 5/11/17.
 */

public interface ContestantResponse {
    void OnSuccess (List<Contestant> contestants);
}
