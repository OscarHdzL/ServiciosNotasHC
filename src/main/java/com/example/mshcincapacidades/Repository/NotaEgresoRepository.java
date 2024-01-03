package com.example.mshcincapacidades.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.mshcincapacidades.Model.Incapacidad;
import com.example.mshcincapacidades.Model.IncapacidadNSSA;
import com.example.mshcincapacidades.Model.RespuestaDelegacion;
import com.example.mshcincapacidades.Model.RespuestaDiagnostico;
import com.example.mshcincapacidades.Model.RespuestaIncapacidad;
import com.example.mshcincapacidades.Model.RespuestaUnidad;
import com.example.mshcincapacidades.Model.Notas.NotaEgreso;
import com.example.mshcincapacidades.Model.Notas.NotaEgresoDetail;
import com.example.mshcincapacidades.Model.Notas.NotaEvolucion;
import com.example.mshcincapacidades.Model.Notas.Referencia;
import com.example.mshcincapacidades.Model.Notas.ReferenciaDetail;
import com.example.mshcincapacidades.Model.Notas.RespuestaDelegacionNotas;
import com.example.mshcincapacidades.Model.Notas.RespuestaUnidadNotas;
import com.example.mshcincapacidades.Model.Notas.SolicitudServicioDetail;

@Repository
public interface NotaEgresoRepository extends MongoRepository<NotaEgreso, String>{
    

@Aggregation(
    pipeline = {
//        "{ $project : { cve_exp_cvesol:1, cve_estatus_expediente:1,  cve_pac_nss : 1 , cve_pac_amedico : 1, cve_pac_curp : 1, fec_nota: 1, des_ooad: 1, des_unidad: 1, cve_especialidad: 1, cve_servicio: 1, cve_nivel: 1, des_diagnostico : 1, des_notas : 1, num_exp_folio: 1, num_exp_anio: 1, num_exp_unidado: 1, num_exp_consec: 1 }}",
        "{ '$match' : {'$and' : [{'cve_pac_nss': ?0},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}",
        "{ '$group': {'_id': { 'fecha':'$fec_nota', 'folio': '$num_exp_folio', 'anio': '$num_exp_anio'}}}",
        "{ '$count': 'conteoNotas' }"
    }
)
Integer countNotasEgresoByNssAgregado(String num_nss, String agregado_medico, String order, Integer desc, Integer skip, Integer limit);


@Aggregation(
    pipeline = {
        "{ $project : { cve_exp_cvesol:1, cve_estatus_expediente:1,  cve_pac_nss : 1 , cve_pac_amedico : 1, cve_pac_curp : 1, fec_nota: 1, des_ooad: 1, des_unidad: 1, cve_especialidad: 1, cve_servicio: 1, cve_nivel: 1, des_diagnostico : 1, des_notas : 1, num_exp_folio: 1, num_exp_anio: 1, num_exp_unidado: 1, num_exp_consec: 1 }}",
        "{ '$match' : {'$and' : [{'cve_pac_nss': ?0},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}",
        "{ '$skip': ?4 }",
        "{ '$limit': ?5}"
    }
)
List<NotaEgreso> findNotasEgresoByNssAgregadoPaginado(String num_nss, String agregado_medico, String order, Integer desc, Integer skip, Integer limit);



/* DELEGACIONES */

@Aggregation(
    pipeline = {
        "{'$match':{'$and':[{'cve_pac_nss':?0},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}",
        "{'$group':{'_id':{'des_ooad':{ '$toUpper': '$des_ooad' }},'des_ooad':{'$first': { '$toUpper': '$des_ooad' } }}},{'$sort':{'des_ooad':1}}"
    }
)
List<RespuestaDelegacionNotas> findDelegacionesByNssAgregado(String num_nss, String agregado_medico);



/* UNIDADES */

@Aggregation(
    pipeline = {
        "{'$match':{'$and':[{'cve_pac_nss':?0},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}",
        "{'$group':{'_id':{'des_unidad':'$des_unidad'},'des_unidad':{'$first': '$des_unidad'}}},{'$sort':{'des_unidad':1}}"
    }
)
List<RespuestaUnidadNotas> findUnidadesByNssAgregado(String num_nss, String agregado_medico);

@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]}, "+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]} ]}}}",
         "{'$group':{'_id':{'des_unidad':'$des_unidad'},'des_unidad':{'$first': '$des_unidad'}}},{'$sort':{'des_unidad':1}}"
        
    }
)
List<RespuestaUnidadNotas> findUnidadesByNssAgregadoFechas(String num_nss, String agregado_medico, Date start, Date end);


