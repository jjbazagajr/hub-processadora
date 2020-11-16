package br.com.hub.avaliacao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OutputMessage {
    private String from;
    private String name;
    private String time;
}
