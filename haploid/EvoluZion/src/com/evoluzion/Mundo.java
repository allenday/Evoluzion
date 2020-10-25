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
	Array<Senergia> ase, ase1, ase2, ase3, ase4, ase5, ase6, ase7, ase8, ase9,
			ase10; // lista de energia verde
	Array<Qenergia> aqe, aqe1, aqe2, aqe3, aqe4, aqe5, aqe6, aqe7, aqe8, aqe9,
			aqe10; // lista de energia roja (biomasa)
	Array<Organismo> aorg; // lista de organismos
	Array<Organismo> aEspesies, aEspesiesTotales; // listas de organismos
													// colectados segun especies

	float ancho, alto, ratio; // dimenciones de la pantalla
	//
	float deltaX = 0; // delta de x entre dos individuos
	float deltaY = 0; // delta de y entre dos individuos
	float medSpeed; // se usa para el calcula de velosidad media
	float temperatura = 25;
	float medTem;
	float eficiencia = 1;
	// suma de toda la masa libre

	int dias, horas, minutos, minutos2, minutos3, segundos, segundos2,
			segundos3, segundos4, segundos5, segundos6; // anota el paso del
														// tiempo
	long edad, delta, delta2, delta3, tiempo; // mide diferencia de tiempo entre
												// una accion y la siguiente
	int tiempoMaximo = 0;
	int Masatotal, MasatotalL, MasatotalR;
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
	int numero = 0; // es usado por los metodos que usan calculos
	int numeroI = 0; // es usado por los metodos que usan calculos
	int numeroD = 0; // es usado por los metodos que usan calculos
	int cantidad = 0; // es usado por los metodos que usan calculos
	int numEspI = 0; // es usado por los metodos que usan calculos
	int numEspD = 0; // es usado por los metodos que usan calculos

	float minStar1, minStar2;
	float TempFinal1, TempFinal2, tempXSecond1, tempXSecond2;
	float deltaTime1, deltaTime2;

	int antibiotico = -1; // 1= true -1= false

	int horizontalTransferRate;

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

	boolean cargarPoblacion = false;
	boolean moverMasa;
	boolean verFrontera;
	boolean parar = false;

	String ruta, poblacion;
	StringBuffer linea, orgNombre; // se usa para archivar los resultado
	StringBuffer ceroIz, ceroIz2; // agrega un cero a la izquierda
	String nombre;

	TextureAtlas textuRA_ENER, textura_ORG, textura_organismos; // contine las
																// imagenes
	Texture auraATB, transferido;
	Archivar f_datos, f_genes, f_arbol, f_proteoma, f_poblacion, f_mutantes; // para
																				// archivar
	NumberFormat format = new DecimalFormat("00.000");
	// private Quadtree quad;

	Rectangle frontera;

	public Mundo(Evoluzion ev, String ruta, String nombre, String poblacion,
			int numOrg, int numSenL, int numQenL, int SenergiaL, int QbiomasaL,
			int numSenR, int numQenR, int SenergiaR, int QbiomasaR,
			boolean cargarPoblacion, boolean moverMasa, boolean verFrontera,
			String genesPedidos, int ingles) {

		this.ev = ev;

		this.numOrg = numOrg;
		this.numSen = numSenL;
		this.numQen = numQenL;
		this.Senergia = SenergiaL;
		this.Qbiomasa = QbiomasaL;

		this.numSenR = numSenR;
		this.numQenR = numQenR;
		this.SenergiaR = SenergiaR;
		this.QbiomasaR = QbiomasaR;
		this.cargarPoblacion = cargarPoblacion;
		this.moverMasa = moverMasa;
		this.verFrontera = verFrontera;
		this.cargarPoblacion = cargarPoblacion;
		this.moverMasa = moverMasa;
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

		// set time to 0
		setDelta();
		setDelta2();
		setDelta3();
		setTiempo();

		linea = new StringBuffer();// used to write text in files

		// textuRA_ENER = new TextureAtlas("data/energia.pack");
		// textura_ORG = new TextureAtlas("data/objetos.pack");
		textura_organismos = new TextureAtlas("data/organismo1.pack");
		// auraATB = new Texture("data/auraATB.png");
		// transferido= new Texture("data/Transferido.png");

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
			// System.out.println("Hola");
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

		ase1.shrink();
		ase2.shrink();
		ase3.shrink();
		ase4.shrink();
		ase5.shrink();
		ase6.shrink();
		ase7.shrink();
		ase8.shrink();
		ase9.shrink();
		ase10.shrink();

		// System.out.println(" ase1: "+ ase1.size+"\n" + " ase2: "+
		// ase2.size+"\n" +" ase3: "+ ase3.size+"\n"+ " ase4: "+
		// ase4.size+"\n");

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

		// aqe.shuffle();
		aqe.shrink();

		// repartirla materia izquierda y derecha
		numero = aqe.size;

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

		numero = aorg.size;

		for (int i = numero - 1; i >= 0; i--) {
			Organismo or = aorg.get(i);

			boolean igual = false;

			String id = or.identificador;

			numero = aEspesies.size;

			for (int ii = numero - 1; ii >= 0; ii--) {

				Organismo or2 = aEspesies.get(ii);

				if (id.equals(or2.nombre)) {
					igual = true;
				}

			}

			if (igual == false) {
				aEspesies.add(or);
			}

		}

		numero = aorg.size;

		for (int i = numero - 1; i >= 0; i--) {

			Organismo or = aorg.get(i);

			boolean igual = false;
			String id = or.identificador;

			numero = aEspesiesTotales.size;

			for (int ii = numero - 1; ii >= 0; ii--) {

				Organismo or2 = aEspesiesTotales.get(ii);

				if (id.equals(or2.nombre)) {
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

	}

	// end of cronstructor metod

	// metods

	public void agregarPrimerosOrg(int num) {

		for (int i = 0; i < num; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho,
					(float) Math.random() * alto);

			aorg.add(new Organismo(new Genoma(), pos, orgNombre, this));

		}
		int o = BiomasaTotal(aorg) / Qbiomasa;
		// System.out.println(BiomasaTotal());
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
		
	//System.out.println(numeroI);	
	}

	// methods for quantifying values ​​or do Statistics

	public void borrarOrganismo(Organismo or) {

		aorg.removeValue(or, true);

		or = null;

		aorg.shrink();
		// try { or.finalize();} catch (Throwable e) { e.printStackTrace();}

	}
	
	//calculos 
	
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

			if (aor.get(i).posicion.x < ancho / 2) {
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
	

	public float temOptimaMedia(Array<Organismo> aor) {
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

	public float temOptimaMediaI(Array<Organismo> aor) {
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
     // System.out.println("T "+ medTem + "N "+ numeroI);
		return medTem / numeroI;
		
		
	}

	public float temOptimaMediaD(Array<Organismo> aor) {
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

	public float velocidadMedia(Array<Organismo> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medSpeed = medSpeed + aor.get(i).speed;
			}
		}
		if (numero == 0) {
			numero = 1;
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
		if (numero == 0) {
			numero = 1;
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
		if (numero == 0) {
			numero = 1;
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
		if (numero == 0) {
			numero = 1;
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
	
	//calculo de la materia

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


	

	public int cantidadPredadores(Array<Organismo> aor) {
		int carnivoros = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).carnivoro == true) {
				carnivoros = carnivoros + 1;
			}
		}
		return carnivoros;
	}

	public int cantidadPredadoresI(Array<Organismo> aor) {
		int carnivoros = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x < ancho / 2
					&& aor.get(i).carnivoro == true) {
				carnivoros = carnivoros + 1;
			}
		}
		return carnivoros;
	}

	public int cantidadPredadoresD(Array<Organismo> aor) {
		int carnivoros = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x > ancho / 2
					&& aor.get(i).carnivoro == true) {
				carnivoros = carnivoros + 1;
			}
		}
		return carnivoros;
	}

	public int cantidadResistentes(Array<Organismo> aor) {
		int resistente = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).resistenciaATB == true) {
				resistente = resistente + 1;
			}
		}
		return resistente;
	}

	public int cantidadResistentesI(Array<Organismo> aor) {
		int resistente = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x < ancho / 2
					&& aor.get(i).resistenciaATB == true) {
				resistente = resistente + 1;
			}
		}
		return resistente;
	}

	public int cantidadResistentesD(Array<Organismo> aor) {
		int resistente = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).posicion.x > ancho / 2
					&& aor.get(i).resistenciaATB == true) {
				resistente = resistente + 1;
			}
		}
		return resistente;
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

	public int BiomasaTotal(Array<Organismo> aor) {
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

	public void chequerBalance() {

		if (verFrontera == false) {

			int a = Masatotal - (MateriaLibre() + BiomasaTotal(aorg));

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
			int a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(aorg));
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

			int b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(aorg));

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
					"población Total" + "     (pob)", "pob");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						linea.replace(0, linea.length(),
								"<h>" + or.nombre.toString() + "dX"
										+ or.posicion.x + "dY" + or.posicion.y
										+ "<color>" + or.adn.color + "<ancho>"
										+ or.adn.ancho + "<alto>" + or.adn.alto
										+ "<speed>" + or.adn.speed + "<temp>"
										+ or.adn.toleranciaTemp + "<sentir>"
										+ or.adn.sentir + "<alcance>"
										+ or.adn.radioConsiente + "<cazar>"
										+ or.adn.cazar + "<escapar>"
										+ or.adn.escapar + "<predador>"
										+ or.adn.predador + "<longevidad>"
										+ or.adn.longevidad + "<tasamut>"
										+ or.adn.tasaMutacion + "<atb>"
										+ or.adn.resistenciaATB + "\n");
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
					"población Marcada" + "     (pob)", "pob");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						if (or.marcado == -1) { // -1 == true
							linea.replace(0, linea.length(),
									"<h>" + or.nombre.toString() + "dX"
											+ or.posicion.x + "dY"
											+ or.posicion.y + "<color>"
											+ or.adn.color + "<ancho>"
											+ or.adn.ancho + "<alto>"
											+ or.adn.alto + "<speed>"
											+ or.adn.speed + "<temp>"
											+ or.adn.toleranciaTemp
											+ "<sentir>" + or.adn.sentir
											+ "<alcance>"
											+ or.adn.radioConsiente + "<cazar>"
											+ or.adn.cazar + "<escapar>"
											+ or.adn.escapar + "<predador>"
											+ or.adn.predador + "<longevidad>"
											+ or.adn.longevidad + "<tasamut>"
											+ or.adn.tasaMutacion + "<atb>"
											+ or.adn.resistenciaATB + "\n");
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
					"población No Marcada" + "     (pob)", "pob");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.creararchivo(fc.getSelectedFile() + ".pob");

					StringBuffer linea = new StringBuffer();

					numero = aorg.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organismo or = aorg.get(i);
						if (or.marcado == 1) { // 1 = false
							linea.replace(0, linea.length(),
									"<h>" + or.nombre.toString() + "dX"
											+ or.posicion.x + "dY"
											+ or.posicion.y + "<color>"
											+ or.adn.color + "<ancho>"
											+ or.adn.ancho + "<alto>"
											+ or.adn.alto + "<speed>"
											+ or.adn.speed + "<temp>"
											+ or.adn.toleranciaTemp
											+ "<sentir>" + or.adn.sentir
											+ "<alcance>"
											+ or.adn.radioConsiente + "<cazar>"
											+ or.adn.cazar + "<escapar>"
											+ or.adn.escapar + "<predador>"
											+ or.adn.predador + "<longevidad>"
											+ or.adn.longevidad + "<tasamut>"
											+ or.adn.tasaMutacion + "<atb>"
											+ or.adn.resistenciaATB + "\n");
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
		numero = aorg.size;
		for (int i = numero - 1; i >= 0; i--) {
			aorg.removeIndex(i);
		}
		int numero2 = aEspesiesTotales.size;
		for (int i = numero2 - 1; i >= 0; i--) {
			aEspesiesTotales.removeIndex(i);
		}

		try {

			FileReader fr = new FileReader(poblacion);
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				Genoma gen = new Genoma();

				gen.color = new StringBuffer(linea.substring(
						linea.indexOf("<color>") + 7, linea.indexOf("<ancho>")));
				gen.ancho = new StringBuffer(linea.substring(
						linea.indexOf("<ancho>") + 7, linea.indexOf("<alto>")));
				gen.alto = new StringBuffer(linea.substring(
						linea.indexOf("<alto>") + 6, linea.indexOf("<speed>")));
				gen.speed = new StringBuffer(linea.substring(
						linea.indexOf("<speed>") + 7, linea.indexOf("<temp>")));
				gen.toleranciaTemp = new StringBuffer(linea.substring(
						linea.indexOf("<temp>") + 6, linea.indexOf("<sentir>")));
				gen.sentir = new StringBuffer(linea.substring(
						linea.indexOf("<sentir>") + 8,
						linea.indexOf("<alcance>")));
				gen.radioConsiente = new StringBuffer(linea.substring(
						linea.indexOf("<alcance>") + 9,
						linea.indexOf("<cazar>")));
				gen.cazar = new StringBuffer(linea.substring(
						linea.indexOf("<cazar>") + 7,
						linea.indexOf("<escapar>")));
				gen.escapar = new StringBuffer(linea.substring(
						linea.indexOf("<escapar>") + 9,
						linea.indexOf("<predador>")));
				gen.predador = new StringBuffer(linea.substring(
						linea.indexOf("<predador>") + 10,
						linea.indexOf("<longevidad>")));
				gen.longevidad = new StringBuffer(linea.substring(
						linea.indexOf("<longevidad>") + 12,
						linea.indexOf("<tasamut>")));
				gen.tasaMutacion = new StringBuffer(linea.substring(
						linea.indexOf("<tasamut>") + 9, linea.indexOf("<atb>")));
				gen.resistenciaATB = new StringBuffer(linea.substring(
						linea.indexOf("<atb>") + 5, linea.length()));
				Vector2 pos = new Vector2(Float.parseFloat(linea.substring(
						linea.indexOf("dX") + 2, linea.indexOf("dY"))),
						Float.parseFloat(linea.substring(
								linea.indexOf("dY") + 2,
								linea.indexOf("<color>"))));
				StringBuffer nombre = new StringBuffer(linea.substring(
						linea.indexOf("<h>") + 3, linea.indexOf("dX")));

				Organismo or = new Organismo(gen, pos, nombre, this);
				// asiganr una direccion a cada organismo

				aorg.add(or);
			}

			// if(verFrontera==false){
			// int o = BiomasaTotal(aorg)/Qbiomasa;
			//
			// for (int i =0; i < o; i++){
			//
			// Qenergia qe = aqe.get(i);
			// qe.visible=false;
			//
			// }}
			//
			// if(verFrontera==true){
			// int a = BiomasaTotal(aorgIz)/Qbiomasa;
			//
			// for (int i =0; i < a; i++){
			//
			// Qenergia qe = aqeIz.get(i);
			// qe.visible=false;
			//
			// }
			// int e = BiomasaTotal(aorgDer)/QbiomasaR;
			//
			// for (int i =0; i < e; i++){
			//
			// Qenergia qe = aqeDer.get(i);
			// qe.visible=false;
			//
			// }
			//
			// }
			//
			Masatotal = MateriaLibre() + BiomasaTotal(aorg);

			MasatotalL = MateriaLibreL() + BiomasaTotalI(aorg);

			MasatotalR = MateriaLibreR() + BiomasaTotalD(aorg);

			br.close();
			fr.close();

		} catch (IOException ex) {

			JOptionPane
					.showMessageDialog(null, "no se puede leer este archivo");

			for (int i = 0; i < numOrg; i++) {

				Vector2 pos = new Vector2((float) Math.random() * ancho,
						(float) Math.random() * alto);

				aorg.add(new Organismo(new Genoma(), pos, orgNombre, this));
			}

			ex.printStackTrace();
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

				x = (int) (x + or.ancho * 2);

				if (x > ancho - or.ancho * 2) {
					x = 10;
					y = y - or.alto * 3;
				}

				if (y <= 10) {
					e = aorg.size;
				} // si se llena la pantalla de organismos, se detiene el loop

				or.Ordenar();

				// System.out.println(indice);

			}
			// if (indice==aorg.size){indice=0;}//si se llega al final de la
			// lista se vuelve a empezar
		}

		if (verFrontera == true) {

			// actualizarListasOrganismos();

			// oreganismos de la derecha

			// lado derecho

			float y = (int) (alto - 120);
			float x = (ancho / 2) + 10;

			// for(Organismo or:aorg){

			// if (or.posicion.x>ancho/2){
			// or.posicion.x=-100;
			// or.posicion.y=-100;}

			// or.Ordenar();//este metodo mueve los organismosa un nueva
			// posicion sin usar el metodo update

			// }

			for (int e = indiceDer; e < aorg.size; e++) {

				if (aorg.get(e).posicion.x > ancho / 2) {

					indiceDer++;

					aorg.get(e).posicion.y = y;
					aorg.get(e).posicion.x = x;

					x = (int) (x + aorg.get(e).ancho * 2);

					if (x > ancho - aorg.get(e).ancho * 2) {
						x = (ancho / 2) + 10;
						y = y - aorg.get(e).alto * 3;
					}

					if (y <= 10) {
						e = aorg.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				aorg.get(e).Ordenar();

			}

			// oreganismos de la Izquierda

			// aorg.sort();

			float y2 = (int) (alto - 120);
			float x2 = 10;

			// for(Organismo or:aorg){
			// if (or.posicion.x<ancho/2){
			// or.posicion.x=-100;
			// or.posicion.y=-100;}

			// or.Ordenar();//este metodo mueve los organismosa un nueva
			// posicion sin usar el metodo update

			// }

			for (int e = indiceIz; e < aorg.size; e++) {

				if (aorg.get(e).posicion.x < ancho / 2) {
					indiceIz++;

					aorg.get(e).posicion.y = y2;
					aorg.get(e).posicion.x = x2;

					x2 = (int) (x2 + aorg.get(e).ancho * 2);

					if (x2 > (ancho / 2) - aorg.get(e).ancho * 2) {
						x2 = 10;
						y2 = y2 - aorg.get(e).alto * 3;
					}

					if (y2 <= 10) {
						e = aorg.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				aorg.get(e).Ordenar();

				// System.out.println(indiceIz);

			}

		}

	}

	// guara las especies que estan en un momento determinado

	// colectar mutantes o especies del momento

	public void colectorEspesies(Array<Organismo> aor, Array<Organismo> esp) {

		int index = esp.size;
		for (int i = index - 1; i >= 0; i--) {
			esp.get(i).cantidad = 1;
			esp.removeValue(esp.get(i), true);
		}

		for (Organismo or : aor) {

			boolean igual = false;

			String id = or.nombre.toString();
			for (Organismo or2 : esp) {

				if (id.equals(or2.nombre.toString())) {
					igual = true;
					or2.cantidad++;
				}

			}
			if (igual == false) {
				esp.add(or);
			}

		}

		esp.sort();
		esp.shrink();

	}

	public void colectorEspesiesI(Array<Organismo> aor, Array<Organismo> esp) {

		int index = esp.size;
		for (int i = index - 1; i >= 0; i--) {
			esp.get(i).cantidad = 1;
			esp.removeValue(esp.get(i), true);
		}

		for (Organismo or : aor) {
			if (or.posicion.x < ancho / 2) {
				boolean igual = false;

				String id = or.nombre.toString();
				for (Organismo or2 : esp) {

					if (or2.posicion.x < ancho / 2
							&& id.equals(or2.nombre.toString())) {
						igual = true;
						or2.cantidad++;
					}

				}
				if (igual == false) {
					esp.add(or);
				}

			}
		}

		esp.sort();
		esp.shrink();

	}

	public void colectorEspesiesD(Array<Organismo> aor, Array<Organismo> esp) {

		int index = esp.size;
		for (int i = index - 1; i >= 0; i--) {
			esp.get(i).cantidad = 1;
			esp.removeValue(esp.get(i), true);
		}

		for (Organismo or : aor) {
			if (or.posicion.x > ancho / 2) {
				boolean igual = false;

				String id = or.nombre.toString();
				for (Organismo or2 : esp) {

					if (or2.posicion.x > ancho / 2
							&& id.equals(or2.nombre.toString())) {
						igual = true;
						or2.cantidad++;
					}

				}
				if (igual == false) {
					esp.add(or);
				}

			}
		}

		esp.sort();
		esp.shrink();

	}

	// colectar todas las que aparesen

	public void colectorEspesiesTotales() {

		numero = aorg.size;
		for (int i = numero - 1; i >= 0; i--) {
			Organismo or = aorg.get(i);

			boolean igual = false;
			String id = or.nombre.toString();
			cantidad = aEspesiesTotales.size;
			for (int ii = cantidad - 1; ii >= 0; ii--) {

				Organismo or2 = aEspesiesTotales.get(ii);

				if (id.equals(or2.nombre.toString())) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesiesTotales.add(or);
			}

		}
		aEspesiesTotales.shrink();

	}

	public StringBuffer timeRep() {

		StringBuffer linea = new StringBuffer();

		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + ";");
		linea.append(addCero2().toString() + minutos2 + ":"
				+ addCero().toString() + segundos + "");
		linea.append("\n");
		return linea;

	}

	public StringBuffer nombreRep(Organismo or) {

		StringBuffer linea = new StringBuffer();

		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre + ";");
		linea.append(">" + or.nombre);
		linea.append("\n");

		return linea;

	}

	public void archivarGenoma() {
		if (verFrontera == false) {
			if (aorg.size > 0) {

				f_genes.escribirArchivo("\n" + tx.tiempo + "(min:seg): "
						+ timeRep().toString());
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					Organismo or = aEspesies.get(i);

					f_genes.escribirArchivo(nombreRep(or).toString() + ""
							+ or.adn.colectarGenoma(this) + "\n");

				}
				f_genes.escribirArchivo("\n");
			}
		}

		if (verFrontera == true) {

			if (aorg.size > 0) {

				// colectorEspesies(aorgIz,aEspesiesIz);

				f_genes.escribirArchivo("\n" + tx.tiempo + "(min:seg): "
						+ timeRep().toString());

				f_genes.escribirArchivo("\n" + tx.panelIz + "\n");

				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x < ancho / 2) {

						f_genes.escribirArchivo(nombreRep(aEspesies.get(i))
								.toString()
								+ ""
								+ aEspesies.get(i).adn.colectarGenoma(this)
								+ "\n");
					}

				}

			}
			if (aorg.size > 0) {

				// colectorEspesies(aorgDer,aEspesiesDer);

				f_genes.escribirArchivo("\n" + tx.panelDer + "\n");
				linea.replace(0, linea.length(), "");
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x > ancho / 2) {

						f_genes.escribirArchivo(nombreRep(aEspesies.get(i))
								.toString()
								+ ""
								+ aEspesies.get(i).adn.colectarGenoma(this)
								+ "\n");
					}
				}

			}
		}

	}

	public void archivarProteoma() {

		if (verFrontera == false) {
			if (aorg.size > 0) {

				f_proteoma.escribirArchivo("\n" + tx.tiempo + "(min:seg): "
						+ timeRep().toString());
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					Organismo or = aEspesies.get(i);
					linea.replace(0, linea.length(), "");

					linea.append(nombreRep(or).toString() + ""
							+ or.adn.colectarProteoma(this) + "\n");

					f_proteoma.escribirArchivo(linea.toString());
				}
				f_proteoma.escribirArchivo("\n");
			}
		}

		if (verFrontera == true) {

			if (aorg.size > 0) {

				colectorEspesies(aorg, aEspesies);

				f_proteoma.escribirArchivo("\n" + tx.tiempo + "(min:seg): "
						+ timeRep().toString());

				f_proteoma.escribirArchivo("\n" + tx.panelIz + "\n");

				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x < ancho / 2) {

						f_proteoma.escribirArchivo(nombreRep(aEspesies.get(i))
								.toString()
								+ ""
								+ aEspesies.get(i).adn.colectarProteoma(this)
								+ "\n");
					}

				}

			}
			if (aorg.size > 0) {

				colectorEspesies(aorg, aEspesies);

				f_proteoma.escribirArchivo("\n" + tx.panelDer + "\n");
				linea.replace(0, linea.length(), "");
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x > ancho / 2) {

						f_proteoma.escribirArchivo(nombreRep(aEspesies.get(i))
								.toString()
								+ ""
								+ aEspesies.get(i).adn.colectarProteoma(this)
								+ "\n");
					}
				}

			}
		}
	}

	public void archivarFenotipo() {

		if (verFrontera == false) {

			if (aorg.size > 0) {

				f_mutantes.escribirArchivo(addCero2().toString() + minutos2
						+ ":" + addCero().toString() + segundos);
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					Organismo or = aEspesies.get(i);

					linea.replace(0, linea.length(), "");
					linea.append(" " + ";" + or.nombre.toString() + ";"
							+ or.cantidad);

					if (colectaralto == true) {
						linea.append(";" + format.format(or.alto));
					}
					if (colectarancho == true) {
						linea.append(";" + format.format(or.ancho));
					}
					if (colectSpeed == true) {
						linea.append(";" + format.format(or.speed));
					}
					if (colectColor == true) {
						linea.append(";" + (int) or.color);
					}
					if (colectarTasaMut == true) {
						linea.append(";" + (int) or.tasaMut);
					}
					if (colectarTemp == true) {
						linea.append(";" + format.format(or.tempOptima));
					}
					if (colectarLongevidad == true) {
						linea.append(";" + format.format(or.longevidad));
					}
					if (colectRadioconsiente == true) {
						linea.append(";" + format.format(or.radioConsiente));
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
					linea.append(";");
					if (colectaralto == false) {
						linea.append(";" + format.format(or.alto));
					}
					if (colectarancho == false) {
						linea.append(";" + format.format(or.ancho));
					}
					if (colectSpeed == false) {
						linea.append(";" + format.format(or.speed));
					}
					if (colectColor == false) {
						linea.append(";" + (int) or.color);
					}
					if (colectarTasaMut == false) {
						linea.append(";" + (int) or.tasaMut);
					}
					if (colectarTemp == false) {
						linea.append(";" + format.format(or.tempOptima));
					}
					if (colectarLongevidad == false) {
						linea.append(";" + format.format(or.longevidad));
					}
					if (colectRadioconsiente == false) {
						linea.append(";" + format.format(or.radioConsiente));
					}
					if (colectPredador == false) {
						linea.append(";" + or.carnivoro);
					}
					if (colectSentir == false) {
						linea.append(";" + or.sentir);
					}
					if (colectCazar == false) {
						linea.append(";" + or.cazar);
					}
					if (colectEscapar == false) {
						linea.append(";" + or.escapar);
					}
					if (colectarResistencia == false) {
						linea.append(";" + or.resistenciaATB);
					}

					linea.append("\n");

					f_mutantes.escribirArchivo(linea.toString());
				}
			}

			// f_fenoma.escribirArchivo();
		}
		if (verFrontera == true) {

			if (aorg.size > 0) {

				f_mutantes.escribirArchivo(addCero2().toString() + minutos2
						+ ":" + addCero().toString() + segundos);

				f_mutantes.escribirArchivo("\n" + tx.panelIz + "\n");

				numero = aEspesies.size;
				linea.replace(0, linea.length(), "");
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x < ancho / 2) {

						linea.append(" " + ";"
								+ aEspesies.get(i).nombre.toString() + ";"
								+ aEspesies.get(i).cantidad);

						if (colectaralto == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).alto));
						}
						if (colectarancho == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).ancho));
						}
						if (colectSpeed == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).speed));
						}
						if (colectColor == true) {
							linea.append(";" + (int) aEspesies.get(i).color);
						}
						if (colectarTasaMut == true) {
							linea.append(";" + (int) aEspesies.get(i).tasaMut);
						}
						if (colectarTemp == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).tempOptima));
						}
						if (colectarLongevidad == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).longevidad));
						}
						if (colectRadioconsiente == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).radioConsiente));
						}
						if (colectPredador == true) {
							linea.append(";" + aEspesies.get(i).carnivoro);
						}
						if (colectSentir == true) {
							linea.append(";" + aEspesies.get(i).sentir);
						}
						if (colectCazar == true) {
							linea.append(";" + aEspesies.get(i).cazar);
						}
						if (colectEscapar == true) {
							linea.append(";" + aEspesies.get(i).escapar);
						}
						if (colectarResistencia == true) {
							linea.append(";" + aEspesies.get(i).resistenciaATB);
						}
						linea.append(";");
						if (colectaralto == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).alto));
						}
						if (colectarancho == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).ancho));
						}
						if (colectSpeed == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).speed));
						}
						if (colectColor == false) {
							linea.append(";" + (int) aEspesies.get(i).color);
						}
						if (colectarTasaMut == false) {
							linea.append(";" + (int) aEspesies.get(i).tasaMut);
						}
						if (colectarTemp == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).tempOptima));
						}
						if (colectarLongevidad == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).longevidad));
						}
						if (colectRadioconsiente == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).radioConsiente));
						}
						if (colectPredador == false) {
							linea.append(";" + aEspesies.get(i).carnivoro);
						}
						if (colectSentir == false) {
							linea.append(";" + aEspesies.get(i).sentir);
						}
						if (colectCazar == false) {
							linea.append(";" + aEspesies.get(i).cazar);
						}
						if (colectEscapar == false) {
							linea.append(";" + aEspesies.get(i).escapar);
						}
						if (colectarResistencia == false) {
							linea.append(";" + aEspesies.get(i).resistenciaATB);
						}
						linea.append("\n");
					}
				}

				f_mutantes.escribirArchivo(linea.toString());
			}

			if (aorg.size > 0) {

				f_mutantes.escribirArchivo(tx.panelDer + "\n");

				linea.replace(0, linea.length(), "");
				numero = aEspesies.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aEspesies.get(i).posicion.x > ancho / 2) {

						linea.append(" " + ";"
								+ aEspesies.get(i).nombre.toString() + ";"
								+ aEspesies.get(i).cantidad);

						if (colectaralto == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).alto));
						}
						if (colectarancho == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).ancho));
						}
						if (colectSpeed == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).speed));
						}
						if (colectColor == true) {
							linea.append(";" + (int) aEspesies.get(i).color);
						}
						if (colectarTasaMut == true) {
							linea.append(";" + (int) aEspesies.get(i).tasaMut);
						}
						if (colectarTemp == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).tempOptima));
						}
						if (colectarLongevidad == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).longevidad));
						}
						if (colectRadioconsiente == true) {
							linea.append(";"
									+ format.format(aEspesies.get(i).radioConsiente));
						}
						if (colectPredador == true) {
							linea.append(";" + aEspesies.get(i).carnivoro);
						}
						if (colectSentir == true) {
							linea.append(";" + aEspesies.get(i).sentir);
						}
						if (colectCazar == true) {
							linea.append(";" + aEspesies.get(i).cazar);
						}
						if (colectEscapar == true) {
							linea.append(";" + aEspesies.get(i).escapar);
						}
						if (colectarResistencia == true) {
							linea.append(";" + aEspesies.get(i).resistenciaATB);
						}
						linea.append(";");
						if (colectaralto == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).alto));
						}
						if (colectarancho == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).ancho));
						}
						if (colectSpeed == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).speed));
						}
						if (colectColor == false) {
							linea.append(";" + (int) aEspesies.get(i).color);
						}
						if (colectarTasaMut == false) {
							linea.append(";" + (int) aEspesies.get(i).tasaMut);
						}
						if (colectarTemp == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).tempOptima));
						}
						if (colectarLongevidad == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).longevidad));
						}
						if (colectRadioconsiente == false) {
							linea.append(";"
									+ format.format(aEspesies.get(i).radioConsiente));
						}
						if (colectPredador == false) {
							linea.append(";" + aEspesies.get(i).carnivoro);
						}
						if (colectSentir == false) {
							linea.append(";" + aEspesies.get(i).sentir);
						}
						if (colectCazar == false) {
							linea.append(";" + aEspesies.get(i).cazar);
						}
						if (colectEscapar == false) {
							linea.append(";" + aEspesies.get(i).escapar);
						}
						if (colectarResistencia == false) {
							linea.append(";" + aEspesies.get(i).resistenciaATB);
						}
						linea.append("\n");
					}
				}

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
					addCero2().toString() + minutos2 + ":"
							+ addCero().toString() + segundos + ";" + aorg.size
							+ ";" + aEspesies.size + ";"
							+ (aorg.size - cantidadPredadores(aorg)) + ";"
							+ cantidadPredadores(aorg) + ";"
							+ cantidadResistentes(aorg) + ";"
							+ format.format(velocidadMedia(aorg)) + ";"
							+ format.format(tamanoMedio(aorg)) + ";"
							+ format.format(longevidadMedio(aorg)) + ";"
							+ tasaMutMedio(aorg) + ";"
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

	// cuenta las mutantes cada 10 segundos

	public void contarMutantes() {

		if (segundos6 >= 10) {

			colectorEspesies(aorg, aEspesies);// colecta las especies o mutante
												// de ese momento

			segundos6 = 0;
		}

	}

	public void tomarMuestras() {

		if (segundos2 >= tiempoMuestreo) {

			colectorEspesies(aorg, aEspesies);// colecta las especies o mutante
												// de ese momento
			// if(verFrontera==true){colectorEspesiesI(aorg,aEspesies);colectorEspesies(aorg,aEspesiesDer);}

			guardarDatos();
			archivarGenoma();
			archivarProteoma();
			archivarFenotipo();

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
		numero = aEspesiesTotales.size;

		for (int i = numero - 1; i >= 0; i--) {
			Organismo or = aEspesiesTotales.get(i);
			linea.replace(0, linea.length(), "");

			linea.append(">" + or.fechaNacimiento + "\n" + or.nombre.toString()
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

					numero = ase.size;
					for (int i = numero - 1; i >= 0; i--) {
						ase.get(i).update();
					}
					if(verFrontera==true) {
						
						cantidadOrganismosI(aorg);
						cantidadOrganismosD(aorg);
						cantidadEspeciesI(aEspesies);
						cantidadEspeciesD(aEspesies);
						
					}
								
					
					// mueve los cuantos de luz
					numero = aqe.size;
					for (int i = numero - 1; i >= 0; i--) {
						aqe.get(i).update();
					}// mueve los cuantos de masa
					numero = aorg.size;
					for (int i = numero - 1; i >= 0; i--) {
						aorg.get(i).update();
					}// mueve los organismos
					if (segundos == 60) {
						System.gc();}
					
					
					detectarColiciones();
					chequerBalance();
					activarCatastrofe();
					activarATB();
					contarMutantes();
					colectorEspesiesTotales();
					tomarMuestras();
					termociclar();
					contadorTiempo();
          // System.out.println("nI " + numeroI+ " nD "+ numeroD + " espI " + numEspI + " espD " + numEspD);
				}
			}

			if (tiempoMaximo > 0) {
				if (aorg.size > 0 && segundos4 < tiempoMaximo) {

					numero = ase.size;
					for (int i = numero - 1; i >= 0; i--) {
						ase.get(i).update();
					}
					 if(verFrontera==true) {
							
						    cantidadOrganismosI(aorg);
							cantidadOrganismosD(aorg);
							cantidadEspeciesI(aEspesies);
							cantidadEspeciesD(aEspesies);
							
						}
					
					// mueve los cuantos de luz
					numero = aqe.size;
					for (int i = numero - 1; i >= 0; i--) {
						aqe.get(i).update();
					}// mueve los cuantos de masa
					numero = aorg.size;
					for (int i = numero - 1; i >= 0; i--) {
						aorg.get(i).update();
					}// mueve los organismos
					if (segundos == 60) {
						System.gc();}
					                   
					detectarColiciones();
					chequerBalance();
					activarCatastrofe();
					activarATB();
					contarMutantes();
					colectorEspesiesTotales();
					tomarMuestras();
					termociclar();
					contadorTiempo();
			// System.out.println("nI " + numeroI+ " nD "+ numeroD + " espI " + numEspI + " espD " + numEspD);
				}
			}
		}

	}

	// method that controls the temperature

	public float termociclar() {

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
			segundos6 = segundos6 + 1;

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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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
								float delta = or.capacidad - or.energia;
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

		// Carnivoro toca otro organismo

		for (int x = 0; x < aorg.size; x++) {

			Organismo or = aorg.get(x);
			Rectangle er = or.borde;

			if (or.carnivoro == true && or.muriendo == 0) {

				for (int a = x; a < aorg.size; a++) {
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (er.overlaps(er2)) {

						if (!or.identificador.equals(or2.identificador)
								&& or.capacidad >= or2.capacidad) {

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
							}
							if (or2.biomasa <= 0) {
								or2.morir();
							}

						}
					}

				}
			}
		}

		// Transferencia horizontal de genes
		if (horizontalTransferRate > 0) {

			for (int x = 0; x < aorg.size; x++) {

				Organismo or = aorg.get(x);
				Rectangle er = or.borde;

				for (int a = x; a < aorg.size; a++) {
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (er.overlaps(er2)) {
						if (a != x) {
							horizontalTransfer(or, or2);// horizontal transfer
														// of genes

							// System.out.println("Colision");
						}
					}
				}
			}
		}

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

	// Horizontal transfer

	public void horizontalTransfer(Organismo or1, Organismo or2) {

		if (horizontalTransferRate > 0) {
			int random = (int) (Math.random() * horizontalTransferRate);//
			// int pos = (int) (Math.random()*100);
			int gentipe = (int) (Math.random() * 12);

			if (random == 0) {

				switch (gentipe) {
				case 0:
					or1.adn.alto.insert(0, or2.adn.alto);
					// if (pos>=50){or1.adn.alto.append(or2.adn.alto);}
					break;
				case 1:
					or1.adn.ancho.insert(0, or2.adn.ancho);
					// if (pos>=50){or1.adn.ancho.append(or2.adn.ancho);}
					break;

				case 2:
					or1.adn.color.insert(0, or2.adn.color);
					// if (pos>=50){or1.adn.color.append(or2.adn.color);}
					break;

				case 3:
					or1.adn.speed.insert(0, or2.adn.speed);
					// if (pos>=50){or1.adn.speed.append(or2.adn.speed);}
					break;

				case 4:
					or1.adn.predador.insert(0, or2.adn.predador);
					// if (pos>=50){or1.adn.predador.append(or2.adn.predador);}
					break;

				case 5:
					or1.adn.sentir.insert(0, or2.adn.sentir);
					// if (pos>=50){or1.adn.sentir.append(or2.adn.sentir);}
					break;

				case 6:
					or1.adn.cazar.insert(0, or2.adn.cazar);
					// if (pos>=50){or1.adn.cazar.append(or2.adn.cazar);}
					break;

				case 7:
					or1.adn.escapar.insert(0, or2.adn.escapar);
					// if (pos>=50){or1.adn.escapar.append(or2.adn.escapar);}
					break;

				case 8:
					or1.adn.radioConsiente.insert(0, or2.adn.radioConsiente);
					// if
					// (pos>=50){or1.adn.radioConsiente.append(or2.adn.radioConsiente);}
					break;

				case 9:
					or1.adn.tasaMutacion.insert(0, or2.adn.tasaMutacion);
					// if
					// (pos>=50){or1.adn.tasaMutacion.append(or2.adn.tasaMutacion);}
					break;

				case 10:
					or1.adn.longevidad.insert(0, or2.adn.longevidad);
					// if
					// (pos>=50){or1.adn.longevidad.append(or2.adn.longevidad);}
					break;

				case 11:
					or1.adn.toleranciaTemp.insert(0, or2.adn.toleranciaTemp);
					// if
					// (pos>=50){or1.adn.toleranciaTemp.append(or2.adn.toleranciaTemp);}
					break;

				case 12:
					or1.adn.resistenciaATB.insert(0, or2.adn.resistenciaATB);
					// if
					// (pos>=50){or1.adn.resistenciaATB.append(or2.adn.resistenciaATB);}
					break;

				}
				// System.out.println("Se tranfirio un gen");
				or1.transferred = true;
				or1.translate();

			}
		}
	}

	public void dispose() {

		textura_organismos.dispose();
	}

}
