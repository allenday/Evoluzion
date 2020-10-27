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

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Pantalla implements Screen {

	protected Evoluzion ev;
	protected Mundo m;
	Texto tx;

	private OrthographicCamera camara;
	private SpriteBatch batch;
	private ShapeRenderer caja, borde, frontera, pausaCaja;
	private BitmapFont fuente;
	private BitmapFont fu_fuente;
	private boolean debugin = false;
	private TextureAtlas ta_atlas;// carga imagenes de atlas de texturas
	private Stage stage; // stage maneja elementos que reciben entradas como
							// botones o eventos
	private Skin sk_skin; // almacena recursos de atlas como imagenes y colores
							// para ser usados mas facilmente
	private TextButton b_pausa, b_Salir, b_colectar; // crea botones con texto
														// similares a los de
														// swing
	private int verPanel = 1;
	private int verBotones1 = 1;

	Organismo or;
	Senergia se;
	Qenergia qe;
	int numero = 0; // se usa para que los metodos cuenten elementos
	int cantidad = 0;
	int cantidad2 = 0;
	int cantidad3 = 0;
	int cont, cont2, cont3;
	int press = 1; // 1=false -1 = true
	int mark = 1; // 1=false -1 = true
	int verFrontera = 1;// 1=false -1 = true
	boolean keypress = false;

	private NumberFormat format = new DecimalFormat("0.00");
	private NumberFormat format3 = new DecimalFormat("0.0");
	private NumberFormat format2 = new DecimalFormat("#");
	private TextButton b_colectarP;
	boolean todoGuardado = false;
	private TextButton b_catastrofe;
	private TextButton b_guardar;
	private TextButton b_colectarPM;
	private TextButton b_colectarPnM;
	private TextButton b_marcarTodo;

	private CheckBox cb_verEnergia;
	private Skin sk_skin2;
	private TextureAtlas ta_atlas2;
	private CheckBox cb_verMasa;
	private CheckBox cb_verOrganismos;
	private TextButton b_antibiotico;
	private TextButton b_frontera;
	int fps = 30; // limit to 30 fps

	// int fps = 0;//without any limits

	public Pantalla(Evoluzion ev, Mundo m) {

		this.ev = ev;
		this.m = m;
		tx = m.tx;// usamos la configuracionde taxto del menu inicio
		if (m.aorg.size > 0) {
			or = m.aorg.get(0);
		}
		if (m.ase.size > 0) {
			se = m.ase.get(0);
		}
		if (m.aqe.size > 0) {
			qe = m.aqe.get(0);
		}

		camara = new OrthographicCamera();
		camara.setToOrtho(false, m.ancho, m.alto);

		batch = new SpriteBatch();
		caja = new ShapeRenderer();
		frontera = new ShapeRenderer();
		pausaCaja = new ShapeRenderer();

		borde = new ShapeRenderer();
		fuente = new BitmapFont();

		ta_atlas = new TextureAtlas("data/botones.pack");// carga el atlas de
															// texturas donde
															// estan los botones
		ta_atlas2 = new TextureAtlas("data/boxes.pack");
		sk_skin = new Skin();
		sk_skin.addRegions(ta_atlas);
		sk_skin2 = new Skin();
		sk_skin2.addRegions(ta_atlas2);

		fu_fuente = new BitmapFont();

	}

	long diff, start = System.currentTimeMillis();
	CheckBox cb_verAlelos;
	CheckBox cb_verDatos;
	CheckBox cb_verFenotipo;
	CheckBox cb_verGenotipo;
	TextButton b_ordenar;
	private TextButton b_stop;

	@Override
	public void render(float delta) {

		// Game loop
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camara.update();
		stage.act(delta);

		// entradasTeclado();

		if (verBotones1 == 1) {

			b_colectarP.setVisible(false);
			b_colectarPM.setVisible(false);
			b_colectarPnM.setVisible(false);

			verBotones1 = verBotones1 * 2;

		}

		if (verBotones1 == -1) {

			b_colectarP.setVisible(true);
			b_colectarPM.setVisible(true);
			b_colectarPnM.setVisible(true);

			verBotones1 = verBotones1 * 2;
		}

		if (m.pausaGame == 1) { // 1=play -1=pausa
			m.update();// actualiza los datos que maneja el Mundo
			// System.out.println("rendering");
		}

		if (m.aorg.size > 0 && m.segundos4 >= m.tiempoMaximo
				&& m.tiempoMaximo > 0) {

			b_ordenar.setVisible(true);

		}

		batch.setProjectionMatrix(camara.combined);
		// comienzo a graficar
		//
		// dibuja Senergia

		if (cb_verEnergia.isChecked() == true) {
			cantidad = m.ase.size;

			for (cont = cantidad - 1; cont >= 0; cont--) {
				se = m.ase.get(cont);
				se.verObjeto(batch);
			}
		}
		// dibujamos Qenergia
		if (cb_verMasa.isChecked() == true) {
			cantidad2 = m.aqe.size;

			for (cont2 = cantidad2 - 1; cont2 >= 0; cont2--) {
				qe = m.aqe.get(cont2);
				qe.verObjeto(batch);
			}
		}

		// for(Qenergia qe :m.aqe){qe.verObjeto(batch);}
		// dibujamos los organismos

		if (cb_verOrganismos.isChecked() == true) {
			cantidad3 = m.aorg.size;

			for (cont3 = cantidad3 - 1; cont3 >= 0; cont3--) {
				or = m.aorg.get(cont3);
				or.verOrganismo(batch);
			}
			// verl los organismos marcados
			cantidad3 = m.aorg.size;

			for (cont3 = cantidad3 - 1; cont3 >= 0; cont3--) {
				or = m.aorg.get(cont3);
				or.verMarcado(borde, batch, fuente);
			}
		}

		// dibujar rectangulos en modo debug
		if (debugin == true) {

			for (Organismo or : m.aorg) {
				or.verBorde(borde);
			}

			for (Qenergia qe : m.aqe) {
				qe.verBorde(borde);
			}
		}

		if (m.verFrontera == true) {

			frontera.begin(ShapeType.FilledRectangle);
			frontera.setColor(Color.CYAN);
			frontera.filledRect(m.frontera.x, m.frontera.y, m.frontera.width,
					m.frontera.height);
			frontera.end();
		}

		caja.begin(ShapeType.FilledRectangle);

		caja.setColor(Color.BLACK);
		caja.filledRect(0, m.alto - 30, m.ancho, 30);
		caja.end();
		batch.begin();
		fuente.draw(batch, "|h: " + m.horas + " |m: " + m.addCero2().toString()
				+ m.minutos + " |s: " + m.addCero().toString() + m.segundos,
				450, m.alto - 60);

		if (m.antibiotico == 1) {
			fuente.draw(batch, tx.antibioticoON, 450, m.alto - 80);
		} else {
			fuente.draw(batch, tx.antibioticoOFF, 455, m.alto - 80);
		}
		;

		batch.end();

		if (m.pausaGame == -1) {

			pausaCaja.begin(ShapeType.FilledRectangle);
			pausaCaja.setColor(Color.BLACK);
			pausaCaja.filledRect((m.ancho / 2) - 100, (m.alto / 2) - 25, 200,
					50);
			pausaCaja.end();

			batch.begin();
			fuente.draw(batch, tx.simulacionEnPausa, 500, 370);
			fuente.draw(batch, tx.oprimaPlayPausa, 425, 350);
			batch.end();
		}

		if (m.verFrontera == false) {

			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(0, m.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch, tx.Organismos + m.aorg.size, 5, m.alto - 60);

				int masatotal = (int) (m.BiomasaTotal(m.aorg) + m
						.MateriaLibre());
				fuente.draw(batch, tx.masaTotal + masatotal, 5, m.alto - 80);
				fuente.draw(batch, tx.masa + m.MateriaLibre(), 5, m.alto - 100);
				fuente.draw(batch, tx.biomasa + m.BiomasaTotal(m.aorg), 5,
						m.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(m.velocidadMedia(m.aorg)), 5,
						m.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(m.tamanoMedio(m.aorg)),
						5, m.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(m.tasaMutMedio(m.aorg)), 5,
						m.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(m.longevidadMedio(m.aorg))
								+ " (s)", 5, m.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + m.cantidadResistentes(m.aorg), 5,
						m.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(m.temperatura), 5,
						m.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(m.temOptimaMedia(m.aorg)), 5,
						m.alto - 260);

				batch.end();
			}

			if (cb_verAlelos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(0, m.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 5, m.alto - 300);
				// mostrar % de alelos
				m.colectarAlelos(m.aorg, m.aAlelos);

				numero = m.aAlelos.size;
				float renglon = m.alto - 330;
				for (int i = 0; i < numero; i++) {

					Alelo al = m.aAlelos.get(i);

					if (!al.nombre.equals(tx.color)
							|| !al.nombre.equals(tx.longevidad)
							|| !al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.aorg.size * 2;
					}

					if (al.nombre.equals(tx.color)
							|| al.nombre.equals(tx.longevidad)
							|| al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.cantidadHembras(m.aorg) * 2
								+ m.cantidadMachos(m.aorg);
					}

					fuente.draw(
							batch,
							al.nombre
									+ ": "
									+ "a"
									+ al.identificador
									+ " > "
									+ format3
											.format(((float) al.cantidad / (float) cantidad) * 100)
									+ " %", 5, renglon);

					renglon = renglon - 20;

				}
				;

				batch.end();
			}

		}

		if (m.verFrontera == true) {

			// panel izquierdo
			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(0, m.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch,
						tx.Organismos + m.numeroI, 5,
						m.alto - 60);

				int masatotal = (int) (m.BiomasaTotalI(m.aorg) + m
						.MateriaLibreL());
				fuente.draw(batch, tx.masaTotal + masatotal, 5, m.alto - 80);
				fuente.draw(batch, tx.masa + m.MateriaLibreL(), 5, m.alto - 100);
				fuente.draw(batch, tx.biomasa + m.BiomasaTotalI(m.aorg), 5,
						m.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(m.velocidadMediaI(m.aorg)), 5,
						m.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(m.tamanoMedioI(m.aorg)),
						5, m.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(m.tasaMutMedioI(m.aorg)), 5,
						m.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(m.longevidadMedioI(m.aorg))
								+ " (s)", 5, m.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + m.cantidadResistentesI(m.aorg), 5,
						m.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(m.temperatura), 5,
						m.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(m.temOptimaMediaI(m.aorg)), 5,
						m.alto - 260);

				batch.end();
			}

			if (cb_verAlelos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(0, m.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 5, m.alto - 300);
				// mostrar % de alelos
				m.colectarAlelosI(m.aorg, m.aAlelos);

				numero = m.aAlelos.size;
				float renglon = m.alto - 330;
				for (int i = numero - 1; i >= 0; i--) {

					Alelo al = m.aAlelos.get(i);

					if (!al.nombre.equals(tx.color)
							|| !al.nombre.equals(tx.longevidad)
							|| !al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.aorg.size * 2;
					}

					if (al.nombre.equals(tx.color)
							|| al.nombre.equals(tx.longevidad)
							|| al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.cantidadHembrasI(m.aorg) * 2
								+ m.cantidadMachosI(m.aorg);
					}

					fuente.draw(
							batch,
							al.nombre
									+ ": "
									+ "a"
									+ al.identificador
									+ " > "
									+ format3
											.format(((float) al.cantidad / (float) cantidad) * 100)
									+ " %", 5, renglon);

					renglon = renglon - 20;
				}

				batch.end();
			}

			// panel derecho

			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(840, m.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch,
						tx.Organismos + m.numeroD, 840,
						m.alto - 60);

				int masatotalD = (int) (m.BiomasaTotalD(m.aorg) + m
						.MateriaLibreR());
				fuente.draw(batch, tx.masaTotal + masatotalD, 840, m.alto - 80);
				fuente.draw(batch, tx.masa + m.MateriaLibreR(), 840,
						m.alto - 100);
				fuente.draw(batch, tx.biomasa + m.BiomasaTotalD(m.aorg), 840,
						m.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(m.velocidadMediaD(m.aorg)),
						840, m.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(m.tamanoMedioD(m.aorg)),
						840, m.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(m.tasaMutMedioD(m.aorg)), 840,
						m.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(m.longevidadMedioD(m.aorg))
								+ " (s)", 840, m.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + m.cantidadResistentesD(m.aorg),
						840, m.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(m.temperatura), 840,
						m.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(m.temOptimaMediaD(m.aorg)),
						840, m.alto - 260);

				batch.end();
			}

			if (cb_verAlelos.isChecked()) {

				caja.begin(ShapeType.FilledRectangle);
				caja.setColor(Color.BLACK);

				caja.filledRect(840, m.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 840, m.alto - 300);
				// mostrar % de alelos
				m.colectarAlelosD(m.aorg, m.aAlelos);

				numero = m.aAlelos.size;
				float renglon = m.alto - 330;
				for (int i = numero - 1; i >= 0; i--) {

					Alelo al = m.aAlelos.get(i);

					if (!al.nombre.equals(tx.color)
							|| !al.nombre.equals(tx.longevidad)
							|| !al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.aorg.size * 2;
					}

					if (al.nombre.equals(tx.color)
							|| al.nombre.equals(tx.longevidad)
							|| al.nombre.equals(tx.fidelidadADNpol)) {

						cantidad = m.cantidadHembrasD(m.aorg) * 2
								+ m.cantidadMachosD(m.aorg);
					}

					fuente.draw(
							batch,
							al.nombre
									+ ": "
									+ "a"
									+ al.identificador
									+ " > "
									+ format3
											.format(((float) al.cantidad / (float) cantidad) * 100)
									+ " %", 840, renglon);

					renglon = renglon - 20;
				}

				batch.end();
			}

		}

		cb_verEnergia.setVisible(true);
		cb_verMasa.setVisible(true);
		cb_verOrganismos.setVisible(true);

		if (verPanel == -1) {

			cb_verEnergia.setVisible(false);
			cb_verMasa.setVisible(false);
			cb_verOrganismos.setVisible(false);
		}

		// botones
		batch.begin();
		stage.draw();
		batch.end();

		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camara.unproject(touchPos);
			// System.out.println(touchPos.x + " "+ touchPos.y);

			cantidad3 = m.aorg.size;

			for (cont3 = cantidad3 - 1; cont3 >= 0; cont3--) {

				or = m.aorg.get(cont3);
				if (touchPos.x > or.posicion.x
						&& touchPos.x < or.posicion.x + or.ancho
						&& touchPos.y > or.posicion.y
						&& touchPos.y < or.posicion.y + or.alto) {

					or.marcado = or.marcado * (-1);

				}
			}
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camara.unproject(touchPos);

			cantidad3 = m.aorg.size;

			for (cont3 = cantidad3 - 1; cont3 >= 0; cont3--) {

				or = m.aorg.get(cont3);

				if (or.marcado == -1 && touchPos.x > or.posicion.x - or.ancho
						&& touchPos.x < or.posicion.x + or.ancho * 2
						&& touchPos.y > or.posicion.y - or.alto
						&& touchPos.y < or.posicion.y + or.alto * 2) {

					or.posicion.x = touchPos.x - or.ancho / 2;
					or.posicion.y = touchPos.y - or.alto / 2;
					or.Ordenar();
				}
			}

		}

		if (Gdx.input.isKeyPressed(Keys.P)) {

			m.pausaGame = m.pausaGame * (-1);

		}

	}

	public void entradasTeclado() {

		Gdx.input.setInputProcessor(new ETeclado(this));
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			// stage maneja elementos que reciben entradas como botones o
			// eventos
			// en este caso se us apara los botones

			stage = new Stage(width, height, true);
			stage.clear();
			Gdx.input.setInputProcessor(stage);

			// instansia los elemrntos de un boton
			// la posocion up y down usando imagenes y el texto que tiene cada
			// uno
			TextButtonStyle estilo = new TextButtonStyle();
			estilo.up = sk_skin.getDrawable("BotonUP");
			estilo.down = sk_skin.getDrawable("BotonDown");
			estilo.font = fu_fuente;

			// instancia los botones

			b_colectar = new TextButton(tx.tomarMuestra, estilo);
			b_colectar.setWidth(130);
			b_colectar.setHeight(20);
			b_colectar.setX(200);
			b_colectar.setX(0);
			b_colectar.setY(m.alto - 20);

			b_guardar = new TextButton(tx.menuGuardar, estilo);
			b_guardar.setWidth(130);
			b_guardar.setHeight(20);
			b_guardar.setX(130);
			b_guardar.setY(m.alto - 20);

			b_colectarP = new TextButton(tx.guardarTodos, estilo);
			b_colectarP.setWidth(130);
			b_colectarP.setHeight(20);
			b_colectarP.setX(130);
			b_colectarP.setY(m.alto - 65);

			b_colectarPM = new TextButton(tx.guardarMarcados, estilo);
			b_colectarPM.setWidth(130);
			b_colectarPM.setHeight(20);
			b_colectarPM.setX(130);
			b_colectarPM.setY(m.alto - 88);

			b_colectarPnM = new TextButton(tx.guardarNoMarcados, estilo);
			b_colectarPnM.setWidth(130);
			b_colectarPnM.setHeight(20);
			b_colectarPnM.setX(130);
			b_colectarPnM.setY(m.alto - 111);

			b_marcarTodo = new TextButton(tx.marcarDesmarcar, estilo);
			b_marcarTodo.setWidth(130);
			b_marcarTodo.setHeight(20);
			b_marcarTodo.setX(260);
			b_marcarTodo.setY(m.alto - 20);

			b_antibiotico = new TextButton(tx.antibiotico, estilo);
			b_antibiotico.setWidth(130);
			b_antibiotico.setHeight(20);
			b_antibiotico.setX(390);
			b_antibiotico.setY(m.alto - 20);

			b_frontera = new TextButton(tx.frontera, estilo);
			b_frontera.setWidth(130);
			b_frontera.setHeight(20);
			b_frontera.setX(390);
			b_frontera.setY(m.alto - 42);

			b_catastrofe = new TextButton(tx.catastrofe, estilo);
			b_catastrofe.setWidth(140);
			b_catastrofe.setHeight(20);
			b_catastrofe.setX(520);
			b_catastrofe.setY(m.alto - 20);

			b_stop = new TextButton(tx.parar, estilo);
			b_stop.setWidth(130);
			b_stop.setHeight(20);
			b_stop.setX(660);
			b_stop.setY(m.alto - 20);

			b_pausa = new TextButton(tx.playPausa, estilo);
			b_pausa.setWidth(130);
			b_pausa.setHeight(20);
			b_pausa.setX(770);
			b_pausa.setY(m.alto - 20);

			b_ordenar = new TextButton(tx.ordenar, estilo);
			b_ordenar.setVisible(false);
			b_ordenar.setWidth(130);
			b_ordenar.setHeight(35);
			b_ordenar.setX((m.ancho / 2) - 130 / 2);
			b_ordenar.setY(5);

			b_Salir = new TextButton(tx.guardarYcerrar, estilo);
			b_Salir.setWidth(130);
			b_Salir.setHeight(20);
			b_Salir.setX(900);
			b_Salir.setY(m.alto - 20);

			CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
			checkBoxStyle.checkboxOff = sk_skin2.getDrawable("boxN0");
			checkBoxStyle.checkboxOn = sk_skin2.getDrawable("boxYES");
			checkBoxStyle.font = fu_fuente;

			cb_verDatos = new CheckBox(tx.verDatos, checkBoxStyle);
			cb_verDatos.getCells().get(0).size(15, 15);
			cb_verDatos.setChecked(true);
			cb_verDatos.setPosition(-10, 630);

			cb_verAlelos = new CheckBox(tx.verAlelos, checkBoxStyle);
			cb_verAlelos.getCells().get(0).size(15, 15);
			cb_verAlelos.setChecked(true);
			cb_verAlelos.setPosition(100, 630);

			cb_verFenotipo = new CheckBox(tx.verFenotipo, checkBoxStyle);
			cb_verFenotipo.getCells().get(0).size(15, 15);
			cb_verFenotipo.setChecked(true);
			cb_verFenotipo.setPosition(220, 630);

			cb_verGenotipo = new CheckBox(tx.verGenotipo, checkBoxStyle);
			cb_verGenotipo.getCells().get(0).size(15, 15);
			cb_verGenotipo.setChecked(true);
			cb_verGenotipo.setPosition(380, 630);

			cb_verEnergia = new CheckBox(tx.verEnergia, checkBoxStyle);
			cb_verEnergia.getCells().get(0).size(15, 15);
			cb_verEnergia.setChecked(false);
			cb_verEnergia.setPosition(530, 630);

			cb_verMasa = new CheckBox(tx.verMasa, checkBoxStyle);
			cb_verMasa.getCells().get(0).size(15, 15);
			cb_verMasa.setChecked(false);
			cb_verMasa.setPosition(680, 630);

			cb_verOrganismos = new CheckBox(tx.verOrgansimo, checkBoxStyle);
			cb_verOrganismos.getCells().get(0).size(15, 15);
			cb_verOrganismos.setChecked(true);
			cb_verOrganismos.setPosition(830, 630);

			stage.addActor(cb_verDatos);
			stage.addActor(cb_verAlelos);
			// stage.addActor(cb_verFenotipo);
			// stage.addActor(cb_verGenotipo);
			stage.addActor(cb_verEnergia);
			stage.addActor(cb_verMasa);
			stage.addActor(cb_verOrganismos);
			stage.addActor(b_Salir);
			stage.addActor(b_pausa);
			stage.addActor(b_stop);
			// stage.addActor(b_verOcultar);
			stage.addActor(b_guardar);
			stage.addActor(b_colectar);
			stage.addActor(b_colectarP);
			stage.addActor(b_colectarPM);
			stage.addActor(b_colectarPnM);
			stage.addActor(b_marcarTodo);
			stage.addActor(b_catastrofe);

			stage.addActor(b_antibiotico);
			stage.addActor(b_frontera);
			stage.addActor(b_ordenar);

			// se agregan los listener para los botones

			b_Salir.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					m.pausaGame = m.pausaGame * (-1);

					Object[] options = { tx.si, tx.no };
					int n = JOptionPane.showOptionDialog(null,
							tx.terminarGuardarMensaje, "",

							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[1]);

					if (n == JOptionPane.YES_OPTION) {
						if (todoGuardado == false) {
							if (m.aorg.size > 0) {
								m.guardarDatos();
								m.archivarGenoma();
								m.archivarGenotipo();
								m.archivarFenotipo2();
								m.archivarAlelos();
							}
							m.f_genes.cerrarArchivo();
							// m.f_proteoma.cerrarArchivo();
							// m.f_mutantes.cerrarArchivo();
							m.f_datos.cerrarArchivo();
							m.f_alelos.cerrarArchivo();
							m.f_genotipos.cerrarArchivo();
						}
						m.f_fenotipos.cerrarArchivo();

						ev.setScreen(new MenuInicio(ev));
						// m = null;
						dispose();
					}
					if (n == JOptionPane.NO_OPTION) {
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

				}
			});

			b_stop.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.pausaGame = m.pausaGame * (-1);

					Object[] options = { tx.si, tx.no };
					int n = JOptionPane.showOptionDialog(null,
							tx.terminarMensaje, "",

							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[1]);

					if (n == JOptionPane.YES_OPTION) {

						m.parar = true;
						m.pausaGame = m.pausaGame * (-1);
						b_ordenar.setVisible(true);
						b_stop.setTouchable(Touchable.disabled);
						b_catastrofe.setTouchable(Touchable.disabled);
						b_antibiotico.setTouchable(Touchable.disabled);
						b_pausa.setTouchable(Touchable.disabled);
					}
					if (n == JOptionPane.NO_OPTION) {
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

				}
			});

			b_pausa.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.pausaGame = m.pausaGame * (-1);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			// b_verOcultar.addListener(new InputListener() {
			// / public boolean touchDown (InputEvent event, float x, float y,
			// int pointer, int button) {

			// verPanel=verPanel*(-1);

			// return true;
			// }

			// public void touchUp (InputEvent event, float x, float y, int
			// pointer, int button) { }});

			b_guardar.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					verBotones1 = verBotones1 / 2;
					verBotones1 = verBotones1 * (-1);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_colectar.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					m.colectorEspesies();
					m.guardarDatos();
					m.archivarGenoma();
					// m.archivarProteoma();
					m.archivarGenotipo();
					m.archivarAlelos();
					m.archivarFenotipo2();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					verBotones1 = verBotones1 / 2;
					// verBotones1= verBotones1*(-1);

				}
			});

			b_colectarP.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.pausaGame = -1;
					m.guardarPoblacion();
					// m.pausaGame=1;
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

					verBotones1 = verBotones1 / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			b_colectarPM.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					m.pausaGame = -1;
					m.guardarPoblacionMarcada();
					//
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

					verBotones1 = verBotones1 / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			b_colectarPnM.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					m.pausaGame = -1;
					m.guardarPoblacionNoMaracada();
					//
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

					verBotones1 = verBotones1 / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			b_marcarTodo.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					numero = m.aorg.size;
					mark = mark * (-1);
					for (int i = numero - 1; i >= 0; i--) {

						if (mark == 1) {
							m.aorg.get(i).marcado = 1;
						}
						if (mark == -1) {
							m.aorg.get(i).marcado = -1;
						}
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

				}
			});

			b_antibiotico.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.antibiotico = m.antibiotico * (-1);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_catastrofe.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.catastrofe();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_frontera.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					verFrontera = verFrontera * (-1);

					if (verFrontera == 1) {
						m.verFrontera = false;

						m.f_datos.escribirArchivo("\n" + tx.datosOrdenados);
					}
					if (verFrontera == -1) {
						m.verFrontera = true;
						m.f_datos.escribirArchivo("\n" + tx.datosOrdenados2);
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_ordenar.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					m.Ordenar();

					b_ordenar.setText(tx.Siguiente);

					if (m.verFrontera == false) {
						if (m.indice == m.aorg.size) {
							m.indice = 0;
							b_ordenar.setText(tx.ordenar);
						}
					}
					if (m.verFrontera == true) {
						if (m.numeroD == m
								.numeroI) {
							if (m.indiceDer == m.numeroD) {
								m.indiceDer = 0;
								m.indiceIz = 0;
								b_ordenar.setText(tx.ordenar);
							}
						}
						if (m.numeroD > m
								.numeroI) {
							if (m.indiceDer == m.numeroD) {
								m.indiceDer = 0;
								m.indiceIz = 0;
								b_ordenar.setText(tx.ordenar);
							}
						}
						if (m.numeroD < m
								.numeroI) {
							if (m.indiceIz == m.numeroI) {
								m.indiceDer = 0;
								m.indiceIz = 0;
								b_ordenar.setText(tx.ordenar);
							}
						}
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

		}

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

		m.dispose();
		sk_skin.dispose();
		fu_fuente.dispose();
		ta_atlas.dispose();
		stage.dispose();
		batch.dispose();

	}

}
