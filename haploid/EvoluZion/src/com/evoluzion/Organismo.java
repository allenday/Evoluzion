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

	Mundo m;

	Sprite imagen, imagen2, imagen3;
	Sprite auraATB, muerte;
	private NumberFormat format = new DecimalFormat("0.00");

	float speed;
	float radioConsiente; // radio de los sentidos
	float toleranciaTemp;
	float tempOptima;
	float ancho;
	float alto;
	float tasaMut = 1000; // si mutar es true, indica la frecuencia de mutacion
	float longevidad; // tiempo en que muere de viejo milisegundos
	float tiempoMitosis; // tiempo en que se divide
	float color; // variable color
	boolean carnivoro = false; // capacidad de tomar alimentarse de otros
								// organismos
	boolean resistenciaATB; // capacidad de resistir Antibioticos
	boolean sentir; // capacidad de sensar el entornno por comida
	boolean cazar; // capacidad de buscar su comida
	boolean escapar;

	int capacidad; // cantidad maxima de masa y energia que puede portar el
					// organismo
	float energia = 484; // energia del organismo
	int biomasa; // biomasa del organismo
	float cuadradoTemp;

	int segundos;
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

	char[] letras = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' };

	Genoma adn; // genoma

	String fechaNacimiento;
	String identificador;// indica la especie del organismo
	// String cromosoma; //todos los genes juntos

	boolean mutar = true; // puede mutar o no
	boolean transferred = false;

	long edad, delta, delta2, delta3; // se usa para determinar el paso del
										// tiempo

	int carniI;
	int sentI;
	int cazarI;
	int escapI;

	StringBuffer nombre;

	Array<Vector2> aObjetos; // memoria donde se guarda la posicion de las
								// fuentes de alimento
	Array<Vector2> aPredadores; // memoria donde se guarda la posicion de las
								// fuentes de peligro

	char base;// nucleotido

	private int resistenciaI;

	private Sprite transferido;

	int muriendo = 0;

	public Organismo(Genoma adn, Vector2 posicion, StringBuffer nombre, Mundo m) {

		this.m = m;

		fechaNacimiento = "" + m.addCero2().toString() + m.minutos2 + ":"
				+ m.addCero().toString() + m.segundos;

		aObjetos = new Array<Vector2>();
		aPredadores = new Array<Vector2>();

		this.adn = adn;
		this.posicion = posicion;
		this.nombre = nombre;

		// direccion del movimiento
		direccion = new Vector2((float) Math.random() * 20,
				(float) Math.random() * 20);
		if (direccion.x < 10) {
			direccion.x = direccion.x * (-1);
		}
		if (direccion.x > 10) {
			direccion.x = direccion.x - 10;
		}
		if (direccion.y < 10) {
			direccion.y = direccion.y * (-1);
		}
		if (direccion.y > 10) {
			direccion.y = direccion.y - 10;
		}

		borde = new Rectangle();

		translate();

		segundos = 0;
		setTime();
		setEdad();
		setDelta();
		setDelta3();

		identificador = new String(Identificar(m));

		if (capacidad <= 0) {
			capacidad = 1;
			morir();
		}

	}

	public void translate() {

		speed = adn.traducirMagnitud(this, adn.speed, patronSpeed)
				/ (250f * m.zoom);
		ancho = adn.traducirMagnitud(this, adn.ancho, patronAncho)
				/ (224f * m.zoom);
		alto = adn.traducirMagnitud(this, adn.alto, patronAlto)
				/ (224f * m.zoom);

		toleranciaTemp = adn.traducirMagnitud(this, adn.toleranciaTemp,
				patronTolerTemp) / 126.2552f;

		color = adn.traducirMagnitud(this, adn.color, patronColor) / 20;

		radioConsiente = adn.traducirMagnitud(this, adn.radioConsiente,
				patronRadiocon) / 30f;
		longevidad = adn.traducirMagnitud(this, adn.longevidad,
				patronLongevidad) * 8.090f;
		tasaMut = adn.traducirMagnitud(this, adn.tasaMutacion, patronTasaMut)
				* 0.6f * m.eficiencia;

		carnivoro = adn.traducirBoolean(adn.predador, patronPredador);
		sentir = adn.traducirBoolean(adn.sentir, patronSentir);
		cazar = adn.traducirBoolean(adn.cazar, patronCazar);
		escapar = adn.traducirBoolean(adn.escapar, patronEscapar);
		resistenciaATB = adn.traducirBoolean(adn.resistenciaATB, patronResATB);

		carniI = (int) adn.traducirMagnitud(this, adn.predador, "M");
		sentI = (int) adn.traducirMagnitud(this, adn.sentir, "M");
		cazarI = (int) adn.traducirMagnitud(this, adn.cazar, "M");
		escapI = (int) adn.traducirMagnitud(this, adn.escapar, "M");
		resistenciaI = (int) adn
				.traducirMagnitud(this, adn.resistenciaATB, "M");

		tiempoMitosis = (int) (longevidad / 3);
		tempOptima = toleranciaTemp - 3f;

		// limita el tamaño menos

		if (alto > 0 && alto < 0.5) {
			alto = 0.5f;
		}
		if (ancho > 0 && ancho < 0.5) {
			ancho = 0.5f;
		}

		capacidad = (int) (ancho * alto);
		biomasa = 0;

		if (energia > capacidad) {
			energia = capacidad;
		}
		if (biomasa > capacidad) {
			biomasa = capacidad;
		}

		int indexcolor = 0;

		if (color == 0) {
			indexcolor = 10;
		}// blanco
		if (color > 0 && color >= 76) {
			indexcolor = 1;
		}
		if (color > 76 && color <= 152) {
			indexcolor = 2;
		}
		if (color > 152 && color <= 228) {
			indexcolor = 0;
		}
		if (color > 228 && color <= 304) {
			indexcolor = 9;
		}
		if (color > 304 && color <= 380) {
			indexcolor = 5;
		}
		if (color > 380 && color <= 456) {
			indexcolor = 6;
		}
		if (color > 456 && color <= 532) {
			indexcolor = 4;
		}
		if (color > 532 && color <= 608) {
			indexcolor = 8;
		}
		if (color > 608 && color <= 684) {
			indexcolor = 3;
		}
		if (color > 684 && color <= 760) {
			indexcolor = 11;
		}
		if (color > 760) {
			indexcolor = 7;
		}

		// System.out.println(color);

		float zoom = 1.5f;

		imagen = new Sprite(m.textura_organismos.getRegions().get(indexcolor));// normal
		imagen.setPosition(this.posicion.x, this.posicion.y);
		imagen.setSize(ancho * zoom, alto * zoom);

		imagen2 = new Sprite(m.textura_organismos.getRegions().get(12));// Carnivoro
		imagen2.setPosition(this.posicion.x, this.posicion.y);
		imagen2.setSize(ancho * zoom, alto * zoom);

		imagen3 = new Sprite(m.textura_organismos.getRegions().get(13));// Sentir
		imagen3.setPosition((float) (this.posicion.x * 2),
				(float) (this.posicion.y));
		imagen3.setSize((float) (ancho * (zoom * 0.7)),
				(float) (alto * (zoom * 0.7)));

		auraATB = new Sprite(m.textura_organismos.getRegions().get(17));
		auraATB.setPosition(this.posicion.x * (float) 0.9, this.posicion.y
				* (float) 0.9);
		auraATB.setSize(ancho * zoom * (float) 1.1, (float) alto * zoom
				* (float) 1.1);

		transferido = new Sprite(m.textura_organismos.getRegions().get(14));
		transferido.setPosition(this.posicion.x, this.posicion.y);
		transferido.setSize(ancho * zoom, alto * zoom);

		borde.height = alto;
		borde.width = ancho;

	}

	// permite al programa saber se un organismo es diferente uno de otro
	public String Identificar(Mundo mu) {

		StringBuffer sb = new StringBuffer();

		if (mu.colectColor == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.color, patronColor) / 10);
		}
		if (mu.colectarancho == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.ancho, patronAncho) / 10);
		}
		if (mu.colectaralto == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.alto, patronAlto) / 10);
		}
		if (mu.colectSpeed == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.speed, patronSpeed) / 9);
		}
		if (mu.colectRadioconsiente == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.radioConsiente,
					patronRadiocon) / 6);
		}
		if (mu.colectarLongevidad == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.longevidad,
					patronLongevidad) / 7);
		}
		if (mu.colectarTasaMut == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.tasaMutacion,
					patronTasaMut) / 8);
		}
		if (mu.colectarTemp == true) {
			sb.append((int) adn.traducirMagnitud(this, adn.toleranciaTemp,
					patronTolerTemp) / 7);
		}
		if (mu.colectSentir == true) {
			sb.append(this.sentI / 6);
		}
		if (mu.colectPredador == true) {
			sb.append(this.carniI / 6);
		}
		if (mu.colectEscapar == true) {
			sb.append(this.escapI / 7);
		}
		if (mu.colectCazar == true) {
			sb.append(this.cazarI / 7);
		}
		if (mu.colectarResistencia == true) {
			sb.append(this.resistenciaI / 8);
		}

		// System.out.print(sb.toString()+" ");

		return sb.toString();

	}

	public void verOrganismo(SpriteBatch sb) {

		sb.begin();
		imagen.draw(sb);

		if (sentir == true) {
			imagen3.draw(sb);
		}
		if (carnivoro == true) {
			imagen2.draw(sb);
		}
		if (resistenciaATB == true) {
			auraATB.draw(sb);
		}
		if (transferred == true) {
			transferido.draw(sb);
		}
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

		imagen.setPosition(posicion.x, posicion.y);
		imagen2.setPosition(posicion.x, posicion.y);// predador
		imagen3.setPosition(posicion.x * 1.005f, posicion.y * 1.005f);// sentir
		auraATB.setPosition(posicion.x - 2f, posicion.y - 2f);
		transferido.setPosition(posicion.x - 2.5f, posicion.y - 2.5f);

		borde.x = posicion.x;
		borde.y = posicion.y;

		borde.x = posicion.x;
		borde.y = posicion.y;

	}

	public void update() {

		if (alto <= 0) {
			morir();
		}// muere si tiene altura 0
		if (ancho <= 0) {
			morir();
		}// muere si tiene ancho 0

		if (toleranciaTemp < m.temperatura) {
			morir();
		}// muere si hace mucho calor
		if (m.temperatura < toleranciaTemp - 6) {
			morir();
		} // muere si hace mucho frio

		if (m.antibiotico == 1) {
			if (resistenciaATB == false) {
				morir();
			}
		} // muere si no es resistente

		if (m.pausaGame == 1) {

			metabolismo();
			contadorTiempo();
			dividirse();
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
		}

		posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x) * speed,
				Gdx.graphics.getDeltaTime() * (direccion.y) * speed);

		Ordenar();

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
			

	}

	public void metabolismo() {

		if (deltaTime() > msecondTime(100)) {

			float movh = (float) Math
					.sqrt(((direccion.x * speed) * (direccion.x * speed))
							+ ((direccion.y * speed) * (direccion.y * speed)));

			cuadradoTemp = (tempOptima - m.temperatura)
					* (tempOptima - m.temperatura);

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

			}
			setTime();

		}

		if (energia <= 0) {

			morir();

		}
	}

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

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

						}
					}
				}

			}

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

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

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

							float qX = or.posicion.x;
							float qY = or.posicion.y;

							float Dx = posicion.x - qX;
							float Dy = posicion.y - qY;

							float h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radioConsiente) {
								aObjetos.add(new Vector2(qX, qY));
							}

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
			int size = aPredadores.size;
			for (int i = size - 1; i >= 0; i--) {
				Vector2 v2 = aPredadores.get(i);
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

	public void cazarObjetos() {

		if ((100 * biomasa) / capacidad < 80 && capacidad != 0) {

			if (aObjetos.size > 0) {
				int size = aObjetos.size;

				for (int i = size - 1; i >= 0; i--) {

					Vector2 v2 = aObjetos.get(i);

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

	public void Borrar(Organismo or) {

		m.aorg.removeValue(or, true);

		or = null;

		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.gc();
	}

	public void apoptosis() {

		if (segundos >= longevidad / 1000) {

			morir();

		}

	}

	public Genoma mutar(Genoma gen) {

		if (m.mutarColor == true) {
			gen.color
					.replace(0, gen.color.length(), replicar((this.adn.color)));
		}
		if (m.mutarSize == true) {
			gen.ancho
					.replace(0, gen.ancho.length(), replicar((this.adn.ancho)));
		}
		if (m.mutarSize == true) {
			gen.alto.replace(0, gen.alto.length(), replicar((this.adn.alto)));
		}
		if (m.mutarSpeed == true) {
			gen.speed
					.replace(0, gen.speed.length(), replicar((this.adn.speed)));
		}
		if (m.mutarTemp == true) {
			gen.toleranciaTemp.replace(0, gen.toleranciaTemp.length(),
					replicar((this.adn.toleranciaTemp)));
		}
		if (m.mutarPredador == true) {
			gen.predador.replace(0, gen.predador.length(),
					replicar((this.adn.predador)));
		}
		if (m.mutarSentir == true) {
			gen.sentir.replace(0, gen.sentir.length(),
					replicar((this.adn.sentir)));
		}
		if (m.mutarCazar == true) {
			gen.cazar
					.replace(0, gen.cazar.length(), replicar((this.adn.cazar)));
		}
		if (m.mutarEscapar == true) {
			gen.escapar.replace(0, gen.escapar.length(),
					replicar((this.adn.escapar)));
		}
		if (m.mutarRadioconsiente == true) {
			gen.radioConsiente.replace(0, gen.radioConsiente.length(),
					replicar(this.adn.radioConsiente));
		}
		if (m.mutarTasaMut == true) {
			gen.tasaMutacion.replace(0, gen.tasaMutacion.length(),
					replicar(this.adn.tasaMutacion));
		}
		if (m.mutarLongevidad == true) {
			gen.longevidad.replace(0, gen.longevidad.length(),
					replicar(this.adn.longevidad));
		}
		if (m.mutarResistencia == true) {
			gen.resistenciaATB.replace(0, gen.resistenciaATB.length(),
					replicar(this.adn.resistenciaATB));
		}

		if (m.mutarColor == false) {
			gen.color = new StringBuffer(adn.color.toString());
		}
		if (m.mutarSize == false) {
			gen.ancho = new StringBuffer(adn.ancho.toString());
		}
		if (m.mutarSize == false) {
			gen.alto = new StringBuffer(adn.alto.toString());
		}
		if (m.mutarSpeed == false) {
			gen.speed = new StringBuffer(adn.speed.toString());
		}
		if (m.mutarTemp == false) {
			gen.toleranciaTemp = new StringBuffer(adn.toleranciaTemp.toString());
		}
		if (m.mutarPredador == false) {
			gen.predador = new StringBuffer(adn.predador.toString());
		}
		if (m.mutarSentir == false) {
			gen.sentir = new StringBuffer(adn.sentir.toString());
		}
		if (m.mutarCazar == false) {
			gen.cazar = new StringBuffer(adn.cazar.toString());
		}
		if (m.mutarEscapar == false) {
			gen.escapar = new StringBuffer(adn.escapar.toString());
		}
		if (m.mutarRadioconsiente == false) {
			gen.radioConsiente = new StringBuffer(adn.radioConsiente.toString());
		}
		if (m.mutarTasaMut == false) {
			gen.tasaMutacion = new StringBuffer(adn.tasaMutacion.toString());
		}
		if (m.mutarLongevidad == false) {
			gen.longevidad = new StringBuffer(adn.longevidad.toString());
		}
		if (m.mutarResistencia == false) {
			gen.resistenciaATB = new StringBuffer(adn.resistenciaATB.toString());
		}

		return gen;

	}

	public void dividirse() {

		int masatotal = (int) (m.BiomasaTotal(m.aorg) + m.MateriaLibre());

		int pro = (int) (Math.random() * 100);// probabilidad de divideirse de
												// inmediato

		if (pro <= 1 && segundos >= tiempoMitosis / 1000
				&& m.aorg.size < m.maximo && capacidad != 0
				&& masatotal <= (m.Masatotal + 100)) {

			if ((100 * biomasa) / capacidad > 50) {
				int biom = biomasa;
				int bio = biom / 2;
				int bio2 = biom - bio;
				float ener = energia / 2;
				float ener2 = energia - ener;

				Genoma gen2 = new Genoma();// nuevo gen para las celulas hijas
				Genoma gen = new Genoma();// nuevo gen para las celulas hijas
				// el nuevo gen sale de copiar el gen de la celula madre

				mutar(gen);
				mutar(gen2);

				StringBuffer sb = new StringBuffer(nombre.toString());
				StringBuffer sb2 = new StringBuffer(nombre.toString());

				Vector2 pos = new Vector2((float) posicion.x - ((ancho) + 3)
						/ 2, (float) posicion.y);

				Organismo or = new Organismo(gen, pos, sb, m);

				or.energia = ener;
				or.biomasa = bio;
				or.marcado = 1 * marcado;
				or.transferred = transferred;
				if (or.capacidad > 1) {
					m.aorg.add(or);
				}

				Vector2 pos2 = new Vector2((float) posicion.x - ((ancho) + 3)
						/ 2, (float) posicion.y);

				Organismo or2 = new Organismo(gen2, pos2, sb2, m);

				or2.energia = ener2;
				or2.biomasa = bio2;
				or2.marcado = 1 * marcado;
				or2.transferred = transferred;

				if (or2.capacidad > 1) {
					m.aorg.add(or2);
				}

				if (!this.identificador.equals(or.identificador)) {

					or.nombre.append(letras[m.index]);

					m.index++;

					if (m.index > 25) {
						m.index = 0;
					}

				}

				if (!this.identificador.equals(or2.identificador)) {

					or2.nombre.append(letras[m.index]);

					m.index++;

					if (m.index > 25) {
						m.index = 0;
					}

				}

				m.borrarOrganismo(this);

			}
		}

	}

	// mide los segundos transcurrido desde su creación

	public void contadorTiempo() {

		if (delta2Time() > msecondTime(1000)) {

			segundos = segundos + 1;
			// System.out.println(segundos + " tiempo mitosis "+ tiempoMitosis);

			setDelta();
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

			if (a < 1) {// se produce una mutacion

				int b = (int) (Math.random() * 1000);

				if (b > 10) {
					// sustitucion
					int c = (int) (Math.random() * 40);
					if (c <= 10) {
						str.setCharAt(ei, 'a');
					}
					if (c > 10 && c <= 20) {
						str.setCharAt(ei, 'c');
					}
					if (c > 20 && c <= 30) {
						str.setCharAt(ei, 'g');
					}
					if (c > 30 && c <= 40) {
						str.setCharAt(ei, 't');
					}

				}
				if (b <= 10) { // delecion o insercion
					int e = (int) (Math.random() * 81);
					if (e > 0 && e <= 10) {
						str.insert(ei, "a");
					}
					if (e > 10 && e <= 20) {
						str.insert(ei, "c");
					}
					if (e > 20 && e <= 30) {
						str.insert(ei, "g");
					}
					if (e > 30 && e <= 40) {
						str.insert(ei, "t");
					}
					if (e > 40 && e <= 50) {
						str.deleteCharAt(ei);
					}
					if (e > 50 && e <= 60) {
						str.deleteCharAt(ei);
					}
					if (e > 60 && e <= 70) {
						str.deleteCharAt(ei);
					}
					if (e > 70 && e <= 80) {
						str.deleteCharAt(ei);
					}
					if (e == 0) {
						str.replace(0, str.length(), " ");
						ei = str.length();
					}
					if (e == 81) {
						str.append("aaaaaa" + str);
					}

				}
			}
		}

		// System.out.println(str);
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
			String p2Name = arg0.nombre.toString();
			int result = nombre.toString().compareTo(p2Name);
			if (result < 0)
				return 1; // or any negative int
			else if (result > 0)
				return -1; // or any positive int
			else
				return 0;
		}

	}
}
