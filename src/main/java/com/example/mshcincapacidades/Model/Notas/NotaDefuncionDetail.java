package com.example.mshcincapacidades.Model.Notas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(value="mc_salud_hos_nt_med_defuncion_stg")
@Data
public class NotaDefuncionDetail{
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
    public String num_frecuencia_repiratoria;
    public String num_glucosa;
    public String num_saturacion_oxigeno;
    public String des_diagnostico_egreso;
    public String fec_ingreso;
    public String fec_egreso;
    public String fec_fallecimiento;
    public String fec_ingreso_cama;
    public String des_lugar_defuncion;
    public String des_leg_tiempo_a;
    public String des_leg_tiempo_b;
    public String des_leg_tiempo_c;
    public String des_leg_tiempo_d;
    public String des_leg_tiempo_e;
    public String des_leg_tiempo_f;
    public String des_unidad_medica_defuncion;
    public String des_sitio;
    public String des_presunto;
    public String des_resumen_defuncion;
    public String des_causa_region_a;
    public String des_causa_region_b;
    public String des_causa_region_c;
    public String des_causa_region_d;
    public String des_causa_region_e;
    public String des_causa_region_f;
    public String cve_med_matricula;
    public String des_nombre_medico;
    public String cve_med_cedula;
    public String cve_estatus_expediente;
    public String fecha_proceso;
    public String num_anio_partition;
}
