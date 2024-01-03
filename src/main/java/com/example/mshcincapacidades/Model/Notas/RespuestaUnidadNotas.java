package com.example.mshcincapacidades.Model.Notas;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class RespuestaUnidadNotas {
        @Id
    public String _id;
    public String des_unidad;
}
