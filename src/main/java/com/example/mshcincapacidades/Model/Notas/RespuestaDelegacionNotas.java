package com.example.mshcincapacidades.Model.Notas;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class RespuestaDelegacionNotas {
    @Id
    public String _id;
    public String des_ooad;
}
