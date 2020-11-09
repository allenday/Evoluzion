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

public class StartMenu implements com.badlogic.gdx.Screen, Serializable {

	Evoluzion ev;
	Text tx;

	World m;
	StartMenu mi;
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

	Archive a_ruta;
	Archive a_idioma;

	Sprite titulo;
	Texture tx_titulo;

	int verPanel = 1;
	int pausaGame = 1;

	float ancho = Gdx.graphics.getWidth();
	float alto = Gdx.graphics.getHeight();
	float orX = 0;
	float orY = 210;

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

	boolean modificado = false;
	boolean modificado2 = false;
	boolean modificado3 = false;
	boolean modificado4 = false;

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
	private TextField tf_gap1, tf_gap2, tf_gap3, tf_gap4, tf_gap6, tf_gap7,
			tf_gap8, tf_gap9, tf_gap10, tf_gap11;
	private TextField tf_Cantidad2;
	private TextField tf_Cantidad3;
	private TextField tf_Cantidad4;
	private TextButton b_load;
	private TextButton b_load2;
	private TextButton b_load3;
	private TextButton b_load4;

	// parametros originales de los organismos a cargar

	int numOrg = 0;
	boolean sexoMacho = true;
	int sexGen = 0;
	int colorGen = 1;
	int anchoGen = 1;
	int altoGen = 1;
	int speedGen = 2;
	int optTempGen = 1;
	int tasMutGen = 1;
	int radioGen = 0;
	int longeGen = 1;
	int predGen = 0;
	int senseGen = 1;
	int escapeGen = 0;
	int cazarGen = 0;
	int resistGen = 0;
	int ferGen = 2;
	int parNoGen = 0;
	int relleno = 0;

	int numOrg2 = 0;
	boolean sexoMacho2 = false;
	int sexGen2 = 1;
	int colorGen2 = 1;
	int anchoGen2 = 1;
	int altoGen2 = 1;
	int speedGen2 = 2;
	int optTempGen2 = 1;
	int tasMutGen2 = 1;
	int radioGen2 = 0;
	int longeGen2 = 1;
	int predGen2 = 0;
	int senseGen2 = 1;
	int escapeGen2 = 0;
	int cazarGen2 = 0;
	int resistGen2 = 0;
	int ferGen2 = 2;
	int parNoGen2 = 0;
	int relleno2 = 0;

	int numOrg3 = 0;
	boolean sexoMacho3 = true;
	int sexGen3 = 0;
	int colorGen3 = 1;
	int anchoGen3 = 1;
	int altoGen3 = 1;
	int speedGen3 = 2;
	int optTempGen3 = 1;
	int tasMutGen3 = 1;
	int radioGen3 = 0;
	int longeGen3 = 1;
	int predGen3 = 0;
	int senseGen3 = 1;
	int escapeGen3 = 0;
	int cazarGen3 = 0;
	int resistGen3 = 0;
	int ferGen3 = 2;
	int parNoGen3 = 0;
	int relleno3 = 0;

	int numOrg4 = 0;
	boolean sexoMacho4 = false;
	int sexGen4 = 1;
	int colorGen4 = 1;
	int anchoGen4 = 1;
	int altoGen4 = 1;
	int speedGen4 = 2;
	int optTempGen4 = 1;
	int tasMutGen4 = 1;
	int radioGen4 = 0;
	int longeGen4 = 1;
	int predGen4 = 0;
	int senseGen4 = 1;
	int escapeGen4 = 0;
	int cazarGen4 = 0;
	int resistGen4 = 0;
	int ferGen4 = 2;
	int parNoGen4 = 0;
	int relleno4 = 0;

	StringBuffer genero, predador;

	ClonesWindow ventana, ventana2, ventana3, ventana4;
	private TextButton b_restaurar;
	private TextField tf_energiaR;
	private TextField tf_biomasaR;
	private TextField tf_NumenergiaR;
	private TextField tf_NumbiomasaR;
	private CheckBox cb_Frontera;
	private CheckBox cb_discriminar;
	private CheckBox cb_mutarferomona;
	private CheckBox cb_colectarferomona;
	private CheckBox cb_Partenogenesis;
	private CheckBox cb_colectarPartenogenesis;
	private TextField tf_gap92;

	public StartMenu(Evoluzion ev) {

		this.ev = ev;
		tx = new Text();
		// m= new Mundo(ev,"",0,50,50,20,20,false);

		mi = this;

		camara = new OrthographicCamera();
		camara.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		f2 = new File("evo_star2.conf");

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

		genero = new StringBuffer();
		predador = new StringBuffer();

		fu_fuente = new BitmapFont();

		rectangulo = new ShapeRenderer();

		a_ruta = new Archive();
		a_idioma = new Archive();

		leerRuta();
		leerIdioma();

		if (ingles == 1) {
			tx.setEnglish();
		}
		if (ingles == -1) {
			tx.setSpanish();
		}

		controles();
		// System.out.println("idioma "+ingles);

		leerMenuIncio();

		ventana = new ClonesWindow(this, 0, "R1", colorGen, anchoGen, altoGen,
				senseGen, optTempGen, predGen, speedGen, cazarGen, escapeGen,
				radioGen, ferGen, parNoGen, tasMutGen, longeGen, resistGen,
				sexGen);
		ventana2 = new ClonesWindow(this, 1, "R2", colorGen2, anchoGen2,
				altoGen2, senseGen2, optTempGen2, predGen2, speedGen2,
				cazarGen2, escapeGen2, radioGen2, ferGen2, parNoGen2,
				tasMutGen2, longeGen2, resistGen2, sexGen2);
		ventana3 = new ClonesWindow(this, 2, "L3", colorGen3, anchoGen3,
				altoGen3, senseGen3, optTempGen3, predGen3, speedGen3,
				cazarGen3, escapeGen3, radioGen3, ferGen3, parNoGen3,
				tasMutGen3, longeGen3, resistGen3, sexGen3);
		ventana4 = new ClonesWindow(this, 3, "L4", colorGen4, anchoGen4,
				altoGen4, senseGen4, optTempGen4, predGen4, speedGen4,
				cazarGen4, escapeGen4, radioGen4, ferGen3, parNoGen4,
				tasMutGen4, longeGen4, resistGen4, sexGen4);

	}

	// leer los archivos de configuracion

	public void escribirIdioma() {

		a_idioma.createArchive("evo_idioma.tmp");

		if (ingles == 1) {
			a_idioma.writeArchive("eng");
		}
		if (ingles == -1) {
			a_idioma.writeArchive("spa");
		}
		a_idioma.cerrarArchivo();
	}