@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},{'$regexMatch':{'input':'$des_ooad','regex':?2,'options':'i'}}]}}}",
         "{'$group':{'_id':{'des_unidad':'$des_unidad'},'des_unidad':{'$first': '$des_unidad'}}},{'$sort':{'des_unidad':1}}"
    }
)
List<RespuestaUnidadNotas> findUnidadesByNssAgregadoDelegacion(String num_nss, String agregado_medico, String delegacion);

@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}}]}}}",
         "{'$group':{'_id':{'des_unidad':'$des_unidad'},'des_unidad':{'$first': '$des_unidad'}}},{'$sort':{'des_unidad':1}}"
    }
)
List<RespuestaUnidadNotas> findUnidadesByNssAgregadoFechasDelegacion(String num_nss, String agregado_medico, Date start, Date end, String delegacion);


////NOTAS TEST
/* @Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},"+
        "{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}}]}}}",
        "{'$sort':{'?5':?6}}",
        "{ '$skip': ?7 }",
        "{ '$limit': ?8}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechasDelegacion(String num_nss, String agregado_medico, Date start, Date end, String delegacion, String order, Integer desc, Integer skip, Integer limit );
 */

 //NOTAS
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}},"+
        "{'$sort':{'?2':?3}}",
        "{ '$skip': ?4 }",
        "{ '$limit': ?5}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregado(String num_nss, String agregado_medico, String order, Integer desc, Integer skip, Integer limit );



@Aggregation(
    pipeline = {
        "{$match: { _id: ObjectId(?0) }}"
    }
)
NotaEgresoDetail findNotaEgresoById(String id);


@Aggregation(
    pipeline = {
//        "{ $project : { cve_exp_cvesol:1, cve_estatus_expediente:1,  cve_pac_nss : 1 , cve_pac_amedico : 1, cve_pac_curp : 1, fec_nota: 1, des_ooad: 1, des_unidad: 1, cve_especialidad: 1, cve_servicio: 1, cve_nivel: 1, des_diagnostico : 1, des_notas : 1, num_exp_folio: 1, num_exp_anio: 1, num_exp_unidado: 1, num_exp_consec: 1 }}",
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']}]}}},"+
        "{ '$group': {'_id': { 'fecha':'$fec_nota', 'folio': '$num_exp_folio', 'anio': '$num_exp_anio'}}}",
        "{ '$count': 'conteoNotas' }"
    }
)
Integer contadorNotasEgresoByNssAgregado(String num_nss, String agregado_medico);



 //NOTAS BY FECHA
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]}]}}},"+
        "{'$sort':{'?4':?5}}",
        "{ '$skip': ?6 }",
        "{ '$limit': ?7}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechas(String num_nss, String agregado_medico, Date start, Date end, String order, Integer desc, Integer skip, Integer limit );

//NOTAS BY FECHA AND DELEGACION
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},"+
        "{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}}]}}}",
        "{'$sort':{'?5':?6}}",
        "{ '$skip': ?7 }",
        "{ '$limit': ?8}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechasDelegacion(String num_nss, String agregado_medico, Date start, Date end, String delegacion, String order, Integer desc, Integer skip, Integer limit );


