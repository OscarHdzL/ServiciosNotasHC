package com.example.mshcincapacidades.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.mshcincapacidades.Model.Notas.NotaInicial;
import com.example.mshcincapacidades.Model.Notas.NotaInicialDetail;
import com.example.mshcincapacidades.Model.Notas.Referencia;

public interface NotaInicialRepository extends MongoRepository<NotaInicial, String>{
    

@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]},{'$eq':['$num_exp_folio',?2]},{'$eq':['$num_exp_anio',?3]},{'$eq':['$num_exp_unidado',?4]},{'$eq':['$cve_exp_cvesol','129']}, {'$eq':['$cve_estatus_expediente','A']}]}}}",
        "{'$sort':{'fec_nota':-1}}"
        
    }
)
List<NotaInicial> findNotasInicial(String num_nss, String agregado_medico, String num_exp_folio, String num_exp_anio, String num_exp_unidado);

@Aggregation(
    pipeline = {
        "{$match: { _id: ObjectId(?0) }}"
    }
)
NotaInicialDetail findNotaInicialById(String id);



}
