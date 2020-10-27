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

public class Texto {

	MenuInicio mi;
	// texto del Menu inicio

	String errorEscribir = "error al escribir el archivo evo_star.conf";
	String errorLectura = "error en la lectura del archivo evo_star.conf \n se usará la configuración por defecto";
	String nombre = "Nombre";
	String simuladorDigital = "Simulador digital de evolución";
	String parametrosEnergiaMasa = "Paramentros de Energía y Masa";
	String soloNenteros = "(Solo números enteros)";
	String otrosParamentros = "Otros parametros";
	String directorioDeTrabajo = "Directorio de Trabajo: ";
	String valorEnergia = "Valor energía";
	String valorBiomasa = "Valor biomasa";
	String cantidadEnergia = "Cantidad energía";
	String cantidadBiomasa = "Cantidad biomasa";
	String tiempoEntreMuestras = "Tiempo entre muestras (seg)";
	String tiempoEntreCatastrofes = "Tiempo entre catástrofes (seg, 0 = inactivado)";
	String tiempoATB = "Tiempo de ATB                 (seg, 0 = inactivado)";
	String tiempoPartida = "Tiempo de la partida         (seg, 0 = ilimitado)";
	String tiempo = "Tiempo";
	String numeroInOrganismos = "Número Inicial de Organismos (L1)";
	String numeroInOrganismos2 = "Número Inicial de Organismos (L2)";
	String numeroMaximoOrg = "Número Maximo de Organismos";
	String temperaturaInicial = "Temperatura inicial (grados centigrados)";
	String grados = "grados centigrados";
	String modificacionMambiente = "Modificación del medio ambiente";
	String comenzar2 = "Comenzar*       Temp. final           Delta tiempo";
	String UnominGradosMin = "1)                min                     grados                   min  ";
	String DosminGradosMin = "2)                min                     grados                   min  ";
	String funcionInactivada = "* 0 = función incativada";
	String marcarGenesMutaran = "Marcar los genes que se mutarán";
	String marcarMutAnalizar = "Marcar los alelos a analizar";
	String multiploADNpol = "Múltiplo que afecta la eficiencia de la ADN-pol";
	String horizontalTransferRate = "Frecuencia de la transferencia horizontal (0= Sin transferencia)";
	String comenzar = "COMENZAR";
	String sobreEvolizion = "Sobre EvoluZion";
	String directorioTrabajo2 = "Directorio de trabajo";
	String cargaPoblacion = "Cargar Población";
	String cargar = "Cargar";
	String salir = "SALIR";
	String moverLaMasa = "Mover la Masa";
	String cargarArchivo = "Cargar Archivo: ";
	String color = "Color";
	String tamano = "Tamaño";
	String alto = "alto";
	String ancho = "ancho";
	String cantidad = "Cantidad";
	String velocidad = "Velocidad";
	String alas = "Alas";
	String sentidos = "Antenas";
	String buscarComida = "Ojos azules";
	String escapar = "Escapar";
	String alcanceVisual = "Alcance Visual";
	String genPredador = "Gen Predador";
	String fidelidadADNpol = "Fid. ADNpol";
	String longevidad = "Longevidad";
	String temOptima = "Temp. Óptima";
	String ResATB = "R.Tox";
	String feromona = "Feromona";
	String fer = "Fero";
	String partenogenesis = "Partenogénesis";
	String partenogen = "Partenogén";
	String fenotipo = "Fenotipo";
	String alelo = "Alelo";
	String secuencia = "Secuancia";
	String porcentage = "Porcentaje";
	String tipo = "tipo";

