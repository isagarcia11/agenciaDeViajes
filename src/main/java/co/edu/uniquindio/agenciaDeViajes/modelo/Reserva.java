package co.edu.uniquindio.agenciaDeViajes.modelo;

import co.edu.uniquindio.agenciaDeViajes.enums.Estado;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva implements Serializable {

    private Cliente Cliente;
    private int cantidadDePersonas;
    private PaqueteTuristico paqueteTuristico;
    private GuiaTuristico guiaTuristico;
    private Estado estado;
    private LocalDate fechaDeSolicitud;
    private LocalDate fechaDeViaje;

    @Override
    public String toString() {
        return "Reserva{" +
                "Cliente=" + Cliente +
                ", cantidadDePersonas=" + cantidadDePersonas +
                ", paqueteTuristico=" + paqueteTuristico +
                ", guiaTuristico=" + guiaTuristico +
                ", estado=" + estado +
                ", fechaDeSolicitud=" + fechaDeSolicitud +
                ", fechaDeViaje=" + fechaDeViaje +
                '}';
    }
}
