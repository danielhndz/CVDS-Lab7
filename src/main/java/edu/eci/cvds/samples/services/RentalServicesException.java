package edu.eci.cvds.samples.services;

public class RentalServicesException extends Exception {

    public static final String CLIENTE_NO_REGISTRADO = "No se encuentra registrado un cliente con el id ingresado.";
    public static final String ITEM_NO_EXISTE_O_NO_ALQUILADO = "El ítem no existe o no se permite esta acción sobre él.";
    public static final String ERROR_CLIENTES = "Error al cargar todos los clientes.";
    public static final String ITEM_NO_REGISTRADO = "No se encuentra registrado un ítem con el id ingresado.";
    public static final String ERROR_ITEMS_DISPONIBLES = "Error al cargar los ítems disponibles.";
    public static final String TIPO_ITEM_NO_REGISTRADO = "No se encuentra registrado un tipo de ítem con el id ingresado.";
    public static final String ERROR_TODOS_TIPO_ITEM = "Error al cargar los tipos de ítem existentes.";
    public static final String ITEM_NO_EXISTE_O_YA_ALQUILADO = "El identificador no corresponde con un ítem o el mismo ya está alquilado.";
    public static final String ERROR_AL_REGISTRAR_CLIENTE = "Error al registrar el cliente.";
    public static final String ID_ITEM_NO_EXISTE = "El identificador del ítem no existe";
    public static final String ERROR_AL_REGISTRAR_ITEM = "Error al intentar registrar el ítem.";

    public RentalServicesException(String message, Exception e) {
        super(message, e);
    }

    public RentalServicesException(String message) {
        super(message);
    }
}