	String losFenotipopresentes = "Los fenotipos presentes en cada momento \n están expresados como un valor numérico: ";
	String losGenotipopresentes = "Los genotipos presentes en cada momento \n están expresados como un valor numérico: ";
	String mensajeSobre = "Evoluzion: simulador digital de evolución \n\n"
			+ "Autor: Adolfo R, Zurita \n\n"
			+ "Investigador Asistente de Conicet \n"
			+ "Profesor adjunto de Genética e introducción a la biología molecular\n"
			+ "Universidad Nacional de San Luis, San Luis, Argentina \n\n"
			+ "azurita1974@gmail.com     \n\n"
			+ "Copyright 2014 Adolfo R. Zurita";
	String agregarDespues = "Agregar /. despues del directorio seleccionado";
	String poblacion = "población";
	String directorio = "Directorio";
	String si = "Si";
	String no = "No";
	String deseaSalir = "¿Desea salir del programa?";
	String cuandoEstenPresentes = "Los genes (cuando están presentes) aparecen en el siguiente orden: \n ";
	String genomasTXT = "_Genomas.txt";
	String datosXls = "_Datos_.csv";
	String datosOrdenados = "Tiempo,Organismos,Predadores,Res.ATB,Vel Media, Tam. Medio,Long Media, Fidelidad ADNpol Media, Temperatura, Temp. Optima Media   \n";
	String datosOrdenados2 = "Tiempo,Organismos(I),Organismos(D),Predadores(I),Predadores(D),Res.Tx(I),Res.Tox(D),Vel Media(I),Vel Media(D), Tam. Medio(I), Tam. Medio(D),Long. Media(I),Long. Media(D), Fidelidad ADNpol Media(I), Fidelidad ADNpol Media(D), Temperatura,Temp. Optima Media(I), Temp. Optima Media(D)   \n";

	String proteomaTXT = "_Proteoma.txt";
	String cuandoEstenPresntesProt = "Las proteínas (cuando están presentes) aparecen en el siguiente orden: \n";
	String arbolTXT = "_Arbol.txt";
	String fenomaTXT = "_Fenotipo.csv";
	String mutantesXLS = "_Mutantes.csv";
	String alelosXLS = "_Alelos.csv";
	String genotipoXLS = "_Genotipo.csv";

	String idioma = "Set to english";
	String losCambiosReiniciar = "Algunos cambios se completarán luego de reiniciar";

	String sexo = "sexo";
	String macho = "macho";
	String hembra = "hembra";
	String ventanalellenda = "Seleccione el genotipo deseado para su organismo y oprima OK \n\n >";
	String verdadero = "verdadero";
	String falso = "falso";
	String genotipo = "Genotipo";
	String cancelar = "Cancelar";
	String modificar = "modificar";
	String cepa = "cepa";
	String predador = "predator";
	String modificado = "modificado";
	String restaurar = "Restaurar";
	String mapaGenomico = "Mapa Genómico* (distancias en centiMorgans)";
	String cadaCamporepresentaa = "*Cada campo de texto representa la distancia relativa entre genes,";
	String expresadosEnUnidades = " expresados en unidades de recombinación o centiMorgan.";
	String genesLigados = " 0 = genes ligados; 50 o más = genes no ligados. Los machos solo tienen una copia de cromosoma 3 (C3)";

	String blanco = "blanco";
	String verde = "verde";
	String celeste = "celeste";
	String naranja = "naranja";
	String azul = "azul";
	String pequeño = "pequeño";
	String mediano = "mediano";
	String grande = "grande";
	String panelDer = "Panel derecho";
	String panelIz = "Panel izquierdo";
	String verFrontera = "Crear Frontera";
	String frontera = "Frontera";
	String verMachoHembra = "Ver M/H";
	String nada = "Nada";
	String normal = "Normal";
	String fuerte = "Fuerte";
	String todo = "Todo";
	String nada2 = "Nada";

	// Texto de la pantalla

	String Organismos = "Organismos : ";
	String mutantes = "Mutantes      : ";
	String masaTotal = "Masa Total  : ";
	String masa = "Masa           : ";
	String biomasa = "Biomasa      : ";
	String velocidadMedia = "Vel Media    : ";
	String tamanoMedi = "Tam Medio    : ";
	String tasaMutacionMedia = "T. Mut. Media: 1/";
	String vidaMdia = "Vida Media  : ";
	String resistensiaATB = "Res. Toxinas: ";
	String temperatura = "Temperatura  : ";
	String temOptimaMedia = "T.Óptima Med.: ";
	String antibioticoON = "Toxinas ON";
	String antibioticoOFF = "Toxinas OFF";
	String guardarYcerrar = "Guardar y Cerrar";
	String playPausa = "Play/Pausa";
	String verOcultar = "Ver/ocultar";
	String menuGuardar = "Menú Guardar";
	String tomarMuestra = "Tomar Muestra";
	String guardarTodos = "Guardar Todos";
	String guardarMarcados = "Guardar Marcados";
	String guardarNoMarcados = "Guardar No Marcados";
	String marcarDesmarcar = "Marcar/Desmarcar";
	String antibiotico = "Toxinas!!!";
	String catastrofe = "Catástrofe!!!";
	String verEnergia = "Ver Energía";
	String verMasa = "Ver Masa";
	String verOrgansimo = "Ver Org.";
	String terminarGuardarMensaje = "Terminar simulación, se guardarán los datos colectados";
	String terminarMensaje = "Terminar simulación";
	String simulacionEnPausa = "Simulación en Pausa.";
	String oprimaPlayPausa = "Oprima Play/Pausa para continuar";

