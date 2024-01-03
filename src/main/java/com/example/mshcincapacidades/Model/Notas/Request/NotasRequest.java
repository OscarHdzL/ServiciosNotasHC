package com.example.mshcincapacidades.Model.Notas.Request;

import com.example.mshcincapacidades.Model.ModelIncapacidadRequest;

import lombok.Data;

@Data
public class NotasRequest {
    Boolean desc;
    ModelNotaRequest model;
    String order;
    Integer page;
    Integer pageSize;   
}
