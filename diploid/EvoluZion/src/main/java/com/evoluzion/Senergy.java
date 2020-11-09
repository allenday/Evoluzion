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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Senergy {

	World m;

	private final Sprite imagen;
	double energy;
	float ancho = 7;
	float alto = 7;
	int speed;
	Vector2 posicion;
	Vector2 direccion;
	Rectangle borde;
	boolean visible = true;

	public Senergy(Vector2 posicion, World m) {

		this.m = m;
		// energia=m.Senergia;
		this.posicion = posicion;
		direccion = new Vector2();

		ancho = (int) (ancho / m.zoom);
		alto = (int) (alto / m.zoom);

		speed = 30;
		borde = new Rectangle();

		borde.height = alto;
		borde.width = ancho;

		borde.x = posicion.x;
		borde.y = posicion.y;

		direccion.y = -1;
		direccion.x = 0;

		imagen = new Sprite(m.organismTexture.getRegions().get(24));
		imagen.setPosition(this.posicion.x, this.posicion.y);
		imagen.setSize(ancho, alto);

	}

	public void verObjeto(SpriteBatch sb) {
		if (visible == true) {
			sb.begin();
			imagen.draw(sb);
			sb.end();
		}

	}

	public void verBorde(ShapeRenderer sr) {

		sr.begin(ShapeType.Filled);

		sr.setColor(Color.CYAN);
		sr.rect(borde.x, borde.y, borde.width, borde.height);
		sr.end();

	}

	public void update() {

		posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x) * speed,
				Gdx.graphics.getDeltaTime() * (direccion.y) * speed);

		imagen.setPosition(posicion.x, posicion.y);
		// imagen.setSize(ancho, alto);
		borde.x = posicion.x;
		borde.y = posicion.y;
		// borde.height= alto;
		// borde.width= ancho;

		if (posicion.y < 0) {
			reset();
		}

	}

	public void reset() {

		// posicion.x= (float) (Math.random()*m.ancho);
		posicion.y = m.alto + 10;
		if (posicion.x > m.ancho / 2) {
			energy = m.SenergiaR;
		}
		if (posicion.x < m.ancho / 2) {
			energy = m.Senergia;
		}

		visible = true;

	}

}