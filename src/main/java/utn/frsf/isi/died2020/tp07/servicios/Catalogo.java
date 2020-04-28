package utn.frsf.isi.died2020.tp07.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utn.frsf.isi.died2020.tp07.modelo.Autor;
import utn.frsf.isi.died2020.tp07.modelo.Curso;
import utn.frsf.isi.died2020.tp07.modelo.Libro;
import utn.frsf.isi.died2020.tp07.modelo.Material;
import utn.frsf.isi.died2020.tp07.modelo.Tema;
import utn.frsf.isi.died2020.tp07.modelo.Video;

public class Catalogo {
	
	public enum CriterioOrdenamiento { TITULO,FECHA_PUBLICACION,CALIFICACION,COSTO,CALIFICACION_COSTO,AUTOR};
	
	private List<Material> catalogo;
	private Set<Autor> autores;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public Catalogo() {
		this.catalogo = new ArrayList<Material>();
		this.autores = new LinkedHashSet<Autor>();
	}

	public Stream<Material> getCatalogo() {
		return catalogo.stream();
	}
	
	public Stream<Autor> getAutores() {
		return autores.stream();
	}
	
	private Optional<Autor> buscarAutor(Predicate<Autor> p) {
		return this.autores.stream().filter( p).findFirst();
		// si encuentra uno con el predicado V --> retorna un Optional con ese autor
		// si no encuentra retorna un optional vacio
	}
	
	private Optional<Material> buscarMaterial(Predicate<Material> p) {
		return this.catalogo.stream().filter(p).findFirst();
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p) {
		return this.catalogo
				.stream()
				.filter(p)
				.sorted()
				.collect(Collectors.toList());
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p,Integer n) {
		return this.catalogo
					.stream()
					.filter(p)
					.sorted()
					.limit(n)
					.collect(Collectors.toList());
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p,Comparator<Material> orden) {
		return this.catalogo
					.stream()
					.filter(p)
					.sorted(orden)
					.collect(Collectors.toList());
	}
	
	private List<Material> buscarListaMaterial(Predicate<Material> p,Integer n,Comparator<Material> orden) {
		return this.catalogo
					.stream()
					.filter(p)
					.limit(n)
					.sorted(orden)
					.collect(Collectors.toList());
	}
	

	private Autor obtenerAutor(String nombre) {
		Optional<Autor> autorOpt = this.buscarAutor(a -> a.getNombre().equalsIgnoreCase(nombre));
		Autor autor = null;
		if(autorOpt.isEmpty()) {
			// si no existia lo crea y lo agrega a la lista
			autor = new Autor(nombre);
			this.autores.add(autor);
		} else {
			// si existia lo retorna
			autor = autorOpt.get();
		}
        return autor;
	}
	
	public void agregarLibro(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,String isbn, Integer paginas) {
		List<Tema> temasLibro = Arrays.asList(temas);
        Libro l = new Libro( titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, isbn, paginas);
        this.catalogo.add(l);
	}

	public void agregarVideo(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,Integer duracion) {
		List<Tema> temasLibro = Arrays.asList(temas);
		Video v = new Video( titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, duracion);
        this.catalogo.add(v);
	}

	public void agregarCurso(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,Double precio,Boolean certificado,Curso.Nivel nivel,Integer clases) {
		List<Tema> temasLibro = Arrays.asList(temas);
		Curso c = new Curso(titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, precio, certificado, nivel, clases);
        this.catalogo.add(c);
	}
	
	public List<Material> buscarPorTemas(Tema tema) {
		return this.buscarListaMaterial(p-> p.getTemas().indexOf(tema)>=0);
	}
	
	public void imprimirCatalogo() {
		this.catalogo.stream().sorted().forEach(System.out::println);
	}

	public void imprimirAutores() {
		this.autores.stream().forEach(System.out::println);
	}

	
	private Comparator<Material> getCriterio(CriterioOrdenamiento criterio) {
		switch(criterio) {
			case CALIFICACION:
				return (m1,m2) -> m2.getCalificacion().compareTo(m1.getCalificacion());
			case TITULO:
				return (m1,m2) -> m1.getTitulo().compareToIgnoreCase(m2.getTitulo());
			case FECHA_PUBLICACION:
				return (m1,m2) -> m1.getFechaPublicacion().compareTo(m2.getFechaPublicacion());
			case AUTOR:
				return (m1,m2) -> m1.getAutor().getNombre().compareToIgnoreCase(m2.getAutor().getNombre());
		}
		return null;
	}
	
	public List<Material> busquedaPorTitulo(String titulo,Integer cantidadDeResultados){
		Predicate<Material> filtrarTitulo = m -> m.getTitulo().toUpperCase().contains(titulo.toUpperCase());		
		return this.buscarListaMaterial(filtrarTitulo, cantidadDeResultados, this.getCriterio(CriterioOrdenamiento.TITULO));
	}
	
	public List<Material> busquedaRangoCalificacionOrdCalif(Integer minimo,Integer max){
		return this.buscarListaMaterial( m -> m.getCalificacion()>= minimo && m.getCalificacion()<=max, this.getCriterio(CriterioOrdenamiento.CALIFICACION));
	}
	
	public List<Material> busquedaPorRangoDeFecha(String inicio, String fin , Integer cantidadDeResultados){
		LocalDateTime fechaInicio,fechaFin;
		fechaInicio = LocalDate.parse(inicio, formatter).atStartOfDay();
		fechaFin = LocalDate.parse(fin, formatter).atStartOfDay();
		Predicate<Material> filtrarPorRangoDeFecha = m -> m.getFechaPublicacion().isBefore(fechaFin) && m.getFechaPublicacion().isAfter(fechaInicio);
		return this.buscarListaMaterial(filtrarPorRangoDeFecha,cantidadDeResultados,this.getCriterio(CriterioOrdenamiento.FECHA_PUBLICACION));
	}
	
	public List<Material> busquedaPorTipoyAutor(Class clas, String nombreAutor, int cantidadDeResultados){
		Predicate<Material> filtrarPorTipoyAutor = m -> m.getClass() == clas && m.getAutor().getNombre().toUpperCase().contains(nombreAutor.toUpperCase());
		return this.buscarListaMaterial(filtrarPorTipoyAutor, cantidadDeResultados, this.getCriterio(CriterioOrdenamiento.AUTOR));
	}
	
	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		System.out.println(LocalDate.parse("20-02-2010", formatter).atTime(LocalTime.now()));
		System.out.println(LocalDate.parse("20-02-2010", formatter).atStartOfDay());
		
	}

	public Collection<Material> busquedaPorRangoDeCalificacion(int calificacionMinima, int calificacionMaxima, int cantidadDeResultados) {
		Predicate<Material> filtrarPorRangoDeCalifiacion = m -> m.getCalificacion() >= calificacionMinima && m.getCalificacion() <= calificacionMaxima;
		return this.buscarListaMaterial(filtrarPorRangoDeCalifiacion , cantidadDeResultados, this.getCriterio(CriterioOrdenamiento.CALIFICACION));
	}

}
