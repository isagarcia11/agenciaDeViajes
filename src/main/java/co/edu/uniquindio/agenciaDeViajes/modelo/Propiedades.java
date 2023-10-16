package co.edu.uniquindio.agenciaDeViajes.modelo;

import lombok.Getter;

import java.util.ResourceBundle;

public class Propiedades {
    @Getter
    private final ResourceBundle resourceBundle;

    private static Propiedades instancia;

    private Propiedades(){

        this.resourceBundle = ResourceBundle.getBundle("textos");
    }

    public static Propiedades getInstance(){
        if(instancia == null){
            instancia = new Propiedades();
        }
        return instancia;
    }
}
