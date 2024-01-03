package com.example.mshcincapacidades.Model.Notas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(value="mc_salud_hos_nt_med_egreso_stg")
@Data
public class ReferenciaDetail{
    @Id
    public String _id;
    public String des_nombre_completo;
    public String num_edad;
    public String ind_sexo;
    public String cve_pac_nss;
    public String cve_pac_amedico;
    public String cve_pac_curp;
    public String cve_ooad;
    public String des_ooad;
    public String des_unidad;
    public String cve_especialidad;
    public String cve_servicio;
    public String cve_nivel;
    public String des_diagnostico;
    public String des_diagnosticohosp;
    public String des_notas;
    public String fec_nota;
    public String num_exp_folio;
    public String num_exp_anio;
    public String num_exp_unidado;
    public String cve_exp_cvesol;
    public String num_exp_consec;
    public String cve_exp_nss;
    public String cve_exp_medicof;
    public String cve_hos_nss;
    public String cve_hos_amedico;
    public String cve_hos_calidad;
    public String num_estatura;
    public String num_peso;
    public String num_temperatura;
    public String num_tension_arterial;
    public String num_frecuencia_cardiaca;
    public String num_frecuencia_respiratoria;
    public String num_glucosa;
    public String num_saturacion_oxigeno;
    public String des_resumen_interrog_planestd;
    public String des_pronostico;
    public String des_complicacion;
    public String num_cama;
    public String fec_ingreso;
    public String fec_egreso;
    public String fec_ingreso_cama;
    public String des_diagnostico_egreso;
    public String num_envio;
    public String des_envioa;
    public String des_motivo_egreso;
    public String des_tipo;
    public String num_adi_folio;
    public String num_adi_anio;
    public String num_adi_unidado;
    public String cve_adi_cvesol;
    public String cve_solicitud;
    public String des_diagnostico_adx;
    public String des_diagnostico2;
    public String des_procedimientos;
    public String des_complicaciones;
    public String cve_med_matricula;
    public String des_nombre_medico;
    public String cve_med_cedula;
    public String cve_estatus_expediente;
    public String fecha_proceso;
    public String num_anio_partition;
}
