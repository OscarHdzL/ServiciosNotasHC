package com.example.mshcincapacidades.Model.Notas.Request;

import lombok.Data;

@Data
public class ModelDelegacionRequestNotas {
    String num_nss;
    String agregado_medico;    
    //String diagnostico;    
    String des_ooad;    
    /* String unidad_expedidora;     */
    String start;
    String end;  
}
