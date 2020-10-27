package com.evoluzion;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaClones extends JFrame implements ActionListener, Closeable,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Texto tx = new Texto();
	JPanel panel1, panel2;
	MenuInicio mi;

	JComboBox<?> jcb_color, jcb_alto, jcb_ancho, jcb_speed, jcb_radio,
			jcb_tasMut, jcb_longevidad, jcb_tolTemp, jcb_sentir, jcb_cazar,
			jcb_escapar, jcb_predador, jcb_resToxin;
	JLabel jl_color, jl_alto, jl_ancho, jl_speed;
	String ruta = "./";
	int ingles = -1; // -1=false 1=true
	int tipo = 1;

	JButton jb_ok;

	private Archivar a_ruta;

	private Archivar a_idioma;

	private JLabel jl_tolTemp;

	private JLabel jl_predador;

	private JLabel jl_sentir;

	private JLabel jl_cazar;

	private JLabel jl_escapar;

	private JLabel jl_radio;

	private JLabel jl_tasMut;

	private JLabel jl_longevidad;

	private JLabel jl_resToxin;

	private JComboBox<?> jcb_genero;

	private JLabel jl_genero;

	private JPanel panel0;

	private JLabel jl_lellenda;

	private JLabel jl_genotipo;

	private JButton jb_cancelar;

	private JComboBox<?> jcb_feromona;

	private JLabel jl_feromona;

	private JLabel jl_parteNoGen;

	private JComboBox<?> jcb_parteNoGen;

	public VentanaClones(MenuInicio mi, int tipo, String str, int col,
			int anch, int alt, int sense, int optTem, int pred, int speedG,
			int cazarG, int escape, int radio, int fer, int parte, int tasMut,
			int longe, int toxRes, int sex) {

		super(str);
		setLayout(null);
		setSize(630, 300);
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);

		this.mi = mi;
		this.tipo = tipo;
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

		String[] color = { tx.blanco, tx.verde, tx.celeste, tx.azul };
		String[] tamaño = { tx.pequeño, tx.mediano, tx.grande };
		String[] speed = { " 0", " 344", " 481", " 547" };
		String[] tolTemp = { " 23", " 25", " 27" };
		String[] radio2 = { " 58", " 100", " 129" };
		String[] tasMut2 = { " 1089", " 2050", " 3225" };
		String[] longevidad = { " 19s", " 30s", " 38s", " 54s" };
		String[] boleano = { tx.falso, tx.verdadero };
		String[] genero = { tx.macho, tx.hembra };
		String[] feromona = { tx.nada, tx.normal, tx.fuerte };
		String[] parteNoGen = { tx.falso, tx.verdadero };

		this.setLayout(new GridBagLayout());// ordena los componentes en una
											// grilla
		GridBagConstraints gbc = new GridBagConstraints();// define como se van
															// a ordenar los
															// componetes

		panel0 = new JPanel();
		panel0.setBackground(Color.white);
		panel0.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(panel0, gbc);

		jl_lellenda = new JLabel();
		jl_lellenda.setText(tx.ventanalellenda);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel0.add(jl_lellenda, gbc);

		jl_genotipo = new JLabel();
		jl_genotipo.setText(tx.genotipo + ":");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel0.add(jl_genotipo, gbc);

		panel1 = new JPanel();
		panel1.setBackground(Color.white);
		panel1.setLayout(new GridLayout());
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(panel1, gbc);

		jb_cancelar = new JButton(tx.cancelar);
		jb_cancelar.setMargin(new Insets(4, 4, 4, 15));
		gbc.gridx = 0;
		gbc.gridy = 0;

		panel1.add(jb_cancelar, gbc);

		jb_ok = new JButton("OK");
		jb_ok.setMargin(new Insets(4, 4, 4, 15));
		gbc.gridx = 1;
		gbc.gridy = 0;

		panel1.add(jb_ok, gbc);

		panel2 = new JPanel();
		panel2.setBackground(Color.white);
		panel2.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(panel2, gbc);

		jcb_resToxin = new JComboBox<Object>(boleano);
		jcb_resToxin.setSelectedIndex(toxRes);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_resToxin, gbc);

		jl_resToxin = new JLabel();
		jl_resToxin.setText(tx.ResATB);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_resToxin, gbc);

		jcb_alto = new JComboBox<Object>(tamaño);
		jcb_alto.setSelectedIndex(alt);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_alto, gbc);

		jl_alto = new JLabel();
		jl_alto.setText(tx.tamano);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_alto);

		jcb_sentir = new JComboBox<Object>(boleano);
		jcb_sentir.setSelectedIndex(sense);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_sentir, gbc);

		jl_sentir = new JLabel();
		jl_sentir.setText(" " + tx.sentidos);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_sentir, gbc);

		jcb_tolTemp = new JComboBox<Object>(tolTemp);
		jcb_tolTemp.setSelectedIndex(optTem);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_tolTemp, gbc);

		jl_tolTemp = new JLabel();
		jl_tolTemp.setText(tx.temOptima);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_tolTemp);

		jcb_predador = new JComboBox<Object>(boleano);
		jcb_predador.setSelectedIndex(pred);
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_predador, gbc);

		jl_predador = new JLabel();
		jl_predador.setText(tx.genPredador);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_predador);

		jcb_speed = new JComboBox<Object>(speed);
		jcb_speed.setSelectedIndex(speedG);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_speed, gbc);

		jl_speed = new JLabel();
		jl_speed.setText(tx.alas);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_speed, gbc);

		jcb_cazar = new JComboBox<Object>(boleano);
		jcb_cazar.setSelectedIndex(cazarG);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_cazar, gbc);

		jl_cazar = new JLabel();
		jl_cazar.setText(tx.buscarComida);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_cazar, gbc);

		jcb_escapar = new JComboBox<Object>(boleano);
		jcb_escapar.setSelectedIndex(escape);
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_escapar, gbc);

		jl_escapar = new JLabel();
		jl_escapar.setText("     " + tx.escapar);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_escapar, gbc);

		jcb_radio = new JComboBox<Object>(radio2);
		jcb_radio.setSelectedIndex(radio);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_radio, gbc);

		jl_radio = new JLabel();
		jl_radio.setText(tx.alcanceVisual);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_radio, gbc);

		jcb_feromona = new JComboBox<Object>(feromona);
		jcb_feromona.setSelectedIndex(fer);
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_feromona, gbc);

		jl_feromona = new JLabel();
		jl_feromona.setText(tx.feromona);
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_feromona, gbc);

		jcb_parteNoGen = new JComboBox<Object>(parteNoGen);
		jcb_parteNoGen.setSelectedIndex(parte);
		gbc.gridx = 5;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_parteNoGen, gbc);

		jl_parteNoGen = new JLabel();
		jl_parteNoGen.setText(tx.partenogen);
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_parteNoGen, gbc);

		jcb_tasMut = new JComboBox<Object>(tasMut2);
		jcb_tasMut.setSelectedIndex(tasMut);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_tasMut, gbc);

		jl_tasMut = new JLabel();
		jl_tasMut.setText(tx.fidelidadADNpol);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_tasMut, gbc);

		jcb_longevidad = new JComboBox<Object>(longevidad);
		jcb_longevidad.setSelectedIndex(longe);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_longevidad, gbc);

		jl_longevidad = new JLabel();
		jl_longevidad.setText("  " + tx.longevidad);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_longevidad, gbc);

		jcb_color = new JComboBox<Object>(color);
		jcb_color.setSelectedIndex(col);
		gbc.gridx = 2;// 2
		gbc.gridy = 6;// 6
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_color, gbc);

		jl_color = new JLabel();
		jl_color.setText(tx.color);
		gbc.gridx = 2;// 2
		gbc.gridy = 5;// 5
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_color, gbc);

		jcb_genero = new JComboBox<Object>(genero);
		jcb_genero.setSelectedIndex(sex);
		gbc.gridx = 4;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_genero, gbc);

		jl_genero = new JLabel();
		jl_genero.setText("    " + tx.sexo);
		gbc.gridx = 4;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_genero, gbc);

		jb_ok.addActionListener(this);
		jb_cancelar.addActionListener(this);

		setVisible(false);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jb_cancelar) {

			setVisible(false);

		}

		if (e.getSource() == jb_ok) {

			if (tipo == 0) {

				if (jcb_genero.getSelectedIndex() == 0) {
					mi.sexoMacho = true;
				}
				if (jcb_genero.getSelectedIndex() == 1) {
					mi.sexoMacho = false;
				}
				mi.colorGen = jcb_color.getSelectedIndex();
				mi.altoGen = jcb_alto.getSelectedIndex();
				mi.anchoGen = jcb_alto.getSelectedIndex();
				mi.speedGen = jcb_speed.getSelectedIndex();
				mi.optTempGen = jcb_tolTemp.getSelectedIndex();
				mi.predGen = jcb_predador.getSelectedIndex();
				mi.senseGen = jcb_sentir.getSelectedIndex();
				mi.cazarGen = jcb_cazar.getSelectedIndex();
				mi.escapeGen = jcb_escapar.getSelectedIndex();
				mi.radioGen = jcb_radio.getSelectedIndex();
				mi.ferGen = jcb_feromona.getSelectedIndex();
				mi.parNoGen = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen = jcb_tasMut.getSelectedIndex();
				mi.longeGen = jcb_longevidad.getSelectedIndex();
				mi.resistGen = jcb_resToxin.getSelectedIndex();

				mi.modificado = true;
			}

			if (tipo == 1) {

				if (jcb_genero.getSelectedIndex() == 0) {
					mi.sexoMacho2 = true;
				}
				if (jcb_genero.getSelectedIndex() == 1) {
					mi.sexoMacho2 = false;
				}
				mi.colorGen2 = jcb_color.getSelectedIndex();
				mi.altoGen2 = jcb_alto.getSelectedIndex();
				mi.anchoGen2 = jcb_alto.getSelectedIndex();
				mi.speedGen2 = jcb_speed.getSelectedIndex();
				mi.optTempGen2 = jcb_tolTemp.getSelectedIndex();
				mi.predGen2 = jcb_predador.getSelectedIndex();
				mi.senseGen2 = jcb_sentir.getSelectedIndex();
				mi.cazarGen2 = jcb_cazar.getSelectedIndex();
				mi.escapeGen2 = jcb_escapar.getSelectedIndex();
				mi.radioGen2 = jcb_radio.getSelectedIndex();
				mi.ferGen2 = jcb_feromona.getSelectedIndex();
				mi.parNoGen2 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen2 = jcb_tasMut.getSelectedIndex();
				mi.longeGen2 = jcb_longevidad.getSelectedIndex();
				mi.resistGen2 = jcb_resToxin.getSelectedIndex();
				mi.modificado2 = true;
			}

			if (tipo == 2) {

				if (jcb_genero.getSelectedIndex() == 0) {
					mi.sexoMacho3 = true;
				}
				if (jcb_genero.getSelectedIndex() == 1) {
					mi.sexoMacho3 = false;
				}
				mi.colorGen3 = jcb_color.getSelectedIndex();
				mi.altoGen3 = jcb_alto.getSelectedIndex();
				mi.anchoGen3 = jcb_alto.getSelectedIndex();
				mi.speedGen3 = jcb_speed.getSelectedIndex();
				mi.optTempGen3 = jcb_tolTemp.getSelectedIndex();
				mi.predGen3 = jcb_predador.getSelectedIndex();
				mi.senseGen3 = jcb_sentir.getSelectedIndex();
				mi.cazarGen3 = jcb_cazar.getSelectedIndex();
				mi.escapeGen3 = jcb_escapar.getSelectedIndex();
				mi.radioGen3 = jcb_radio.getSelectedIndex();
				mi.ferGen3 = jcb_feromona.getSelectedIndex();
				mi.parNoGen3 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen3 = jcb_tasMut.getSelectedIndex();
				mi.longeGen3 = jcb_longevidad.getSelectedIndex();
				mi.resistGen3 = jcb_resToxin.getSelectedIndex();
				mi.modificado3 = true;
			}

			if (tipo == 3) {

				if (jcb_genero.getSelectedIndex() == 0) {
					mi.sexoMacho4 = true;
				}
				if (jcb_genero.getSelectedIndex() == 1) {
					mi.sexoMacho4 = false;
				}
				mi.colorGen4 = jcb_color.getSelectedIndex();
				mi.altoGen4 = jcb_alto.getSelectedIndex();
				mi.anchoGen4 = jcb_alto.getSelectedIndex();
				mi.speedGen4 = jcb_speed.getSelectedIndex();
				mi.optTempGen4 = jcb_tolTemp.getSelectedIndex();
				mi.predGen4 = jcb_predador.getSelectedIndex();
				mi.senseGen4 = jcb_sentir.getSelectedIndex();
				mi.cazarGen4 = jcb_cazar.getSelectedIndex();
				mi.escapeGen4 = jcb_escapar.getSelectedIndex();
				mi.radioGen4 = jcb_radio.getSelectedIndex();
				mi.ferGen4 = jcb_feromona.getSelectedIndex();
				mi.parNoGen4 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen4 = jcb_tasMut.getSelectedIndex();
				mi.longeGen4 = jcb_longevidad.getSelectedIndex();
				mi.resistGen4 = jcb_resToxin.getSelectedIndex();
				mi.modificado4 = true;

			}

			setVisible(false);

		}
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub

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

}