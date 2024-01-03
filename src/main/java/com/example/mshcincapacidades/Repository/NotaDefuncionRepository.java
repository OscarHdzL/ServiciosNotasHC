package com.example.mshcincapacidades.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mshcincapacidades.Model.Notas.NotaDefuncion;
import com.example.mshcincapacidades.Model.Notas.NotaDefuncionDetail;
import com.example.mshcincapacidades.Model.Notas.NotaInicial;


public interface NotaDefuncionRepository extends MongoRepository<NotaDefuncion, String>{
    

@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]},{'$eq':['$num_exp_folio',?2]},{'$eq':['$num_exp_anio',?3]},{'$eq':['$num_exp_unidado',?4]},{'$eq':['$cve_exp_cvesol','158']}, {'$eq':['$cve_estatus_expediente','A']}]}}}",
        "{'$sort':{'fec_nota':-1}}"
        
    }
)
List<NotaDefuncion> findNotasDefuncion(String num_nss, String agregado_medico, String num_exp_folio, String num_exp_anio, String num_exp_unidado);



@Aggregation(
    pipeline = {
        "{$match: { _id: ObjectId(?0) }}"
    }
)
NotaDefuncionDetail findNotaDefuncionById(String id);



}
