package com.example.mshcincapacidades.Model.Notas;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.mongodb.core.mapping.Document;


@Document(value="mc_salud_hos_nt_med_egreso_stg")
public class NotaEgreso extends NotaGenerica {
    public List<NotaGenericaDTO> listaSubnotas = new ArrayList<>();

    public List<NotaGenericaDTO> getListaSubnotas() {
        return listaSubnotas;
    }

    public void addItemListaSubnotas(NotaGenericaDTO item) {
        this.listaSubnotas.add(item);
    }

    public void setListaSubnotas(List<NotaGenericaDTO> listaSubnotas) {
        this.listaSubnotas = listaSubnotas;
    }


    
}
