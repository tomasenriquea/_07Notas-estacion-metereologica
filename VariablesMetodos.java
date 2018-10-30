package muestras._07Notas;

import java.util.Scanner;

public class VariablesMetodos {
	
	// DATOS ESTATIDOS Y CONSTANTES
	
	
	
	
	
	
	//-------------------------------------------------------------------------------------------------
	//METODOS
	
	
	// Esto es para mostrar el menú de opciones.
	public static int menu() {
		System.out.println("\n\n\n");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|||||||||||||||||||BOMBILLAS||||||||||||||||||");
		System.out.println();
		System.out.println("1. Inicializar las Bombillas.\n" + 
						   "2. Verificar estado de bombillas.\n" + 
						   "3. Subir o bajar intensidad de una bombilla\n" + 
						   "0. Salir.");

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int dato;
		do {
			System.out.print("\nElija un opción: "); // Controla que solo se ingrese una de las 4 opciones.
			dato = sc.nextInt();
		} while (dato > 3);

		return dato;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