	String verAlelos = "Ver Alelo";
	String verDatos = "Ver Datos";
	String ordenar = "Ordenar";
	String Siguiente = "Siguiente";
	String parar = "Parar";

	String verFenotipo = "Ver Fenotipo";

	String verGenotipo = "Ver Genotipo";

	public Texto() {

	}

	public void setEspanol() {

		errorEscribir = "error al escribir el archivo evo_star.conf";
		errorLectura = "error en la lectura del archivo evo_star.conf \n se usará la configuración por defecto";
		nombre = "Nombre";
		simuladorDigital = "Simulador digital de evolución";
		parametrosEnergiaMasa = "Paramentros de Energía y Masa";
		soloNenteros = "(Solo números enteros)";
		otrosParamentros = "Otros parametros";
		directorioDeTrabajo = "Directorio de Trabajo: ";
		valorEnergia = "Valor energía";
		valorBiomasa = "Valor biomasa";
		cantidadEnergia = "Cantidad energía";
		cantidadBiomasa = "Cantidad biomasa";
		tiempoEntreMuestras = "Tiempo entre muestras (seg)";
		tiempoEntreCatastrofes = "Tiempo entre catástrofes (seg, 0 = inactivado)";
		tiempoATB = "Tiempo de Toxinas           (seg, 0 = inactivado)";
		tiempoPartida = "Tiempo de la partida         (seg, 0 = ilimitado)";
		tiempo = "Tiempo";
		numeroInOrganismos = "Número Inicial de Organismos (L1)";
		numeroInOrganismos2 = "Número Inicial de Organismos (L2)";
		numeroMaximoOrg = "Número Maximo de Organismos";
		temperaturaInicial = "Temperatura inicial (grados centigrados)";
		grados = "grados centigrados";
		modificacionMambiente = "Modificación del medio ambiente";
		comenzar2 = "Comenzar*       Temp. final           Delta tiempo";
		UnominGradosMin = "1)                min                     grados                   min  ";
		DosminGradosMin = "2)                min                     grados                   min  ";
		funcionInactivada = "* 0 = función incativada";
		marcarGenesMutaran = "Marcar los genes que se mutarán";
		marcarMutAnalizar = "Marcar los alelos a analizar";
		multiploADNpol = "Múltiplo que afecta la eficiencia de la ADN-pol";
		horizontalTransferRate = "Frecuencia de la transferencia horizontal (0= Sin transferencia)";
		comenzar = "COMENZAR";
		sobreEvolizion = "Sobre EvoluZion";
		directorioTrabajo2 = "Directorio de trabajo";
		cargaPoblacion = "Cargar Población";
		cargar = "Cargar";
		salir = "SALIR";
		moverLaMasa = "Mover la Masa";
		cargarArchivo = "Cargar Archivo: ";
		color = "Color";
		tamano = "Tamaño";
		alto = "alto";
		ancho = "ancho";
		cantidad = "Cantidad";
		velocidad = "Velocidad";
		alas = "Alas";
		sentidos = "Antenas";
		buscarComida = "Ojos azules";
		escapar = "Ojos amarillos";
		alcanceVisual = "Alcance Visual";
		genPredador = "Gen Predador";
		fidelidadADNpol = "Fid. ADNpol";
		longevidad = "Longevidad";
		temOptima = "Temp. Óptima";
		ResATB = "R.Tox";
		feromona = "Feromona";
		fer = "Fero";
		partenogenesis = "Partenogén";
		partenogen = "Partenogén";
		fenotipo = "Fenotipo";
		alelo = "Alelo";
		secuencia = "Secuancia";
		porcentage = "Porcentaje";
		tipo = "Tipo";
		losFenotipopresentes = "Los fenotipos presentes en cada momento \n están expresados como un valor numérico: ";
		losGenotipopresentes = "Los genotipos presentes en cada momento \n están expresados como un valor numérico: ";
		mensajeSobre = "Evoluzion: simulador digital de evolución \n\n"
				+ "Autor: Adolfo R, Zurita \n\n"
				+ "Investigador Asistente de Conicet \n"
				+ "Profesor adjunto de Genética e introducción a la biología molecular\n"
				+ "Universidad Nacional de San Luis, San Luis, Argentina \n\n"
				+ "azurita1974@gmail.com     \n\n"
				+ "Copyright 2014 Adolfo R. Zurita";
		agregarDespues = "Agregar /. despues del directorio seleccionado";
		poblacion = "población";
		directorio = "Directorio";
		si = "Si";
		no = "No";
		deseaSalir = "¿Desea salir del programa?";
		cuandoEstenPresentes = "Los genes (cuando están presentes) aparecen en el siguiente orden: \n ";
		genomasTXT = "_Genomas.txt";
		datosXls = "_Datos_.csv";
		datosOrdenados = "Tiempo;Organismos;Predadores;Res.ATB;Vel Media; Tam. Medio;Long Media; Fidelidad ADNpol Media; Temperatura; Temp. Óptima Media   \n";
		datosOrdenados2 = "Tiempo;Organismos(I);Organismos(D);Predadores(I);Predadores(D);Res.Tx(I);Res.Tox(D);Vel Media(I);Vel Media(D); Tam. Medio(I); Tam. Medio(D);Long. Media(I);Long. Media(D); Fidelidad ADNpol Media(I); Fidelidad ADNpol Media(D); Temperatura;Temp. Optima Media(I); Temp. Optima Media(D)   \n";
		proteomaTXT = "_Proteoma.txt";
		cuandoEstenPresntesProt = "Las proteínas (cuando están presentes) aparecen en el siguiente orden: \n";
		arbolTXT = "_Arbol.txt";
		fenomaTXT = "_Fenotipo.csv";
		mutantesXLS = "_Mutantes.csv";
		alelosXLS = "_Alelos.csv";
		genotipoXLS = "_Genotipo.csv";
		idioma = "Set to english";
		losCambiosReiniciar = "Algunos cambios se completarán luego de reiniciar";

		sexo = "sexo";
		macho = "macho";
		hembra = "hembra";
		ventanalellenda = "Seleccione el genotipo deseado para su organismo y oprima OK \n\n >";
		verdadero = "verdadero";
		falso = "falso";
		genotipo = "Genotipo";
		cancelar = "Cancelar";
		modificar = "modificar";
		cepa = "cepa";
		predador = "predador";
		modificado = "modificado";
		restaurar = "Restaurar";
		mapaGenomico = "Mapa Genómico* (distancias en centiMorgans)";
		cadaCamporepresentaa = "*Cada campo de texto representa la distancia relativa entre genes,";
		expresadosEnUnidades = " expresados en unidades de recombinación o centiMorgan.";
		genesLigados = " 0 = genes ligados; 50 o más = genes no ligados. Los machos solo tienen una copia de cromosoma 3 (C3)";

		blanco = "blanco";
		verde = "verde";
		celeste = "celeste";
		naranja = "naranja";
		azul = "azul";
		pequeño = "pequeño";
		mediano = "mediano";
		grande = "grande";
		panelDer = "Panel derecho";
		panelIz = "Panel izquierdo";
		verFrontera = "Crear frontera";
		frontera = "Frontera";
		verMachoHembra = "Ver M/H";
		nada = "Nada";
		normal = "Normal";
		fuerte = "Fuerte";
		todo = "Todo";
		nada2 = "Nada";

		// de la pantalla

		Organismos = "Organismos : ";
		mutantes = "Mutantes      : ";
		masaTotal = "Masa Total  : ";
		masa = "Masa           : ";
		biomasa = "Biomasa      : ";
		velocidadMedia = "Vel Media    : ";
		tamanoMedi = "Tam Medio    : ";
		tasaMutacionMedia = "T. Mut. Media: 1/";
		vidaMdia = "Vida Media  : ";
		resistensiaATB = "Res. Toxinas: ";
		temperatura = "Temperatura  : ";
		temOptimaMedia = "T.Óptima Med.: ";
		antibioticoON = "Toxinas ON";
		antibioticoOFF = "Toxinas OFF";
		guardarYcerrar = "Guardar y Cerrar";
		playPausa = "Play/Pausa";
		verOcultar = "Ver/ocultar";
		menuGuardar = "Menú Guardar";
		tomarMuestra = "Tomar Muestra";
		guardarTodos = "Guardar Todos";
		guardarMarcados = "Guardar Marcados";
		guardarNoMarcados = "Guardar No Marcados";
		marcarDesmarcar = "Marcar/Desmarcar";
		antibiotico = "Toxinas!!!";
		catastrofe = "Catástrofe!!!";
		verEnergia = "Ver Energía";
		verMasa = "Ver Masa";
		verAlelos = "Ver Alelos";
		verDatos = "Ver Datos";
		verFenotipo = "Ver Fenotipo";
		verGenotipo = "Ver Genotipo";
		verOrgansimo = "Ver Org.";
		terminarGuardarMensaje = "Terminar simulación, se guardarán los datos colectados";
		terminarMensaje = "Terminar simulación";
		simulacionEnPausa = "Simulación en Pausa";
		oprimaPlayPausa = "Oprima Play/Pausa para continuar";
		ordenar = "Ordenar";
		Siguiente = "Siguiente";
		parar = "Parar";

	}

