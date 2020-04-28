package utn.frsf.isi.died2020.tp07;

import utn.frsf.isi.died2020.tp07.modelo.Curso;
import utn.frsf.isi.died2020.tp07.modelo.Material;
import utn.frsf.isi.died2020.tp07.modelo.Tema;
import utn.frsf.isi.died2020.tp07.modelo.Video;
import utn.frsf.isi.died2020.tp07.modelo.Libro;
import utn.frsf.isi.died2020.tp07.servicios.Catalogo;

public class App {
	
	public static void main(String[] args) {
		System.out.println("Catalogo Learn With US");
		
		Catalogo cat = new Catalogo();
		cat.agregarLibro("BBc Libro 1", "Martin", 50, "01-02-2018", new Tema[] {Tema.IA,Tema.PYTHON}, "12345647", 340);
		cat.agregarLibro("Bca Libro 2", "Pilar", 60, "01-02-2020", new Tema[] {Tema.IOT,Tema.GESTION}, "32433242", 120);
		cat.agregarLibro("ABc Libro 3", "Francisca", 75, "11-12-2014", new Tema[] {Tema.JAVA,Tema.PYTHON}, "3463345345", 110);
		cat.agregarLibro("Axc Libro 4", "Martin", 88, "20-03-2019", new Tema[] {Tema.ALGORITMOS,Tema.PYTHON,Tema.JAVASCRIPT}, "736564343", 540);
		cat.agregarLibro("Ayt Libro 5", "Francisca", 43, "01-02-2018", new Tema[] {Tema.JAVA,Tema.MOBILE}, "7346466", 362);

		cat.agregarVideo("Axc Video 1", "Martin", 50, "24-08-2016", new Tema[] {Tema.REACT,Tema.JAVASCRIPT,Tema.WEB},  8);
		cat.agregarVideo("BbC Video 2", "Pilar", 60, "08-06-2019", new Tema[] {Tema.JAVA,Tema.GESTION},10);
		cat.agregarVideo("NBy Video 3", "Pilar", 75, "17-10-2019", new Tema[] {Tema.JAVA,Tema.PYTHON,Tema.IA}, 14);
		cat.agregarVideo("NHy Video 4", "Martin", 88, "09-04-2020", new Tema[] {Tema.REACT,Tema.ALGORITMOS,Tema.MOBILE}, 25);
		cat.agregarVideo("Bac Video 5", "Francisca", 43, "11-02-2020", new Tema[] {Tema.JAVASCRIPT,Tema.MOBILE,Tema.WEB},12);

		cat.agregarCurso("Hyn Curso 1", "Francisca", 50, "29-03-2017", new Tema[] {Tema.IA,Tema.PYTHON,Tema.REACT}, 20.0, false,Curso.Nivel.BASE,6 );
		cat.agregarCurso("Abc Curso 2", "Pilar", 60, "30-07-2019", new Tema[] {Tema.MOBILE,Tema.GESTION,Tema.IA}, 22.0, true,Curso.Nivel.EXPERTO,12);
		cat.agregarCurso("Bhy Curso 3", "Francisca", 75, "26-06-2018", new Tema[] {Tema.JAVASCRIPT,Tema.PYTHON}, 18.0, false,Curso.Nivel.AVANZADO,4);
		cat.agregarCurso("BBc Curso 4", "Martin", 88, "20-03-2020", new Tema[] {Tema.AGIL,Tema.PYTHON,Tema.JAVASCRIPT}, 6.0, true,Curso.Nivel.BASE,9);
		cat.agregarCurso("Axy Curso 5", "Pilar", 43, "02-06-2010", new Tema[] {Tema.GESTION,Tema.ING_SOFT,Tema.AGIL }, 15.0, true,Curso.Nivel.AVANZADO,5);
		
		cat.imprimirCatalogo();
		cat.imprimirAutores();
		System.out.println("=============================================");
		System.out.println("Busco Libros que alguno de sus temas sea PYTHON");

		cat.buscarPorTemas(Tema.PYTHON).stream().forEach(System.out::println);
		
		System.out.println("=============================================");
		System.out.println("Busco por rango de calificacion ordenada decrecientemente");
		System.out.println("");
		cat.busquedaRangoCalificacionOrdCalif(75,88).stream().forEach(System.out::println);
		
		System.out.println("=============================================");
		System.out.println("Busco por titulo");
		System.out.println("");		
		cat.busquedaPorTitulo("Libro", 5).stream().forEach(System.out::println);
		
		System.out.println("=============================================");
		System.out.println("Busco por rango de fecha");
		System.out.println("");		
		cat.busquedaPorRangoDeFecha("01-01-2018","01-01-2020",3).stream().forEach(System.out::println);
		
		System.out.println("=============================================");
		System.out.println("Busco por tipo de material y autor");
		System.out.println("");		
		cat.busquedaPorTipoyAutor(Libro.class,"Pilar",3).stream().forEach(System.out::println);
		
		System.out.println("=============================================");
		System.out.println("Busco por rango de calificación");
		System.out.println("");		
		cat.busquedaPorRangoDeCalificacion(45,90,5).stream().forEach(System.out::println);
		
}

}
