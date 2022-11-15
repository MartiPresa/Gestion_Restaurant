package negocio;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

import excepciones.CantComensalesInvalida_Exception;
import excepciones.CantHijosInvalida_Exception;
import excepciones.ContrasenaIncorrecta_Exception;
import excepciones.EdadInvalida_Exception;
import excepciones.MesaOcupada_Exception;
import excepciones.NoExisteID_Exception;
import excepciones.NoExisteMesa_Exception;
import excepciones.NoExisteMozo_Exception;
import excepciones.NoExisteOperario_Exception;
import excepciones.NyARepetido_Exception;
import excepciones.UserNameRepetido_Exception;
import excepciones.precioInvalido_Exception;
import excepciones.prodEnUso_Exception;
import modelo.Enumerados;
import modelo.Mesa;
import modelo.Mozo;
import modelo.Operario;
import modelo.Producto;
import modelo.Sueldo;

public class FuncionalidadAdmin extends FuncionalidadOperario {

	public FuncionalidadAdmin(Operario operario) {
		super(operario);
	}
	public FuncionalidadAdmin() {
		
	}

	public void agregaMozo(String NyA, GregorianCalendar fechaNacimiento, int cantHijos, Enumerados.estadoMozo estado) throws EdadInvalida_Exception, CantHijosInvalida_Exception, NyARepetido_Exception {
		LocalDate today = LocalDate.now();
        LocalDate fechaNac = LocalDate.of(fechaNacimiento.get(Calendar.YEAR), fechaNacimiento.get(Calendar.MONTH), fechaNacimiento.get(Calendar.DAY_OF_MONTH));
        long edad = ChronoUnit.YEARS.between(fechaNac, today);
       if (Sistema.getInstance().getMozos().get(NyA)!= null)
    	   throw new NyARepetido_Exception("Ya existe un mozo registrado con este nombre");
		if (edad < 18)
			throw new EdadInvalida_Exception("Debe ser mayor de 18 anios.");
		if (cantHijos<0)
			throw new CantHijosInvalida_Exception("Ingrese una cantidad de hijos valida.");
		Sistema.getInstance().getMozos().put(NyA, new Mozo(NyA,cantHijos));
	}

	public void eliminaMozo(String NyA)throws NoExisteMozo_Exception{	
		if (Sistema.getInstance().getMozos().get(NyA)!= null)
			Sistema.getInstance().getMozos().remove(NyA);
		else
			throw new NoExisteMozo_Exception("El mozo que desea eliminar no existe");
	}
	
	public void modificaEstadoOperario (String userName, boolean activo) throws NoExisteOperario_Exception{
		Operario opActual = Sistema.getInstance().getOperariosRegistrados().get(userName);
		if (opActual!=null)
			opActual.setActivo(activo);
		else
			throw new NoExisteOperario_Exception("No existe el opeario que desea modificar");
	}
	
	
	/**
	 * Metodo que registra un nuevo operario en el sistema. <br>
	 * Post: Agrega un nuevo operario al HashMap
	 * 
	 * @param NyA      atributo correspondiente al nombre y apellido del operario
	 *                 que desea registrarse. <br>
	 * @param userName atributo correspondiente al nombre de usuario que usara el
	 *                 operario para el login. <br>
	 * @param password atributo que representa la contrasena y que corresponde al
	 *                 userName. <br>
	 * @throws UserNameRepetido_Exception si el nombre de usuario ingresado esta
	 *                                    asociado a otra cuenta.
	 * @throws ContrasenaIncorrecta_Exception 
	 */
	
	public void registraOperario (String NyA, String userName, String password, Enumerados.estadoOperario estado) throws UserNameRepetido_Exception, ContrasenaIncorrecta_Exception{
		boolean activo=true;
		if (estado == Enumerados.estadoOperario.INACTIVO)
			activo = false;
		if (this.verificaPassword(password)== false)
			throw new ContrasenaIncorrecta_Exception("El campo contrase�a debe contener entre 6 y 12 caracteres. Con al menos 1 d�gito y 1 may�scula");
		else if(Sistema.getInstance().getOperariosRegistrados().putIfAbsent(userName, new Operario(NyA,userName,password,activo)) != null)	// si ya estaba registrado tiramos excepcion????
			throw new UserNameRepetido_Exception("El userName '"+userName+"' ya esta asociado a un operario.");
	}


	public void eliminaOperario(String userName)throws NoExisteOperario_Exception{
		if (GestionOperario.existeOperario(userName) == false)
			throw new NoExisteOperario_Exception("No existe el operario que desea eliminar");
		else
			GestionOperario.eliminaOperario(userName);
	}

	public void agregaProducto(String nombre, double precioCosto, double precioVenta, int stockInicial) throws precioInvalido_Exception{
		
		if(precioVenta < precioCosto) 
			throw new precioInvalido_Exception("El precio de venta debe ser mayor o igual al costo.");
		else
			if(precioVenta < 0 || precioCosto < 0) 
				throw new precioInvalido_Exception("Ninguno de los precios puede ser negativo.");
			
		Producto producto = new Producto(nombre,precioCosto,precioVenta,stockInicial);
		Sistema.getInstance().getProductos().put(producto.getIdProd(), producto);
	}

	public void eliminaProducto(int idProd) throws prodEnUso_Exception, NoExisteID_Exception {
		if (GestionProductos.existeProducto(idProd) == false) //fijarse de ponerlo en gestion prod venta
			throw new NoExisteID_Exception("No existe el producto que desea eliminar");
		if(GestionComandas.contieneProd(idProd) == true)
			throw new prodEnUso_Exception("El producto esta en una comanda activa, no puede ser eliminado");
		GestionProductos.eliminaProducto(idProd);
	}
	
	public void agregaMesa(int cantSillas) throws CantComensalesInvalida_Exception {
		if (Mesa.getSiguienteNroMesa()>0 && cantSillas < 2) {
			throw new CantComensalesInvalida_Exception("Solo se permiten menos de dos comensales en la barra");
		}
		Mesa mesa = new Mesa(cantSillas);
		Sistema.getInstance().getMesas().put(mesa.getNroMesa(), mesa);
	}
	
	public void eliminaMesa(int nroMesa) throws NoExisteMesa_Exception, MesaOcupada_Exception {
		if (GestionMesas.existeMesa(nroMesa) == false) //fijarse de ponerlo en gestion mesa
			throw new NoExisteMesa_Exception("No existe la mesa que desea eliminar");
		if (Sistema.getInstance().getMesas().get(nroMesa).getEstado() == Enumerados.estadoMesa.OCUPADA)
			throw new MesaOcupada_Exception("Espere a que se libere la mesa para elimianrla.");
		GestionMesas.eliminaMesa(nroMesa);
	}
	
	public void modificaRemuneracionBasica(double remBasica) {
		Sueldo.setRemBasic(remBasica);
	}
	
}
