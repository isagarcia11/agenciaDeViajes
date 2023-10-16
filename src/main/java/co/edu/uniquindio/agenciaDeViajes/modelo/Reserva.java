package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    private String Cliente;
    private int cantidadDePersonas;
    private String paqueteTuristico;
    private String guiaTuristico;
    private Enum estado;
    private LocalDate fechaDeSolicitud;
    private LocalDate fechaDeViaje;


}
