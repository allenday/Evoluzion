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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuInicio implements Screen, Serializable {

	Evoluzion ev;
	Texto tx;

	Mundo m;
	OrthographicCamera camara;
	SpriteBatch batch;
	Stage stage;
	File f2;
	TextureAtlas ta_atlas, ta_atlas2;// carga imagenes de atlas de texturas
	Skin sk_skin, sk_skin2, sk_skin3; // almacena recursos de atlas como
										// imagenes y colores para ser usados
										// mas facilmente
	TextButton b_salir, b_comenzar; // crea botones con texto similares a los de
									// swing
	CheckBoxStyle cbStile;

	ShapeRenderer rectangulo;

	Archivar a_ruta;
	Archivar a_idioma;

	Sprite titulo;
	Texture tx_titulo;

	int verPanel = 1;
	int pausaGame = 1;

	float ancho = Gdx.graphics.getWidth();
	float alto = Gdx.graphics.getHeight();
	float orX = 0;
	float orY = 190;

	int numSenL = 800;
	int numQenL = 800;
	int SenergiaL = 20;
	int QbiomasaL = 20;
	int numSenR = 800;
	int numQenR = 800;
	int SenergiaR = 20;
	int QbiomasaR = 20;
	int tiempoMuestreo = 60;
	int ingles = -1; // -1=false 1=true
	boolean mutarColor = true;
	boolean mutarTamaño = true;
	boolean mutarSpeed = true;
	boolean mutarSentir = true;
	boolean mutarPredador = true;

	boolean colectSize = true;
	boolean colectSpeed = true;
	boolean colectColor = true;
	boolean colectSentir = true;
	boolean colectPredador = true;

	String nulo = "";
	String cero = "0";

	BitmapFont fuente;
	BitmapFont fu_fuente;
	BitmapFont fu_titulo;
	//
	CheckBox cb_mutar;
	CheckBox cb_mutarSize;
	CheckBox cb_mutarSpeed;
	CheckBox cb_mutarSentir;
	CheckBox cb_mutarPredador;
	CheckBox cb_mutarCazar;
	CheckBox cb_mutarEscapar;
	CheckBox cb_mutarRadioCon;
	CheckBox cb_mutarTasaMut;
	CheckBox cb_mutarLongevidad;

	CheckBox cb_colectarColor;
	CheckBox cb_colectarSize;
	CheckBox cb_colectarSpeed;
	CheckBox cb_colectarSentir;
	CheckBox cb_colectarPredador;
	CheckBox cb_leerPoblacion;

	TextField text, text2, tf_energia, tf_biomasa;
	TextFieldStyle tfs_text;

	TextField tf_Numbiomasa;
	TextField tf_Numenergia;
	private TextButton b_GuardarEn;

	String ruta = "./";
	private String poblacion = "";
	private TextButton b_CargarP;
	private CheckBox cb_colectarTasaMut;
	private CheckBox cb_colectarLongevidad;
	private CheckBox cb_colectarCazar;
	private CheckBox cb_colectarEscapar;
	private CheckBox cb_colectarRadioCon;
	private TextField text3;
	private TextButton b_Informacion, b_mutar_todo, b_mutar_nada,
			b_seguir_todo, b_seguir_nada;
	private TextField text4;
	private TextField tf_Cantidad;
	private CheckBox cb_moverMasa;
	private TextField tf_CantidadMax;
	private CheckBox cb_colectarTolerancia;
	private CheckBox cb_mutartolerancia;
	private TextField tf_Temperatura;
	private TextField tf_Start1;
	private TextField tf_Start2;
	private TextField tf_TempFinal1;
	private TextField tf_TempFinal2;
	private TextField tf_DeltaTiempo1;
	private TextField tf_DeltaTiempo2;
	private TextField tf_MultiploPol;
	private CheckBox cb_mutarResistencia;
	private CheckBox cb_colectarResistencia;
	private TextField tf_ATB;
	private TextButton b_Idioma;
	private TextField tf_HorizontalTransfer;

	private TextField tf_NumenergiaR;
	private TextField tf_NumbiomasaR;
	private CheckBox cb_Frontera;
	private TextField tf_energiaR;
	private TextField tf_biomasaR;

	public MenuInicio(Evoluzion ev) {

		this.ev = ev;
		tx = new Texto();
		// m= new Mundo(ev,"",0,50,50,20,20,false);

		camara = new OrthographicCamera();
		camara.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		f2 = new File("evo_star.conf");

		batch = new SpriteBatch();

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
		// fu_titulo = new
		// BitmapFont(Gdx.files.internal("data/fMensage.fnt"),false);
		// tx_titulo = new Texture("data/titulo.png");
		// titulo = new Sprite(tx_titulo);
		// titulo.setSize(500,100);
		// titulo.setPosition(50,550);

		rectangulo = new ShapeRenderer();

		a_ruta = new Archivar();
		a_idioma = new Archivar();

		leerRuta();
		leerIdioma();

		if (ingles == 1) {
			tx.setIngles();
		}
		if (ingles == -1) {
			tx.setEspanol();
		}

		controles();
		// System.out.println("idioma "+ingles);

		leerMenuIncio();

	}

	// leer los archivos de configuracion

	public void escribirIdioma() {

		a_idioma.creararchivo("evo_idioma.tmp");

		if (ingles == 1) {
			a_idioma.escribirArchivo("eng");
		}
		if (ingles == -1) {
			a_idioma.escribirArchivo("spa");
		}
		a_idioma.cerrarArchivo();
	}

	public void leerIdioma() {

		try {
			FileReader fr = new FileReader("evo_idioma.tmp");
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				String str = linea.substring(0, linea.length());

				if (str.equals("eng")) {
					ingles = 1;
				}
				if (str.equals("spa")) {
					ingles = -1;
				}

			}

			br.close();
			fr.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void escribirRuta() {

		a_ruta.creararchivo("evo_ruta.tmp");
		a_ruta.escribirArchivo(ruta);
		a_ruta.cerrarArchivo();

	}

	public String leerRuta() {

		try {
			FileReader fr2 = new FileReader("evo_ruta.tmp");
			BufferedReader br = new BufferedReader(fr2);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				ruta = linea.substring(0, linea.length());

			}

			br.close();
			fr2.close();
		} catch (FileNotFoundException e) {
			ruta = System.getProperty("user.dir");
			e.printStackTrace();
		} catch (IOException e) {
			ruta = System.getProperty("user.dir");// TODO Auto-generated catch
													// block
			e.printStackTrace();
		}
		return ruta;
	}

	// leer la configuracion inicial

	public void guardarMenuInicio() {

		try {

			FileOutputStream fos2 = new FileOutputStream(f2);
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);

			// oos2.writeObject(text.getText());

			oos2.writeObject(text2.getText());// tiempo entre muestras
			oos2.writeObject(text3.getText());// tiemo entre catastrofes
			oos2.writeObject(text4.getText());// tiempo de partida
			oos2.writeObject(tf_ATB.getText());// tiempo de ATB

			oos2.writeObject(tf_Cantidad.getText());
			oos2.writeObject(tf_CantidadMax.getText());
			oos2.writeObject(cb_moverMasa.isChecked());
			oos2.writeObject(cb_Frontera.isChecked());

			oos2.writeObject(tf_energia.getText());
			oos2.writeObject(tf_biomasa.getText());
			oos2.writeObject(tf_Numbiomasa.getText());
			oos2.writeObject(tf_Numenergia.getText());
			oos2.writeObject(tf_energiaR.getText());
			oos2.writeObject(tf_biomasaR.getText());
			oos2.writeObject(tf_NumbiomasaR.getText());
			oos2.writeObject(tf_NumenergiaR.getText());

			oos2.writeObject(cb_mutar.isChecked());
			oos2.writeObject(cb_mutarSize.isChecked());
			oos2.writeObject(cb_mutarSpeed.isChecked());
			oos2.writeObject(cb_mutarSentir.isChecked());
			oos2.writeObject(cb_mutarPredador.isChecked());
			oos2.writeObject(cb_mutarCazar.isChecked());
			oos2.writeObject(cb_mutarEscapar.isChecked());
			oos2.writeObject(cb_mutarRadioCon.isChecked());
			oos2.writeObject(cb_mutarTasaMut.isChecked());
			oos2.writeObject(cb_mutarLongevidad.isChecked());
			oos2.writeObject(cb_mutartolerancia.isChecked());
			oos2.writeObject(cb_mutarResistencia.isChecked());

			oos2.writeObject(cb_colectarColor.isChecked());
			oos2.writeObject(cb_colectarSize.isChecked());
			oos2.writeObject(cb_colectarSpeed.isChecked());
			oos2.writeObject(cb_colectarSentir.isChecked());
			oos2.writeObject(cb_colectarPredador.isChecked());
			oos2.writeObject(cb_colectarCazar.isChecked());
			oos2.writeObject(cb_colectarEscapar.isChecked());
			oos2.writeObject(cb_colectarRadioCon.isChecked());
			oos2.writeObject(cb_colectarTasaMut.isChecked());
			oos2.writeObject(cb_colectarLongevidad.isChecked());
			oos2.writeObject(cb_colectarTolerancia.isChecked());
			oos2.writeObject(cb_colectarResistencia.isChecked());

			oos2.close();
			fos2.close();

		}

		catch (Exception ex) {
			// JFrame jf = new JFrame();
			// jf.setVisible(true);
			// jf.setLocation(500, 500);
			// jf.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(null, tx.errorEscribir);
			ex.printStackTrace();
			// jf.dispose();
		}

	}

	public void leerMenuIncio() {

		try {
			FileInputStream fis = new FileInputStream(f2);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// text.setText((String)ois.readObject());

			text2.setText((String) ois.readObject());
			text3.setText((String) ois.readObject());
			text4.setText((String) ois.readObject());
			tf_ATB.setText((String) ois.readObject());

			tf_Cantidad.setText((String) ois.readObject());
			tf_CantidadMax.setText((String) ois.readObject());
			cb_moverMasa.setChecked((Boolean) ois.readObject());
			cb_Frontera.setChecked((Boolean) ois.readObject());

			tf_energia.setText((String) ois.readObject());
			tf_biomasa.setText((String) ois.readObject());
			tf_Numbiomasa.setText((String) ois.readObject());
			tf_Numenergia.setText((String) ois.readObject());
			tf_energiaR.setText((String) ois.readObject());
			tf_biomasaR.setText((String) ois.readObject());
			tf_NumbiomasaR.setText((String) ois.readObject());
			tf_NumenergiaR.setText((String) ois.readObject());

			cb_mutar.setChecked((Boolean) ois.readObject());
			cb_mutarSize.setChecked((Boolean) ois.readObject());
			cb_mutarSpeed.setChecked((Boolean) ois.readObject());
			cb_mutarSentir.setChecked((Boolean) ois.readObject());
			cb_mutarPredador.setChecked((Boolean) ois.readObject());
			cb_mutarCazar.setChecked((Boolean) ois.readObject());
			cb_mutarEscapar.setChecked((Boolean) ois.readObject());
			cb_mutarRadioCon.setChecked((Boolean) ois.readObject());
			cb_mutarTasaMut.setChecked((Boolean) ois.readObject());
			cb_mutarLongevidad.setChecked((Boolean) ois.readObject());
			cb_mutartolerancia.setChecked((Boolean) ois.readObject());
			cb_mutarResistencia.setChecked((Boolean) ois.readObject());

			cb_colectarColor.setChecked((Boolean) ois.readObject());
			cb_colectarSize.setChecked((Boolean) ois.readObject());
			cb_colectarSpeed.setChecked((Boolean) ois.readObject());
			cb_colectarSentir.setChecked((Boolean) ois.readObject());
			cb_colectarPredador.setChecked((Boolean) ois.readObject());
			cb_colectarCazar.setChecked((Boolean) ois.readObject());
			cb_colectarEscapar.setChecked((Boolean) ois.readObject());
			cb_colectarRadioCon.setChecked((Boolean) ois.readObject());
			cb_colectarTasaMut.setChecked((Boolean) ois.readObject());
			cb_colectarLongevidad.setChecked((Boolean) ois.readObject());
			cb_colectarTolerancia.setChecked((Boolean) ois.readObject());
			cb_colectarResistencia.setChecked((Boolean) ois.readObject());

			ois.close();
			fis.close();

		} catch (Exception ex) {

			JOptionPane.showMessageDialog(null, tx.errorLectura);
			ingles = -1;
			text.setText(tx.nombre);
			text2.setText("10");
			text3.setText("0");
			text4.setText("0");
			tf_ATB.setText("0");
			tf_energia.setText("20");
			tf_biomasa.setText("5");
			tf_Numbiomasa.setText("1500");
			tf_Numenergia.setText("1500");

			tf_energiaR.setText("20");
			tf_biomasaR.setText("5");
			tf_NumbiomasaR.setText("1500");
			tf_NumenergiaR.setText("1500");

			tf_Cantidad.setText("10");
			tf_CantidadMax.setText("1500");

			cb_mutar.setChecked(true);
			cb_mutarSize.setChecked(true);
			cb_mutarSpeed.setChecked(true);
			cb_mutarSentir.setChecked(true);
			cb_mutarPredador.setChecked(true);
			cb_mutarCazar.setChecked(true);
			cb_mutarEscapar.setChecked(true);
			cb_mutarRadioCon.setChecked(true);
			cb_mutarTasaMut.setChecked(true);
			cb_mutarLongevidad.setChecked(true);
			cb_mutartolerancia.setChecked(true);
			cb_mutarResistencia.setChecked(true);

			cb_colectarColor.setChecked(true);
			cb_colectarSize.setChecked(true);
			cb_colectarSpeed.setChecked(false);
			cb_colectarSentir.setChecked(false);
			cb_colectarPredador.setChecked(false);
			cb_colectarCazar.setChecked(false);
			cb_colectarEscapar.setChecked(false);
			cb_colectarRadioCon.setChecked(false);
			cb_colectarTasaMut.setChecked(false);
			cb_colectarLongevidad.setChecked(false);
			cb_colectarResistencia.setChecked(false);
			cb_colectarResistencia.setChecked(false);

			ex.printStackTrace();
		}

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// System.out.println(ingles);
		camara.update();
		stage.act(delta);

		batch.setProjectionMatrix(camara.combined);
		// botones
		batch.begin();
		stage.draw();
		// cb_mutar.draw(batch, 1);
		batch.end();

		batch.begin();
		// fu_titulo.draw(batch, "evoluzion 0.5.6", 400, 550);
		// titulo.draw(batch);

		fuente.draw(batch, tx.simuladorDigital, 400 + orX, 680);

		fuente.draw(batch, tx.parametrosEnergiaMasa, 405 + orX, 390 + orY);
		fuente.draw(batch, tx.soloNenteros, 430 + orX, 370 + orY);

		fuente.draw(batch, tx.otrosParamentros, 750 + orX, 390 + orY);
		fuente.draw(batch, tx.soloNenteros, 730 + orX, 370 + orY);

		fuente.draw(batch, tx.directorioDeTrabajo + ruta, 20 + orX, 440 + orY);
		fuente.draw(batch, "" + poblacion, 180 + orX, 417 + orY);

		fuente.draw(batch, tx.panelIz + "--|--" + tx.panelDer, 400 + orX,
				348 + orY);

		fuente.draw(batch, tx.valorEnergia, 470 + orX, 325 + orY);
		fuente.draw(batch, tx.valorBiomasa, 470 + orX, 295 + orY);
		fuente.draw(batch, tx.cantidadEnergia, 460 + orX, 265 + orY);
		fuente.draw(batch, tx.cantidadBiomasa, 460 + orX, 235 + orY);

		fuente.draw(batch, tx.tiempoEntreMuestras, 700 + orX, 325 + orY);
		fuente.draw(batch, tx.tiempoEntreCatastrofes, 700 + orX, 297 + orY);
		fuente.draw(batch, tx.tiempoATB, 700 + orX, 265 + orY);
		fuente.draw(batch, tx.tiempoPartida, 700 + orX, 239 + orY);
		fuente.draw(batch, tx.numeroInOrganismos, 700 + orX, 209 + orY);
		fuente.draw(batch, tx.numeroMaximoOrg, 700 + orX, 177 + orY);
		fuente.draw(batch, tx.temperaturaInicial, 700 + orX, 109 + orY);

		fuente.draw(batch, tx.modificacionMambiente, 700 + orX, 77 + orY);

		fuente.draw(batch, tx.comenzar2, 670 + orX, 50 + orY);
		fuente.draw(batch, tx.UnominGradosMin, 660 + orX, 25 + orY);
		fuente.draw(batch, tx.DosminGradosMin, 660 + orX, -5 + orY);

		fuente.draw(batch, tx.funcionInactivada, 660 + orX, -33 + orY);

		fuente.draw(batch, tx.marcarGenesMutaran, 5 + orX, 370 + orY);
		fuente.draw(batch, tx.marcarMutAnalizar, 5 + orX, 170 + orY);

		fuente.draw(batch, tx.losfenotipos, 20 + orX, -15 + orY);

		fuente.draw(batch, tx.multiploADNpol, 65 + orX, -90 + orY);

		fuente.draw(batch, "1/", 20 + orX, -120 + orY);
		fuente.draw(batch, tx.horizontalTransferRate, 120 + orX, -120 + orY);

		// fuente.draw(batch,"* Múltiplo que afecta la eficiencia de la ADN polimerasa",
		// 20+orX, -40+orY);

		batch.end();

		rectangulo.begin(ShapeType.Filled);
		rectangulo.setColor(Color.WHITE);
		rectangulo.rect(20 + orX, 200 + orY, 350, 150); // panel genes mutar
		rectangulo.rect(650 + orX, -140 + orY, 355, 490);
		rectangulo.rect(380 + orX, 150 + orY, 260, 200);// panel de energia
		rectangulo.rect(20 + orX, -2 + orY, 350, 150); // panel genes seguir

		rectangulo.end();

	}

	public void chequeoTexto() {

		if (tf_ATB.getText().equals(nulo)) {
			tf_ATB.setText(cero);
		}
		if (tf_energia.getText().equals(nulo)) {
			tf_energia.setText(cero);
		}
		if (tf_biomasa.getText().equals(nulo)) {
			tf_biomasa.setText(cero);
		}
		if (tf_Numenergia.getText().equals(nulo)) {
			tf_Numenergia.setText(cero);
		}
		if (tf_Numbiomasa.getText().equals(nulo)) {
			tf_Numbiomasa.setText(cero);
		}
		if (tf_energiaR.getText().equals(nulo)) {
			tf_energiaR.setText(cero);
		}
		if (tf_biomasaR.getText().equals(nulo)) {
			tf_biomasaR.setText(cero);
		}
		if (tf_NumenergiaR.getText().equals(nulo)) {
			tf_NumenergiaR.setText(cero);
		}
		if (tf_NumbiomasaR.getText().equals(nulo)) {
			tf_NumbiomasaR.setText(cero);
		}
		if (text2.getText().equals(nulo)) {
			text2.setText(cero);
		}
		if (text3.getText().equals(nulo)) {
			text3.setText(cero);
		}

		if (text4.getText().equals(nulo)) {
			text4.setText(cero);
		}

		if (tf_Cantidad.getText().equals(nulo)) {
			tf_Cantidad.setText(cero);
		}

		if (tf_CantidadMax.getText().equals(nulo)) {
			tf_CantidadMax.setText(cero);
		}
		if (tf_Temperatura.getText().equals(nulo)) {
			tf_Temperatura.setText(cero);
		}
		if (tf_Start1.getText().equals(nulo)) {
			tf_Start1.setText(cero);
		}
		if (tf_Start2.getText().equals(nulo)) {
			tf_Start2.setText(cero);
		}
		if (tf_TempFinal1.getText().equals(nulo)) {
			tf_TempFinal1.setText(cero);
		}
		if (tf_TempFinal2.getText().equals(nulo)) {
			tf_TempFinal2.setText(cero);
		}
		if (tf_DeltaTiempo1.getText().equals(nulo)) {
			tf_DeltaTiempo1.setText(cero);
		}
		if (tf_DeltaTiempo2.getText().equals(nulo)) {
			tf_DeltaTiempo2.setText(cero);
		}
		if (tf_MultiploPol.getText().equals(nulo)) {
			tf_MultiploPol.setText(cero);
		}
		if (tf_HorizontalTransfer.getText().equals(nulo)) {
			tf_HorizontalTransfer.setText(cero);
		}

	}

	public void controles() {

		if (stage == null) {
			// stage maneja elementos que reciben entradas como botones o
			// eventos
			// en este caso se us apara los botones

			Viewport viewport = new ScreenViewport();
			viewport.update((int) ancho, (int) alto, true);
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

			b_comenzar = new TextButton(tx.comenzar, estilo);
			b_comenzar.setWidth(200);
			b_comenzar.setHeight(30);
			b_comenzar.setX((ancho / 2) - b_comenzar.getWidth() / 2 + orX);
			b_comenzar.setY(35 + orY);

			b_Informacion = new TextButton(tx.sobreEvolizion, estilo);
			b_Informacion.setWidth(120);
			b_Informacion.setHeight(30);
			b_Informacion.setX(ancho - b_Informacion.getWidth() - 5);
			b_Informacion.setY(alto - b_Informacion.getHeight() - 5);

			b_Idioma = new TextButton(tx.idioma, estilo);
			b_Idioma.setWidth(120);
			b_Idioma.setHeight(30);
			b_Idioma.setX(ancho - b_Informacion.getWidth() - 125);
			b_Idioma.setY(alto - b_Informacion.getHeight() - 5);

			b_GuardarEn = new TextButton(tx.directorioTrabajo2, estilo);
			b_GuardarEn.setWidth(200);
			b_GuardarEn.setHeight(30);
			b_GuardarEn.setX((ancho / 2) - b_comenzar.getWidth() / 2 + orX);
			b_GuardarEn.setY(115 + orY);

			b_CargarP = new TextButton(tx.cargaPoblacion, estilo);
			b_CargarP.setWidth(200);
			b_CargarP.setHeight(30);
			b_CargarP.setX((ancho / 2) - b_comenzar.getWidth() / 2);
			b_CargarP.setY(75 + orY);

			b_salir = new TextButton(tx.salir, estilo);
			b_salir.setWidth(200);
			b_salir.setHeight(30);
			b_salir.setX((ancho / 2) - b_salir.getWidth() / 2 + orX);
			b_salir.setY(-5 + orY);

			CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
			checkBoxStyle.checkboxOff = sk_skin2.getDrawable("boxN0");
			checkBoxStyle.checkboxOn = sk_skin2.getDrawable("boxYES");
			checkBoxStyle.font = fu_fuente;

			tfs_text = new TextFieldStyle();
			tfs_text.background = sk_skin2.getDrawable("boxN0");
			tfs_text.font = fu_fuente;
			tfs_text.fontColor = Color.BLACK;

			text = new TextField(tx.nombre, tfs_text);
			text.setSize(180, 25);
			text.setCursorPosition(0);
			text.setPosition(20 + orX, 450 + orY);

			text2 = new TextField("" + 60, tfs_text);
			text2.setSize(38, 20);
			text2.setCursorPosition(0);
			text2.setPosition(660 + orX, 310 + orY);

			text3 = new TextField("" + 0, tfs_text);
			text3.setSize(38, 20);
			text3.setCursorPosition(0);
			text3.setPosition(660 + orX, 280 + orY);

			tf_ATB = new TextField("" + 0, tfs_text);
			tf_ATB.setSize(38, 20);
			tf_ATB.setCursorPosition(0);
			tf_ATB.setPosition(660 + orX, 250 + orY);

			text4 = new TextField("" + 0, tfs_text);
			text4.setSize(38, 20);
			text4.setCursorPosition(0);
			text4.setPosition(660 + orX, 220 + orY);

			tf_energia = new TextField("20", tfs_text);
			tf_energia.setSize(30, 20);
			tf_energia.setCursorPosition(0);
			tf_energia.setPosition(410 + orX, 305 + orY);

			tf_energiaR = new TextField("20", tfs_text);
			tf_energiaR.setSize(30, 20);
			tf_energiaR.setCursorPosition(0);
			tf_energiaR.setPosition(590 + orX, 305 + orY);

			tf_biomasa = new TextField("20", tfs_text);
			tf_biomasa.setSize(30, 20);
			tf_biomasa.setCursorPosition(0);
			tf_biomasa.setPosition(410 + orX, 275 + orY);

			tf_biomasaR = new TextField("20", tfs_text);
			tf_biomasaR.setSize(30, 20);
			tf_biomasaR.setCursorPosition(0);
			tf_biomasaR.setPosition(590 + orX, 275 + orY);

			tf_Numenergia = new TextField("800", tfs_text);
			tf_Numenergia.setSize(40, 20);
			tf_Numenergia.setCursorPosition(0);
			tf_Numenergia.setPosition(410 + orX, 245 + orY);

			tf_NumenergiaR = new TextField("800", tfs_text);
			tf_NumenergiaR.setSize(40, 20);
			tf_NumenergiaR.setCursorPosition(0);
			tf_NumenergiaR.setPosition(580 + orX, 245 + orY);

			tf_Numbiomasa = new TextField("800", tfs_text);
			tf_Numbiomasa.setSize(40, 20);
			tf_Numbiomasa.setCursorPosition(0);
			tf_Numbiomasa.setPosition(410 + orX, 215 + orY);

			tf_NumbiomasaR = new TextField("800", tfs_text);
			tf_NumbiomasaR.setSize(40, 20);
			tf_NumbiomasaR.setCursorPosition(0);
			tf_NumbiomasaR.setPosition(580 + orX, 215 + orY);

			cb_moverMasa = new CheckBox(tx.moverLaMasa, checkBoxStyle);
			cb_moverMasa.getCells().get(0).size(15, 15);
			cb_moverMasa.setChecked(true);
			cb_moverMasa.setPosition(360 + orX, 160 + orY);

			cb_Frontera = new CheckBox(tx.verFrontera, checkBoxStyle);
			cb_Frontera.getCells().get(0).size(15, 15);
			cb_Frontera.setChecked(true);
			cb_Frontera.setPosition(360 + orX, 135 + orY);

			tf_Cantidad = new TextField("1", tfs_text);
			tf_Cantidad.setSize(38, 20);
			tf_Cantidad.setCursorPosition(0);
			tf_Cantidad.setPosition(660 + orX, 190 + orY);

			tf_CantidadMax = new TextField("1000", tfs_text);
			tf_CantidadMax.setSize(38, 20);
			tf_CantidadMax.setCursorPosition(0);
			tf_CantidadMax.setPosition(660 + orX, 160 + orY);

			cb_moverMasa = new CheckBox(tx.moverLaMasa, checkBoxStyle);
			cb_moverMasa.getCells().get(0).size(15, 15);
			cb_moverMasa.setChecked(true);
			cb_moverMasa.setPosition(360 + orX, 160 + orY);

			tf_Temperatura = new TextField("25", tfs_text);
			tf_Temperatura.setSize(38, 20);
			tf_Temperatura.setCursorPosition(0);
			tf_Temperatura.setPosition(660 + orX, 90 + orY);

			tf_Start1 = new TextField("0", tfs_text);
			tf_Start1.setSize(60, 20);
			tf_Start1.setCursorPosition(0);
			tf_Start1.setPosition(675 + orX, 10 + orY);

			tf_TempFinal1 = new TextField("0", tfs_text);
			tf_TempFinal1.setSize(60, 20);
			tf_TempFinal1.setCursorPosition(0);
			tf_TempFinal1.setPosition(780 + orX, 10 + orY);

			tf_DeltaTiempo1 = new TextField("0", tfs_text);
			tf_DeltaTiempo1.setSize(60, 20);
			tf_DeltaTiempo1.setCursorPosition(0);
			tf_DeltaTiempo1.setPosition(895 + orX, 10 + orY);

			tf_Start2 = new TextField("0", tfs_text);
			tf_Start2.setSize(60, 20);
			tf_Start2.setCursorPosition(0);
			tf_Start2.setPosition(675 + orX, -20 + orY);

			tf_TempFinal2 = new TextField("0", tfs_text);
			tf_TempFinal2.setSize(60, 20);
			tf_TempFinal2.setCursorPosition(0);
			tf_TempFinal2.setPosition(780 + orX, -20 + orY);

			tf_DeltaTiempo2 = new TextField("0", tfs_text);
			tf_DeltaTiempo2.setSize(60, 20);
			tf_DeltaTiempo2.setCursorPosition(0);
			tf_DeltaTiempo2.setPosition(895 + orX, -20 + orY);

			cb_leerPoblacion = new CheckBox(tx.cargarArchivo, checkBoxStyle);
			cb_leerPoblacion.getCells().get(0).size(15, 15);
			cb_leerPoblacion.setChecked(false);
			cb_leerPoblacion.setPosition(0 + orX, 380 + orY);
			// recuado mutar genes
			b_mutar_todo = new TextButton(tx.todo, estilo);
			b_mutar_todo.setWidth(50);
			b_mutar_todo.setHeight(20);
			b_mutar_todo.setPosition(260 + orX, 355 + orY);

			b_mutar_nada = new TextButton(tx.nada, estilo);
			b_mutar_nada.setWidth(50);
			b_mutar_nada.setHeight(20);
			b_mutar_nada.setPosition(320 + orX, 355 + orY);

			cb_mutar = new CheckBox(tx.color, checkBoxStyle);
			cb_mutar.getCells().get(0).size(20, 20);
			cb_mutar.setChecked(true);
			cb_mutar.setPosition(20 + orX, 305 + orY);

			cb_mutarSize = new CheckBox(tx.tamano, checkBoxStyle);
			cb_mutarSize.getCells().get(0).size(20, 20);
			cb_mutarSize.setChecked(true);
			cb_mutarSize.setPosition(20 + orX, 280 + orY);

			cb_mutarSpeed = new CheckBox(tx.velocidad, checkBoxStyle);
			cb_mutarSpeed.getCells().get(0).size(20, 20);
			cb_mutarSpeed.setChecked(true);
			cb_mutarSpeed.setPosition(20 + orX, 255 + orY);

			cb_mutarSentir = new CheckBox(tx.sentidos, checkBoxStyle);
			cb_mutarSentir.getCells().get(0).size(20, 20);
			cb_mutarSentir.setChecked(true);
			cb_mutarSentir.setPosition(20 + orX, 230 + orY);

			cb_mutarCazar = new CheckBox(tx.buscarComida, checkBoxStyle);
			cb_mutarCazar.getCells().get(0).size(20, 20);
			cb_mutarCazar.setChecked(true);
			cb_mutarCazar.setPosition(200 + orX, 305 + orY);

			cb_mutarEscapar = new CheckBox(tx.escapar, checkBoxStyle);
			cb_mutarEscapar.getCells().get(0).size(20, 20);
			cb_mutarEscapar.setChecked(true);
			cb_mutarEscapar.setPosition(200 + orX, 280 + orY);

			cb_mutarRadioCon = new CheckBox(tx.alcanceVisual, checkBoxStyle);
			cb_mutarRadioCon.getCells().get(0).size(20, 20);
			cb_mutarRadioCon.setChecked(true);
			cb_mutarRadioCon.setPosition(200 + orX, 255 + orY);

			cb_mutarPredador = new CheckBox(tx.genPredador, checkBoxStyle);
			cb_mutarPredador.getCells().get(0).size(20, 20);
			cb_mutarPredador.setChecked(true);
			cb_mutarPredador.setPosition(200 + orX, 230 + orY);

			cb_mutarTasaMut = new CheckBox("Fidelidad ADNpol", checkBoxStyle);
			cb_mutarTasaMut.getCells().get(0).size(20, 20);
			cb_mutarTasaMut.setChecked(true);
			cb_mutarTasaMut.setPosition(20 + orX, 205 + orY);

			cb_mutarLongevidad = new CheckBox(tx.longevidad, checkBoxStyle);
			cb_mutarLongevidad.getCells().get(0).size(20, 20);
			cb_mutarLongevidad.setChecked(true);
			cb_mutarLongevidad.setPosition(200 + orX, 205 + orY);

			cb_mutartolerancia = new CheckBox(tx.temOptima, checkBoxStyle);
			cb_mutartolerancia.getCells().get(0).size(20, 20);
			cb_mutartolerancia.setChecked(true);
			cb_mutartolerancia.setPosition(20 + orX, 180 + orY);

			cb_mutarResistencia = new CheckBox(tx.ResATB, checkBoxStyle);
			cb_mutarResistencia.getCells().get(0).size(20, 20);
			cb_mutarResistencia.setChecked(true);
			cb_mutarResistencia.setPosition(200 + orX, 180 + orY);

			// Recuadro seguir genes

			b_seguir_todo = new TextButton(tx.todo, estilo);
			b_seguir_todo.setWidth(50);
			b_seguir_todo.setHeight(20);
			b_seguir_todo.setPosition(260 + orX, 155 + orY);

			b_seguir_nada = new TextButton(tx.nada, estilo);
			b_seguir_nada.setWidth(50);
			b_seguir_nada.setHeight(20);
			b_seguir_nada.setPosition(320 + orX, 155 + orY);

			cb_colectarColor = new CheckBox(tx.color, checkBoxStyle);
			cb_colectarColor.getCells().get(0).size(20, 20);
			cb_colectarColor.setChecked(true);
			cb_colectarColor.setPosition(20 + orX, 105 + orY);

			cb_colectarSize = new CheckBox(tx.tamano, checkBoxStyle);
			cb_colectarSize.getCells().get(0).size(20, 20);
			cb_colectarSize.setChecked(true);
			cb_colectarSize.setPosition(20 + orX, 80 + orY);

			cb_colectarSpeed = new CheckBox(tx.velocidad, checkBoxStyle);
			cb_colectarSpeed.getCells().get(0).size(20, 20);
			cb_colectarSpeed.setChecked(false);
			cb_colectarSpeed.setPosition(20 + orX, 55 + orY);

			cb_colectarSentir = new CheckBox(tx.sentidos, checkBoxStyle);
			cb_colectarSentir.getCells().get(0).size(20, 20);
			cb_colectarSentir.setChecked(false);
			cb_colectarSentir.setPosition(20 + orX, 30 + orY);

			cb_colectarCazar = new CheckBox(tx.buscarComida, checkBoxStyle);
			cb_colectarCazar.getCells().get(0).size(20, 20);
			cb_colectarCazar.setChecked(false);
			cb_colectarCazar.setPosition(200 + orX, 105 + orY);

			cb_colectarEscapar = new CheckBox(tx.escapar, checkBoxStyle);
			cb_colectarEscapar.getCells().get(0).size(20, 20);
			cb_colectarEscapar.setChecked(false);
			cb_colectarEscapar.setPosition(200 + orX, 80 + orY);

			cb_colectarRadioCon = new CheckBox(tx.alcanceVisual, checkBoxStyle);
			cb_colectarRadioCon.getCells().get(0).size(20, 20);
			cb_colectarRadioCon.setChecked(false);
			cb_colectarRadioCon.setPosition(200 + orX, 55 + orY);

			cb_colectarPredador = new CheckBox(tx.genPredador, checkBoxStyle);
			cb_colectarPredador.getCells().get(0).size(20, 20);
			cb_colectarPredador.setChecked(false);
			cb_colectarPredador.setPosition(200 + orX, 30 + orY);

			cb_colectarTasaMut = new CheckBox(tx.fidelidadADNpol, checkBoxStyle);
			cb_colectarTasaMut.getCells().get(0).size(20, 20);
			cb_colectarTasaMut.setChecked(false);
			cb_colectarTasaMut.setPosition(20 + orX, 5 + orY);

			cb_colectarLongevidad = new CheckBox(tx.longevidad, checkBoxStyle);
			cb_colectarLongevidad.getCells().get(0).size(20, 20);
			cb_colectarLongevidad.setChecked(false);
			cb_colectarLongevidad.setPosition(200 + orX, 5 + orY);

			cb_colectarTolerancia = new CheckBox(tx.temOptima, checkBoxStyle);
			cb_colectarTolerancia.getCells().get(0).size(20, 20);
			cb_colectarTolerancia.setChecked(false);
			cb_colectarTolerancia.setPosition(20 + orX, -20 + orY);

			cb_colectarResistencia = new CheckBox(tx.ResATB, checkBoxStyle);
			cb_colectarResistencia.getCells().get(0).size(20, 20);
			cb_colectarResistencia.setChecked(false);
			cb_colectarResistencia.setPosition(200 + orX, -20 + orY);

			tf_MultiploPol = new TextField("1.0", tfs_text);
			tf_MultiploPol.setSize(30, 20);
			tf_MultiploPol.setCursorPosition(0);
			tf_MultiploPol.setPosition(30 + orX, -105 + orY);

			tf_HorizontalTransfer = new TextField("500000", tfs_text);
			tf_HorizontalTransfer.setSize(80, 20);
			tf_HorizontalTransfer.setCursorPosition(0);
			tf_HorizontalTransfer.setPosition(35 + orX, -135 + orY);

			// se agregan los listener para los botones
			stage.addActor(b_Informacion);
			stage.addActor(b_Idioma);
			stage.addActor(b_salir);
			stage.addActor(b_GuardarEn);
			stage.addActor(b_comenzar);
			stage.addActor(b_CargarP);
			stage.addActor(cb_mutar);
			stage.addActor(cb_mutarSize);
			stage.addActor(cb_mutarSpeed);
			stage.addActor(cb_mutarSentir);
			stage.addActor(cb_mutarPredador);
			stage.addActor(cb_mutarCazar);
			stage.addActor(cb_mutarEscapar);
			stage.addActor(cb_mutarRadioCon);
			stage.addActor(cb_mutarPredador);
			stage.addActor(cb_mutarLongevidad);
			stage.addActor(cb_mutarTasaMut);
			stage.addActor(cb_mutartolerancia);
			stage.addActor(cb_mutarResistencia);

			stage.addActor(cb_colectarColor);
			stage.addActor(cb_colectarSize);
			stage.addActor(cb_colectarSpeed);
			stage.addActor(cb_colectarSentir);

			stage.addActor(cb_colectarCazar);
			stage.addActor(cb_colectarEscapar);
			stage.addActor(cb_colectarRadioCon);
			stage.addActor(cb_colectarPredador);
			stage.addActor(cb_colectarTasaMut);
			stage.addActor(cb_colectarLongevidad);
			stage.addActor(cb_colectarTolerancia);
			stage.addActor(cb_colectarResistencia);

			stage.addActor(cb_leerPoblacion);
			stage.addActor(cb_moverMasa);
			stage.addActor(cb_Frontera);
			stage.addActor(text);
			stage.addActor(text2);
			stage.addActor(text3);
			stage.addActor(text4);
			stage.addActor(tf_ATB);
			stage.addActor(tf_energia);
			stage.addActor(tf_energiaR);
			stage.addActor(tf_biomasa);
			stage.addActor(tf_biomasaR);
			stage.addActor(tf_Numenergia);
			stage.addActor(tf_NumenergiaR);
			stage.addActor(tf_Numbiomasa);
			stage.addActor(tf_NumbiomasaR);
			stage.addActor(tf_Cantidad);
			stage.addActor(tf_CantidadMax);
			stage.addActor(tf_Temperatura);
			stage.addActor(tf_Start1);
			stage.addActor(tf_Start2);
			stage.addActor(tf_TempFinal1);
			stage.addActor(tf_TempFinal2);
			stage.addActor(tf_DeltaTiempo1);
			stage.addActor(tf_DeltaTiempo2);
			stage.addActor(tf_MultiploPol);
			stage.addActor(tf_HorizontalTransfer);

			stage.addActor(b_mutar_todo);
			stage.addActor(b_mutar_nada);
			stage.addActor(b_seguir_todo);
			stage.addActor(b_seguir_nada);

			b_mutar_todo.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					cb_mutar.setChecked(true);
					cb_mutarSize.setChecked(true);
					cb_mutarSpeed.setChecked(true);
					cb_mutarSentir.setChecked(true);
					cb_mutarPredador.setChecked(true);
					cb_mutarCazar.setChecked(true);
					cb_mutarEscapar.setChecked(true);
					cb_mutarRadioCon.setChecked(true);
					cb_mutarLongevidad.setChecked(true);
					cb_mutarTasaMut.setChecked(true);
					cb_mutartolerancia.setChecked(true);
					cb_mutarResistencia.setChecked(true);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_mutar_nada.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					cb_mutar.setChecked(false);
					cb_mutarSize.setChecked(false);
					cb_mutarSpeed.setChecked(false);
					cb_mutarSentir.setChecked(false);
					cb_mutarPredador.setChecked(false);
					cb_mutarCazar.setChecked(false);
					cb_mutarEscapar.setChecked(false);
					cb_mutarRadioCon.setChecked(false);
					cb_mutarLongevidad.setChecked(false);
					cb_mutarTasaMut.setChecked(false);
					cb_mutartolerancia.setChecked(false);
					cb_mutarResistencia.setChecked(false);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_seguir_todo.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					cb_colectarColor.setChecked(true);
					cb_colectarSize.setChecked(true);
					cb_colectarSize.setChecked(true);
					cb_colectarSpeed.setChecked(true);
					cb_colectarSentir.setChecked(true);
					cb_colectarCazar.setChecked(true);
					cb_colectarEscapar.setChecked(true);
					cb_colectarRadioCon.setChecked(true);
					cb_colectarPredador.setChecked(true);
					cb_colectarLongevidad.setChecked(true);
					cb_colectarTasaMut.setChecked(true);
					cb_colectarTolerancia.setChecked(true);
					cb_colectarResistencia.setChecked(true);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_seguir_nada.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					cb_colectarColor.setChecked(false);
					cb_colectarSize.setChecked(false);
					cb_colectarSize.setChecked(false);
					cb_colectarSpeed.setChecked(false);
					cb_colectarSentir.setChecked(false);
					cb_colectarCazar.setChecked(false);
					cb_colectarEscapar.setChecked(false);
					cb_colectarRadioCon.setChecked(false);
					cb_colectarPredador.setChecked(false);
					cb_colectarLongevidad.setChecked(false);
					cb_colectarTasaMut.setChecked(false);
					cb_colectarTolerancia.setChecked(false);
					cb_colectarResistencia.setChecked(false);

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_Informacion.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// JFrame jf = new JFrame();
					// jf.setVisible(true);
					// jf.setLocation(500, 500);
					// jf.setAlwaysOnTop(true);

					JOptionPane.showMessageDialog(null, tx.mensajeSobre);

					// jf.dispose();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_Idioma.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					ingles = ingles * (-1);

					if (ingles == 1) {
						tx.setIngles();
					}
					if (ingles == -1) {
						tx.setEspanol();
					}

					JOptionPane.showMessageDialog(null, tx.losCambiosReiniciar);

					escribirIdioma();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

			b_GuardarEn.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// Agrega Filtro para ver tipos de archivo
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./"));

					fc.setDialogTitle(tx.agregarDespues);

					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

					int returnVal = fc.showSaveDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						ruta = fc.getCurrentDirectory().getAbsolutePath() + "/";

					}

					escribirRuta();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

				}
			});

			b_CargarP.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					// Agrega Filtro para ver tipos de archivo
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("ruta"));
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							tx.poblacion + "     (pob)", "pob");
					fc.setFileFilter(filter);

					fc.setDialogTitle(tx.directorio);

					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						poblacion = fc.getSelectedFile().getAbsolutePath();
						cb_leerPoblacion.setChecked(true);
					}

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {

				}
			});

			b_salir.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					// JFrame jf = new JFrame();
					// jf.setVisible(true);
					// jf.setLocation(500, 500);
					// jf.setAlwaysOnTop(true);

					Object[] options = { tx.si, tx.no };
					int n = JOptionPane.showOptionDialog(null, tx.deseaSalir,
							"",

							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options,
							options[1]);

					if (n == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
					// jf.dispose();
				}

			});

			b_comenzar.addListener(new InputListener() {

				private int tiempoCatastrofe;
				int tiempoATB;
				private int tiempoMaximo;
				private int numOrg;
				private int numOrgMax;
				private float Temperatura;
				private int Start1;
				private int Start2;
				private float TempFinal1;
				private float TempFinal2;
				private int DeltaTiempo1;
				private int DeltaTiempo2;
				private float eficiencia;
				private int horizontalTransferRate;

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					chequeoTexto();

					guardarMenuInicio();

					try {
						SenergiaL = Integer.parseInt(tf_energia.getText());
						QbiomasaL = Integer.parseInt(tf_biomasa.getText());
						numSenL = Integer.parseInt(tf_Numenergia.getText());
						numQenL = Integer.parseInt(tf_Numbiomasa.getText());
						SenergiaR = Integer.parseInt(tf_energiaR.getText());
						QbiomasaR = Integer.parseInt(tf_biomasaR.getText());
						numSenR = Integer.parseInt(tf_NumenergiaR.getText());
						numQenR = Integer.parseInt(tf_NumbiomasaR.getText());
						tiempoMuestreo = Integer.parseInt(text2.getText());
						tiempoCatastrofe = Integer.parseInt(text3.getText());
						tiempoATB = Integer.parseInt(tf_ATB.getText());
						tiempoMaximo = Integer.parseInt(text4.getText());
						numOrg = Integer.parseInt(tf_Cantidad.getText());
						numOrgMax = Integer.parseInt(tf_CantidadMax.getText());
						Temperatura = Float.parseFloat(tf_Temperatura.getText());
						Start1 = Integer.parseInt(tf_Start1.getText());
						Start2 = Integer.parseInt(tf_Start2.getText());
						TempFinal1 = Float.parseFloat(tf_TempFinal1.getText());
						TempFinal2 = Float.parseFloat(tf_TempFinal2.getText());
						DeltaTiempo1 = Integer.parseInt(tf_DeltaTiempo1
								.getText());
						DeltaTiempo2 = Integer.parseInt(tf_DeltaTiempo2
								.getText());
						eficiencia = Float.parseFloat(tf_MultiploPol.getText());
						horizontalTransferRate = Integer
								.parseInt(tf_HorizontalTransfer.getText());

					} catch (NumberFormatException e) {
						SenergiaL = Integer.parseInt(tf_energia.getText());
						QbiomasaL = Integer.parseInt(tf_biomasa.getText());
						numSenL = Integer.parseInt(tf_Numenergia.getText());
						numQenL = Integer.parseInt(tf_Numbiomasa.getText());
						SenergiaR = Integer.parseInt(tf_energiaR.getText());
						QbiomasaR = Integer.parseInt(tf_biomasaR.getText());
						numSenR = Integer.parseInt(tf_NumenergiaR.getText());
						numQenR = Integer.parseInt(tf_NumbiomasaR.getText());
						tiempoMuestreo = 60;
						tiempoCatastrofe = 0;
						tiempoATB = 0;
						tiempoMaximo = 0;
						numOrg = 1;
						numOrgMax = 1000;
						Temperatura = 0;
						Start1 = 0;
						Start2 = 0;
						TempFinal1 = 0;
						TempFinal2 = 0;
						DeltaTiempo1 = 0;
						DeltaTiempo2 = 0;
						eficiencia = 1;
						horizontalTransferRate = 5000000;

						e.printStackTrace();
					}

					m = new Mundo(ev, ruta, text.getText(), poblacion, numOrg,
							numSenL, numQenL, SenergiaL, QbiomasaL, numSenR,
							numQenR, SenergiaR, QbiomasaR, cb_leerPoblacion
									.isChecked(), cb_moverMasa.isChecked(),
							cb_Frontera.isChecked(), genesPedidos().toString(),
							ingles);

					m.tiempoMuestreo = tiempoMuestreo;
					m.tiempoCatastrofe = tiempoCatastrofe;
					m.tiempoATB = tiempoATB;
					m.tiempoMaximo = tiempoMaximo;
					m.maximo = numOrgMax;
					m.temperatura = Temperatura;
					m.minStar1 = Start1;
					m.minStar2 = Start2;
					m.deltaTime1 = DeltaTiempo1;
					m.deltaTime2 = DeltaTiempo2;
					m.TempFinal1 = TempFinal1;
					m.TempFinal2 = TempFinal2;
					m.eficiencia = eficiencia;
					m.horizontalTransferRate = horizontalTransferRate;

					if (DeltaTiempo1 != 0) {
						m.tempXSecond1 = (TempFinal1 - Temperatura)
								/ (DeltaTiempo1 * 60);
					}

					if (DeltaTiempo2 != 0) {
						m.tempXSecond2 = (TempFinal2 - TempFinal1)
								/ (DeltaTiempo2 * 60);
					}

					m.colectColor = cb_colectarColor.isChecked();
					m.colectarancho = cb_colectarSize.isChecked();
					m.colectaralto = cb_colectarSize.isChecked();
					m.colectSpeed = cb_colectarSpeed.isChecked();
					m.colectSentir = cb_colectarSentir.isChecked();
					m.colectCazar = cb_colectarCazar.isChecked();
					m.colectEscapar = cb_colectarEscapar.isChecked();
					m.colectRadioconsiente = cb_colectarRadioCon.isChecked();
					m.colectPredador = cb_colectarPredador.isChecked();
					m.colectarLongevidad = cb_colectarLongevidad.isChecked();
					m.colectarTasaMut = cb_colectarTasaMut.isChecked();
					m.colectarTemp = cb_colectarTolerancia.isChecked();
					m.colectarResistencia = cb_colectarResistencia.isChecked();

					m.mutarColor = cb_mutar.isChecked();
					m.mutarSize = cb_mutarSize.isChecked();
					m.mutarSpeed = cb_mutarSpeed.isChecked();
					m.mutarSentir = cb_mutarSentir.isChecked();
					m.mutarPredador = cb_mutarPredador.isChecked();
					m.mutarCazar = cb_mutarCazar.isChecked();
					m.mutarEscapar = cb_mutarEscapar.isChecked();
					m.mutarRadioconsiente = cb_mutarRadioCon.isChecked();
					m.mutarLongevidad = cb_mutarLongevidad.isChecked();
					m.mutarTasaMut = cb_mutarTasaMut.isChecked();
					m.mutarTemp = cb_mutartolerancia.isChecked();
					m.mutarResistencia = cb_mutarResistencia.isChecked();

					if (cb_leerPoblacion.isChecked() == false) {
						m.agregarPrimerosOrg(numOrg);
					}
					if (cb_leerPoblacion.isChecked() == true) {
						m.leerArchivoPoblacion();
					}

					m.setDelta();
					m.setDelta2();
					m.setDelta3();
					m.setTiempo();

					m.segundos = 0;
					m.segundos2 = 0;
					m.segundos3 = 0;
					m.segundos4 = 0;
					m.segundos5 = 0;

					m.colectorEspesiesTotales();

					m.f_genes.creararchivo(ruta + m.nombre + tx.genomasTXT);
					m.f_genes.escribirArchivo(tx.cuandoEstenPresentes
							+ genesPedidos2() + genesPedidos().toString()
							+ "\n");
					m.f_datos.creararchivo(ruta + m.nombre + tx.datosXls);
					if (m.verFrontera == false) {
						m.f_datos.escribirArchivo(tx.datosOrdenados);
					}
					if (m.verFrontera == true) {
						m.f_datos.escribirArchivo(tx.datosOrdenados2);
					}

					m.f_proteoma.creararchivo(ruta + m.nombre + tx.proteomaTXT);
					m.f_proteoma.escribirArchivo(tx.cuandoEstenPresntesProt
							+ genesPedidos().toString() + "\n");
					m.f_mutantes.creararchivo(ruta + m.nombre + tx.mutantesXLS);

					StringBuffer linea = new StringBuffer(tx.tiempo
							+ "(min:seg);" + tx.nombre + ";" + tx.cantidad + "");

					if (cb_colectarSize.isChecked()) {
						linea.append("; " + tx.alto);
					}
					if (cb_colectarSize.isChecked()) {
						linea.append("; " + tx.ancho);
					}
					if (cb_colectarSpeed.isChecked()) {
						linea.append("; " + tx.velocidad);
					}
					if (cb_colectarColor.isChecked()) {
						linea.append("; " + tx.color);
					}
					if (cb_colectarTasaMut.isChecked()) {
						linea.append("; " + tx.fidelidadADNpol);
					}
					if (cb_colectarTolerancia.isChecked()) {
						linea.append("; " + tx.temOptima);
					}
					if (cb_colectarLongevidad.isChecked()) {
						linea.append("; " + tx.longevidad);
					}
					if (cb_colectarRadioCon.isChecked()) {
						linea.append("; " + tx.alcanceVisual);
					}
					if (cb_colectarPredador.isChecked()) {
						linea.append("; " + tx.genPredador);
					}
					if (cb_colectarSentir.isChecked()) {
						linea.append("; " + tx.sentidos);
					}
					if (cb_colectarCazar.isChecked()) {
						linea.append("; " + tx.buscarComida);
					}
					if (cb_colectarEscapar.isChecked()) {
						linea.append("; " + tx.escapar);
					}
					if (cb_colectarResistencia.isChecked()) {
						linea.append("; " + tx.resistensiaATB);
					}
					linea.append(";");
					if (cb_colectarSize.isChecked() == false) {
						linea.append("; " + tx.alto);
					}
					if (cb_colectarSize.isChecked() == false) {
						linea.append("; " + tx.ancho);
					}
					if (cb_colectarSpeed.isChecked() == false) {
						linea.append("; " + tx.velocidad);
					}
					if (cb_colectarColor.isChecked() == false) {
						linea.append("; " + tx.color);
					}
					if (cb_colectarTasaMut.isChecked() == false) {
						linea.append("; " + tx.fidelidadADNpol);
					}
					if (cb_colectarTolerancia.isChecked() == false) {
						linea.append("; " + tx.temOptima);
					}
					if (cb_colectarLongevidad.isChecked() == false) {
						linea.append("; " + tx.longevidad);
					}
					if (cb_colectarRadioCon.isChecked() == false) {
						linea.append("; " + tx.alcanceVisual);
					}
					if (cb_colectarPredador.isChecked() == false) {
						linea.append("; " + tx.genPredador);
					}
					if (cb_colectarSentir.isChecked() == false) {
						linea.append("; " + tx.sentidos);
					}
					if (cb_colectarCazar.isChecked() == false) {
						linea.append("; " + tx.buscarComida);
					}
					if (cb_colectarEscapar.isChecked() == false) {
						linea.append("; " + tx.escapar);
					}
					if (cb_colectarResistencia.isChecked() == false) {
						linea.append("; " + tx.resistensiaATB);
					}

					linea.append("\n");

					m.f_mutantes.escribirArchivo(linea.toString());
					m.f_arbol.creararchivo(ruta + m.nombre + tx.arbolTXT);
					m.f_arbol.escribirArchivo(genesPedidos().toString() + "\n");

					// System.out.println(m.segundos);
					m.colectorEspesies(m.aorg, m.aEspesies);
					// if(m.verFrontera==true){
					// m.colectorEspesies(m.aorgIz,m.aEspesiesIz);
					// m.colectorEspesies(m.aorgDer,m.aEspesiesDer); }
					m.guardarDatos();
					m.archivarGenoma();
					m.archivarFenotipo();
					m.archivarProteoma();

					ev.setScreen(new Pantalla(ev, m));
					dispose();

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
				}
			});

		}

		// leerMenuIncio();
	}

	public StringBuffer genesPedidos2() {

		StringBuffer lin = new StringBuffer();

		lin.append(tx.color + ">");
		lin.append(tx.ancho + ">");
		lin.append(tx.alto + ">");
		lin.append(tx.velocidad + ">");
		lin.append(tx.sentidos + ">");
		lin.append(tx.genPredador + ">");
		lin.append(tx.fidelidadADNpol + ">");
		lin.append(tx.buscarComida + ">");
		lin.append(tx.escapar + ">");
		lin.append(tx.alcanceVisual + ">");
		lin.append(tx.longevidad + ">");
		lin.append(tx.temOptima + ">");
		lin.append(tx.ResATB + "");

		lin.append("\n");
		return lin;
	}

	public StringBuffer genesPedidos() {

		StringBuffer lin = new StringBuffer();

		lin.append(tx.color + ";");
		lin.append(tx.ancho + ";");
		lin.append(tx.alto + ";");
		lin.append(tx.velocidad + ";");
		lin.append(tx.sentidos + ";");
		lin.append(tx.genPredador + ";");
		lin.append(tx.fidelidadADNpol + ";");
		lin.append(tx.buscarComida + ";");
		lin.append(tx.escapar + ";");
		lin.append(tx.alcanceVisual + ";");
		lin.append(tx.longevidad + ";");
		lin.append(tx.temOptima + ";");
		lin.append(tx.ResATB + "");

		return lin;
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
		ta_atlas.dispose();
		ta_atlas2.dispose();

	}

	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void read(Json json, JsonValue jsonValue) {

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

}
