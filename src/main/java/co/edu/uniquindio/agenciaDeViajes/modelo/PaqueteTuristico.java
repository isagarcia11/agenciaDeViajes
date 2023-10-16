package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteTuristico {

    private String nombre;
    private String duracion;
    private String serviciosAdicionales;
    private float precio;
    private int cupoMaximo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;


}