	public void setIngles() {

		errorEscribir = "Error writing file evo_star.conf";
		errorLectura = "error reading the file evo_star.conf \n the original configuration will be used";
		nombre = "Name";
		simuladorDigital = "Digital simulator of evolution";
		parametrosEnergiaMasa = "Parameters of Energy and Mass";
		soloNenteros = "(Only integer numbers)";
		otrosParamentros = "Additional parameters";
		directorioDeTrabajo = "Working folder: ";
		valorEnergia = "Energy value";
		valorBiomasa = "Mass value";
		cantidadEnergia = "Amount of energy";
		cantidadBiomasa = "Amount of mass";
		tiempoEntreMuestras = "Time between samples(seg)";
		tiempoEntreCatastrofes = "Time btw catastrophes     (seg, 0 = inactivated)";
		tiempoATB = "Time of Toxines                (seg, 0 = inactivated)";
		tiempoPartida = "Running time                     (seg, 0 = unlimited)";
		tiempo = "Time";
		numeroInOrganismos = "Starting Number of Organisms";
		numeroInOrganismos2 = "Starting Number of Organisms";
		numeroMaximoOrg = "Maximum Number of Organisms";
		temperaturaInicial = "Initial temperature (Celsius degrees )";
		grados = "Celsius degrees";
		modificacionMambiente = "Environmental modification";
		comenzar2 = "Start*                Final Temp.           Delta time";
		UnominGradosMin = "1)                min                     degrees                   min  ";
		DosminGradosMin = "2)                min                     degrees                   min  ";
		funcionInactivada = "* 0 = inactivated";
		marcarGenesMutaran = "Mark genes allowed to mutate";
		marcarMutAnalizar = "Mark the alleles you want to analyse";
		multiploADNpol = "Multiple that affect DNApol efficiency";
		horizontalTransferRate = "Horizontal transfer rate  (0 = no transfer)";
		comenzar = "START";
		sobreEvolizion = "About EvoluZion";
		directorioTrabajo2 = "Working folder";
		cargaPoblacion = "Load Population";
		cargar = "Load";
		salir = "EXIT";
		moverLaMasa = "Moving the Mass";
		cargarArchivo = "Load File: ";
		color = "Color";
		tamano = "Size";
		alto = "high";
		ancho = "width";
		cantidad = "Quantity";
		velocidad = "Speed";
		alas = "Wings";
		sentidos = "Antennas";
		buscarComida = "Blue eyes";
		escapar = "Yellow eyes";
		alcanceVisual = "Visual Range";
		genPredador = "Predator gen";
		fidelidadADNpol = "DNApol fielity";
		longevidad = "Lifespan";
		temOptima = "Optimal temp.";
		ResATB = "Tox.R";
		feromona = "Pheromone";
		fer = "Pher";
		partenogenesis = "Parthenogenesis";
		partenogen = "Parthenogen";
		alelo = "Allele";
		secuencia = "Sequence";
		fenotipo = "Phenotipe";
		porcentage = "Percentaje";
		tipo = "Type";
		losFenotipopresentes = "The phenotypes presented in each moment \n are expressed as a numerical value. ";
		losGenotipopresentes = "The genotypes presented in each moment \n are expressed as a numerical value. ";
		mensajeSobre = "Evoluzion: digital simulator of evolution \n\n"
				+ "Autor: Adolfo R, Zurita \n\n"
				+ "Assistant Researcher of CONICET\n"
				+ "Associate Professor of Genetics and introduction to molecular biology\n"
				+ "National University of San Luis, San Luis, Argentina \n\n"
				+ "azurita1974@gmail.com     \n\n"
				+ "Copyright 2014 Adolfo R. Zurita";
		agregarDespues = "Add /. after the selcted folder";
		poblacion = "population";
		directorio = "Folder";
		si = "Yes";
		no = "No";
		deseaSalir = "Do you want to exit the program?";
		cuandoEstenPresentes = "The genes (when present) appear in the following order: \n ";
		genomasTXT = "_Genome.txt";
		datosXls = "_Data.csv";
		datosOrdenados = "Time;Organisms;Predators;Tox Res.;Avg. Speed;Avg. Size;Avg. Lifespan;Avg. ADNpol fidelity; Temperature;Avg. Optimal temp. \n";
		datosOrdenados2 = "Time;Organisms(L);Organisms(R);Predators(L);Predators(R);Tox. Res.(L);Tox. Res.(R);Avg. Speed(L);Avg. Speed(R); Avg. Size(L); Avg. Size(R);Avg. Lifespan(L);Avg. Lifespan(R); ADNpol fidelity(L); ADNpol fidelity(R); Temperature;Avg. Optimal temp.(D); Avg. Optimal temp.(R)   \n";
		proteomaTXT = "_Proteome.txt";
		cuandoEstenPresntesProt = "The proteins (when present) appear in the following order: \n";
		arbolTXT = "_Tree.txt";
		fenomaTXT = "_Phenotype.csv";
		mutantesXLS = "_Mutants.csv";
		alelosXLS = "_Alleles.csv";
		genotipoXLS = "_Genotype.csv";
		idioma = "cambiar a español";
		losCambiosReiniciar = "Some changes will be completed after restart";

		sexo = "sex";
		macho = "male";
		hembra = "female";
		ventanalellenda = "Select the desired genotype of your organism and press OK \n\n >";
		verdadero = "true";
		falso = "false";
		genotipo = "Genotype";
		cancelar = "Cancel";
		modificar = "modify";
		cepa = "strain";
		predador = "predator";
		modificado = "modified";
		restaurar = "Restore";
		mapaGenomico = "Genomic Map* (distances in centiMorgans)";

		cadaCamporepresentaa = "*Each text field represents the relative distance between genes,";
		expresadosEnUnidades = " expressed in units of recombination or centiMorgan.";
		genesLigados = " 0= Linked genes; 50 or more = Unlinked genes. Males have only one copy of chromosome 3 (C3). ";

		blanco = "white";
		verde = "green";
		celeste = "light blue";
		naranja = "orange";
		azul = "blue";
		pequeño = "small";
		mediano = "medium";
		grande = "large";
		panelDer = "Right panel";
		panelIz = "       Left panel";
		verFrontera = "Create border";
		frontera = "Border";
		verMachoHembra = "See M/F";
		nada = "Nothing";
		normal = "Normal";
		fuerte = "Strong";
		todo = "All";
		nada2 = "None";

		// de la pantalla

		Organismos = "Organism   : ";
		mutantes = "Mutants       : ";
		masaTotal = "Total Mass  : ";
		masa = "Mass           : ";
		biomasa = "Bio-mass     : ";
		velocidadMedia = "Avg. Speed    : ";
		tamanoMedi = "Avg. Size    : ";
		tasaMutacionMedia = "Avg. Mut. rate.: 1/";
		vidaMdia = "Avg. Lifespan: ";
		resistensiaATB = "Tox. Resistense: ";
		temperatura = "Temperature  : ";
		temOptimaMedia = "Avg. Opt. Temp.: ";
		antibioticoON = "Toxines  ON";
		antibioticoOFF = "Toxines  OFF";
		guardarYcerrar = "Save and Close";
		playPausa = "Play/Pause";
		verOcultar = "View / hide";
		menuGuardar = "Save menu";
		tomarMuestra = "Take Sample";
		guardarTodos = "Save all";
		guardarMarcados = "Save Marked";
		guardarNoMarcados = "Save Unmarked";
		marcarDesmarcar = "Mark/Unmark";
		antibiotico = "Toxines!!!";
		catastrofe = "catastrophe!!!";
		verEnergia = "See Energy";
		verMasa = "See Mass";
		verOrgansimo = "See Org.";
		verAlelos = "See Alleles";
		verFenotipo = "See Phenotype";
		verGenotipo = "See Genotype";
		verDatos = "See Data";
		terminarGuardarMensaje = "End simulation, collected data will be saved";
		terminarMensaje = "End simulation";
		simulacionEnPausa = "Pause";
		oprimaPlayPausa = "Press Play/Pause to continue";
		ordenar = "Arrange";
		Siguiente = "Next";
		parar = "Stop";
	}

}
