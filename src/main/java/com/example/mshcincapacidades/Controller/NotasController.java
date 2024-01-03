package com.example.mshcincapacidades.Controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mshcincapacidades.Model.Notas.Request.DelegacionRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.EspecialidadRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.NotasRequest;
import com.example.mshcincapacidades.Model.Notas.Request.ServicioRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.SolictudReferenciasRequest;
import com.example.mshcincapacidades.Model.Notas.Request.UnidadRequestNotas;
import com.example.mshcincapacidades.Service.NotaEgresoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mshc-nota-historial/v1")
@RequiredArgsConstructor
public class NotasController {

    private final NotaEgresoService notaEgresoService;

    @PostMapping("/egreso")
    public ResponseEntity<Object> findAllIncapacidadesByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.llenarListaNotas(request), HttpStatus.OK);
    }

    @GetMapping("/egreso/{id}")
    public ResponseEntity<Object> findNotaEgresoById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.finNotaEgresoById(id), HttpStatus.OK);
    }

    @PostMapping("/referencias-contrareferencias")
    public ResponseEntity<Object> findAllReferenciasByNSS_AgregadoMedico(@RequestBody SolictudReferenciasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findReferenciasNota(request), HttpStatus.OK);
    }

    @PostMapping("/solicitudes-servicio")
    public ResponseEntity<Object> findAllSolicitudByNSS_AgregadoMedico(@RequestBody SolictudReferenciasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findSolicitudesServicioNota(request), HttpStatus.OK);
    }

/*     @PostMapping("/evolucion")
    public ResponseEntity<Object> findAllEvolucionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findEvolucion(), HttpStatus.OK);
    } */

    @GetMapping("/evolucion/{id}")
    public ResponseEntity<Object> findEvolucionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findEvolucionById(id), HttpStatus.OK);
    }

/*     @PostMapping("/revision")
    public ResponseEntity<Object> findAllRevisionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findRevision(), HttpStatus.OK);
    } */

    @GetMapping("/revision/{id}")
    public ResponseEntity<Object> findRevisionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findRevisionById(id), HttpStatus.OK);
    }

/*     @PostMapping("/defuncion")
    public ResponseEntity<Object> findAllDefuncionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findDefuncion(), HttpStatus.OK);
    }
 */
    @GetMapping("/defuncion/{id}")
    public ResponseEntity<Object> findDefuncionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findDefuncionById(id), HttpStatus.OK);
    }

/*     @PostMapping("/inicial")
    public ResponseEntity<Object> findAllInicialByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicial(), HttpStatus.OK);
    } */

    @GetMapping("/inicial/{id}")
    public ResponseEntity<Object> findInicialById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialById(id), HttpStatus.OK);
    }

/*     @PostMapping("/inicial-gineco")
    public ResponseEntity<Object> findAllInicialGinecoByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialGineco(), HttpStatus.OK);
    } */

    @GetMapping("/inicial-gineco/{id}")
    public ResponseEntity<Object> findInicialGinecoById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialGinecoById(id), HttpStatus.OK);
    }


    /* CATALOGOS */

    @PostMapping("/unidades")
    public ResponseEntity<Object> findUnidades(@RequestBody UnidadRequestNotas request) throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findUnidades(request), HttpStatus.OK);
    }


        @PostMapping("/delegaciones")
    public ResponseEntity<Object> findDelegaciones(@RequestBody DelegacionRequestNotas request) throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findDelegaciones(request), HttpStatus.OK);
    }

    
    @PostMapping("/servicio")
    public ResponseEntity<Object> findServicio(@RequestBody ServicioRequestNotas request) throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findServicio(request), HttpStatus.OK);
    }

     @PostMapping("/especialidad")
    public ResponseEntity<Object> findEspecialidad(@RequestBody EspecialidadRequestNotas request) throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findEspecialidad(request), HttpStatus.OK);
    }
}
