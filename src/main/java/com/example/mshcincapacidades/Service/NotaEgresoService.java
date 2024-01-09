package com.example.mshcincapacidades.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnionWithOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.mshcincapacidades.Model.IncapacidadNSSA;
import com.example.mshcincapacidades.Model.IncapacidadesRequest;
import com.example.mshcincapacidades.Model.RespuestaIncapacidad;
import com.example.mshcincapacidades.Model.Notas.NotaDefuncion;
import com.example.mshcincapacidades.Model.Notas.NotaDefuncionDetail;
import com.example.mshcincapacidades.Model.Notas.NotaEgreso;
import com.example.mshcincapacidades.Model.Notas.NotaEgresoDetail;
import com.example.mshcincapacidades.Model.Notas.NotaEvolucion;
import com.example.mshcincapacidades.Model.Notas.NotaEvolucionDetail;
import com.example.mshcincapacidades.Model.Notas.NotaGenerica;
import com.example.mshcincapacidades.Model.Notas.NotaGenericaDTO;
import com.example.mshcincapacidades.Model.Notas.NotaInicial;
import com.example.mshcincapacidades.Model.Notas.NotaInicialDetail;
import com.example.mshcincapacidades.Model.Notas.NotaInicialGinecologia;
import com.example.mshcincapacidades.Model.Notas.NotaInicialGinecologiaDetail;
import com.example.mshcincapacidades.Model.Notas.NotaRevision;
import com.example.mshcincapacidades.Model.Notas.NotaRevisionDetail;
import com.example.mshcincapacidades.Model.Notas.Referencia;
import com.example.mshcincapacidades.Model.Notas.ReferenciaDetail;
import com.example.mshcincapacidades.Model.Notas.RespuestaDelegacionNotas;
import com.example.mshcincapacidades.Model.Notas.RespuestaEspecialidadNotas;
import com.example.mshcincapacidades.Model.Notas.RespuestaServicioNotas;
import com.example.mshcincapacidades.Model.Notas.RespuestaUnidadNotas;
import com.example.mshcincapacidades.Model.Notas.SolicitudServicioDetail;
import com.example.mshcincapacidades.Model.Notas.Request.DelegacionRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.EspecialidadRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.ModelDelegacionRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.ModelNotaRequest;
import com.example.mshcincapacidades.Model.Notas.Request.NotasRequest;
import com.example.mshcincapacidades.Model.Notas.Request.ServicioRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.SolictudReferenciasRequest;
import com.example.mshcincapacidades.Model.Notas.Request.UnidadRequestNotas;
import com.example.mshcincapacidades.Repository.IncapacidadNSSARepository;
import com.example.mshcincapacidades.Repository.NotaDefuncionRepository;
import com.example.mshcincapacidades.Repository.NotaEgresoRepository;
import com.example.mshcincapacidades.Repository.NotaEvolucionRepository;
import com.example.mshcincapacidades.Repository.NotaInicialGinecoRepository;
import com.example.mshcincapacidades.Repository.NotaInicialRepository;
import com.example.mshcincapacidades.Repository.NotaRevisionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaEgresoService {

    @Autowired
    private MongoTemplate mt;

    @Autowired
    private final NotaEgresoRepository notaEgresoRepository;

    @Autowired
    private final NotaEvolucionRepository notaEvolucionRepository;

    @Autowired
    private final NotaRevisionRepository notaRevisionRepository;

    @Autowired
    private final NotaInicialRepository notaInicialRepository;

    @Autowired
    private final NotaInicialGinecoRepository notaInicialGinecoRepository;

    @Autowired
    private final NotaDefuncionRepository notaDefuncionRepository;
    
    

    public Object findNotasEgresoNSSAMedico(NotasRequest request) {

        // return notaEgresoRepository.findNotasEgresoById("6581c757c6b143ac82ec79a1");

        // return
        // notaEgresoRepository.countNotasEgresoByNssAgregado(request.getModel().getNum_nss(),
        // request.getModel().getAgregado_medico(), "",-1, request.getPage()-1,
        // request.getPageSize());
        return notaEgresoRepository.findNotasEgresoByNssAgregadoPaginado(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), "", -1, request.getPage() - 1, request.getPageSize());
        // return
        // notaEgresoRepository.findDelegacionesByNssAgregado(request.getModel().getNum_nss(),
        // request.getModel().getAgregado_medico());
    }


    public List<RespuestaDelegacionNotas> findDelegaciones(DelegacionRequestNotas request){
        return notaEgresoRepository.findDelegacionesByNssAgregado(request.getModel().getNum_nss(), request.getModel().getAgregado_medico());
    }

    public List<RespuestaUnidadNotas> findUnidades(UnidadRequestNotas request) throws ParseException {

        if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() == null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            // Formato entrada => "1/1/2021"
            String[] splitStart = request.getModel().getStart().split("/");
            String[] splitEnd = request.getModel().getEnd().split("/");

            return notaEgresoRepository.findUnidadesByNssAgregadoFechas(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate);

        } else if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() != null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            String[] splitStart = request.getModel().getStart().split("/");
            String[] splitEnd = request.getModel().getEnd().split("/");



            return notaEgresoRepository.findUnidadesByNssAgregadoFechasDelegacion(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad());
        } else if (request.getModel().getStart() == null && request.getModel().getEnd() == null
                && request.getModel().getDes_ooad() != null) {

            return notaEgresoRepository.findUnidadesByNssAgregadoDelegacion(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), request.getModel().getDes_ooad());
        } else {
            return notaEgresoRepository.findUnidadesByNssAgregado(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico());
        }

    }



    public List<RespuestaServicioNotas> findServicio(ServicioRequestNotas request) throws ParseException {

        if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() == null && request.getModel().getDes_unidad() == null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            // Formato entrada => "1/1/2021"

            return notaEgresoRepository.findServicioByNssAgregadoFechas(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate);

        } else if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            return notaEgresoRepository.findServicioByNssAgregadoFechasDelegacionUnidad(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(), request.getModel().getDes_unidad());
        } else if (request.getModel().getStart() == null && request.getModel().getEnd() == null
                && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null) {

            return notaEgresoRepository.findServicioByNssAgregadoDelegacionUnidad(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), request.getModel().getDes_ooad(), request.getModel().getDes_unidad());
        } else {
            return notaEgresoRepository.findServicioByNssAgregado(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico());
        }

    }

    public List<RespuestaEspecialidadNotas> findEspecialidad(EspecialidadRequestNotas request) throws ParseException {

        if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() == null && request.getModel().getDes_unidad() == null && request.getModel().getCve_servicio() == null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            // Formato entrada => "1/1/2021"

            return notaEgresoRepository.findEspecialidadByNssAgregadoFechas(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate);

        } else if (request.getModel().getStart() != null && request.getModel().getEnd() != null
                && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null && request.getModel().getCve_servicio() != null) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = dateFormat.parse(request.getModel().getStart());
            Date toDate = dateFormat.parse(request.getModel().getEnd());

            Calendar c1 = Calendar.getInstance();
            c1.setTime(toDate);
            c1.add(Calendar.DATE, 1);
            toDate = c1.getTime();

            return notaEgresoRepository.findEspecialidadByNssAgregadoFechasDelegacionUnidadServicio(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(), request.getModel().getDes_unidad(), request.getModel().getCve_servicio());
        } else if (request.getModel().getStart() == null && request.getModel().getEnd() == null
                && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null && request.getModel().getCve_servicio() != null) {

            return notaEgresoRepository.findEspecialidadByNssAgregadoDelegacionUnidadServicio(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico(), request.getModel().getDes_ooad(), request.getModel().getDes_unidad(), request.getModel().getCve_servicio());
        } else {
            return notaEgresoRepository.findEspecialidadByNssAgregado(request.getModel().getNum_nss(),
                    request.getModel().getAgregado_medico());
        }

    }

    /// NOTAS
    public Page<NotaEgreso> findNSS_AgregadoMedico(NotasRequest request) throws ParseException {

        Pageable paging = PageRequest.of(request.getPage() - 1, request.getPageSize());

        final List<NotaEgreso> results = getIncapacidadesGroup(request, paging);
        return new PageImpl<>(results, paging, 100);
    }

    private List<NotaEgreso> getIncapacidadesGroup(NotasRequest request, Pageable pageable)
            throws ParseException {

        // PAGINACION
        SkipOperation skip = new SkipOperation(pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation limit = new LimitOperation(pageable.getPageSize());

        SortOperation sort = Aggregation.sort(request.getDesc() ? Sort.Direction.DESC : Sort.Direction.ASC,
                request.getOrder());

        // CONDICIONES DE BUSQUEDA
        Criteria cNSS = Criteria.where("cve_pac_nss").is(request.getModel().getNum_nss());
        Criteria agregado = new Criteria("cve_pac_amedico").is(request.getModel().getAgregado_medico());
        Criteria agregadoVacio = new Criteria("cve_pac_amedico").is("");

        Criteria cAgregadoMedico = new Criteria().orOperator(agregado, agregadoVacio);

        MatchOperation matchStage = null;
        Collection<Criteria> cCriteriosFiltros = new ArrayList<>();

        if (request.getModel().getStart() != null && request.getModel().getEnd() != null) {

            CriteriosSoloFechas(request, cCriteriosFiltros);
        }
        /*
         * if (request.getModel().getDelegacion_expedidora() != null) {
         * CriteriosSoloDelegacionExpedidora(request, cCriteriosFiltros);
         * }
         * 
         * if (request.getModel().getUnidad_expedidora() != null) {
         * CriteriosSoloUnidadExpedidora(request, cCriteriosFiltros);
         * }
         * 
         * if (request.getModel().getDiagnostico() != null) {
         * CriteriosSoloDiagnostico(request, cCriteriosFiltros);
         * }
         */
        cCriteriosFiltros.add(cNSS);
        cCriteriosFiltros.add(cAgregadoMedico);

        Criteria cCriterios = new Criteria().andOperator(cCriteriosFiltros);
        matchStage = Aggregation.match(cCriterios);

        /* EMPIEZA EL GROUP */

        // SE COMBINAN LAS COLECCIONES
        UnionWithOperation unionWith = UnionWithOperation.unionWith("incapacidades");

        // SE APLICA EL PAGINADO SOLICITADO
        // Aggregation aggregation2 = Aggregation.newAggregation(unionWith, matchStage,
        // sort, skip, limit);
        Aggregation aggregation2 = Aggregation.newAggregation(matchStage, sort, skip, limit);

        List<NotaEgreso> respuesta = mt
                .aggregate(aggregation2, "mc_salud_hos_nt_med_egreso_stg", NotaEgreso.class)
                .getMappedResults();

        return respuesta;
    }

    public static void CriteriosSoloFechas(NotasRequest request, Collection<Criteria> cCriterios)
            throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        Criteria cInicio = Criteria.where("fec_nota").gte(fromDate);
        Criteria cFin = Criteria.where("fec_nota").lte(toDate);

        cCriterios.add(cInicio);
        cCriterios.add(cFin);

        // return cCriterios;

    }

    public List<NotaEgreso> findNotasEgreso(NotasRequest request) throws ParseException {

       //SOLO FECHAS
        if(request.getModel().getStart() != null && request.getModel().getEnd() != null && request.getModel().getDes_ooad() == null  && request.getModel().getDes_ooad() == null 
           && request.getModel().getDes_unidad() == null && request.getModel().getCve_servicio() == null && request.getModel().getCve_especialidad() == null){
            return findNotasEgresoByFechas(request);
        } 
        // FECHAS Y DELEGACION
        else if(request.getModel().getStart() != null && request.getModel().getEnd() != null && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() == null && request.getModel().getCve_servicio() == null && request.getModel().getCve_especialidad() == null){
            return findNotasEgresoByFechasDelegacion(request);
        }

         // FECHAS, DELEGACION Y UNIDAD
        else if(request.getModel().getStart() != null && request.getModel().getEnd() != null && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null && request.getModel().getCve_servicio() == null && request.getModel().getCve_especialidad() == null){
             return findNotasEgresoByFechasDelegacionUnidad(request);
        }
       
        // FECHAS, DELEGACION, UNIDAD Y SERVICIO
        else if(request.getModel().getStart() != null && request.getModel().getEnd() != null && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null && request.getModel().getCve_servicio() != null && request.getModel().getCve_especialidad() == null){
             return findNotasEgresoByFechasDelegacionUnidadServicio(request);
        }

        // FECHAS, DELEGACION, UNIDAD SERVICIO Y ESPECIALIDAD
        else if(request.getModel().getStart() != null && request.getModel().getEnd() != null && request.getModel().getDes_ooad() != null && request.getModel().getDes_unidad() != null && request.getModel().getCve_servicio() != null && request.getModel().getCve_especialidad() != null){
            return findNotasEgresoByFechasDelegacionUnidadServicioEspecialidad(request);
        }
        else {
            return findNotasEgresoNssAgregado(request);
        }
        

    }


     public List<NotaEgreso> findNotasEgresoNssAgregado(NotasRequest request) throws ParseException {

        
        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregado(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(),
                request.getOrder(), descendente, skip, limit);

    }


    
     public Integer findCountNotasEgresoNssAgregado(NotasRequest request) throws ParseException {

    
        Integer contador = notaEgresoRepository.contadorNotasEgresoByNssAgregado(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico());

                if(contador == null){
                    contador = 0;
                }

                return contador;

    }


    

    
    public List<NotaEgreso> findNotasEgresoByFechas(NotasRequest request) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregadoFechas(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), fromDate, toDate,
                request.getOrder(), descendente, skip, limit);

    }

    public List<NotaEgreso> findNotasEgresoByFechasDelegacion(NotasRequest request) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregadoFechasDelegacion(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(),
                request.getOrder(), descendente, skip, limit);

    }

    
    public List<NotaEgreso> findNotasEgresoByFechasDelegacionUnidad(NotasRequest request) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregadoFechasDelegacionUnidad(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(), request.getModel().getDes_unidad(),
                request.getOrder(), descendente, skip, limit);

    }

        public List<NotaEgreso> findNotasEgresoByFechasDelegacionUnidadServicio(NotasRequest request) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregadoFechasDelegacionUnidadServicio(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(), request.getModel().getDes_unidad(), request.getModel().getCve_servicio(),
                request.getOrder(), descendente, skip, limit);

    }

    public List<NotaEgreso> findNotasEgresoByFechasDelegacionUnidadServicioEspecialidad(NotasRequest request) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = dateFormat.parse(request.getModel().getStart());
        Date toDate = dateFormat.parse(request.getModel().getEnd());

        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        c1.add(Calendar.DATE, 1);
        toDate = c1.getTime();

        // 1 => Sort ascending, -1 => Sort descending

        Integer descendente = request.getDesc() ? -1 : 1;
        Integer skip = (request.getPage() - 1) * request.getPageSize();
        Integer limit = request.getPageSize();
        return notaEgresoRepository.findNotasByNssAgregadoFechasDelegacionUnidadServicioEspecialidad(request.getModel().getNum_nss(),
                request.getModel().getAgregado_medico(), fromDate, toDate, request.getModel().getDes_ooad(), request.getModel().getDes_unidad(), request.getModel().getCve_servicio(), request.getModel().getCve_especialidad(),
                request.getOrder(), descendente, skip, limit);

    }


    public Object llenarListaNotas(NotasRequest request) throws ParseException{



        Integer total = findCountNotasEgresoNssAgregado(request);

        Pageable paging = PageRequest.of(request.getPage() - 1, request.getPageSize());

// final List<IncapacidadNSSA> results = getIncapacidades(request, paging);
//final List<IncapacidadNSSA> results = getIncapacidadesGroup(request, paging);
//return new PageImpl<>(results, paging, total);



      List<NotaEgreso> listaPrincipal =  findNotasEgreso(request);


      listaPrincipal.forEach(notaEgreso -> {
        
         //Nota inicial 
        if(notaEgreso.getCve_especialidad().equals("GINECOLOGIA") || notaEgreso.getCve_especialidad().equals("GINECOLOGÍA")){
            //Nota inicial gineco
            List<NotaInicialGinecologia> listaNotaInicialGineco = notaInicialGinecoRepository.findNotasInicialGineco(notaEgreso.getCve_pac_nss(), notaEgreso.getCve_pac_amedico(), notaEgreso.getNum_exp_folio(), notaEgreso.getNum_exp_anio(), notaEgreso.getNum_exp_unidado());
            if(listaNotaInicialGineco.size()>0){
                notaEgreso.addItemListaSubnotas(llenarNotaDTO(listaNotaInicialGineco.get(0)));
            }
        } else {
            List<NotaInicial> listaNotaInicial = notaInicialRepository.findNotasInicial(notaEgreso.getCve_pac_nss(), notaEgreso.getCve_pac_amedico(), notaEgreso.getNum_exp_folio(), notaEgreso.getNum_exp_anio(), notaEgreso.getNum_exp_unidado());
            if(listaNotaInicial.size()>0){
                notaEgreso.addItemListaSubnotas(llenarNotaDTO(listaNotaInicial.get(0)));
            }
        }
        

       
        
        //Nota revision
        List<NotaRevision> listaNotaRevision = notaRevisionRepository.findNotasRevision(notaEgreso.getCve_pac_nss(), notaEgreso.getCve_pac_amedico(), notaEgreso.getNum_exp_folio(), notaEgreso.getNum_exp_anio(), notaEgreso.getNum_exp_unidado());
        if(listaNotaRevision.size()>0){
            notaEgreso.addItemListaSubnotas(llenarNotaDTO(listaNotaRevision.get(0)));
        }

        //Nota evolucion
        List<NotaEvolucion> listaNotaEvolucion = notaEvolucionRepository.findNotasEvolucion(notaEgreso.getCve_pac_nss(), notaEgreso.getCve_pac_amedico(), notaEgreso.getNum_exp_folio(), notaEgreso.getNum_exp_anio(), notaEgreso.getNum_exp_unidado());
        if(listaNotaEvolucion.size()>0){
            notaEgreso.addItemListaSubnotas(llenarNotaDTO(listaNotaEvolucion.get(0)));
        }

         //Nota egreso
        notaEgreso.addItemListaSubnotas(llenarNotaDTO(notaEgreso));

        //Nota defuncion
        List<NotaDefuncion> listaNotaDefuncion = notaDefuncionRepository.findNotasDefuncion(notaEgreso.getCve_pac_nss(), notaEgreso.getCve_pac_amedico(), notaEgreso.getNum_exp_folio(), notaEgreso.getNum_exp_anio(), notaEgreso.getNum_exp_unidado());
        if(listaNotaDefuncion.size()>0){
            notaEgreso.addItemListaSubnotas(llenarNotaDTO(listaNotaDefuncion.get(0)));
        }
        
        
      });

        //return listaPrincipal;

        return new PageImpl<>(listaPrincipal, paging, total);

    }


    public NotaGenericaDTO llenarNotaDTO(NotaGenerica nota){

        NotaGenericaDTO notaRespuesta = new NotaGenericaDTO();

        notaRespuesta.set_id(nota.get_id());
        notaRespuesta.setDes_nombre_completo(nota.getDes_nombre_completo());
        notaRespuesta.setNum_edad(nota.getNum_edad());
        notaRespuesta.setInd_sexo(nota.getInd_sexo());
        notaRespuesta.setCve_pac_nss(nota.getCve_pac_nss());
        notaRespuesta.setCve_pac_amedico(nota.getCve_pac_amedico());
        notaRespuesta.setCve_pac_curp(nota.getCve_pac_curp());
        notaRespuesta.setCve_ooad(nota.getCve_ooad());
        notaRespuesta.setDes_ooad(nota.getDes_ooad());
        notaRespuesta.setDes_unidad(nota.getDes_unidad());
        notaRespuesta.setCve_especialidad(nota.getCve_especialidad());
        notaRespuesta.setCve_servicio(nota.getCve_servicio());
        notaRespuesta.setCve_nivel(nota.getCve_nivel());
        notaRespuesta.setDes_diagnostico(nota.getDes_diagnostico());
        notaRespuesta.setDes_diagnosticohosp(nota.getDes_diagnosticohosp());
        notaRespuesta.setDes_notas(nota.getDes_notas());
        notaRespuesta.setFec_nota(nota.getFec_nota());
        notaRespuesta.setNum_exp_folio(nota.getNum_exp_folio());
        notaRespuesta.setNum_exp_anio(nota.getNum_exp_anio());
        notaRespuesta.setNum_exp_unidado(nota.getNum_exp_unidado());
        notaRespuesta.setCve_exp_cvesol(nota.getCve_exp_cvesol());
        notaRespuesta.setNum_exp_consec(nota.getNum_exp_consec());
        notaRespuesta.setCve_exp_nss(nota.getCve_exp_nss());
        notaRespuesta.setCve_exp_medicof(nota.getCve_exp_medicof());

    
        return notaRespuesta;

    }


    public NotaEgresoDetail finNotaEgresoById(String id){
        return notaEgresoRepository.findNotaEgresoById(id);
    }

    public List<SolicitudServicioDetail> findSolicitudesServicioNota(SolictudReferenciasRequest request){
        
        return notaEgresoRepository.findNotasSolicitudServicio(request.getNum_nss(),request.getAgregado_medico(), request.getNum_exp_folio(), request.getNum_exp_anio(), request.getNum_exp_unidado());
    }

    public List<ReferenciaDetail> findReferenciasNota(SolictudReferenciasRequest request){
        return notaEgresoRepository.findNotasReferencia(request.getNum_nss(),request.getAgregado_medico(), request.getNum_exp_folio(), request.getNum_exp_anio(), request.getNum_exp_unidado());
    }


    public Object findReferencias(){
        return notaEgresoRepository.findNotasReferencia("3386680002", "1M1968OR", "118", "23", "08HA66");
    }

     public Object findSolicitud(){
        return notaEgresoRepository.findNotasSolicitudServicio("3386680002", "1M1968OR", "118", "23", "08HA66");
    }

     public Object findEvolucion(){
        return notaEvolucionRepository.findNotasEvolucion("3386680002", "1M1968OR", "118", "23", "08HA66");
    }

    public NotaEvolucionDetail findEvolucionById(String id){
        return notaEvolucionRepository.findNotaEvolucionById(id);
    }

    public Object findInicial(){
        return notaInicialRepository.findNotasInicial("3386680002", "1M1968OR", "118", "23", "08HA66");
    }

    public NotaInicialDetail findInicialById(String id){
        return notaInicialRepository.findNotaInicialById(id);
    }

    public Object findInicialGineco(){
        return notaInicialGinecoRepository.findNotasInicialGineco("0817891719", "2F1995OR", "7", "23", "22HF10");
    }

    public NotaInicialGinecologiaDetail findInicialGinecoById(String id){
        return notaInicialGinecoRepository.findNotaInicialGinecologiaById(id);
    }

    public Object findRevision(){
        return notaRevisionRepository.findNotasRevision("3386680002", "1M1968OR", "118", "23", "08HA66");
    }

    public NotaRevisionDetail findRevisionById(String id){
        return notaRevisionRepository.findNotaRevisionById(id);
    }

    public Object findDefuncion(){
        return notaDefuncionRepository.findNotasDefuncion("1914915181", "1F1991OR", "7", "23", "34HD02");
    }

      public NotaDefuncionDetail findDefuncionById(String id){
        return notaDefuncionRepository.findNotaDefuncionById(id);
    }

}