	public void leerIdioma() {

		try {
			FileReader fr = new FileReader("evo_idioma.tmp");
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				String str = linea;

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

		a_ruta.createArchive("evo_ruta.tmp");
		a_ruta.writeArchive(ruta);
		a_ruta.cerrarArchivo();

	}

	public String leerRuta() {

		try {
			FileReader fr2 = new FileReader("evo_ruta.tmp");
			BufferedReader br = new BufferedReader(fr2);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				ruta = linea;

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
			oos2.writeObject(tf_Cantidad2.getText());
			oos2.writeObject(tf_Cantidad3.getText());
			oos2.writeObject(tf_Cantidad4.getText());
			oos2.writeObject(tf_HorizontalTransfer.getText());

			oos2.writeObject(tf_CantidadMax.getText());
			oos2.writeObject(cb_moverMasa.isChecked());
			oos2.writeObject(cb_Frontera.isChecked());
			oos2.writeObject(cb_discriminar.isChecked());

			oos2.writeObject(tf_energia.getText());
			oos2.writeObject(tf_biomasa.getText());
			oos2.writeObject(tf_Numbiomasa.getText());
			oos2.writeObject(tf_Numenergia.getText());
			oos2.writeObject(tf_energiaR.getText());
			oos2.writeObject(tf_biomasaR.getText());
			oos2.writeObject(tf_NumbiomasaR.getText());
			oos2.writeObject(tf_NumenergiaR.getText());

			oos2.writeObject(tf_gap1.getText());
			oos2.writeObject(tf_gap2.getText());
			oos2.writeObject(tf_gap3.getText());
			oos2.writeObject(tf_gap4.getText());

			oos2.writeObject(tf_gap6.getText());
			oos2.writeObject(tf_gap7.getText());
			oos2.writeObject(tf_gap8.getText());
			oos2.writeObject(tf_gap9.getText());

			oos2.writeObject(tf_gap10.getText());
			oos2.writeObject(tf_gap11.getText());

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
			oos2.writeObject(cb_mutarferomona.isChecked());
			oos2.writeObject(cb_Partenogenesis.isChecked());

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
			oos2.writeObject(cb_colectarferomona.isChecked());
			oos2.writeObject(cb_colectarPartenogenesis.isChecked());

			oos2.close();
			fos2.close();

		}

		catch (Exception ex) {

			JOptionPane.showMessageDialog(null, tx.errorEscribir);
			ex.printStackTrace();

		}

	}

	public void leerMenuIncio() {

		try {
			FileInputStream fis = new FileInputStream(f2);
			ObjectInputStream ois = new ObjectInputStream(fis);

			text2.setText((String) ois.readObject());
			text3.setText((String) ois.readObject());
			text4.setText((String) ois.readObject());
			tf_ATB.setText((String) ois.readObject());

			tf_Cantidad.setText((String) ois.readObject());
			tf_Cantidad2.setText((String) ois.readObject());
			tf_Cantidad3.setText((String) ois.readObject());
			tf_Cantidad4.setText((String) ois.readObject());
			tf_HorizontalTransfer.setText((String) ois.readObject());

			tf_CantidadMax.setText((String) ois.readObject());
			cb_moverMasa.setChecked((Boolean) ois.readObject());
			cb_Frontera.setChecked((Boolean) ois.readObject());
			cb_discriminar.setChecked((Boolean) ois.readObject());

			tf_energia.setText((String) ois.readObject());
			tf_biomasa.setText((String) ois.readObject());
			tf_Numbiomasa.setText((String) ois.readObject());
			tf_Numenergia.setText((String) ois.readObject());
			tf_energiaR.setText((String) ois.readObject());
			tf_biomasaR.setText((String) ois.readObject());
			tf_NumbiomasaR.setText((String) ois.readObject());
			tf_NumenergiaR.setText((String) ois.readObject());

			tf_gap1.setText((String) ois.readObject());
			tf_gap2.setText((String) ois.readObject());
			tf_gap3.setText((String) ois.readObject());
			tf_gap4.setText((String) ois.readObject());

			tf_gap6.setText((String) ois.readObject());
			tf_gap7.setText((String) ois.readObject());
			tf_gap8.setText((String) ois.readObject());
			tf_gap9.setText((String) ois.readObject());

			tf_gap10.setText((String) ois.readObject());
			tf_gap11.setText((String) ois.readObject());

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
			cb_mutarferomona.setChecked((Boolean) ois.readObject());
			cb_Partenogenesis.setChecked((Boolean) ois.readObject());

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
			cb_colectarferomona.setChecked((Boolean) ois.readObject());
			cb_colectarPartenogenesis.setChecked((Boolean) ois.readObject());

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
			tf_biomasa.setText("10");
			tf_Numbiomasa.setText("1500");
			tf_Numenergia.setText("1500");
			tf_energiaR.setText("20");
			tf_biomasaR.setText("10");
			tf_NumbiomasaR.setText("1500");
			tf_NumenergiaR.setText("1500");

			tf_Cantidad.setText("50");
			tf_Cantidad2.setText("0");
			tf_Cantidad3.setText("0");
			tf_Cantidad4.setText("50");
			tf_HorizontalTransfer.setText("0");
			tf_CantidadMax.setText("1000");

			tf_gap1.setText("0");
			tf_gap2.setText("0");
			tf_gap3.setText("0");
			tf_gap4.setText("0");

			tf_gap6.setText("0");
			tf_gap7.setText("0");
			tf_gap8.setText("0");
			tf_gap9.setText("0");

			tf_gap10.setText("0");
			tf_gap11.setText("0");

			cb_mutar.setChecked(false);
			cb_mutarSize.setChecked(false);
			cb_mutarSpeed.setChecked(false);
			cb_mutarSentir.setChecked(false);
			cb_mutarPredador.setChecked(false);
			cb_mutarCazar.setChecked(false);
			cb_mutarEscapar.setChecked(false);
			cb_mutarRadioCon.setChecked(false);
			cb_mutarTasaMut.setChecked(false);
			cb_mutarLongevidad.setChecked(false);
			cb_mutartolerancia.setChecked(false);
			cb_mutarResistencia.setChecked(false);
			cb_mutarferomona.setChecked(false);
			cb_Partenogenesis.setChecked(false);

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
			cb_colectarferomona.setChecked(false);
			cb_colectarPartenogenesis.setChecked(false);

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

		fuente.draw(batch, tx.simuladorDigital, 400 + orX, 690);

		fuente.draw(batch, tx.parametrosEnergiaMasa, 405 + orX, 390 + orY);
		fuente.draw(batch, tx.soloNenteros, 430 + orX, 370 + orY);

		fuente.draw(batch, tx.otrosParamentros, 750 + orX, 390 + orY);
		fuente.draw(batch, tx.soloNenteros, 730 + orX, 370 + orY);

		fuente.draw(batch, tx.directorioDeTrabajo + ruta, 20 + orX, 440 + orY);
		fuente.draw(batch, "" + poblacion, 180 + orX, 417 + orY);

		fuente.draw(batch, tx.panelIz + "---|---" + tx.panelDer, 400 + orX,
				348 + orY);

		fuente.draw(batch, tx.valorEnergia, 470 + orX, 325 + orY);
		fuente.draw(batch, tx.valorBiomasa, 470 + orX, 295 + orY);
		fuente.draw(batch, tx.cantidadEnergia, 460 + orX, 265 + orY);
		fuente.draw(batch, tx.cantidadBiomasa, 460 + orX, 235 + orY);

		fuente.draw(batch, tx.tiempoEntreMuestras, 700 + orX, 335 + orY);
		fuente.draw(batch, tx.tiempoEntreCatastrofes, 700 + orX, 307 + orY);
		fuente.draw(batch, tx.tiempoATB, 700 + orX, 275 + orY);
		fuente.draw(batch, tx.tiempoPartida, 700 + orX, 249 + orY);

		fuente.draw(batch, tx.cepa + " (R1)" + " " + texGenero(sexoMacho) + " "
				+ texPredador(predGen), 700 + orX, 209 + orY);

		fuente.draw(batch, texModificado(modificado), 950 + orX, 209 + orY);

		fuente.draw(batch, tx.cepa + " (R2)" + " " + texGenero(sexoMacho2)
				+ " " + texPredador(predGen2), 700 + orX, 177 + orY);

		fuente.draw(batch, texModificado(modificado2), 950 + orX, 177 + orY);

		fuente.draw(batch, tx.cepa + " (L3)" + " " + texGenero(sexoMacho3)
				+ " " + texPredador(predGen3), 700 + orX, 145 + orY);

		fuente.draw(batch, texModificado(modificado3), 950 + orX, 145 + orY);

		fuente.draw(batch, tx.cepa + " (L4)" + " " + texGenero(sexoMacho4)
				+ " " + texPredador(predGen4), 700 + orX, 113 + orY);

		fuente.draw(batch, texModificado(modificado4), 950 + orX, 113 + orY);

		fuente.draw(batch, tx.numeroMaximoOrg, 700 + orX, 45 + orY);
		fuente.draw(batch, tx.temperaturaInicial, 700 + orX, 10 + orY);

		fuente.draw(batch, tx.modificacionMambiente, 700 + orX, -20 + orY);

		fuente.draw(batch, tx.comenzar2, 670 + orX, -40 + orY);
		fuente.draw(batch, tx.UnominGradosMin, 660 + orX, -65 + orY);
		fuente.draw(batch, tx.DosminGradosMin, 660 + orX, -105 + orY);

		fuente.draw(batch, tx.funcionInactivada, 660 + orX, -122 + orY);

		fuente.draw(batch, tx.marcarGenesMutaran, 10 + orX, 370 + orY);
		fuente.draw(batch, tx.marcarMutAnalizar, 10 + orX, 190 + orY);

		fuente.draw(batch, tx.mapaGenomico, 80 + orX, 0 + orY);

		fuente.draw(batch, tx.multiploADNpol, 720 + orX, -150 + orY);

		// fuente.draw(batch,"1/", 480+orX, -180+orY);
		// fuente.draw(batch,tx.horizontalTransferRate, 600+orX, -180+orY);
		// fuente.draw(batch,"* Múltiplo que afecta la eficiencia de la ADN polimerasa",
		// 20+orX, -40+orY);

		// mapa genomico

		fuente.draw(batch, "C1) 5`->" + tx.ResATB, 0 + orX, -30 + orY);
		fuente.draw(batch, tx.size, 150 + orX, -30 + orY);
		fuente.draw(batch, tx.senses, 265 + orX, -30 + orY);
		fuente.draw(batch, tx.optimalTemp, 380 + orX, -30 + orY);
		fuente.draw(batch, tx.genPredador + "-->3`", 515 + orX, -30 + orY);

		fuente.draw(batch, "C2) 5`->" + tx.velocidad, 0 + orX, -75 + orY);
		fuente.draw(batch, tx.searchFood, 130 + orX, -75 + orY);
		fuente.draw(batch, tx.escape, 230 + orX, -75 + orY);
		fuente.draw(batch, tx.alcanceVisual, 340 + orX, -75 + orY);
		fuente.draw(batch, tx.fer, 462 + orX, -75 + orY);
		fuente.draw(batch, tx.partenogen + "-->3`", 525 + orX, -75 + orY);

		fuente.draw(batch, "C3) 5`->" + tx.fidelidadADNpol, 0 + orX, -115 + orY);
		fuente.draw(batch, tx.longevidad, 200 + orX, -115 + orY);
		fuente.draw(batch, tx.color + "-->3`", 355 + orX, -115 + orY);

		fuente.draw(batch, tx.cadaCamporepresentaa, 22 + orX, -150 + orY);
		fuente.draw(batch, tx.expresadosEnUnidades, 22 + orX, -170 + orY);
		fuente.draw(batch, tx.genesLigados, 22 + orX, -190 + orY);

		batch.end();

		rectangulo.begin(ShapeType.Filled);
		rectangulo.setColor(Color.WHITE);
		rectangulo.rect(20 + orX, 200 + orY, 350, 150);
		rectangulo.rect(650 + orX, -170 + orY, 365, 520);
		rectangulo.rect(380 + orX, 150 + orY, 260, 200);// panel de energia
		rectangulo.rect(20 + orX, 20 + orY, 350, 150);

		rectangulo.rect(25 + orX, -55 + orY, 615, 35);
		rectangulo.rect(25 + orX, -97 + orY, 615, 35);
		rectangulo.rect(25 + orX, -140 + orY, 460, 35);

		rectangulo.end();

	}

	public String texModificado(boolean mod) {
		String text = "";

		if (mod == true) {
			text = tx.modificado;
		}
		if (mod == false) {
			text = "";
		}

		return text;

	}

	public String texGenero(boolean macho) {

		if (macho == true) {
			genero.replace(0, genero.length(), "(" + tx.male + ")");
		}
		if (macho == false) {
			genero.replace(0, genero.length(), "(" + tx.female + ")");
		}

		return genero.toString();

	}

	public String texPredador(int pred) {

		if (pred == 0) {
			predador.replace(0, predador.length(), "");
		}
		if (pred == 1) {
			predador.replace(0, predador.length(), "(" + tx.predador + ")");
		}

		return predador.toString();

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
		if (tf_Cantidad2.getText().equals(nulo)) {
			tf_Cantidad2.setText(cero);
		}
		if (tf_Cantidad3.getText().equals(nulo)) {
			tf_Cantidad3.setText(cero);
		}
		if (tf_Cantidad4.getText().equals(nulo)) {
			tf_Cantidad4.setText(cero);
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

		if (tf_gap1.getText().equals(nulo)) {
			tf_gap1.setText(cero);
		}
		if (tf_gap2.getText().equals(nulo)) {
			tf_gap2.setText(cero);
		}
		if (tf_gap3.getText().equals(nulo)) {
			tf_gap3.setText(cero);
		}
		if (tf_gap4.getText().equals(nulo)) {
			tf_gap4.setText(cero);
		}

		if (tf_gap6.getText().equals(nulo)) {
			tf_gap6.setText(cero);
		}
		if (tf_gap7.getText().equals(nulo)) {
			tf_gap7.setText(cero);
		}
		if (tf_gap8.getText().equals(nulo)) {
			tf_gap8.setText(cero);
		}
		if (tf_gap9.getText().equals(nulo)) {
			tf_gap9.setText(cero);
		}
		if (tf_gap92.getText().equals(nulo)) {
			tf_gap92.setText(cero);
		}
		if (tf_gap10.getText().equals(nulo)) {
			tf_gap10.setText(cero);
		}
		if (tf_gap11.getText().equals(nulo)) {
			tf_gap11.setText(cero);
		}

	}

	public void controles() {

		if (stage == null) {
			// stage maneja elementos que reciben entradas como botones o
			// eventos
			// en este caso se us apara los botones

			ScreenViewport viewport = new ScreenViewport();
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
			text2.setPosition(660 + orX, 320 + orY);

			text3 = new TextField("" + 0, tfs_text);
			text3.setSize(38, 20);
			text3.setCursorPosition(0);
			text3.setPosition(660 + orX, 290 + orY);

			tf_ATB = new TextField("" + 0, tfs_text);
			tf_ATB.setSize(38, 20);
			tf_ATB.setCursorPosition(0);
			tf_ATB.setPosition(660 + orX, 260 + orY);

			text4 = new TextField("" + 0, tfs_text);
			text4.setSize(38, 20);
			text4.setCursorPosition(0);
			text4.setPosition(660 + orX, 230 + orY);

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

			cb_discriminar = new CheckBox(tx.verMachoHembra, checkBoxStyle);
			cb_discriminar.getCells().get(0).size(15, 15);
			cb_discriminar.setChecked(true);
			cb_discriminar.setPosition(510 + orX, 160 + orY);

			tf_Cantidad = new TextField("1", tfs_text);
			tf_Cantidad.setSize(38, 20);
			tf_Cantidad.setCursorPosition(0);
			tf_Cantidad.setPosition(660 + orX, 190 + orY);

			b_load = new TextButton(tx.modificar, estilo);
			b_load.setWidth(70);
			b_load.setHeight(20);
			b_load.setX(895 + orX);
			b_load.setY(190 + orY);

			tf_Cantidad2 = new TextField("0", tfs_text);
			tf_Cantidad2.setSize(38, 20);
			tf_Cantidad2.setCursorPosition(0);
			tf_Cantidad2.setPosition(660 + orX, 160 + orY);

			b_load2 = new TextButton(tx.modificar, estilo);
			b_load2.setWidth(70);
			b_load2.setHeight(20);
			b_load2.setX(895 + orX);
			b_load2.setY(160 + orY);

			tf_Cantidad3 = new TextField("0", tfs_text);
			tf_Cantidad3.setSize(38, 20);
			tf_Cantidad3.setCursorPosition(0);
			tf_Cantidad3.setPosition(660 + orX, 130 + orY);

			b_load3 = new TextButton(tx.modificar, estilo);
			b_load3.setWidth(70);
			b_load3.setHeight(20);
			b_load3.setX(895 + orX);
			b_load3.setY(130 + orY);

			tf_Cantidad4 = new TextField("0", tfs_text);
			tf_Cantidad4.setSize(38, 20);
			tf_Cantidad4.setCursorPosition(0);
			tf_Cantidad4.setPosition(660 + orX, 100 + orY);

			b_load4 = new TextButton(tx.modificar, estilo);
			b_load4.setWidth(70);
			b_load4.setHeight(20);
			b_load4.setX(895 + orX);
			b_load4.setY(97 + orY);

			b_restaurar = new TextButton(tx.restaurar + " " + tx.cepa, estilo);
			b_restaurar.setWidth(120);
			b_restaurar.setHeight(25);
			b_restaurar.setX(750 + orX);
			b_restaurar.setY(60 + orY);

			tf_CantidadMax = new TextField("1000", tfs_text);
			tf_CantidadMax.setSize(38, 20);
			tf_CantidadMax.setCursorPosition(0);
			tf_CantidadMax.setPosition(660 + orX, 30 + orY);

			tf_Temperatura = new TextField("25", tfs_text);
			tf_Temperatura.setSize(38, 20);
			tf_Temperatura.setCursorPosition(0);
			tf_Temperatura.setPosition(660 + orX, -5 + orY);

			tf_Start1 = new TextField("0", tfs_text);
			tf_Start1.setSize(60, 20);
			tf_Start1.setCursorPosition(0);
			tf_Start1.setPosition(675 + orX, -80 + orY);

			tf_TempFinal1 = new TextField("0", tfs_text);
			tf_TempFinal1.setSize(60, 20);
			tf_TempFinal1.setCursorPosition(0);
			tf_TempFinal1.setPosition(780 + orX, -120 + orY);

			tf_DeltaTiempo1 = new TextField("0", tfs_text);
			tf_DeltaTiempo1.setSize(60, 20);
			tf_DeltaTiempo1.setCursorPosition(0);
			tf_DeltaTiempo1.setPosition(895 + orX, -120 + orY);

			tf_Start2 = new TextField("0", tfs_text);
			tf_Start2.setSize(60, 20);
			tf_Start2.setCursorPosition(0);
			tf_Start2.setPosition(675 + orX, -120 + orY);

			tf_TempFinal2 = new TextField("0", tfs_text);
			tf_TempFinal2.setSize(60, 20);
			tf_TempFinal2.setCursorPosition(0);
			tf_TempFinal2.setPosition(780 + orX, -80 + orY);

			tf_DeltaTiempo2 = new TextField("0", tfs_text);
			tf_DeltaTiempo2.setSize(60, 20);
			tf_DeltaTiempo2.setCursorPosition(0);
			tf_DeltaTiempo2.setPosition(895 + orX, -80 + orY);

			cb_leerPoblacion = new CheckBox(tx.cargarArchivo, checkBoxStyle);
			cb_leerPoblacion.getCells().get(0).size(16, 16);
			cb_leerPoblacion.setChecked(false);
			cb_leerPoblacion.setPosition(0 + orX, 380 + orY);

			// recuadro mutar genes
			b_mutar_todo = new TextButton(tx.todo, estilo);
			b_mutar_todo.setWidth(50);
			b_mutar_todo.setHeight(20);
			b_mutar_todo.setPosition(260 + orX, 355 + orY);

			b_mutar_nada = new TextButton(tx.nada2, estilo);
			b_mutar_nada.setWidth(50);
			b_mutar_nada.setHeight(20);
			b_mutar_nada.setPosition(320 + orX, 355 + orY);

			cb_mutar = new CheckBox(tx.color, checkBoxStyle);
			cb_mutar.getCells().get(0).size(16, 16);
			cb_mutar.setChecked(true);
			cb_mutar.setPosition(20 + orX, 306 + orY);

			cb_mutarSize = new CheckBox(tx.size, checkBoxStyle);
			cb_mutarSize.getCells().get(0).size(16, 16);
			cb_mutarSize.setChecked(true);
			cb_mutarSize.setPosition(20 + orX, 285 + orY);

			cb_mutarSpeed = new CheckBox(tx.alas, checkBoxStyle);
			cb_mutarSpeed.getCells().get(0).size(16, 16);
			cb_mutarSpeed.setChecked(true);
			cb_mutarSpeed.setPosition(20 + orX, 264 + orY);

			cb_mutarSentir = new CheckBox(tx.senses, checkBoxStyle);
			cb_mutarSentir.getCells().get(0).size(16, 16);
			cb_mutarSentir.setChecked(true);
			cb_mutarSentir.setPosition(20 + orX, 243 + orY);

			cb_mutarTasaMut = new CheckBox(tx.fidelidadADNpol, checkBoxStyle);
			cb_mutarTasaMut.getCells().get(0).size(16, 16);
			cb_mutarTasaMut.setChecked(true);
			cb_mutarTasaMut.setPosition(20 + orX, 222 + orY);

			cb_mutartolerancia = new CheckBox(tx.optimalTemp, checkBoxStyle);
			cb_mutartolerancia.getCells().get(0).size(16, 16);
			cb_mutartolerancia.setChecked(true);
			cb_mutartolerancia.setPosition(20 + orX, 201 + orY);

			cb_mutarferomona = new CheckBox(tx.feromona, checkBoxStyle);
			cb_mutarferomona.getCells().get(0).size(16, 16);
			cb_mutarferomona.setChecked(true);
			cb_mutarferomona.setPosition(20 + orX, 180 + orY);

			cb_Partenogenesis = new CheckBox(tx.partenogenesis, checkBoxStyle);
			cb_Partenogenesis.getCells().get(0).size(16, 16);
			cb_Partenogenesis.setChecked(true);
			cb_Partenogenesis.setPosition(200 + orX, 180 + orY);

			cb_mutarCazar = new CheckBox(tx.searchFood, checkBoxStyle);
			cb_mutarCazar.getCells().get(0).size(16, 16);
			cb_mutarCazar.setChecked(true);
			cb_mutarCazar.setPosition(200 + orX, 306 + orY);

			cb_mutarEscapar = new CheckBox(tx.escape, checkBoxStyle);
			cb_mutarEscapar.getCells().get(0).size(16, 16);
			cb_mutarEscapar.setChecked(true);
			cb_mutarEscapar.setPosition(200 + orX, 285 + orY);

			cb_mutarRadioCon = new CheckBox(tx.alcanceVisual, checkBoxStyle);
			cb_mutarRadioCon.getCells().get(0).size(16, 16);
			cb_mutarRadioCon.setChecked(true);
			cb_mutarRadioCon.setPosition(200 + orX, 264 + orY);

			cb_mutarPredador = new CheckBox(tx.genPredador, checkBoxStyle);
			cb_mutarPredador.getCells().get(0).size(16, 16);
			cb_mutarPredador.setChecked(true);
			cb_mutarPredador.setPosition(200 + orX, 243 + orY);

			cb_mutarLongevidad = new CheckBox(tx.longevidad, checkBoxStyle);
			cb_mutarLongevidad.getCells().get(0).size(16, 16);
			cb_mutarLongevidad.setChecked(true);
			cb_mutarLongevidad.setPosition(200 + orX, 222 + orY);

			cb_mutarResistencia = new CheckBox(tx.ResATB, checkBoxStyle);
			cb_mutarResistencia.getCells().get(0).size(16, 16);
			cb_mutarResistencia.setChecked(true);
			cb_mutarResistencia.setPosition(200 + orX, 201 + orY);

			// Recuadro seguir genes

			b_seguir_todo = new TextButton(tx.todo, estilo);
			b_seguir_todo.setWidth(50);
			b_seguir_todo.setHeight(20);
			b_seguir_todo.setPosition(260 + orX, 175 + orY);

			b_seguir_nada = new TextButton(tx.nada2, estilo);
			b_seguir_nada.setWidth(50);
			b_seguir_nada.setHeight(20);
			b_seguir_nada.setPosition(320 + orX, 175 + orY);

			cb_colectarColor = new CheckBox(tx.color, checkBoxStyle);
			cb_colectarColor.getCells().get(0).size(16, 16);
			cb_colectarColor.setChecked(true);
			cb_colectarColor.setPosition(20 + orX, 125 + orY);

			cb_colectarSize = new CheckBox(tx.size, checkBoxStyle);
			cb_colectarSize.getCells().get(0).size(16, 16);
			cb_colectarSize.setChecked(true);
			cb_colectarSize.setPosition(20 + orX, 104 + orY);

			cb_colectarSpeed = new CheckBox(tx.alas, checkBoxStyle);
			cb_colectarSpeed.getCells().get(0).size(16, 16);
			cb_colectarSpeed.setChecked(false);
			cb_colectarSpeed.setPosition(20 + orX, 83 + orY);

			cb_colectarSentir = new CheckBox(tx.senses, checkBoxStyle);
			cb_colectarSentir.getCells().get(0).size(16, 16);
			cb_colectarSentir.setChecked(false);
			cb_colectarSentir.setPosition(20 + orX, 62 + orY);

			cb_colectarCazar = new CheckBox(tx.searchFood, checkBoxStyle);
			cb_colectarCazar.getCells().get(0).size(16, 16);
			cb_colectarCazar.setChecked(false);
			cb_colectarCazar.setPosition(200 + orX, 125 + orY);

			cb_colectarEscapar = new CheckBox(tx.escape, checkBoxStyle);
			cb_colectarEscapar.getCells().get(0).size(16, 16);
			cb_colectarEscapar.setChecked(false);
			cb_colectarEscapar.setPosition(200 + orX, 104 + orY);

			cb_colectarRadioCon = new CheckBox(tx.alcanceVisual, checkBoxStyle);
			cb_colectarRadioCon.getCells().get(0).size(16, 16);
			cb_colectarRadioCon.setChecked(false);
			cb_colectarRadioCon.setPosition(200 + orX, 83 + orY);

			cb_colectarPredador = new CheckBox(tx.genPredador, checkBoxStyle);
			cb_colectarPredador.getCells().get(0).size(16, 16);
			cb_colectarPredador.setChecked(false);
			cb_colectarPredador.setPosition(200 + orX, 62 + orY);

			cb_colectarLongevidad = new CheckBox(tx.longevidad, checkBoxStyle);
			cb_colectarLongevidad.getCells().get(0).size(16, 16);
			cb_colectarLongevidad.setChecked(false);
			cb_colectarLongevidad.setPosition(200 + orX, 41 + orY);

			cb_colectarResistencia = new CheckBox(tx.ResATB, checkBoxStyle);
			cb_colectarResistencia.getCells().get(0).size(16, 16);
			cb_colectarResistencia.setChecked(false);
			cb_colectarResistencia.setPosition(200 + orX, 20 + orY);

			cb_colectarTasaMut = new CheckBox(tx.fidelidadADNpol, checkBoxStyle);
			cb_colectarTasaMut.getCells().get(0).size(16, 16);
			cb_colectarTasaMut.setChecked(false);
			cb_colectarTasaMut.setPosition(20 + orX, 41 + orY);

			cb_colectarTolerancia = new CheckBox(tx.optimalTemp, checkBoxStyle);
			cb_colectarTolerancia.getCells().get(0).size(16, 16);
			cb_colectarTolerancia.setChecked(false);
			cb_colectarTolerancia.setPosition(20 + orX, 20 + orY);

			cb_colectarferomona = new CheckBox(tx.feromona, checkBoxStyle);
			cb_colectarferomona.getCells().get(0).size(16, 16);
			cb_colectarferomona.setChecked(false);
			cb_colectarferomona.setPosition(20 + orX, -1 + orY);

			cb_colectarPartenogenesis = new CheckBox(tx.partenogenesis,
					checkBoxStyle);
			cb_colectarPartenogenesis.getCells().get(0).size(16, 16);
			cb_colectarPartenogenesis.setChecked(true);
			cb_colectarPartenogenesis.setPosition(200 + orX, -1 + orY);

			tf_MultiploPol = new TextField("1.0", tfs_text);
			tf_MultiploPol.setSize(40, 20);
			tf_MultiploPol.setCursorPosition(0);
			tf_MultiploPol.setPosition(670 + orX, -165 + orY);

			tf_HorizontalTransfer = new TextField("500000", tfs_text);
			tf_HorizontalTransfer.setSize(80, 20);
			tf_HorizontalTransfer.setCursorPosition(0);
			tf_HorizontalTransfer.setPosition(500 + orX, -200 + orY);

			// cuadros de texto del mapeo genico

			tf_gap1 = new TextField("0", tfs_text);
			tf_gap1.setSize(30, 20);
			tf_gap1.setCursorPosition(0);
			tf_gap1.setPosition(100 + orX, -45 + orY);

			tf_gap2 = new TextField("0", tfs_text);
			tf_gap2.setSize(30, 20);
			tf_gap2.setCursorPosition(0);
			tf_gap2.setPosition(220 + orX, -45 + orY);

			tf_gap3 = new TextField("0", tfs_text);
			tf_gap3.setSize(30, 20);
			tf_gap3.setCursorPosition(0);
			tf_gap3.setPosition(340 + orX, -45 + orY);

			tf_gap4 = new TextField("0", tfs_text);
			tf_gap4.setSize(30, 20);
			tf_gap4.setCursorPosition(0);
			tf_gap4.setPosition(480 + orX, -45 + orY);

			tf_gap6 = new TextField("0", tfs_text);
			tf_gap6.setSize(30, 20);
			tf_gap6.setCursorPosition(0);
			tf_gap6.setPosition(100 + orX, -90 + orY);

			tf_gap7 = new TextField("0", tfs_text);
			tf_gap7.setSize(30, 20);
			tf_gap7.setCursorPosition(0);
			tf_gap7.setPosition(195 + orX, -90 + orY);

			tf_gap8 = new TextField("0", tfs_text);
			tf_gap8.setSize(30, 20);
			tf_gap8.setCursorPosition(0);
			tf_gap8.setPosition(305 + orX, -90 + orY);

			tf_gap9 = new TextField("0", tfs_text);
			tf_gap9.setSize(30, 20);
			tf_gap9.setCursorPosition(0);
			tf_gap9.setPosition(431 + orX, -90 + orY);

			tf_gap92 = new TextField("0", tfs_text);
			tf_gap92.setSize(30, 20);
			tf_gap92.setCursorPosition(0);
			tf_gap92.setPosition(495 + orX, -90 + orY);

			tf_gap10 = new TextField("0", tfs_text);
			tf_gap10.setSize(30, 20);
			tf_gap10.setCursorPosition(0);
			tf_gap10.setPosition(150 + orX, -130 + orY);

			tf_gap11 = new TextField("0", tfs_text);
			tf_gap11.setSize(30, 20);
			tf_gap11.setCursorPosition(0);
			tf_gap11.setPosition(300 + orX, -130 + orY);

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
			stage.addActor(cb_mutarferomona);
			stage.addActor(cb_Partenogenesis);

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
			stage.addActor(cb_colectarferomona);
			stage.addActor(cb_colectarPartenogenesis);

			stage.addActor(cb_leerPoblacion);
			stage.addActor(cb_moverMasa);
			stage.addActor(cb_Frontera);
			stage.addActor(cb_discriminar);

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
			stage.addActor(tf_Cantidad2);
			stage.addActor(tf_Cantidad3);
			stage.addActor(tf_Cantidad4);
			stage.addActor(tf_CantidadMax);
			stage.addActor(tf_Temperatura);
			stage.addActor(tf_Start1);
			stage.addActor(tf_Start2);
			stage.addActor(tf_TempFinal1);
			stage.addActor(tf_TempFinal2);
			stage.addActor(tf_DeltaTiempo1);
			stage.addActor(tf_DeltaTiempo2);
			stage.addActor(tf_MultiploPol);
			// stage.addActor(tf_HorizontalTransfer);

			stage.addActor(tf_gap1);
			stage.addActor(tf_gap2);
			stage.addActor(tf_gap3);
			stage.addActor(tf_gap4);

			stage.addActor(tf_gap6);
			stage.addActor(tf_gap7);
			stage.addActor(tf_gap8);
			stage.addActor(tf_gap9);
			stage.addActor(tf_gap92);

			stage.addActor(tf_gap10);
			stage.addActor(tf_gap11);

			stage.addActor(b_load);
			stage.addActor(b_load2);
			stage.addActor(b_load3);
			stage.addActor(b_load4);
			stage.addActor(b_restaurar);

			stage.addActor(b_mutar_todo);
			stage.addActor(b_mutar_nada);
			stage.addActor(b_seguir_todo);
			stage.addActor(b_seguir_nada);

			// botones

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
					cb_mutarferomona.setChecked(true);
					cb_Partenogenesis.setChecked(true);

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
					cb_mutarferomona.setChecked(false);
					cb_Partenogenesis.setChecked(false);

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
					cb_colectarferomona.setChecked(true);
					cb_colectarPartenogenesis.setChecked(true);

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
					cb_colectarferomona.setChecked(false);
					cb_colectarPartenogenesis.setChecked(false);

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
						tx.setEnglish();
					}
					if (ingles == -1) {
						tx.setSpanish();
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
							tx.poblacion + "     (pob2)", "pob2");
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
					//

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

			b_load.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					ventana.setVisible(true);

					// jf.dispose();
				}

			});

			b_load2.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					ventana2.setVisible(true);

					// jf.dispose();
				}

			});

			b_load3.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					ventana3.setVisible(true);

					// jf.dispose();
				}

			});

			b_load4.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					ventana4.setVisible(true);

					// jf.dispose();
				}

			});

			b_restaurar.addListener(new InputListener() {

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					//
					ev.setScreen(new StartMenu(ev));
					// jf.dispose();
				}

			});

			b_comenzar.addListener(new InputListener() {

				private int tiempoCatastrofe;
				int tiempoATB;
				private int tiempoMaximo;
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
				private int numOrg2;

				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					chequeoTexto();// se asegura que no esten los boxs sin
									// escribir

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
						numOrg2 = Integer.parseInt(tf_Cantidad2.getText());
						numOrg3 = Integer.parseInt(tf_Cantidad3.getText());
						numOrg4 = Integer.parseInt(tf_Cantidad4.getText());

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
						SenergiaL = 20;
						QbiomasaL = 20;
						numSenL = 800;
						numQenL = 800;
						SenergiaR = 20;
						QbiomasaR = 20;
						numSenR = 800;
						numQenR = 800;
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

					m = new World(ev, ruta, text.getText(), poblacion, numOrg,
							numSenL, numQenL, SenergiaL, QbiomasaL, numSenR,
							numQenR, SenergiaR, QbiomasaR, cb_leerPoblacion
							.isChecked(), cb_moverMasa.isChecked(),
							cb_Frontera.isChecked(),
							cb_discriminar.isChecked(), genesPedidos()
							.toString(), ingles);

					m.tiempoMuestreo = tiempoMuestreo;
					m.tiempoCatastrofe = tiempoCatastrofe;
					m.tiempoATB = tiempoATB;
					m.maxTime = tiempoMaximo;
					m.maximo = numOrgMax;
					m.temperatura = Temperatura;
					m.minStar1 = Start1;
					m.minStar2 = Start2;
					m.deltaTime1 = DeltaTiempo1;
					m.deltaTime2 = DeltaTiempo2;
					m.TempFinal1 = TempFinal1;
					m.TempFinal2 = TempFinal2;
					m.efficiency = eficiencia;
					m.horizontalTransferRate = horizontalTransferRate;

					m.gap1 = Integer.parseInt(tf_gap1.getText());
					m.gap2 = Integer.parseInt(tf_gap2.getText());
					m.gap3 = Integer.parseInt(tf_gap3.getText());
					m.gap4 = Integer.parseInt(tf_gap4.getText());

					m.gap6 = Integer.parseInt(tf_gap6.getText());
					m.gap7 = Integer.parseInt(tf_gap7.getText());
					m.gap8 = Integer.parseInt(tf_gap8.getText());
					m.gap9 = Integer.parseInt(tf_gap9.getText());
					m.gap10 = Integer.parseInt(tf_gap10.getText());
					m.gap11 = Integer.parseInt(tf_gap11.getText());

					//
					// m.gap1 = Integer.parseInt(tf_gap1.getText());
					// m.gap2 = Integer.parseInt(tf_gap2.getText());
					// m.gap3 = Integer.parseInt(tf_gap3.getText());
					// m.gap4 = Integer.parseInt(tf_gap4.getText());
					//
					// m.gap6 = Integer.parseInt(tf_gap6.getText());
					// m.gap7 = Integer.parseInt(tf_gap7.getText());
					// m.gap8 = Integer.parseInt(tf_gap8.getText());
					// m.gap9 = Integer.parseInt(tf_gap9.getText());
					// m.gap10 = Integer.parseInt(tf_gap10.getText());
					// m.gap11 = Integer.parseInt(tf_gap11.getText());

					if (DeltaTiempo1 != 0) {
						m.tempXSecond1 = (TempFinal1 - Temperatura)
								/ (DeltaTiempo1 * 60);
					}

					if (DeltaTiempo2 != 0) {
						m.tempXSecond2 = (TempFinal2 - TempFinal1)
								/ (DeltaTiempo2 * 60);
					}

					m.collectWidth = cb_colectarSize.isChecked();
					m.collectHeight = cb_colectarSize.isChecked();
					m.collectSpeed = cb_colectarSpeed.isChecked();
					m.collectColor = cb_colectarColor.isChecked();
					m.collectSense = cb_colectarSentir.isChecked();
					m.collectHunt = cb_colectarCazar.isChecked();
					m.collectEscape = cb_colectarEscapar.isChecked();
					m.collectRadius = cb_colectarRadioCon.isChecked();
					m.collectPheromone = cb_colectarferomona.isChecked();
					m.colectarParteNoGen = cb_colectarPartenogenesis
							.isChecked();
					m.collectPredator = cb_colectarPredador.isChecked();
					m.collectLongevity = cb_colectarLongevidad.isChecked();
					m.collectMutationRate = cb_colectarTasaMut.isChecked();
					m.collectTemp = cb_colectarTolerancia.isChecked();
					m.collectResistance = cb_colectarResistencia.isChecked();

					m.mutarColor = cb_mutar.isChecked();
					m.mutarSize = cb_mutarSize.isChecked();
					m.mutarSpeed = cb_mutarSpeed.isChecked();
					m.mutarSentir = cb_mutarSentir.isChecked();
					m.mutarPredador = cb_mutarPredador.isChecked();
					m.mutarCazar = cb_mutarCazar.isChecked();
					m.mutarEscapar = cb_mutarEscapar.isChecked();
					m.mutarRadioconsiente = cb_mutarRadioCon.isChecked();
					m.mutarFeromona = cb_mutarferomona.isChecked();
					m.mutarParteNoGen = cb_Partenogenesis.isChecked();
					m.mutarLongevidad = cb_mutarLongevidad.isChecked();
					m.mutarTasaMut = cb_mutarTasaMut.isChecked();
					m.mutarTemp = cb_mutartolerancia.isChecked();
					m.mutarResistencia = cb_mutarResistencia.isChecked();

					// primero intenta leer los organismos de algun archivo
					// pueden ser heterocigotas

					if (cb_leerPoblacion.isChecked() == true) {
						m.leerArchivoPoblacion();
					}

					// luego intenta crear los organismos seteados en el
					// programa son homocigotas

					int ma = 0;

					while (ma < numOrgMax) {

						if (ma < numOrg) {
							m.agregarPrimerosOrg(1, sexoMacho, 1, resistGen,
									anchoGen, altoGen, senseGen, optTempGen,
									predGen, speedGen, cazarGen, escapeGen,
									radioGen, ferGen, parNoGen, relleno,
									tasMutGen, longeGen, colorGen);
						}
						if (ma < numOrg2) {
							m.agregarPrimerosOrg(1, sexoMacho2, 1, resistGen2,
									anchoGen2, altoGen2, senseGen2,
									optTempGen2, predGen2, speedGen2,
									cazarGen2, escapeGen2, radioGen2, ferGen2,
									parNoGen2, relleno2, tasMutGen2, longeGen2,
									colorGen2);
						}
						if (ma < numOrg3) {
							m.agregarPrimerosOrg(2, sexoMacho3, 1, resistGen3,
									anchoGen3, altoGen3, senseGen3,
									optTempGen3, predGen3, speedGen3,
									cazarGen3, escapeGen3, radioGen3, ferGen3,
									parNoGen3, relleno3, tasMutGen3, longeGen3,
									colorGen3);
						}
						if (ma < numOrg4) {
							m.agregarPrimerosOrg(2, sexoMacho4, 1, resistGen4,
									anchoGen4, altoGen4, senseGen4,
									optTempGen4, predGen4, speedGen4,
									cazarGen4, escapeGen4, radioGen4, ferGen4,
									parNoGen4, relleno4, tasMutGen4, longeGen4,
									colorGen4);
						}

						ma++;
					}

					m.organisms.shuffle();

					m.setDelta();
					m.setDelta2();
					m.setDelta3();
					m.setTiempo();

					m.seconds = 0;
					m.segundos2 = 0;
					m.segundos3 = 0;
					m.seconds4 = 0;
					m.segundos5 = 0;

					// m.colectorEspesiesTotales();

					m.f_genes.createArchive(ruta + m.nombre + tx.genomasTXT);
					m.f_genes.writeArchive(tx.cuandoEstenPresentes + "\n");
					m.f_datos.createArchive(ruta + m.nombre + tx.datosXls);
					if (m.verFrontera == false) {
						m.f_datos.writeArchive(tx.datosOrdenados);
					}
					if (m.verFrontera == true) {
						m.f_datos.writeArchive(tx.datosOrdenados2);
					}
					// m.f_proteoma.creararchivo(ruta+m.nombre+tx.proteomaTXT);
					// m.f_proteoma.escribirArchivo(tx.cuandoEstenPresntesProt+genesPedidos().toString()+
					// "\n");
					// m.f_mutantes.creararchivo(ruta+m.nombre+tx.mutantesXLS);

					StringBuffer linea = new StringBuffer(tx.tiempo + ";"
							+ tx.nombre + ";" + tx.cantidad + "");

					if (cb_colectarResistencia.isChecked()) {
						linea.append("; " + tx.resistensiaATB);
					}
					if (cb_colectarSize.isChecked()) {
						linea.append("; " + tx.size);
					}
					// if(cb_colectarSize.isChecked()){linea.append(", "+
					// tx.ancho);}
					if (cb_colectarSpeed.isChecked()) {
						linea.append("; " + tx.alas);
					}
					if (cb_colectarTasaMut.isChecked()) {
						linea.append("; " + tx.fidelidadADNpol);
					}
					if (cb_colectarTolerancia.isChecked()) {
						linea.append("; " + tx.optimalTemp);
					}
					if (cb_colectarLongevidad.isChecked()) {
						linea.append("; " + tx.longevidad);
					}
					if (cb_colectarRadioCon.isChecked()) {
						linea.append("; " + tx.alcanceVisual);
					}
					if (cb_colectarferomona.isChecked()) {
						linea.append("; " + tx.feromona);
					}
					if (cb_colectarPartenogenesis.isChecked()) {
						linea.append("; " + tx.partenogenesis);
					}
					if (cb_colectarPredador.isChecked()) {
						linea.append("; " + tx.genPredador);
					}
					if (cb_colectarSentir.isChecked()) {
						linea.append("; " + tx.senses);
					}
					if (cb_colectarCazar.isChecked()) {
						linea.append("; " + tx.searchFood);
					}
					if (cb_colectarEscapar.isChecked()) {
						linea.append("; " + tx.escape);
					}
					if (cb_colectarColor.isChecked()) {
						linea.append("; " + tx.color);
					}
					linea.append("\n");

					// m.f_mutantes.escribirArchivo(linea.toString());

					linea.replace(0, linea.length(), "");

					linea.append(tx.tiempo + "(min:seg)" + ";" + tx.tipo + ";"
							+ tx.nombre + ";" + tx.porcentage + " (%)" + ";"
							+ tx.cantidad + "\n");

					m.f_alelos.createArchive(ruta + m.nombre + tx.alelosXLS);
					m.f_alelos.writeArchive(linea.toString());

					linea.replace(0, linea.length(), "");
					linea.append(tx.losGenotipopresentes + "\n\n");
					linea.append(";" + tx.tiempo + "(min:seg)" + ";" + tx.sexo);

					if (cb_colectarResistencia.isChecked()) {
						linea.append(";" + tx.ResATB);
					}
					if (cb_colectarSize.isChecked()) {
						linea.append(";" + tx.size);
					}
					if (cb_colectarSpeed.isChecked()) {
						linea.append(";" + tx.alas);
					}
					if (cb_colectarTolerancia.isChecked()) {
						linea.append(";" + tx.optimalTemp);
					}
					if (cb_colectarPredador.isChecked()) {
						linea.append(";" + tx.genPredador);
					}
					if (cb_colectarSentir.isChecked()) {
						linea.append(";" + tx.senses);
					}
					if (cb_colectarCazar.isChecked()) {
						linea.append(";" + tx.searchFood);
					}
					if (cb_colectarEscapar.isChecked()) {
						linea.append(";" + tx.escape);
					}
					if (cb_colectarRadioCon.isChecked()) {
						linea.append(";" + tx.alcanceVisual);
					}
					if (cb_colectarferomona.isChecked()) {
						linea.append(";" + tx.feromona);
					}
					if (cb_colectarPartenogenesis.isChecked()) {
						linea.append(";" + tx.partenogenesis);
					}
					if (cb_colectarTasaMut.isChecked()) {
						linea.append(";" + tx.tasaMutacionMedia);
					}
					if (cb_colectarLongevidad.isChecked()) {
						linea.append(";" + tx.longevidad);
					}
					if (cb_colectarColor.isChecked()) {
						linea.append(";" + tx.color);
					}
					linea.append(";" + tx.cantidad + ";" + tx.porcentage
							+ " (%)");
					linea.append("\n");

					// m.f_alelos.escribirArchivo(linea.toString());
					m.f_genotipos
							.createArchive(ruta + m.nombre + tx.genotipoXLS);
					m.f_genotipos.writeArchive(linea.toString());

					linea.replace(0, linea.length(), "");

					linea.append(tx.losFenotipopresentes + "\n\n");
					linea.append(";" + tx.tiempo + "(min:seg)" + ";" + tx.sexo);
					if (cb_colectarResistencia.isChecked()) {
						linea.append(";" + tx.ResATB);
					}
					if (cb_colectarSize.isChecked()) {
						linea.append(";" + tx.size);
					}
					if (cb_colectarSpeed.isChecked()) {
						linea.append(";" + tx.alas);
					}
					if (cb_colectarTolerancia.isChecked()) {
						linea.append(";" + tx.optimalTemp);
					}
					if (cb_colectarPredador.isChecked()) {
						linea.append(";" + tx.genPredador);
					}
					if (cb_colectarSentir.isChecked()) {
						linea.append(";" + tx.senses);
					}
					if (cb_colectarCazar.isChecked()) {
						linea.append(";" + tx.searchFood);
					}
					if (cb_colectarEscapar.isChecked()) {
						linea.append(";" + tx.escape);
					}
					if (cb_colectarRadioCon.isChecked()) {
						linea.append(";" + tx.alcanceVisual);
					}
					if (cb_colectarferomona.isChecked()) {
						linea.append(";" + tx.feromona);
					}
					if (cb_colectarPartenogenesis.isChecked()) {
						linea.append(";" + tx.partenogenesis);
					}
					if (cb_colectarTasaMut.isChecked()) {
						linea.append(";" + tx.tasaMutacionMedia);
					}
					if (cb_colectarLongevidad.isChecked()) {
						linea.append(";" + tx.longevidad);
					}
					if (cb_colectarColor.isChecked()) {
						linea.append(";" + tx.color);
					}
					linea.append(";" + tx.cantidad + ";" + tx.porcentage
							+ " (%)");
					linea.append("\n");

					m.f_fenotipos.createArchive(ruta + m.nombre + tx.fenomaTXT);
					m.f_fenotipos.writeArchive(linea.toString());
					m.archivarFenotipo2();

					// m.f_arbol.creararchivo(ruta+m.nombre+tx.arbolTXT);

					// System.out.println(m.segundos);
					// m.colectorEspesies();
					m.guardarDatos();
					m.archivarGenoma();
					// m.archivarFenotipo();
					m.archivarAlelos();
					m.archivarGenotipo();

					ev.setScreen(new com.evoluzion.Screen(ev, m));
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

	public StringBuffer genesPedidos() {

		StringBuffer lin = new StringBuffer();
		if (cb_colectarResistencia.isChecked() == true) {
			lin.append(tx.ResATB + ">");
		}
		if (cb_colectarSize.isChecked() == true) {
			lin.append(tx.size + ">");
		}
		if (cb_colectarSpeed.isChecked() == true) {
			lin.append(tx.alas + ">");
		}
		if (cb_colectarTolerancia.isChecked() == true) {
			lin.append(tx.optimalTemp + ">");
		}
		if (cb_colectarPredador.isChecked() == true) {
			lin.append(tx.genPredador + ">");
		}
		if (cb_colectarSentir.isChecked() == true) {
			lin.append(tx.senses + ">");
		}
		if (cb_colectarCazar.isChecked() == true) {
			lin.append(tx.searchFood + ">");
		}
		if (cb_colectarEscapar.isChecked() == true) {
			lin.append(tx.escape + ">");
		}
		if (cb_colectarRadioCon.isChecked() == true) {
			lin.append(tx.alcanceVisual + ">");
		}
		if (cb_colectarferomona.isChecked() == true) {
			lin.append(tx.feromona + ">");
		}
		if (cb_colectarPartenogenesis.isChecked() == true) {
			lin.append(tx.partenogenesis + ">");
		}
		if (cb_colectarTasaMut.isChecked() == true) {
			lin.append(tx.fidelidadADNpol + ">");
		}
		if (cb_colectarLongevidad.isChecked() == true) {
			lin.append(tx.longevidad + ">");
		}
		if (cb_colectarColor.isChecked() == true) {
			lin.append(tx.color + ">");
		}

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
		ventana.dispose();
		ventana2.dispose();
		ventana3.dispose();
		ventana4.dispose();

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
