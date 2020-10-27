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
	StringBuffer feromona;
	StringBuffer parteNoGen;

	char base;
	char amino;
	int promotor;
	int suma, suma2;
	StringBuffer proteina;
	String codon;
	int atgPos;
	int tataPos;
	boolean bool;

	public Genoma(Mundo m, int toxRes, int anch, int alt, int sense,
			int optTem, int pred) {

		this.m = m;

		proteina = new StringBuffer();

		if (toxRes == 0) {
			resistenciaATB = new StringBuffer(
					"aaatataaaaggtctagcaatgtccctgggcgcgaccggcgaaaatgaatttcagtattataccattgaacgtattattcagggctgcattattggcgataaaaaagtgggcaaaaccaccattattcgtcgtctgtcctgcctgatgaataccctgtcctaagtcacttatcattgccaactcgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagcctt");
		}
		if (toxRes == 1) {
			resistenciaATB = new StringBuffer(
					"aaatataaaaggtctagcaatgtccctgggcgcgaccggcgaaaatgaatttcagtattataccattgaacgtattattccgggctgcattattggcgataaaaaagtgggcaaaaccaccattattcgtcgtctgtcctgcctgatgaataccctgtcctaagtcacttatcattgccaactcgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagcctt");
		}
		// MSLGAT[GE]NEFQYYTIER[II]QGCIIGDKKVGKTTIIRRLSCLMNTLS*
		// ......[GAP]G..{2,12}[IMLV][IMLV]....

		if (anch == 0) {
			ancho = new StringBuffer(
					"aaaatataaaaggtctagcagtggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgatgctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattcataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (anch == 1) {
			ancho = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (anch == 2) {
			ancho = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggtgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcacgtattgaaaccgcgacccgtcgtgtgggcgagtttggctgctccaaaaaaccgaatttaaccgcgattctgtgcgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*
		// ..[RKH][R]......S[KRH]..
		if (alt == 0) {
			alto = new StringBuffer(
					"aaaatataaaaggtctagcagtggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgatgctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattcataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (alt == 1) {
			alto = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (alt == 2) {
			alto = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggtgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcacgtattgaaaccgcgacccgtcgtgtgggcgagtttggctgctccaaaaaaccgaatttaaccgcgattctgtgcgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*

		if (sense == 0) {
			sentir = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaactgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattctgaaagtgtgtatcaagtttggagatctgtccaatccatgcaggccaatagaaataagcacac");
		}
		if (sense == 1) {
			sentir = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagtttattgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattctgaaagtgtgtatcaagtttggagatctgtccaatccatgcaggccaatagaaataagcacac");
		}
		// MLSDK[M]PLTHLLKLLEQFF[EI]VCDETEKHSGK*"
		// "[M].{4,13}[IV][EV]"

		if (optTem == 0) {
			toleranciaTemp = new StringBuffer(
					"aaatataaaaggtctagccatgctgtccaccgtgtattgctccctgatttccgaatccccgaaacatccgtttctgtgcgtgtctaaagcgatgcgtcagtataacgaatccgattattcccgtctgattaccgaacagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggacctactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		if (optTem == 1) {
			toleranciaTemp = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccaccgtgtattgctccctgatttccgaatccccgaaacatccgtttctgtgcgtgtataaagcgatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggagatactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		if (optTem == 2) {
			toleranciaTemp = new StringBuffer(
					"aaatataaaaagtctagcaatgctgtccaccgtgtattgctacctgatttccgaatccccgaaacatccgtttctgtgcgtgtataaagcgatgcgtcagtataaagagtccgattattcccgtctgattaccgaatagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggagatactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		// MLSTVYCSLISESPKHPFLCVYKAMRQYKESDYSRLITEQPRQ*
		// ...[VYS]..........[HPF].......

		if (pred == 0) {
			predador = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataagatcttggccacacagggatcgacaatctcttctgtattaatacggagaatgccttagccctcctctacaacgacgaggcgcccttagagcatgcccatgcaacgctgtcatggcacatcatcacac");
		}
		if (pred == 1) {
			predador = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctggtgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataagatcttggccacacatggatcgacaatctcttctgtattaatacagagaatgccttagcgcttctctacaacgacgaggcgcccttagagcatgcccatgcaacgctgtcatggcacatcatcacac");
		}
		// MLSDK[M]PLTHL[LK]LLEQFFEIVCDETEKHSGK*"
		// .....[M].{4,7}[MIV][KRH]..

	}

	public Genoma(Mundo m, int speedG, int cazarG, int escape, int radio,
			int fero, int parteNo, int relleno) {// int relleno lo agrego para
													// que no me de error por
													// tener dos metodos
													// duplicados

		this.m = m;

		proteina = new StringBuffer();

		if (speedG == 0) {
			speed = new StringBuffer(
					"aaatctaaaaggtctagcaatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcaggcgctgattcgtccggtggattttctggcgctgctgtttggctccctgtgccatgatctgggccataccggcattgataatctgttttgcatttaatggtcataaccacttatttttcgatccacgacaacgtgcttttggctgttcttatcgagctggagtcctctta");
		}
		if (speedG == 1) {
			speed = new StringBuffer(
					"aaatataaaaggcctagcaatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcaggcgctgattcgtccggtggattttctggcgctgctggtttggctcactgtgccatgatctgggccataccggcattgataatctgttttgcatttaatgggcataaccacttatttttcgaaccacgacaacgtgcttttggctgttcttatcgagctggagtcctctta");
		}
		if (speedG == 2) {
			speed = new StringBuffer(
					"aaatataaaaggtctagcaatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcaggcgctgattcgtccggtggattttctggcgctgctgtttggctccctgtgccatgatctgggccataccggcattgataatctgttttgcatttaatgggcataaccacttatttttcgatccacgacaacgtgcttttggctgttcttatcgagctggagtcctctta");
		}
		if (speedG == 3) {
			speed = new StringBuffer(
					"aaatataaaaggtttagcaatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcaggcgctgattcgtccggtggattttctggtgctgctgtttggctccctgtgccatgatctgggccataccggcattgataatctgttttgcatttaatgggcataaccacttatttttcgatccacgacaacgtgcttttggctgttcttatcgagctggagtcctctta");
		}// MRQYKESDYS[RLI]TEQPR[Q][A]LIRPVDFLALLFGSLCHDLGHTGIDNLFCI*
		// .[RKH][LMI][LMI].....Q[AG]........

		if (cazarG == 0) {
			cazar = new StringBuffer(
					"aaatatgaaaggtctagcaatgatttccaccgaagcgaaagaatccgaactggcgtcctatctttatccggcgctgttcgaaaccgtgatggataattccgatgaacatcgtagccagggccatgcgctgctgattaatgattccgaagcgaatcgtgcgacctttaccgaatccatgtaaggaattaattattaaaatacttgagcaccacaatgattcggacattgctttgctcagatggtacattc");
		}
		if (cazarG == 1) {
			cazar = new StringBuffer(
					"aaatataaaaggtctagcaatgatttccaccgaagcgaaagaatccgaactggcgtcctatcgttatccggcgctgtccgaaaccgtgatggataattccgatgaacatcgtacccagggccatccgctgctgatttatgattccgaagcgaatcgtgcgtcctttaccgaatccatgtaaggatttaattattaaaatacttgagccccacaatgattcggagattgctttgctcagatggtacattc");
		}
		// MISTEAKESELASYRY[PAL]SETVMDNSDEHRTQG[HPL]LINDSEANRATFTESM*"
		// ..[PAG][A][L].{4,10}[HKR]...

		if (escape == 0) {
			escapar = new StringBuffer(
					"aaatataaaaggtctagcaatggaaattgcgattcagatgctgaccgaaaaaaccatgctgtataaagaagcgctgttttgctataccgtgccggaagtgattcaggaaattgatcgttccctggataattccctgaaatcctaaggtatgcagtggccctgatgaacgagttctggtcactgggtgaccttatgctagagtgtggtctggagccagacaagatcaagacccgtccacagaaaggtgaag");
		}
		if (escape == 1) {
			escapar = new StringBuffer(
					"aaatataaaaggtctagcaatgaacattgcgattcagatgctgaccgtaaaaaccatgctgtataaagaagcgctgttttgctataccgtgccggaagtgattcaggaaattgatcgttgcctggataattccctgaaatcctaaggtatgcagtggccctgatgaacgagttctggtgacttggtgaccttatggtagagtgtggtctcgagccagacaagatcaagacccgtccatagaaaggtgaag");
		}

		// MEIAI[Q]MLTEKTMLYKEAL[FC]YTVPEVIQEIDRSLDNSLKS*"
		// .....[QE].{2,15}[RKH][C]......

		if (radio == 0) {
			radioConsiente = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgatcattatgttttgcgatatttaaggctttacctccctgtccgataaaatgccgctgtcccatctcctgtaacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacatacaagcgaatctaaatgcaaccgtcg");
		}
		if (radio == 1) {
			radioConsiente = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgaccattatgttttgcgatattaaaggctttacctccctgtccgataaaatgccgctgacccatctgctgtaacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacttacaagcgaatctaaatgcaaccgtcg");
		}
		if (radio == 2) {
			radioConsiente = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgaccattatgttttgcgatattaaaggctttacctccctgtccgataaaatgccgctgacccatctgctgttacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacttacaagcgaatctaaatgcaaccgtcg");
		}

		// MEALKSHATVRF[A]TI[MF]CDIKGFTSLSDKMPLTHLL*
		// ..[APTS]..[MILV]F

		if (fero == 0) {
			feromona = new StringBuffer(
					"tggccagtcctaatcacgagtatctcttcaagcttttggtcattggagactctggagtgggcaaatccgctcttcttctgcgcctgtgtgataagatctttaatgcgtcgtacattacgacgataggtgtagacttcaaggtcaaatcgctaaatataaaggacaacactgttaaattacaaatatgggacacggccggccaagagaagttccggaccattacttctacgtactaccggtga");
		}
		if (fero == 1) {
			feromona = new StringBuffer(
					"aaatataaaaggtctagcaatggccagtcctaatcacgagtatctattcaagcttttggtcattggagactctggagtgggcaaatccgctcttcttctgcgcctgtgtgataagatctttaatgcgtagtacattacgacgataggtgtagacttcaaggtcaaatcgctaaatataaaggacaacactgttaaattacaaatatgggacacggccggccaagagaagttccggaccattacttctacgtactaccggtga");
		}
		if (fero == 2) {
			feromona = new StringBuffer(
					"aaatataaaaggtctagcaatggccagtcctaatcacgagtatctcttcaagcttttggtcattggagactctggagtgggcaaatccgctcttcttctgcgcctgtgtgataagatctttaatgcgtcgtacattacgacgataggtgtagacttcaaggtcaaatcgctaaatataaaggacaacactgttaaattacaaatatgggacacggccggccaagagaagttccggaccattacttctacgtactaccggtga");
		}

		// MASPNHEYLFKLLVIGDSGVGKSALLLRLCDKIFNASYITTIGVDFKVKSLNIKDNTVKLQIWDTAGQEKFRTITSTYYR*
		// M........F[KGS].{2,20}.[LIGSV][LIGSV]..

		if (parteNo == 1) {
			parteNoGen = new StringBuffer(
					"aaatataaaaggtctagcaaaattggctaaaaatgccgactaccagaagatagacgagtacacgaagggctttatgagccggctcaacctatattactgtctcctggactttttgtctgagtgcataggggtgcagaagattaaatcgacgtacaccctttatatggtagcagccgggcttgaaagcattctagatattaactataaaggccctgtcagcggctacctactgggacaggatgacgaccaggagccacaagcataa");
		}

		if (parteNo == 0) {
			parteNoGen = new StringBuffer(
					"aaatataaaaggtctagcaatggctaaaaatgccgactaccagaagatagacgagtacacgaagggctttatgagccggctcaacctatattactgtctcctggactttttgtctgagtgcataggggtgcagaagattaaatcgacgtacaccctttatatggtagcagccgggcttgaaagcattctagatattaactataaaggccctgtcagcggctacctactgggacaggatgacgaccaggagccacaagcataa");
		}
		// MAKNADYQKIDEYTKGFMSRLNLYYCLLDFLSECIGVQKIKSTYTLYMVAAGLESILDINYKGPVSGYLLGQDDDQEPQA*

		// "M..........DFL........[MI]"
		// 1 el gen se expresa y el organismo no es parte no genetico
		// 0 el gen no se expresa y si es homocicoto el organismo es parte no
		// genetico

	}

	public Genoma(Mundo m, int tasMut, int longe, int col) {

		this.m = m;

		proteina = new StringBuffer();

		if (tasMut == 0) {
			tasaMutacion = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgtaagaagattttctgaaaccgccggaacgtacttttcataaaatttatattcagtaaataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}
		if (tasMut == 1) {
			tasaMutacion = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgaaagaagattttctgaaaccgccggaacgtatttttcataaaatttatattcagtaaataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}
		if (tasMut == 2) {
			tasaMutacion = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgaaagaagattttctgaaaccgccggaacgtatttttcataaaatttatattcagtatataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}

		// MDENEKQERLL[M]S[LL]PRN[V]AMEMKEDFLKPPERIFHKIYIQ*
		// .M.[LMIV][LMI]...[VLI]..
		if (longe == 0) {
			longevidad = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgtccattccgaattttaattaattttatattgaactgtatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longe == 1) {
			longevidad = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgtccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longe == 2) {
			longevidad = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgaccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaatataaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagctgattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longe == 3) {
			longevidad = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaattcgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttggtccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}

		// MMSNPRNM[DL]YYQSYS[Q]VGVM[F]ASIPNFNDFYIELDGNNMGVE*
		// .....[DN][LMIV]......[QE]....[FY]

		if (col == 0) {
			color = new StringBuffer(
					"aaatatataaggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgatcatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatac");
		}// a0
		if (col == 1) {
			color = new StringBuffer(
					"aaatataaagggtctagcaaagctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatcc");
		}// a256
		if (col == 2) {
			color = new StringBuffer(
					"aaatataatgggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtgttttcataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaataaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatac");
		}// a452
		if (col == 3) {
			color = new StringBuffer(
					"aaatataaaaggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatatattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgactacctaatagttgtcagtgccatgaatacatatac");
		}// a588
		if (col == 4) {
			color = new StringBuffer(
					"aaatataaaagttctagcaatgctgctgcgtattctgcagctgacccaatttaacgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacccaatcgatgtcggtgccatgaatacatatac");
		}// a700
		// MLLRILQLTQFTEFITQSAYF[YT]IPPVAVVIMILRSMYRRL[ILI]FSQNVEEIWSFCNRTTSCMTT*

	}

	public Genoma(Mundo m) {
		this.m = m;
	}

	public StringBuffer colectarGenoma(Mundo m) {

		StringBuffer linea = new StringBuffer();
		if (m.colectarResistencia == true) {
			linea.append(resistenciaATB + "\n");
		}
		if (m.colectarancho == true) {
			linea.append(ancho + "\n");
		}
		if (m.colectaralto == true) {
			linea.append(alto + "\n");
		}
		if (m.colectSpeed == true) {
			linea.append(speed + "\n");
		}
		if (m.colectSentir == true) {
			linea.append(sentir + "\n");
		}
		if (m.colectPredador == true) {
			linea.append(predador + "\n");
		}
		if (m.colectarTasaMut == true) {
			linea.append(tasaMutacion + "\n");
		}
		if (m.colectCazar == true) {
			linea.append(cazar + "\n");
		}
		if (m.colectEscapar == true) {
			linea.append(escapar + "\n");
		}
		if (m.colectRadioconsiente == true) {
			linea.append(radioConsiente + "\n");
		}
		if (m.colectarFeromona == true) {
			linea.append(feromona + "\n");
		}
		if (m.colectarParteNoGen == true) {
			linea.append(parteNoGen + "\n");
		}
		if (m.colectarLongevidad == true) {
			linea.append(longevidad + "\n");
		}
		if (m.colectarTemp == true) {
			linea.append(toleranciaTemp + "\n");
		}
		if (m.colectColor == true) {
			linea.append(color + "\n");
		}

		return linea;

	}

	public StringBuffer colectarProteoma(Mundo m) {

		StringBuffer linea = new StringBuffer();

		if (m.colectarResistencia == true) {
			linea.append(traducir(resistenciaATB) + "\n");
		}
		if (m.colectarancho == true) {
			linea.append(traducir(ancho) + "\n");
		}
		if (m.colectaralto == true) {
			linea.append(traducir(alto) + "\n");
		}
		if (m.colectSpeed == true) {
			linea.append(traducir(speed) + "\n");
		}
		if (m.colectSentir == true) {
			linea.append(traducir(sentir) + "\n");
		}
		if (m.colectPredador == true) {
			linea.append(traducir(predador) + "\n");
		}
		if (m.colectarTasaMut == true) {
			linea.append(traducir(tasaMutacion) + "\n");
		}
		if (m.colectCazar == true) {
			linea.append(traducir(cazar) + "\n");
		}
		if (m.colectEscapar == true) {
			linea.append(traducir(escapar) + "\n");
		}
		if (m.colectRadioconsiente == true) {
			linea.append(traducir(radioConsiente) + "\n");
		}
		if (m.colectarFeromona == true) {
			linea.append(traducir(feromona) + "\n");
		}
		if (m.colectarParteNoGen == true) {
			linea.append(traducir(parteNoGen) + "\n");
		}
		if (m.colectarLongevidad == true) {
			linea.append(traducir(longevidad) + "\n");
		}
		if (m.colectarTemp == true) {
			linea.append(traducir(toleranciaTemp) + "\n");
		}

		if (m.colectColor == true) {
			linea.append(traducir(color) + "\n");
		}

		return linea;
	}

	public int valorPromotor(StringBuffer srt) {

		promotor = 0;

		// detectar promotor TATA

		tataPos = -1;
		Pattern p = Pattern.compile("[acgt][at]t[at]a[at][ag]....[at]");
		Matcher m = p.matcher(srt);
		if (m.find()) {
			tataPos = m.start();
		}

		// obtener el valos del promotor
		if (tataPos != -1 && tataPos < 12) {

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

		return (float) promotor * suma;
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
		return bool;

	}

	public float traducirColor(Organismo org, String str1, String str2) {

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

		return (r + g + b);
	}

	public StringBuffer traducir(StringBuffer str) {// lee el adn y asigna
													// valores numericos a los
													// paramentros del organismo

		// detectar promotor TATA

		tataPos = -1;
		Pattern p = Pattern.compile("[acgt][at]t[at]a[at][ag]....[at]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			tataPos = m.start();

		}

		// detectar ATG

		atgPos = -1;
		Pattern p2 = Pattern.compile("atg");
		Matcher m2 = p2.matcher(str);
		if (m2.find()) {
			atgPos = m2.start();

		}

		proteina.delete(0, proteina.length());
		// obtenemos la secuencia de la proteina

		if (tataPos != -1 && atgPos != -1 && tataPos < atgPos && tataPos < 12) {

			for (int e = atgPos; e <= str.length() - 3; e = e + 3) {

				codon = str.substring(e, e + 3);

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

		return proteina;
	}
}
