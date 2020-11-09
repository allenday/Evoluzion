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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Screen implements com.badlogic.gdx.Screen {

	protected Evoluzion ev;
	protected World world;
	Text tx;

	private final OrthographicCamera camara;
	private final SpriteBatch batch;
	private final ShapeRenderer caja;
	private final ShapeRenderer borde;
	private final ShapeRenderer frontera;
	private final ShapeRenderer pausaCaja;
	private final BitmapFont fuente;
	private final BitmapFont fu_fuente;
	private final boolean debugin = false;
	private final TextureAtlas ta_atlas;// carga imagenes de atlas de texturas
	private Stage stage; // stage maneja elementos que reciben entradas como
	// botones o eventos
	private final Skin sk_skin; // almacena recursos de atlas como imagenes y colores
	// para ser usados mas facilmente
	private TextButton bPause, bExit, bCollect; // crea botones con texto
	// similares a los de
	// swing
	private final int viewPanel = 1;
	private int viewButtons = 1;

	Organism or;
	Senergy se;
	Qenergy qe;
	int number = 0; // se usa para que los metodos cuenten elementos
	int quantity = 0;
	int quantity2 = 0;
	int quantity3 = 0;
	int count1, count2, count3;
	int press = 1; // 1=false -1 = true
	int mark = 1; // 1=false -1 = true
	int verFrontera = 1;// 1=false -1 = true
	boolean keypress = false;

	private final NumberFormat format = new DecimalFormat("0.00");
	private final NumberFormat format3 = new DecimalFormat("0.0");
	private final NumberFormat format2 = new DecimalFormat("#");
	private TextButton b_colectarP;
	boolean todoGuardado = false;
	private TextButton bCatastrophe;
	private TextButton b_guardar;
	private TextButton b_colectarPM;
	private TextButton b_colectarPnM;
	private TextButton bSelectAll;

	private CheckBox cb_viewEnergy;
	private final Skin sk_skin2;
	private final TextureAtlas ta_atlas2;
	private CheckBox cb_viewMass;
	private CheckBox cb_verOrganismos;
	private TextButton b_antibiotico;
	private TextButton b_frontera;
	int fps = 30; // limit to 30 fps

	// int fps = 0;//without any limits

	public Screen(Evoluzion ev, World world) {

		this.ev = ev;
		this.world = world;
		tx = world.tx;// usamos la configuracionde taxto del menu inicio
		if (world.organisms.size > 0) {
			or = world.organisms.get(0);
		}
		if (world.ase.size > 0) {
			se = world.ase.get(0);
		}
		if (world.aqe.size > 0) {
			qe = world.aqe.get(0);
		}

		camara = new OrthographicCamera();
		camara.setToOrtho(false, world.ancho, world.alto);

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
	CheckBox cb_viewAlleles;
	CheckBox cb_verDatos;
	CheckBox cb_viewPhenotype;
	CheckBox cb_viewGenotype;
	TextButton b_ordenar;
	private TextButton b_stop;

	@Override
	public void render(float delta) {

		// Game loop
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camara.update();
		stage.act(delta);

		// entradasTeclado();

		if (viewButtons == 1) {

			b_colectarP.setVisible(false);
			b_colectarPM.setVisible(false);
			b_colectarPnM.setVisible(false);

			viewButtons = viewButtons * 2;

		}

		if (viewButtons == -1) {

			b_colectarP.setVisible(true);
			b_colectarPM.setVisible(true);
			b_colectarPnM.setVisible(true);

			viewButtons = viewButtons * 2;
		}

		if (world.pausaGame == 1) { // 1=play -1=pausa
			world.update();// actualiza los datos que maneja el Mundo
			// System.out.println("rendering");
		}

		if (world.organisms.size > 0 && world.seconds4 >= world.maxTime
				&& world.maxTime > 0) {

			b_ordenar.setVisible(true);

		}

		batch.setProjectionMatrix(camara.combined);
		// comienzo a graficar
		//
		// dibuja Senergia

		if (cb_viewEnergy.isChecked() == true) {
			quantity = world.ase.size;

			for (count1 = quantity - 1; count1 >= 0; count1--) {
				se = world.ase.get(count1);
				se.verObjeto(batch);
			}
		}
		// dibujamos Qenergia
		if (cb_viewMass.isChecked() == true) {
			quantity2 = world.aqe.size;

			for (count2 = quantity2 - 1; count2 >= 0; count2--) {
				qe = world.aqe.get(count2);
				qe.verObjeto(batch);
			}
		}

		// for(Qenergia qe :m.aqe){qe.verObjeto(batch);}
		// dibujamos los organismos

		if (cb_verOrganismos.isChecked() == true) {
			quantity3 = world.organisms.size;

			for (count3 = quantity3 - 1; count3 >= 0; count3--) {
				or = world.organisms.get(count3);
				or.verOrganismo(batch);
			}
			// verl los organismos marcados
			quantity3 = world.organisms.size;

			for (count3 = quantity3 - 1; count3 >= 0; count3--) {
				or = world.organisms.get(count3);
				or.verMarcado(borde, batch, fuente);
			}
		}

		// dibujar rectangulos en modo debug
		if (debugin == true) {

			for (Organism or : world.organisms) {
				or.verBorde(borde);
			}

			for (Qenergy qe : world.aqe) {
				qe.verBorde(borde);
			}
		}

		if (world.verFrontera == true) {

			frontera.begin(ShapeType.Filled);
			frontera.setColor(Color.CYAN);
			frontera.rect(world.frontera.x, world.frontera.y, world.frontera.width,
					world.frontera.height);
			frontera.end();
		}

		caja.begin(ShapeType.Filled);

		caja.setColor(Color.BLACK);
		caja.rect(0, world.alto - 30, world.ancho, 30);
		caja.end();
		batch.begin();
		fuente.draw(batch, "|h: " + world.horas + " |m: " + world.addCero2().toString()
						+ world.minutos + " |s: " + world.addCero().toString() + world.seconds,
				450, world.alto - 60);

		if (world.antibiotico == 1) {
			fuente.draw(batch, tx.antibioticoON, 450, world.alto - 80);
		} else {
			fuente.draw(batch, tx.antibioticoOFF, 455, world.alto - 80);
		}

		batch.end();

		if (world.pausaGame == -1) {

			pausaCaja.begin(ShapeType.Filled);
			pausaCaja.setColor(Color.BLACK);
			pausaCaja.rect((world.ancho / 2) - 100, (world.alto / 2) - 25, 200,
					50);
			pausaCaja.end();

			batch.begin();
			fuente.draw(batch, tx.simulacionEnPausa, 500, 370);
			fuente.draw(batch, tx.oprimaPlayPausa, 425, 350);
			batch.end();
		}

		if (world.verFrontera == false) {

			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(0, world.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch, tx.Organismos + world.organisms.size, 5, world.alto - 60);

				int masatotal = (int) (world.BiomasaTotal(world.organisms) + world
						.MateriaLibre());
				fuente.draw(batch, tx.masaTotal + masatotal, 5, world.alto - 80);
				fuente.draw(batch, tx.masa + world.MateriaLibre(), 5, world.alto - 100);
				fuente.draw(batch, tx.biomasa + world.BiomasaTotal(world.organisms), 5,
						world.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(world.velocidadMedia(world.organisms)), 5,
						world.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(world.tamanoMedio(world.organisms)),
						5, world.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(world.tasaMutMedio(world.organisms)), 5,
						world.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(world.longevidadMedio(world.organisms))
								+ " (s)", 5, world.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + world.cantidadResistentes(world.organisms), 5,
						world.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(world.temperatura), 5,
						world.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(world.temOptimaMedia(world.organisms)), 5,
						world.alto - 260);

				batch.end();
			}

			if (cb_viewAlleles.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(0, world.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 5, world.alto - 300);
				// mostrar % de alelos
				world.colectarAlelos(world.organisms, world.aAlelos);

				number = world.aAlelos.size;
				float renglon = world.alto - 330;
				for (int i = 0; i < number; i++) {

					Allele al = world.aAlelos.get(i);

					if (!al.name.equals(tx.color)
							|| !al.name.equals(tx.longevidad)
							|| !al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.organisms.size * 2;
					}

					if (al.name.equals(tx.color)
							|| al.name.equals(tx.longevidad)
							|| al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.cantidadHembras(world.organisms) * 2
								+ world.cantidadMachos(world.organisms);
					}

					fuente.draw(
							batch,
							al.name
									+ ": "
									+ "a"
									+ al.identifier
									+ " > "
									+ format3
									.format(((float) al.quantity / (float) quantity) * 100)
									+ " %", 5, renglon);

					renglon = renglon - 20;

				}

				batch.end();
			}

		}

		if (world.verFrontera == true) {

			// panel izquierdo
			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(0, world.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch,
						tx.Organismos + world.numeroI, 5,
						world.alto - 60);

				int masatotal = world.BiomasaTotalI(world.organisms) + world
						.MateriaLibreL();
				fuente.draw(batch, tx.masaTotal + masatotal, 5, world.alto - 80);
				fuente.draw(batch, tx.masa + world.MateriaLibreL(), 5, world.alto - 100);
				fuente.draw(batch, tx.biomasa + world.BiomasaTotalI(world.organisms), 5,
						world.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(world.velocidadMediaI(world.organisms)), 5,
						world.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(world.tamanoMedioI(world.organisms)),
						5, world.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(world.tasaMutMedioI(world.organisms)), 5,
						world.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(world.longevidadMedioI(world.organisms))
								+ " (s)", 5, world.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + world.cantidadResistentesI(world.organisms), 5,
						world.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(world.temperatura), 5,
						world.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(world.temOptimaMediaI(world.organisms)), 5,
						world.alto - 260);

				batch.end();
			}

			if (cb_viewAlleles.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(0, world.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 5, world.alto - 300);
				// mostrar % de alelos
				world.colectarAlelosI(world.organisms, world.aAlelos);

				number = world.aAlelos.size;
				float renglon = world.alto - 330;
				for (int i = number - 1; i >= 0; i--) {

					Allele al = world.aAlelos.get(i);

					if (!al.name.equals(tx.color)
							|| !al.name.equals(tx.longevidad)
							|| !al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.organisms.size * 2;
					}

					if (al.name.equals(tx.color)
							|| al.name.equals(tx.longevidad)
							|| al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.cantidadHembrasI(world.organisms) * 2
								+ world.cantidadMachosI(world.organisms);
					}

					fuente.draw(
							batch,
							al.name
									+ ": "
									+ "a"
									+ al.identifier
									+ " > "
									+ format3
									.format(((float) al.quantity / (float) quantity) * 100)
									+ " %", 5, renglon);

					renglon = renglon - 20;
				}

				batch.end();
			}

			// panel derecho

			if (cb_verDatos.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(840, world.alto - 300, 180, 300);
				caja.end();

				batch.begin();

				fuente.setColor(Color.WHITE);
				fuente.draw(batch,
						tx.Organismos + world.numeroD, 840,
						world.alto - 60);

				int masatotalD = world.BiomasaTotalD(world.organisms) + world
						.MateriaLibreR();
				fuente.draw(batch, tx.masaTotal + masatotalD, 840, world.alto - 80);
				fuente.draw(batch, tx.masa + world.MateriaLibreR(), 840,
						world.alto - 100);
				fuente.draw(batch, tx.biomasa + world.BiomasaTotalD(world.organisms), 840,
						world.alto - 120);
				fuente.draw(
						batch,
						tx.velocidadMedia
								+ format.format(world.velocidadMediaD(world.organisms)),
						840, world.alto - 140);
				fuente.draw(batch,
						tx.tamanoMedi + format.format(world.tamanoMedioD(world.organisms)),
						840, world.alto - 160);
				fuente.draw(
						batch,
						tx.tasaMutacionMedia
								+ format2.format(world.tasaMutMedioD(world.organisms)), 840,
						world.alto - 180);
				fuente.draw(batch,
						tx.vidaMdia + format.format(world.longevidadMedioD(world.organisms))
								+ " (s)", 840, world.alto - 200);
				fuente.draw(batch,
						tx.resistensiaATB + world.cantidadResistentesD(world.organisms),
						840, world.alto - 220);
				fuente.draw(batch,
						tx.temperatura + format.format(world.temperatura), 840,
						world.alto - 240);
				fuente.draw(
						batch,
						tx.temOptimaMedia
								+ format.format(world.temOptimaMediaD(world.organisms)),
						840, world.alto - 260);

				batch.end();
			}

			if (cb_viewAlleles.isChecked()) {

				caja.begin(ShapeType.Filled);
				caja.setColor(Color.BLACK);

				caja.rect(840, world.alto - 540, 180, 250);
				caja.end();

				batch.begin();

				fuente.draw(batch, tx.alelo + "s:", 840, world.alto - 300);
				// mostrar % de alelos
				world.colectarAlelosD(world.organisms, world.aAlelos);

				number = world.aAlelos.size;
				float renglon = world.alto - 330;
				for (int i = number - 1; i >= 0; i--) {

					Allele al = world.aAlelos.get(i);

					if (!al.name.equals(tx.color)
							|| !al.name.equals(tx.longevidad)
							|| !al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.organisms.size * 2;
					}

					if (al.name.equals(tx.color)
							|| al.name.equals(tx.longevidad)
							|| al.name.equals(tx.fidelidadADNpol)) {

						quantity = world.cantidadHembrasD(world.organisms) * 2
								+ world.cantidadMachosD(world.organisms);
					}

					fuente.draw(
							batch,
							al.name
									+ ": "
									+ "a"
									+ al.identifier
									+ " > "
									+ format3
									.format(((float) al.quantity / (float) quantity) * 100)
									+ " %", 840, renglon);

					renglon = renglon - 20;
				}

				batch.end();
			}

		}

		cb_viewEnergy.setVisible(true);
		cb_viewMass.setVisible(true);
		cb_verOrganismos.setVisible(true);

		if (viewPanel == -1) {

			cb_viewEnergy.setVisible(false);
			cb_viewMass.setVisible(false);
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

			quantity3 = world.organisms.size;

			for (count3 = quantity3 - 1; count3 >= 0; count3--) {

				or = world.organisms.get(count3);
				if (touchPos.x > or.position.x
						&& touchPos.x < or.position.x + or.width
						&& touchPos.y > or.position.y
						&& touchPos.y < or.position.y + or.height) {

					or.mark = or.mark * (-1);

				}
			}
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camara.unproject(touchPos);

			quantity3 = world.organisms.size;

			for (count3 = quantity3 - 1; count3 >= 0; count3--) {

				or = world.organisms.get(count3);

				if (or.mark == -1 && touchPos.x > or.position.x - or.width
						&& touchPos.x < or.position.x + or.width * 2
						&& touchPos.y > or.position.y - or.height
						&& touchPos.y < or.position.y + or.height * 2) {

					or.position.x = touchPos.x - or.width / 2;
					or.position.y = touchPos.y - or.height / 2;
					or.Ordenar();
				}
			}

		}

		if (Gdx.input.isKeyPressed(Keys.P)) {

			world.pausaGame = world.pausaGame * (-1);

		}

	}

	public void entradasTeclado() {

		Gdx.input.setInputProcessor(new Keyboard(this));
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			// stage maneja elementos que reciben entradas como botones o
			// eventos
			// en este caso se us apara los botones

			ScreenViewport viewport = new ScreenViewport();
			viewport.update(height, width, true);
			stage = new Stage(viewport);
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

			bCollect = new TextButton(tx.tomarMuestra, estilo);
			bCollect.setWidth(130);
			bCollect.setHeight(20);
			bCollect.setX(200);
			bCollect.setX(0);
			bCollect.setY(world.alto - 20);

			b_guardar = new TextButton(tx.menuGuardar, estilo);
			b_guardar.setWidth(130);
			b_guardar.setHeight(20);
			b_guardar.setX(130);
			b_guardar.setY(world.alto - 20);

			b_colectarP = new TextButton(tx.guardarTodos, estilo);
			b_colectarP.setWidth(130);
			b_colectarP.setHeight(20);
			b_colectarP.setX(130);
			b_colectarP.setY(world.alto - 65);

			b_colectarPM = new TextButton(tx.guardarMarcados, estilo);
			b_colectarPM.setWidth(130);
			b_colectarPM.setHeight(20);
			b_colectarPM.setX(130);
			b_colectarPM.setY(world.alto - 88);

			b_colectarPnM = new TextButton(tx.guardarNoMarcados, estilo);
			b_colectarPnM.setWidth(130);
			b_colectarPnM.setHeight(20);
			b_colectarPnM.setX(130);
			b_colectarPnM.setY(world.alto - 111);

			bSelectAll = new TextButton(tx.marcarDesmarcar, estilo);
			bSelectAll.setWidth(130);
			bSelectAll.setHeight(20);
			bSelectAll.setX(260);
			bSelectAll.setY(world.alto - 20);

			b_antibiotico = new TextButton(tx.antibiotico, estilo);
			b_antibiotico.setWidth(130);
			b_antibiotico.setHeight(20);
			b_antibiotico.setX(390);
			b_antibiotico.setY(world.alto - 20);

			b_frontera = new TextButton(tx.frontera, estilo);
			b_frontera.setWidth(130);
			b_frontera.setHeight(20);
			b_frontera.setX(390);
			b_frontera.setY(world.alto - 42);

			bCatastrophe = new TextButton(tx.catastrofe, estilo);
			bCatastrophe.setWidth(140);
			bCatastrophe.setHeight(20);
			bCatastrophe.setX(520);
			bCatastrophe.setY(world.alto - 20);

			b_stop = new TextButton(tx.parar, estilo);
			b_stop.setWidth(130);
			b_stop.setHeight(20);
			b_stop.setX(660);
			b_stop.setY(world.alto - 20);

			bPause = new TextButton(tx.playPausa, estilo);
			bPause.setWidth(130);
			bPause.setHeight(20);
			bPause.setX(770);
			bPause.setY(world.alto - 20);

			b_ordenar = new TextButton(tx.ordenar, estilo);
			b_ordenar.setVisible(false);
			b_ordenar.setWidth(130);
			b_ordenar.setHeight(35);
			b_ordenar.setX((world.ancho / 2) - 130 / 2);
			b_ordenar.setY(5);

			bExit = new TextButton(tx.guardarYcerrar, estilo);
			bExit.setWidth(130);
			bExit.setHeight(20);
			bExit.setX(900);
			bExit.setY(world.alto - 20);

			CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
			checkBoxStyle.checkboxOff = sk_skin2.getDrawable("boxN0");
			checkBoxStyle.checkboxOn = sk_skin2.getDrawable("boxYES");
			checkBoxStyle.font = fu_fuente;

			cb_verDatos = new CheckBox(tx.verDatos, checkBoxStyle);
			cb_verDatos.getCells().get(0).size(15, 15);
			cb_verDatos.setChecked(true);
			cb_verDatos.setPosition(-10, 630);

			cb_viewAlleles = new CheckBox(tx.verAlelos, checkBoxStyle);
			cb_viewAlleles.getCells().get(0).size(15, 15);
			cb_viewAlleles.setChecked(true);
			cb_viewAlleles.setPosition(100, 630);

			cb_viewPhenotype = new CheckBox(tx.verFenotipo, checkBoxStyle);
			cb_viewPhenotype.getCells().get(0).size(15, 15);
			cb_viewPhenotype.setChecked(true);
			cb_viewPhenotype.setPosition(220, 630);

			cb_viewGenotype = new CheckBox(tx.verGenotipo, checkBoxStyle);
			cb_viewGenotype.getCells().get(0).size(15, 15);
			cb_viewGenotype.setChecked(true);
			cb_viewGenotype.setPosition(380, 630);

			cb_viewEnergy = new CheckBox(tx.verEnergia, checkBoxStyle);
			cb_viewEnergy.getCells().get(0).size(15, 15);
			cb_viewEnergy.setChecked(false);
			cb_viewEnergy.setPosition(530, 630);

			cb_viewMass = new CheckBox(tx.verMasa, checkBoxStyle);
			cb_viewMass.getCells().get(0).size(15, 15);
			cb_viewMass.setChecked(false);
			cb_viewMass.setPosition(680, 630);

			cb_verOrganismos = new CheckBox(tx.verOrgansimo, checkBoxStyle);
			cb_verOrganismos.getCells().get(0).size(15, 15);
			cb_verOrganismos.setChecked(true);
			cb_verOrganismos.setPosition(830, 630);

			stage.addActor(cb_verDatos);
			stage.addActor(cb_viewAlleles);
			// stage.addActor(cb_verFenotipo);
			// stage.addActor(cb_verGenotipo);
			stage.addActor(cb_viewEnergy);
			stage.addActor(cb_viewMass);
			stage.addActor(cb_verOrganismos);
			stage.addActor(bExit);
			stage.addActor(bPause);
			stage.addActor(b_stop);
			// stage.addActor(b_verOcultar);
			stage.addActor(b_guardar);
			stage.addActor(bCollect);
			stage.addActor(b_colectarP);
			stage.addActor(b_colectarPM);
			stage.addActor(b_colectarPnM);
			stage.addActor(bSelectAll);
			stage.addActor(bCatastrophe);

			stage.addActor(b_antibiotico);
			stage.addActor(b_frontera);
			stage.addActor(b_ordenar);

			// se agregan los listener para los botones

			bExit.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
										 int pointer, int button) {
					world.pausaGame = world.pausaGame * (-1);

					Object[] options = {tx.si, tx.no};
					int n = JOptionPane.showOptionDialog(null,
							tx.terminarGuardarMensaje, "",

							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[1]);

					if (n == JOptionPane.YES_OPTION) {
						if (todoGuardado == false) {
							if (world.organisms.size > 0) {
								world.guardarDatos();
								world.archivarGenoma();
								world.archivarGenotipo();
								world.archivarFenotipo2();
								world.archivarAlelos();
							}
							world.f_genes.cerrarArchivo();
							// m.f_proteoma.cerrarArchivo();
							// m.f_mutantes.cerrarArchivo();
							world.f_datos.cerrarArchivo();
							world.f_alelos.cerrarArchivo();
							world.f_genotipos.cerrarArchivo();
						}
						world.f_fenotipos.cerrarArchivo();

						ev.setScreen(new StartMenu(ev));
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

					world.pausaGame = world.pausaGame * (-1);

					Object[] options = {tx.si, tx.no};
					int n = JOptionPane.showOptionDialog(null,
							tx.terminarMensaje, "",

							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[1]);

					if (n == JOptionPane.YES_OPTION) {

						world.parar = true;
						world.pausaGame = world.pausaGame * (-1);
						b_ordenar.setVisible(true);
						b_stop.setTouchable(Touchable.disabled);
						bCatastrophe.setTouchable(Touchable.disabled);
						b_antibiotico.setTouchable(Touchable.disabled);
						bPause.setTouchable(Touchable.disabled);
					}
					if (n == JOptionPane.NO_OPTION) {
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {

				}
			});

			bPause.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
										 int pointer, int button) {

					world.pausaGame = world.pausaGame * (-1);

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

					viewButtons = viewButtons / 2;
					viewButtons = viewButtons * (-1);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				}
			});

			bCollect.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
										 int pointer, int button) {
					world.colectorEspesies();
					world.guardarDatos();
					world.archivarGenoma();
					// m.archivarProteoma();
					world.archivarGenotipo();
					world.archivarAlelos();
					world.archivarFenotipo2();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					viewButtons = viewButtons / 2;
					// verBotones1= verBotones1*(-1);

				}
			});

			b_colectarP.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					world.pausaGame = -1;
					world.guardarPoblacion();
					// m.pausaGame=1;
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

					viewButtons = viewButtons / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			b_colectarPM.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					world.pausaGame = -1;
					world.guardarPoblacionMarcada();
					//
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

					viewButtons = viewButtons / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			b_colectarPnM.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					world.pausaGame = -1;
					world.guardarPoblacionNoMaracada();
					//
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {

					viewButtons = viewButtons / 2;
					// verBotones1= verBotones1*(-1);
					// m.pausaGame= 1;

				}
			});

			bSelectAll.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
										 int pointer, int button) {
					number = world.organisms.size;
					mark = mark * (-1);
					for (int i = number - 1; i >= 0; i--) {

						if (mark == 1) {
							world.organisms.get(i).mark = 1;
						}
						if (mark == -1) {
							world.organisms.get(i).mark = -1;
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

					world.antibiotico = world.antibiotico * (-1);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				}
			});

			bCatastrophe.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
										 int pointer, int button) {

					world.catastrofe();

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
						world.verFrontera = false;

						world.f_datos.writeArchive("\n" + tx.datosOrdenados);
					}
					if (verFrontera == -1) {
						world.verFrontera = true;
						world.f_datos.writeArchive("\n" + tx.datosOrdenados2);
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

					world.Ordenar();

					b_ordenar.setText(tx.Siguiente);

					if (world.verFrontera == false) {
						if (world.indice == world.organisms.size) {
							world.indice = 0;
							b_ordenar.setText(tx.ordenar);
						}
					}
					if (world.verFrontera == true) {
						if (world.numeroD == world
								.numeroI) {
							if (world.indiceDer == world.numeroD) {
								world.indiceDer = 0;
								world.indiceIz = 0;
								b_ordenar.setText(tx.ordenar);
							}
						}
						if (world.numeroD > world
								.numeroI) {
							if (world.indiceDer == world.numeroD) {
								world.indiceDer = 0;
								world.indiceIz = 0;
								b_ordenar.setText(tx.ordenar);
							}
						}
						if (world.numeroD < world
								.numeroI) {
							if (world.indiceIz == world.numeroI) {
								world.indiceDer = 0;
								world.indiceIz = 0;
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

		world.dispose();
		sk_skin.dispose();
		fu_fuente.dispose();
		ta_atlas.dispose();
		stage.dispose();
		batch.dispose();

	}

}
