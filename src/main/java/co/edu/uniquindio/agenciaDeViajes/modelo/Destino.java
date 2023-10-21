package co.edu.uniquindio.agenciaDeViajes.modelo;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino {

    private String nombre;
    private String ciudad;
    private String descripcion;
    private Clima clima;
    private String imagen;

    public String toFileFormat() {
        return nombre + ";" + ciudad + ";" + descripcion + ";" + imagen + ";" + clima;
    }
}
