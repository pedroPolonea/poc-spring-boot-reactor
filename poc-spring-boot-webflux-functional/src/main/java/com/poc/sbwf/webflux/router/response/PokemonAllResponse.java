package com.poc.sbwf.webflux.router.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonAllResponse {
    private int count;
    private String next;
    private int previous;
    private List<PokemonAllResultResponse> results;
}