//NOTAS BY FECHA AND DELEGACION AND UNIDAD
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},"+
        "{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}},{'$regexMatch':{'input':'$des_unidad','regex':?5,'options':'i'}}]}}}",
        "{'$sort':{'?6':?7}}",
        "{ '$skip': ?8 }",
        "{ '$limit': ?9}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechasDelegacionUnidad(String num_nss, String agregado_medico, Date start, Date end, String delegacion, String unidad, String order, Integer desc, Integer skip, Integer limit );


//NOTAS BY FECHA AND DELEGACION AND UNIDAD AND SERVICIO
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},"+
        "{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}},{ '$regexMatch':{'input':'$des_unidad','regex':?5,'options':'i'}},{ '$regexMatch':{'input':'$cve_servicio','regex':?6,'options':'i'}}]}}}",
        "{'$sort':{'?7':?8}}",
        "{ '$skip': ?9 }",
        "{ '$limit': ?10}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechasDelegacionUnidadServicio(String num_nss, String agregado_medico, Date start, Date end, String delegacion, String unidad, String servicio, String order, Integer desc, Integer skip, Integer limit );


//NOTAS BY FECHA AND DELEGACION AND UNIDAD AND SERVICIO AND ESPECIALIDAD
@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]}, {'$eq':['$cve_exp_cvesol','76']},{'$eq':['$cve_estatus_expediente','A']},"+
        "{ '$gte' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?2]},"+
        "{ '$lt' : [{ '$dateFromString': { 'dateString': '$fec_nota', 'format': '%Y-%m-%d %H:%M:%S'}},?3]},"+
        "{ '$regexMatch':{'input':'$des_ooad','regex':?4,'options':'i'}},{ '$regexMatch':{'input':'$des_unidad','regex':?5,'options':'i'}},{ '$regexMatch':{'input':'$cve_servicio','regex':?6,'options':'i'}},{ '$regexMatch':{'input':'$cve_especialidad','regex':?7,'options':'i'}}]}}}",
        "{'$sort':{'?8':?9}}",
        "{ '$skip': ?10 }",
        "{ '$limit': ?11}"
        
    }
)
List<NotaEgreso> findNotasByNssAgregadoFechasDelegacionUnidadServicioEspecialidad(String num_nss, String agregado_medico, Date start, Date end, String delegacion, String unidad, String servicio, String especialidad, String order, Integer desc, Integer skip, Integer limit );




@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]},{'$eq':['$num_exp_folio',?2]},{'$eq':['$num_exp_anio',?3]},{'$eq':['$num_exp_unidado',?4]},{'$eq':['$cve_exp_cvesol','127']}, {'$eq':['$cve_estatus_expediente','A']}]}}}",
        "{'$sort':{'fec_nota':-1}}"
        
    }
)
List<ReferenciaDetail> findNotasReferencia(String num_nss, String agregado_medico, String num_exp_folio, String num_exp_anio, String num_exp_unidado);


@Aggregation(
    pipeline = {
        "{$match: { _id: ObjectId(?0) }}"
    }
)
Referencia findReferenciaById(String id);


@Aggregation(
    pipeline = {
        "{'$match':{'$expr':{'$and':[{'$eq':['$cve_pac_nss',?0]},{'$eq':['$cve_pac_amedico',?1]},{'$eq':['$num_exp_folio',?2]},{'$eq':['$num_exp_anio',?3]},{'$eq':['$num_exp_unidado',?4]},{'$eq':['$cve_exp_cvesol','3']}, {'$eq':['$cve_estatus_expediente','A']}]}}}",
        "{'$sort':{'fec_nota':-1}}"
        
    }
)
List<SolicitudServicioDetail> findNotasSolicitudServicio(String num_nss, String agregado_medico, String num_exp_folio, String num_exp_anio, String num_exp_unidado);


@Aggregation(
    pipeline = {
        "{$match: { _id: ObjectId(?0) }}"
    }
)
Referencia findSolicitudById(String id);






}
