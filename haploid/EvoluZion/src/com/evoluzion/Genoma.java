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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Genoma {

	Mundo m;
	Organismo or;
	StringBuffer ancho, alto;
	StringBuffer speed;
	StringBuffer color;
	StringBuffer predador;
	StringBuffer radioConsiente, sentir, cazar, escapar;
	StringBuffer tasaMutacion;
	StringBuffer longevidad;
	StringBuffer toleranciaTemp;
	StringBuffer resistenciaATB;

	char base;
	char amino;
	int promotor;
	int suma, suma2;
	StringBuffer proteina;
	String codon;
	int atgPos;
	int tataPos;
	boolean bool;

	public Genoma() {

		proteina = new StringBuffer();

		ancho = new StringBuffer(
				"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccgaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacctaacagtcccgaatactcaccgtctgtcagctcttgg");
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*
		// ..[RKH][R]......S[KRH]..

		alto = new StringBuffer(
				"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccgaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacctaacagtcccgaatactcaccgtctgtcagctcttgg");
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*
		// ..[RKH][R]......S[KRH]..
		speed = new StringBuffer(
				"aaatataaaaggtctagcaatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcaggcgctgattcgtccggtggattttctggcgctgctgtttggctccctgtgccatgatctgggccataccggcattgataatctgttttgcatttaatgggcataaccacttatttttcgatccacgacaacgtgcttttggctgttcttatcgagctggagtcctctta");
		// MRQYKESDYS[RLI]TEQPR[Q][A]LIRPVDFLALLFGSLCHDLGHTGIDNLFCI*
		// .[RKH][LMI][LMI].....Q[AG]........
		color = new StringBuffer(
				"aaatataaaaggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatac");
		// MLLRILQLTQFTEFITQSAYF[YT]IPPVAVVIMILRSMYRRL[ILI]FSQNVEEIWSFCNRTTSCMTT*

		predador = new StringBuffer(
				"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataagatcttggccacacagggatcgacaatctcttctgtattaatacggagaatgccttagccctcctctacaacgacgaggcgcccttagagcatgcccatgcaacgctgtcatggcacatcatcacac");
		// MLSDK[M]PLTHL[LK]LLEQFFEIVCDETEKHSGK*"
		// .....[M].{4,7}[MIV][KRH]..
		sentir = new StringBuffer(
				"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattctgaaagtgtgtatcaagtttggagatctgtccaatccatgcaggccaatagaaataagcacac");
		// MLSDK[M]PLTHLLKLLEQFF[EI]VCDETEKHSGK*"
		// "[M].{4,13}[IV][EV]"
		cazar = new StringBuffer(
				"aaatataaaaggtctagcaatgatttccaccgaagcgaaagaatccgaactggcgtcctatcgttatccggcgctgtccgaaaccgtgatggataattccgatgaacatcgtacccagggccatccgctgctgattaatgattccgaagcgaatcgtgcgacctttaccgaatccatgtaaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattc");
		// MISTEAKESELASYRY[PAL]SETVMDNSDEHRTQG[HPL]LINDSEANRATFTESM*"
		// ..[PAG][A][L].{4,10}[HKR]...

		escapar = new StringBuffer(
				"aaatataaaaggtctagcaatggaaattgcgattcagatgctgaccgaaaaaaccatgctgtataaagaagcgctgttttgctataccgtgccggaagtgattcaggaaattgatcgttccctggataattccctgaaatcctaaggtatgcagtggccctgatgaacgagttctggtcactgggtgaccttatgctagagtgtggtctggagccagacaagatcaagacccgtccacagaaaggtgaag");
		// MEIAI[Q]MLTEKTMLYKEAL[FC]YTVPEVIQEIDRSLDNSLKS*"
		// .....[QE].{2,15}[RKH][C]......

		radioConsiente = new StringBuffer(
				"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgaccattatgttttgcgatattaaaggctttacctccctgtccgataaaatgccgctgacccatctgctgtaacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacttacaagcgaatctaaatgcaaccgtcg");
		// MEALKSHATVRF[A]TI[MF]CDIKGFTSLSDKMPLTHLL*
		// ..[APTS]..[MILV]F
		tasaMutacion = new StringBuffer(
				"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgaaagaagattttctgaaaccgccggaacgtatttttcataaaatttatattcagtaaataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		// MDENEKQERLL[M]S[LL]PRN[V]AMEMKEDFLKPPERIFHKIYIQ*
		// .M.[LMIV][LMI]...[VLI]..
		longevidad = new StringBuffer(
				"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgtccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		// MMSNPRNM[DL]YYQSYS[Q]VGVM[F]ASIPNFNDFYIELDGNNMGVE*
		// .....[DN][LMIV]......[QE]....[FY]

		toleranciaTemp = new StringBuffer(
				"aaatataaaaggtctagcaatgctgtccaccgtgtattgctccctgatttccgaatccccgaaacatccgtttctgtgcgtgtataaagcgatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggagatactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		// MLSTVYCSLISESPKHPFLCVYKAMRQYKESDYSRLITEQPRQ*
		// ...[VYS]..........[HPF].......

		resistenciaATB = new StringBuffer(
				"aaatataaaaggtctagcaatgtccctgggcgcgaccggcgaaaatgaatttcagtattataccattgaacgtattattcagggctgcattattggcgataaaaaagtgggcaaaaccaccattattcgtcgtctgtcctgcctgatgaataccctgtcctaagtcacttatcattgccaactcgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagcctt");
		// MSLGAT[GE]NEFQYYTIER[II]QGCIIGDKKVGKTTIIRRLSCLMNTLS*
		// ......[GAP]G..{2,12}[IMLV][IMLV]....
	}

	public StringBuffer colectarGenoma(Mundo m) {

		StringBuffer linea = new StringBuffer();
		linea.append(color + ";");
		linea.append(ancho + ";");
		linea.append(alto + ";");
		linea.append(speed + ";");
		linea.append(sentir + ";");
		linea.append(predador + ";");
		linea.append(tasaMutacion + ";");
		linea.append(cazar + ";");
		linea.append(escapar + ";");
		linea.append(radioConsiente + ";");
		linea.append(longevidad + ";");
		linea.append(toleranciaTemp + ";");
		linea.append(resistenciaATB);

		return linea;

	}

	public StringBuffer colectarProteoma(Mundo m) {

		StringBuffer linea = new StringBuffer();
		linea.append(traducir(color) + ";");
		linea.append(traducir(ancho) + ";");
		linea.append(traducir(alto) + ";");
		linea.append(traducir(speed) + ";");
		linea.append(traducir(sentir) + ";");
		linea.append(traducir(predador) + ";");
		linea.append(traducir(tasaMutacion) + ";");
		linea.append(traducir(cazar) + ";");
		linea.append(traducir(escapar) + ";");
		linea.append(traducir(radioConsiente) + ";");
		linea.append(traducir(longevidad) + ";");
		linea.append(traducir(toleranciaTemp) + ";");
		linea.append(traducir(resistenciaATB) + "");

		return linea;

	}

	public int valorPromotor(StringBuffer srt) {

		promotor = 0;
		// tataPos=0;
		// atgPos=0;

		// detectar promotor TATA

		tataPos = -1;
		Pattern p = Pattern.compile("[acgt][at]t[at]a[at][ag]....[at]");
		Matcher m = p.matcher(srt);
		if (m.find()) {
			tataPos = m.start();
			// if(tataPos==-1){System.out.println("no hay promotor");}

		}

		// obtener el valos del promotor
		if (tataPos != -1) {

			for (int e = tataPos; e < tataPos + 12; e++) {

				// obtenemos la letra de una posicion determinada
				base = srt.charAt(e);
				suma = 0;
				if (base == 'a') {
					suma = 1;
				}
				if (base == 'c') {
					suma = 0;
				}
				if (base == 'g') {
					suma = 0;
				}
				if (base == 't') {
					suma = 1;
				}

				promotor = promotor + suma;

			}
		}

		if (promotor < 0) {
			promotor = 0;
		}

		// System.out.println("Promotor: "+ promotor);
		return promotor;

	}

	public int puntajeAminoAcidos(char c) {

		int protV = 0;

		if (c == 'C') {
			protV = 0;
		}

		if (c == 'S') {
			protV = 1;
		}
		if (c == 'T') {
			protV = 2;
		}
		if (c == 'P') {
			protV = 3;
		}
		if (c == 'A') {
			protV = 4;
		}
		if (c == 'G') {
			protV = 5;
		}

		if (c == 'N') {
			protV = 6;
		}
		if (c == 'D') {
			protV = 7;
		}
		if (c == 'E') {
			protV = 8;
		}
		if (c == 'Q') {
			protV = 9;
		}

		if (c == 'H') {
			protV = 10;
		}
		if (c == 'R') {
			protV = 11;
		}
		if (c == 'K') {
			protV = 12;
		}

		if (c == 'M') {
			protV = 13;
		}
		if (c == 'I') {
			protV = 14;
		}
		if (c == 'L') {
			protV = 15;
		}
		if (c == 'V') {
			protV = 16;
		}

		if (c == 'F') {
			protV = 17;
		}
		if (c == 'Y') {
			protV = 18;
		}

		if (c == 'W') {
			protV = 19;
		}

		return protV;

		// System.out.println(protV);
	}

	public float traducirMagnitud(Organismo or, StringBuffer strB, String str) {

		promotor = valorPromotor(strB);// valor del promotor
		suma = 0;
		proteina = traducir(strB);// secuencia de la proteina

		Pattern p = Pattern.compile(str);// tiene que encontrar esta condición
		Matcher m = p.matcher(proteina);
		if (m.find()) {// detectar la secuencia conservada

			// si esta está, obtener el valor de la proteina

			for (int i = 0; i < proteina.length(); i++) {
				amino = proteina.charAt(i);

				suma = suma + puntajeAminoAcidos(amino);

				// System.out.println("promotor "+ promotor+ "suma"+ suma);
			}
		}

		return promotor * suma;

	}

	public boolean traducirBoolean(StringBuffer strb, String str) {
		bool = false;

		int promotor = valorPromotor(strb);// valor del promotor
		// float protV=0;
		proteina = traducir(strb);// secuencia de la proteina

		Pattern p = Pattern.compile(str);// si encuentra esta condicion se hace
											// predador
		Matcher m = p.matcher(proteina);
		if (m.find()) {

			bool = true;
		}

		if (promotor == 0) {
			bool = false;
		}
		;

		// System.out.println(promotor+ "  "+ bool);

		return bool;

	}

	public int traducirColor(Organismo org, String str1, String str2) {

		float r = 0;
		float g = 0;
		float b = 0;

		float promotor = valorPromotor(color);// valor del promotor

		proteina = traducir(color);// secuencia de la proteina

		int a = proteina.indexOf(str1);// detectar la secuencia conservada
		int a2 = proteina.indexOf(str2);// detectar la segunda secuencia
										// conservada

		if (a == -1) {
			r = 255;
			g = 255;
		}// no hay rojo y verde
		if (a2 == -1) {
			b = 255;
		}// no hay azul

		if (a != -1 & a2 != -1 && a > a2) {
			r = 0;
			g = 0;
			b = 0;
		}

		// obtener el color rojo
		if (a != -1 && a2 != -1 && a < a2) {

			for (int i = 0; i < a; i++) {

				amino = proteina.charAt(i);

				r = r + puntajeAminoAcidos(amino);
			}
		}

		// obtener el color verde
		if (a != -1 && a2 != -1 && a < a2) {

			for (int i = a; i < a2; i++) {

				amino = proteina.charAt(i);

				g = g + puntajeAminoAcidos(amino);
			}
		}

		// obtener el color azul
		if (a != -1 && a2 != -1 && a < a2) {

			for (int i = a2; i < proteina.length(); i++) {

				amino = proteina.charAt(i);

				b = b + puntajeAminoAcidos(amino);
			}
		}

		if (promotor == 0) {
			r = 0;
			g = 0;
			b = 0;
		}
		;
		if (promotor > 0) {
			r = promotor * r;
			g = promotor * g;
			b = promotor * b;
		}

		if (r > 255) {
			r = 255;
		}
		if (g > 255) {
			g = 255;
		}
		if (b > 255) {
			b = 255;
		}

		// System.out.println("promotor: "+promotor+" r: "+r
		// +" g: "+g+" b: "+b);

		return (int) (r + g + b);

	}

	public StringBuffer traducir(StringBuffer str) {// lee el adn y asigna
													// valores numericos a los
													// paramentros del organismo

		// promotor=0;
		// tataPos=0;
		// atgPos=0;

		// detectar promotor TATA

		tataPos = -1;
		Pattern p = Pattern.compile("[acgt][at]t[at]a[at][ag]....[at]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			tataPos = m.start();
			// if(tataPos==-1){System.out.println("no hay promotor");}

		}

		// detectar ATG

		atgPos = -1;
		Pattern p2 = Pattern.compile("atg");
		Matcher m2 = p2.matcher(str);
		if (m2.find()) {
			atgPos = m2.start();
			// if(atgPos==-1){System.out.println("no hay atg");}

		}

		proteina.delete(0, proteina.length());
		// obtenemos la secuencia de la proteina

		if (tataPos != -1 && atgPos != -1 && tataPos < atgPos) {

			for (int e = atgPos; e <= str.length() - 3; e = e + 3) {

				codon = str.substring(e, e + 3);

				// System.out.println("codon: " +codon);

				if (codon.equals("atg")) {
					proteina.append("M");
				}
				if (codon.equals("aga") || codon.equals("agg")
						|| codon.equals("cga") || codon.equals("cgc")
						|| codon.equals("cgg") || codon.equals("cgt")) {
					proteina.append("R");
				}
				if (codon.equals("gca") || codon.equals("gcc")
						|| codon.equals("gcg") || codon.equals("gct")) {
					proteina.append("A");
				}
				if (codon.equals("aat") || codon.equals("aac")) {
					proteina.append("N");
				}
				if (codon.equals("gat") || codon.equals("gac")) {
					proteina.append("D");
				}
				if (codon.equals("tgt") || codon.equals("tgc")) {
					proteina.append("C");
				}
				if (codon.equals("caa") || codon.equals("cag")) {
					proteina.append("Q");
				}
				if (codon.equals("gaa") || codon.equals("gag")) {
					proteina.append("E");
				}
				if (codon.equals("gga") || codon.equals("ggc")
						|| codon.equals("ggg") || codon.equals("ggt")) {
					proteina.append("G");
				}
				if (codon.equals("cat") || codon.equals("cac")) {
					proteina.append("H");
				}
				if (codon.equals("ata") || codon.equals("att")
						|| codon.equals("atc")) {
					proteina.append("I");
				}
				if (codon.equals("tta") || codon.equals("ttg")
						|| codon.equals("ctt") || codon.equals("ctc")
						|| codon.equals("cta") || codon.equals("ctg")) {
					proteina.append("L");
				}
				if (codon.equals("aaa") || codon.equals("aag")) {
					proteina.append("K");
				}
				if (codon.equals("ttt") || codon.equals("ttc")) {
					proteina.append("F");
				}
				if (codon.equals("tct") || codon.equals("tcc")
						|| codon.equals("tca") || codon.equals("tcg")
						|| codon.equals("agt") || codon.equals("agc")) {
					proteina.append("S");
				}
				if (codon.equals("act") || codon.equals("acg")
						|| codon.equals("acc") || codon.equals("aca")) {
					proteina.append("T");
				}
				if (codon.equals("tgg")) {
					proteina.append("W");
				}
				if (codon.equals("tat") || codon.equals("tac")) {
					proteina.append("Y");
				}
				if (codon.equals("gta") || codon.equals("gtc")
						|| codon.equals("gtg") || codon.equals("gtt")) {
					proteina.append("V");
				}
				if (codon.equals("cca") || codon.equals("ccc")
						|| codon.equals("ccg") || codon.equals("cct")) {
					proteina.append("P");
				}
				if (codon.equals("taa") || codon.equals("tag")
						|| codon.equals("tga")) {
					e = str.length();
				}

			}
		}

		// System.out.println("proteina: "+ proteina);

		return proteina;

	}

}
