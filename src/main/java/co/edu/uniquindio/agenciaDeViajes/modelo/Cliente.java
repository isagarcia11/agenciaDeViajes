package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {

    private String identificacion;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;

    public String toFileFormat() {
        return identificacion + ";" + nombre + ";" + correo + ";" + telefono + ";" + direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
