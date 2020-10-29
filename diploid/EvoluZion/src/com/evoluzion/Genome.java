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

public class Genome {

	World world;
	StringBuffer width, height;
	StringBuffer speed;
	StringBuffer color;
	StringBuffer predator;
	StringBuffer radius, sense, hunt, escape;
	StringBuffer mutationRate;
	StringBuffer longevity;
	StringBuffer toleranceTemp;
	StringBuffer resistanceATB;
	StringBuffer pheromone;
	StringBuffer parteNoGen;

	char base;
	char amino;
	int promoter;
	int sum;
	StringBuffer protein;
	String codon;
	int atgPos;
	int tataPos;

	public Genome(World world, int toxRes, int width, int height, int sense,
				  int optimalTemp, int pred) {

		this.world = world;

		protein = new StringBuffer();

		if (toxRes == 0) {
			resistanceATB = new StringBuffer(
					"aaatataaaaggtctagcaatgtccctgggcgcgaccggcgaaaatgaatttcagtattataccattgaacgtattattcagggctgcattattggcgataaaaaagtgggcaaaaccaccattattcgtcgtctgtcctgcctgatgaataccctgtcctaagtcacttatcattgccaactcgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagcctt");
		}
		if (toxRes == 1) {
			resistanceATB = new StringBuffer(
					"aaatataaaaggtctagcaatgtccctgggcgcgaccggcgaaaatgaatttcagtattataccattgaacgtattattccgggctgcattattggcgataaaaaagtgggcaaaaccaccattattcgtcgtctgtcctgcctgatgaataccctgtcctaagtcacttatcattgccaactcgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagcctt");
		}
		// MSLGAT[GE]NEFQYYTIER[II]QGCIIGDKKVGKTTIIRRLSCLMNTLS*
		// ......[GAP]G..{2,12}[IMLV][IMLV]....

		if (width == 0) {
			this.width = new StringBuffer(
					"aaaatataaaaggtctagcagtggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgatgctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattcataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (width == 1) {
			this.width = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (width == 2) {
			this.width = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggtgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcacgtattgaaaccgcgacccgtcgtgtgggcgagtttggctgctccaaaaaaccgaatttaaccgcgattctgtgcgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*
		// ..[RKH][R]......S[KRH]..
		if (height == 0) {
			this.height = new StringBuffer(
					"aaaatataaaaggtctagcagtggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgatgctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattcataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (height == 1) {
			this.height = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggcgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcccgtattgaaaccgcgacccgtcgtgtgggcgtgtttggctgctccaaaaaaccgaattaaaccgcgattctgtccgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		if (height == 2) {
			this.height = new StringBuffer(
					"aaatataaaaggtctagcaatggtgggtgtgtttggctgctccaaaaaaccaaattataccgcgattctgtccgataccgtgaatgtggcgtcacgtattgaaaccgcgacccgtcgtgtgggcgagtttggctgctccaaaaaaccgaatttaaccgcgattctgtgcgataccctgaatctggcgtcccgtattgataccgcgacctaacagttccgaatactcaccgtctgtcagctcttgg");
		}
		// MVGVFGCSKKPNYTAILSDTVNVASRIETAT[RR]VGVFGCSKKPNYTAI[L]SDTVNVASRIETAT*

		if (sense == 0) {
			this.sense = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaactgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattctgaaagtgtgtatcaagtttggagatctgtccaatccatgcaggccaatagaaataagcacac");
		}
		if (sense == 1) {
			this.sense = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagtttattgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataaggatttaattattaaaatacttgagcaccacaatgattcggagattgctttgctcagatggtacattctgaaagtgtgtatcaagtttggagatctgtccaatccatgcaggccaatagaaataagcacac");
		}
		// MLSDK[M]PLTHLLKLLEQFF[EI]VCDETEKHSGK*"
		// "[M].{4,13}[IV][EV]"

		if (optimalTemp == 0) {
			toleranceTemp = new StringBuffer(
					"aaatataaaaggtctagccatgctgtccaccgtgtattgctccctgatttccgaatccccgaaacatccgtttctgtgcgtgtctaaagcgatgcgtcagtataacgaatccgattattcccgtctgattaccgaacagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggacctactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		if (optimalTemp == 1) {
			toleranceTemp = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccaccgtgtattgctccctgatttccgaatccccgaaacatccgtttctgtgcgtgtataaagcgatgcgtcagtataaagaatccgattattcccgtctgattaccgaacagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggagatactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		if (optimalTemp == 2) {
			toleranceTemp = new StringBuffer(
					"aaatataaaaagtctagcaatgctgtccaccgtgtattgctacctgatttccgaatccccgaaacatccgtttctgtgcgtgtataaagcgatgcgtcagtataaagagtccgattattcccgtctgattaccgaatagccgcgtcagtaatgggtttgaatgtatgctataagtatttgctgcgtggaaagctaatggagatactccctcagcttatccatttcacccacaagagcactgagtccgaatac");
		}
		// MLSTVYCSLISESPKHPFLCVYKAMRQYKESDYSRLITEQPRQ*
		// ...[VYS]..........[HPF].......

		if (pred == 0) {
			predator = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctgctgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataagatcttggccacacagggatcgacaatctcttctgtattaatacggagaatgccttagccctcctctacaacgacgaggcgcccttagagcatgcccatgcaacgctgtcatggcacatcatcacac");
		}
		if (pred == 1) {
			predator = new StringBuffer(
					"aaatataaaaggtctagcaatgctgtccgataaaatgccgctgacccatctggtgaaactgctggaacagttttttgaaattgtgtgcgatgaaaccgaaaaacattccggcaaataagatcttggccacacatggatcgacaatctcttctgtattaatacagagaatgccttagcgcttctctacaacgacgaggcgcccttagagcatgcccatgcaacgctgtcatggcacatcatcacac");
		}
		// MLSDK[M]PLTHL[LK]LLEQFFEIVCDETEKHSGK*"
		// .....[M].{4,7}[MIV][KRH]..

	}

	public Genome(World world, int speedG, int huntG, int escape, int radius,
				  int phero, int parteNo, int fill) {// int fill lo agrego para
		// que no me de error por
		// tener dos metodos
		// duplicados

		this.world = world;

		protein = new StringBuffer();

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

		if (huntG == 0) {
			hunt = new StringBuffer(
					"aaatatgaaaggtctagcaatgatttccaccgaagcgaaagaatccgaactggcgtcctatctttatccggcgctgttcgaaaccgtgatggataattccgatgaacatcgtagccagggccatgcgctgctgattaatgattccgaagcgaatcgtgcgacctttaccgaatccatgtaaggaattaattattaaaatacttgagcaccacaatgattcggacattgctttgctcagatggtacattc");
		}
		if (huntG == 1) {
			hunt = new StringBuffer(
					"aaatataaaaggtctagcaatgatttccaccgaagcgaaagaatccgaactggcgtcctatcgttatccggcgctgtccgaaaccgtgatggataattccgatgaacatcgtacccagggccatccgctgctgatttatgattccgaagcgaatcgtgcgtcctttaccgaatccatgtaaggatttaattattaaaatacttgagccccacaatgattcggagattgctttgctcagatggtacattc");
		}
		// MISTEAKESELASYRY[PAL]SETVMDNSDEHRTQG[HPL]LINDSEANRATFTESM*"
		// ..[PAG][A][L].{4,10}[HKR]...

		if (escape == 0) {
			this.escape = new StringBuffer(
					"aaatataaaaggtctagcaatggaaattgcgattcagatgctgaccgaaaaaaccatgctgtataaagaagcgctgttttgctataccgtgccggaagtgattcaggaaattgatcgttccctggataattccctgaaatcctaaggtatgcagtggccctgatgaacgagttctggtcactgggtgaccttatgctagagtgtggtctggagccagacaagatcaagacccgtccacagaaaggtgaag");
		}
		if (escape == 1) {
			this.escape = new StringBuffer(
					"aaatataaaaggtctagcaatgaacattgcgattcagatgctgaccgtaaaaaccatgctgtataaagaagcgctgttttgctataccgtgccggaagtgattcaggaaattgatcgttgcctggataattccctgaaatcctaaggtatgcagtggccctgatgaacgagttctggtgacttggtgaccttatggtagagtgtggtctcgagccagacaagatcaagacccgtccatagaaaggtgaag");
		}

		// MEIAI[Q]MLTEKTMLYKEAL[FC]YTVPEVIQEIDRSLDNSLKS*"
		// .....[QE].{2,15}[RKH][C]......

		if (radius == 0) {
			this.radius = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgatcattatgttttgcgatatttaaggctttacctccctgtccgataaaatgccgctgtcccatctcctgtaacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacatacaagcgaatctaaatgcaaccgtcg");
		}
		if (radius == 1) {
			this.radius = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgaccattatgttttgcgatattaaaggctttacctccctgtccgataaaatgccgctgacccatctgctgtaacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacttacaagcgaatctaaatgcaaccgtcg");
		}
		if (radius == 2) {
			this.radius = new StringBuffer(
					"aaatataaaaggtctagcaatggaagcgctgaaatcccatgcgaccgtgcgttttgcgaccattatgttttgcgatattaaaggctttacctccctgtccgataaaatgccgctgacccatctgctgttacgcaaattggatttacgcagagcatcgtgaagggcttctggaccgttgtggagcggttttggaaagccttggcaggtgtagagtttagcgacttacaagcgaatctaaatgcaaccgtcg");
		}

		// MEALKSHATVRF[A]TI[MF]CDIKGFTSLSDKMPLTHLL*
		// ..[APTS]..[MILV]F

		if (phero == 0) {
			pheromone = new StringBuffer(
					"tggccagtcctaatcacgagtatctcttcaagcttttggtcattggagactctggagtgggcaaatccgctcttcttctgcgcctgtgtgataagatctttaatgcgtcgtacattacgacgataggtgtagacttcaaggtcaaatcgctaaatataaaggacaacactgttaaattacaaatatgggacacggccggccaagagaagttccggaccattacttctacgtactaccggtga");
		}
		if (phero == 1) {
			pheromone = new StringBuffer(
					"aaatataaaaggtctagcaatggccagtcctaatcacgagtatctattcaagcttttggtcattggagactctggagtgggcaaatccgctcttcttctgcgcctgtgtgataagatctttaatgcgtagtacattacgacgataggtgtagacttcaaggtcaaatcgctaaatataaaggacaacactgttaaattacaaatatgggacacggccggccaagagaagttccggaccattacttctacgtactaccggtga");
		}
		if (phero == 2) {
			pheromone = new StringBuffer(
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

	public Genome(World world, int mutationRate, int longevity, int color) {

		this.world = world;

		protein = new StringBuffer();

		if (mutationRate == 0) {
			this.mutationRate = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgtaagaagattttctgaaaccgccggaacgtacttttcataaaatttatattcagtaaataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}
		if (mutationRate == 1) {
			this.mutationRate = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgaaagaagattttctgaaaccgccggaacgtatttttcataaaatttatattcagtaaataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}
		if (mutationRate == 2) {
			this.mutationRate = new StringBuffer(
					"aaatataaaaggtctagcaatggatgaaaatgaaaaacaggaacgtctgctgatgtccctgctgccgcgtaatgtggcgatggaaatgaaagaagattttctgaaaccgccggaacgtatttttcataaaatttatattcagtatataacacatattatagactcgaccaatctggagaacttgtacttactcttcagcagcgcattctccaggctgtgttaaacatagcggtcatacgcaccttggaata");
		}

		// MDENEKQERLL[M]S[LL]PRN[V]AMEMKEDFLKPPERIFHKIYIQ*
		// .M.[LMIV][LMI]...[VLI]..
		if (longevity == 0) {
			this.longevity = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgtccattccgaattttaattaattttatattgaactgtatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longevity == 1) {
			this.longevity = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgtccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longevity == 2) {
			this.longevity = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaatccgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttgcgaccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaatataaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagctgattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}
		if (longevity == 3) {
			this.longevity = new StringBuffer(
					"aaatataaaaggtctagcaatgtccaattcgcgtaatatggatctgtattatcagtcctattcccaggtgggcgtgatgtttggtccattccgaattttaatgatttttatattgaactggatggcaataatatgggcgtggaataaaaagctgaagaagaccatcgtggatggcctgcctgactattcgccagttgcagcagattgcgagcccacatatcaaagcgtgtggaagcaactaaatgagagta");
		}

		// MMSNPRNM[DL]YYQSYS[Q]VGVM[F]ASIPNFNDFYIELDGNNMGVE*
		// .....[DN][LMIV]......[QE]....[FY]

		if (color == 0) {
			this.color = new StringBuffer(
					"aaatatataaggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgatcatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatac");
		}// a0
		if (color == 1) {
			this.color = new StringBuffer(
					"aaatataaagggtctagcaaagctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatcc");
		}// a256
		if (color == 2) {
			this.color = new StringBuffer(
					"aaatataatgggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatttattacccagtccgcgtgttttcataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaataaatttggtccttttgcaatcgtaccacctcctgcatgaccacctaatcgatgtcagtgccatgaatacatatac");
		}// a452
		if (color == 3) {
			this.color = new StringBuffer(
					"aaatataaaaggtctagcaatgctgctgcgtattctgcagctgacccagtttaccgaatatattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgactacctaatagttgtcagtgccatgaatacatatac");
		}// a588
		if (color == 4) {
			this.color = new StringBuffer(
					"aaatataaaagttctagcaatgctgctgcgtattctgcagctgacccaatttaacgaatttattacccagtccgcgtatttttataccattccgccggtggcggtggtgattatgattctgcgttccatgtatcgtcgtctgattctgattttttcccagaatgtggaagaaatttggtccttttgcaatcgtaccacctcctgcatgaccacccaatcgatgtcggtgccatgaatacatatac");
		}// a700
		// MLLRILQLTQFTEFITQSAYF[YT]IPPVAVVIMILRSMYRRL[ILI]FSQNVEEIWSFCNRTTSCMTT*

	}

	public StringBuffer collectGenome(World world) {
		StringBuffer line = new StringBuffer();

		if (world.collectResistance) {
			line.append(resistanceATB);
		}
		if (world.collectWidth) {
			line.append(width);
		}
		if (world.collectHeight) {
			line.append(height);
		}
		if (world.collectSpeed) {
			line.append(speed);
		}
		if (world.collectSense) {
			line.append(sense);
		}
		if (world.collectPredator) {
			line.append(predator);
		}
		if (world.collectMutationRate) {
			line.append(mutationRate);
		}
		if (world.collectHunt) {
			line.append(hunt);
		}
		if (world.collectEscape) {
			line.append(escape);
		}
		if (world.collectRadius) {
			line.append(radius);
		}
		if (world.collectPheromone) {
			line.append(pheromone);
		}
		if (world.colectarParteNoGen) {
			line.append(parteNoGen);
		}
		if (world.collectLongevity) {
			line.append(longevity);
		}
		if (world.collectTemp) {
			line.append(toleranceTemp);
		}
		if (world.collectColor) {
			line.append(color);
		}
		line.append("\n");

		return line;

	}

	public StringBuffer collectProteome(World world) {

		StringBuffer line = new StringBuffer();

		if (world.collectResistance) {
			line.append(translateProtein(resistanceATB));
		}
		if (world.collectWidth) {
			line.append(translateProtein(width));
		}
		if (world.collectHeight) {
			line.append(translateProtein(height));
		}
		if (world.collectSpeed) {
			line.append(translateProtein(speed));
		}
		if (world.collectSense) {
			line.append(translateProtein(sense));
		}
		if (world.collectPredator) {
			line.append(translateProtein(predator));
		}
		if (world.collectMutationRate) {
			line.append(translateProtein(mutationRate));
		}
		if (world.collectHunt) {
			line.append(translateProtein(hunt));
		}
		if (world.collectEscape) {
			line.append(translateProtein(escape));
		}
		if (world.collectRadius) {
			line.append(translateProtein(radius));
		}
		if (world.collectPheromone) {
			line.append(translateProtein(pheromone));
		}
		if (world.colectarParteNoGen) {
			line.append(translateProtein(parteNoGen));
		}
		if (world.collectLongevity) {
			line.append(translateProtein(longevity));
		}
		if (world.collectTemp) {
			line.append(translateProtein(toleranceTemp));
		}

		if (world.collectColor) {
			line.append(translateProtein(color));
		}
		line.append("\n");

		return line;
	}

	public int promoterValue(StringBuffer sb) {

		promoter = 0;

		// detectar promotor TATA

		tataPos = -1;
		Pattern p = Pattern.compile("[acgt][at]t[at]a[at][ag]....[at]");
		Matcher m = p.matcher(sb);
		if (m.find()) {
			tataPos = m.start();
		}

		// obtener el valos del promotor
		if (tataPos != -1 && tataPos < 12) {

			for (int e = tataPos; e < tataPos + 12; e++) {
				// obtenemos la letra de una posicion determinada
				base = sb.charAt(e);
				sum = 0;
				switch (base) {
					case 'a':
						sum = 1;
					case 'c':
						sum = 0;
					case 'g':
						sum = 0;
					case 't':
						sum = 1;
				}

				promoter = promoter + sum;

			}
		}

		if (promoter < 0) {
			promoter = 0;
		}

		return promoter;
	}

	public int scoreAA(char c) {
		switch (c) {
			case 'C':
				return 0;
			case 'S':
				return 1;
			case 'T':
				return 2;
			case 'P':
				return 3;
			case 'A':
				return 4;
			case 'G':
				return 5;
			case 'N':
				return 6;
			case 'D':
				return 7;
			case 'E':
				return 8;
			case 'Q':
				return 9;
			case 'H':
				return 10;
			case 'R':
				return 11;
			case 'K':
				return 12;
			case 'M':
				return 13;
			case 'I':
				return 14;
			case 'L':
				return 15;
			case 'V':
				return 16;
			case 'F':
				return 17;
			case 'Y':
				return 18;
			case 'W':
				return 19;
		}
		return -1;
	}

	public float translateMagnitude(StringBuffer strB, String str) {

		promoter = promoterValue(strB);// valor del promotor
		sum = 0;
		protein = translateProtein(strB);// secuencia de la proteina

		Pattern p = Pattern.compile(str);// tiene que encontrar esta condición
		Matcher m = p.matcher(protein);
		if (m.find()) {// detectar la secuencia conservada

			// si esta está, obtener el valor de la proteina
			for (int i = 0; i < protein.length(); i++) {
				// System.out.println("promotor "+ promotor+ "suma"+ suma);
				sum += protein.charAt(i);
			}
		}

		return (float) promoter * sum;
	}

	public boolean translateBoolean(StringBuffer strb, String str) {
		int promoter = promoterValue(strb);// valor del promoter
		// float protV=0;
		protein = translateProtein(strb);// secuencia de la proteina

		Pattern p = Pattern.compile(str);// si encuentra esta condicion se hace
		// predador
		Matcher m = p.matcher(protein);
		if (m.find()) {
			return true;
		} else if (promoter == 0) {
			return false;
		} else {
			return false;
		}
	}

	public float translateColor(String str1, String str2) {

		float r = 0;
		float g = 0;
		float b = 0;

		float promoter = promoterValue(color);// valor del promotor

		protein = translateProtein(color);// secuencia de la proteina

		int a = protein.indexOf(str1);// detectar la secuencia conservada
		int a2 = protein.indexOf(str2);// detectar la segunda secuencia
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

				amino = protein.charAt(i);

				r = r + scoreAA(amino);
			}
		}

		// obtener el color verde
		if (a != -1 && a2 != -1 && a < a2) {

			for (int i = a; i < a2; i++) {

				amino = protein.charAt(i);

				g = g + scoreAA(amino);
			}
		}

		// obtener el color azul
		if (a != -1 && a2 != -1 && a < a2) {

			for (int i = a2; i < protein.length(); i++) {

				amino = protein.charAt(i);

				b = b + scoreAA(amino);
			}
		}

		if (promoter == 0) {
			r = 0;
			g = 0;
			b = 0;
		} else if (promoter > 0) {
			r = promoter * r;
			g = promoter * g;
			b = promoter * b;
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

	public StringBuffer translateProtein(StringBuffer str) {// lee el adn y asigna
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

		protein.delete(0, protein.length());

		if (tataPos != -1 && atgPos != -1 && tataPos < atgPos && tataPos < 12) {

			for (int e = atgPos; e <= str.length() - 3; e = e + 3) {
				codon = str.substring(e, e + 3);
				String aa = "";
				switch (codon) {
					case "aaa":
						aa = "K";
						break;
					case "aat":
						aa = "N";
						break;
					case "aac":
						aa = "N";
						break;
					case "aag":
						aa = "K";
						break;
					case "ata":
						aa = "I";
						break;
					case "att":
						aa = "I";
						break;
					case "atc":
						aa = "I";
						break;
					case "atg":
						aa = "M";
						break;
					case "aca":
						aa = "T";
						break;
					case "act":
						aa = "T";
						break;
					case "acc":
						aa = "T";
						break;
					case "acg":
						aa = "T";
						break;
					case "aga":
						aa = "R";
						break;
					case "agt":
						aa = "S";
						break;
					case "agc":
						aa = "S";
						break;
					case "agg":
						aa = "R";
						break;

					case "taa":
						return protein;
					case "tat":
						aa = "Y";
						break;
					case "tac":
						aa = "Y";
						break;
					case "tag":
						return protein;
					case "tta":
						aa = "L";
						break;
					case "ttt":
						aa = "F";
						break;
					case "ttc":
						aa = "F";
						break;
					case "ttg":
						aa = "L";
						break;
					case "tca":
						aa = "S";
						break;
					case "tct":
						aa = "S";
						break;
					case "tcc":
						aa = "S";
						break;
					case "tcg":
						aa = "S";
						break;
					case "tga":
						return protein;
					case "tgt":
						aa = "C";
						break;
					case "tgc":
						aa = "C";
						break;
					case "tgg":
						aa = "W";
						break;

					case "caa":
						aa = "Q";
						break;
					case "cat":
						aa = "H";
						break;
					case "cac":
						aa = "H";
						break;
					case "cag":
						aa = "Q";
						break;
					case "cta":
						aa = "L";
						break;
					case "ctt":
						aa = "L";
						break;
					case "ctc":
						aa = "L";
						break;
					case "ctg":
						aa = "L";
						break;
					case "cca":
						aa = "P";
						break;
					case "cct":
						aa = "P";
						break;
					case "ccc":
						aa = "P";
						break;
					case "ccg":
						aa = "P";
						break;
					case "cga":
						aa = "R";
						break;
					case "cgt":
						aa = "R";
						break;
					case "cgc":
						aa = "R";
						break;
					case "cgg":
						aa = "R";
						break;

					case "gaa":
						aa = "E";
						break;
					case "gat":
						aa = "D";
						break;
					case "gac":
						aa = "D";
						break;
					case "gag":
						aa = "E";
						break;
					case "gta":
						aa = "V";
						break;
					case "gtt":
						aa = "V";
						break;
					case "gtc":
						aa = "V";
						break;
					case "gtg":
						aa = "V";
						break;
					case "gca":
						aa = "A";
						break;
					case "gct":
						aa = "A";
						break;
					case "gcc":
						aa = "A";
						break;
					case "gcg":
						aa = "A";
						break;
					case "gga":
						aa = "G";
						break;
					case "ggt":
						aa = "G";
						break;
					case "ggc":
						aa = "G";
						break;
					case "ggg":
						aa = "G";
						break;
				}
				protein.append(aa);
			}
		}
		return protein;
	}
}
