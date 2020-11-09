package com.evoluzion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ClonesWindow extends JFrame implements ActionListener, Closeable,
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Text tx = new Text();
	JPanel panel1, panel2;
	StartMenu mi;

	JComboBox<?> jcb_color, jcb_height, jcb_width, jcb_speed, jcb_radius,
			jcb_tasMut, jcb_longevity, jcb_tolTemp, jcb_sense, jcb_hunt,
			jcb_escape, jcb_predator, jcb_resToxin;
	JLabel jl_color, jl_height, jl_width, jl_speed;
	String path = "./";
	int english = -1; // -1=false 1=true
	int type = 1;

	JButton jb_ok;

	private final Archive a_route;

	private final Archive a_language;

	private final JLabel jl_tolTemp;

	private final JLabel jl_predator;

	private final JLabel jl_sense;

	private final JLabel jl_hunt;

	private final JLabel jl_escape;

	private final JLabel jl_radius;

	private final JLabel jl_tasMut;

	private final JLabel jl_longevity;

	private final JLabel jl_resToxin;

	private final JComboBox<?> jcb_gender;

	private final JLabel jl_gender;

	private final JPanel panel0;

	private final JLabel jl_legend;

	private final JLabel jl_genotype;

	private final JButton jb_cancel;

	private final JComboBox<?> jcb_pheromone;

	private final JLabel jl_pheromone;

	private final JLabel jl_parteNoGen;

	private final JComboBox<?> jcb_parteNoGen;

	public ClonesWindow(StartMenu mi, int type, String str, int color,
						int width, int height, int sense, int optimalTemp, int pred, int speedG,
						int huntG, int escape, int radius, int pheromone, int parte, int mutationRate,
						int longevity, int toxRes, int sex) {

		super(str);
		setLayout(null);
		setSize(630, 300);
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);

		this.mi = mi;
		this.type = type;
		a_route = new Archive();
		a_language = new Archive();

		readPath();
		readLanguage();

		if (english == 1) {
			tx.setEnglish();
		}
		if (english == -1) {
			tx.setSpanish();
		}

		String[] colors = {tx.white, tx.green, tx.celeste, tx.blue};
		String[] size = {tx.small, tx.medium, tx.large};
		String[] speed = {" 0", " 344", " 481", " 547"};
		String[] tolTemp = {" 23", " 25", " 27"};
		String[] radio2 = {" 58", " 100", " 129"};
		String[] tasMut2 = {" 1089", " 2050", " 3225"};
		String[] longevidad = {" 19s", " 30s", " 38s", " 54s"};
		String[] boleano = {tx.falso, tx.verdadero};
		String[] genero = {tx.male, tx.female};
		String[] feromona = {tx.none, tx.normal, tx.strong};
		String[] parteNoGen = {tx.falso, tx.verdadero};

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

		jl_legend = new JLabel();
		jl_legend.setText(tx.legendWindow);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel0.add(jl_legend, gbc);

		jl_genotype = new JLabel();
		jl_genotype.setText(tx.genotype + ":");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel0.add(jl_genotype, gbc);

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

		jb_cancel = new JButton(tx.cancel);
		jb_cancel.setMargin(new Insets(4, 4, 4, 15));
		gbc.gridx = 0;
		gbc.gridy = 0;

		panel1.add(jb_cancel, gbc);

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

		jcb_height = new JComboBox<Object>(size);
		jcb_height.setSelectedIndex(height);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_height, gbc);

		jl_height = new JLabel();
		jl_height.setText(tx.size);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_height);

		jcb_sense = new JComboBox<Object>(boleano);
		jcb_sense.setSelectedIndex(sense);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_sense, gbc);

		jl_sense = new JLabel();
		jl_sense.setText(" " + tx.senses);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_sense, gbc);

		jcb_tolTemp = new JComboBox<Object>(tolTemp);
		jcb_tolTemp.setSelectedIndex(optimalTemp);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_tolTemp, gbc);

		jl_tolTemp = new JLabel();
		jl_tolTemp.setText(tx.optimalTemp);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_tolTemp);

		jcb_predator = new JComboBox<Object>(boleano);
		jcb_predator.setSelectedIndex(pred);
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_predator, gbc);

		jl_predator = new JLabel();
		jl_predator.setText(tx.genPredador);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_predator);

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

		jcb_hunt = new JComboBox<Object>(boleano);
		jcb_hunt.setSelectedIndex(huntG);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_hunt, gbc);

		jl_hunt = new JLabel();
		jl_hunt.setText(tx.searchFood);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_hunt, gbc);

		jcb_escape = new JComboBox<Object>(boleano);
		jcb_escape.setSelectedIndex(escape);
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_escape, gbc);

		jl_escape = new JLabel();
		jl_escape.setText("     " + tx.escape);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_escape, gbc);

		jcb_radius = new JComboBox<Object>(radio2);
		jcb_radius.setSelectedIndex(radius);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_radius, gbc);

		jl_radius = new JLabel();
		jl_radius.setText(tx.alcanceVisual);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_radius, gbc);

		jcb_pheromone = new JComboBox<Object>(feromona);
		jcb_pheromone.setSelectedIndex(pheromone);
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_pheromone, gbc);

		jl_pheromone = new JLabel();
		jl_pheromone.setText(tx.feromona);
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_pheromone, gbc);

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
		jcb_tasMut.setSelectedIndex(mutationRate);
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

		jcb_longevity = new JComboBox<Object>(longevidad);
		jcb_longevity.setSelectedIndex(longevity);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_longevity, gbc);

		jl_longevity = new JLabel();
		jl_longevity.setText("  " + tx.longevidad);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_longevity, gbc);

		jcb_color = new JComboBox<Object>(colors);
		jcb_color.setSelectedIndex(color);
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

		jcb_gender = new JComboBox<Object>(genero);
		jcb_gender.setSelectedIndex(sex);
		gbc.gridx = 4;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jcb_gender, gbc);

		jl_gender = new JLabel();
		jl_gender.setText("    " + tx.sexo);
		gbc.gridx = 4;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panel2.add(jl_gender, gbc);

		jb_ok.addActionListener(this);
		jb_cancel.addActionListener(this);

		setVisible(false);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jb_cancel) {

			setVisible(false);

		}

		if (e.getSource() == jb_ok) {

			if (type == 0) {

				if (jcb_gender.getSelectedIndex() == 0) {
					mi.sexoMacho = true;
				}
				if (jcb_gender.getSelectedIndex() == 1) {
					mi.sexoMacho = false;
				}
				mi.colorGen = jcb_color.getSelectedIndex();
				mi.altoGen = jcb_height.getSelectedIndex();
				mi.anchoGen = jcb_height.getSelectedIndex();
				mi.speedGen = jcb_speed.getSelectedIndex();
				mi.optTempGen = jcb_tolTemp.getSelectedIndex();
				mi.predGen = jcb_predator.getSelectedIndex();
				mi.senseGen = jcb_sense.getSelectedIndex();
				mi.cazarGen = jcb_hunt.getSelectedIndex();
				mi.escapeGen = jcb_escape.getSelectedIndex();
				mi.radioGen = jcb_radius.getSelectedIndex();
				mi.ferGen = jcb_pheromone.getSelectedIndex();
				mi.parNoGen = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen = jcb_tasMut.getSelectedIndex();
				mi.longeGen = jcb_longevity.getSelectedIndex();
				mi.resistGen = jcb_resToxin.getSelectedIndex();

				mi.modificado = true;
			}

			if (type == 1) {

				if (jcb_gender.getSelectedIndex() == 0) {
					mi.sexoMacho2 = true;
				}
				if (jcb_gender.getSelectedIndex() == 1) {
					mi.sexoMacho2 = false;
				}
				mi.colorGen2 = jcb_color.getSelectedIndex();
				mi.altoGen2 = jcb_height.getSelectedIndex();
				mi.anchoGen2 = jcb_height.getSelectedIndex();
				mi.speedGen2 = jcb_speed.getSelectedIndex();
				mi.optTempGen2 = jcb_tolTemp.getSelectedIndex();
				mi.predGen2 = jcb_predator.getSelectedIndex();
				mi.senseGen2 = jcb_sense.getSelectedIndex();
				mi.cazarGen2 = jcb_hunt.getSelectedIndex();
				mi.escapeGen2 = jcb_escape.getSelectedIndex();
				mi.radioGen2 = jcb_radius.getSelectedIndex();
				mi.ferGen2 = jcb_pheromone.getSelectedIndex();
				mi.parNoGen2 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen2 = jcb_tasMut.getSelectedIndex();
				mi.longeGen2 = jcb_longevity.getSelectedIndex();
				mi.resistGen2 = jcb_resToxin.getSelectedIndex();
				mi.modificado2 = true;
			}

			if (type == 2) {

				if (jcb_gender.getSelectedIndex() == 0) {
					mi.sexoMacho3 = true;
				}
				if (jcb_gender.getSelectedIndex() == 1) {
					mi.sexoMacho3 = false;
				}
				mi.colorGen3 = jcb_color.getSelectedIndex();
				mi.altoGen3 = jcb_height.getSelectedIndex();
				mi.anchoGen3 = jcb_height.getSelectedIndex();
				mi.speedGen3 = jcb_speed.getSelectedIndex();
				mi.optTempGen3 = jcb_tolTemp.getSelectedIndex();
				mi.predGen3 = jcb_predator.getSelectedIndex();
				mi.senseGen3 = jcb_sense.getSelectedIndex();
				mi.cazarGen3 = jcb_hunt.getSelectedIndex();
				mi.escapeGen3 = jcb_escape.getSelectedIndex();
				mi.radioGen3 = jcb_radius.getSelectedIndex();
				mi.ferGen3 = jcb_pheromone.getSelectedIndex();
				mi.parNoGen3 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen3 = jcb_tasMut.getSelectedIndex();
				mi.longeGen3 = jcb_longevity.getSelectedIndex();
				mi.resistGen3 = jcb_resToxin.getSelectedIndex();
				mi.modificado3 = true;
			}

			if (type == 3) {

				if (jcb_gender.getSelectedIndex() == 0) {
					mi.sexoMacho4 = true;
				}
				if (jcb_gender.getSelectedIndex() == 1) {
					mi.sexoMacho4 = false;
				}
				mi.colorGen4 = jcb_color.getSelectedIndex();
				mi.altoGen4 = jcb_height.getSelectedIndex();
				mi.anchoGen4 = jcb_height.getSelectedIndex();
				mi.speedGen4 = jcb_speed.getSelectedIndex();
				mi.optTempGen4 = jcb_tolTemp.getSelectedIndex();
				mi.predGen4 = jcb_predator.getSelectedIndex();
				mi.senseGen4 = jcb_sense.getSelectedIndex();
				mi.cazarGen4 = jcb_hunt.getSelectedIndex();
				mi.escapeGen4 = jcb_escape.getSelectedIndex();
				mi.radioGen4 = jcb_radius.getSelectedIndex();
				mi.ferGen4 = jcb_pheromone.getSelectedIndex();
				mi.parNoGen4 = jcb_parteNoGen.getSelectedIndex();
				mi.tasMutGen4 = jcb_tasMut.getSelectedIndex();
				mi.longeGen4 = jcb_longevity.getSelectedIndex();
				mi.resistGen4 = jcb_resToxin.getSelectedIndex();
				mi.modificado4 = true;

			}

			setVisible(false);

		}
	}

	public void close() {
	}

	public void readPath() {
		try {
		FileReader fr2 = new FileReader("evo_ruta.tmp");
		BufferedReader br;
		br = new BufferedReader(fr2);
		String linea;
		linea = null;
		while ((linea = br.readLine()) != null) path = linea;

		br.close();
		fr2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readLanguage() {

		try {
			FileReader fr = new FileReader("evo_idioma.tmp");
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				String str = linea;

				if (str.equals("eng")) {
					english = 1;
				}
				if (str.equals("spa")) {
					english = -1;
				}

			}

			br.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}// TODO Auto-generated catch block


	}

}