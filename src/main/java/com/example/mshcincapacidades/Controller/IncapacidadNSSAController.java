package com.example.mshcincapacidades.Controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mshcincapacidades.Model.DelegacionRequest;
import com.example.mshcincapacidades.Model.DiagnosticoRequest;
import com.example.mshcincapacidades.Model.IncapacidadesRequest;
import com.example.mshcincapacidades.Model.ModelDelegacionRequest;
import com.example.mshcincapacidades.Model.ModelDiagnosticoRequest;
import com.example.mshcincapacidades.Model.ModelUnidadesRequest;
import com.example.mshcincapacidades.Model.UnidadRequest;
import com.example.mshcincapacidades.Model.DTO.IncapacidadDTO;
import com.example.mshcincapacidades.Service.IncapacidadNSSAService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mshc-incapacidades/v1")
@RequiredArgsConstructor
public class IncapacidadNSSAController {

    //@Autowired
    private final IncapacidadNSSAService incapacidadNSSAService;
    


    @GetMapping("/incapacidades/{folio}/{nss}/{agregado_medico}")
    public IncapacidadDTO findIncapacidadByFolioNSSAgregado(@PathVariable String folio, @PathVariable String nss, @PathVariable String agregado_medico) {
        return incapacidadNSSAService.findIncapacidadByFolioNSSAgregado(folio, nss, agregado_medico);
    }


    @PostMapping("/incapacidades/diagnosticos")
    public ResponseEntity<Object> findDiagnosticosByNssAgregado(@RequestBody DiagnosticoRequest request) throws ParseException {

        return new ResponseEntity<>(incapacidadNSSAService.findDiagnosticos(request), HttpStatus.OK);
    }


    @PostMapping("/incapacidades/unidades")
    public ResponseEntity<Object> findUnidades(@RequestBody UnidadRequest request) throws ParseException {

        return new ResponseEntity<>(incapacidadNSSAService.findUnidades(request), HttpStatus.OK);
    }

    @PostMapping("/incapacidades/delegaciones")
    public ResponseEntity<Object> findDelegaciones(@RequestBody DelegacionRequest request) throws ParseException {

        return new ResponseEntity<>(incapacidadNSSAService.findDelegaciones(request), HttpStatus.OK);
    }

        @PostMapping("/incapacidades")
    public ResponseEntity<Object> findAllIncapacidadesByNSS_AgregadoMedico(@RequestBody IncapacidadesRequest request) throws ParseException {

        return new ResponseEntity<>(incapacidadNSSAService.findNSS_AgregadoMedico(request), HttpStatus.OK);
    }


      @PostMapping("/incapacidades/test")
    public ResponseEntity<Object> findAllIncapacidadesByNSS_AgregadoMedico_Diagnostico(@RequestBody IncapacidadesRequest request) throws ParseException {

        return new ResponseEntity<>(incapacidadNSSAService.findIncapacidadesByNssAgregadoDiagnostico(request), HttpStatus.OK);
    }



}
