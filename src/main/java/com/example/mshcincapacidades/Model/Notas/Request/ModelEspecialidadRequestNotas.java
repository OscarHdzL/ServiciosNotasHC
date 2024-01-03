package com.example.mshcincapacidades.Model.Notas.Request;

import lombok.Data;

@Data
public class ModelEspecialidadRequestNotas {
    String num_nss;
    String agregado_medico;    
    String des_ooad;
    String des_unidad;
    String cve_servicio; 
    String start;
    String end;
}
