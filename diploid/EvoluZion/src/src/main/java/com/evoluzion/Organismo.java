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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Organismo implements Comparable<Organismo> {

	private Mundo m;

	Sprite cuerpo, alas, ojos, antenas, cabezaP, resToxinas, sexo, sp_feromona,
			muerte;

	float speed;
	float seno, coseno;
	float radioConsiente; // radio de los sentidos
	float feromona;
	double toleranciaTemp;
	double tempOptima;
	float SaludCoefi;
	private NumberFormat format = new DecimalFormat("0.00");
	double cuadradoTemp;

	float ancho;
	float alto;
	float tasaMut = 1000; // si mutar es true, indica la frecuencia de mutacion
	float capacidad; // cantidad maxima de masa y energia que puede portar el
						// organismo
	double energia = 484; // energia del organismo
	double biomasa = 1; // biomasa del organismo
	float color; // variable color
	float longevidad; // tiempo en que muere de viejo milisegundos
	int tiempoMitosis; // tiempo en que se divide
	int tiempoCiclo;
	int segundos, segundos2,segundos3;
	int azar;// se usa para las operaciones random
	int marcado = 1; // 1= false -1= true
	int cantidad = 1;
	Vector2 posicion;
	Vector2 direccion;

	Rectangle borde;

	// patron de traduccion de las proteinas

	String patronAncho = "..[RKH][R]......S[KRH]..";
	String patronAlto = "..[RKH][R]......S[KRH]..";
	String patronSpeed = ".[RKH][LMI][LMI].....Q[AG]........";
	String patronColor = "ILI";
	String patronPredador = ".....[M].{4,7}[MIV][KRH]..";
	String patronRadiocon = "..[APTS]..[MILV]F";
	String patronSentir = "[M].{4,13}[IV][EV]";
	String patronCazar = "..[PAG][A][L].{4,10}[HKR]...";
	String patronEscapar = ".....[QE].{2,15}[RKH][C]......";
	String patronTasaMut = ".M.[LMIV][LMI]...[VLI]..";
	String patronLongevidad = ".....[DN][LMIV]......[QE]....[FY]";
	String patronTolerTemp = "...[VYS]..........[HPF].......";
	String patronResATB = "......[GAP]G..{2,12}[IMLV][IMLV]....";
	String patronFeromona = "M........F[KGS].{2,20}.[LIGSV][LIGSV]..";
	String patronParteNoGen = "M..........DFL........[MI]";

	char[] letras = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' };

	String fechaNacimiento;

	boolean mutar = true; // puede mutar o no
	boolean carnivoro = false; // capacidad de tomar alimentarse de otros
								// organismos
	boolean resistenciaATB; // capacidad de resistir Antibioticos
	boolean sentir; // capacidad de sensar el entornno por comida
	boolean cazar; // capacidad de buscar su comida
	boolean escapar;
	boolean transferred = false;
	boolean male = false;
	boolean intoxicado = false;
	boolean parteNoGen = false;// se reproduce asexualmente
	
	//modelo de infeccion viral
	
	boolean infectado = false;
	boolean inmune = false;
	int inmunidad;

	long edad, delta, delta2, deltaToxico, deltaEnfermo, delta3, deltaAleatorio; // se usa
																	// para
																	// determinar
																	// el paso
																	// del
																	// tiempo
	Genoma adn, adn2, adn3, adn4, adn5, adn6, ch1, ch2, ch3, ch4, ch5, ch6,
			ch7, ch8, ch9, ch10, ch11, ch12; // genoma

	int identificador;// indica la especie del organismo
	int carniI;
	int sentI;
	int cazarI;
	int escapI;
	int carniI2;
	int sentI2;
	int cazarI2;
	int escapI2;
	int resistenciaI2;
	int parteNoGenI;
	int parteNoGenI2;
	int resistenciaI;
	int muriendo = 0;

	String cromosoma; // todos los genes juntos
	StringBuffer nombre;

	Array<Vector2> aObjetos; // memoria donde se guarda la posicion de las
								// fuentes de alimento
	Array<Organismo> aHembras; // memoria donde se guarda la posicion de las
								// hembras
	Array<Vector2> aPredadores; // memoria donde se guarda la posicion de las
								// fuentes de peligro
	Array<Alelo> aAlelos;
	Array<Genotipo> aGenomas;
	char base;// nucleotido

	Alelo aColor, aColor2, aAlto, aAlto2;
	Genotipo genotipo;

	Fenotipo fenotipo;
	StringBuffer gen;

	public Organismo() {

	}

	public Organismo(boolean male, Genoma adn, Genoma adn2, Genoma adn3,
			Genoma adn4, Genoma adn5, Genoma adn6, Vector2 posicion,
			StringBuffer nombre, Mundo m) {

		this.m = m;

		fechaNacimiento = "" + m.minutos2 + ":" + m.segundos;

		aObjetos = new Array<Vector2>();
		aHembras = new Array<Organismo>();
		aPredadores = new Array<Vector2>();
		aAlelos = new Array<Alelo>();
		aGenomas = new Array<Genotipo>();

		this.adn = adn;
		this.adn2 = adn2;
		this.adn3 = adn3;
		this.adn4 = adn4;
		this.adn5 = adn5;
		this.adn6 = adn6;
		this.male = male;

		this.posicion = posicion;
		this.nombre = nombre;

		ch1 = new Genoma(m, 1, 1, 1, 0, 0, 0);
		ch2 = new Genoma(m, 1, 1, 1, 0, 0, 0);

		ch3 = new Genoma(m, 1, 1, 1, 0, 0, 0);
		ch4 = new Genoma(m, 1, 1, 1, 0, 0, 0);

		ch5 = new Genoma(m, 0, 0, 0, 0, 0, 0, 0);
		ch6 = new Genoma(m, 0, 0, 0, 0, 0, 0, 0);

		ch7 = new Genoma(m, 0, 0, 0, 0, 0, 0, 0);
		ch8 = new Genoma(m, 0, 0, 0, 0, 0, 0, 0);

		ch9 = new Genoma(m, 0, 0, 0);
		ch10 = new Genoma(m, 0, 0, 0);

		ch11 = new Genoma(m, 0, 0, 0);
		ch12 = new Genoma(m, 0, 0, 0);

		direccion = new Vector2();
		borde = new Rectangle();

		translate();

		segundos = 0;
		segundos2 = 0;
		segundos3 = 0;
		setTime();
		setEdad();
		setDelta();
		setDelta3();
		setDeltaToxico();
		setDeltaEnfermo();

		// alelos paracomparar
		if (m.colectarResistencia == true) {
			aAlelos.add(new Alelo(m.tx.ResATB, (int) adn.traducirMagnitud(this,
					adn.resistenciaATB, patronResATB) / 10, adn.resistenciaATB
					.toString()));//
			aAlelos.add(new Alelo(m.tx.ResATB, (int) adn2.traducirMagnitud(
					this, adn2.resistenciaATB, patronResATB) / 10,
					adn2.resistenciaATB.toString()));
		}//
		if (m.colectaralto == true) {
			aAlelos.add(new Alelo(m.tx.tamano, (int) adn.traducirMagnitud(this,
					adn.ancho, patronAncho)
					/ 10
					+ (int) adn.traducirMagnitud(this, adn.alto, patronAlto)
					/ 10, adn.alto.toString() + adn.alto.toString()));
			aAlelos.add(new Alelo(m.tx.tamano, (int) adn2.traducirMagnitud(
					this, adn2.ancho, patronAncho)
					/ 10
					+ (int) adn2.traducirMagnitud(this, adn2.alto, patronAlto)
					/ 10, adn2.alto.toString() + adn2.alto.toString()));
		}
		if (m.colectSpeed == true) {
			aAlelos.add(new Alelo(m.tx.alas, (int) (adn3.traducirMagnitud(this,
					adn3.speed, patronSpeed) / 9), adn3.speed.toString()));
			aAlelos.add(new Alelo(m.tx.alas, (int) (adn4.traducirMagnitud(this,
					adn4.speed, patronSpeed) / 9), adn4.speed.toString()));
		}

		if (m.colectarTemp == true) {
			aAlelos.add(new Alelo(m.tx.temOptima, (int) (adn.traducirMagnitud(
					this, adn.toleranciaTemp, patronTolerTemp) / 7),
					adn.toleranciaTemp.toString()));
			aAlelos.add(new Alelo(m.tx.temOptima, (int) (adn2.traducirMagnitud(
					this, adn2.toleranciaTemp, patronTolerTemp) / 7),
					adn2.toleranciaTemp.toString()));
		}
		if (m.colectPredador == true) {
			aAlelos.add(new Alelo(m.tx.predador, (int) adn.traducirMagnitud(
					this, adn.predador, patronPredador) / 6, adn.predador
					.toString()));
			aAlelos.add(new Alelo(m.tx.predador, (int) adn2.traducirMagnitud(
					this, adn2.predador, patronPredador) / 6, adn2.predador
					.toString()));
		}
		if (m.colectSentir == true) {
			aAlelos.add(new Alelo(m.tx.sentidos, (int) adn.traducirMagnitud(
					this, adn.sentir, patronSentir) / 6, adn.sentir.toString()));
			aAlelos.add(new Alelo(m.tx.sentidos, (int) adn2.traducirMagnitud(
					this, adn2.sentir, patronSentir) / 6, adn2.sentir
					.toString()));
		}

		if (m.colectCazar == true) {
			aAlelos.add(new Alelo(m.tx.buscarComida, (int) adn3
					.traducirMagnitud(this, adn3.cazar, patronCazar) / 7,
					adn3.cazar.toString()));
			aAlelos.add(new Alelo(m.tx.buscarComida, (int) adn4
					.traducirMagnitud(this, adn4.cazar, patronCazar) / 7,
					adn4.cazar.toString()));
		}
		if (m.colectEscapar == true) {
			aAlelos.add(new Alelo(m.tx.escapar, (int) adn3.traducirMagnitud(
					this, adn3.escapar, patronEscapar) / 7, adn3.escapar
					.toString()));
			aAlelos.add(new Alelo(m.tx.escapar, (int) adn4.traducirMagnitud(
					this, adn4.escapar, patronEscapar) / 7, adn4.escapar
					.toString()));
		}
		if (m.colectRadioconsiente == true) {
			aAlelos.add(new Alelo(m.tx.alcanceVisual,
					(int) adn3.traducirMagnitud(this, adn3.radioConsiente,
							patronRadiocon) / 7, adn3.radioConsiente.toString()));
			aAlelos.add(new Alelo(m.tx.alcanceVisual,
					(int) adn4.traducirMagnitud(this, adn4.radioConsiente,
							patronRadiocon) / 7, adn4.radioConsiente.toString()));
		}
		if (m.colectarFeromona == true) {
			aAlelos.add(new Alelo(m.tx.feromona, (int) adn3.traducirMagnitud(
					this, adn3.feromona, patronFeromona) / 30, adn3.feromona
					.toString()));
			aAlelos.add(new Alelo(m.tx.feromona, (int) adn4.traducirMagnitud(
					this, adn4.feromona, patronFeromona) / 30, adn4.feromona
					.toString()));
		}

		if (m.colectarParteNoGen == true) {
			aAlelos.add(new Alelo(m.tx.partenogenesis,
					(int) adn3.traducirMagnitud(this, adn3.parteNoGen,
							patronParteNoGen) / 10, adn3.parteNoGen.toString()));
			aAlelos.add(new Alelo(m.tx.partenogenesis,
					(int) adn4.traducirMagnitud(this, adn4.parteNoGen,
							patronParteNoGen) / 10, adn4.parteNoGen.toString()));
		}

		if (m.colectarTasaMut == true) {
			aAlelos.add(new Alelo(m.tx.fidelidadADNpol,
					(int) ((adn5.traducirMagnitud(this, adn5.tasaMutacion,
							patronTasaMut) / 8)), adn5.tasaMutacion.toString()));
			if (male == false) {
				aAlelos.add(new Alelo(m.tx.fidelidadADNpol, (int) ((adn6
						.traducirMagnitud(this, adn6.tasaMutacion,
								patronTasaMut) / 8)), adn6.tasaMutacion
						.toString()));
			}
		}
		if (m.colectarLongevidad == true) {
			aAlelos.add(new Alelo(m.tx.longevidad,
					(int) (adn5.traducirMagnitud(this, adn5.longevidad,
							patronLongevidad) / 7), adn5.longevidad.toString()));
			if (male == false) {
				aAlelos.add(new Alelo(m.tx.longevidad, (int) (adn6
						.traducirMagnitud(this, adn6.longevidad,
								patronLongevidad) / 7), adn6.longevidad
						.toString()));
			}
		}
		if (m.colectColor == true) {
			aAlelos.add(new Alelo(m.tx.color, (int) adn5.traducirMagnitud(this,
					adn5.color, patronColor) / 10, adn5.color.toString()));
			if (male == false) {
				aAlelos.add(new Alelo(m.tx.color, (int) adn6.traducirMagnitud(
						this, adn6.color, patronColor) / 10, adn6.color
						.toString()));
			}
		}

		else {
		}

		// Genomas para comparar

		gen = new StringBuffer("");

		if (m.verMachoHembra == true) {
			if (male == true) {
				gen.append("" + m.tx.macho);
			}
			if (male == false) {
				gen.append("" + m.tx.hembra);
			}
		}
		if (m.verMachoHembra == false) {
			if (male == true) {
				gen.append("");
			}
			if (male == false) {
				gen.append("");
			}
		}
		int n1 = 0;
		int n2 = 0;

		if (male == false) {

			for (int i = 0; i < aAlelos.size; i = i + 2) {
				n1 = aAlelos.get(i).identificador;
				n2 = aAlelos.get(i + 1).identificador;

				if (n1 == n2) {
					gen.append(";a" + n1 + " / a" + n2);
				}
				if (n1 > n2) {
					gen.append(";a" + n1 + " / a" + n2);
				}
				if (n1 < n2) {
					gen.append(";a" + n2 + " / a" + n1);
				}
			}

		}

		if (male == true) {

			int salto = 2;

			for (int i = 0; i < aAlelos.size; i = i + salto) {

				if (!aAlelos.get(i).nombre.equals(m.tx.color)
						&& !aAlelos.get(i).nombre.equals(m.tx.longevidad)
						&& !aAlelos.get(i).nombre.equals(m.tx.fidelidadADNpol)) {

					n1 = aAlelos.get(i).identificador;
					n2 = aAlelos.get(i + 1).identificador;

					if (n1 == n2) {
						gen.append(";a" + n1 + " / a" + n2);
					}
					if (n1 > n2) {
						gen.append(";a" + n1 + " / a" + n2);
					}
					if (n1 < n2) {
						gen.append(";a" + n2 + " / a" + n1);
					}

				}

				if (aAlelos.get(i).nombre.equals(m.tx.color)
						|| aAlelos.get(i).nombre.equals(m.tx.longevidad)
						|| aAlelos.get(i).nombre.equals(m.tx.fidelidadADNpol)) {

					n1 = aAlelos.get(i).identificador;

					gen.append(";a" + n1 + " /");
					salto = 1;
				}

			}

		}

		genotipo = new Genotipo(gen.toString());

	}

	public float translateSpeed(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (g1.traducirMagnitud(this, g1.speed, patronSpeed) / 250)
				/ m.zoom;
		float gen2 = (g2.traducirMagnitud(this, g2.speed, patronSpeed) / 250)
				/ m.zoom;

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateAncho(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (g1.traducirMagnitud(this, g1.ancho, patronAncho) / (224 * m.zoom));
		float gen2 = (g2.traducirMagnitud(this, g2.ancho, patronAncho) / (224 * m.zoom));

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public float translateAlto(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (g1.traducirMagnitud(this, g1.alto, patronAlto) / (224 * m.zoom));
		float gen2 = (g2.traducirMagnitud(this, g2.alto, patronAlto) / (224 * m.zoom));

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public double translateToleTemp(Genoma g1, Genoma g2) {
		double fenotipe = 0;

		double gen1 = (g1.traducirMagnitud(this, g1.toleranciaTemp,
				patronTolerTemp) / 126.2552);
		double gen2 = (g2.traducirMagnitud(this, g2.toleranciaTemp,
				patronTolerTemp) / 126.2552);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateColor(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = g1.traducirMagnitud(this, g1.color, patronColor) / 10;
		float gen2 = g2.traducirMagnitud(this, g2.color, patronColor) / 10;

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public float translateRadio(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (g1.traducirMagnitud(this, g1.radioConsiente,
				patronRadiocon) / 30);
		float gen2 = (g2.traducirMagnitud(this, g2.radioConsiente,
				patronRadiocon) / 30);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateferomona(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		if (male == false) {

			float gen1 = (g1
					.traducirMagnitud(this, g1.feromona, patronFeromona) / 30);
			float gen2 = (g2
					.traducirMagnitud(this, g2.feromona, patronFeromona) / 30);

			if (gen1 == 0 && gen2 == 0) {
				fenotipe = 0;
			}
			if (gen1 == 0 && gen2 > 0) {
				fenotipe = gen2;
			}
			if (gen2 == 0 && gen1 > 0) {
				fenotipe = gen1;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
				fenotipe = gen2;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
				fenotipe = gen1;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
				fenotipe = gen1;
			}

		}

		return fenotipe;
	}

	public float translaLongevidad(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (g1
				.traducirMagnitud(this, g1.longevidad, patronLongevidad) * 8.090f);
		float gen2 = (g2
				.traducirMagnitud(this, g2.longevidad, patronLongevidad) * 8.090f);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translaTasaMut(Genoma g1, Genoma g2) {
		float fenotipe = 0;

		float gen1 = (float) ((g1.traducirMagnitud(this, g1.tasaMutacion,
				patronTasaMut) / 1.9) * m.eficiencia);
		float gen2 = (float) ((g2.traducirMagnitud(this, g2.tasaMutacion,
				patronTasaMut) / 1.9) * m.eficiencia);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public boolean translatePredator(Genoma g1, Genoma g2) {
		boolean fenotipe = true;

		boolean gen1 = g1.traducirBoolean(g1.predador, patronPredador);
		boolean gen2 = g2.traducirBoolean(g2.predador, patronPredador);
		;

		if (gen1 == false && gen2 == false) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateSense(Genoma g1, Genoma g2) {
		boolean fenotipe = true;

		boolean gen1 = g1.traducirBoolean(g1.sentir, patronSentir);
		boolean gen2 = g2.traducirBoolean(g2.sentir, patronSentir);

		if (gen1 == false && gen2 == false) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateCazar(Genoma g1, Genoma g2) {
		boolean fenotipe = true;

		boolean gen1 = g1.traducirBoolean(g1.cazar, patronCazar);
		boolean gen2 = g2.traducirBoolean(g2.cazar, patronCazar);

		if (gen1 == false && gen2 == false) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateEscape(Genoma g1, Genoma g2) {
		boolean fenotipe = true;

		boolean gen1 = g1.traducirBoolean(g1.escapar, patronEscapar);
		boolean gen2 = g2.traducirBoolean(g2.escapar, patronEscapar);

		if (gen1 == false && gen2 == false) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translatParteNoGen(Genoma g1, Genoma g2) {
		boolean fenotipe = false;

		boolean gen1 = g1.traducirBoolean(g1.parteNoGen, patronParteNoGen);
		boolean gen2 = g2.traducirBoolean(g2.parteNoGen, patronParteNoGen);

		if (gen1 == false && gen2 == false) {
			fenotipe = true;
		}

		return fenotipe;

	}

	public boolean translateResisATB(Genoma g1, Genoma g2) {
		boolean fenotipe = true;

		boolean gen1 = g1.traducirBoolean(g1.resistenciaATB, patronResATB);
		boolean gen2 = g2.traducirBoolean(g2.resistenciaATB, patronResATB);

		if (gen1 == false && gen2 == false) {
			fenotipe = false;
		}

		return fenotipe;
	}

	// para machos homosigotas en C3

	public float translateColor(Genoma g1) {

		float gen1 = g1.traducirMagnitud(this, g1.color, patronColor) / 10;

		return gen1;
	}

	public float translaLongevidad(Genoma g1) {

		float gen1 = (g1
				.traducirMagnitud(this, g1.longevidad, patronLongevidad) * 8.090f);

		return gen1;
	}

	public float translaTasaMut(Genoma g1) {

		float gen1 = (float) ((g1.traducirMagnitud(this, g1.tasaMutacion,
				patronTasaMut) / 1.9) * m.eficiencia);

		return (int) gen1;
	}

	public void translate() {

		resistenciaATB = translateResisATB(adn, adn2);
		ancho = translateAncho(adn, adn2);
		alto = translateAlto(adn, adn2);
		sentir = translateSense(adn, adn2);
		toleranciaTemp = translateToleTemp(adn, adn2);
		carnivoro = translatePredator(adn, adn2);

		speed = translateSpeed(adn3, adn4);
		cazar = translateCazar(adn3, adn4);
		escapar = translateEscape(adn3, adn4);
		radioConsiente = translateRadio(adn3, adn4);
		feromona = translateferomona(adn3, adn4);
		parteNoGen = translatParteNoGen(adn3, adn4);

		if (male == false) {
			longevidad = translaLongevidad(adn5, adn6);
			tasaMut = translaTasaMut(adn5, adn6);
			color = translateColor(adn5, adn6);
		}

		if (male == true) {
			longevidad = translaLongevidad(adn5);
			tasaMut = translaTasaMut(adn5);
			color = translateColor(adn5);
		}

		// asigna valoresnumericos para los genes booleanos
		resistenciaI = (int) adn.traducirMagnitud(this, adn.resistenciaATB,
				patronResATB) / 8;
		carniI = (int) adn.traducirMagnitud(this, adn.predador, patronPredador) / 6;
		sentI = (int) adn.traducirMagnitud(this, adn.sentir, patronSentir) / 6;
		cazarI = (int) adn3.traducirMagnitud(this, adn3.cazar, patronCazar) / 7;
		escapI = (int) adn3.traducirMagnitud(this, adn3.escapar, patronEscapar) / 7;
		parteNoGenI = (int) adn3.traducirMagnitud(this, adn3.parteNoGen,
				patronParteNoGen) / 10;

		resistenciaI2 = (int) adn2.traducirMagnitud(this, adn2.resistenciaATB,
				patronResATB) / 8;
		carniI2 = (int) adn2.traducirMagnitud(this, adn2.predador,
				patronPredador) / 6;
		sentI2 = (int) adn2.traducirMagnitud(this, adn2.sentir, patronSentir) / 6;
		cazarI2 = (int) adn4.traducirMagnitud(this, adn4.cazar, patronCazar) / 7;
		escapI2 = (int) adn4
				.traducirMagnitud(this, adn4.escapar, patronEscapar) / 7;
		parteNoGenI2 = (int) adn4.traducirMagnitud(this, adn4.parteNoGen,
				patronParteNoGen) / 10;

		// suma todos los genes y se multiplican por un factor de ponderacion
		// para que cada uno de más o menos 1000

		float suma = (int) ((ancho * 135.3) + (alto * 135.3) + (speed * 115.6)
				+ (toleranciaTemp * 35.52) + (longevidad * 0.03333) + (tasaMut * 0.486));
		// + resistenciaI +resistenciaI2 + sentI+ sentI2+ carniI + carniI2+
		// radioConsiente+cazarI+cazarI2+escapI+escapI2
		// +parteNoGenI+parteNoGenI2) ;

		// System.out.println(ancho +";"+ alto+";"+ speed +";"+
		// toleranciaTemp+";"+ longevidad+";"+ tasaMut+";"+
		// resistenciaI +";"+resistenciaI2+";" + sentI+";"+ sentI2+";"+
		// carniI+";" + carniI2+";"+
		// radioConsiente+";"+cazarI+";"+cazarI2+";"+escapI+";"+escapI2
		// +";"+parteNoGenI+";"+parteNoGenI2);

		SaludCoefi = suma / 6000;

		tiempoMitosis = (int) (20000 / SaludCoefi);
		longevidad = longevidad * SaludCoefi;

		tempOptima = toleranciaTemp - 3;

		// System.out.println("suma: "+suma + ", SaludCoefi: "+SaludCoefi
		// +", TiemMit: "+ tiempoMitosis+ ",longev: "+ longevidad);

		// limita el tamaño menos

		// limita el tamaño menos

		if (alto > 0 && alto < 0.5) {
			alto = 0.5f;
		}
		if (ancho > 0 && ancho < 0.5) {
			ancho = 0.5f;
		}

		capacidad = (int) (ancho * alto);
		biomasa = 0;

		capacidad = (ancho * alto);
		// biomasa=1;

		if (energia > capacidad) {
			energia = capacidad;
		}
		// if(biomasa>capacidad){biomasa=capacidad;}

		int indexcolor = 0;

		if (color == 0) {
			indexcolor = 0;
		}
		if (color > 0 && color >= 76) {
			indexcolor = 1;
		}
		if (color > 76 && color <= 152) {
			indexcolor = 2;
		}
		if (color > 152 && color <= 228) {
			indexcolor = 3;
		}
		if (color > 228 && color <= 304) {
			indexcolor = 4;
		}
		if (color > 304 && color <= 380) {
			indexcolor = 5;
		}
		if (color > 380 && color <= 456) {
			indexcolor = 6;
		}
		if (color > 456 && color <= 532) {
			indexcolor = 7;
		}
		if (color > 532 && color <= 608) {
			indexcolor = 8;
		}
		if (color > 608 && color <= 684) {
			indexcolor = 9;
		}
		if (color > 684 && color <= 760) {
			indexcolor = 10;
		}
		if (color > 760) {
			indexcolor = 10;
		}

		cuerpo = new Sprite(m.textura_organismos.getRegions().get(indexcolor));
		cuerpo.setOrigin(posicion.x, posicion.y);
		cuerpo.setPosition(this.posicion.x, this.posicion.y);
		cuerpo.setSize(ancho, alto * 3);

		alas = new Sprite(m.textura_organismos.getRegions().get(16));
		alas.setOrigin(posicion.x, posicion.y);
		alas.setPosition(this.posicion.x, this.posicion.y);
		alas.setSize(speed * 3, 10);

		int colorOjos = 0;

		if (cazar == false && escapar == false) {
			colorOjos = 11;
		}
		if (cazar == true && escapar == false) {
			colorOjos = 12;
		}
		if (cazar == false && escapar == true) {
			colorOjos = 13;
		}
		if (cazar == true && escapar == true) {
			colorOjos = 14;
		}

		ojos = new Sprite(m.textura_organismos.getRegions().get(colorOjos));
		ojos.setSize(ancho, alto);
		ojos.setPosition(this.posicion.x, this.posicion.y);

		antenas = new Sprite(m.textura_organismos.getRegions().get(17));
		antenas.setSize(ancho, alto);

		cabezaP = new Sprite(m.textura_organismos.getRegions().get(15));
		cabezaP.setSize(ancho, alto);

		resToxinas = new Sprite(m.textura_organismos.getRegions().get(18));
		resToxinas.setSize(ancho, alto);

		// transferido = new Sprite(m.transferido);
		// transferido.setPosition(this.posicion.x,this.posicion.y);
		// transferido.setSize(ancho+5, alto+5);

		int g = 0;

		if (male == true) {
			g = 21;
		}
		if (male == false && parteNoGen == false) {
			g = 20;
		}
		if (male == false && parteNoGen == true) {
			g = 22;
		}

		sexo = new Sprite(m.textura_organismos.getRegions().get(g));
		sexo.setOrigin(posicion.x, posicion.y);
		sexo.setSize(ancho * 2, alto * 2);
		sexo.setPosition(this.posicion.x, this.posicion.y - alto);

		sp_feromona = new Sprite(m.textura_organismos.getRegions().get(19));
		sp_feromona.setOrigin(this.posicion.x, this.posicion.y);
		sp_feromona.setSize(feromona / 5, feromona / 5);
		sp_feromona.setPosition(this.posicion.x, this.posicion.y - alto);

		fenotipo = new Fenotipo(m);
		fenotipo.nombre = getFenotipo(m);

		borde.height = alto;
		borde.width = ancho;

	}

	public String getFenotipo(Mundo mu) {

		StringBuffer sb = new StringBuffer();

		if (m.verMachoHembra == true) {
			if (male == true) {
				sb.append(m.tx.macho);
			}
			if (male == false) {
				sb.append(m.tx.hembra);
			}
		}

		if (mu.colectarResistencia == true) {

			boolean ge1 = adn.traducirBoolean(adn.resistenciaATB, patronResATB);
			boolean ge2 = adn2.traducirBoolean(adn2.resistenciaATB,
					patronResATB);

			if (ge1 == ge2) {
				if (resistenciaI > resistenciaI2) {
					sb.append(";Tox-" + this.resistenciaI);
				}
				if (resistenciaI2 > resistenciaI) {
					sb.append(";Tox-" + this.resistenciaI2);
				}
				if (resistenciaI2 == resistenciaI) {
					sb.append(";Tox-" + this.resistenciaI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";Tox-" + this.resistenciaI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";Tox-" + this.resistenciaI2);
			}
		}

		if (mu.colectarancho == true) {
			sb.append(";Siz-"
					+ ((int) (this.ancho * 54.934) + (int) (this.alto * 54.934)));
		}

		if (mu.colectSpeed == true) {
			sb.append(";Win-" + (int) (this.speed * 55.477f));
		}

		if (mu.colectarTemp == true) {
			sb.append(";Tem-" + (int) (this.toleranciaTemp));
		}

		if (mu.colectPredador == true) {
			boolean ge1 = adn.traducirBoolean(adn.predador, patronPredador);
			boolean ge2 = adn2.traducirBoolean(adn.predador, patronPredador);

			if (ge1 == ge2) {
				if (carniI > carniI2) {
					sb.append(";Pre-" + this.carniI);
				}
				if (carniI2 > carniI) {
					sb.append(";Pre-" + this.carniI2);
				}
				if (carniI2 == carniI) {
					sb.append(";Pre-" + this.carniI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";Pre-" + this.carniI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";Pre-" + this.carniI2);
			}

		}

		if (mu.colectSentir == true) {

			boolean ge1 = adn.traducirBoolean(adn.sentir, patronSentir);
			boolean ge2 = adn2.traducirBoolean(adn2.sentir, patronSentir);

			if (ge1 == ge2) {
				if (sentI > sentI2) {
					sb.append(";Sen-" + this.sentI);
				}
				if (sentI2 > sentI) {
					sb.append(";Sen-" + this.sentI2);
				}
				if (sentI2 == sentI) {
					sb.append(";Sen-" + this.sentI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";Sen-" + this.sentI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";Sen-" + this.sentI2);
			}

		}

		if (mu.colectCazar == true) {

			boolean ge1 = adn3.traducirBoolean(adn3.cazar, patronCazar);
			boolean ge2 = adn4.traducirBoolean(adn4.cazar, patronCazar);

			if (ge1 == ge2) {
				if (cazarI > cazarI2) {
					sb.append(";Ba-" + this.cazarI);
				}
				if (cazarI2 > cazarI) {
					sb.append(";Ba-" + this.cazarI2);
				}
				if (cazarI2 == cazarI) {
					sb.append(";Ba-" + this.cazarI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";Ba-" + this.cazarI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";Ba-" + this.cazarI2);
			}

		}

		if (mu.colectEscapar == true) {
			boolean ge1 = adn3.traducirBoolean(adn3.escapar, patronEscapar);
			boolean ge2 = adn4.traducirBoolean(adn4.escapar, patronEscapar);

			if (ge1 == ge2) {
				if (escapI > escapI2) {
					sb.append(";Ya-" + this.escapI);
				}
				if (escapI2 > escapI) {
					sb.append(";Ya-" + this.escapI2);
				}
				if (escapI2 == escapI) {
					sb.append(";Ya-" + this.escapI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";Ya-" + this.escapI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";Ya-" + this.escapI2);
			}
		}
		if (mu.colectarParteNoGen == true) {
			boolean ge1 = adn3.traducirBoolean(adn3.parteNoGen,
					patronParteNoGen);
			boolean ge2 = adn4.traducirBoolean(adn4.parteNoGen,
					patronParteNoGen);

			if (ge1 == ge2) {
				if (parteNoGenI > parteNoGenI2) {
					sb.append(";nG-" + this.parteNoGenI);
				}
				if (parteNoGenI2 > parteNoGenI) {
					sb.append(";nG-" + this.parteNoGenI2);
				}
				if (parteNoGenI2 == parteNoGenI) {
					sb.append(";nG-" + this.parteNoGenI2);
				}
			}
			if (ge1 == true && ge2 == false) {
				sb.append(";nG-" + this.parteNoGenI);
			}
			if (ge1 == false && ge2 == true) {
				sb.append(";nG-" + this.parteNoGenI2);
			}
		}

		if (mu.colectRadioconsiente == true) {
			sb.append(";Rc-" + (int) this.radioConsiente);
		}

		if (mu.colectarFeromona == true) {
			sb.append(";Fr-" + (int) this.feromona);
		}

		if (mu.colectarTasaMut == true) {
			sb.append(";Mut-" + this.tasaMut);
		}
		if (mu.colectarLongevidad == true) {
			sb.append(";lon-" + this.longevidad);
		}

		if (mu.colectColor == true) {
			sb.append(";Col-" + (int) this.color);
		}

		// System.out.println(sentI);
		return sb.toString();

	}

	// permite al programa saber se un organismo es diferente uno de otro

	public float Identificar(Mundo mu) {

		return ((alto / ancho) * (alto * ancho));

	}

	public void verOrganismo(SpriteBatch sb) {

		sb.begin();

		if (male == false) {
			sp_feromona.draw(sb);
		}
		cuerpo.draw(sb);
		alas.draw(sb);
		if (carnivoro == true) {
			cabezaP.draw(sb);
		}
		ojos.draw(sb);
		if (sentir == true) {
			antenas.draw(sb);
		}
		if (resistenciaATB == true) {
			resToxinas.draw(sb);
		}
		sexo.draw(sb);
		sb.end();

	}

	public void verMarcado(ShapeRenderer sr, SpriteBatch sb, BitmapFont f) {
		if (marcado == -1) {
			sr.begin(ShapeType.Rectangle);

			sr.setColor(Color.GREEN);
			sr.rect(borde.x - 2, borde.y - 2, borde.width + 4, borde.height + 4);
			sr.end();

			sb.begin();
			f.setColor(Color.GREEN);
			f.draw(sb, nombre, posicion.x + ancho + 5, posicion.y + (alto) + 5);
			f.draw(sb, "M:" + format.format(biomasa), posicion.x + ancho + 5,
					posicion.y);
			f.draw(sb, "E:" + format.format(energia), posicion.x + ancho + 5,
					posicion.y - 20);
			f.draw(sb, "V:" + format.format(speed), posicion.x + ancho + 5,
					posicion.y - 40);
			sb.end();

		}
	}

	public void verBorde(ShapeRenderer sr) {

		sr.begin(ShapeType.Rectangle);

		sr.setColor(Color.CYAN);
		sr.rect(borde.x, borde.y, borde.width, borde.height);
		sr.end();

	}

	public void Ordenar() {

		cuerpo.setPosition(posicion.x, posicion.y);
		alas.setPosition(
				this.posicion.x + cuerpo.getWidth() / 2 - alas.getWidth() / 2,
				this.posicion.y + cuerpo.getHeight() / 2 - alas.getHeight() / 2);

		ojos.setPosition(
				this.posicion.x + cuerpo.getWidth() / 2 - ojos.getWidth() / 2,
				this.posicion.y + cuerpo.getHeight() - alas.getHeight() / 2);
		if (sentir == true) {
			antenas.setPosition(this.posicion.x + cuerpo.getWidth() / 2
					- antenas.getWidth() / 2,
					this.posicion.y + cuerpo.getHeight());
		}
		if (carnivoro == true) {
			cabezaP.setPosition(this.posicion.x + cuerpo.getWidth() / 2
					- cabezaP.getWidth() / 2,
					this.posicion.y + cuerpo.getHeight() - cabezaP.getHeight());
		}
		if (resistenciaATB == true) {
			resToxinas.setPosition(this.posicion.x + cuerpo.getWidth() / 2
					- resToxinas.getWidth() / 2,
					this.posicion.y + resToxinas.getHeight() / 2);
		}
		sexo.setPosition(posicion.x - sexo.getWidth() / 4,
				posicion.y - sexo.getHeight());
		sp_feromona.setPosition(
				this.posicion.x + cuerpo.getWidth() / 2
						- sp_feromona.getWidth() / 2,
				this.posicion.y + cuerpo.getHeight() / 2
						- sp_feromona.getHeight() / 2);

		// transferido.setPosition(posicion.x-2.5f,posicion.y-2.5f);

		borde.x = posicion.x;
		borde.y = posicion.y;

	}

	public void update() {

		if (alto <= 1 || ancho <= 1) {
			morir();
		}

		if (alto > 1 && ancho > 1) {
			// if(biomasa<=0){morir();}

			if (toleranciaTemp < m.temperatura) {
				morir();
			}
			if (m.temperatura < toleranciaTemp - 6) {
				morir();
			}

			if (m.antibiotico == 1) {
				if (resistenciaATB == false) {
					intoxicado();
				}
			}
			
			//if (infectado == true) {
				
			//	enfermedad();
			//	System.out.println(segundos3);
			//}

			if (m.pausaGame == 1) {
				metabolismo();
				contadorTiempo();
				apoptosis();
				aleatorio();

				if (sentir == true) {
					escannearObjetos();
				}
				if (cazar == true) {
					cazarObjetos();
				}
				if (escapar == true) {
					escapar();
				}
				if (male == true) {
					buscarHembra();
				}

			}

			posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x) * speed,
					Gdx.graphics.getDeltaTime() * (direccion.y) * speed);

			cuerpo.setPosition(posicion.x, posicion.y);
			alas.setPosition(
					this.posicion.x + cuerpo.getWidth() / 2 - alas.getWidth()
							/ 2, this.posicion.y + cuerpo.getHeight() / 2
							- alas.getHeight() / 2);

			ojos.setPosition(
					this.posicion.x + cuerpo.getWidth() / 2 - ojos.getWidth()
							/ 2,
					this.posicion.y + cuerpo.getHeight() - alas.getHeight() / 2);
			if (sentir == true) {
				antenas.setPosition(this.posicion.x + cuerpo.getWidth() / 2
						- antenas.getWidth() / 2,
						this.posicion.y + cuerpo.getHeight());
			}
			if (carnivoro == true) {
				cabezaP.setPosition(
						this.posicion.x + cuerpo.getWidth() / 2
								- cabezaP.getWidth() / 2,
						this.posicion.y + cuerpo.getHeight()
								- cabezaP.getHeight());
			}
			if (resistenciaATB == true) {
				resToxinas.setPosition(this.posicion.x + cuerpo.getWidth() / 2
						- resToxinas.getWidth() / 2, this.posicion.y
						+ resToxinas.getHeight() / 2);
			}
			sexo.setPosition(posicion.x - sexo.getWidth() / 4, posicion.y
					- sexo.getHeight());
			sp_feromona.setPosition(
					this.posicion.x + cuerpo.getWidth() / 2
							- sp_feromona.getWidth() / 2,
					this.posicion.y + cuerpo.getHeight() / 2
							- sp_feromona.getHeight() / 2);

			// transferido.setPosition(posicion.x-2.5f,posicion.y-2.5f);

			borde.x = posicion.x;
			borde.y = posicion.y;

			// direccion.x= (float) Math.sqrt((this.SPEED*this.SPEED) -
			// (direccion.y*direccion.y));
	if (m.verFrontera==false) {
			if (posicion.x < 0) {
				posicion.x = m.ancho-ancho;
				//direccion.x = direccion.x * (-1);
			}
			if (posicion.x > m.ancho) {
				posicion.x = 0+ancho;
				//direccion.x = direccion.x * (-1);
			}
	}
	if (m.verFrontera== true) {
		if (posicion.x < 0) {
			posicion.x = 1;
			direccion.x = direccion.x * (-1);
		}
		if (posicion.x > m.ancho) {
			posicion.x = m.ancho -ancho;
			direccion.x = direccion.x * (-1);
		}
}

			if (posicion.y < 0) {
				posicion.y = m.alto-alto;
				//direccion.y = direccion.y * (-1);
			}
			if (posicion.y > m.alto) {
				posicion.y = 0 + alto;
				//direccion.y = direccion.y * (-1);
			}
			// System.out.println("Speed " + SPEED);

		}
	}

	public void metabolismo() {

		if (deltaTime() > msecondTime(100)) {

			float movX = direccion.x * speed;
			float movY = direccion.y * speed;

			float movh = (float) Math.sqrt((movX * movX) + (movY * movY));

			cuadradoTemp = (tempOptima - m.temperatura)
					* (tempOptima - m.temperatura);

			// System.out.println(cuadradoTemp);

			if (cuadradoTemp <= 1) {
				energia = energia
						- (float) (ancho * alto)
						/ 150f
						- (0.5f * (float) (ancho * alto) * (movh * movh) / 350000f)
						* m.zoom;

			}

			if (cuadradoTemp > 1) {

				energia = (float) energia
						- (float) ((ancho * alto) / 400f)
						* cuadradoTemp
						- (0.5f * (float) (ancho * alto) * (movh * movh) / 35000f)
						* m.zoom * cuadradoTemp;

				// System.out.println((float)(ancho*alto)/10000f*cuadradoTemp);

			}
			setTime();
		}

		if (energia <= 0) {

			morir();

		}
	}// (Math.random()*(Max-Min))+Min)

	public void aleatorio() {

		if (delta3Time() > msecondTime((long) (Math.random() * (20000 - 5000)) + 5000)) {

			direccion.x = (float) (Math.random() * (10 + 10) - 10);
			direccion.y = (float) (Math.random() * (10 + 10) - 10);

			if (direccion.x > 10) {
				direccion.x = 10;
			}
			if (direccion.x < -10) {
				direccion.x = -10;
			}
			if (direccion.y > 10) {
				direccion.y = 10;
			}
			if (direccion.y < -10) {
				direccion.y = -10;
			}

			setDelta3();

		}

	}

	public void escannearObjetos() {

		if (sentir == true) {
			for (int i = 0; i < aObjetos.size; i++) {
				aObjetos.removeIndex(i);
			}// limpia la lista par un nuevo escaneo
			for (int i = 0; i < aPredadores.size; i++) {
				aPredadores.removeIndex(i);
			}// limpia la lista par un nuevo escaneo
			if (male == true) {
				for (int i = 0; i < aHembras.size; i++) {
					aHembras.removeIndex(i);
				}
			}

			// si la frontera no esta activada

			if (m.verFrontera == false) {
				if (carnivoro == false) {

					for (Qenergia qe : m.aqe) {

						if (qe.visible == true) {

							float qX = qe.posicion.x;
							float qY = qe.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivoro == true) {

					for (int i = 0; i < m.aorg.size; i++) {
						Organismo or = m.aorg.get(i);

						if (this != or && identificador != or.identificador
								&& capacidad > or.capacidad) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

				if (male == true) {

					for (int i = 0; i < m.aorg.size; i++) {
						Organismo or = m.aorg.get(i);

						if (or.male == false
								&& or.segundos >= or.tiempoMitosis / 1000
								&& (or.biomasa / or.capacidad) > 0.8) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente * 3) {
								aHembras.add(or);
							}

							// System.out.println("hembra detectada"+
							// aHembras.size);

						}
					}
				}
			}

			// si la frontera esta activada

			// si la frontera esta activada

			if (m.verFrontera == true) {
				// panel izquierdo

				if (carnivoro == false && this.posicion.x < m.ancho / 2) { // si
																			// el
																			// organismo
																			// esta
																			// a
																			// la
																			// izquierda

					for (Qenergia qeI : m.aqe) {

						if (qeI.posicion.x < m.ancho / 2 && qeI.visible == true) {

							float qX = qeI.posicion.x;
							float qY = qeI.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivoro == true && this.posicion.x < m.ancho / 2) {

					for (int i = 0; i < m.aorg.size; i++) {
						Organismo or = m.aorg.get(i);

						if (or.posicion.x < m.ancho / 2 && this != or
								&& identificador != or.identificador
								&& capacidad > or.capacidad) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

				// panel Derecho

				if (carnivoro == false && this.posicion.x > m.ancho / 2) { // si
																			// el
																			// organismo
																			// esta
																			// a
																			// la
																			// Derecha

					for (Qenergia qeD : m.aqe) {

						if (qeD.posicion.x > m.ancho / 2 && qeD.visible == true) {

							float qX = qeD.posicion.x;
							float qY = qeD.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivoro == true && this.posicion.x > m.ancho / 2) {

					for (int i = 0; i < m.aorg.size; i++) {
						Organismo or = m.aorg.get(i);

						if (or.posicion.x > m.ancho / 2 && this != or
								&& identificador != or.identificador
								&& capacidad > or.capacidad) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

			}
			aObjetos.shrink();
			aPredadores.shrink();

		}

	}

	public void escapar() {

		if (aPredadores.size > 0) {

			for (Vector2 v2 : aPredadores) {

				float qX = v2.x;
				float qY = v2.y;

				float Dx = posicion.x - qX;
				float Dy = posicion.y - qY;

				float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

				float cosX = Dx / h;
				float senY = Dy / h;

				float fh = 100 / (h);

				

				direccion.x = direccion.x + (cosX * fh / 1);
				direccion.y = direccion.y + (senY * fh / 1);

				// System.out.println("Objetos vistos: "+ aObjetos.size);

			}
		}
		if (direccion.x > 10) {
			direccion.x = 10;
		}
		if (direccion.x < -10) {
			direccion.x = -10;
		}
		if (direccion.y > 10) {
			direccion.y = 10;
		}
		if (direccion.y < -10) {
			direccion.y = -10;
		}
		// System.out.println("dirX: "+direccion.x + " dirY: "+ direccion.y);
	}

	public void buscarHembra() {

		if (segundos >= tiempoMitosis / 1000 && (biomasa / capacidad) > 0.8) { // el
																				// macho
																				// tiene
																				// que
																				// estar
																				// maduro

			if (aHembras.size > 0) {

				int size = aHembras.size;

				for (int i = size - 1; i > 0; i--) {

					Organismo or = aHembras.get(i);

					if (or.segundos >= tiempoMitosis / 1000) {// la hembra tien
																// que estar
																// maduro

						float qX = or.posicion.x;
						float qY = or.posicion.y;

						float Dx = posicion.x - qX;
						float Dy = posicion.y - qY;

						float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

						float cosX = Dx / h;
						float senY = Dy / h;

						float fh = or.feromona / (h);

						direccion.x = direccion.x - (cosX * fh / 1);
						direccion.y = direccion.y - (senY * fh / 1);

					

					}
					if (direccion.x > 10) {
						direccion.x = 10;
					}
					if (direccion.x < -10) {
						direccion.x = -10;
					}
					if (direccion.y > 10) {
						direccion.y = 10;
					}
					if (direccion.y < -10) {
						direccion.y = -10;
					}
					
				}
			}
		}

	}

	public void cazarObjetos() {

		if (capacidad > 0 && (100 * biomasa) / capacidad < 80) {

			if (aObjetos.size > 0) {

				for (Vector2 v2 : aObjetos) {

					float qX = v2.x;
					float qY = v2.y;

					float Dx = posicion.x - qX;
					float Dy = posicion.y - qY;

					float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

					float cosX = Dx / h;
					float senY = Dy / h;

					float fh = 100 / (h);

					

					direccion.x = direccion.x - (cosX * fh / 1);
					direccion.y = direccion.y - (senY * fh / 1);

					// System.out.println("Objetos vistos: "+ aObjetos.size);

				}
			}
			if (direccion.x > 10) {
				direccion.x = 10;
			}
			if (direccion.x < -10) {
				direccion.x = -10;
			}
			if (direccion.y > 10) {
				direccion.y = 10;
			}
			if (direccion.y < -10) {
				direccion.y = -10;
			}
			
		}
	}
	
	public void enfermar() {//el organismo se enfermó
		
		
		
		
	}

	public void morir() {

		direccion.x = 0;
		direccion.y = 0;

		if (muriendo == 0) {

			if (alto <= 1 || ancho <= 1) {
				m.borrarOrganismo(this);
			}

			if (alto > 1 && ancho > 1) {

				if (posicion.x < m.ancho / 2) {// el organismo se encuentra en
												// elpanel izquierdo

					for (int i = 0; i < m.aqe.size; i++) {

						Qenergia qe = m.aqe.get(i);

						if (qe.posicion.x < m.ancho / 2 && qe.visible == false) {

							if (biomasa > m.Qbiomasa) {

								qe.visible = true;

								qe.update();

								biomasa = biomasa - m.Qbiomasa;

							}

							if (biomasa <= m.Qbiomasa) {

								qe.visible = true;

								qe.update();

								biomasa = 0;
							}

							if (biomasa <= 0) {
								i = m.aqe.size;
							}
						}
					}
				}

				if (posicion.x > m.ancho / 2) {// el organismo se encuentra en
												// el panel Derecho

					for (int i = 0; i < m.aqe.size; i++) {

						Qenergia qe = m.aqe.get(i);

						if (qe.posicion.x > m.ancho / 2 && qe.visible == false) {

							if (biomasa > m.QbiomasaR) {

								qe.visible = true;
								// qe.posicion.x= this.posicion.x;
								// qe.posicion.y= this.posicion.y;
								qe.update();

								biomasa = biomasa - m.Qbiomasa;
								qe.masa = m.QbiomasaR;

							}

							if (biomasa <= m.QbiomasaR) {

								qe.visible = true;
								qe.masa = m.QbiomasaR;

								qe.update();

								biomasa = 0;
							}

							if (biomasa <= 0) {
								i = m.aqe.size;
							}
						}
					}
				}
			}
		}

		m.borrarOrganismo(this);

	}

	public void intoxicado() {

		if (m.antibiotico == 1 && this.resistenciaATB == false) {

			intoxicado = true;
		}

		if (intoxicado = true) {

			this.contadorTiempoToxico();
		}
		if (segundos2 > 4) {
			morir();
		}

	}
	
	
	public void enfermedad() {//evolucion de la enfermedad

		
		
		//this.contadorTiempoEnfermo();
		
		//if (segundos3 > 10) {
			
		///int random = (int) (Math.random()*100);//lo dejamos a la suerte
		
		//	if(random <= m.virus.mortalidad) {morir();} //se muere
		//	if(random > m.virus.mortalidad) {infectado = false; inmune=true;}//vive y queda inmune
		//}

	}

	public void apoptosis() {

		if (segundos >= longevidad / 1000) {

			morir();

		}

	}

	
	// determina la distribucion de alelos

	public void crossOver() {

		boolean cross = false;
		int chance;
		StringBuffer buffer = new StringBuffer();

		int i = m.gap1 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap1 * 2) {
			cross = true;
		}

		if (cross == true) {
			buffer.replace(0, buffer.length(), ch2.resistenciaATB.toString());
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					ch3.resistenciaATB.toString());
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					buffer.toString());

		}

		i = m.gap2 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap2 * 2) {
			cross = true;
		}

		if (cross == true) {
			buffer.replace(0, buffer.length(), ch2.resistenciaATB.toString());
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					ch3.resistenciaATB.toString());
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.ancho.toString());
			ch2.ancho.replace(0, ch2.ancho.length(), ch3.ancho.toString());
			ch3.ancho.replace(0, ch3.ancho.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.alto.toString());
			ch2.alto.replace(0, ch2.alto.length(), ch3.alto.toString());
			ch3.alto.replace(0, ch3.alto.length(), buffer.toString());
		}

		i = m.gap3 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap3 * 2) {
			cross = true;
		}

		if (cross == true) {
			buffer.replace(0, buffer.length(), ch2.resistenciaATB.toString());
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					ch3.resistenciaATB.toString());
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.ancho.toString());
			ch2.ancho.replace(0, ch2.ancho.length(), ch3.ancho.toString());
			ch3.ancho.replace(0, ch3.ancho.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.alto.toString());
			ch2.alto.replace(0, ch2.alto.length(), ch3.alto.toString());
			ch3.alto.replace(0, ch3.alto.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.sentir.toString());
			ch2.sentir.replace(0, ch2.sentir.length(), ch3.sentir.toString());
			ch3.sentir.replace(0, ch3.sentir.length(), buffer.toString());
		}

		i = m.gap4 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap4 * 2) {
			cross = true;
		}

		if (cross == true) {
			buffer.replace(0, buffer.length(), ch2.resistenciaATB.toString());
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					ch3.resistenciaATB.toString());
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.ancho.toString());
			ch2.ancho.replace(0, ch2.ancho.length(), ch3.ancho.toString());
			ch3.ancho.replace(0, ch3.ancho.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.alto.toString());
			ch2.alto.replace(0, ch2.alto.length(), ch3.alto.toString());
			ch3.alto.replace(0, ch3.alto.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.sentir.toString());
			ch2.sentir.replace(0, ch2.sentir.length(), ch3.sentir.toString());
			ch3.sentir.replace(0, ch3.sentir.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.toleranciaTemp.toString());
			ch2.toleranciaTemp.replace(0, ch2.toleranciaTemp.length(),
					ch3.toleranciaTemp.toString());
			ch3.toleranciaTemp.replace(0, ch3.toleranciaTemp.length(),
					buffer.toString());
		}

		i = m.gap6 * 2;

		i--;
		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap6 * 2) {
			cross = true;
		}

		if (cross == true) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
		}

		i = m.gap7 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap7 * 2) {
			cross = true;
		}

		if (cross == true) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.cazar.toString());
			ch6.cazar.replace(0, ch6.cazar.length(), ch7.cazar.toString());
			ch7.cazar.replace(0, ch7.cazar.length(), buffer.toString());
		}

		i = m.gap8 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap8 * 2) {
			cross = true;
		}

		if (cross == true) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.cazar.toString());
			ch6.cazar.replace(0, ch6.cazar.length(), ch7.cazar.toString());
			ch7.cazar.replace(0, ch7.cazar.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escapar.toString());
			ch6.escapar
					.replace(0, ch6.escapar.length(), ch7.escapar.toString());
			ch7.escapar.replace(0, ch7.escapar.length(), buffer.toString());

		}

		i = m.gap9 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap9 * 2) {
			cross = true;
		}

		if (cross == true) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.cazar.toString());
			ch6.cazar.replace(0, ch6.cazar.length(), ch7.cazar.toString());
			ch7.cazar.replace(0, ch7.cazar.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escapar.toString());
			ch6.escapar
					.replace(0, ch6.escapar.length(), ch7.escapar.toString());
			ch7.escapar.replace(0, ch7.escapar.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.radioConsiente.toString());
			ch6.radioConsiente.replace(0, ch6.radioConsiente.length(),
					ch7.radioConsiente.toString());
			ch7.radioConsiente.replace(0, ch7.radioConsiente.length(),
					buffer.toString());

		}

		i = m.gap92 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < m.gap92 * 2) {
			cross = true;
		}

		if (cross == true) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.cazar.toString());
			ch6.cazar.replace(0, ch6.cazar.length(), ch7.cazar.toString());
			ch7.cazar.replace(0, ch7.cazar.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escapar.toString());
			ch6.escapar
					.replace(0, ch6.escapar.length(), ch7.escapar.toString());
			ch7.escapar.replace(0, ch7.escapar.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.radioConsiente.toString());
			ch6.radioConsiente.replace(0, ch6.radioConsiente.length(),
					ch7.radioConsiente.toString());
			ch7.radioConsiente.replace(0, ch7.radioConsiente.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch6.feromona.toString());
			ch6.feromona.replace(0, ch6.feromona.length(),
					ch7.feromona.toString());
			ch7.feromona.replace(0, ch7.feromona.length(), buffer.toString());
		}

		// cromosomas 3

		i = m.gap10 * 2;
		if (male == false) {

			cross = false;
			chance = (int) (Math.random() * 100);

			if (chance < m.gap10 * 2) {
				cross = true;
			}

			if (cross == true) {

				buffer.replace(0, buffer.length(), ch10.tasaMutacion.toString());
				ch10.tasaMutacion.replace(0, ch10.tasaMutacion.length(),
						ch11.tasaMutacion.toString());
				ch11.tasaMutacion.replace(0, ch11.tasaMutacion.length(),
						buffer.toString());

			}

			i = m.gap11 * 2;

			cross = false;
			chance = (int) (Math.random() * 100);
			if (chance < m.gap11 * 2) {
				cross = true;
			}

			if (cross == true) {

				buffer.replace(0, buffer.length(), ch10.tasaMutacion.toString());
				ch10.tasaMutacion.replace(0, ch10.tasaMutacion.length(),
						ch11.tasaMutacion.toString());
				ch11.tasaMutacion.replace(0, ch11.tasaMutacion.length(),
						buffer.toString());
				buffer.replace(0, buffer.length(), ch10.longevidad.toString());
				ch10.longevidad.replace(0, ch10.longevidad.length(),
						ch11.longevidad.toString());
				ch11.longevidad.replace(0, ch11.longevidad.length(),
						buffer.toString());

			}

		}

	}

	public void replication() {

		if (m.mutarResistencia == true) {
			ch1.resistenciaATB.replace(0, ch1.resistenciaATB.length(),
					replicar(adn.resistenciaATB));
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					replicar(adn.resistenciaATB));
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					replicar(adn2.resistenciaATB));
			ch4.resistenciaATB.replace(0, ch4.resistenciaATB.length(),
					replicar(adn2.resistenciaATB));
		}

		if (m.mutarSize == true) {
			ch1.ancho.replace(0, ch1.ancho.length(), replicar(adn.ancho));
			ch2.ancho.replace(0, ch2.ancho.length(), replicar(adn.ancho));
			ch3.ancho.replace(0, ch3.ancho.length(), replicar(adn2.ancho));
			ch4.ancho.replace(0, ch4.ancho.length(), replicar(adn2.ancho));
		}

		if (m.mutarSize == true) {
			ch1.alto.replace(0, ch1.alto.length(), replicar(adn.alto));
			ch2.alto.replace(0, ch2.alto.length(), replicar(adn.alto));
			ch3.alto.replace(0, ch3.alto.length(), replicar(adn2.alto));
			ch4.alto.replace(0, ch4.alto.length(), replicar(adn2.alto));
		}

		if (m.mutarSpeed == true) {
			ch5.speed.replace(0, ch5.speed.length(), replicar(adn3.speed));
			ch6.speed.replace(0, ch6.speed.length(), replicar(adn3.speed));
			ch7.speed.replace(0, ch7.speed.length(), replicar(adn4.speed));
			ch8.speed.replace(0, ch8.speed.length(), replicar(adn4.speed));
		}

		if (m.mutarTemp == true) {
			ch1.toleranciaTemp.replace(0, ch1.toleranciaTemp.length(),
					replicar(adn.toleranciaTemp));
			ch2.toleranciaTemp.replace(0, ch2.toleranciaTemp.length(),
					replicar(adn.toleranciaTemp));
			ch3.toleranciaTemp.replace(0, ch3.toleranciaTemp.length(),
					replicar(adn2.toleranciaTemp));
			ch4.toleranciaTemp.replace(0, ch4.toleranciaTemp.length(),
					replicar(adn2.toleranciaTemp));
		}

		if (m.mutarPredador == true) {
			ch1.predador.replace(0, ch1.predador.length(),
					replicar(adn.predador));
			ch2.predador.replace(0, ch2.predador.length(),
					replicar(adn.predador));
			ch3.predador.replace(0, ch3.predador.length(),
					replicar(adn2.predador));
			ch4.predador.replace(0, ch4.predador.length(),
					replicar(adn2.predador));
		}

		if (m.mutarSentir == true) {
			ch1.sentir.replace(0, ch1.sentir.length(), replicar(adn.sentir));
			ch2.sentir.replace(0, ch2.sentir.length(), replicar(adn.sentir));
			ch3.sentir.replace(0, ch3.sentir.length(), replicar(adn2.sentir));
			ch4.sentir.replace(0, ch4.sentir.length(), replicar(adn2.sentir));
		}

		if (m.mutarCazar == true) {
			ch5.cazar.replace(0, ch5.cazar.length(), replicar(adn3.cazar));
			ch6.cazar.replace(0, ch6.cazar.length(), replicar(adn3.cazar));
			ch7.cazar.replace(0, ch7.cazar.length(), replicar(adn4.cazar));
			ch8.cazar.replace(0, ch8.cazar.length(), replicar(adn4.cazar));
		}

		if (m.mutarEscapar == true) {
			ch5.escapar
					.replace(0, ch5.escapar.length(), replicar(adn3.escapar));
			ch6.escapar
					.replace(0, ch6.escapar.length(), replicar(adn3.escapar));
			ch7.escapar
					.replace(0, ch7.escapar.length(), replicar(adn4.escapar));
			ch8.escapar
					.replace(0, ch8.escapar.length(), replicar(adn4.escapar));

		}
		if (m.mutarRadioconsiente == true) {
			ch5.radioConsiente.replace(0, ch5.radioConsiente.length(),
					replicar(adn3.radioConsiente));
			ch6.radioConsiente.replace(0, ch6.radioConsiente.length(),
					replicar(adn3.radioConsiente));
			ch7.radioConsiente.replace(0, ch7.radioConsiente.length(),
					replicar(adn4.radioConsiente));
			ch8.radioConsiente.replace(0, ch8.radioConsiente.length(),
					replicar(adn4.radioConsiente));
		}
		if (m.mutarFeromona == true) {
			ch5.feromona.replace(0, ch5.feromona.length(),
					replicar(adn3.feromona));
			ch6.feromona.replace(0, ch6.feromona.length(),
					replicar(adn3.feromona));
			ch7.feromona.replace(0, ch7.feromona.length(),
					replicar(adn4.feromona));
			ch8.feromona.replace(0, ch8.feromona.length(),
					replicar(adn4.feromona));
		}
		if (m.mutarParteNoGen == true) {
			ch5.parteNoGen.replace(0, ch5.parteNoGen.length(),
					replicar(adn3.parteNoGen));
			ch6.parteNoGen.replace(0, ch6.parteNoGen.length(),
					replicar(adn3.parteNoGen));
			ch7.parteNoGen.replace(0, ch7.parteNoGen.length(),
					replicar(adn4.parteNoGen));
			ch8.parteNoGen.replace(0, ch8.parteNoGen.length(),
					replicar(adn4.parteNoGen));
		}
		if (m.mutarTasaMut == true) {
			ch9.tasaMutacion.replace(0, ch9.tasaMutacion.length(),
					replicar(adn5.tasaMutacion));
			ch10.tasaMutacion.replace(0, ch10.tasaMutacion.length(),
					replicar(adn5.tasaMutacion));
			if (male == false) {
				ch11.tasaMutacion.replace(0, ch11.tasaMutacion.length(),
						replicar(adn6.tasaMutacion));
				ch12.tasaMutacion.replace(0, ch12.tasaMutacion.length(),
						replicar(adn6.tasaMutacion));
			}
		}
		if (m.mutarLongevidad == true) {
			ch9.longevidad.replace(0, ch9.longevidad.length(),
					replicar(adn5.longevidad));
			ch10.longevidad.replace(0, ch10.longevidad.length(),
					replicar(adn5.longevidad));
			if (male == false) {
				ch11.longevidad.replace(0, ch11.longevidad.length(),
						replicar(adn6.longevidad));
				ch12.longevidad.replace(0, ch12.longevidad.length(),
						replicar(adn6.longevidad));
			}
		}

		if (m.mutarColor == true) {
			ch9.color.replace(0, ch9.color.length(), replicar(adn5.color));
			ch10.color.replace(0, ch10.color.length(), replicar(adn5.color));
			if (male == false) {
				ch11.color
						.replace(0, ch11.color.length(), replicar(adn6.color));
				ch12.color
						.replace(0, ch12.color.length(), replicar(adn6.color));
			}

		}

		if (m.mutarResistencia == false) {
			ch1.resistenciaATB.replace(0, ch1.resistenciaATB.length(),
					adn.resistenciaATB.toString());
			ch2.resistenciaATB.replace(0, ch2.resistenciaATB.length(),
					adn.resistenciaATB.toString());
			ch3.resistenciaATB.replace(0, ch3.resistenciaATB.length(),
					adn2.resistenciaATB.toString());
			ch4.resistenciaATB.replace(0, ch4.resistenciaATB.length(),
					adn2.resistenciaATB.toString());
		}

		if (m.mutarSize == false) {
			ch1.ancho.replace(0, ch1.ancho.length(), adn.ancho.toString());
			ch2.ancho.replace(0, ch2.ancho.length(), adn.ancho.toString());
			ch3.ancho.replace(0, ch3.ancho.length(), adn2.ancho.toString());
			ch4.ancho.replace(0, ch4.ancho.length(), adn2.ancho.toString());
		}

		if (m.mutarSize == false) {
			ch1.alto.replace(0, ch1.alto.length(), adn.alto.toString());
			ch2.alto.replace(0, ch2.alto.length(), adn.alto.toString());
			ch3.alto.replace(0, ch3.alto.length(), adn2.alto.toString());
			ch4.alto.replace(0, ch4.alto.length(), adn2.alto.toString());
		}

		if (m.mutarTemp == false) {
			ch1.toleranciaTemp.replace(0, ch1.toleranciaTemp.length(),
					adn.toleranciaTemp.toString());
			ch2.toleranciaTemp.replace(0, ch2.toleranciaTemp.length(),
					adn.toleranciaTemp.toString());
			ch3.toleranciaTemp.replace(0, ch3.toleranciaTemp.length(),
					adn2.toleranciaTemp.toString());
			ch4.toleranciaTemp.replace(0, ch4.toleranciaTemp.length(),
					adn2.toleranciaTemp.toString());
		}

		if (m.mutarPredador == false) {
			ch1.predador.replace(0, ch1.predador.length(),
					adn.predador.toString());
			ch2.predador.replace(0, ch2.predador.length(),
					adn.predador.toString());
			ch3.predador.replace(0, ch3.predador.length(),
					adn2.predador.toString());
			ch4.predador.replace(0, ch4.predador.length(),
					adn2.predador.toString());
		}

		if (m.mutarSentir == false) {
			ch1.sentir.replace(0, ch1.sentir.length(), adn.sentir.toString());
			ch2.sentir.replace(0, ch2.sentir.length(), adn.sentir.toString());
			ch3.sentir.replace(0, ch3.sentir.length(), adn2.sentir.toString());
			ch4.sentir.replace(0, ch4.sentir.length(), adn2.sentir.toString());
		}

		if (m.mutarSpeed == false) {
			ch5.speed.replace(0, ch5.speed.length(), adn3.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), adn3.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), adn4.speed.toString());
			ch8.speed.replace(0, ch8.speed.length(), adn4.speed.toString());
		}

		if (m.mutarCazar == false) {
			ch5.cazar.replace(0, ch5.cazar.length(), adn3.cazar.toString());
			ch6.cazar.replace(0, ch6.cazar.length(), adn3.cazar.toString());
			ch7.cazar.replace(0, ch7.cazar.length(), adn4.cazar.toString());
			ch8.cazar.replace(0, ch8.cazar.length(), adn4.cazar.toString());
		}

		if (m.mutarEscapar == false) {
			ch5.escapar.replace(0, ch5.escapar.length(),
					adn3.escapar.toString());
			ch6.escapar.replace(0, ch6.escapar.length(),
					adn3.escapar.toString());
			ch7.escapar.replace(0, ch7.escapar.length(),
					adn4.escapar.toString());
			ch8.escapar.replace(0, ch8.escapar.length(),
					adn4.escapar.toString());

		}
		if (m.mutarRadioconsiente == false) {
			ch5.radioConsiente.replace(0, ch5.radioConsiente.length(),
					adn3.radioConsiente.toString());
			ch6.radioConsiente.replace(0, ch6.radioConsiente.length(),
					adn3.radioConsiente.toString());
			ch7.radioConsiente.replace(0, ch7.radioConsiente.length(),
					adn4.radioConsiente.toString());
			ch8.radioConsiente.replace(0, ch8.radioConsiente.length(),
					adn4.radioConsiente.toString());
		}
		if (m.mutarFeromona == false) {
			ch5.feromona.replace(0, ch5.feromona.length(),
					adn3.feromona.toString());
			ch6.feromona.replace(0, ch6.feromona.length(),
					adn3.feromona.toString());
			ch7.feromona.replace(0, ch7.feromona.length(),
					adn4.feromona.toString());
			ch8.feromona.replace(0, ch8.feromona.length(),
					adn4.feromona.toString());
		}
		if (m.mutarParteNoGen == false) {
			ch5.parteNoGen.replace(0, ch5.parteNoGen.length(),
					adn3.parteNoGen.toString());
			ch6.parteNoGen.replace(0, ch6.parteNoGen.length(),
					adn3.parteNoGen.toString());
			ch7.parteNoGen.replace(0, ch7.parteNoGen.length(),
					adn4.parteNoGen.toString());
			ch8.parteNoGen.replace(0, ch8.parteNoGen.length(),
					adn4.parteNoGen.toString());

		}

		if (m.mutarTasaMut == false) {
			ch9.tasaMutacion.replace(0, ch9.tasaMutacion.length(),
					adn5.tasaMutacion.toString());
			ch10.tasaMutacion.replace(0, ch10.tasaMutacion.length(),
					adn5.tasaMutacion.toString());
			if (male == false) {
				ch11.tasaMutacion.replace(0, ch11.tasaMutacion.length(),
						adn6.tasaMutacion.toString());
				ch12.tasaMutacion.replace(0, ch12.tasaMutacion.length(),
						adn6.tasaMutacion.toString());
			}
		}
		if (m.mutarLongevidad == false) {
			ch9.longevidad.replace(0, ch9.longevidad.length(),
					adn5.longevidad.toString());
			ch10.longevidad.replace(0, ch10.longevidad.length(),
					adn5.longevidad.toString());
			if (male == false) {
				ch11.longevidad.replace(0, ch11.longevidad.length(),
						adn6.longevidad.toString());
				ch12.longevidad.replace(0, ch12.longevidad.length(),
						adn6.longevidad.toString());
			}
		}

		if (m.mutarColor == false) {
			ch9.color.replace(0, ch9.color.length(), adn5.color.toString());
			ch10.color.replace(0, ch10.color.length(), adn5.color.toString());
			if (male == false) {
				ch11.color.replace(0, ch11.color.length(),
						adn6.color.toString());
				ch12.color.replace(0, ch12.color.length(),
						adn6.color.toString());
			}
		}

	}

	public void gametoGenesis() {// coping the genome

		replication();
		crossOver();

	}

	public Genoma randomCigoto() {

		Genoma cigoto = new Genoma(m, 1, 1, 1, 0, 0, 0);
		int a = (int) (Math.random() * 2000);

		if (a <= 500) {
			cigoto = ch1;
		}
		if (a > 500 && a <= 1000) {
			cigoto = ch4;
		}
		if (a > 1000 && a <= 1500) {
			cigoto = ch3;
		}
		if (a > 1500 && a <= 2000) {
			cigoto = ch2;
		}

		return cigoto;

	}

	public Genoma randomCigoto2() {

		Genoma cigoto = new Genoma(m, 0, 0, 0, 0, 0, 0);
		int a = (int) (Math.random() * 2000);

		if (a <= 500) {
			cigoto = ch5;
		}
		if (a > 500 && a <= 1000) {
			cigoto = ch8;
		}
		if (a > 1000 && a <= 1500) {
			cigoto = ch7;
		}
		if (a > 1500 && a <= 2000) {
			cigoto = ch6;
		}

		return cigoto;

	}

	public Genoma randomCigoto3() {

		Genoma cigoto = new Genoma(m, 0, 0, 0);
		int a = (int) (Math.random() * 2000);
		if (male == false) {
			if (a <= 500) {
				cigoto = ch9;
			}
			if (a > 500 && a <= 1000) {
				cigoto = ch12;
			}
			if (a > 1000 && a <= 1500) {
				cigoto = ch11;
			}
			if (a > 1500 && a <= 2000) {
				cigoto = ch10;
			}
		}

		if (male == true) {

			if (a <= 1000) {
				cigoto = ch9;
			}
			if (a > 1000 && a <= 2000) {
				cigoto = ch10;
			}

		}

		return cigoto;

	}

	// Fecundacion

	public void Fecundation(Organismo o1, Organismo o2) {

		float frac = o1.Identificar(m) / o2.Identificar(m);

		if (frac > 1) {
			frac = 1 / frac;
		}

		int masatotal = (int) (m.BiomasaTotal(m.aorg) + m.MateriaLibre());
		if (o1.capacidad > 1 && o2.capacidad > 1
				&& o1.carnivoro == o2.carnivoro && m.aorg.size < m.maximo
				&& masatotal <= (m.Masatotal + 100)) {

			if (o1.biomasa > o1.capacidad / 2 && o2.biomasa > o2.capacidad / 2) {

				o1.gametoGenesis();
				o2.gametoGenesis();

				StringBuffer sb = new StringBuffer(o1.nombre.toString());
				StringBuffer sb2 = new StringBuffer(o1.nombre.toString());
				StringBuffer sb3 = new StringBuffer(o2.nombre.toString());
				StringBuffer sb4 = new StringBuffer(o2.nombre.toString());

				Vector2 pos = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);

				float x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir = new Vector2(seno, coseno);

				Vector2 pos2 = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir2 = new Vector2(seno, coseno);

				Vector2 pos3 = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir3 = new Vector2(seno, coseno);

				Vector2 pos4 = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir4 = new Vector2(seno, coseno);

				boolean sex = true;
				int gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				Organismo h1 = new Organismo(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos, sb, m);
				h1.male = sex;
				h1.direccion = dir;
				h1.energia = o1.energia / 5;
				h1.biomasa = o1.biomasa / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				Organismo h2 = new Organismo(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos2, sb2, m);
				h2.male = sex;
				h2.direccion = dir2;
				h2.energia = o1.energia / 5;
				h2.biomasa = o1.biomasa / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				Organismo h3 = new Organismo(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos3, sb3, m);

				h3.direccion = dir3;
				h3.energia = o2.energia / 5;
				h3.biomasa = o2.biomasa / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				Organismo h4 = new Organismo(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos4, sb4, m);
				h4.male = sex;
				h4.direccion = dir4;
				h4.energia = o2.energia / 5;
				h4.biomasa = o2.biomasa / 5;

				o1.energia = o1.energia / 5; // la hembra paga elcosto
												// energetico
				o1.biomasa = o1.biomasa / 5;

				m.aorg.add(h1);
				m.aorg.add(h2);
				m.aorg.add(h3);
				m.aorg.add(h4);

			}
		}
	}

	public void ReproduccionParteNoGen(Organismo o1) {// Con cortejo

		int masatotal = (int) (m.BiomasaTotal(m.aorg) + m.MateriaLibre());

		if (o1.capacidad > 1 && m.aorg.size < m.maximo
				&& masatotal <= (m.Masatotal + 100)) {

			if (o1.biomasa > o1.capacidad / 2) {

				o1.gametoGenesis();

				StringBuffer sb = new StringBuffer(o1.nombre.toString());
				StringBuffer sb2 = new StringBuffer(o1.nombre.toString());

				Vector2 pos = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);

				float x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir = new Vector2(seno, coseno);

				Vector2 pos2 = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir2 = new Vector2(seno, coseno);

				Organismo h1 = new Organismo(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos, sb, m);

				h1.direccion = dir;
				h1.energia = o1.energia / 5;
				h1.biomasa = o1.biomasa / 5;

				Organismo h2 = new Organismo(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos2, sb2, m);

				h2.direccion = dir2;
				h2.energia = o1.energia / 5;
				h2.biomasa = o1.biomasa / 5;

				o1.energia = o1.energia / 5;
				o1.biomasa = o1.biomasa / 5;

				m.aorg.add(h1);
				m.aorg.add(h2);

				o1.tiempoCiclo = 0;

			}
		}
	}

	public void ReproduccionParteNoGenSinCortejo(Organismo o1) {// Sin cortejo

		int masatotal = (int) (m.BiomasaTotal(m.aorg) + m.MateriaLibre());

		if (o1.capacidad > 1 && m.aorg.size < m.maximo
				&& masatotal <= (m.Masatotal + 100)) {

			if (o1.biomasa > o1.capacidad / 2) {

				o1.gametoGenesis();

				StringBuffer sb = new StringBuffer(o1.nombre.toString());
				StringBuffer sb2 = new StringBuffer(o1.nombre.toString());

				Vector2 pos = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);

				float x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir = new Vector2(seno, coseno);

				Vector2 pos2 = new Vector2((float) posicion.x + ((ancho) + 3)
						/ 2, (float) posicion.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				Vector2 dir2 = new Vector2(seno, coseno);

				Organismo h1 = new Organismo(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos, sb, m);

				h1.direccion = dir;
				h1.energia = o1.energia / 5;
				h1.biomasa = o1.biomasa / 5;

				Organismo h2 = new Organismo(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos2, sb2, m);

				h2.direccion = dir2;
				h2.energia = o1.energia / 5;
				h2.biomasa = o1.biomasa / 5;

				o1.energia = o1.energia / 5;
				o1.biomasa = o1.biomasa / 5;

				m.aorg.add(h1);

				o1.tiempoCiclo = 0;

			}
		}
	}

	// mide los segundos transcurrido desde su creación

	public void contadorTiempo() {

		if (delta2Time() > msecondTime(1000)) {

			segundos = segundos + 1;
			tiempoCiclo = tiempoCiclo + 1;

			setDelta();
		}
	}
	
	public void contadorTiempoEnfermo() {

		if (deltaEnfermo() > msecondTime(1000)) {

			segundos3 = segundos3 + 1;

			setDeltaEnfermo();
		}

	}

	public void contadorTiempoToxico() {

		if (deltaToxico() > msecondTime(1000)) {

			segundos2 = segundos2 + 1;

			setDeltaToxico();
		}

	}

	// mide el tiempo transcurrido desde el unltimo set

	public long deltaTime() {
		return TimeUtils.nanoTime() - delta;
	}

	public long edadTime() {
		return TimeUtils.nanoTime() - edad;
	}

	public long delta2Time() {
		return TimeUtils.nanoTime() - delta2;
	}

	public long deltaToxico() {
		return TimeUtils.nanoTime() - deltaToxico;
	}
	
	public long deltaEnfermo() {
		return TimeUtils.nanoTime() - deltaEnfermo;
	}

	public long delta3Time() {
		return TimeUtils.nanoTime() - delta3;
	}

	// anota el tiempo de juego transcurrido en el momento que se invoca un
	// evento

	public void setTime() {
		delta = TimeUtils.nanoTime();
	}

	public void setEdad() {
		edad = TimeUtils.nanoTime();
	}

	public void setDelta() {
		delta2 = TimeUtils.nanoTime();
	}

	public void setDeltaToxico() {
		deltaToxico = TimeUtils.nanoTime();
	}
	
	public void setDeltaEnfermo() {
		deltaEnfermo = TimeUtils.nanoTime();
	}

	public void setDelta3() {
		delta3 = TimeUtils.nanoTime();
	}

	// convierte de ms a nanosegundos para mas comodidad
	public long msecondTime(long ms) {

		return ms * 1000000;
	}

	public String replicar(StringBuffer str) {

		for (int ei = 0; ei < str.length(); ei++) {// se recorre toada la
													// longitud edl gen letra
													// por letra

			// obtenemos la letra de una posicion determinada
			base = str.charAt(ei);

			// tiramos los dados
			int a = 0;
			if (mutar == true) {
				a = (int) (Math.random() * tasaMut);
			}
			if (mutar == false) {
				a = 100;
			}

			if (a <= 1) {// se produce una mutacion

				int b = (int) (Math.random() * 1000);

				if (b > 10) {
					// sustitucion
					int c = (int) (Math.random() * 40);
					if (c <= 10) {
						c = 0;
					}
					if (c > 10 && c <= 20) {
						c = 1;
					}
					if (c > 20 && c <= 30) {
						c = 2;
					}
					if (c > 30 && c <= 40) {
						c = 3;
					}

					switch (c) {
					case 0:
						str.setCharAt(ei, 'a');
						break;
					case 1:
						str.setCharAt(ei, 'c');
						break;
					case 2:
						str.setCharAt(ei, 'g');
						break;
					case 3:
						str.setCharAt(ei, 't');
						break;

					}
				}
				if (b <= 10) { // delecion o insercion
					int e = (int) (Math.random() * 81);
					if (e > 0 && e <= 10) {
						e = 0;
					}
					if (e > 10 && e <= 20) {
						e = 1;
					}
					if (e > 20 && e <= 30) {
						e = 2;
					}
					if (e > 30 && e <= 40) {
						e = 3;
					}
					if (e > 40 && e <= 50) {
						e = 4;
					}
					if (e > 50 && e <= 60) {
						e = 5;
					}
					if (e > 60 && e <= 70) {
						e = 6;
					}
					if (e > 70 && e <= 80) {
						e = 7;
					}
					if (e == 0) {
						e = 8;
					}
					if (e == 81) {
						e = 9;
					}

					switch (e) {
					case 0:
						str.insert(ei, "a");
						break;
					case 1:
						str.insert(ei, "c");
						break;
					case 2:
						str.insert(ei, "g");
						break;
					case 3:
						str.insert(ei, "t");
						break;
					case 4:
						str.deleteCharAt(ei);
						break;
					case 5:
						str.deleteCharAt(ei);
						break;
					case 6:
						str.deleteCharAt(ei);
						break;
					case 7:
						str.deleteCharAt(ei);
						break;
					case 8:
						str.replace(0, str.length(), " ");
						ei = str.length();
						break;
					case 9:
						str.append("aaaaaa" + str);
						break;
					}
				}

			}

		}

		return str.toString();
	}

	@Override
	public String toString() {
		return nombre.toString();
	}

	@Override
	public int compareTo(Organismo arg0) {
		{
			// The order of two Organism's is determined by the name.
			String p2Name = arg0.fenotipo.nombre;
			;

			if (fenotipo.nombre.compareTo(p2Name) < 0)
				return 1; // or any negative int
			else if (fenotipo.nombre.compareTo(p2Name) > 0)
				return -1; // or any positive int
			else
				return 0;
		}
	}

}