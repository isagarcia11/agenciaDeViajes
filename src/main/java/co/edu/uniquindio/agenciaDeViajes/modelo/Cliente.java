package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private String identificacion;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;

    public String toFileFormat() {
        return identificacion + ";" + nombre + ";" + correo + ";" + telefono + ";" + direccion;
    }
}
