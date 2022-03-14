package br.com.layon.movielist.dto;

import lombok.Data;

@Data
public class AwardInfo {
    String producer;
    int interval;
    int previousWin;
    int followingWin;
}
