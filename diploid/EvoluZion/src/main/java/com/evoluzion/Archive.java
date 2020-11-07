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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archive {

	private File f;
	private FileWriter fr;

	public Archive() {

	}

	public void createArchive(String str) {
		try {
			f = new File(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			fr = new FileWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeArchive(String line) {

		try {
			fr.append(line);
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public void cerrarArchivo() {

		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
