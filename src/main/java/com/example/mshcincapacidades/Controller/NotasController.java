package com.example.mshcincapacidades.Controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mshcincapacidades.Model.DelegacionRequest;
import com.example.mshcincapacidades.Model.UnidadRequest;
import com.example.mshcincapacidades.Model.Notas.Request.DelegacionRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.ModelDelegacionRequestNotas;
import com.example.mshcincapacidades.Model.Notas.Request.NotasRequest;
import com.example.mshcincapacidades.Model.Notas.Request.UnidadRequestNotas;
import com.example.mshcincapacidades.Service.NotaEgresoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mshc-notas-historial/v1")
@RequiredArgsConstructor
public class NotasController {

    private final NotaEgresoService notaEgresoService;

    @PostMapping("/egreso")
    public ResponseEntity<Object> findAllIncapacidadesByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.llenarListaNotas(request), HttpStatus.OK);
    }

    @PostMapping("/egreso/{id}")
    public ResponseEntity<Object> findNotaEgresoById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.finNotaEgresoById(id), HttpStatus.OK);
    }

    @PostMapping("/referencia")
    public ResponseEntity<Object> findAllReferenciasByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findReferencias(), HttpStatus.OK);
    }

    @PostMapping("/solicitud")
    public ResponseEntity<Object> findAllSolicitudByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findSolicitud(), HttpStatus.OK);
    }

    @PostMapping("/evolucion")
    public ResponseEntity<Object> findAllEvolucionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findEvolucion(), HttpStatus.OK);
    }

    @PostMapping("/evolucion/{id}")
    public ResponseEntity<Object> findEvolucionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findEvolucionById(id), HttpStatus.OK);
    }

    @PostMapping("/revision")
    public ResponseEntity<Object> findAllRevisionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findRevision(), HttpStatus.OK);
    }

    @PostMapping("/revision/{id}")
    public ResponseEntity<Object> findRevisionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findRevisionById(id), HttpStatus.OK);
    }

    @PostMapping("/defuncion")
    public ResponseEntity<Object> findAllDefuncionByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findDefuncion(), HttpStatus.OK);
    }

    @PostMapping("/defuncion/{id}")
    public ResponseEntity<Object> findDefuncionById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findDefuncionById(id), HttpStatus.OK);
    }

    @PostMapping("/inicial")
    public ResponseEntity<Object> findAllInicialByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicial(), HttpStatus.OK);
    }

    @PostMapping("/inicial/{id}")
    public ResponseEntity<Object> findInicialById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialById(id), HttpStatus.OK);
    }

    @PostMapping("/inicial-gineco")
    public ResponseEntity<Object> findAllInicialGinecoByNSS_AgregadoMedico(@RequestBody NotasRequest request)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialGineco(), HttpStatus.OK);
    }

    @PostMapping("/inicial-gineco/{id}")
    public ResponseEntity<Object> findInicialGinecoById(@PathVariable String id)
            throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findInicialGinecoById(id), HttpStatus.OK);
    }


    @PostMapping("/unidades")
    public ResponseEntity<Object> findUnidades(@RequestBody UnidadRequestNotas request) throws ParseException {

        return new ResponseEntity<>(notaEgresoService.findUnidades(request), HttpStatus.OK);
    }
}
