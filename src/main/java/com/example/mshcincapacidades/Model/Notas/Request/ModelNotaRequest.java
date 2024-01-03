package com.example.mshcincapacidades.Model.Notas.Request;

import lombok.Data;

@Data
public class ModelNotaRequest {
    String id;
    String num_nss;
    String agregado_medico;    
    String diagnostico;    
    String des_ooad;    
    String des_unidad;    
    String cve_servicio;    
    String cve_especialidad;    
    String start;
    String end;
}
