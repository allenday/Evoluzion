/*Copyright 2014 Adolfo R. Zurita*/

/*This file is part of EvoluZion.

 EvoluZion is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 EvoluZion is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with EvoluZion.  If not, see <http://www.gnu.org/licenses/>.*/

package com.evoluzion;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Mundo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Evoluzion ev;// clase prinsipal
	MenuInicio mi; // menu inicio
	Texto tx;
	
	//Virus virus = new Virus(20,20,10,95); 
		
	Array<Senergia> ase, ase1, ase2, ase3, ase4, ase5, ase6, ase7, ase8, ase9,
			ase10; // lista de energia verde
	Array<Qenergia> aqe, aqe1, aqe2, aqe3, aqe4, aqe5, aqe6, aqe7, aqe8, aqe9,
			aqe10; // lista de energia roja (biomasa)
	Array<Organismo> aorg; // lista de organismos
	Array<Organismo> aEspesies, aEspesiesTotales; // listas de organismos
													// colectados segun especies
	Array<Alelo> aAlelos;
	Array<Fenotipo> aFenotipos;
	Array<Genotipo> aGenotipos;

	float ancho, alto, ratio; // dimenciones de la pantalla
	//
	float deltaX = 0; // delta de x entre dos individuos
	float deltaY = 0; // delta de y entre dos individuos
	float medSpeed; // se usa para el calcula de velosidad media
	double temperatura = 25;
	double medTem;
	float eficiencia = 1;
	// suma de toda la masa libre

	int dias, horas, minutos, minutos2, minutos3, segundos, segundos2,
			segundos3, segundos4, segundos5, segundos6; // anota el paso del
														// tiempo
	long edad, delta, delta2, delta3, tiempo; // mide diferencia de tiempo entre
												// una accion y la siguiente
	int tiempoMaximo = 0;
	double Masatotal, MasatotalL, MasatotalR;
	int tiempoMuestreo;
	int tiempoCatastrofe;
	int tiempoATB;
	int cantidadMuestras;
	int contadorMuestreo;
	int Senergia;
	int Qbiomasa;
	int numOrg;
	int numSen;
	int numQen;
	int numSenR;
	int numQenR;
	int SenergiaR;
	int QbiomasaR;
	int EnRe;
	int BioRe;
	float medSize;
	int medMut;
	int medLon;
	int pausaGame = 1;// 1= play -1=stop
	float zoom = 2;
	int index = 0;
	int maximo = 1000; // numero maximo de organismos
	int ingles = -1; // -1=false 1=true
	int indice = 0, indiceDer = 0, indiceIz = 0; // se usa para ordenar los
													// organismos al final de la
													// partida
	
	
	float minStar1, minStar2;
	float TempFinal1, TempFinal2, tempXSecond1, tempXSecond2;
	float deltaTime1, deltaTime2;
	float salud;
	// hotSpot for crossover

	int gap1, gap2, gap3, gap4, gap5, gap6, gap7, gap8, gap9, gap92, gap10,
			gap11;

	int antibiotico = -1; // 1= true -1= false

	int numero = 0; // es usado por metodos que cuentan cosas
	int id = 0; // es usado por metodos que cuentan cosas
	int cantidad = 0;// es usado por metodos que cuentan cosas
	
	int numeroI = 0; // es usado por los metodos que usan calculos
	int numeroD = 0; // es usado por los metodos que usan calculos
	
	int numEspI = 0; // es usado por los metodos que usan calculos
	int numEspD = 0; // es usado por los metodos que usan calculos
	
	
	int horizontalTransferRate;

	boolean verFrontera = false;
	boolean verMachoHembra = false;
	boolean parar = false;

	boolean colectarancho = false;
	boolean colectaralto = false;
	boolean colectSpeed = false;
	boolean colectColor = false;
	boolean colectSentir = false;
	boolean colectPredador = false;
	boolean colectCazar = false;
	boolean colectEscapar = false;
	boolean colectRadioconsiente = false;
	boolean colectarTasaMut = false;
	boolean colectarLongevidad = false;
	boolean colectarTemp = false;
	boolean colectarResistencia = false;
	boolean colectarFeromona = false;
	boolean colectarParteNoGen = false;

	boolean mutarColor = true;
	boolean mutarSize = true;
	boolean mutarSpeed = true;
	boolean mutarSentir = true;
	boolean mutarPredador = true;
	boolean mutarCazar = true;
	boolean mutarEscapar = true;
	boolean mutarRadioconsiente = true;
	boolean mutarTasaMut = true;
	boolean mutarLongevidad = true;
	boolean mutarTemp = true;
	boolean mutarResistencia = true;
	boolean mutarFeromona = false;
	boolean mutarParteNoGen = false;

	boolean cargarPoblacion = false;
	boolean moverMasa;

	String ruta, poblacion;
	StringBuffer linea, orgNombre; // se usa para archivar los resultado
	StringBuffer ceroIz, ceroIz2; // agrega un cero a la izquierda
	String nombre;

	TextureAtlas textuRA_ENER, textura_ORG, textura_organismos, textura_sex; // contine
																				// las
																				// imagenes
	Texture auraATB, transferido, textura_feromona;
	Archivar f_datos, f_genes, f_arbol, f_proteoma, f_poblacion, f_mutantes,
			f_alelos, f_fenotipos, f_genotipos, f_alelos2; // para archivar
	NumberFormat format = new DecimalFormat("0.00");
	NumberFormat format2 = new DecimalFormat("0");
	NumberFormat format3 = new DecimalFormat("#.##");

	Rectangle frontera;

	public Mundo(Evoluzion ev, String ruta, String nombre, String poblacion,
			int numOrg, int numSen, int numQen, int Senergia, int Qbiomasa,
			int numSenR, int numQenR, int SenergiaR, int QbiomasaR,
			boolean cargarPoblacion, boolean moverMasa, boolean verFrontera,
			boolean verMachoHembra, String genesPedidos, int ingles) {
		this.ev = ev;

		this.numOrg = numOrg;
		this.numSen = numSen;
		this.numQen = numQen;
		this.Senergia = Senergia;
		this.Qbiomasa = Qbiomasa;

		this.numSenR = numSenR;
		this.numQenR = numQenR;
		this.SenergiaR = SenergiaR;
		this.QbiomasaR = QbiomasaR;
		this.cargarPoblacion = cargarPoblacion;
		this.moverMasa = moverMasa;
		this.verFrontera = verFrontera;
		this.verMachoHembra = verMachoHembra;
		this.nombre = nombre;
		this.ruta = ruta;
		this.poblacion = poblacion;
		this.ingles = ingles;

		tx = new Texto();
		if (ingles == 1) {
			tx.setIngles();
		}
		if (ingles == -1) {
			tx.setEspanol();
		}

		orgNombre = new StringBuffer("a");
		ceroIz = new StringBuffer("");
		ceroIz2 = new StringBuffer("");

		// quaddtree

		// quad = new Quadtree(0, new Rectangle(0,0,ancho,alto));

		// tamaño de la pantalla
		ancho = Gdx.graphics.getWidth();
		alto = Gdx.graphics.getHeight();

		// listas
		ase = new Array<Senergia>();
		ase1 = new Array<Senergia>();
		ase2 = new Array<Senergia>();
		ase3 = new Array<Senergia>();
		ase4 = new Array<Senergia>();
		ase5 = new Array<Senergia>();
		ase6 = new Array<Senergia>();
		ase7 = new Array<Senergia>();
		ase8 = new Array<Senergia>();
		ase9 = new Array<Senergia>();
		ase10 = new Array<Senergia>();
		aqe = new Array<Qenergia>();
		aqe1 = new Array<Qenergia>();
		aqe2 = new Array<Qenergia>();
		aqe3 = new Array<Qenergia>();
		aqe4 = new Array<Qenergia>();
		aqe5 = new Array<Qenergia>();
		aqe6 = new Array<Qenergia>();
		aqe7 = new Array<Qenergia>();
		aqe8 = new Array<Qenergia>();
		aqe9 = new Array<Qenergia>();
		aqe10 = new Array<Qenergia>();

		aorg = new Array<Organismo>();

		aEspesies = new Array<Organismo>();

		aEspesiesTotales = new Array<Organismo>();

		aAlelos = new Array<Alelo>();
		aFenotipos = new Array<Fenotipo>();
		aGenotipos = new Array<Genotipo>();

		// set time to 0
		setDelta();
		setDelta2();
		setDelta3();
		setTiempo();

		linea = new StringBuffer();// used to write text in files

		textura_organismos = new TextureAtlas("data/organismo.pack");
		

		frontera = new Rectangle();
		frontera.x = (ancho / 2) - 10;
		frontera.y = 0;
		frontera.height = alto;
		frontera.width = 20;

		// agregar cuantos de energia solar panel Izquierdo

		float x = 0;
		float y = alto;
		for (int i = 0; i < this.numSen; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			x = x + 20;
			if (x >= ancho) {
				x = 0;
				y = y - 20;
			}

			Senergia se = new Senergia(pos, this);
			se.energia = this.Senergia;
			ase.add(se);
		}

		// agregar cuantos de energia solar panel Derecho

		for (int e = 0; e < this.numSenR; e++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			x = x + 20;
			if (x >= ancho) {
				x = 0;
				y = y - 20;
			}

			Senergia se = new Senergia(pos, this);
			se.energia = this.SenergiaR;
			ase.add(se);

		}

		ase.shuffle();
		ase.shrink();

		// agregar cuantos de energia en las listas divididas por sector

		for (int i = 0; i < ase.size; i++) {

			if (ase.get(i).posicion.x <= ancho * (1 / 10)) {
				ase1.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (1 / 10)
					&& ase.get(i).posicion.x <= ancho * (2 / 10)) {
				ase2.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (2 / 10)
					&& ase.get(i).posicion.x <= ancho * (3 / 10)) {
				ase3.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (3 / 10)
					&& ase.get(i).posicion.x <= ancho * (4 / 10)) {
				ase4.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (4 / 10)
					&& ase.get(i).posicion.x <= ancho * (5 / 10)) {
				ase5.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (5 / 10)
					&& ase.get(i).posicion.x <= ancho * (6 / 10)) {
				ase6.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (6 / 10)
					&& ase.get(i).posicion.x <= ancho * (7 / 10)) {
				ase7.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (7 / 10)
					&& ase.get(i).posicion.x <= ancho * (8 / 10)) {
				ase8.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (8 / 10)
					&& ase.get(i).posicion.x <= ancho * (9 / 10)) {
				ase9.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (9 / 10)
					&& ase.get(i).posicion.x <= ancho) {
				ase10.add(ase.get(i));
			}

		}

		ase1.shrink();// ajusta el tamaño de la lista a la cantedidad de
						// elmentos que tiene
		ase2.shrink();
		ase3.shrink();
		ase4.shrink();
		ase5.shrink();
		ase6.shrink();
		ase7.shrink();
		ase8.shrink();
		ase9.shrink();
		ase10.shrink();

		// agregar materia del lado izquierdo

		for (int i = 0; i < this.numQen; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			aqe.add(new Qenergia(pos, moverMasa, Qbiomasa, this));
		}

		// agregar materia del lado Derecho

		for (int i = 0; i < this.numQenR; i++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			aqe.add(new Qenergia(pos, moverMasa, this.QbiomasaR, this));
		}

		// agregar materia invisible para usarla en el balanse de masa

		for (int i = 0; i < numQen / 3; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			Qenergia qe = new Qenergia(pos, moverMasa, Qbiomasa, this);
			qe.visible = false;
			aqe.add(qe);
		}

		for (int i = 0; i < this.numQenR / 3; i++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			Qenergia qe = new Qenergia(pos, moverMasa, QbiomasaR, this);
			qe.visible = false;
			aqe.add(qe);
		}

		aqe.shrink();

		// repartirla materia izquierda y derecha

		// agregar cuantos de materia en las listas divididas por sector

		for (int i = 0; i < aqe.size; i++) {

			if (aqe.get(i).posicion.x <= ancho * (1 / 10)) {
				aqe1.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (1 / 10)
					&& aqe.get(i).posicion.x <= ancho * (2 / 10)) {
				aqe2.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (2 / 10)
					&& aqe.get(i).posicion.x <= ancho * (3 / 10)) {
				aqe3.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (3 / 10)
					&& aqe.get(i).posicion.x <= ancho * (4 / 10)) {
				aqe4.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (4 / 10)
					&& aqe.get(i).posicion.x <= ancho * (5 / 10)) {
				aqe5.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (5 / 10)
					&& aqe.get(i).posicion.x <= ancho * (6 / 10)) {
				aqe6.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (6 / 10)
					&& aqe.get(i).posicion.x <= ancho * (7 / 10)) {
				aqe7.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (7 / 10)
					&& aqe.get(i).posicion.x <= ancho * (8 / 10)) {
				aqe8.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (8 / 10)
					&& aqe.get(i).posicion.x <= ancho * (9 / 10)) {
				aqe9.add(aqe.get(i));
			}
			if (aqe.get(i).posicion.x > ancho * (9 / 10)
					&& aqe.get(i).posicion.x <= ancho) {
				aqe10.add(aqe.get(i));
			}

		}
		aqe1.shrink();
		aqe2.shrink();
		aqe3.shrink();
		aqe4.shrink();
		aqe5.shrink();
		aqe6.shrink();
		aqe7.shrink();
		aqe8.shrink();
		aqe9.shrink();
		aqe10.shrink();

		// colecta la primera especie en la lista

		for (Organismo or : aorg) {

			boolean igual = false;

			id = or.identificador;
			for (Organismo or2 : aEspesies) {

				if (id == or2.identificador) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesies.add(or);
			}

		}

		for (Organismo or : aorg) {

			boolean igual = false;
			id = or.identificador;

			for (Organismo or2 : aEspesiesTotales) {

				if (id == or2.identificador) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesiesTotales.add(or);
			}

		}

		// manejamos los archivos
		f_datos = new Archivar();
		f_genes = new Archivar();
		f_proteoma = new Archivar();
		f_arbol = new Archivar();
		f_poblacion = new Archivar();
		f_mutantes = new Archivar();
		f_alelos = new Archivar();
		f_fenotipos = new Archivar();
		f_genotipos = new Archivar();
		f_alelos2 = new Archivar();

	}

	// end of cronstructor metod

	// metodos

	public void agregarPrimerosOrg(int pan, boolean sex, int num, int tosRes,
			int anch, int alt, int senses, int optTem, int pred, int speed,
			int cazar, int escape, int radio, int fer, int parteNoGen,
			int relleno, int dnaPol, int longe, int col) {

		for (int i = 0; i < num; i++) {

			float a = 0;
			if (pan == 1) {
				a = (float) Math.random() * (ancho - (ancho / 2)) + ancho / 2;
			}
			if (pan == 2) {
				a = (float) Math.random() * ancho / 2;
			}

			Vector2 pos = new Vector2(a, (float) Math.random() * alto);

			Vector2 dir = new Vector2((float) Math.random() * 20,
					(float) Math.random() * 20);
			if (dir.x < 10) {
				dir.x = dir.x * (-1);
			}
			if (dir.x > 10) {
				dir.x = dir.x - 10;
			}
			if (dir.y < 10) {
				dir.y = dir.y * (-1);
			}
			if (dir.y > 10) {
				dir.y = dir.y - 10;
			}

			Organismo or = new Organismo(sex, new Genoma(this, tosRes, anch,
					alt, senses, optTem, pred), new Genoma(this, tosRes, anch,
					alt, senses, optTem, pred), new Genoma(this, speed, cazar,
					escape, radio, fer, parteNoGen, relleno), new Genoma(this,
					speed, cazar, escape, radio, fer, parteNoGen, relleno),
					new Genoma(this, dnaPol, longe, col), new Genoma(this,
							dnaPol, longe, col), pos, orgNombre, this);
			or.direccion = dir;
			
			or.infectado =true;
			
			aorg.add(or);

		}
		double o = BiomasaTotal(aorg) / Qbiomasa;

		for (int i = 0; i < o; i++) {

			Qenergia qe = aqe.get(i);
			qe.visible = false;

		}

		Masatotal = MateriaLibre() + BiomasaTotal(aorg);

		MasatotalL = MateriaLibreL() + BiomasaTotalI(aorg);

		MasatotalR = MateriaLibreR() + BiomasaTotalD(aorg);
		
		cantidadOrganismosI(aorg);
		cantidadOrganismosD(aorg);
		cantidadEspeciesI(aorg);
		cantidadEspeciesD(aorg);
		
		

	}

	public int cantidadOrganismos(Array<Organismo> aor) {

		return aor.size;
	}
	
	
	public void cantidadOrganismosI(Array<Organismo> aor) {  

		int num = 0;
		
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).posicion.x < ancho / 2) {
				num = num + 1;
			}
		//	System.out.println("dentro del loop"+ num);//return numeroI;
			
		}
		numeroI = num;
		//System.out.println("dentro del metodo"+ numeroI);//return numeroI;
	}

	//caluculos
	
	
	public void cantidadOrganismosD(Array<Organismo> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).posicion.x > ancho / 2) {
				num = num + 1;
			}
		}
		numeroD = num;
		//return numeroD;
	}
	
	public void cantidadEspeciesI(Array<Organismo> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).posicion.x > ancho / 2) {
				num = num + 1;
			}
		}
		numEspI = num;
		//return numeroD;
	}
	
	public void cantidadEspeciesD(Array<Organismo> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).posicion.x > ancho / 2) {
				num = num + 1;
			}
		}
		numEspD = num;
		//return numeroD;
	}
		
	
	

	public void borrarOrganismo(Organismo or) {

		aorg.removeValue(or, true);

		or = null;

		aorg.shrink();

	}

	// cuenta la cantidad de machos

	public int cantidadMachos(Array<Organismo> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadMachosI(Array<Organismo> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).posicion.x < ancho / 2 && aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadMachosD(Array<Organismo> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).posicion.x > ancho / 2 && aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadHembras(Array<Organismo> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public int cantidadHembrasI(Array<Organismo> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).posicion.x < ancho / 2 && aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public int cantidadHembrasD(Array<Organismo> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).posicion.x > ancho / 2 && aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public double temOptimaMedia(Array<Organismo> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medTem = medTem + aor.get(i).tempOptima;
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numero;
	}

	public double temOptimaMediaI(Array<Organismo> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					medTem = medTem + aor.get(i).tempOptima;
				}
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numeroI;
	}

	public double temOptimaMediaD(Array<Organismo> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					medTem = medTem + aor.get(i).tempOptima;
				}
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numeroD;
	}

	
	public int cantidadPredadores(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).carnivoro == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadPredadoresI(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x < ancho / 2
					&& aor.get(i).carnivoro == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadPredadoresD(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x > ancho / 2
					&& aor.get(i).carnivoro == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentes(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).resistenciaATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentesI(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x < ancho / 2
					&& aor.get(i).resistenciaATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentesD(Array<Organismo> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x > ancho / 2
					&& aor.get(i).resistenciaATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public float velocidadMedia(Array<Organismo> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medSpeed = medSpeed + aor.get(i).speed;
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSpeed / numero;
	}

	public float velocidadMediaI(Array<Organismo> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					medSpeed = medSpeed + aor.get(i).speed;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSpeed / numeroI;
	}

	public float velocidadMediaD(Array<Organismo> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					medSpeed = medSpeed + aor.get(i).speed;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSpeed / numeroD;
	}

	public int tasaMutMedio(Array<Organismo> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medMut = (int) (medMut + aor.get(i).tasaMut);
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (medMut / numero);
	}

	public int tasaMutMedioI(Array<Organismo> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					medMut = (int) (medMut + aor.get(i).tasaMut);
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (medMut / numeroI);
	}

	public int tasaMutMedioD(Array<Organismo> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					medMut = (int) (medMut + aor.get(i).tasaMut);
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (medMut / numeroD);
	}

	public float longevidadMedio(Array<Organismo> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medLon = (int) (medLon + aor.get(i).longevidad);
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (float) (medLon) / 1000 / numero;
	}

	public float longevidadMedioI(Array<Organismo> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					medLon = (int) (medLon + aor.get(i).longevidad);
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (float) (medLon) / 1000 / numeroI;
	}

	public float longevidadMedioD(Array<Organismo> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					medLon = (int) (medLon + aor.get(i).longevidad);
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return (float) (medLon) / 1000 / numeroD;
	}

	public float saludMedio(Array<Organismo> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				salud = salud + aor.get(i).SaludCoefi;
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return salud / numero;
	}

	public float saludMedioD(Array<Organismo> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					salud = salud + aor.get(i).SaludCoefi;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return salud / numeroD;
	}

	public float saludMedioI(Array<Organismo> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					salud = salud + aor.get(i).SaludCoefi;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return salud / numeroI;
	}

	public float tamanoMedio(Array<Organismo> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medSize = medSize + aor.get(i).capacidad;
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSize / numero;
	}

	public float tamanoMedioI(Array<Organismo> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x < ancho / 2) {
					medSize = medSize + aor.get(i).capacidad;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSize / numeroI;
	}

	public float tamanoMedioD(Array<Organismo> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).posicion.x > ancho / 2) {
					medSize = medSize + aor.get(i).capacidad;
				}
			}
		}
		if (numero == 0 || numeroI== 0|| numeroD==0) {
			numero = 1; numeroI=1; numeroD=1;
		}

		return medSize / numeroD;
	}

	public int MateriaLibre() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).masa);
			}
		}
		return materia;
	}

	public int MateriaLibreL() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).posicion.x < ancho / 2 && aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).masa);
			}
		}
		return materia;
	}

	public int MateriaLibreR() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).posicion.x > ancho / 2 && aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).masa);
			}
		}
		return materia;
	}

	public double BiomasaTotal(Array<Organismo> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			biomasaTotal = (int) (biomasaTotal + aor.get(i).biomasa);
		}
		return biomasaTotal;
	}

	public int BiomasaTotalD(Array<Organismo> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x > ancho / 2) {
				biomasaTotal = (int) (biomasaTotal + aor.get(i).biomasa);
			}
		}
		return biomasaTotal;
	}

	public int BiomasaTotalI(Array<Organismo> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x < ancho / 2) {
				biomasaTotal = (int) (biomasaTotal + aor.get(i).biomasa);
			}
		}
		return biomasaTotal;
	}

	public int BioenergiaTotal(Array<Organismo> aor) {
		int energiaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			energiaTotal = (int) (energiaTotal + aor.get(i).energia);
		}
		return energiaTotal;
	}

	// to correct rounding errors in the total mass of the system, add or remove
	// mass

	public void chequearBalance() {

		if (verFrontera == false) {

			double a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

		}

		if (verFrontera == true) {
			double a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));
			numero = 0;
			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			// lado derecho

			double b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).posicion.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

		}
	}

	// kill 95% of all organism

	public void catastrofe() {

		numero = aorg.size;
		for (int i = numero - 1; i >= 0; i--) {

			int e = (int) (Math.random() * 10000);

			if (e > 500) {
				aorg.get(i).morir();
			}

		}

	}

	// metodos para colectar los datos

	public void guardarPoblacion() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población Total" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						linea.replace(0, linea.length(), "<Male>" + or.male
								+ "<h>" + or.nombre.toString() + "dX"
								+ or.posicion.x + "dY" + or.posicion.y
								+ "<atb>" + or.adn.resistenciaATB + "<ancho>"
								+ or.adn.ancho + "<alto>" + or.adn.alto
								+ "<sentir>" + or.adn.sentir + "<temp>"
								+ or.adn.toleranciaTemp + "<predador>"
								+ or.adn.predador + "<speed>" + or.adn3.speed
								+ "<cazar>" + or.adn3.cazar + "<escapar>"
								+ or.adn3.escapar + "<alcance>"
								+ or.adn3.radioConsiente + "<fero>"
								+ or.adn3.feromona + "<noGen>"
								+ or.adn3.parteNoGen + "<tasamut>"
								+ or.adn5.tasaMutacion + "<longevidad>"
								+ or.adn5.longevidad + "<color>"
								+ or.adn5.color + "<atb2>"
								+ or.adn2.resistenciaATB + "<ancho2>"
								+ or.adn2.ancho + "<alto2>" + or.adn2.alto
								+ "<sentir2>" + or.adn2.sentir + "<temp2>"
								+ or.adn2.toleranciaTemp + "<predador2>"
								+ or.adn2.predador + "<speed2>" + or.adn4.speed
								+ "<cazar2>" + or.adn4.cazar + "<escapar2>"
								+ or.adn4.escapar + "<alcance2>"
								+ or.adn4.radioConsiente + "<fero2>"
								+ or.adn4.feromona + "<noGen2>"
								+ or.adn4.parteNoGen + "<tasamut2>"
								+ or.adn6.tasaMutacion + "<longevidad2>"
								+ or.adn6.longevidad + "<color2>"
								+ or.adn6.color + "\n");
						f_poblacion.escribirArchivo(linea.toString());
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void guardarPoblacionMarcada() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población Marcada" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						if (or.marcado == -1) { // -1 == true
							linea.replace(0, linea.length(), "<Male>" + or.male
									+ "<h>" + or.nombre.toString() + "dX"
									+ or.posicion.x + "dY" + or.posicion.y
									+ "<atb>" + or.adn.resistenciaATB
									+ "<ancho>" + or.adn.ancho + "<alto>"
									+ or.adn.alto + "<sentir>" + or.adn.sentir
									+ "<temp>" + or.adn.toleranciaTemp
									+ "<predador>" + or.adn.predador
									+ "<speed>" + or.adn3.speed + "<cazar>"
									+ or.adn3.cazar + "<escapar>"
									+ or.adn3.escapar + "<alcance>"
									+ or.adn3.radioConsiente + "<fero>"
									+ or.adn3.feromona + "<noGen>"
									+ or.adn3.parteNoGen + "<tasamut>"
									+ or.adn5.tasaMutacion + "<longevidad>"
									+ or.adn5.longevidad + "<color>"
									+ or.adn5.color + "<atb2>"
									+ or.adn2.resistenciaATB + "<ancho2>"
									+ or.adn2.ancho + "<alto2>" + or.adn2.alto
									+ "<sentir2>" + or.adn2.sentir + "<temp2>"
									+ or.adn2.toleranciaTemp + "<predador2>"
									+ or.adn2.predador + "<speed2>"
									+ or.adn4.speed + "<cazar2>"
									+ or.adn4.cazar + "<escapar2>"
									+ or.adn4.escapar + "<alcance2>"
									+ or.adn4.radioConsiente + "<fero2>"
									+ or.adn4.feromona + "<noGen2>"
									+ or.adn4.parteNoGen + "<tasamut2>"
									+ or.adn6.tasaMutacion + "<longevidad2>"
									+ or.adn6.longevidad + "<color2>"
									+ or.adn6.color + "\n");
							f_poblacion.escribirArchivo(linea.toString());
						}
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void guardarPoblacionNoMaracada() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población No Marcada" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						if (or.marcado == 1) { // 1 = false
							linea.replace(0, linea.length(), "<Male>" + or.male
									+ "<h>" + or.nombre.toString() + "dX"
									+ or.posicion.x + "dY" + or.posicion.y
									+ "<atb>" + or.adn.resistenciaATB
									+ "<ancho>" + or.adn.ancho + "<alto>"
									+ or.adn.alto + "<sentir>" + or.adn.sentir
									+ "<temp>" + or.adn.toleranciaTemp
									+ "<predador>" + or.adn.predador
									+ "<speed>" + or.adn3.speed + "<cazar>"
									+ or.adn3.cazar + "<escapar>"
									+ or.adn3.escapar + "<alcance>"
									+ or.adn3.radioConsiente + "<fero>"
									+ or.adn3.feromona + "<noGen>"
									+ or.adn3.parteNoGen + "<tasamut>"
									+ or.adn5.tasaMutacion + "<longevidad>"
									+ or.adn5.longevidad + "<color>"
									+ or.adn5.color + "<atb2>"
									+ or.adn2.resistenciaATB + "<ancho2>"
									+ or.adn2.ancho + "<alto2>" + or.adn2.alto
									+ "<sentir2>" + or.adn2.sentir + "<temp2>"
									+ or.adn2.toleranciaTemp + "<predador2>"
									+ or.adn2.predador + "<speed2>"
									+ or.adn4.speed + "<cazar2>"
									+ or.adn4.cazar + "<escapar2>"
									+ or.adn4.escapar + "<alcance2>"
									+ or.adn4.radioConsiente + "<fero2>"
									+ or.adn4.feromona + "<noGen2>"
									+ or.adn4.parteNoGen + "<tasamut2>"
									+ or.adn6.tasaMutacion + "<longevidad2>"
									+ or.adn6.longevidad + "<color2>"
									+ or.adn6.color + "\n");
							f_poblacion.escribirArchivo(linea.toString());
						}
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void leerArchivoPoblacion() {

		for (Organismo or : aorg) {
			aorg.removeValue(or, true);
		}
		;
		for (Organismo or : aEspesiesTotales) {
			aEspesiesTotales.removeValue(or, true);
		}

		try {

			FileReader fr = new FileReader(poblacion);
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				Genoma gen = new Genoma(this, 1, 1, 1, 0, 0, 0);
				Genoma gen2 = new Genoma(this, 1, 1, 1, 0, 0, 0);

				Genoma gen3 = new Genoma(this, 0, 0, 0, 0, 0, 0, 0);
				Genoma gen4 = new Genoma(this, 0, 0, 0, 0, 0, 0, 0);

				Genoma gen5 = new Genoma(this, 0, 0, 0);
				Genoma gen6 = new Genoma(this, 0, 0, 0);

				StringBuffer sexo = new StringBuffer(linea.substring(
						linea.indexOf("<Male>") + 6, linea.indexOf("<h>")));
				StringBuffer nombre = new StringBuffer(linea.substring(
						linea.indexOf("<h>") + 3, linea.indexOf("dX")));
				Vector2 pos = new Vector2(
						Float.parseFloat(linea.substring(
								linea.indexOf("dX") + 2, linea.indexOf("dY"))),
						Float.parseFloat(linea.substring(
								linea.indexOf("dY") + 2, linea.indexOf("<atb>"))));

				boolean male = true;

				if (sexo.toString().equals("true")) {
					male = true;
				}
				if (sexo.toString().equals("false")) {
					male = false;
				}

				gen.resistenciaATB.replace(
						0,
						gen.resistenciaATB.length(),
						linea.substring(linea.indexOf("<atb>") + 5,
								linea.indexOf("<ancho>")));
				gen.ancho.replace(
						0,
						gen.ancho.length(),
						linea.substring(linea.indexOf("<ancho>") + 7,
								linea.indexOf("<alto>")));
				gen.alto.replace(
						0,
						gen.alto.length(),
						linea.substring(linea.indexOf("<alto>") + 6,
								linea.indexOf("<sentir>")));
				gen.sentir.replace(
						0,
						gen.sentir.length(),
						linea.substring(linea.indexOf("<sentir>") + 8,
								linea.indexOf("<temp>")));
				gen.toleranciaTemp.replace(
						0,
						gen.toleranciaTemp.length(),
						linea.substring(linea.indexOf("<temp>") + 6,
								linea.indexOf("<predador>")));
				gen.predador.replace(
						0,
						gen.predador.length(),
						linea.substring(linea.indexOf("<predador>") + 10,
								linea.indexOf("<speed>")));

				gen3.speed.replace(
						0,
						gen3.speed.length(),
						linea.substring(linea.indexOf("<speed>") + 7,
								linea.indexOf("<cazar>")));
				gen3.cazar.replace(
						0,
						gen3.cazar.length(),
						linea.substring(linea.indexOf("<cazar>") + 7,
								linea.indexOf("<escapar>")));
				gen3.escapar.replace(
						0,
						gen3.escapar.length(),
						linea.substring(linea.indexOf("<escapar>") + 9,
								linea.indexOf("<alcance>")));
				gen3.radioConsiente.replace(
						0,
						gen3.radioConsiente.length(),
						linea.substring(linea.indexOf("<alcance>") + 9,
								linea.indexOf("<fero>")));
				gen3.feromona.replace(
						0,
						gen3.feromona.length(),
						linea.substring(linea.indexOf("<fero>") + 6,
								linea.indexOf("<noGen>")));
				gen3.parteNoGen.replace(
						0,
						gen4.parteNoGen.length(),
						linea.substring(linea.indexOf("<noGen>") + 7,
								linea.indexOf("<tasamut2>")));

				gen5.tasaMutacion.replace(
						0,
						gen5.tasaMutacion.length(),
						linea.substring(linea.indexOf("<tasamut>") + 9,
								linea.indexOf("<longevidad>")));
				gen5.longevidad.replace(0, gen5.longevidad.length(), linea
						.substring(linea.indexOf("<longevidad>") + 12,
								linea.indexOf("<color>")));
				gen5.color.replace(
						0,
						gen5.color.length(),
						linea.substring(linea.indexOf("<color>") + 7,
								linea.length()));

				gen2.resistenciaATB.replace(
						0,
						gen2.resistenciaATB.length(),
						linea.substring(linea.indexOf("<atb2>") + 6,
								linea.indexOf("<ancho2>")));
				gen2.ancho.replace(
						0,
						gen2.ancho.length(),
						linea.substring(linea.indexOf("<ancho2>") + 8,
								linea.indexOf("<alto2>")));
				gen2.alto.replace(
						0,
						gen2.alto.length(),
						linea.substring(linea.indexOf("<alto2>") + 7,
								linea.indexOf("<sentir2>")));
				gen2.sentir.replace(
						0,
						gen2.sentir.length(),
						linea.substring(linea.indexOf("<sentir2>") + 9,
								linea.indexOf("<temp2>")));
				gen2.toleranciaTemp.replace(
						0,
						gen2.toleranciaTemp.length(),
						linea.substring(linea.indexOf("<temp2>") + 7,
								linea.indexOf("<predador2>")));
				gen2.predador.replace(0, gen2.predador.length(), linea
						.substring(linea.indexOf("<predador2>") + 11,
								linea.indexOf("<speed2>")));

				gen4.speed.replace(
						0,
						gen4.speed.length(),
						linea.substring(linea.indexOf("<speed2>") + 8,
								linea.indexOf("<cazar2>")));
				gen4.cazar.replace(
						0,
						gen4.cazar.length(),
						linea.substring(linea.indexOf("<cazar2>") + 8,
								linea.indexOf("<escapar2>")));
				gen4.escapar.replace(
						0,
						gen4.escapar.length(),
						linea.substring(linea.indexOf("<escapar2>") + 10,
								linea.indexOf("<alcance2>")));
				gen4.radioConsiente.replace(
						0,
						gen4.radioConsiente.length(),
						linea.substring(linea.indexOf("<alcance2>") + 10,
								linea.indexOf("<fero2>")));
				gen4.feromona.replace(
						0,
						gen4.feromona.length(),
						linea.substring(linea.indexOf("<fero2>") + 7,
								linea.indexOf("<noGen2>")));
				gen4.parteNoGen.replace(
						0,
						gen4.parteNoGen.length(),
						linea.substring(linea.indexOf("<noGen2>") + 8,
								linea.indexOf("<tasamut2>")));

				gen6.tasaMutacion.replace(
						0,
						gen6.tasaMutacion.length(),
						linea.substring(linea.indexOf("<tasamut2>") + 10,
								linea.indexOf("<longevidad2>")));
				gen6.longevidad.replace(0, gen6.longevidad.length(), linea
						.substring(linea.indexOf("<longevidad2>") + 11,
								linea.indexOf("<color2>")));
				gen6.color.replace(
						0,
						gen6.color.length(),
						linea.substring(linea.indexOf("<color2>") + 8,
								linea.length()));

				Organismo or = new Organismo(male, gen, gen2, gen3, gen4, gen5,
						gen6, pos, nombre, this);

				// asiganr una direccion a cada organismo

				float x = (float) Math.random() * 360;
				float seno = (float) Math.sin(x) * 10;
				float coseno = (float) Math.sin(x + 3.1416 / 2) * 10;
				Vector2 dir = new Vector2(seno, coseno);

				or.direccion.x = dir.x;
				or.direccion.y = dir.y;
				aorg.add(or);
			}

			Masatotal = MateriaLibre() + BiomasaTotal(aorg);

			MasatotalL = MateriaLibreL() + BiomasaTotalI(aorg);

			MasatotalR = MateriaLibreR() + BiomasaTotalD(aorg);

			br.close();
			fr.close();

		} catch (IOException ex) {

			JOptionPane
					.showMessageDialog(null, "no se puede leer este archivo");

			for (int i = 0; i < numOrg; i++) {

				Vector2 dir = new Vector2((float) Math.random() * 20,
						(float) Math.random() * 20);
				if (dir.x < 10) {
					dir.x = dir.x * (-1);
				}
				if (dir.x > 10) {
					dir.x = dir.x - 10;
				}
				if (dir.y < 10) {
					dir.y = dir.y * (-1);
				}
				if (dir.y > 10) {
					dir.y = dir.y - 10;
				}

				ex.printStackTrace();
			}
		}
	}

	// ordena las los organismos para que se vean mejor al final

	public void Ordenar() {

		if (verFrontera == false) {

			aorg.sort();

			float y = (int) (alto - 120);
			float x = 10;

			for (Organismo or : aorg) {

				or.posicion.x = -100;
				or.posicion.y = -100;

				or.Ordenar();// este metodo mueve los organismosa un nueva
								// posicion sin usar el metodo update

			}

			for (int e = indice; e < aorg.size; e++) {
				Organismo or = aorg.get(e);

				indice++;

				or.posicion.y = y;
				or.posicion.x = x;

				x = (int) (x + or.ancho * 4);

				if (x > ancho - or.ancho) {
					x = 10;
					y = y - or.alto * 6;
				}

				if (y <= 10) {
					e = aorg.size;
				} // si se llena la pantalla de organismos, se detiene el loop

				or.Ordenar();

			}

		}

		if (verFrontera == true) {

			// actualizarListasOrganismos();

			// oreganismos de la derecha

			// lado derecho

			float y = (int) (alto - 120);
			float x = (ancho / 2) + 10;

			for (int e = indiceDer; e < aorg.size; e++) {

				if (aorg.get(e).posicion.x > ancho / 2) {

					indiceDer++;

					aorg.get(e).posicion.y = y;
					aorg.get(e).posicion.x = x;

					x = (int) (x + aorg.get(e).ancho * 4);

					if (x > ancho - aorg.get(e).ancho * 4) {
						x = (ancho / 2) + 10;
						y = y - aorg.get(e).alto * 6;
					}

					if (y <= 10) {
						e = aorg.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				aorg.get(e).Ordenar();

			}

			// oreganismos de la Izquierda

			float y2 = (int) (alto - 120);
			float x2 = 10;

			for (int e = indiceIz; e < aorg.size; e++) {

				if (aorg.get(e).posicion.x < ancho / 2) {
					indiceIz++;

					aorg.get(e).posicion.y = y2;
					aorg.get(e).posicion.x = x2;

					x2 = (int) (x2 + aorg.get(e).ancho * 4);

					if (x2 > (ancho / 2) - aorg.get(e).ancho * 4) {
						x2 = 10;
						y2 = y2 - aorg.get(e).alto * 6;
					}

					if (y2 <= 10) {
						e = aorg.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				aorg.get(e).Ordenar();

			}
		}
	}

	// guara las especies que estan en un momento determinado

	// colectar mutantes o especies del momento

	public void colectorEspesies() {

		for (Organismo or : aEspesies) {
			or.cantidad = 1;
			aEspesies.removeValue(or, true);
		}

		for (Organismo or : aorg) {

			boolean igual = false;

			String id = or.nombre.toString();
			for (Organismo or2 : aEspesies) {

				if (id.equals(or2.nombre.toString())) {
					igual = true;
					or2.cantidad++;
				}

			}
			if (igual == false) {
				aEspesies.add(or);
			}

		}

		aEspesies.sort();

	}

	// colect color allele
	public void colectGenotipo(Array<Organismo> aor, Array<Genotipo> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).cantidad = 1;
				agen.removeValue(agen.get(i), true);
			}

			aor.get(0).genotipo.cantidad = 0;
			agen.add(aor.get(0).genotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;

				for (int j = 0; j < agen.size; j++) {

					if (agen.get(j).nombre.equals(aor.get(i).genotipo.nombre)) {

						igual = true;
						agen.get(j).cantidad++;
					}

				}
				if (igual == false) {
					agen.add(aor.get(i).genotipo);
				}
			}

			agen.sort();

		}
	}

	public void colectGenotipoI(Array<Organismo> aor, Array<Genotipo> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).cantidad = 1;
				agen.removeValue(agen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x < ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			aor.get(pos).genotipo.cantidad = 0;
			agen.add(aor.get(pos).genotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;
				if (aor.get(i).posicion.x < ancho / 2) {
					for (int j = 0; j < agen.size; j++) {

						if (agen.get(j).nombre
								.equals(aor.get(i).genotipo.nombre)) {

							igual = true;
							agen.get(j).cantidad++;
						}

					}
					if (igual == false) {
						agen.add(aor.get(i).genotipo);
					}
				}
			}

			agen.sort();
		}
	}

	public void colectGenotipoD(Array<Organismo> aor, Array<Genotipo> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).cantidad = 1;
				agen.removeValue(agen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x > ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			aor.get(pos).genotipo.cantidad = 0;
			agen.add(aor.get(pos).genotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;
				if (aor.get(i).posicion.x > ancho / 2) {
					for (int j = 0; j < agen.size; j++) {

						if (agen.get(j).nombre
								.equals(aor.get(i).genotipo.nombre)) {

							igual = true;
							agen.get(j).cantidad++;
						}

					}
					if (igual == false) {
						agen.add(aor.get(i).genotipo);
					}
				}
			}

			agen.sort();
		}
	}

	public void colectFenotipo(Array<Organismo> aor, Array<Fenotipo> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).cantidad = 1;
				afen.removeValue(afen.get(i), true);
			}

			aor.get(0).fenotipo.cantidad = 0;
			afen.add(aor.get(0).fenotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;

				for (int j = 0; j < afen.size; j++) {

					if (afen.get(j).nombre.equals(aor.get(i).fenotipo.nombre)) {

						igual = true;
						afen.get(j).cantidad++;
					}

				}
				if (igual == false) {
					afen.add(aor.get(i).fenotipo);
				}
			}

			afen.sort();

		}
	}

	public void colectFenotipoI(Array<Organismo> aor, Array<Fenotipo> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).cantidad = 1;
				afen.removeValue(afen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x < ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			aor.get(pos).fenotipo.cantidad = 0;
			afen.add(aor.get(pos).fenotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {
				if (aor.get(i).posicion.x < ancho / 2) {
					boolean igual = false;

					for (int j = 0; j < afen.size; j++) {

						if (afen.get(j).nombre
								.equals(aor.get(i).fenotipo.nombre)) {

							igual = true;
							afen.get(j).cantidad++;
						}

					}
					if (igual == false) {
						afen.add(aor.get(i).fenotipo);
					}
				}
			}

			afen.sort();

		}
	}

	public void colectFenotipoD(Array<Organismo> aor, Array<Fenotipo> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).cantidad = 1;
				afen.removeValue(afen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x > ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			aor.get(pos).fenotipo.cantidad = 0;
			afen.add(aor.get(pos).fenotipo);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {
				if (aor.get(i).posicion.x > ancho / 2) {
					boolean igual = false;

					for (int j = 0; j < afen.size; j++) {

						if (afen.get(j).nombre
								.equals(aor.get(i).fenotipo.nombre)) {

							igual = true;
							afen.get(j).cantidad++;
						}

					}
					if (igual == false) {
						afen.add(aor.get(i).fenotipo);
					}
				}
			}

			afen.sort();

		}
	}

	public void colectarAlelos(Array<Organismo> aor, Array<Alelo> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).cantidad = 1;
				aal.removeValue(aal.get(i), true);
			}

			for (int i = 0; i < aor.get(0).aAlelos.size; i = i + 2) {

				Alelo al = aor.get(0).aAlelos.get(i);
				al.cantidad = 0;
				aal.add(al);

			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organismo or = aor.get(i);

				for (int a = 0; a < or.aAlelos.size; a++) {

					Alelo al = or.aAlelos.get(a);
					boolean igual = false;
					for (int j = 0; j < aal.size; j++) {

						if (aal.get(j).nombre.equals(al.nombre)
								&& aal.get(j).identificador == al.identificador) {

							igual = true;
							aal.get(j).cantidad++;
						}

					}
					if (igual == false) {
						aal.add(al);
					}

				}

			}

			aal.sort();
		}
	}

	public void colectarAlelosI(Array<Organismo> aor, Array<Alelo> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).cantidad = 1;
				aal.removeValue(aal.get(i), true);
			}// limpia la lista

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x < ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			for (int i = 0; i < aor.get(pos).aAlelos.size; i = i + 2) {

				Alelo al = aor.get(pos).aAlelos.get(i);
				al.cantidad = 0;
				aal.add(al);
			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organismo or = aor.get(i);
				if (or.posicion.x < ancho / 2) {
					for (int a = 0; a < or.aAlelos.size; a++) {

						Alelo al = or.aAlelos.get(a);
						boolean igual = false;
						for (int j = 0; j < aal.size; j++) {

							if (aal.get(j).nombre.equals(al.nombre)
									&& aal.get(j).identificador == al.identificador) {

								igual = true;
								aal.get(j).cantidad++;
							}

						}
						if (igual == false) {
							aal.add(al);
						}

					}
				}

			}

			aal.sort();
		}
	}

	public void colectarAlelosD(Array<Organismo> aor, Array<Alelo> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).cantidad = 1;
				aal.removeValue(aal.get(i), true);
			}// limpia la lista

			int pos = 0;
			for (int i = 0; i < aorg.size; i++) {
				if (aorg.get(i).posicion.x > ancho / 2) {
					pos = i;
					i = aorg.size;
				}
			}

			for (int i = 0; i < aor.get(pos).aAlelos.size; i = i + 2) {

				Alelo al = aor.get(pos).aAlelos.get(i);
				al.cantidad = 0;
				aal.add(al);
			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organismo or = aor.get(i);
				if (or.posicion.x > ancho / 2) {
					for (int a = 0; a < or.aAlelos.size; a++) {

						Alelo al = or.aAlelos.get(a);
						boolean igual = false;
						for (int j = 0; j < aal.size; j++) {

							if (aal.get(j).nombre.equals(al.nombre)
									&& aal.get(j).identificador == al.identificador) {

								igual = true;
								aal.get(j).cantidad++;
							}

						}
						if (igual == false) {
							aal.add(al);
						}

					}
				}

			}

			aal.sort();
		}
	}

	// colectar todas las que aparecen

	public void colectorEspesiesTotales(Array<Organismo> aor) {

		for (Organismo or : aor) {

			boolean igual = false;
			String id = or.nombre.toString();

			for (Organismo or2 : aEspesiesTotales) {

				if (id.equals(or2.nombre.toString())) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesiesTotales.add(or);
			}

		}

	}

	public void archivarAlelos() {

		if (verFrontera == false) {

			colectarAlelos(aorg, aAlelos);

			numero = aAlelos.size;
			int cantidad = 0;

			for (Alelo al : aAlelos) {
				cantidad = cantidad + al.cantidad;
			}

			linea.replace(0, linea.length(), "");
			linea.append(addCero2().toString() + minutos2 + ":"
					+ addCero().toString() + segundos + "");

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				if (!al.nombre.equals(tx.color)
						|| !al.nombre.equals(tx.longevidad)
						|| !al.nombre.equals(tx.fidelidadADNpol)) {

					cantidad = aorg.size * 2;
				}

				if (al.nombre.equals(tx.color)
						|| al.nombre.equals(tx.longevidad)
						|| al.nombre.equals(tx.fidelidadADNpol)) {

					cantidad = cantidadHembras(aorg) * 2 + cantidadMachos(aorg);
				}

				linea.append(";"
						+ al.nombre
						+ ";"
						+ "a"
						+ al.identificador
						+ ";"
						+ format3
								.format(((float) al.cantidad / (float) cantidad) * 100)
						+ ";" + al.cantidad);

				linea.append("\n");
			}
			linea.append("\n");
			f_alelos.escribirArchivo(linea.toString());
		}

		if (verFrontera == true) {

			colectarAlelosD(aorg, aAlelos);// trabajar con los organismosdela
											// derecha
			numero = aAlelos.size;
			int cantidadD = 0;
			for (Alelo al : aAlelos) {
				cantidadD = cantidadD + al.cantidad;
			}

			linea.replace(0, linea.length(), "");

			linea.append(addCero2().toString() + minutos2 + ":"
					+ addCero().toString() + segundos + "");
			linea.append("\n" + tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				if (!al.nombre.equals(tx.color)
						|| !al.nombre.equals(tx.longevidad)
						|| !al.nombre.equals(tx.fidelidadADNpol)) {

					cantidadD = aorg.size * 2;
				}

				if (al.nombre.equals(tx.color)
						|| al.nombre.equals(tx.longevidad)
						|| al.nombre.equals(tx.fidelidadADNpol)) {

					cantidadD = cantidadHembrasD(aorg) * 2
							+ cantidadMachosD(aorg);
				}
				linea.append(";"
						+ al.nombre
						+ ";"
						+ "a"
						+ al.identificador
						+ ";"
						+ format3
								.format(((float) al.cantidad / (float) al.cantidad) * 100)
						+ ";" + al.cantidad);

				linea.append("\n");
			}
			linea.append("\n");

			colectarAlelosI(aorg, aAlelos);// trabajar con los organismosdela
											// izquierd
			numero = aAlelos.size;
			int cantidadI = 0;
			for (Alelo al : aAlelos) {
				cantidadI = cantidadI + al.cantidad;
			}

			linea.append(tx.panelIz + "\n");// trabajar con los organismos de la
											// izquierda

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				if (!al.nombre.equals(tx.color)
						|| !al.nombre.equals(tx.longevidad)
						|| !al.nombre.equals(tx.fidelidadADNpol)) {

					cantidadI = aorg.size * 2;
				}

				if (al.nombre.equals(tx.color)
						|| al.nombre.equals(tx.longevidad)
						|| al.nombre.equals(tx.fidelidadADNpol)) {

					cantidadI = cantidadHembrasI(aorg) * 2
							+ cantidadMachosI(aorg);
				}
				linea.append(";"
						+ al.nombre
						+ ";"
						+ "a"
						+ al.identificador
						+ ";"
						+ format3
								.format(((float) al.cantidad / (float) al.cantidad) * 100)
						+ ";" + al.cantidad);

				linea.append("\n");
			}
			linea.append("\n");

			f_alelos.escribirArchivo(linea.toString());
		}

	}

	public void archivarFenotipo2() {

		if (verFrontera == false) {
			colectFenotipo(aorg, aFenotipos);

			numero = aFenotipos.size;
			int cantidad = 0;

			for (Fenotipo fn : aFenotipos) {
				cantidad = cantidad + fn.cantidad;
			}

			linea.replace(0, linea.length(), "");

			for (int i = numero - 1; i >= 0; i--) {

				Fenotipo fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidad) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidad) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");
			f_fenotipos.escribirArchivo(linea.toString());
		}

		if (verFrontera == true) {

			colectFenotipoD(aorg, aFenotipos);// trabajar con los organismos de
												// la derecha
			numero = aFenotipos.size;
			int cantidadD = 0;
			for (Fenotipo fn : aFenotipos) {
				cantidadD = cantidadD + fn.cantidad;
			}

			linea.replace(0, linea.length(), "");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Fenotipo fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidadD) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidadD) * 100));
				}

				linea.append("\n");
			}

			colectFenotipoI(aorg, aFenotipos);// trabajar con los organismos de
												// la izquierda
			numero = aFenotipos.size;
			int cantidadI = 0;
			for (Fenotipo fn : aFenotipos) {
				cantidadI = cantidadI + fn.cantidad;
			}

			linea.append(tx.panelIz + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Fenotipo fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidadI) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.nombre
							+ ";"
							+ fn.cantidad
							+ ";"
							+ format.format(((float) fn.cantidad / (float) cantidadI) * 100));
				}

				linea.append("\n");
			}

			f_fenotipos.escribirArchivo(linea.toString());
		}

	}

	public void archivarGenotipo() {

		if (verFrontera == false) {

			colectGenotipo(aorg, aGenotipos);

			numero = aGenotipos.size;
			int cantidad = 0;

			for (Genotipo gn : aGenotipos) {
				cantidad = cantidad + gn.cantidad;
			}

			linea.replace(0, linea.length(), "");
			// linea.append(";"+minutos2+":"+segundos+"");

			for (int i = numero - 1; i >= 0; i--) {

				Genotipo gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidad) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidad) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");
			f_genotipos.escribirArchivo(linea.toString());
		}

		if (verFrontera == true) {

			colectGenotipoD(aorg, aGenotipos);// trabajar con los organismos de
												// la derecha
			numero = aGenotipos.size;
			int cantidadD = 0;
			for (Genotipo gn : aGenotipos) {
				cantidadD = cantidadD + gn.cantidad;
			}
			linea.replace(0, linea.length(), "");
			// linea.append(minutos2+":"+segundos+"\n");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Genotipo gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidadD) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidadD) * 100));
				}

				linea.append("\n");
			}

			colectGenotipoI(aorg, aGenotipos);// trabajar con los organismos de
												// la izquierda
			numero = aGenotipos.size;
			int cantidadI = 0;
			for (Genotipo gn : aGenotipos) {
				cantidadI = cantidadI + gn.cantidad;
			}

			linea.append(tx.panelIz + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Genotipo gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidadI) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.nombre
							+ ";"
							+ gn.cantidad
							+ ";"
							+ format.format(((float) gn.cantidad / (float) cantidadI) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");

			f_genotipos.escribirArchivo(linea.toString());
		}

	}

	public void archivarGenoma() {
		if (verFrontera == false) {

			colectarAlelos(aorg, aAlelos);

			numero = aAlelos.size;
			int cantidad = 0;

			for (Alelo al : aAlelos) {
				cantidad = cantidad + al.cantidad;
			}

			linea.replace(0, linea.length(), "");
			linea.append("\n" + tx.tiempo + ": " + addCero2().toString()
					+ minutos2 + ":" + addCero().toString() + segundos + "\n\n");

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				linea.append(al.nombre + "-a" + al.identificador + ">  "
						+ al.secuencia);

				linea.append("\n");
			}
			f_genes.escribirArchivo(linea.toString());
		}

		if (verFrontera == true) {

			colectarAlelosD(aorg, aAlelos);// trabajar con los organismosdela
											// derecha
			numero = aAlelos.size;
			int cantidadD = 0;
			for (Alelo al : aAlelos) {
				cantidadD = cantidadD + al.cantidad;
			}
			linea.replace(0, linea.length(), "");
			linea.append("\n" + tx.tiempo + ": " + addCero2().toString()
					+ minutos2 + ":" + addCero().toString() + segundos + "\n\n");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				linea.append(al.nombre + "-a" + al.identificador + ">  "
						+ al.secuencia);

				linea.append("\n");
			}

			colectarAlelosI(aorg, aAlelos);// trabajar con los organismosdela
											// izquierd
			numero = aAlelos.size;
			int cantidadI = 0;
			for (Alelo al : aAlelos) {
				cantidadI = cantidadI + al.cantidad;
			}

			linea.append(tx.panelIz + "\n");// trabajar con los organismos de la
											// izquierda

			for (int i = numero - 1; i >= 0; i--) {

				Alelo al = aAlelos.get(i);

				linea.append(al.nombre + "-a" + al.identificador + ">  "
						+ al.secuencia);

				linea.append("\n");
			}

			f_genes.escribirArchivo(linea.toString());
		}

	}

	public void archivarProteoma() {
		if (aorg.size > 0) {

			f_proteoma.escribirArchivo("" + addCero2().toString() + minutos2
					+ ":" + addCero().toString() + segundos + "\n");
			numero = aEspesies.size;
			for (int i = numero - 1; i >= 0; i--) {
				Organismo or = aEspesies.get(i);
				linea.replace(0, linea.length(), "");
				linea.append(">" + or.nombre.toString() + "\n"
						+ or.adn.colectarProteoma(this) + "\n\n");

				f_proteoma.escribirArchivo(linea.toString());
			}
			f_proteoma.escribirArchivo("\n\n");
		}
	}

	public void archivarFenotipo() {
		if (aorg.size > 0) {

			f_mutantes.escribirArchivo("" + addCero2().toString() + minutos2
					+ ":" + addCero().toString() + segundos);
			numero = aEspesies.size;
			for (int i = numero - 1; i >= 0; i--) {
				Organismo or = aEspesies.get(i);

				linea.replace(0, linea.length(), "");
				linea.append(" " + ";" + or.nombre.toString() + ";"
						+ or.cantidad);

				if (colectaralto == true) {
					linea.append("," + (or.alto + or.ancho));
				}

				if (colectSpeed == true) {
					linea.append(";" + or.speed);
				}
				if (colectColor == true) {
					linea.append(";" + or.color);
				}
				if (colectarTasaMut == true) {
					linea.append(";" + or.tasaMut);
				}
				if (colectarTemp == true) {
					linea.append(";" + or.tempOptima);
				}
				if (colectarLongevidad == true) {
					linea.append(";" + or.longevidad);
				}
				if (colectRadioconsiente == true) {
					linea.append(";" + or.radioConsiente);
				}
				if (colectarFeromona == true) {
					linea.append(";" + or.feromona);
				}
				if (colectPredador == true) {
					linea.append(";" + or.carnivoro);
				}
				if (colectSentir == true) {
					linea.append(";" + or.sentir);
				}
				if (colectCazar == true) {
					linea.append(";" + or.cazar);
				}
				if (colectEscapar == true) {
					linea.append(";" + or.escapar);
				}
				if (colectarResistencia == true) {
					linea.append(";" + or.resistenciaATB);
				}
				linea.append("\n");

				f_mutantes.escribirArchivo(linea.toString());
			}

		}
	}

	public int contarOrganismos(Array<Organismo> aor, boolean der) {

		int cuenta = 0;

		for (int i = 0; i < aor.size; i++) {
			if (der == true) {
				if (aor.get(i).posicion.x > ancho / 2) {
					cuenta = cuenta + 1;
				}
			}
			if (der == false) {
				if (aor.get(i).posicion.x < ancho / 2) {
					cuenta = cuenta + 1;
				}
			}

		}

		return cuenta;

	}

	// colecta datos para graficar
	public void guardarDatos() {
		if (verFrontera == false) {
			linea.replace(
					0,
					linea.length(),
					"" + addCero2().toString() + minutos2 + ":"
							+ addCero().toString() + segundos + ";" + aorg.size
							+ ";" + cantidadPredadores(aorg) + ";"
							+ cantidadResistentes(aorg) + ";"
							+ format.format(velocidadMedia(aorg)) + ";"
							+ format.format(tamanoMedio(aorg)) + ";"
							+ format.format(longevidadMedio(aorg)) + ";"
							+ format.format(tasaMutMedio(aorg)) + ";"
							+ format.format(temperatura) + ";"
							+ format.format(temOptimaMedia(aorg)) + "\n");
			f_datos.escribirArchivo(linea.toString());
		}

		if (verFrontera == true) {

			linea.replace(
					0,
					linea.length(),
					addCero2().toString()
							+ minutos2
							+ ":"
							+ addCero().toString()
							+ segundos
							+ ";"
							+ contarOrganismos(aorg, false)
							+ ";"
							+ contarOrganismos(aorg, true)
							+ ";"
							+ contarOrganismos(aEspesies, false)
							+ ";"
							+ contarOrganismos(aEspesies, true)
							+ ";"
							+ (contarOrganismos(aorg, false) - cantidadPredadoresI(aorg))
							+ ";"
							+ (contarOrganismos(aorg, true) - cantidadPredadoresD(aorg))
							+ ";" + cantidadPredadoresI(aorg) + ";"
							+ cantidadPredadoresD(aorg) + ";"
							+ cantidadResistentesI(aorg) + ";"
							+ cantidadResistentesD(aorg) + ";"
							+ format.format(velocidadMediaI(aorg)) + ";"
							+ format.format(velocidadMediaD(aorg)) + ";"
							+ format.format(tamanoMedioI(aorg)) + ";"
							+ format.format(tamanoMedioD(aorg)) + ";"
							+ format.format(longevidadMedioI(aorg)) + ";"
							+ format.format(longevidadMedioD(aorg)) + ";"
							+ tasaMutMedioI(aorg) + ";" + tasaMutMedioD(aorg)
							+ ";" + format.format(temperatura) + ";"
							+ format.format(temOptimaMediaI(aorg)) + ";"
							+ format.format(temOptimaMediaD(aorg)) + "\n");
			f_datos.escribirArchivo(linea.toString());
		}

	}

	public void tomarMuestras() {

		if (segundos2 >= tiempoMuestreo) {

			colectorEspesies();// colecta las especies o mutante de ese momento

			guardarDatos();
			archivarGenoma();

			archivarGenotipo();
			archivarFenotipo2();
			archivarAlelos();

			segundos2 = 0;
		}
	}

	public void activarCatastrofe() {

		if (segundos3 >= tiempoCatastrofe && tiempoCatastrofe != 0) {

			catastrofe();

			segundos3 = 0;
		}

	}

	public void activarATB() {

		if (segundos5 == tiempoATB && antibiotico == -1 && tiempoATB != 0) {

			antibiotico = 1;

		}

	}

	public void archivaTodaslasEspecies() {

		StringBuffer linea = new StringBuffer();

		for (Organismo or : aEspesiesTotales) {
			linea.replace(0, linea.length(), "");
			linea.append(">" + or.fechaNacimiento + " " + or.nombre.toString()
					+ "\n" + or.adn.colectarGenoma(this) + "\n");
			f_arbol.escribirArchivo(linea.toString());
		}

		f_arbol.cerrarArchivo();
	}

	// metodo que actualiza el sitema

	public void update() {

		if (parar == false) {
			if (tiempoMaximo == 0) {
				if (aorg.size > 0) {
					
                  if(verFrontera==true) {
						
						cantidadOrganismosI(aorg);
						cantidadOrganismosD(aorg);
						cantidadEspeciesI(aEspesies);
						cantidadEspeciesD(aEspesies);
						
					}

					for (Senergia es : ase) {
						es.update();
					}// mueve los cuantos de luz
					for (Qenergia eq : aqe) {
						eq.update();
					}// mueve los cuantos de luz
					for (Organismo or : aorg) {
						or.update();
					}// mueve los organismos
					if (segundos == 60) {
						System.gc();
					}
					detectarColiciones();
					repoduccionParteNoGen();
					chequearBalance();
					activarCatastrofe();
					activarATB();
					colectorEspesiesTotales(aorg);
					tomarMuestras();
					termociclar();
					contadorTiempo();

				}
			}

			if (tiempoMaximo > 0) {
				if (aorg.size > 0 && segundos4 < tiempoMaximo) {

					 if(verFrontera==true) {
							
							cantidadOrganismosI(aorg);
							cantidadOrganismosD(aorg);
							cantidadEspeciesI(aEspesies);
							cantidadEspeciesD(aEspesies);
							
						}
					
					for (Senergia es : ase) {
						es.update();
					}// mueve los cuantos de luz
					for (Qenergia eq : aqe) {
						eq.update();
					}// mueve los cuantos de luz
					for (Organismo or : aorg) {
						or.update();
					}// mueve los organismos
					if (segundos == 60) {
						System.gc();
					}
					detectarColiciones();
					repoduccionParteNoGen();
					chequearBalance();
					activarCatastrofe();
					activarATB();
					colectorEspesiesTotales(aorg);
					tomarMuestras();
					termociclar();
					contadorTiempo();

				}
			}
		}
	}

	// method that controls the temperature

	public double termociclar() {

		// primer control de temperatura

		if (deltaTime1 <= 0 && minStar1 != 0 && minutos2 >= minStar1) {
			temperatura = TempFinal1;
		}

		if (deltaTime1 > 0 && minStar1 != 0 && minutos2 >= minStar1
				&& minutos2 < minStar1 + deltaTime1) {

			if (delta3Time() > msecondTime(1000)) {

				temperatura = temperatura + tempXSecond1;

				setDelta3();

			}
		}

		// segundo control de temperatura

		if (deltaTime2 <= 0 && minStar2 != 0 && minutos2 >= minStar2) {
			temperatura = TempFinal2;
		}

		if (deltaTime2 > 0 && minStar2 != 0 && minutos2 >= minStar2
				&& minutos2 < minStar2 + deltaTime2) {

			if (delta3Time() > msecondTime(1000)) {

				temperatura = temperatura + tempXSecond2;

				setDelta3();

			}
		}

		return temperatura;

	}

	// metodos que manejan el tiempo de corrida

	public void contadorTiempo() {

		if (deltaTime() > msecondTime(1000)) {

			segundos = segundos + 1;
			segundos2 = segundos2 + 1;
			segundos3 = segundos3 + 1;
			segundos4 = segundos4 + 1;
			segundos5 = segundos5 + 1;

			if (segundos == 60) {
				segundos = 0;
				minutos = minutos + 1;
				minutos2 = minutos2 + 1;
				minutos3 = minutos3 + 1;
			}

			if (minutos == 60) {
				minutos = 0;
				horas = horas + 1;
			}

			if (horas == 24) {
				horas = 0;
				dias = dias + 1;
			}

			setDelta();
		}
	}

	// agregar cero antes de los segundos menores a 9

	public StringBuffer addCero() {

		ceroIz.replace(0, ceroIz.length(), "");

		if (segundos < 10) {
			ceroIz.append("0");
		}
		;

		return ceroIz;
	}

	public StringBuffer addCero2() {

		ceroIz2.replace(0, ceroIz2.length(), "");

		if (minutos2 < 10) {
			ceroIz2.append("0");
		}
		;

		return ceroIz2;
	}

	// mide el tiempo transcurrido desde el unltimo set

	public long deltaTime() {
		return TimeUtils.nanoTime() - delta;
	}

	public long delta2Time() {
		return TimeUtils.nanoTime() - delta2;
	}

	public long delta3Time() {
		return TimeUtils.nanoTime() - delta3;
	}

	public long tiemoTime() {
		return TimeUtils.nanoTime() - tiempo;
	}

	// anota el tiempo de juego transcurrido en el momento que se invoca un
	// evento

	public void setDelta() {
		delta = TimeUtils.nanoTime();
	}

	public void setDelta2() {
		delta2 = TimeUtils.nanoTime();
	}

	public void setDelta3() {
		delta3 = TimeUtils.nanoTime();
	}

	public void setTiempo() {
		tiempo = TimeUtils.nanoTime();
	}

	// convierte de ms a nanosegundos para mas comodidad
	public long msecondTime(long ms) {

		return ms * 1000000;
	}

	public void detectarColiciones() {

		// organismo toca la energia verde

		for (int i = 0; i < aorg.size; i++) {
			Organismo or = aorg.get(i);
			Rectangle er = or.borde;

			if (or.carnivoro == false && or.muriendo == 0) { // el organismo no
																// es carnivoro

				// chequea la posicion del organismo para ver que cuantos de
				// energia buscar

				if (or.posicion.x <= ancho * (1 / 10)) {

					for (int a = 0; a < ase1.size; a++) {
						Senergia se = ase1.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (1 / 10)
						&& or.posicion.x <= ancho * (2 / 10)) {

					for (int a = 0; a < ase2.size; a++) {
						Senergia se = ase2.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (2 / 10)
						&& or.posicion.x <= ancho * (3 / 10)) {

					for (int a = 0; a < ase3.size; a++) {
						Senergia se = ase3.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (3 / 10)
						&& or.posicion.x <= ancho * (4 / 10)) {

					for (int a = 0; a < ase4.size; a++) {
						Senergia se = ase4.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (4 / 10)
						&& or.posicion.x <= ancho * (5 / 10)) {

					for (int a = 0; a < ase5.size; a++) {
						Senergia se = ase5.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (5 / 10)
						&& or.posicion.x <= ancho * (6 / 10)) {

					for (int a = 0; a < ase6.size; a++) {
						Senergia se = ase6.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (6 / 10)
						&& or.posicion.x <= ancho * (7 / 10)) {

					for (int a = 0; a < ase7.size; a++) {
						Senergia se = ase7.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (7 / 10)
						&& or.posicion.x <= ancho * (8 / 10)) {

					for (int a = 0; a < ase8.size; a++) {
						Senergia se = ase8.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (8 / 10)
						&& or.posicion.x <= ancho * (9 / 10)) {

					for (int a = 0; a < ase9.size; a++) {
						Senergia se = ase9.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

				if (or.posicion.x > ancho * (9 / 10) && or.posicion.x <= ancho) {

					for (int a = 0; a < ase10.size; a++) {
						Senergia se = ase10.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energia < or.capacidad) {
								double delta = or.capacidad - or.energia;
								or.energia = or.energia + se.energia;
								se.energia = se.energia - delta;
								if (se.energia <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energia > or.capacidad) {
							or.energia = or.capacidad;
						}
					}
				}

			}
		}

		// organismo toca la biomasa

		for (int i = 0; i < aorg.size; i++) {
			Organismo or = aorg.get(i);
			Rectangle er = or.borde;

			if (or.carnivoro == false && or.muriendo == 0) {

				// chequea la posicion del organismo para ver que cuantos de
				// energia buscar

				if (or.posicion.x <= ancho * (1 / 10)) {
					for (int a = 0; a < aqe1.size; a++) {
						Qenergia qe = aqe1.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (1 / 10)
						&& or.posicion.x <= ancho * (2 / 10)) {
					for (int a = 0; a < aqe2.size; a++) {
						Qenergia qe = aqe2.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (2 / 10)
						&& or.posicion.x <= ancho * (3 / 10)) {
					for (int a = 0; a < aqe3.size; a++) {
						Qenergia qe = aqe3.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (3 / 10)
						&& or.posicion.x <= ancho * (4 / 10)) {
					for (int a = 0; a < aqe4.size; a++) {
						Qenergia qe = aqe4.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (4 / 10)
						&& or.posicion.x <= ancho * (5 / 10)) {
					for (int a = 0; a < aqe5.size; a++) {
						Qenergia qe = aqe5.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (5 / 10)
						&& or.posicion.x <= ancho * (6 / 10)) {
					for (int a = 0; a < aqe6.size; a++) {
						Qenergia qe = aqe6.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (6 / 10)
						&& or.posicion.x <= ancho * (7 / 10)) {
					for (int a = 0; a < aqe7.size; a++) {
						Qenergia qe = aqe7.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (7 / 10)
						&& or.posicion.x <= ancho * (8 / 10)) {
					for (int a = 0; a < aqe8.size; a++) {
						Qenergia qe = aqe8.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (8 / 10)
						&& or.posicion.x <= ancho * (9 / 10)) {
					for (int a = 0; a < aqe9.size; a++) {
						Qenergia qe = aqe9.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.posicion.x > ancho * (9 / 10) && or.posicion.x <= ancho) {
					for (int a = 0; a < aqe10.size; a++) {
						Qenergia qe = aqe10.get(a);
						Rectangle tr = qe.borde;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomasa < or.capacidad) {
									or.biomasa = (int) (or.biomasa + qe.masa);
									qe.visible = false;
								}
							}
						}
					}
				}

			}
		}

		// Organismo toca organismo

		for (int x = 0; x < aorg.size; x++) {

			Organismo or = aorg.get(x);
			Rectangle er = or.borde;

			// Fecundacion
			if (or.segundos >= or.tiempoMitosis / 1000 && or.male == false
					&& or.parteNoGen == false) {// Si es mayor de edad, hembra y
												// se reproduce sexualmente

				for (int a = x; a < aorg.size; a++) { // Se busca pareja
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (or2.segundos >= or2.tiempoMitosis / 1000
							&& or2.male == true) { // o2 es macho

						if (er.overlaps(er2)) { // Se tocan?
							if (a != x) {

								or.Fecundation(or, or2); // Se produce la
															// fecundacion
								a = aorg.size; // No busca más machos

							}
						}
					}
				}
			}

			// Fecundacion partenogenesis con Cortejo

			if (or.segundos >= or.tiempoMitosis / 1000 && or.male == false
					&& or.parteNoGen == true) {// Si es mayor de edad, hembra y
												// se reproduce asexualmente

				for (int a = x; a < aorg.size; a++) { // Se busca pareja
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (or2.segundos >= or2.tiempoMitosis / 1000
							&& or2.male == true) { // o2 es macho

						if (er.overlaps(er2)) { // Se tocan?
							if (a != x) {

								or.ReproduccionParteNoGen(or); // se reproduce
																// asexualmente
																// y listo
								a = aorg.size; // No busca más machos

							}
						}
					}
				}
			}
			
			
			//infectado
			
		/*	if(or.infectado== true) {//el organismo esta infactado
				
				for (int a = 0; a < aorg.size; a++) { // Se busca una un sano
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (or2.infectado == false) { // or2 es otro organismo y no esta infectado

						if (er.overlaps(er2)) { //Se tocan ?
							
						float random = (float) (Math.random()*1000);//
						
				 		if(random < 1000*(float) (virus.contagio/100) ) {//chanse de contagiarse por contacto
					 			 		
				 		if(or2.inmune=false){ or2.infectado = true; }//el organismo se enferma
				 		
				 		if(or2.inmune=true) { 
				 			float random2 = (float) (Math.random()*100);//si supera la proteccion de la inmunidad
				 			
				 			if (random2> virus.vacuna) { or2.infectado = true; }//el organismo se infecta
				 		}
				 		
				 		}}

					}
				}
			}*/
				
			
			
			
			// Carnivoros

			if (or.carnivoro == true && or.muriendo == 0) { // El organismo es
															// carnivoro

				for (int a = 0; a < aorg.size; a++) { // Se busca una presa
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (or.identificador != or2.identificador
							&& or.capacidad >= or2.capacidad) { // La presa es
																// otro
																// organismo

						if (er.overlaps(er2)) { // Se tocan ?
							if (a != x) {

								// intercamvio de masa y energia
								EnRe = (int) (or.capacidad - or.energia);
								BioRe = (int) (or.capacidad - or.biomasa);

								if (EnRe >= or2.energia && EnRe > 0) {
									or.energia = or.energia + or2.energia;
									or2.energia = 0;
								}
								if (EnRe < or2.energia && EnRe > 0) {
									or.energia = or.energia + EnRe;
									or2.energia = or2.energia - EnRe;
								}

								if (BioRe >= or2.biomasa && BioRe > 0) {
									or.biomasa = or.biomasa + or2.biomasa;
									or2.biomasa = 0;
								}
								if (BioRe < or2.biomasa && BioRe > 0) {
									or.biomasa = or.biomasa + BioRe;
									or2.biomasa = or2.biomasa - BioRe;
								}

								if (or2.energia <= 0) {
									or2.morir();
								} // muerte de la presa

							}
						}

					}
				}
			}

		}

		// organismo toca la barrera

		// organismo toca la barrera

		if (verFrontera == true) {

			for (int x = 0; x < aorg.size; x++) {

				Organismo or = aorg.get(x);
				Rectangle er = or.borde;
				if (er.overlaps(frontera)) {
					if (or.posicion.x < (ancho/2-or.ancho) || or.posicion.x < ancho/2 && or.direccion.x > 0) {
						or.posicion.x = 0;
						//or.direccion.x = -1;
					}

					if (or.posicion.x > (ancho/2+or.ancho*2) || or.posicion.x > ancho/2 && or.direccion.x < 0) {
						or.posicion.x = ancho;
						//or.direccion.x = 1;
					}

				}
			}
		}
	}

	// reproduccion partenogenetica Sin cortejo
	public void repoduccionParteNoGen() {

		// reproduccion partenogenetica

		for (int x = 0; x < aorg.size; x++) {

			Organismo or = aorg.get(x);

			if (or.segundos >= or.tiempoMitosis / 1000 && or.tiempoCiclo >= 10
					&& or.male == false && or.parteNoGen == true) {// Si es
																	// mayor de
																	// edad,
																	// hembra y
																	// se
																	// reproduce
																	// asexualmente

				or.ReproduccionParteNoGenSinCortejo(or); // se reproduce
															// asexualmente y
															// listo

			}
		}
	}

	// Horizontal transfer
	
	public void contagio(Organismo or1, Organismo or2) {
		
		
		
	}

	public void horizontalTransfer(Organismo or1, Organismo or2) {

		if (horizontalTransferRate > 0) {
			int random = (int) (Math.random() * horizontalTransferRate);//
			// int pos = (int) (Math.random()*100);
			int gentipe = (int) (Math.random() * 12);

			if (random == 0) {

				switch (gentipe) {
				case 0:
					or1.adn.alto.insert(0, or2.adn.alto);

					break;
				case 1:
					or1.adn.ancho.insert(0, or2.adn.ancho);

					break;

				case 2:
					or1.adn.color.insert(0, or2.adn.color);

					break;

				case 3:
					or1.adn.speed.insert(0, or2.adn.speed);

					break;

				case 4:
					or1.adn.predador.insert(0, or2.adn.predador);

					break;

				case 5:
					or1.adn.sentir.insert(0, or2.adn.sentir);

					break;

				case 6:
					or1.adn.cazar.insert(0, or2.adn.cazar);

					break;

				case 7:
					or1.adn.escapar.insert(0, or2.adn.escapar);

					break;

				case 8:
					or1.adn.radioConsiente.insert(0, or2.adn.radioConsiente);

					break;

				case 9:
					or1.adn.tasaMutacion.insert(0, or2.adn.tasaMutacion);

					break;

				case 10:
					or1.adn.longevidad.insert(0, or2.adn.longevidad);

					break;

				case 11:
					or1.adn.toleranciaTemp.insert(0, or2.adn.toleranciaTemp);

					break;

				case 12:
					or1.adn.resistenciaATB.insert(0, or2.adn.resistenciaATB);

					break;

				}

				or1.transferred = true;
				or1.translate();

			}
		}
	}

	public void dispose() {

		// textuRA_ENER.dispose();
		// textura_ORG.dispose();
		// auraATB.dispose();
		// transferido.dispose();
		// textura_sex.dispose();
		// textura_feromona.dispose();
		textura_organismos.dispose();
	}

}