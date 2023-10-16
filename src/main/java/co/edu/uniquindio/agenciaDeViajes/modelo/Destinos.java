package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destinos {

    private String nombre;
    private String ciudad;
    private String descripcion;
    private Clima clima;
    private String imagenes;
}
