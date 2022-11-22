package util;

/**
 * Enumerados utilizados en los mensajes de los cuadros de di�logo modal
 * para poder realizar test GUI.
 */
public enum Mensajes 
{

	PRECIO_INVALIDO("Ninguno de los precios puede ser negativo."),
	PRECIO_VENTA_MENOR_COSTO("El precio de venta debe ser mayor o igual al costo."),
	ACTUALIZA_DATOS("Datos actualizados.");
	
	private String valor;

    private Mensajes(String valor)
    {
	this.valor = valor;
    }

    public String getValor()
    {
	return valor;
    }

    public void setValor(String valor)
    {
	this.valor = valor;
    }
    

}
